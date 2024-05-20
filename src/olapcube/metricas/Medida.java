package olapcube.metricas;

import java.util.List;

/**
 * Clase abstracta que representa una medida a calcular en conjunto de datos
 */
public abstract class Medida {
    private String nombre;

    public Medida(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Calcula la medida a partir de una lista de valores
     * 
     * @param valores Lista de valores
     * @return Medida calculada
     */
    public abstract double calcular(List<Double> valores);

}
