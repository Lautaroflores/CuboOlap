package olapcube.metricas;
import java.util.List;

/**
 * Clase abstracta que calcula el valor mínimo de una lista de valores. (Extiende a Medida)
 */
public class Minimo extends Medida {

    public Minimo() {
        super("Minimo");
    }

    @Override
    public double calcular(List<Double> valores) {
        double minimo = Double.MAX_VALUE;
        for (double valor : valores) {
            if (valor < minimo) {
                minimo = valor;
            }
        }
        return minimo;
    }
}
