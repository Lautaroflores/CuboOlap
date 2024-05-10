/* Esta clase va a leer los csv
 * fechas, productos, puntos_venta y la fact table 'ventas'.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Clase que lee los csv

public class leerCsv {
   
    static String fechas = "src/archivos/fechas.csv";
    static String productos = "src/archivos/productos.csv";
    static String ventas = "src/archivos/ventas.csv";
    static String puntos_venta = "src/archivos/puntos_venta.csv";


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
    
        //Chequeo como salió todo
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
