package olapcube.configuration;

/**
 * Clase que representa la configuración de un cubo
 */
public class ConfigCubo {
    private String nombre;                  // Nombre del cubo
    private ConfigDimension[] dimensiones;  // Configuración de las dimensiones
    private ConfigHechos hechos;            // Configuración de los hechos

    /**
     * Constructor de la clase
     * 
     * @param nombre Nombre del cubo
     * @param hechos Configuración de los hechos
     * @param dimensiones Configuración de las dimensiones
     */
    public ConfigCubo(String nombre, ConfigHechos hechos, ConfigDimension[] dimensiones) {
        this.nombre = nombre;
        this.dimensiones = dimensiones;
        this.hechos = hechos;
    }

    public ConfigDimension[] getDimensiones() {
        return dimensiones;
    }

    public ConfigHechos getHechos() {
        return hechos;
    }

    public String getNombre() {
        return nombre;
    }
}
