/**
 * Representa una partida individual del juego del Ahorcado.
 * Responsabilidad: Manejar el estado y lógica de una partida.
 */
public class PartidaHangman {
    private static final int VIDAS_INICIALES = 10;
    private static final char CARACTER_OCULTO = '_';
    
    private final String fraseSecreta;
    private final StringBuilder fraseActual;
    private final StringBuilder letrasUtilizadas;
    private final StringBuilder intentosArriesgados;
    private int vidasRestantes;
    
    public PartidaHangman(String fraseSecreta) {
        this.fraseSecreta = fraseSecreta;
        this.fraseActual = inicializarFraseOculta(fraseSecreta);
        this.letrasUtilizadas = new StringBuilder();
        this.intentosArriesgados = new StringBuilder();
        this.vidasRestantes = VIDAS_INICIALES;
    }
    
    private StringBuilder inicializarFraseOculta(String frase) {
        StringBuilder oculta = new StringBuilder();
        
        for (int i = 0; i < frase.length(); i++) {
            char caracter = frase.charAt(i);
            
            if (caracter == ' ') {
                oculta.append(' ');
            } else {
                oculta.append(CARACTER_OCULTO);
            }
        }
        
        return oculta;
    }
    
    public boolean intentarLetra(char letra) {
        agregarLetraUtilizada(letra);
        boolean acierto = revelarLetraEnFrase(letra);
        
        if (!acierto) {
            perderVida();
        }
        
        return acierto;
    }
    
    private void agregarLetraUtilizada(char letra) {
        letrasUtilizadas.append(Character.toLowerCase(letra));
    }
    
    private boolean revelarLetraEnFrase(char letra) {
        boolean seEncontro = false;
        
        for (int posicion = 0; posicion < fraseSecreta.length(); posicion++) {
            if (Character.toUpperCase(fraseSecreta.charAt(posicion)) == letra) {
                fraseActual.setCharAt(posicion, fraseSecreta.charAt(posicion));
                seEncontro = true;
            }
        }
        
        return seEncontro;
    }
    
    public boolean intentarFrase(String intento) {
        agregarIntentoArriesgado(intento);
        boolean acierto = revelarFraseEnSecreta(intento);
        
        if (!acierto) {
            perderVida();
        }
        
        return acierto;
    }
    
    private void agregarIntentoArriesgado(String intento) {
        intentosArriesgados.append("\"").append(intento).append("\", ");
    }
    
    private boolean revelarFraseEnSecreta(String intento) {
        boolean seEncontro = false;
        int posicionOcurrencia = fraseSecreta.toUpperCase().indexOf(intento.toUpperCase());
        
        while (posicionOcurrencia != -1) {
            revelarSubcadena(posicionOcurrencia, intento.length());
            seEncontro = true;
            posicionOcurrencia = fraseSecreta.toUpperCase()
                .indexOf(intento.toUpperCase(), posicionOcurrencia + 1);
        }
        
        return seEncontro;
    }
    
    private void revelarSubcadena(int inicio, int longitud) {
        for (int offset = 0; offset < longitud; offset++) {
            int posicion = inicio + offset;
            fraseActual.setCharAt(posicion, fraseSecreta.charAt(posicion));
        }
    }
    
    private void perderVida() {
        vidasRestantes--;
    }
    
    public boolean yaSeIntentoLaLetra(char letra) {
        String letraMinuscula = Character.toString(letra).toLowerCase();
        return letrasUtilizadas.indexOf(letraMinuscula) != -1;
    }
    
    public boolean estaEnCurso() {
        return tieneVidasRestantes() && !estaResuelta();
    }
    
    private boolean tieneVidasRestantes() {
        return vidasRestantes > 0;
    }
    
    private boolean estaResuelta() {
        return fraseActual.indexOf(String.valueOf(CARACTER_OCULTO)) == -1;
    }
    
    public boolean haGanado() {
        return estaResuelta();
    }
    
    // Getters para la interfaz
    public String getFraseActual() {
        return fraseActual.toString();
    }
    
    public int getVidasRestantes() {
        return vidasRestantes;
    }
    
    public String getLetrasUtilizadas() {
        return letrasUtilizadas.toString();
    }
    
    public String getIntentosArriesgados() {
        return intentosArriesgados.toString();
    }
    
    public String getFraseSecreta() {
        return fraseSecreta;
    }
}