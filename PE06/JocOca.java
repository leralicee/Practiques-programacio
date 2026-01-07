import java.util.Scanner;

/**
 * Implementació del Joc de l'Oca per a 2-4 jugadors.
 */
public class JocOca {

    // Codis de colors ANSI
    private static final String RESET = "\u001B[0m";
    private static final String VERMELL = "\u001B[38;5;204m";
    private static final String VERD = "\u001B[38;5;120m";
    private static final String GROC = "\u001B[38;5;229m";
    private static final String BLAU = "\u001B[38;5;111m";
    private static final String MAGENTA = "\u001B[38;5;219m";
    private static final String CYAN = "\u001B[38;5;159m";
    private static final String ROSA = "\u001B[38;5;218m";
    private static final String TARONJA = "\u001B[38;5;216m";
    private static final String LILA = "\u001B[38;5;183m";
    private static final String VERD_AIGUA = "\u001B[38;5;122m";
    private static final String NEGRETA = "\u001B[1m";

    private final Scanner scanner = new Scanner(System.in);
    private final int MAX_JUGADORS = 4;
    private final int MIN_JUGADORS = 2;
    private final int CASELLA_FINAL = 63;
    private final int CASELLA_POU = 31;
    private final int CASELLA_LABERINT_TORNADA = 39;
    private final int CASELLA_UN_DAU = 60;
    private final int CASELLA_DAUS_36 = 26;
    private final int CASELLA_DAUS_45 = 53;
    
    // Penalitzacions de torns
    private final int TORNS_FONDA = 1;
    private final int TORNS_POU = 2;
    private final int TORNS_PRESO = 3;
    
    private final int[] CASELLES_OCA = {5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59};
    
    private String[] tauler;
    private String[] nomsJugadors;
    private int[] posicions;
    private int[] tornsPenalitzacio;
    private String[] motiuPenalitzacio;
    private boolean[] primerTornFet;
    private boolean jocActiu = true;

    public static void main(String[] args) {
        JocOca joc = new JocOca();
        joc.iniciarJoc();
    }

    /**
     * Mètode principal que inicia el joc.
     */
    public void iniciarJoc() {
        inicialitzarJoc();
        jugar();
        scanner.close();
    }

    /**
     * Demana un número enter dins d'un rang específic.
     */
    public int demanarInt(String missatge, int min, int max) {
        while (true) {
            System.out.print(CYAN + missatge + " (" + min + " - " + max + "): " + RESET);
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.println(VERMELL + "ERROR: El valor ha d'estar entre " + min + " i " + max + "." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(VERMELL + "ERROR: Entrada no vàlida." + RESET);
            }
        }
    }

    /**
     * Demana una cadena de text no buida.
     */
    public String demanarString(String missatge) {
        String input;
        do {
            System.out.print(CYAN + missatge + RESET);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(VERMELL + "ERROR: El camp no pot estar buit." + RESET);
            }
        } while (input.isEmpty());
        return input;
    }

    /**
     * Inicialitza totes les estructures de dades del joc.
     */
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
        
        System.out.println("\n" + ROSA + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(ROSA + "║    " + NEGRETA + "BENVINGUTS AL JOC DE L'OCA" + RESET + ROSA + "    ║" + RESET);
        System.out.println(ROSA + "╚════════════════════════════════════════╝" + RESET);
        System.out.println("\n" + LILA + "Jugadors: " + String.join(", ", nomsJugadors) + RESET);
        System.out.println(CYAN + "Comenceu a la casella 0 (Inici)" + RESET + "\n");
    }

    /**
     * Crea el tauler amb totes les caselles i els seus tipus.
     */
    public String[] inicialitzarTauler() {
        String[] taulerNou = new String[64];
        for (int i = 0; i < taulerNou.length; i++) {
            taulerNou[i] = obtenirTipusCasella(i);
        }
        return taulerNou;
    }

    /**
     * Retorna el tipus de casella segons el número.
     */
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

    /**
     * Demana els noms dels jugadors i valida que no estiguin duplicats.
     */
    public String[] demanarJugadors() {
        int numJugadors = demanarInt("Introdueix el nombre de jugadors", MIN_JUGADORS, MAX_JUGADORS);
        String[] jugadors = new String[numJugadors];
        
        for (int i = 0; i < numJugadors; i++) {
            String nom;
            boolean nomDuplicat;
            do {
                nom = demanarString("Introdueix el nom del jugador " + (i + 1) + ": ");
                nomDuplicat = false;
                
                // Comprovar si el nom ja existeix (case-insensitive)
                for (int j = 0; j < i; j++) {
                    if (jugadors[j].equalsIgnoreCase(nom)) {
                        nomDuplicat = true;
                        System.out.println(VERMELL + "ERROR: Aquest nom ja està en ús (o és molt similar). Tria un altre." + RESET);
                        break;
                    }
                }
            } while (nomDuplicat);
            
            jugadors[i] = nom;
        }
        return jugadors;
    }

    /**
     * Bucle principal del joc.
     */
    public void jugar() {
        int jugadorActual = 0;

        while (jocActiu) {
            imprimirTorn(jugadorActual);

            if (!comprovarPenalitzacions(jugadorActual)) {
                // El jugador està penalitzat, no marca el primer torn com a fet
                jugadorActual = seguentJugador(jugadorActual);
                continue;
            }

            executarTorn(jugadorActual);
            
            primerTornFet[jugadorActual] = true;
            jugadorActual = seguentJugador(jugadorActual);
        }
    }

    /**
     * Executa el torn complet d'un jugador, incloent torns extra.
     */
    public void executarTorn(int jugador) {
        boolean tornExtra = true;
        boolean primerTir = !primerTornFet[jugador];
        
        while (tornExtra && jocActiu) {
            demanarTiro();
            if (!jocActiu) {
                break;
            }

            int resultatDaus = tirarIProcessarDaus(jugador, primerTir);
            
            // Després del primer tir, ja no és el primer tir
            if (primerTir) {
                primerTir = false;
            }
            
            // Cas especial 3-6 o 4-5: ja s'ha mogut el jugador i torna a tirar
            if (resultatDaus == -1) {
                // Combinació especial - sempre dona torn extra
                tornExtra = true;
            }
            // Cas normal: moviment amb daus
            else if (resultatDaus > 0) {
                moureJugador(jugador, resultatDaus);
                
                if (!jocActiu) {
                    break;
                }
                
                // Comprovar si dona torn extra
                tornExtra = esCasellaTornExtra(posicions[jugador]);
            }
        }
    }

    /**
     * Retorna l'índex del següent jugador.
     */
    public int seguentJugador(int jugadorActual) {
        return (jugadorActual + 1) % nomsJugadors.length;
    }

    /**
     * Comprova si una casella dona torn extra (Oca o Pont).
     */
    public boolean esCasellaTornExtra(int posicio) {
        String tipusCasella = tauler[posicio];
        return tipusCasella.equals("Oca") || tipusCasella.equals("Pont");
    }

    /**
     * Mostra la informació del torn actual.
     */
    public void imprimirTorn(int jugadorActual) {
        System.out.println("\n" + TARONJA + "__________________________________________\n" + RESET);
        System.out.println(NEGRETA + MAGENTA + "És el torn del jugador " + (jugadorActual + 1) + ", " 
                + nomsJugadors[jugadorActual] + "." + RESET);
        System.out.println(CYAN + "Posició actual: Casella " + posicions[jugadorActual] + RESET + "\n");
    }

    /**
     * Comprova si un jugador té penalitzacions i les gestiona.
     * Retorna true si el jugador pot tirar, false si està penalitzat.
     */
    public boolean comprovarPenalitzacions(int jugador) {
        if (tornsPenalitzacio[jugador] > 0) {
            System.out.println(VERMELL + nomsJugadors[jugador] + " està a la " + motiuPenalitzacio[jugador]
                    + ". Torns restants: " + tornsPenalitzacio[jugador] + RESET);
            tornsPenalitzacio[jugador]--;

            if (tornsPenalitzacio[jugador] == 0) {
                System.out.println(VERD + nomsJugadors[jugador] + " surt de la " + motiuPenalitzacio[jugador] + RESET);
                motiuPenalitzacio[jugador] = "";
            }
            System.out.println();
            return false;
        }
        return true;
    }

    /**
     * Demana al jugador que tiri els daus o surti del joc.
     */
    public void demanarTiro() {
        boolean entradaValida = false;
        String entrada;
        
        while (!entradaValida) {
            entrada = demanarString(">> ");
            if (entrada.equalsIgnoreCase("tiro")) {
                entradaValida = true;
            } else if (entrada.equalsIgnoreCase("sortir")) {
                System.out.println("\n" + ROSA + "Joc acabat" + RESET + "\n");
                jocActiu = false;
                entradaValida = true;
            } else {
                System.out.println(GROC + "Escriu 'tiro' per tirar els daus o 'sortir' per acabar el joc." + RESET);
            }
        }
    }

    /**
     * Tira els daus i processa combinacions especials del primer torn.
     * Retorna el número de caselles a avançar, o -1 si és combinació especial.
     */
    public int tirarIProcessarDaus(int jugador, boolean esPrimerTir) {
        int[] daus = tirarDaus(jugador);
        int sumaDaus = daus[0] + daus[1];

        mostrarResultatDaus(daus, sumaDaus);

        // Només comprovar combinacions especials en el primer tir i amb 2 daus
        if (esPrimerTir && daus[1] != 0) {
            if (esCombinacio36(daus)) {
                return processarCombinacioEspecial(jugador, CASELLA_DAUS_36);
            } else if (esCombinacio45(daus)) {
                return processarCombinacioEspecial(jugador, CASELLA_DAUS_45);
            }
        }
        
        return sumaDaus;
    }

    /**
     * Comprova si els daus són 3 i 6.
     */
    public boolean esCombinacio36(int[] daus) {
        return (daus[0] == 3 && daus[1] == 6) || (daus[0] == 6 && daus[1] == 3);
    }

    /**
     * Comprova si els daus són 4 i 5.
     */
    public boolean esCombinacio45(int[] daus) {
        return (daus[0] == 4 && daus[1] == 5) || (daus[0] == 5 && daus[1] == 4);
    }

    /**
     * Processa les combinacions especials 3-6 i 4-5 del primer torn.
     * Retorna -1 per indicar que s'ha processat la combinació especial.
     */
    public int processarCombinacioEspecial(int jugador, int casellaDestinacio) {
        System.out.println(LILA + "De dau a dau i tiro perquè m'ha tocat." + RESET);
        posicions[jugador] = casellaDestinacio;
        System.out.println(VERD_AIGUA + "Avances fins la casella " + casellaDestinacio + "." + RESET);
        
        // Marcar el primer torn com a fet
        primerTornFet[jugador] = true;
        
        // Les caselles 26 i 53 són caselles normals, però processem per si de cas
        // (això permet que el codi sigui més robust si canvien les regles)
        processarCasellaEspecial(jugador);
        
        return -1; // Indica que és una jugada especial
    }

    /**
     * Mostra el resultat dels daus.
     */
    public void mostrarResultatDaus(int[] daus, int suma) {
        if (daus[1] == 0) {
            System.out.println(TARONJA + "Has obtingut un " + daus[0] + " = " + suma + "." + RESET);
        } else {
            System.out.println(TARONJA + "Has obtingut un " + daus[0] + " i " + daus[1] + " = " + suma + "." + RESET);
        }
    }

    /**
     * Tira 1 o 2 daus segons la posició del jugador.
     */
    public int[] tirarDaus(int jugador) {
        if (posicions[jugador] >= CASELLA_UN_DAU) {
            return new int[] {tirarDau(), 0};
        } else {
            return new int[] {tirarDau(), tirarDau()};
        }
    }

    /**
     * Genera un valor aleatori entre 1 i 6.
     */
    public int tirarDau() {
        return (int) (Math.random() * 6) + 1;
    }

    /**
     * Mou el jugador el nombre de caselles indicat.
     * Gestiona el rebot si es passa de la casella 63.
     */
    public void moureJugador(int jugador, int passos) {
        int novaPosicio = posicions[jugador] + passos;

        // Si es passa de la casella final, rebota cap enrere
        if (novaPosicio > CASELLA_FINAL) {
            int exces = novaPosicio - CASELLA_FINAL;
            novaPosicio = CASELLA_FINAL - exces;
            System.out.println(GROC + "Passes de la casella 63. Rebots a la casella " + novaPosicio + "." + RESET);
        }

        posicions[jugador] = novaPosicio;
        System.out.println(VERD_AIGUA + "Avances fins la casella " + novaPosicio + "." + RESET);

        processarCasellaEspecial(jugador);
    }

    /**
     * Processa l'efecte de les caselles especials.
     */
    public void processarCasellaEspecial(int jugador) {
        int posicio = posicions[jugador];
        String tipusCasella = tauler[posicio];

        if (tipusCasella.isEmpty() || tipusCasella.equals("Inici")) {
            return;
        }

        System.out.print(CYAN + "Casella nº " + posicio + ": " + NEGRETA + tipusCasella + RESET + CYAN + ". " + RESET);

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

    /**
     * Gestiona la casella Oca: avança a la següent oca.
     */
    public void oca(int jugador) {
        int seguentOca = buscarSeguentOca(posicions[jugador]);
        
        // Si no hi ha més oques, es queda a la casella actual
        if (seguentOca == -1) {
            System.out.println(LILA + "És l'última oca. Torna a tirar." + RESET);
        } else {
            System.out.println(LILA + "D'oca en oca i tiro perquè em toca" + RESET);
            posicions[jugador] = seguentOca;
            System.out.println(VERD_AIGUA + "Avances fins la casella " + seguentOca + "." + RESET);
        }
    }

    /**
     * Busca la següent casella amb oca.
     * Retorna -1 si no hi ha cap més oca.
     */
    public int buscarSeguentOca(int posicioActual) {
        for (int oca : CASELLES_OCA) {
            if (oca > posicioActual) {
                return oca;
            }
        }
        // Si no hi ha més oques, retorna -1
        return -1;
    }

    /**
     * Gestiona la casella Pont: salta a l'altre pont.
     */
    public void pont(int jugador) {
        System.out.println(BLAU + "De pont a pont i tiro perquè m'emporta el corrent" + RESET);

        int altrePont = (posicions[jugador] == 6) ? 12 : 6;
        posicions[jugador] = altrePont;
        System.out.println(VERD_AIGUA + "Avances fins la casella " + altrePont + "." + RESET);
    }

    /**
     * Gestiona la casella Fonda: 1 torn sense moure's.
     */
    public void fonda(int jugador) {
        System.out.println(GROC + "Fonda: una jugada sense moure's." + RESET);
        aplicarPenalitzacio(jugador, TORNS_FONDA, "FONDA");
    }

    /**
     * Gestiona la casella Pou: 2 torns sense moure's.
     * Si hi ha un altre jugador al pou, aquest surt i l'actual hi entra.
     */
    public void pou(int jugador) {
        int jugadorAlPou = buscarJugadorAlPou(jugador);
        
        if (jugadorAlPou != -1) {
            System.out.println(BLAU + "Pou: " + nomsJugadors[jugadorAlPou] + " surt del pou!" + RESET);
            treurePenalitzacio(jugadorAlPou);
            System.out.println(VERMELL + nomsJugadors[jugador] + " entra al pou." + RESET);
        } else {
            System.out.println(BLAU + "Pou: dues jugades sense moure's." + RESET);
        }

        aplicarPenalitzacio(jugador, TORNS_POU, "POU");
    }

    /**
     * Busca si hi ha un jugador al pou (exclou el jugador actual).
     */
    public int buscarJugadorAlPou(int jugadorActual) {
        for (int i = 0; i < nomsJugadors.length; i++) {
            if (i != jugadorActual && posicions[i] == CASELLA_POU && motiuPenalitzacio[i].equals("POU")) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Aplica una penalització a un jugador.
     */
    public void aplicarPenalitzacio(int jugador, int torns, String motiu) {
        tornsPenalitzacio[jugador] = torns;
        motiuPenalitzacio[jugador] = motiu;
    }

    /**
     * Elimina la penalització d'un jugador.
     */
    public void treurePenalitzacio(int jugador) {
        tornsPenalitzacio[jugador] = 0;
        motiuPenalitzacio[jugador] = "";
    }

    /**
     * Gestiona la casella Laberint: torna a la casella 39.
     */
    public void laberint(int jugador) {
        System.out.println(MAGENTA + "Laberint: tornes a la casella 39." + RESET);
        posicions[jugador] = CASELLA_LABERINT_TORNADA;
        System.out.println(GROC + "Retornes a la casella " + CASELLA_LABERINT_TORNADA + "." + RESET);
    }

    /**
     * Gestiona la casella Presó: 3 torns sense moure's.
     */
    public void preso(int jugador) {
        System.out.println(VERMELL + "Presó: no et pots moure durant 3 torns." + RESET);
        aplicarPenalitzacio(jugador, TORNS_PRESO, "PRESÓ");
    }

    /**
     * Gestiona la casella La mort: torna a l'inici.
     */
    public void laMort(int jugador) {
        System.out.println(VERMELL + NEGRETA + "La mort: tornes a l'inici." + RESET);
        posicions[jugador] = 0;
        System.out.println(GROC + "Tornes a la casella 0." + RESET);
    }

    /**
     * Anuncia el guanyador del joc.
     */
    public void jardiDeLaOca(int jugador) {
        String nomGuanyador = nomsJugadors[jugador];
        String missatge = nomGuanyador + " HA GUANYAT!";
        int midaMarc = Math.max(missatge.length() + 4, 42);
        
        System.out.println();
        
        // Línia superior
        System.out.print(ROSA + NEGRETA + "╔");
        for (int i = 0; i < midaMarc; i++) {
            System.out.print("═");
        }
        System.out.println("╗" + RESET);
        
        // Línia central amb missatge centrat
        int espaisTotalPadding = midaMarc - missatge.length();
        int espaisEsquerra = espaisTotalPadding / 2;
        int espaisDreta = espaisTotalPadding - espaisEsquerra;
        
        System.out.print(ROSA + NEGRETA + "║");
        for (int i = 0; i < espaisEsquerra; i++) {
            System.out.print(" ");
        }
        System.out.print(missatge);
        for (int i = 0; i < espaisDreta; i++) {
            System.out.print(" ");
        }
        System.out.println("║" + RESET);
        
        // Línia inferior
        System.out.print(ROSA + NEGRETA + "╚");
        for (int i = 0; i < midaMarc; i++) {
            System.out.print("═");
        }
        System.out.println("╝" + RESET);
        
        System.out.println();
    }
}