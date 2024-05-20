package olapcube.readers;

/**
 * Interfaz que define el comportamiento de un lector de datasets
 */
public interface DatasetReader {
    /**
     * Método que permite leer un dataset y retornar una matriz con los datos
     * @return Matriz con los datos del dataset
     */
    public String[][] read();
}
