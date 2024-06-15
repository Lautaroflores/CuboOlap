package olapcube.acciones;

import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;


public class SeleccionarDimension {
    private Cubo cubo;
    private Proyeccion proyeccion;
    Scanner scanner = new Scanner(System.in);
    
    public SeleccionarDimension(Cubo cubo, Proyeccion proyeccion) {
        this.cubo = cubo;
        this.proyeccion = proyeccion;
        
        }
        // Método para seleccionar una sola dimension
    public void ejecutar(String dimensionUnica) {
            
        Dimension dim = cubo.getDimension(dimensionUnica);
        if (dim == null) {
            System.out.println("Dimensión no válida");
            return;
        }

    
    }
    //Metodo para la proyección en 2D
    public void ejecutar(String dimensionPrincipal, String dimensionSecundaria) {
        Dimension dimPrincipal = cubo.getDimension(dimensionPrincipal);
        Dimension dimSecundaria = cubo.getDimension(dimensionSecundaria);

        if (dimPrincipal == null || dimSecundaria == null) {
            System.out.println("Dimensiones no válidas");
            return;
        }

    
    }
}