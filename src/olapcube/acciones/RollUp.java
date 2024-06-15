package olapcube.acciones;

import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;

public class RollUp {
    private Cubo cubo;

    public RollUp(Cubo cubo) {
        this.cubo = cubo.copiar();
    }

    public void aplicar(String nombreDimension) {
        Dimension dimension = cubo.getDimension(nombreDimension);
        dimension.rollUp();
        System.out.println("Nivel actual de la dimensión " + nombreDimension + ": " + dimension.getNivelActual());
    }
}