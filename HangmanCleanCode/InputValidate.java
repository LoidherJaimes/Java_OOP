/**
 * Valida las entradas del usuario.
 * Responsabilidad: Verificar que las entradas sean válidas.
 */
public class InputValidate {
    
    /**
     * Verifica si la entrada no está vacía.
     */
    public static boolean esEntradaValida(String entrada) {
        return entrada != null && !entrada.isEmpty();
    }
    
    /**
     * Verifica si la entrada es una sola letra.
     */
    public static boolean esUnaLetra(String entrada) {
        return entrada != null && entrada.length() == 1;
    }
    
    /**
     * Verifica si la entrada es una frase (más de una letra).
     */
    public static boolean esUnaFrase(String entrada) {
        return entrada != null && entrada.length() > 1;
    }
}