import java.util.*;

public class App
{
    public static void main(String[] args)
    {
        List<String> frases = Frases.cargarFrases("frases.txt");
        Random random = new Random();
        
        Scanner sc = new Scanner(System.in);
        
        boolean continuar = true;
        int numeroJuego = 0, numeroVictorias = 0;
        
        while(continuar)
        {
            int opcion, vidas = 10;
            
            System.out.println("==== Juego del Ahorcado ====");
            System.out.println("Partidas jugadas: " + numeroJuego);
            System.out.println("Partidas ganadas: " + numeroVictorias);
            
            System.out.println("\n¿Qué deseas hacer?");
            
            if(numeroJuego == 0)
            {
                System.out.println("1) Jugar.\n2) Salir.");
            } else {
                System.out.println("1) Continuar jugando.\n2) Salir.");
            }
            
            opcion = sc.nextInt();
            sc.nextLine();
            
            if(opcion != 1){
                continuar = false;
                continue;
            }
            
            if(frases.isEmpty())
            {
                System.out.println("No se encontraron frases disponibles.");
                return;
            }
            
            String fraseElegida = frases.get(random.nextInt(frases.size()));
            
            StringBuilder fraseOculta = new StringBuilder();
            StringBuilder letrasUtilizadas = new StringBuilder();
            StringBuilder intentosArriesgados = new StringBuilder();
            
            for(int i = 0; i < fraseElegida.length(); i++)
            {
                char c = fraseElegida.charAt(i);
                
                if (c == ' ')
                {
                    fraseOculta.append(" ");
                } else {
                    fraseOculta.append("_");
                }
            }
            
            while ((vidas > 0) && (fraseOculta.indexOf("_") != -1))
            {
                System.out.println("\nFrase: " + fraseOculta);
                System.out.println("\nVidas restantes: " + vidas);
                System.out.println("\nLetras utilizadas: " + letrasUtilizadas);
                System.out.println("\nIntentos Arriesgados: " + intentosArriesgados);
                System.out.println("\nIngrese una letra, palabra(s) o frase: ");
                
                String intento = sc.nextLine().trim().toUpperCase();
                
                if(intento.isEmpty())
                {
                    System.out.println("No se ingresó nada. Intente de nuevo.");
                    continue;
                }
                
                char letraIngresada = intento.charAt(0);
                boolean acierto = false;
                
                if (intento.length() == 1)
                {
                    if (letrasUtilizadas.indexOf(Character.toString(letraIngresada).toLowerCase()) != -1)
                    {
                        System.out.println("Ya intentaste con " + Character.toString(letraIngresada).toLowerCase() + ". Prueba otra letra.");
                        continue;
                    }
                    
                    letrasUtilizadas.append(Character.toLowerCase(letraIngresada));
                    
                    for(int j = 0; j < fraseElegida.length(); j++)
                    {
                        if(Character.toUpperCase(fraseElegida.charAt(j)) == letraIngresada)
                        {
                            fraseOculta.setCharAt(j, fraseElegida.charAt(j));
                            acierto = true;
                        }
                    }
                    
                    if(acierto)
                    {
                        System.out.println("¡La letra " + letraIngresada + " se encuentra en la frase!");
                    } else {
                        vidas--;
                        System.out.println("La letra " + letraIngresada + " no se encuentra en la frase.");
                    }
                } else {
                    int posicionOcurrencia = fraseElegida.toUpperCase().indexOf(intento.toUpperCase());
                    
                    while(posicionOcurrencia != -1) {
                        for (int j = 0; j < intento.length(); j++) {
                            fraseOculta.setCharAt(posicionOcurrencia + j, fraseElegida.charAt(posicionOcurrencia + j));
                        }
                        acierto = true;
                        
                        posicionOcurrencia = fraseElegida.toUpperCase().indexOf(intento.toUpperCase(), posicionOcurrencia + 1);
                    }
                    
                    intentosArriesgados.append("\"").append(intento).append("\", ");
                    
                    if(acierto)
                    {
                        System.out.println("¡La frase \"" + intento + "\" se encuentra en la frase!");
                    } else {
                        vidas--;
                        System.out.println("La frase \"" + intento + "\" no se encuentra en la frase.");
                    }
                }
            }
            
            if(fraseOculta.indexOf("_") == -1)
            {
                System.out.println("\n¡Felicidades, ganaste!");
                System.out.println("La frase era: \"" + fraseElegida + "\".\n");
                numeroVictorias++;
            } else {
                System.out.println("\nSe acabaron los intentos.");
                System.out.println("¡Mas suerte la proxima!");
                System.out.println("\nLa frase era: \"" + fraseElegida + "\".\n");
            }
            
            numeroJuego++;
        }
        
        sc.close();
    }
}
