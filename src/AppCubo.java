import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.configuration.ConfigCubo;
import olapcube.configuration.ConfigDimension;
import olapcube.configuration.ConfigHechos;
import olapcube.estructura.Cubo;
import olapcube.metricas.*;
import olapcube.acciones.SeleccionarDimension;



public class AppCubo {

    private static final String[] NOMBRES_HECHOS = new String[] {"cantidad", "valor_unitario", "valor_total", "costo"};
    private static final Integer[] COLUMNAS_HECHOS = new Integer[] {3, 4, 5, 6};
    
    private static ConfigCubo crearConfigCubo() {
        return new ConfigCubo(
            "Cubo de ventas",
            ConfigHechos.configCSV(
                "src/olapcube/datasets-olap/ventas.csv", 
                NOMBRES_HECHOS,
                COLUMNAS_HECHOS
            ),
            new ConfigDimension[] {
                ConfigDimension.configCSV("Productos", "src/olapcube/datasets-olap/productos.csv", 0, 3, 0),
                ConfigDimension.configCSV("Fechas", "src/olapcube/datasets-olap/fechas.csv", 0, 5, 2),
                ConfigDimension.configCSV("POS", "src/olapcube/datasets-olap/puntos_venta.csv", 0, 4, 1)
            }
        );
    }

    public static void main(String[] args) {
    
        try {
            Scanner scanner = new Scanner(System.in);
            Opciones opciones = new Opciones(scanner);
    
            Medida medidaSeleccionada = opciones.seleccionarMedida();
            if (medidaSeleccionada == null) {
                System.out.println("La medida seleccionada no es válida.");
                scanner.close();
                return;
            }
    
            ConfigCubo config = crearConfigCubo();
            
            Cubo cubo = Cubo.crearFromConfig(config);
            cubo.setMedidas(medidaSeleccionada.getClass().getSimpleName().toLowerCase(), medidaSeleccionada);
            System.out.println("Seleccione lo que quiere probar:");
            System.out.println("1. Proyección en 2D \n2. DrillDown \n3. RollUp \n4. Slice \n5. Dice");
            int opcion = scanner.nextInt();
            if (opcion == 1) {
                
                System.out.println("Seleccione las dimensiones:");
                System.out.println("Dimensión principal (1. POS, 2. Fechas, 3. Productos):");
                int dim1 = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Dimensión secundaria (1. POS, 2. Fechas, 3. Productos):");
                int dim2 = scanner.nextInt();
                scanner.nextLine();
            
                String[] dimensiones = {"POS", "Fechas", "Productos"};
                if (dim1 < 1 || dim1 > 3 || dim2 < 1 || dim2 > 3 || dim1 == dim2) {
                    System.out.println("Las dimensiones seleccionadas no son válidas");
                    scanner.close();
                    return;
                }
            
                Proyeccion proyeccion = new Proyeccion(cubo);
                SeleccionarDimension seleccionarDimension = new SeleccionarDimension(cubo, proyeccion);
                seleccionarDimension.ejecutar(dimensiones[dim1 - 1], dimensiones[dim2 - 1]);
            
                proyeccion.seleccionarHecho("costo");
            
            
                scanner.close();
            }
            if (opcion == 4) {

              // ...
                Proyeccion proyeccion = cubo.proyectar();
                proyeccion.print("POS");

                proyeccion.seleccionarHecho("costo");
                proyeccion.print("POS", "Fechas");
                Cubo cuboSlice = cubo.slice("Fechas", "2017");

                // Aquí puedes imprimir el cuboSlice o hacer lo que necesites con él
                cuboSlice.proyectar().print("POS", "Fechas");
                scanner.close();
            }

            if (opcion == 5) {

                Proyeccion proyeccion = cubo.proyectar();
                proyeccion.print("POS");

                proyeccion.seleccionarHecho("costo");
                proyeccion.print("POS", "Fechas");
                Cubo cuboDice = cubo.dice("Fechas", new String[] {"2017", "2018"});
                cuboDice.dice("POS", new String[] {"Canada", "France"}).proyectar().print("POS", "Fechas");
            }

            else {
                System.out.println("Opción no válida");
            }

            
        }
        catch(Error error ){
        System.out.println("Error: " + error);    
        }
    }
}