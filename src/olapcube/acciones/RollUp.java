package olapcube.acciones;

import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;

public class RollUp {
    private Cubo cubo;

    public RollUp(Cubo cubo) {
        this.cubo = cubo;
    }

    public void aplicar(String nombreDimension) {
        Dimension dimension = cubo.getDimension(nombreDimension);
        dimension.rollUp();
        System.out.println("Se aplicó Roll-Up en la dimensión: " + nombreDimension);

        System.out.println("Nivel actual de la dimensión " + nombreDimension + ": " + dimension.getNivelActual());
    }
}