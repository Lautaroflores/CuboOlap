package olapcube.acciones;

import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;


public class SeleccionarDimension {
    private Cubo cubo;
    private Proyeccion proyeccion;
    Scanner scanner = new Scanner(System.in);
    private String[] dimensiones = {"POS", "Fechas", "Productos"};
    
    public SeleccionarDimension(Cubo cubo, Proyeccion proyeccion) {
        this.cubo = cubo;
        this.proyeccion = proyeccion;
        
        }
        // Método para seleccionar una sola dimension
    public void ejecutar(String dimensionUnica) {
            
        Dimension dim = cubo.getDimension(dimensionUnica);
        if (dim == null) {
            System.out.println("Dimensión no válida");
            return;
        }

    
    }
    //Metodo para la proyección en 2D
    public void ejecutar(String dimensionPrincipal, String dimensionSecundaria) {
        Dimension dimPrincipal = cubo.getDimension(dimensionPrincipal);
        Dimension dimSecundaria = cubo.getDimension(dimensionSecundaria);

        if (dimPrincipal == null || dimSecundaria == null) {
            System.out.println("Dimensiones no válidas");
            return;
        }

    
    }
    public void seleccionarUnaDimension(Cubo cubo, Proyeccion proyeccion) {
        System.out.println("Seleccione la dimension:");
        System.out.println("1. POS, 2. Fechas, 3. Productos:");

        int dim = scanner.nextInt();
        scanner.nextLine();

        if (dim < 1 || dim > 3) {
            System.out.println("La dimensión seleccionada no es válida");
            return;
        }

        SeleccionarDimension seleccionarDimension = new SeleccionarDimension(cubo, proyeccion);
        seleccionarDimension.ejecutar(dimensiones[dim-1]);

        proyeccion.seleccionarHecho();

        proyeccion.print(dimensiones[dim - 1]);
    }

    public void seleccionarDosDimensiones(Cubo cubo, Proyeccion proyeccion) {
        System.out.println("Seleccione las dimensiones:");
        System.out.println("Dimensión principal (1. POS, 2. Fechas, 3. Productos):");
        int dim1 = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Dimensión secundaria (1. POS, 2. Fechas, 3. Productos):");
        int dim2 = scanner.nextInt();
        scanner.nextLine();

        if (dim1 < 1 || dim1 > 3 || dim2 < 1 || dim2 > 3 || dim1 == dim2) {
            System.out.println("Las dimensiones seleccionadas no son válidas");
            return;
        }

        SeleccionarDimension seleccionarDimension = new SeleccionarDimension(cubo, proyeccion);
        seleccionarDimension.ejecutar(dimensiones[dim1 - 1], dimensiones[dim2 - 1]);

        proyeccion.seleccionarHecho();
        proyeccion.print(dimensiones[dim1 - 1], dimensiones[dim2 - 1]);
    }
}