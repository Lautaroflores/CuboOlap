package olapcube.configuration;

import olapcube.readers.CSVReader;
import olapcube.readers.DatasetReader;

/**
 * Clase que representa la configuración de una dimensión
 */
public class ConfigDimension {
    private String nombre;
    private DatasetReader datasetReader;
    private int columnaKey;
    private int columnaValor;
    private int columnaFkHechos;
    private String[] niveles;
    private int[] columnasValores; // Columnas correspondientes a los niveles en el dataset

    private ConfigDimension(String nombre, DatasetReader datasetReader, int columnaKey, int columnaValor, int columnaFkHechos, String[] niveles, int[] columnasValores) {
        this.nombre = nombre;
        this.datasetReader = datasetReader;
        this.columnaKey = columnaKey;
        this.columnaValor = columnaValor;
        this.columnaFkHechos = columnaFkHechos;
        this.niveles = niveles;
        this.columnasValores = columnasValores;
    }

    public static ConfigDimension configCSV(String nombre, String filePath, int columnaKey, int columnaValor, int columnaFkHechos, String[] niveles, int[] columnasValores) {
        return new ConfigDimension(nombre, new CSVReader(filePath), columnaKey, columnaValor, columnaFkHechos, niveles, columnasValores);
    }

    public String getNombre() {
        return nombre;
    }

    public DatasetReader getDatasetReader() {
        return datasetReader;
    }

    public int getColumnaKey() {
        return columnaKey;
    }

    public int getColumnaValor() {
        return columnaValor;
    }

    public int getColumnaFkHechos() {
        return columnaFkHechos;
    }

    public String[] getNiveles() {
        return niveles;
    }

    public int[] getColumnasValores() {
        return columnasValores;
    }

    @Override
    public String toString() {
        return "ConfigDimension [nombre=" + nombre + ", datasetReader=" + datasetReader + ", columnaKey=" + columnaKey
                + ", columnaValor=" + columnaValor + ", columnaFkHechos=" + columnaFkHechos + ", niveles=" + String.join(", ", niveles) + "]";
    }
}