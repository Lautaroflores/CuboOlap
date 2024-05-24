import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.configuration.ConfigCubo;
import olapcube.configuration.ConfigDimension;
import olapcube.configuration.ConfigHechos;
import olapcube.estructura.Cubo;
import olapcube.metricas.*;

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
        Scanner scanner = new Scanner(System.in); // Crear un objeto Scanner

        // Crear una instancia de Opciones pasando el objeto Scanner
        Opciones opciones = new Opciones(scanner);

        // Usar Opciones para seleccionar una medida
        Medida medidaSeleccionada = opciones.seleccionarMedida();
        if (medidaSeleccionada == null) {
            // Salir del programa si la medida seleccionada es null
            System.out.println("La medida seleccionada no es válida.");
            scanner.close();
            return;
        }

        // Configuración del cubo
        ConfigCubo config = crearConfigCubo();
        Cubo cubo = Cubo.crearFromConfig(config); // Crear la instancia del cubo antes de configurar la medida
        cubo.setMedidas(medidaSeleccionada.getClass().getSimpleName().toLowerCase(), medidaSeleccionada);

        // Cerrar el objeto Scanner al finalizar
        scanner.close();
        
        System.out.println("Cubo creado: " + cubo);

        // Proyecciones
        Proyeccion proyeccion = cubo.proyectar();
        
        // Mostrar Dimension POS (hecho: default)
        proyeccion.print("POS");

        // Mostrar Dimensiones POS vs Fechas (hecho: cantidad)
        proyeccion.seleccionarHecho("costo");
        proyeccion.print("POS", "Fechas");
    }
    
}
