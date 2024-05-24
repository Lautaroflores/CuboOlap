package olapcube.metricas;
import java.util.List;

import javax.print.attribute.standard.MediaSize.NA;

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
            else if (Double.valueOf(valor).equals()) {
                minimo = 0;
            }
        }
        return minimo;
    }
}
