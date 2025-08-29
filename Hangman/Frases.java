import java.io.*;
import java.util.*;

public class Frases
{
    public static List<String> cargarFrases(String rutaArchivo)
    {
        List<String> frases = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                frases.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return frases;
    }
}
