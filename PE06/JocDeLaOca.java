import java.util.Scanner;

public class JocDeLaOca {
    
    public final Scanner scanner = new Scanner(System.in);
    public final int NUM_CASELLES = 63;
    public final int MAX_JUGADORS = 4;
    public final int MIN_JUGADORS = 2;
    public String[] JUGADORS;
    public static void main(String[] args) {
        JocDeLaOca joc = new JocDeLaOca();
        joc.iniciarJoc();
    }

    public void iniciarJoc() {
        JUGADORS = demanarJugadors();

    }

    public int demanarInt(String missatge, int min, int max) {
        
        while (true) {
            System.out.print(missatge + " (" + min + " - " + max + "): ");
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.println("ERROR: El valor ha d'estar entre " + min + " i " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Entrada no vàlida.");
            }
        }
    }

    public String demanarString(String missatge) {
        System.out.print(missatge + ": ");
        return scanner.nextLine();
    }

    public String[] demanarJugadors() {
        int numJugadors = demanarInt("Introdueix el nombre de jugadors", MIN_JUGADORS, MAX_JUGADORS);
        String[] jugadors = new String[numJugadors];
        for (int i = 0; i < numJugadors; i++) {
            jugadors[i] = demanarString("Introdueix el nom del jugador " + (i + 1));
        }
        return jugadors;
    }

}