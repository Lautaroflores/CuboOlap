import java.util.Arrays;
import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.acciones.SeleccionarHecho;
import olapcube.configuration.ConfigHechos;
import olapcube.acciones.SeleccionarDimension;
import olapcube.acciones.Slice;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;
import olapcube.metricas.*;
import olapcube.acciones.RollUp;
import olapcube.acciones.DrillDown;

public class Opciones {
    private Scanner scanner;
    private ConfigHechos configHechos;
    String[] dimensiones = {"POS", "Fechas", "Productos"};
    
    public Opciones(Scanner scanner) {
        this.scanner = scanner;
        this.configHechos = ConfigHechos.configCSV("src/olapcube/datasets-olap/ventas.csv", new String[] {"cantidad", "valor_unitario", "valor_total", "costo"}, new Integer[] {3, 4, 5, 6});
    }

    public void iniciarSeleccionHecho() {
        SeleccionarHecho seleccionarHecho = new SeleccionarHecho(configHechos, scanner);
        seleccionarHecho.ejecutar();
    }

    public Medida seleccionarMedida() {
        System.out.println("Seleccione el número de la métrica a ejecutar:");
        System.out.println("1. Suma");
        System.out.println("2. Media");
        System.out.println("3. Count");
        System.out.println("4. Máximo");
        System.out.println("5. Mínimo");
        int opcion = scanner.nextInt();
        scanner.nextLine();

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
                return null;
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

    public void seleccionarDimensionParaSlice(Cubo cubo, Proyeccion proyeccion) {
        System.out.println("Ingrese la dimensión para realizar el Slice:");
        System.out.println("1. POS, 2. Fechas, 3. Productos");
        int dimSlice = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese el valor específico para la dimensión " + dimensiones[dimSlice - 1] + ":");
        Dimension dimSeleccionada = cubo.getDimension(dimensiones[dimSlice - 1]);
        if (dimSeleccionada != null) {
            System.out.println("Valores únicos de la dimensión " + dimensiones[dimSlice - 1] + ": " + Arrays.toString(dimSeleccionada.getValores()));
        } else {
            System.out.println("Valor no válido");
            return;
        }

        String valor = scanner.nextLine();

        System.out.println("Dimensión secundaria (1. POS, 2. Fechas, 3. Productos):");
        int dim2 = scanner.nextInt();
        scanner.nextLine();

        String[] dimensiones = {"POS", "Fechas", "Productos"};

        Cubo cuboSlice = Slice.slice(cubo, dimensiones[dimSlice - 1], valor);

        if (dimSlice < 1 || dimSlice > 3 || dim2 < 1 || dim2 > 3 || dimSlice == dim2) {
            System.out.println("Las dimensiones seleccionadas no son válidas");
            return;
        }
        proyeccion.seleccionarHecho();
        cuboSlice.proyectar().print(dimensiones[dimSlice - 1], dimensiones[dim2 - 1]);
    }

    public void seleccionarDimensionParaDice(Cubo cubo, Proyeccion proyeccion) {
        System.out.println("Ingrese la dimensión para realizar el Dice:");
        System.out.println("1. POS, 2. Fechas, 3. Productos");
        int dimDice = scanner.nextInt();
        scanner.nextLine();
        Dimension dimSeleccionada = cubo.getDimension(dimensiones[dimDice - 1]);
        
        System.out.println("Ingrese los valores específicos para la dimensión " + dimensiones[dimDice - 1] + " separados por comas:");
        if (dimSeleccionada != null) {
            System.out.println("Valores únicos de la dimensión " + dimensiones[dimDice - 1] + ": " + Arrays.toString(dimSeleccionada.getValores()));
        } else {
            System.out.println("Dimension no válida");
            return;
        }
    
        String valores = scanner.nextLine();
        String[] valoresSeleccionados = valores.split(",");
        
        boolean valoresValidos = Arrays.stream(valoresSeleccionados).allMatch(valor -> Arrays.asList(dimSeleccionada.getValores()).contains(valor.trim()));
    
        if (!valoresValidos) {
            System.out.println("Alguno de los valores ingresados no es válido.");
            return;
        }
        
        cubo.filtrarDimension(dimensiones[dimDice - 1], valoresSeleccionados);
    
        System.out.println("Dimensión secundaria (1. POS, 2. Fechas, 3. Productos):");
        int dim2 = scanner.nextInt();
        scanner.nextLine();
    
        System.out.println("Ingrese los valores específicos para la dimensión " + dimensiones[dim2 - 1] + " separados por comas:");
        Dimension dimSeleccionada2 = cubo.getDimension(dimensiones[dim2 - 1]);
        if (dimSeleccionada2 != null) {
            System.out.println("Valores únicos de la dimensión " + dimensiones[dim2 - 1] + ": " + Arrays.toString(dimSeleccionada2.getValores()));
        } else {
            System.out.println("Dimension no válida");
            return;
        }
    
        String valores2 = scanner.nextLine();
        String[] valoresSeleccionados2 = valores2.split(",");
    
        boolean valoresValidos2 = Arrays.stream(valoresSeleccionados2).allMatch(valor -> Arrays.asList(dimSeleccionada2.getValores()).contains(valor.trim()));
    
        if (!valoresValidos2) {
            System.out.println("Alguno de los valores ingresados no es válido.");
            return;
        }
        proyeccion.seleccionarHecho();
        cubo.filtrarDimension(dimensiones[dim2 - 1], valoresSeleccionados2);
        cubo.proyectar().print(dimensiones[dimDice - 1], dimensiones[dim2 - 1]);
    }
    
    public void seleccionarDimensionParaRollUp(Cubo cubo, Proyeccion proyeccion) {
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
    
    public void seleccionarDimensionParaDrillDown(Cubo cubo, Proyeccion proyeccion) {
        System.out.println("Seleccione la dimensión para aplicar Drill-Down:");
        System.out.println("1. POS, 2. Fechas, 3. Productos");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        String dimension = dimensiones[opcion - 1];
        Dimension dim = cubo.getDimension(dimension);

        if (dim != null) {
            String nivelActualAntesDeDrillDown = dim.getNivelActual();
            System.out.println("Nivel actual de la dimensión " + dimension + ": " + nivelActualAntesDeDrillDown);
            
            DrillDown drillDown = new DrillDown(cubo);
            drillDown.aplicar(dimension);
            proyeccion.seleccionarHecho();
            proyeccionDimensiones(cubo, dim);
        }

    }
    
    private void proyeccionDimensiones(Cubo cubo, Dimension dimension) {
        Proyeccion proyeccion = cubo.proyectar();
        System.out.println("Seleccione la dimensión secundaria para proyectar:");
        System.out.println("1. POS, 2. Fechas, 3. Productos");
        int dimSecundaria = scanner.nextInt();
        scanner.nextLine();
        String dimensionSecundaria = dimensiones[dimSecundaria - 1];
        proyeccion.seleccionarHecho();
        proyeccion.print(dimension.getNombre(), dimensionSecundaria);
    }
    }