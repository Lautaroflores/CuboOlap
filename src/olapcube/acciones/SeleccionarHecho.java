package olapcube.acciones;

import olapcube.configuration.ConfigHechos;
import java.util.Scanner;
/**
 * Clase que representa la acción de seleccionar un hecho
 */
public class SeleccionarHecho {
    private ConfigHechos configHechos; // Configuración de los hechos
    private Scanner scanner; // Scanner para leer la entrada del usuario
    private static String hecho; // Hecho seleccionado

    /**
     * Constructor de la clase
     * 
     * @param configHechos Configuración de los hechos
     * @param scanner Scanner para leer la entrada del usuario
     */

    public SeleccionarHecho(ConfigHechos configHechos, Scanner scanner) {
        this.configHechos = configHechos;
        this.scanner = scanner;
    }

   
    public void ejecutar() {
        String[] nombresHechos = configHechos.getNombresHechos();
        System.out.println("Seleccione el hecho a trabajar indicando el número de columna:");
        for (int i = 0; i < nombresHechos.length; i++) {
            System.out.println((i + 1) + ". " + nombresHechos[i]);
        }
    
        int columnaSeleccionada = scanner.nextInt();
        String hechoSeleccionado = nombresHechos[columnaSeleccionada - 1];
        for (String hecho : nombresHechos) {
            if (configHechos.getColumnaHecho(hecho).equals(columnaSeleccionada)) {
                hechoSeleccionado = hecho;
                break;
            }
        }
    
        if (hechoSeleccionado != null) {
            System.out.println("Trabajando con el hecho '" + hechoSeleccionado + "' en la columna " + columnaSeleccionada);
            hecho = hechoSeleccionado; 
        } else {
            System.out.println("Número de columna no válido.");
        }
    }
    
    public static String getHecho() {
        return hecho; 
    }
}