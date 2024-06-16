package olapcube.acciones;

import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;  



public class DrillDown extends Accion{
    

    public DrillDown(Cubo cubo) {
        this.cubo = cubo;
    }

    public void aplicar(String nombreDimension) {
        Dimension dimension = cubo.getDimension(nombreDimension);
        dimension.drillDown();
        System.out.println("Se aplicó Drill-Down en la dimensión: " + nombreDimension);
        System.out.println("Nivel actual de la dimensión " + nombreDimension + ": " + dimension.getNivelActual());
    }

    @Override
    public void ejecutar(Cubo cubo, Proyeccion proyeccion) {
        Scanner scanner = new Scanner(System.in);
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


}