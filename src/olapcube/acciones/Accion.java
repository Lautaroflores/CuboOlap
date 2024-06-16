package olapcube.acciones;

import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;

public abstract class Accion {
    protected Cubo cubo;
    protected String[] dimensiones = {"POS", "Fechas", "Productos"};
    public abstract void ejecutar(Cubo cubo, Proyeccion proyeccion); {
    //Reescribir en subclases
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