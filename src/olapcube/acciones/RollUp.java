package olapcube.acciones;

import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;

/**
 * Clase que representa la acción de Roll-Up, extiende a la clase Accion
 */

public class RollUp extends Accion{

    /**
     * Constructor de la clase
     * @param cubo Cubo sobre el que se aplicará la acción
     */
    
    public RollUp(Cubo cubo) {
        this.cubo = cubo;
    }

    public void aplicar(String nombreDimension) {
        Dimension dimension = cubo.getDimension(nombreDimension);
        dimension.rollUp();
        System.out.println("Se aplicó Roll-Up en la dimensión: " + nombreDimension);

        System.out.println("Nivel actual de la dimensión " + nombreDimension + ": " + dimension.getNivelActual());
    }

    @Override
    public void ejecutar(Cubo cubo, Proyeccion proyeccion) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione la dimensión para aplicar Roll-Up:");
        System.out.println("1. POS, 2. Fechas, 3. Productos");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        String dimension = dimensiones[opcion - 1];
        Dimension dim = cubo.getDimension(dimension);
        
        if (dim != null) {
            System.out.println("Nivel actual de la dimensión " + dimension + ": " + dim.getNivelActual());
            RollUp rollUp = new RollUp(cubo);
            rollUp.aplicar(dimension);
            proyeccion.seleccionarHecho();
            proyeccionDimensiones(cubo, dim);

        }
    }

}