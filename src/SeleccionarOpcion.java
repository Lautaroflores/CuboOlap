import java.util.Scanner;
import olapcube.Proyeccion;
import olapcube.acciones.SeleccionarHecho;
import olapcube.configuration.ConfigHechos;
import olapcube.acciones.SeleccionarDimension;
import olapcube.acciones.Slice;
import olapcube.estructura.Cubo;
import olapcube.metricas.*;
import olapcube.acciones.RollUp;
import olapcube.acciones.Dice;
import olapcube.acciones.DrillDown;

/**
 * Clase que permite seleccionar las acciones a realizar en el cubo OLAP.
 */

public class SeleccionarOpcion {
    private Scanner scanner; // Scanner para leer la entrada del usuario
    private ConfigHechos configHechos; // Configuración de los hechos
    
    /**
     * Constructor de la clase
     * 
     * @param scanner Scanner para leer la entrada del usuario
     * @param configHechos Configuración de los hechos
     */
    
    public SeleccionarOpcion(Scanner scanner) {
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

    // Método para seleccionar cero dimensiones
    public void seleccionarCeroDimensiones(Cubo cubo, Proyeccion proyeccion) {
        proyeccion.seleccionarHecho();
        proyeccion.printSinDimensiones();
    }

    public void seleccionarUnaDimension(Cubo cubo, Proyeccion proyeccion) {
        SeleccionarDimension seleccionarDimension = new SeleccionarDimension(cubo, proyeccion);
        seleccionarDimension.seleccionarUnaDimension(cubo, proyeccion);
    }

    public void seleccionarDosDimensiones(Cubo cubo, Proyeccion proyeccion) {
        SeleccionarDimension seleccionarDimension = new SeleccionarDimension(cubo, proyeccion);
        seleccionarDimension.seleccionarDosDimensiones(cubo, proyeccion);
    }

    public void seleccionarDimensionParaSlice(Cubo cubo, Proyeccion proyeccion) {
        Slice slice = new Slice();
        slice.ejecutar(cubo, proyeccion);
    }


    public void seleccionarDimensionParaDice(Cubo cubo, Proyeccion proyeccion) {
        Dice dice = new Dice();
        dice.ejecutar(cubo, proyeccion);
    }
    
    public void seleccionarDimensionParaRollUp(Cubo cubo, Proyeccion proyeccion) {
        RollUp rollUp = new RollUp(cubo);
        rollUp.ejecutar(cubo, proyeccion);
    }
    
    
    public void seleccionarDimensionParaDrillDown(Cubo cubo, Proyeccion proyeccion) {
        DrillDown drillDown = new DrillDown(cubo);
        drillDown.ejecutar(cubo, proyeccion);
    }

}