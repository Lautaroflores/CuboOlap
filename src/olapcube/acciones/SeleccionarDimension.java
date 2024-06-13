package olapcube.acciones;

import olapcube.Proyeccion;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;


public class SeleccionarDimension {
    private Cubo cubo;
    private Proyeccion proyeccion;


    public SeleccionarDimension(Cubo cubo, Proyeccion proyeccion) {
        this.cubo = cubo;
        this.proyeccion = proyeccion;

    }

    public void ejecutar(String dimensionPrincipal, String dimensionSecundaria) {
        Dimension dimPrincipal = cubo.getDimension(dimensionPrincipal);
        Dimension dimSecundaria = cubo.getDimension(dimensionSecundaria);

        if (dimPrincipal == null || dimSecundaria == null) {
            System.out.println("Dimensiones no válidas");
            return;
        }

        // Muestra la proyección de la dimensión principal vs la dimensión secundaria
        proyeccion.print(dimensionPrincipal, dimensionSecundaria);
    }
}