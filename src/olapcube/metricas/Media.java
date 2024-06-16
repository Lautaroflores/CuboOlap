package olapcube.metricas;

import java.util.List;

/**
 * Clase abstracta que calcula la media de una lista de valores. (Extiende a Medida)
 */

public class Media extends Medida {

    public Media () {
        super("Media");
    }

    @Override
    public double calcular(List<Double> valores) {
        double suma_media = 0;
        for (double valor : valores) {
            suma_media += valor;
        }
        return suma_media / valores.size();
    }
}
