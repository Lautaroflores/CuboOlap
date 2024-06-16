package olapcube.metricas;

import java.util.List;

/**
 * Clase que representa la medida de suma de una lista de valores. (Extiende a Medida)
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
