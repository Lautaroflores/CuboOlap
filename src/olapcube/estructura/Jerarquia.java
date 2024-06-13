package olapcube.estructura;


import java.util.Scanner;

public class Jerarquia {

    public int seleccionarJerarquia(String dimension) {
        Scanner scanner = new Scanner(System.in);
        int jerarquiaSeleccionada = -1;
    
        switch (dimension) {
            case "POS":
                System.out.println("Seleccione una opción:\n1. Región\n2. País\n3. Provincia\n4. Ciudad\n5. Punto de venta");
                break;
            case "TIEMPO":
                System.out.println("Seleccione una opción:\n1. Año\n2. Quarter\n3. Mes\n4. Fecha");
                break;
            case "PRODUCTO":
                System.out.println("Seleccione una opción:\n1. Categoría\n2. Subcategoría\n3. Producto");
                break;
        }
    
        int opcion = scanner.nextInt();
        scanner.nextLine();
    
        switch (dimension) {
            case "POS":
            
                jerarquiaSeleccionada =opcion - 1;
                break;
            case "TIEMPO":
            
                jerarquiaSeleccionada = opcion - 1;
                break;
            case "PRODUCTO":

                jerarquiaSeleccionada = opcion - 1;
                break;
        }
        scanner.close();
    
        return jerarquiaSeleccionada;

    }
}