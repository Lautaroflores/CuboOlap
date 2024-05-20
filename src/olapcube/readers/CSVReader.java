package olapcube.readers;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que permite leer un archivo CSV y retornar los datos en forma de matriz.
 * 
 * @see estructuras.olapcube.readers.DatasetReader
 */
public class CSVReader implements DatasetReader {
    private String path;                        // Ruta del archivo CSV
    private List<String> columnas;              // Columnas del archivo CSV
    private boolean ignorarIndice = false;      // Ignorar la primera columna
    private boolean header;                     // Indica si el archivo CSV tiene encabezado (primera fila)

    public CSVReader(String path) {
        this.path = path;
        this.columnas = new ArrayList<>();
        this.header = true;
    }

    public CSVReader(String path, String[] columnas) {
        this(path);
        this.columnas = Arrays.asList(columnas);
    }

    public List<String> getColumnas() {
        return columnas;
    }

    @Override
    public String[][] read() {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String linea;
            boolean primera = true;
            while ((linea = reader.readLine()) != null) {
                String[] valores = linea.split(";");
                if (ignorarIndice) {
                    valores = Arrays.copyOfRange(valores, 1, valores.length); 
                }
                if (primera && header) {
                    columnas = Arrays.asList(valores);
                    primera = false;
                } else {
                    if (columnas.size() != valores.length) {
                        throw new RuntimeException("El número de columnas no coincide con el número de valores");
                    }
                    rows.add(valores);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] data = new String[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        return data;
    }
}
