package olapcube.estructura;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import olapcube.configuration.ConfigDimension;

public class Dimension {
    private String nombre;
    private Map<String, Set<Integer>> valoresToCeldas;
    private Map<Integer, String> idToValores;
    private int columnaFkHechos;
    private String[] niveles;  // Jerarquía de niveles
    private int nivelActual;   // Nivel actual en la jerarquía
    private int[] columnasValores; // Columnas correspondientes a los niveles en el dataset
    private ConfigDimension configDimension; // Configuración de la dimensión

    private Dimension(String nombre, String[] niveles, int[] columnasValores, ConfigDimension configDimension) {
        this.nombre = nombre;
        this.valoresToCeldas = new HashMap<>();
        this.idToValores = new HashMap<>();
        this.niveles = niveles;
        this.nivelActual = 0; // Inicialmente en el nivel más bajo (detalle)
        this.columnasValores = columnasValores;
        this.configDimension = configDimension;
    }

    public static Dimension crear(ConfigDimension configDimension) {
        String nombre = configDimension.getNombre();
        String[] niveles = configDimension.getNiveles();
        int[] columnasValores = configDimension.getColumnasValores();
        Dimension dim = new Dimension(nombre, niveles, columnasValores, configDimension);
        dim.columnaFkHechos = configDimension.getColumnaFkHechos();
        for (String[] datos : configDimension.getDatasetReader().read()) {
            int pkDimension = Integer.parseInt(datos[configDimension.getColumnaKey()]);
            String valor = datos[columnasValores[dim.nivelActual]];
            dim.idToValores.put(pkDimension, valor);
            dim.valoresToCeldas.put(valor, new HashSet<>());
        }
        return dim;
    }

    public Dimension copiar() {
        Dimension nueva = new Dimension(this.nombre, this.niveles, this.columnasValores, this.configDimension);
        nueva.valoresToCeldas = new HashMap<>();
        for (String valor : this.valoresToCeldas.keySet()) {
            nueva.valoresToCeldas.put(valor, this.valoresToCeldas.get(valor));
        }
        nueva.idToValores = this.idToValores;
        nueva.columnaFkHechos = this.columnaFkHechos;
        nueva.nivelActual = this.nivelActual;
        return nueva;
    }

    public void filtrar(String valor) {
        filtrar(new String[] {valor});
    }

    public void filtrar(String[] valores) {
        HashMap<String, Set<Integer>> nuevosValores = new HashMap<>();
        for (String valor : valores) {
            nuevosValores.put(valor, valoresToCeldas.get(valor));
        }
        valoresToCeldas = nuevosValores;
    }

    @Override
    public String toString() {
        return "Dimension [nombre=" + nombre + "]";
    }

    public String[] getValores() {
        return valoresToCeldas.keySet().toArray(new String[0]);
    }

    public Set<Integer> getIndicesCeldas(String valor) {
        return valoresToCeldas.get(valor);
    }

    public String getNombre() {
        return nombre;
    }

    public String getValorFromId(Integer id) {
        return idToValores.get(id);
    }

    public int getColumnaFkHechos() {
        return columnaFkHechos;
    }

    public String getNivelActual() {
        return niveles[nivelActual];
    }

    public void agregarHecho(int idValor, int indiceCelda) {
        if (!idToValores.containsKey(idValor)) {
            throw new IllegalArgumentException("El id " + idValor + " del valor no existe en la dimension " + nombre);
        }
        valoresToCeldas.get(idToValores.get(idValor)).add(indiceCelda);
    }

    public void rollUp() {
        if (nivelActual > 0) {
            nivelActual--;
            actualizarValoresToCeldas();
        } else {
            System.out.println("No se puede hacer Roll-up, ya está en el nivel más alto.");
        }
    }

    public void drillDown() {
        if (nivelActual < niveles.length - 1) {
            nivelActual++;
            actualizarValoresToCeldas();
        } else {
            System.out.println("No se puede hacer Drill-down, ya está en el nivel más bajo.");
        }
    }

    private void actualizarValoresToCeldas() {
        valoresToCeldas.clear();
        for (String[] datos : configDimension.getDatasetReader().read()) {
            String valor = datos[columnasValores[nivelActual]];
            int pkDimension = Integer.parseInt(datos[configDimension.getColumnaKey()]);
            idToValores.put(pkDimension, valor);
            valoresToCeldas.putIfAbsent(valor, new HashSet<>());
            valoresToCeldas.get(valor).add(pkDimension);
        }
    }
}