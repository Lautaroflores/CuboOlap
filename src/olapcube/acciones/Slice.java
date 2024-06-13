package olapcube.acciones;

import olapcube.estructura.Cubo;

public class Slice {

    public static Cubo slice(Cubo cuboOriginal, String nombreDim, String valor) {
        Cubo cubo = cuboOriginal.copiar();
        cubo.dimensiones.get(nombreDim).filtrar(valor);
        return cubo;
    }
}
