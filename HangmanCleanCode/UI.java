import java.util.Scanner;

/**
 * Maneja toda la interacción con el usuario.
 * Responsabilidad: Entrada y salida del juego.
 */
public class UI {
    private final Scanner scanner;
    
    public UI() {
        this.scanner = new Scanner(System.in);
    }
    
    public void mostrarMenuPrincipal(GameStats estadisticas) {
        System.out.println("==== Juego del Ahorcado ====");
        System.out.println("Partidas jugadas: " + estadisticas.getPartidasJugadas());
        System.out.println("Partidas ganadas: " + estadisticas.getPartidasGanadas());
        
        System.out.println("\n¿Qué deseas hacer?");
        
        if (estadisticas.esLaPrimeraPartida()) {
            System.out.println("1) Jugar.\n2) Salir.");
        } else {
            System.out.println("1) Continuar jugando.\n2) Salir.");
        }
    }
    
    public int leerOpcionMenu() {
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }
    
    public void mostrarEstadoPartida(PartidaHangman partida) {
        System.out.println("\nFrase: " + partida.getFraseActual());
        System.out.println("\nVidas restantes: " + partida.getVidasRestantes());
        System.out.println("\nLetras utilizadas: " + partida.getLetrasUtilizadas());
        System.out.println("\nIntentos Arriesgados: " + partida.getIntentosArriesgados());
        System.out.println("\nIngrese una letra, palabra(s) o frase: ");
    }
    
    public String solicitarIntento() {
        return scanner.nextLine().trim().toUpperCase();
    }
    
    public void mostrarErrorEntradaVacia() {
        System.out.println("No se ingresó nada. Intente de nuevo.");
    }
    
    public void mostrarLetraYaUtilizada(char letra) {
        System.out.println("Ya intentaste con " + Character.toString(letra).toLowerCase() + 
            ". Prueba otra letra.");
    }
    
    public void mostrarResultadoLetra(char letra, boolean acierto) {
        if (acierto) {
            System.out.println("¡La letra " + letra + " se encuentra en la frase!");
        } else {
            System.out.println("La letra " + letra + " no se encuentra en la frase.");
        }
    }
    
    public void mostrarResultadoFrase(String frase, boolean acierto) {
        if (acierto) {
            System.out.println("¡La frase \"" + frase + "\" se encuentra en la frase!");
        } else {
            System.out.println("La frase \"" + frase + "\" no se encuentra en la frase.");
        }
    }
    
    public void mostrarVictoria(String fraseSecreta) {
        System.out.println("\n¡Felicidades, ganaste!");
        System.out.println("La frase era: \"" + fraseSecreta + "\".\n");
    }
    
    public void mostrarDerrota(String fraseSecreta) {
        System.out.println("\nSe acabaron los intentos.");
        System.out.println("¡Mas suerte la proxima!");
        System.out.println("\nLa frase era: \"" + fraseSecreta + "\".\n");
    }
    
    public void cerrar() {
        scanner.close();
    }
}