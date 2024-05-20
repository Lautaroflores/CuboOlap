package olapcube.estructura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa una celda de un cubo OLAP.
 * Cada celda almacena valores para uno o más hechos.
 * A su vez, cada hecho puede tener uno o más valores. Por ejemplo, cuando se agrupan
 * varias celdas en una sola, se pueden tener varios valores para un mismo hecho.
 * 
 * Ejemplo de celda agrupada con dos hechos 'valor' y 'cantidad':
 
    | valor | cantidad |
    |-------|----------|
    |  10   |    5     |
    |  20   |    3     |
    |  10   |    2     |
    |  25   |    1     |

    En este caso el Map de la celda tendría la siguiente estructura:
    {
        "valor": [10, 20, 10, 25],
        "cantidad": [5, 3, 2, 1]
    }
 */
public class Celda {
    private Map<String, List<Double>> hechos;   // Mapeo de nombres de hecho a valores
    
    public Celda() {
        hechos = new HashMap<>();
    }

    public boolean esVacia() {
        return hechos.isEmpty();
    }
    
    @Override
    public String toString() {
        return "Celda [hechos=" + hechos.keySet() + "]";
    }

    /**
     * Obtiene los valores de un hecho
     * 
     * @param nombreHecho Nombre del hecho
     * @return Lista de valores del hecho
     */
    public List<Double> getValores(String nombreHecho) {
        if (!hechos.containsKey(nombreHecho)) {
            return new ArrayList<>();
        }
        return hechos.get(nombreHecho);
    }

    /**
     * Agrega un valor a un hecho
     * 
     * @param nombreHecho Nombre del hecho
     * @param valor Valor a agregar
     */
    public void agregarHecho(String nombreHecho, Double valor) {
        if (!hechos.containsKey(nombreHecho)) {
            hechos.put(nombreHecho, new ArrayList<>());
        }
        hechos.get(nombreHecho).add(valor);
    }

    /**
     * Agrupa varias celdas en una sola
     * 
     * @param celdas Lista de celdas a agrupar
     * @return Celda agrupada
     */
    public static Celda agrupar(List<Celda> celdas) {
        Celda nuevaCelda = new Celda();
        for (Celda celda : celdas) {
            for (String nombreHecho : celda.hechos.keySet()) {
                for (Double valor : celda.hechos.get(nombreHecho)) {
                    nuevaCelda.agregarHecho(nombreHecho, valor);
                }
            }
        }
        return nuevaCelda;
    }
    /**
     * Devuelve el mapa de hechos de la celda.
     * 
     * @return Mapa de hechos
     */
    public Map<String, List<Double>> getHechos() {
        return hechos;
    }

    /**
     * Comprueba si esta celda tiene los mismos hechos que otra celda.
     * 
     * @param otraCelda La otra celda con la que comparar
     * @return true si las dos celdas tienen los mismos hechos, false de lo contrario
     */
    public boolean mismosHechos(Celda otraCelda) {
        return this.hechos.keySet().equals(otraCelda.getHechos().keySet());
    }

}
