package olapcube.acciones;

import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;

public class ProyectarDimension {
    private Scanner scanner;
    private String[] dimensiones;

    public ProyectarDimension(Scanner scanner, String[] dimensiones) {
        this.scanner = scanner;
        this.dimensiones = dimensiones;
    }

    public void proyeccionDimensiones(Cubo cubo, Dimension dimension) {
        Proyeccion proyeccion = cubo.proyectar();
        System.out.println("Seleccione la dimensión secundaria para proyectar:");
        System.out.println("1. POS, 2. Fechas, 3. Productos");
        int dimSecundaria = scanner.nextInt();
        scanner.nextLine();
        String dimensionSecundaria = dimensiones[dimSecundaria - 1];
        proyeccion.print(dimension.getNombre(), dimensionSecundaria);
    }
}
