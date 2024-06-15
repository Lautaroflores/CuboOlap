package olapcube.acciones;

import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;

public class DrillDown {
    private Cubo cubo;

    public DrillDown(Cubo cubo) {
        this.cubo = cubo.copiar();
    }

    public void aplicar(String nombreDimension) {
        Dimension dimension = cubo.getDimension(nombreDimension);
        dimension.drillDown();
        System.out.println("Nivel actual de la dimensión " + nombreDimension + ": " + dimension.getNivelActual());
    }
}