import java.util.Scanner;

public class JocDeLaOca {
    
    public final Scanner scanner = new Scanner(System.in);
    public final int MAX_JUGADORS = 4;
    public final int MIN_JUGADORS = 2;
    public String[] tauler;
    public String[] nomsJugadors;
    public int[] posicions;
    public int[] tornsPenalitzacio;
    public String[] motiuPenalitzacio;
    public static void main(String[] args) {
        JocDeLaOca joc = new JocDeLaOca();
        joc.iniciarJoc();
    }

    public void iniciarJoc() {
        inicialitzarJoc();
        jugar();
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

    public void inicialitzarJoc() {
        tauler = inicialitzarTauler();
        nomsJugadors = demanarJugadors();
        int numJugadors = nomsJugadors.length;
        posicions = new int[numJugadors];
        tornsPenalitzacio = new int[numJugadors];
        motiuPenalitzacio = new String[numJugadors];
        for (int i = 0; i < numJugadors; i++) {
            posicions[i] = 0;
            tornsPenalitzacio[i] = 0;
            motiuPenalitzacio[i] = "";
        }
        nomsJugadors = organitzarOrdreJugadors();
        System.out.println("Benvinguts al Joc de la Oca; " + String.join(", ", nomsJugadors) + "!\nComenceu a la casella 0 (Inici)");
    }

    public String[] inicialitzarTauler() {
        tauler = new String[64];
        for (int i = 0; i < tauler.length; i++) {
            switch (i) {
                case 0:
                    tauler[i] = "Inici";
                    break;
                case 5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59:
                    tauler[i] = "Oca";
                    break;
                case 6, 12:
                    tauler[i] = "Pont";
                    break;
                case 19:
                    tauler[i] = "Fonda";
                    break;
                case 26:
                    tauler[i] = "Daus 3-6";
                    break;
                case 31:
                    tauler[i] = "Pou";
                    break;
                case 42:
                    tauler[i] = "Laberint";
                    break;
                case 52:
                    tauler[i] = "Presó";
                    break;
                case 53:
                    tauler[i] = "Daus 4-5";
                    break;
                case 58:
                    tauler[i] = "La mort";
                    break;
                case 63:
                    tauler[i] = "Jardí de la oca";
                    break;
                default:
                    tauler[i] = "";
                    break;
            }
        }

        return tauler;
    }

    public String[] demanarJugadors() {
        int numJugadors = demanarInt("Introdueix el nombre de jugadors", MIN_JUGADORS, MAX_JUGADORS);
        String[] jugadors = new String[numJugadors];
        for (int i = 0; i < numJugadors; i++) {
            jugadors[i] = demanarString("Introdueix el nom del jugador " + (i + 1));
        }
        return jugadors;
    }

    public String[] organitzarOrdreJugadors() {
        String[] jugadorsOrdenats = nomsJugadors.clone();

        
        return jugadorsOrdenats;
    }

    public void jugar() {
        boolean joc = true;
        int jugadorActual = 0;
        int torn = 1;

        while (joc){
            System.out.println("\n--- Torn " + torn + " ---");
            System.out.println("Ës el torn del jugador " + (jugadorActual + 1) + ", " + nomsJugadors[jugadorActual] + ".");

            // Comprovar penalitzacions
            if (tornsPenalitzacio[jugadorActual] > 0) {
                System.out.println(nomsJugadors[jugadorActual] + " esta a la " + motiuPenalitzacio[jugadorActual] + ". Torns restants: " + tornsPenalitzacio[jugadorActual]);
                tornsPenalitzacio[jugadorActual]--;
            
                if (tornsPenalitzacio[jugadorActual] == 0) {
                    System.out.println(nomsJugadors[jugadorActual] + " surt de la " + motiuPenalitzacio[jugadorActual]);
                    motiuPenalitzacio[jugadorActual] = "";
                }
            
                // Passar al següent jugador
                jugadorActual = (jugadorActual + 1) % nomsJugadors.length;
                torn++;
                continue;
            }

            // Demanar tiro
            System.out.print(">> ");
            String entrada = scanner.nextLine();
        
            if (!entrada.equalsIgnoreCase("tiro")) {
                System.out.println("Has d'escriure 'tiro' per tirar els daus!");
                continue;
            }

            // Tirar daus
            int[] daus = tirarDaus(jugadorActual);
            int sumaDaus = daus[0] + daus[1];
        
            // Mostrar resultat
            if (daus[1] == 0) {
                System.out.println("Has obtingut un " + daus[0] + " = " + sumaDaus + ".");
            } else {
                System.out.println("Has obtingut un " + daus[0] + " i " + daus[1] + " = " + sumaDaus + ".");
            }

            // Moure jugador
            moureJugador(jugadorActual, sumaDaus);

            // Comprovar si ha guanyat
            if (posicions[jugadorActual] == 63) {
                System.out.println(nomsJugadors[jugadorActual] + " HA GUANYAT!");
                joc = false;
                break;
            }

            // Passar al següent jugador
            jugadorActual = (jugadorActual + 1) % nomsJugadors.length;
            torn++;
        }
        
    }

    public int tirarDau() {
        return (int)(Math.random() * 6) + 1;
    }

    public int[] tirarDaus(int jugador) {
        if (posicions[jugador] >= 60) {
            int dau = tirarDau();
            return new int[]{dau, 0};
        } else {
            int dau1 = tirarDau();
            int dau2 = tirarDau();
            return new int[]{dau1, dau2};
        }
    }

    public void moureJugador(int jugador, int passos) {
        int posicioAntiga = posicions[jugador];
        int novaPosicio = posicioAntiga + passos;
    
        // Comprovar rebot
        if (novaPosicio > 63) {
            int excés = novaPosicio - 63;
            novaPosicio = 63 - excés;
            System.out.println("Passes de la casella 63. Rebot a la casella " + novaPosicio + ".");
        }
    
        posicions[jugador] = novaPosicio;
        System.out.println("Avances fins la casella " + novaPosicio + ".");
    
        // Processar casella especial
        //procesarCasellaEspecial(jugador);
    }
}