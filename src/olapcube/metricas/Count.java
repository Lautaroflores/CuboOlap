package olapcube.metricas;
import java.util.List;


public class Count extends Medida{

    public Count() {
        super("Count");
    }

    @Override
    public double calcular(List<Double> valores) {
        return valores.size();
    }

}
