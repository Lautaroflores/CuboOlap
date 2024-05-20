import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperTabla {
    private List<String[]> data;

    public SuperTabla(List<String[]> fechas, List<String[]> productos, List<String[]> puntosVenta, List<String[]> hechos) {
        this.data = new ArrayList<>();
        combineData(fechas, productos, puntosVenta, hechos);
    }

    private void combineData(List<String[]> fechas, List<String[]> productos, List<String[]> puntosVenta, List<String[]> hechos) {
        Map<String, String[]> fechasMap = createLookupMap(fechas);
        Map<String, String[]> productosMap = createLookupMap(productos);
        Map<String, String[]> puntosVentaMap = createLookupMap(puntosVenta);

        for (String[] hecho : hechos) {
            String[] fecha = fechasMap.get(hecho[2]);
            String[] producto = productosMap.get(hecho[0]);
            String[] puntoVenta = puntosVentaMap.get(hecho[1]);

            if (fecha != null && producto != null && puntoVenta != null) {
                List<String> combinedRow = new ArrayList<>();
                addToRow(combinedRow, fecha);
                addToRow(combinedRow, producto);
                addToRow(combinedRow, puntoVenta);
                addToRow(combinedRow, hecho);

                data.add(combinedRow.toArray(new String[0]));
            }
        }
    }

    private Map<String, String[]> createLookupMap(List<String[]> data) {
        Map<String, String[]> lookupMap = new HashMap<>();
        for (String[] row : data) {
            lookupMap.put(row[0], row);
        }
        return lookupMap;
    }

    private void addToRow(List<String> row, String[] data) {
        for (String cell : data) {
            row.add(cell);
        }
    }

    public List<String[]> getData() {
        return data;
    }

    public void printData() {
        for (String[] row : data) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }
}