package olapcube.estructura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import olapcube.Proyeccion;
import olapcube.configuration.ConfigCubo;
import olapcube.configuration.ConfigDimension;

import olapcube.metricas.*;

/**
 * Representa un cubo OLAP.
 */
public class Cubo {
    private Map<String, Dimension> dimensiones; // Mapeo de nombres de dimensión al objeto de la dimensión
    private static Map<String, Medida> medidas;        // Mapeo de nombres de medida al objeto de la medida
    private List<Celda> celdas;                 // Lista de celdas del cubo
    private List<String> nombresHechos;         // Nombres de los hechos (columnas con valores del dataset de hechos)
    
    private Cubo() {
        dimensiones = new HashMap<>();
        celdas = new ArrayList<>();
        nombresHechos = new ArrayList<>();

        // TODO: Externalizar esta configuracion !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        medidas = new HashMap<>();
        //medidas.put("count", new Count());
    }
    public void setMedidas(String nombre, Medida medida){
        medidas.put(nombre, medida);
    }
    /**
     * Método constructor que permite crear un cubo a partir de una configuración
     * 
     * @param config Configuración del cubo
     * @return Cubo
     */
    public static Cubo crearFromConfig(ConfigCubo config) {
        Cubo cubo = new Cubo();

        // Creacion de dimensiones
        for (ConfigDimension configDimension : config.getDimensiones()) {
            cubo.agregarDimension(Dimension.crear(configDimension));
        }

        // Creacion de hechos
        cubo.nombresHechos = List.of(config.getHechos().getNombresHechos());

        int indiceCelda = 0;
        for (String[] datos : config.getHechos().getDatasetReader().read()) {
            Celda celda = new Celda();
            for (String hecho : cubo.nombresHechos) {
                int columnaHecho = config.getHechos().getColumnaHecho(hecho);
                celda.agregarHecho(hecho, Double.parseDouble(datos[columnaHecho]));
            }
            cubo.agregarCelda(celda);

            // Agrega la celda a las dimensiones
            for (Dimension dimension : cubo.dimensiones.values()) {
                int columnaFkHechos = dimension.getColumnaFkHechos();
                int fk = Integer.parseInt(datos[columnaFkHechos]);
                dimension.agregarHecho(fk, indiceCelda);
            }

            indiceCelda++;
        }

        return cubo;
    }

    public List<String> getNombresHechos() {
        return nombresHechos;
    }

    public List<String> getNombresDimensiones() {
        return new ArrayList<>(dimensiones.keySet());
    }

    public List<String> getMedidas() {
        return new ArrayList<>(medidas.keySet());
    }

    public Medida getMedida(String nombre) {
        return medidas.get(nombre);
    }

    public Dimension getDimension(String nombre) {
        if (!dimensiones.containsKey(nombre)) {
            throw new IllegalArgumentException("Dimension no encontrada: " + nombre);
        }
        return dimensiones.get(nombre);
    }

    public void agregarDimension(Dimension dim1) {
        dimensiones.put(dim1.getNombre(), dim1);
    }

    public void agregarCelda(Celda celda) {
        

        // Valida que la celda tenga los mismos hechos que las celdas anteriores
        if (!celdas.isEmpty()) {
            Celda primeraCelda = celdas.get(0);
            if (!primeraCelda.mismosHechos(celda)) {
                throw new IllegalArgumentException("La celda no tiene los mismos hechos que las celdas anteriores.");
            }
        }
    
        // Valida que la celda tenga la misma cantidad de hechos que los hechos del cubo
        if (celda.getHechos().size() != nombresHechos.size()) {
            throw new IllegalArgumentException("La celda no tiene la misma cantidad de hechos que los hechos del cubo.");
        }
    
        // Valida que la celda tenga la misma cantidad de valores para cada hecho
        for (String hecho : nombresHechos) {
            if (!celda.getHechos().containsKey(hecho)) {
                throw new IllegalArgumentException("La celda no contiene el hecho: " + hecho);
            }
        }
    
        celdas.add(celda);
    }       

    @Override
    public String toString() {
        return "Cubo [celdas=" + celdas.size() + ", dimensiones=" + dimensiones.keySet() + ", medidas=" + medidas.size() + "]";
    }

    /**
     * Obtiene las celdas a partir de un conjunto de indices
     * @param indices Conjunto de indices
     * @return Lista de celdas
     */
    private List<Celda> celdasFromIndices(Set<Integer> indices) {
        List<Celda> celdas = new ArrayList<>();
        for (Integer indice : indices) {
            celdas.add(this.celdas.get(indice));
        }
        return celdas;
    }

    /**
     * Obtiene una celda a partir de una dimensión y un valor, reduciendo las dos dimensiones restantes.
     * 
     * @param dimension La dimensión a la que pertenece el valor
     * @param valor El valor de la dimensión a buscar
     * @return Celda que agrupa todas las celdas que contienen el valor en esa dimensión
     */
    public Celda getCelda(Dimension dimension, String valor) {
        return Celda.agrupar(celdasFromIndices(dimension.getIndicesCeldas(valor)));
    }

    /**
     * Obtiene una celda a partir de dos dimensiones y dos valores, reduciendo la dimensión restante.
     * 
     * @param dim1 La primera dimensión
     * @param valor1 El valor de la primera dimensión
     * @param dim2 La segunda dimensión
     * @param valor2 El valor de la segunda dimensión
     * @return Celda que agrupa todas las celdas que contienen los valores en esas dos dimensiones
     */
    public Celda getCelda(Dimension dim1, String valor1, Dimension dim2, String valor2) {
        Set<Integer> indicesComunes = celdasComunes(dim1.getIndicesCeldas(valor1), dim2.getIndicesCeldas(valor2));
        return Celda.agrupar(celdasFromIndices(indicesComunes));
    }

    /**
     * Obtiene el conjunto de índices que existen en ambos conjuntos (intersección)
     * 
     * @param set1 El primer conjunto de índices
     * @param set2 El segundo conjunto de índices
     * @return Conjunto de índices que representa la intersección de ambos conjuntos
     */
    private static Set<Integer> celdasComunes(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> nuevo = new HashSet<>(set1);
        nuevo.retainAll(set2);
        return nuevo;
    }

    public Proyeccion proyectar() {
        return new Proyeccion(this);
    }
}
