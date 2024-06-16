package olapcube.acciones;

import java.util.Arrays;
import java.util.Scanner;

import olapcube.Proyeccion;
import olapcube.estructura.Cubo;
import olapcube.estructura.Dimension;

/**
 * Clase que representa la acción de Slice, extiende a la clase Accion
 */
public class Slice extends Accion{

    /**
     * Constructor de la clase
     * @param cubo Cubo sobre el que se aplicará la acción
     * @param nombreDim Dimensión seleccionada para realizar la proyección
     * @param valor Valor de la dimensión seleccionada
     */
    public static Cubo slice(Cubo cuboOriginal, String nombreDim, String valor) {
        Cubo cubo = cuboOriginal.copiar();
        cubo.dimensiones.get(nombreDim).filtrar(valor);
        return cubo;
    }

    @Override
    public void ejecutar(Cubo cubo, Proyeccion proyeccion) {
            try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese la dimensión para realizar el Slice:");
            System.out.println("1. POS, 2. Fechas, 3. Productos");
            int dimSlice = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Ingrese el valor específico para la dimensión " + dimensiones[dimSlice - 1] + ":");
            Dimension dimSeleccionada = cubo.getDimension(dimensiones[dimSlice - 1]);
            if (dimSeleccionada != null) {
                System.out.println("Valores únicos de la dimensión " + dimensiones[dimSlice - 1] + ": " + Arrays.toString(dimSeleccionada.getValores()));
                
            } else {
                System.out.println("Valor no válido");
                return;
            }

            String valor = scanner.nextLine();

            System.out.println("Dimensión secundaria (1. POS, 2. Fechas, 3. Productos):");
            int dim2 = scanner.nextInt();
            scanner.nextLine();

            

            Cubo cuboSlice = Slice.slice(cubo, dimensiones[dimSlice - 1], valor);

            if (dimSlice < 1 || dimSlice > 3 || dim2 < 1 || dim2 > 3 || dimSlice == dim2) {
                System.out.println("Las dimensiones seleccionadas no son válidas");
                return;
            }
            proyeccion.seleccionarHecho();
            cuboSlice.proyectar().print(dimensiones[dimSlice - 1], dimensiones[dim2 - 1]);
        } catch (Exception e) {
            System.out.println("Error al realizar Slice");
        }
    }
}
    

