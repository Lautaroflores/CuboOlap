package olapcube.metricas;
import java.util.List;

/**
 * Clase abstracta que hace un conteo de los elementos de una lista de valores. (Extiende a Medida)
 */

public class Count extends Medida{

    public Count() {
        super("Count");
    }

    @Override
    public double calcular(List<Double> valores) {
        return valores.size();
    }

}
