package olapcube.estructura;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import olapcube.configuration.ConfigDimension;

/**
 * Clase que representa una dimension de un cubo OLAP.
 */
public class Dimension {
    private String nombre;                              // Nombre de la dimension
    private Map<String, Set<Integer>> valoresToCeldas;  // Mapeo de valores de la dimensión a celdas en el cubo
    private Map<Integer, String> idToValores;           // Mapeo de ids (pk) de la dimensión a valores
    private int columnaFkHechos;                        // Columna que contiene la clave foránea en la tabla de los hechos
    
    /**
     * Constructor de la clase
     * 
     * @param nombre Nombre de la dimension
     */
    private Dimension(String nombre) {
        this.nombre = nombre;
        valoresToCeldas = new HashMap<>();
        idToValores = new HashMap<>();
    }

    /**
     * Método constructor que permite crear una dimensión a partir de una configuración
     * 
     * @param configDimension Configuración de la dimensión
     * @return Dimension
     */
    public static Dimension crear(ConfigDimension configDimension) {
        Dimension dim = new Dimension(configDimension.getNombre());
        dim.columnaFkHechos = configDimension.getColumnaFkHechos();
        for (String[] datos : configDimension.getDatasetReader().read()) {
            int pkDimension = Integer.parseInt(datos[configDimension.getColumnaKey()]);
            String valor = datos[configDimension.getColumnaValor()];
            dim.idToValores.put(pkDimension, valor);
            dim.valoresToCeldas.put(valor, new HashSet<>());
        }

        return dim;
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

    /**
     * Método que permite agregar un hecho a la dimensión
     * 
     * @param idValor id (pk) de la dimensión
     * @param indiceCelda índice de la celda en el cubo
     */
    public void agregarHecho(int idValor, int indiceCelda) {
        if (!idToValores.containsKey(idValor)) {
            throw new IllegalArgumentException("El id " + idValor + " del valor no existe en la dimension " + nombre);
        }
        valoresToCeldas.get(idToValores.get(idValor)).add(indiceCelda);
    }
}
