package olapcube.metricas;

import java.util.List;

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
