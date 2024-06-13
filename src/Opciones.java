import java.util.Arrays;
import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.acciones.SeleccionarDimension;
import olapcube.acciones.Slice;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;
import olapcube.metricas.*;


public class Opciones {
    private Scanner scanner; // Declara un objeto Scanner como campo de la clase
    String[] dimensiones = {"POS", "Fechas", "Productos"};
    
    public Opciones(Scanner scanner) {
        this.scanner = scanner; // Inicializa el objeto Scanner con el que se pasa como argumento
       
    }

    public Medida seleccionarMedida() {
        System.out.println("Seleccione el número de la métrica a ejecutar:");
        System.out.println("1. Suma");
        System.out.println("2. Media");
        System.out.println("3. Count");
        System.out.println("4. Máximo");
        System.out.println("5. Mínimo");
        int opcion = scanner.nextInt(); // Se lee la opción ingresada por el usuario
        scanner.nextLine(); // Consumir la nueva línea después del entero

        // Selección de la medida
        switch (opcion) {
            case 1:
                return new Suma();
            case 2:
                return new Media();
            case 3:
                return new Count();
            case 4:
                return new Maximo();
            case 5:
                return new Minimo();
            default:
                return null; // Retornar null si la opción no es válida
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
        seleccionarDimension.ejecutar(dimensiones[dim - 1]);

        proyeccion.seleccionarHecho("costo");

        scanner.close();

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

        proyeccion.seleccionarHecho("costo");
        scanner.close();
    }

    public Cubo seleccionarDimensionParaSlice(Cubo cubo) {

        System.out.println("Ingrese la dimensión para realizar el Slice:");
        System.out.println("1. POS, 2. Fechas, 3. Productos");
        int dimSlice = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese el valor específico para la dimensión " + dimensiones[dimSlice - 1] +":");
        Dimension dimSeleccionada = cubo.getDimension(dimensiones[dimSlice - 1]);
        if (dimSeleccionada != null) {
            System.out.println("Valores únicos de la dimensión " + dimensiones[dimSlice - 1] + ": " + Arrays.toString(dimSeleccionada.getValores()));
        } else {
            System.out.println("Valor no válido");
            return null;
        }

        String valor = scanner.nextLine();

        System.out.println("Dimensión secundaria (1. POS, 2. Fechas, 3. Productos):");
        int dim2 = scanner.nextInt();
        scanner.nextLine();

        String[] dimensiones = {"POS", "Fechas", "Productos"};

        Cubo cuboSlice = Slice.slice(cubo, dimensiones[dimSlice - 1], valor);

        if (dimSlice < 1 || dimSlice > 3 || dim2 < 1 || dim2 > 3 || dimSlice == dim2) {
            System.out.println("Las dimensiones seleccionadas no son válidas");
            return null;
        }

        cuboSlice.proyectar().print(dimensiones[dimSlice - 1], dimensiones[dim2 - 1]);
        scanner.close();
        return cuboSlice;
    }

    public void seleccionarDimensionParaDice(Cubo cubo) {
        
            System.out.println("Ingrese la dimensión para realizar el Dice:");
            System.out.println("1. POS, 2. Fechas, 3. Productos");
            int dimDice = scanner.nextInt();
            scanner.nextLine();
        
            System.out.println("Ingrese los valores específicos para la dimensión " + dimensiones[dimDice - 1] + " separados por comas:");
            Dimension dimSeleccionada = cubo.getDimension(dimensiones[dimDice - 1]);
            if (dimSeleccionada != null) {
                System.out.println("Valores únicos de la dimensión " + dimensiones[dimDice - 1] + ": " + Arrays.toString(dimSeleccionada.getValores()));
            } else {
                System.out.println("Valor no válido");
                return;
            }
        
            String valores = scanner.nextLine();
            String[] valoresSeleccionados = valores.split(",");
        
            // Aquí se debería modificar o crear un método en Cubo para filtrar por múltiples valores
           cubo.filtrarDimension(dimensiones[dimDice - 1], valoresSeleccionados);
        
            System.out.println("Dimensión secundaria (1. POS, 2. Fechas, 3. Productos):");
            int dim2 = scanner.nextInt();
            scanner.nextLine();
        
            System.out.println("Ingrese los valores específicos para la dimensión " + dimensiones[dim2 - 1] + " separados por comas:");
            Dimension dimSeleccionada2 = cubo.getDimension(dimensiones[dim2 - 1]);
            if (dimSeleccionada2 != null) {
                System.out.println("Valores únicos de la dimensión " + dimensiones[dim2 - 1] + ": " + Arrays.toString(dimSeleccionada2.getValores()));
            } else {
                System.out.println("Valor no válido");
                return;
            }
        
            String valores2 = scanner.nextLine();
            String[] valoresSeleccionados2 = valores2.split(",");
        
            // Similar al paso anterior, filtrar la segunda dimensión por múltiples valores
             cubo.filtrarDimension(dimensiones[dim2 - 1], valoresSeleccionados2);
        }
    }

    


