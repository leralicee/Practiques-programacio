import java.util.Scanner;

public class JocOca {

    public final Scanner scanner = new Scanner(System.in);
    public final int MAX_JUGADORS = 4;
    public final int MIN_JUGADORS = 2;
    public final int CASELLA_FINAL = 63;
    public final int CASELLA_POU = 31;
    public final int CASELLA_LABERINT_TORNADA = 39;
    public final int CASELLA_UN_DAU = 60;
    public final int[] CASELLES_OCA = {5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59, 63};
    
    public String[] tauler;
    public String[] nomsJugadors;
    public int[] posicions;
    public int[] tornsPenalitzacio;
    public String[] motiuPenalitzacio;
    public boolean[] primerTornFet;
    public boolean joc = true;

    public static void main(String[] args) {
        JocOca joc = new JocOca();
        joc.iniciarJoc();
    }

    public void iniciarJoc() {
        inicialitzarJoc();
        jugar();
        scanner.close();
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
        String input;
        do {
            System.out.print(missatge);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("ERROR: El camp no pot estar buit.");
            }
        } while (input.isEmpty());
        return input;
    }

    public void inicialitzarJoc() {
        tauler = inicialitzarTauler();
        nomsJugadors = demanarJugadors();
        int numJugadors = nomsJugadors.length;
        posicions = new int[numJugadors];
        tornsPenalitzacio = new int[numJugadors];
        motiuPenalitzacio = new String[numJugadors];
        primerTornFet = new boolean[numJugadors];
        
        for (int i = 0; i < numJugadors; i++) {
            posicions[i] = 0;
            tornsPenalitzacio[i] = 0;
            motiuPenalitzacio[i] = "";
            primerTornFet[i] = false;
        }
        
        System.out.println("\nBenvinguts al Joc de la Oca: " + String.join(", ", nomsJugadors)
                + "!\nComenceu a la casella 0 (Inici)");
    }

    public String[] inicialitzarTauler() {
        String[] taulerNou = new String[64];
        for (int i = 0; i < taulerNou.length; i++) {
            taulerNou[i] = obtenirTipusCasella(i);
        }
        return taulerNou;
    }

    public String obtenirTipusCasella(int casella) {
        switch (casella) {
            case 0: return "Inici";
            case 5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59: return "Oca";
            case 6, 12: return "Pont";
            case 19: return "Fonda";
            case 31: return "Pou";
            case 42: return "Laberint";
            case 52: return "Presó";
            case 58: return "La mort";
            case 63: return "Jardí de la oca";
            default: return "";
        }
    }

    public String[] demanarJugadors() {
        int numJugadors = demanarInt("Introdueix el nombre de jugadors", MIN_JUGADORS, MAX_JUGADORS);
        String[] jugadors = new String[numJugadors];
        for (int i = 0; i < numJugadors; i++) {
            jugadors[i] = demanarString("Introdueix el nom del jugador " + (i + 1) + ": ");
        }
        return jugadors;
    }

    public void jugar() {
        int jugadorActual = 0;

        while (joc) {
            imprimirTorn(jugadorActual);

            if (!comprovarPenalitzacions(jugadorActual)) {
                primerTornFet[jugadorActual] = true;
                jugadorActual = seguentJugador(jugadorActual);
                continue;
            }

            executarTorn(jugadorActual);
            
            if (!primerTornFet[jugadorActual]) {
                primerTornFet[jugadorActual] = true;
            }
            
            jugadorActual = seguentJugador(jugadorActual);
        }
    }

    public void executarTorn(int jugador) {
        boolean tornExtra = true;
        
        while (tornExtra && joc) {
            demanarTiro();
            if (!joc) {
                break;
            }

            int resultatDaus = tirarIProcessarDaus(jugador);
            
            if (resultatDaus != -1) {
                moureJugador(jugador, resultatDaus);
                
                if (posicions[jugador] == CASELLA_FINAL) {
                    jardiDeLaOca(jugador);
                    joc = false;
                    break;
                }
                
                tornExtra = esCasellaTornExtra(posicions[jugador]);
            }
        }
    }

    public int seguentJugador(int jugadorActual) {
        return (jugadorActual + 1) % nomsJugadors.length;
    }

    public boolean esCasellaTornExtra(int posicio) {
        String tipusCasella = tauler[posicio];
        return tipusCasella.equals("Oca") || tipusCasella.equals("Pont");
    }

    public void imprimirTorn(int jugadorActual) {
        System.out.println("\n__________________________________________");
        System.out.println("És el torn del jugador " + (jugadorActual + 1) + ", " + nomsJugadors[jugadorActual] + ".");
        System.out.println("Posició actual: Casella " + posicions[jugadorActual]);
    }

    public boolean comprovarPenalitzacions(int jugador) {
        if (tornsPenalitzacio[jugador] > 0) {
            System.out.println("\n" + nomsJugadors[jugador] + " està a la " + motiuPenalitzacio[jugador]
                    + ". Torns restants: " + tornsPenalitzacio[jugador]);
            tornsPenalitzacio[jugador]--;

            if (tornsPenalitzacio[jugador] == 0) {
                System.out.println(nomsJugadors[jugador] + " surt de la " + motiuPenalitzacio[jugador]);
                motiuPenalitzacio[jugador] = "";
            }
            return false;
        }
        return true;
    }

    public void demanarTiro() {
        boolean entradaValida = false;
        String entrada;
        
        while (!entradaValida) {
            entrada = demanarString(">> ");
            if (entrada.equalsIgnoreCase("tiro")) {
                entradaValida = true;
            } else if (entrada.equalsIgnoreCase("sortir")) {
                System.out.println("\nJoc acabat");
                joc = false;
                entradaValida = true;
            } else {
                System.out.println("Escriu 'tiro' per tirar els daus o 'sortir' per acabar el joc.");
            }
        }
    }

    public int tirarIProcessarDaus(int jugador) {
        int[] daus = tirarDaus(jugador);
        int sumaDaus = daus[0] + daus[1];

        mostrarResultatDaus(daus, sumaDaus);

        if (!primerTornFet[jugador] && daus[1] != 0) {
            if (esCombinacio36(daus)) {
                return processarCombinacioEspecial(jugador, 26);
            } else if (esCombinacio45(daus)) {
                return processarCombinacioEspecial(jugador, 53);
            }
        }
        
        return sumaDaus;
    }

    public boolean esCombinacio36(int[] daus) {
        return (daus[0] == 3 && daus[1] == 6) || (daus[0] == 6 && daus[1] == 3);
    }

    public boolean esCombinacio45(int[] daus) {
        return (daus[0] == 4 && daus[1] == 5) || (daus[0] == 5 && daus[1] == 4);
    }

    public int processarCombinacioEspecial(int jugador, int casellaDestinacio) {
        System.out.println("De dado a dado y tiro porque me ha tocado");
        posicions[jugador] = casellaDestinacio;
        System.out.println("Avances fins la casella " + casellaDestinacio + ".");
        primerTornFet[jugador] = true;
        processarCasellaEspecial(jugador);
        return -1;
    }

    public void mostrarResultatDaus(int[] daus, int suma) {
        if (daus[1] == 0) {
            System.out.println("Has obtingut un " + daus[0] + " = " + suma + ".");
        } else {
            System.out.println("Has obtingut un " + daus[0] + " i " + daus[1] + " = " + suma + ".");
        }
    }

    public int[] tirarDaus(int jugador) {
        if (posicions[jugador] >= CASELLA_UN_DAU) {
            return new int[] {tirarDau(), 0};
        } else {
            return new int[] {tirarDau(), tirarDau()};
        }
    }

    public int tirarDau() {
        return (int) (Math.random() * 6) + 1;
    }

    public void moureJugador(int jugador, int passos) {
        int novaPosicio = posicions[jugador] + passos;

        if (novaPosicio > CASELLA_FINAL) {
            int exces = novaPosicio - CASELLA_FINAL;
            novaPosicio = CASELLA_FINAL - exces;
            System.out.println("Passes de la casella 63. Rebots a la casella " + novaPosicio + ".");
        }

        posicions[jugador] = novaPosicio;
        System.out.println("Avances fins la casella " + novaPosicio + ".");

        processarCasellaEspecial(jugador);
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
            case "Pou":
                pou(jugador);
                break;
            case "Laberint":
                laberint(jugador);
                break;
            case "Presó":
                preso(jugador);
                break;
            case "La mort":
                laMort(jugador);
                break;
            case "Jardí de la oca":
                jardiDeLaOca(jugador);
                break;
        }
    }

    public void oca(int jugador) {
        System.out.println("De oca en oca y tiro porque me toca");

        int seguentOca = buscarSeguentOca(posicions[jugador]);
        posicions[jugador] = seguentOca;
        System.out.println("Avances fins la casella " + seguentOca + ".");

        if (seguentOca == CASELLA_FINAL) {
            jardiDeLaOca(jugador);
            joc = false;
        }
    }

    public int buscarSeguentOca(int posicioActual) {
        for (int oca : CASELLES_OCA) {
            if (oca > posicioActual) {
                return oca;
            }
        }
        return CASELLA_FINAL;
    }

    public void pont(int jugador) {
        System.out.println("De puente a puente y tiro porque me lleva la corriente");

        int altrePont = (posicions[jugador] == 6) ? 12 : 6;
        posicions[jugador] = altrePont;
        System.out.println("Avances fins la casella " + altrePont + ".");
    }

    public void fonda(int jugador) {
        System.out.println("(Fonda: una jugada sense moure's)");
        aplicarPenalitzacio(jugador, 1, "FONDA");
    }

    public void pou(int jugador) {
        System.out.println("(Pou: dues jugades sense moure's)");

        int jugadorAlPou = buscarJugadorAlPou(jugador);
        
        if (jugadorAlPou != -1) {
            System.out.println(nomsJugadors[jugadorAlPou] + " surt del pou i " + nomsJugadors[jugador] + " hi entra.");
            treurePenalitzacio(jugadorAlPou);
        }

        aplicarPenalitzacio(jugador, 2, "POU");
    }

    public int buscarJugadorAlPou(int jugadorActual) {
        for (int i = 0; i < nomsJugadors.length; i++) {
            if (i != jugadorActual && posicions[i] == CASELLA_POU && motiuPenalitzacio[i].equals("POU")) {
                return i;
            }
        }
        return -1;
    }

    public void aplicarPenalitzacio(int jugador, int torns, String motiu) {
        tornsPenalitzacio[jugador] = torns;
        motiuPenalitzacio[jugador] = motiu;
    }

    public void treurePenalitzacio(int jugador) {
        tornsPenalitzacio[jugador] = 0;
        motiuPenalitzacio[jugador] = "";
    }

    public void laberint(int jugador) {
        System.out.println("(Laberint: tornes a la casella 39)");
        posicions[jugador] = CASELLA_LABERINT_TORNADA;
        System.out.println("Retornes a la casella " + CASELLA_LABERINT_TORNADA + ".");
    }

    public void preso(int jugador) {
        System.out.println("(Presó: no et pots moure durant 3 torns)");
        aplicarPenalitzacio(jugador, 3, "PRESÓ");
    }

    public void laMort(int jugador) {
        System.out.println("(La mort: tornes a l'inici)");
        posicions[jugador] = 0;
        System.out.println("Tornes a la casella 0.");
    }

    public void jardiDeLaOca(int jugador) {
        System.out.println(nomsJugadors[jugador] + " HA GUANYAT!");
    }
}