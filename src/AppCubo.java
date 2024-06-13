

import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.configuration.ConfigCubo;
import olapcube.configuration.ConfigDimension;
import olapcube.configuration.ConfigHechos;
import olapcube.estructura.Cubo;
import olapcube.metricas.*;
import olapcube.acciones.Dice;
import olapcube.acciones.Slice;



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
            System.out.println("Medida seleccionada: " + medidaSeleccionada.getClass().getSimpleName());
            System.out.println("Seleccione lo que quiere probar:");
            System.out.println("1. Proyección en 1D\n2. Proyección en 2D \n3. DrillDown \n4. RollUp \n5. Slice \n6. Dice");
            int opcion = scanner.nextInt();
            Proyeccion proyeccion = cubo.proyectar();
            switch (opcion) {
                case 1:
                    opciones.seleccionarUnaDimension(cubo, proyeccion);
                    break;
                case 2:
                    opciones.seleccionarDosDimensiones(cubo, proyeccion);
                    break;

                case 5:
                    opciones.seleccionarDimensionParaSlice(cubo);
                    break;

                case 6: 
                    opciones.seleccionarDimensionParaDice(cubo);
                    break;
                default:
                    System.out.println("Por ahora no hay nada");
                    break;
            }
            
        }
        catch(Error error ){
        System.out.println("Error: " + error);    
        }
    }
}