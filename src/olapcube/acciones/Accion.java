package olapcube.acciones;

import java.util.Scanner;
import olapcube.Proyeccion;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;

/**
 * Clase abstracta que representa una acción sobre un cubo OLAP
 */

public abstract class Accion {
    protected Cubo cubo; // Cubo sobre el que se realiza la acción
    protected String[] dimensiones = {"POS", "Fechas", "Productos"}; // Nombres de las dimensiones
    /**
     * Constructor de la clase
     * 
     * @param cubo Nombre del cubo
     * @param dimensiones Nombre de las dimensiones
     */
    public abstract void ejecutar(Cubo cubo, Proyeccion proyeccion); {
    //No tiene que hacer nada, es para poder reescribirlo en cada subclase.
    }

    public void proyeccionDimensiones(Cubo cubo, Dimension dimension) {
        Scanner scanner = new Scanner(System.in);
        Proyeccion proyeccion = cubo.proyectar();
        System.out.println("Seleccione la dimensión secundaria para proyectar:");
        System.out.println("1. POS, 2. Fechas, 3. Productos");
        int dimSecundaria = scanner.nextInt();
        scanner.nextLine();
        String dimensionSecundaria = dimensiones[dimSecundaria - 1];
        proyeccion.print(dimension.getNombre(), dimensionSecundaria);
        
    }
}