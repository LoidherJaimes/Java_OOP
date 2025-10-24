import java.util.List;

/**
 * Punto de entrada de la aplicación del Juego del Ahorcado.
 * Responsabilidad: Inicializar y ejecutar el juego.
 */
public class App {
    public static void main(String[] args) {
        List<String> frases = Frases.cargarFrases("frases.txt");
        
        if (frases.isEmpty()) {
            System.out.println("No se encontraron frases disponibles.");
            return;
        }
        
        Game juego = new Game(frases);
        juego.iniciar();
    }
}