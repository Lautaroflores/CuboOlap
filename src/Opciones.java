import java.util.Scanner;


import olapcube.metricas.*;


public class Opciones {
    private Scanner scanner; // Declara un objeto Scanner como campo de la clase
    
    public Opciones(Scanner scanner) {
        this.scanner = scanner; // Inicializa el objeto Scanner con el que se pasa como argumento
       
    }

    public Medida seleccionarMedida() {
        System.out.println("Seleccione el número de la métrica a ejecutar:");
        System.out.println("1. Suma");
        System.out.println("2. Media");
        System.out.println("3. Count");
        System.out.println("4. Máximo");
        System.out.println("5. Mínimo");
        int opcion = scanner.nextInt(); // Se lee la opción ingresada por el usuario
        scanner.nextLine(); // Consumir la nueva línea después del entero

        // Selección de la medida
        switch (opcion) {
            case 1:
                return new Suma();
            case 2:
                return new Media();
            case 3:
                return new Count();
            case 4:
                return new Maximo();
            case 5:
                return new Minimo();
            default:
                return null; // Retornar null si la opción no es válida
        }
    }

  
}


