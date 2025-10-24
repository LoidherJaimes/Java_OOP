import java.util.List;
import java.util.Random;

/**
 * Orquestador del juego del Ahorcado.
 * Responsabilidad: Coordinar el flujo del juego entre partidas.
 */
public class Game {
    private final List<String> frasesDisponibles;
    private final Random random;
    private final UI interfaz;
    private final GameStats estadisticas;
    
    public Game(List<String> frases) {
        this.frasesDisponibles = frases;
        this.random = new Random();
        this.interfaz = new UI();
        this.estadisticas = new GameStats();
    }
    
    public void iniciar() {
        boolean continuar = true;
        
        while (continuar) {
            interfaz.mostrarMenuPrincipal(estadisticas);
            
            int opcion = interfaz.leerOpcionMenu();
            
            if (opcion != 1) {
                continuar = false;
                continue;
            }
            
            jugarPartida();
            estadisticas.incrementarPartidasJugadas();
        }
        
        interfaz.cerrar();
    }
    
    private void jugarPartida() {
        String fraseSecreta = seleccionarFraseAleatoria();
        PartidaHangman partida = new PartidaHangman(fraseSecreta);
        
        while (partida.estaEnCurso()) {
            interfaz.mostrarEstadoPartida(partida);
            String intento = interfaz.solicitarIntento();
            procesarIntento(partida, intento);
        }
        
        mostrarResultadoPartida(partida);
    }
    
    private String seleccionarFraseAleatoria() {
        int indiceAleatorio = random.nextInt(frasesDisponibles.size());
        return frasesDisponibles.get(indiceAleatorio);
    }
    
    private void procesarIntento(PartidaHangman partida, String intento) {
        if (!InputValidate.esEntradaValida(intento)) {
            interfaz.mostrarErrorEntradaVacia();
            return;
        }
        
        if (InputValidate.esUnaLetra(intento)) {
            procesarIntentoLetra(partida, intento);
        } else {
            procesarIntentoFrase(partida, intento);
        }
    }
    
    private void procesarIntentoLetra(PartidaHangman partida, String letra) {
        char letraChar = letra.charAt(0);
        
        if (partida.yaSeIntentoLaLetra(letraChar)) {
            interfaz.mostrarLetraYaUtilizada(letraChar);
            return;
        }
        
        boolean acierto = partida.intentarLetra(letraChar);
        interfaz.mostrarResultadoLetra(letraChar, acierto);
    }
    
    private void procesarIntentoFrase(PartidaHangman partida, String frase) {
        boolean acierto = partida.intentarFrase(frase);
        interfaz.mostrarResultadoFrase(frase, acierto);
    }
    
    private void mostrarResultadoPartida(PartidaHangman partida) {
        if (partida.haGanado()) {
            interfaz.mostrarVictoria(partida.getFraseSecreta());
            estadisticas.incrementarPartidasGanadas();
        } else {
            interfaz.mostrarDerrota(partida.getFraseSecreta());
        }
    }
}