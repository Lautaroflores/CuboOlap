package olapcube.configuration;

import java.util.HashMap;
import java.util.Map;

import olapcube.readers.CSVReader;
import olapcube.readers.DatasetReader;

/**
 * Configuración de los hechos de un cubo OLAP.
 */
public class ConfigHechos {
    private Map<String, Integer> hechosColumnas;    // Mapeo de nombres de hechos a columnas en el dataset de hechos
    private DatasetReader datasetReader;            // DatasetReader de los hechos
    
    /**
     * Constructor de la clase
     * 
     * @param nombres Nombres de los hechos
     * @param columnas Columnas de los hechos (columnas con valores del dataset de hechos)
     * @param datasetReader DatasetReader de los hechos
     */
    private ConfigHechos(String[] nombres, Integer[] columnas, DatasetReader datasetReader) {
        if (nombres.length != columnas.length) {
            throw new RuntimeException("nombre y columnas deben tener misma longitud");
        }
        this.hechosColumnas = new HashMap<>();
        for (int i=0; i < nombres.length; i++) {
            hechosColumnas.put(nombres[i], columnas[i]);
        }
        this.datasetReader = datasetReader;
    }

    /**
     * Método constructor que permite crear una configuración de hechos a partir de un archivo CSV
     * 
     * @param filePath Ruta del archivo CSV
     * @param nombres Nombres de los hechos
     * @param columnas Columnas de los hechos (columnas con valores del dataset de hechos)
     * @return
     */
    public static ConfigHechos configCSV(String filePath, String[] nombres, Integer[] columnas) {
        return new ConfigHechos(nombres, columnas, new CSVReader(filePath));
    }

    public DatasetReader getDatasetReader() {
        return datasetReader;
    }

    public String[] getNombresHechos() {
        return hechosColumnas.keySet().toArray(new String[0]);
    }

    public Integer getColumnaHecho(String nombre) {
        return hechosColumnas.get(nombre);
    }

}
