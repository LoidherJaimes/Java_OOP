/**
 * Maneja las estadísticas del juego.
 * Responsabilidad: Llevar el conteo de partidas jugadas y ganadas.
 */
public class GameStats {
    private int partidasJugadas;
    private int partidasGanadas;
    
    public GameStats() {
        this.partidasJugadas = 0;
        this.partidasGanadas = 0;
    }
    
    public void incrementarPartidasJugadas() {
        partidasJugadas++;
    }
    
    public void incrementarPartidasGanadas() {
        partidasGanadas++;
    }
    
    public boolean esLaPrimeraPartida() {
        return partidasJugadas == 0;
    }
    
    public int getPartidasJugadas() {
        return partidasJugadas;
    }
    
    public int getPartidasGanadas() {
        return partidasGanadas;
    }
}