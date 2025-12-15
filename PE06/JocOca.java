import java.util.Scanner;

public class JocOca {
    
    public final Scanner scanner = new Scanner(System.in);
    public final int MAX_JUGADORS = 4;
    public final int MIN_JUGADORS = 2;
    public String[] tauler;
    public String[] nomsJugadors;
    public int[] posicions;
    public int[] tornsPenalitzacio;
    public String[] motiuPenalitzacio;
    public boolean joc = true;
    
    public static void main(String[] args) {
        JocOca joc = new JocOca();
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
        System.out.print(missatge);
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
        System.out.println("\nBenvinguts al Joc de la Oca; " + String.join(", ", nomsJugadors) + "!\nComenceu a la casella 0 (Inici)");
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
            jugadors[i] = demanarString("Introdueix el nom del jugador " + (i + 1) + ": ");
        }
        return jugadors;
    }

    public String[] organitzarOrdreJugadors() {
        String[] jugadorsOrdenats = nomsJugadors.clone();
        // falta implementar
        return jugadorsOrdenats;
    }

    public void jugar() {
        int jugadorActual = 0;
        int torn = 1;

        while (joc){
            imprimirTorn(torn, jugadorActual);

            boolean jugadorPotTirar = comprovarPenalitzacions(jugadorActual);
            if (!jugadorPotTirar) {
                jugadorActual = (jugadorActual + 1) % nomsJugadors.length;
                torn++;
                continue;
            }

            demanarTiro();
            if (!joc) {
                break;
            }

            int resultatDaus = dausJugador(jugadorActual);
            moureJugador(jugadorActual, resultatDaus);

            comprovarGuanyat(jugadorActual);

            jugadorActual = (jugadorActual + 1) % nomsJugadors.length;
            torn++;
        }
    }

    public void segonTorn(int jugador) {
        System.out.println("Has de tornar a tirar els daus");
        
        demanarTiro();
        if (!joc) {
            return;
        }

        int resultatDaus = dausJugador(jugador);
            moureJugador(jugador, resultatDaus);

        comprovarGuanyat(jugador);
    }

    public void imprimirTorn(int torn, int jugadorActual) {
        System.out.println("\nTorn nº: " + torn);
        System.out.println("Jugador: " + nomsJugadors[jugadorActual]);
        System.out.println("Posició actual: Casella " + posicions[jugadorActual]);
    }

    public boolean comprovarPenalitzacions(int jugador) {
        if (tornsPenalitzacio[jugador] > 0) {
            System.out.println("\n" + nomsJugadors[jugador] + " esta a la " + motiuPenalitzacio[jugador] + ". Torns restants: " + tornsPenalitzacio[jugador]);
            tornsPenalitzacio[jugador]--;
            
            if (tornsPenalitzacio[jugador] == 0) {
                System.out.println("\n" + nomsJugadors[jugador] + " surt de la " + motiuPenalitzacio[jugador]);
                motiuPenalitzacio[jugador] = "";
            }
            return false;
        } else {
            return true;
        }
    }

    public void demanarTiro() {
        boolean entradaValida = false;
        String entrada = "";
        while (!entradaValida) {
            entrada = demanarString(">> ");
            if (!entrada.equalsIgnoreCase("tiro") && !entrada.equalsIgnoreCase("sortir")) {
                System.out.println("Escriu 'tiro' per tirar els daus o 'sortir' per acabar el joc.");
            } else {
                entradaValida = true;
            }
        }
        
        if (entrada.equalsIgnoreCase("sortir")) {
            System.out.println("\nJoc acabat");
            joc = false;
        }
    }

    public int dausJugador(int jugador) {
        int[] daus = tirarDaus(jugador);
        int sumaDaus = daus[0] + daus[1];
        if (daus[1] == 0) {
            System.out.println("Has obtingut un " + daus[0] + " = " + sumaDaus + ".");
        } else {
            System.out.println("Has obtingut un " + daus[0] + " i " + daus[1] + " = " + sumaDaus + ".");
        }
        return sumaDaus;
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

    public int tirarDau() {
        return (int)(Math.random() * 6) + 1;
    }

    public void moureJugador(int jugador, int passos) {
        int posicioAntiga = posicions[jugador];
        int novaPosicio = posicioAntiga + passos;
    
        // Comprovar rebot
        if (novaPosicio > 63) {
            int excés = novaPosicio - 63;
            novaPosicio = 63 - excés;
            System.out.println("\nPasses de la casella 63. Rebot a la casella " + novaPosicio + ".");
        }
    
        posicions[jugador] = novaPosicio;
        System.out.println("\nAvances fins la casella " + novaPosicio + ".");
    
        processarCasellaEspecial(jugador);
    }

    public void comprovarGuanyat(int jugador){
        if (posicions[jugador] == 63) {
            System.out.println("\n" + nomsJugadors[jugador] + " HA GUANYAT!");
            joc = false;
        }
    }

    public void processarCasellaEspecial(int jugador) {
        int posicio = posicions[jugador];
        String tipusCasella = tauler[posicio];

        if (tipusCasella.isEmpty() || tipusCasella.equals("Inici")) {
            return;
        }
    
        System.out.print("Casella nº " + posicio + ": " + tipusCasella + ". ");
    
        switch (tipusCasella) {
            case "Oca":
                oca(jugador);
                break;
            
            case "Pont":
                pont(jugador);
                break;
            
            case "Fonda":
                fonda(jugador);
                break;
            
            case "Daus 3-6":
                daus36(jugador);
                break;
            
            case "Pou":
                pou(jugador);
                break;
            
            case "Laberint":
                laberint(jugador);
                break;
            
            case "Presó":
                preso(jugador);
                break;
            
            case "Daus 4-5":
                daus45(jugador);
                break;
            
            case "La mort":
                laMort(jugador);
                break;
            
            case "Jardí de la oca":
                jardiDeLaOca(jugador);
                break;
            
            default:
                System.out.println("Casella especial sense implementar"); //no hauria de passar
        }
    }

    public void oca(int jugador) {
        System.out.println("De oca en oca y tiro porque me toca");
    
        int posicioActual = posicions[jugador];
        int seguentOca = -1;
        int[] oques = {5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59, 63};
    
        for (int oca : oques) {
            if (oca > posicioActual) {
                seguentOca = oca;
                break;
            }
        }
    
        if (seguentOca == -1) {
            seguentOca = 63;
        }

        posicions[jugador] = seguentOca;
        System.out.println("Avances fins la casella " + seguentOca + ".");
    
        segonTorn(jugador);
    }

    public void pont(int jugador) {
        System.out.println("De puente a puente y tiro porque me lleva la corriente");
        
        int posicioActual = posicions[jugador];
        int altrePont;
    
        if (posicioActual == 6) {
            altrePont = 12;
        } else {
            altrePont = 6;
        }
    
        posicions[jugador] = altrePont;
        System.out.println("Avances fins la casella " + altrePont + ".");
    
        segonTorn(jugador);
    }

    public void fonda(int jugador) {
        System.out.println("(Fonda: una jugada sense moure's)");
        // Penalització: 1 torn
        tornsPenalitzacio[jugador] = 1;
        motiuPenalitzacio[jugador] = "FONDA";
    }

    public void daus36(int jugador) {
        System.out.println("De dado a dado y tiro porque me ha tocado");
        // Aquí necessitem saber si és la primera tirada,CAL ACABAR
    }

    public void pou(int jugador) {
        System.out.println("(Pou: dues jugades sense moure's)");
        // Penalització: 2 torns
        tornsPenalitzacio[jugador] = 2;
        motiuPenalitzacio[jugador] = "POU";
    }

    public void laberint(int jugador) {
        System.out.println("(Laberint: tornes a la casella 39)");
        posicions[jugador] = 39;
        System.out.println("Tornes a la casella 39.");
    }

    public void preso(int jugador) {
        System.out.println("(Presó: no et pots moure durant 3 torns)");
        // Penalització: 3 torns
        tornsPenalitzacio[jugador] = 3;
        motiuPenalitzacio[jugador] = "PRESO";
    }

    public void daus45(int jugador) {
        System.out.println("(Daus 4-5)");
        // per acabar
    }

    public void laMort(int jugador) {
        System.out.println("La mort: tornes a l'inici");
        posicions[jugador] = 0;
        System.out.println("Tornes a la casella 0 ");
    }

    public void jardiDeLaOca(int jugador) {
        // Ja s'ha gestionar a jugar()
    }
}