import java.util.List;

/*public class Main {
    public static void main(String[] args) {
        // Leer datos de los CSVs
        List<String[]> fechasData = leerCsv.readCSV(leerCsv.fechas);
        List<String[]> productosData = leerCsv.readCSV(leerCsv.productos);
        List<String[]> puntosVentaData = leerCsv.readCSV(leerCsv.puntosVenta);
        List<String[]> ventasData = leerCsv.readCSV(leerCsv.ventas);

        // Imprimir la cantidad de filas y columnas
        printFileStats("Fechas", fechasData);
        printFileStats("Productos", productosData);
        printFileStats("Puntos de Venta", puntosVentaData);
        printFileStats("Ventas", ventasData);

        // Crear objetos Dimension
        Dimension fechasDimension = new Dimension(fechasData);
        Dimension productosDimension = new Dimension(productosData);
        Dimension puntosVentaDimension = new Dimension(puntosVentaData);

        // Crear objeto Hechos
        Hechos ventasFact = new Hechos(ventasData);

        // Crear objeto CuboOlap
        CuboOlap cuboOlap = new CuboOlap();
        cuboOlap.addDimension(fechasDimension);
        cuboOlap.addDimension(productosDimension);
        cuboOlap.addDimension(puntosVentaDimension);
        cuboOlap.setHechos(ventasFact); // Llamar al método setHechos

        // Imprimir el contenido del cubo OLAP
        System.out.println("Cubo OLAP creado con éxito.");
    }

    private static void printFileStats(String fileName, List<String[]> data) {
        if (data.isEmpty()) {
            System.out.println(fileName + " está vacío o no se pudo leer.");
            return;
        }

        int rowCount = data.size();
        int columnCount = data.get(0).length;

        System.out.println(fileName + ":");
        System.out.println("Cantidad de filas: " + rowCount);
        System.out.println("Cantidad de columnas: " + columnCount);
        System.out.println();
    }
}
*/
public class Main extends LeerCsv{
    public static void main(String[] args) {
        List<String[]> fechasData = LeerCsv.readCSV(LeerCsv.fechas);
        List<String[]> productosData = LeerCsv.readCSV(LeerCsv.productos);
        List<String[]> puntosVentaData = LeerCsv.readCSV(LeerCsv.puntosVenta);
        List<String[]> hechosData = LeerCsv.readCSV(LeerCsv.ventas);

        System.out.println("Cantidad de filas y columnas de cada CSV:");
        System.out.println("Fechas: " + fechasData.size() + " filas, " + (fechasData.isEmpty() ? 0 : fechasData.get(0).length) + " columnas");
        System.out.println("Productos: " + productosData.size() + " filas, " + (productosData.isEmpty() ? 0 : productosData.get(0).length) + " columnas");
        System.out.println("Puntos de Venta: " + puntosVentaData.size() + " filas, " + (puntosVentaData.isEmpty() ? 0 : puntosVentaData.get(0).length) + " columnas");
        System.out.println("Hechos: " + hechosData.size() + " filas, " + (hechosData.isEmpty() ? 0 : hechosData.get(0).length) + " columnas");

        Dimension fechas = new Dimension(fechasData);
        Dimension productos = new Dimension(productosData);
        Dimension puntosVenta = new Dimension(puntosVentaData);
        Hechos hechos = new Hechos(hechosData);

        CuboOlap cuboOlap = new CuboOlap(fechas, productos, puntosVenta, hechos);
        SuperTabla superTable = new SuperTabla(fechasData, productosData, puntosVentaData, hechosData);

        System.out.println("Datos combinados en la Super Tabla:");
        /*superTable.printData();*/
    }
}