package olapcube;

import java.util.Arrays;

import olapcube.estructura.Celda;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;

/**
 * Clase que representa una proyeccion de un cubo OLAP
 */
public class Proyeccion {
    private Cubo cubo;              // Cubo sobre el que se realiza la proyeccion
    private int maxFilas = 10;      // Maximo de filas a mostrar
    private int maxColumnas = 10;   // Maximo de columnas a mostrar
    private String hecho;           // Hecho a proyectar
    private String medida;          // Medida a proyectar
    
    // Atributos para mostrar en consola
    private String formatoCelda = "%8.8s";
    private String separador = " | ";

    /**
     * Constructor de la clase
     * 
     * @param cubo Cubo sobre el que se realiza la proyeccion
     */
    public Proyeccion(Cubo cubo) {
        this.cubo = cubo;
        this.hecho = cubo.getNombresHechos().get(0);    // Selecciona el primer hecho por defecto
        this.medida = cubo.getMedidas().get(0);         // Selecciona la primera medida por defecto
    }

    public void seleccionarHecho(String hecho) {
        this.hecho = hecho;
    }

    public void seleccionarMedida(String medida) {
        this.medida = medida;
    }

    /**
     * Muestra la proyeccion de una dimension
     * 
     * @param nombreDimension Nombre de la dimension a proyectar
     */
    public void print(String nombreDimension) {
        Dimension dimension = cubo.getDimension(nombreDimension);
        System.out.println("Proyeccion de " + dimension.getNombre());
        
        String[] columnas = new String[] {hecho + " (" + medida + ")"};

        // Genera celdas de la proyeccion
        Double[][] valores = new Double[dimension.getValores().length][1];
        for (int i = 0; i < dimension.getValores().length; i++) {
            String valorDimension = dimension.getValores()[i];
            Celda celdaAgrupada = cubo.getCelda(dimension, valorDimension);
            valores[i][0] = cubo.getMedida(medida).calcular(celdaAgrupada.getValores(hecho));
        }

        // Muestra en consola
        printTablaConsola(dimension.getValores(), columnas, valores);
    }

    /**
     * Muestra la proyeccion de dos dimensiones
     * 
     * @param nombreDim1 Nombre de la primera dimension (filas)
     * @param nombreDim2 Nombre de la segunda dimension (columnas)
     */
    public void print(String nombreDim1, String nombreDim2) {
        Dimension dimension1 = cubo.getDimension(nombreDim1);
        Dimension dimension2 = cubo.getDimension(nombreDim2);
        System.out.println("Proyeccion de " + dimension1.getNombre() + " vs " + dimension2.getNombre() + " - " + hecho + " (" + medida + ")");
        
        // Genera celdas de la proyeccion
        Double[][] valores = new Double[dimension1.getValores().length][dimension2.getValores().length];
        for (int i = 0; i < dimension1.getValores().length; i++) {
            String valorDim1 = dimension1.getValores()[i];
            for (int j = 0; j < dimension2.getValores().length; j++) {
                String valorDim2 = dimension2.getValores()[j];
                Celda celdaAgrupada = cubo.getCelda(dimension1, valorDim1, dimension2, valorDim2);
                valores[i][j] = cubo.getMedida(medida).calcular(celdaAgrupada.getValores(hecho));
            }
        }

        // Muestra en consola
        printTablaConsola(dimension1.getValores(), dimension2.getValores(), valores);
    }

    /**
     * Muestra una tabla en consola
     * 
     * @param indice Labels o valores de las filas
     * @param header Labels o valores de las columnas
     * @param valores Valores de la tabla
     */
    private void printTablaConsola(String[] indice, String[] header, Double[][] valores) {
        if (indice.length > maxFilas) {
            indice = Arrays.copyOfRange(indice, 0, maxFilas);
        }
        if (header.length > maxColumnas) {
            header = Arrays.copyOfRange(header, 0, maxColumnas);
        }

        // Print del header
        System.out.printf(formatoCelda, separador);
        for (String columna : header) {
            System.out.printf(formatoCelda, columna);
            System.out.print(separador);
        }
        System.out.println();
        System.out.println("---------------------------------");

        for (int i = 0; i < indice.length; i++) {
            System.out.printf(formatoCelda, indice[i]);
            System.out.print(separador);
            for (int j = 0; j < header.length; j++) {
                // TODO: Formatear bien el valor de la celda
                System.out.printf(formatoCelda, valores[i][j]);
                System.out.print(separador);
            }
            System.out.println();
        }
    }
}
