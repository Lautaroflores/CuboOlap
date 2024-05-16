import java.util.ArrayList;
import java.util.List;

public class CuboOlap {
    private List<Dimension> dimensiones;
    private Hechos hechos;
    private Dimension fechas;
    private Dimension productos;
    private Dimension puntosVenta;

    public CuboOlap() {
        this.dimensiones = new ArrayList<>();
    }

    public void addDimension(Dimension dimension) {
        dimensiones.add(dimension);
    }

    public void setHechos(Hechos hechos) {
        this.hechos = hechos;
    }

    // Métodos adicionales para operaciones OLAP

    public CuboOlap(Dimension fechas, Dimension productos, Dimension puntosVenta, Hechos hechos) {
        this.fechas = fechas;
        this.productos = productos;
        this.puntosVenta = puntosVenta;
        this.hechos = hechos;
    }
}


    // Métodos adicionales relacionados con el cubo OLA