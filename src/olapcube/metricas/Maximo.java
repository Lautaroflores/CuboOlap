package olapcube.metricas;

import java.util.List;

/**
 * Clase abstracta que calcula el valor máximo de una lista de valores. (Extiende a Medida)
 */

public class Maximo extends Medida {

    public Maximo() {
        super("Maximo");
    }

    @Override
    public double calcular(List<Double> valores) {
        double maximo = Double.MIN_VALUE;
        for (double valor : valores) {
            if (valor > maximo) {
                maximo = valor;
            }
        }
        return maximo;
    }

}
