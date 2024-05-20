package olapcube.metricas;

import java.util.List;

/**
 * Clase que representa una medida de suma
 */
public class Suma extends Medida {

    public Suma() {
        super("Suma");
    }

    @Override
    public double calcular(List<Double> valores) {
        double suma = 0;
        for (Double valor : valores) {
            suma += valor;
        }
        
        return suma;
    }
}
