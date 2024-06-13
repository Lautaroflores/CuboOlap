package olapcube.acciones;

import olapcube.estructura.Cubo;

public class Dice {
    
 

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
}
