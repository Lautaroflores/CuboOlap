package olapcube.acciones;

import java.util.Arrays;
import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;

/**
 * Clase que representa la acción de Dice, extiende a la clase Accion
 */

public class Dice extends Accion{
    
    private String[] dimensiones = {"POS", "Fechas", "Productos"}; // Dimensiones válidas para realizar el Dice

    /**
    * @param dimensiones Arreglo de dimensiones válidas para realizar el Dice
    */
    public Cubo dice (Cubo cuboOriginal, String nombreDim, String[] valores) {

        
        Cubo cubo = cuboOriginal.copiar();
        
        cubo.dimensiones.get(nombreDim).filtrar(valores);       
 
        return cubo;
     }

     public Cubo dice (Cubo cuboOriginal,String nombreDim1, String[] valores1, String nombreDim2, String[] valores2) {
        
        Cubo cubo = cuboOriginal.copiar();

       
        cubo.dimensiones.get(nombreDim1).filtrar(valores1);      
        cubo.dimensiones.get(nombreDim2).filtrar(valores2); 

        return cubo;
    }
    public Cubo dice (Cubo cuboOriginal,String nombreDim1, String[] valores1, String nombreDim2, String[] valores2, String nombreDim3, String[] valores3) {
        
        Cubo cubo = cuboOriginal.copiar();
       
        cubo.dimensiones.get(nombreDim1).filtrar(valores1);   
        cubo.dimensiones.get(nombreDim2).filtrar(valores2);
        cubo.dimensiones.get(nombreDim3).filtrar(valores3);    

        return cubo;
    }
    @Override
    public void ejecutar(Cubo cubo, Proyeccion proyeccion) {
            try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese la dimensión para realizar el Dice:");
            System.out.println("1. POS, 2. Fechas, 3. Productos");
            int dimDice = scanner.nextInt();
            scanner.nextLine();
            Dimension dimSeleccionada = cubo.getDimension(dimensiones[dimDice - 1]);
            
            System.out.println("Ingrese los valores específicos para la dimensión " + dimensiones[dimDice - 1] + " separados por comas:");
            if (dimSeleccionada != null) {
                System.out.println("Valores únicos de la dimensión " + dimensiones[dimDice - 1] + ": " + Arrays.toString(dimSeleccionada.getValores()));
            } else {
                System.out.println("Dimension no válida");
                scanner.close();
                return;
            }
        
            String valores = scanner.nextLine();
            String[] valoresSeleccionados = valores.split(",");
            
            boolean valoresValidos = Arrays.stream(valoresSeleccionados).allMatch(valor -> Arrays.asList(dimSeleccionada.getValores()).contains(valor.trim()));
        
            if (!valoresValidos) {
                System.out.println("Alguno de los valores ingresados no es válido.");
                scanner.close();
                return;
            }
            
            cubo.filtrarDimension(dimensiones[dimDice - 1], valoresSeleccionados);
        
            System.out.println("Dimensión secundaria (1. POS, 2. Fechas, 3. Productos):");
            int dim2 = scanner.nextInt();
            scanner.nextLine();
        
            System.out.println("Ingrese los valores específicos para la dimensión " + dimensiones[dim2 - 1] + " separados por comas:");
            Dimension dimSeleccionada2 = cubo.getDimension(dimensiones[dim2 - 1]);
            if (dimSeleccionada2 != null) {
                System.out.println("Valores únicos de la dimensión " + dimensiones[dim2 - 1] + ": " + Arrays.toString(dimSeleccionada2.getValores()));
            } else {
                System.out.println("Dimension no válida");
                scanner.close();
                return;
            }
        
            String valores2 = scanner.nextLine();
            String[] valoresSeleccionados2 = valores2.split(",");
        
            boolean valoresValidos2 = Arrays.stream(valoresSeleccionados2).allMatch(valor -> Arrays.asList(dimSeleccionada2.getValores()).contains(valor.trim()));
        
            if (!valoresValidos2) {
                System.out.println("Alguno de los valores ingresados no es válido.");
                scanner.close();
                return;
            }
            proyeccion.seleccionarHecho();
            cubo.filtrarDimension(dimensiones[dim2 - 1], valoresSeleccionados2);
            cubo.proyectar().print(dimensiones[dimDice - 1], dimensiones[dim2 - 1]);
        
        } catch (Exception e) {
            System.out.println("Error al realizar Dice");
        }
    }
}
