package olapcube.metricas;

import java.util.List;

/**
 * Clase abstracta que representa una medida a calcular en un conjunto de datos
 */
public abstract class Medida {
    private String nombre;

    public Medida(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    public abstract double calcular(List<Double> valores);

    

}
