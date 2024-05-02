/* Necesito crear un cubo olap. Esta clase va a leer los csv
 * fechas, productos, puntos_venta y la fact table 'ventas'
 * y los va a procesar para seleccionar los datos que necesito 
 * en el orden jerarquico que necesite 
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Comentame cada bloque de codigo para que sepas que hace cada cosa

//Clase que lee los csv

public class leerCsv {
   
    static String fechas = "fechas.csv";
    static String productos = "productos.csv";
    static String ventas = "ventas.csv";
    static String puntos_venta = "puntos_venta.csv";


    //Lee el csv y lo guarda en una lista

    private static List<String[]> readCSV(String fileName) {


        List<String[]> data = new ArrayList<>();
        try {
            File file = new File(fileName);
            //El scanner lee el archivo y lo guarda en la lista data
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); //Lee la linea
                String[] row = line.split(","); //Separa los datos por comas
                if (data.size() < 5) //Veo solamente las primeras 5 filas
                data.add(row);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace(); //Imprime el error
        }
        return data;
    }

    private static void printData(List<String[]> data) { //Imprime los datos
        for (String[] row : data) { 
            for (String cell : row) { 
                System.out.print(cell + "\t"); 
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        List<String[]> dimensionData1 = readCSV(fechas);
        List<String[]> dimensionData2 = readCSV(productos);
        List<String[]> dimensionData3 = readCSV(puntos_venta);
        List<String[]> factData = readCSV(ventas);
    
        // Aquí puedes manipular los datos según sea necesario, como crear tablas en una base de datos o realizar operaciones con los datos.
    
        // Por ejemplo, podrías imprimir los datos para verificar si se leyeron correctamente:
        System.out.println("Dimension 1 Data:");
        printData(dimensionData1);
    
        System.out.println("Dimension 2 Data:");
        printData(dimensionData2);
    
        System.out.println("Dimension 3 Data:");
        printData(dimensionData3); 
    
        System.out.println("Fact Data:");
        printData(factData); 
    }
 
}
