import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Escacs {
    private char[][] tauler;
    private String jugadorBlanques;
    private String jugadorNegres;
    private boolean jocEnCurs;
    private boolean tornBlanques; // true = blanques, false = negres
    private List<String> historialMoviments;
    private List<Character> pecesCapturadesBlanques;
    private List<Character> pecesCapturadesNegres;
    private boolean demanarJugadors;
    private String guanyador;
    private Scanner scanner;

    // Variables per enrocs
    private boolean reiBlancMogut;
    private boolean reiNegreMogut;
    private boolean torreBlancaA8Moguda;
    private boolean torreBlancaH8Moguda;
    private boolean torreNegraA1Moguda;
    private boolean torreNegraH1Moguda;

    // Constants per les peces
    private static final char PEOBLANC = 'P';
    private static final char TORREBLANC = 'T';
    private static final char CAVALLBLANC = 'C';
    private static final char ALFILBLANC = 'A';
    private static final char REINABLANC = 'Q';
    private static final char REIBLANC = 'K';

    private static final char PEONEGRE = 'p';
    private static final char TORRENEGRE = 't';
    private static final char CAVALLNEGRE = 'c';
    private static final char ALFILNEGRE = 'a';
    private static final char REINANEGRE = 'q';
    private static final char REINEGRE = 'k';

    private static final char BUIT = '.';

    // Constants del tauler
    private static final int MIDA_TAULER = 8;
    private static final int FILA_PEONS_NEGRES = 1;
    private static final int FILA_PEONS_BLANCS = 6;
    private static final int FILA_PECES_NEGRES = 0;
    private static final int FILA_PECES_BLANQUES = 7;

    // Constants per enrocs
    private static final int COLUMNA_REI_INICIAL = 4;
    private static final int COLUMNA_TORRE_DAMA = 0;
    private static final int COLUMNA_TORRE_REI = 7;
    private static final int COLUMNA_REI_ENROC_CURT = 6;
    private static final int COLUMNA_REI_ENROC_LLARG = 2;
    private static final int COLUMNA_TORRE_ENROC_CURT = 5;
    private static final int COLUMNA_TORRE_ENROC_LLARG = 3;

    // CONSTRUCTOR I MAIN

    public static void main(String[] args) {
        Escacs app = new Escacs();
        app.iniciar();
    }

    /**
     * Constructor de la classe Escacs.
     * Inicialitza el tauler, les llistes de captures, l'historial de moviments
     * i estableix l'estat inicial del joc amb les blanques començant.
     */
    public Escacs() {
        tauler = new char[MIDA_TAULER][MIDA_TAULER];
        historialMoviments = new ArrayList<>();
        pecesCapturadesBlanques = new ArrayList<>();
        pecesCapturadesNegres = new ArrayList<>();
        scanner = new Scanner(System.in);
        tornBlanques = true; // blanques comencen
        jocEnCurs = true;
        demanarJugadors = true;
        reiBlancMogut = false;
        reiNegreMogut = false;
        torreBlancaA8Moguda = false;
        torreBlancaH8Moguda = false;
        torreNegraA1Moguda = false;
        torreNegraH1Moguda = false;
    }

    // INICIALITZACIÓ

    /**
     * Inicia el joc d'escacs.
     * Demana els noms dels jugadors, inicialitza el tauler i comença el bucle de
     * joc principal.
     */
    public void iniciar() {
        System.out.println("\n=== JOC D'ESCACS ===\n");

        if (demanarJugadors)
            demanarNomsJugadors();

        inicialitzarTauler();

        bucleJoc();
    }

    private void demanarNomsJugadors() {
        jugadorBlanques = demanarString("Nom del jugador amb peces BLANQUES: ");

        jugadorNegres = demanarString("Nom del jugador amb peces NEGRES: ");

        if (jugadorBlanques.equalsIgnoreCase(jugadorNegres)) {
            System.out.println("Els dos jugadors no poden tenir el mateix nom, introdueix noms diferents.");
            demanarNomsJugadors();
            return; // Evitar continuar amb noms iguals
        }

        System.out.println("\n" + jugadorBlanques + " jugarà amb les blanques (majúscules)");
        System.out.println(jugadorNegres + " jugarà amb les negres (minúscules)\n");
    }

    /**
     * Inicialitza el tauler amb les peces a les seves posicions inicials.
     * Peces negres a les files 1-2 (minúscules), peces blanques a les files 7-8
     * (majúscules).
     * Les files 3-6 queden buides.
     */
    private void inicialitzarTauler() {

        // Emplenar tot el tauler amb caselles buides
        for (int i = 0; i < MIDA_TAULER; i++) {
            for (int j = 0; j < MIDA_TAULER; j++) {
                tauler[i][j] = BUIT;
            }
        }

        // Fila 1: Peces NEGRES
        tauler[FILA_PECES_NEGRES][0] = TORRENEGRE; // a1
        tauler[FILA_PECES_NEGRES][1] = CAVALLNEGRE; // b1
        tauler[FILA_PECES_NEGRES][2] = ALFILNEGRE; // c1
        tauler[FILA_PECES_NEGRES][3] = REINANEGRE; // d1
        tauler[FILA_PECES_NEGRES][4] = REINEGRE; // e1
        tauler[FILA_PECES_NEGRES][5] = ALFILNEGRE; // f1
        tauler[FILA_PECES_NEGRES][6] = CAVALLNEGRE; // g1
        tauler[FILA_PECES_NEGRES][7] = TORRENEGRE; // h1

        // Fila 2: Peons negres
        for (int j = 0; j < MIDA_TAULER; j++) {
            tauler[FILA_PEONS_NEGRES][j] = PEONEGRE;
        }

        // Fila 7: Peons blancs
        for (int j = 0; j < MIDA_TAULER; j++) {
            tauler[FILA_PEONS_BLANCS][j] = PEOBLANC;
        }

        // Fila 8: Peces BLANQUES
        tauler[FILA_PECES_BLANQUES][0] = TORREBLANC; // a8
        tauler[FILA_PECES_BLANQUES][1] = CAVALLBLANC; // b8
        tauler[FILA_PECES_BLANQUES][2] = ALFILBLANC; // c8
        tauler[FILA_PECES_BLANQUES][3] = REINABLANC; // d8
        tauler[FILA_PECES_BLANQUES][4] = REIBLANC; // e8
        tauler[FILA_PECES_BLANQUES][5] = ALFILBLANC; // f8
        tauler[FILA_PECES_BLANQUES][6] = CAVALLBLANC; // g8
        tauler[FILA_PECES_BLANQUES][7] = TORREBLANC; // h8
    }

    // VISUALITZACIÓ

    /**
     * Mostra el tauler d'escacs per consola amb un format visual.
     * Utilitza caràcters Unicode per a les vores i alterna colors per a les
     * caselles.
     */
    private void mostrarTauler() {
        System.out.println();

        System.out.println("    ╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗");

        for (int i = 0; i < MIDA_TAULER; i++) {
            System.out.print("  " + (i + 1) + " ║");

            for (int j = 0; j < MIDA_TAULER; j++) {
                char peça = tauler[i][j];

                if (peça == BUIT) {
                    if ((i + j) % 2 == 0) {
                        System.out.print(" ░ ");
                    } else {
                        System.out.print("   ");
                    }
                } else {
                    System.out.print(" " + peça + " ");
                }

                System.out.print("║");
            }

            System.out.println();

            if (i < MIDA_TAULER - 1) {
                System.out.println("    ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣");
            } else {
                System.out.println("    ╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝");
            }
        }

        System.out.print("      ");
        for (char columna = 'a'; columna <= 'h'; columna++) {
            System.out.print(columna + "   ");
        }
        System.out.println("\n");
    }

    private void mostrarPecesCapturades() {
        if (!pecesCapturadesBlanques.isEmpty()) {
            System.out.print("Peces capturades per " + jugadorBlanques + ": ");
            for (char peça : pecesCapturadesBlanques) {
                System.out.print(peça + " ");
            }
            System.out.println();
        }

        if (!pecesCapturadesNegres.isEmpty()) {
            System.out.print("Peces capturades per " + jugadorNegres + ": ");
            for (char peça : pecesCapturadesNegres) {
                System.out.print(peça + " ");
            }
            System.out.println();
        }
    }

    private void mostrarHistorialMoviments() {
        System.out.println("\n=== HISTORIAL DE MOVIMENTS ===");

        if (historialMoviments.isEmpty()) {
            System.out.println("No s'han fet moviments.");
            return;
        }

        for (int i = 0; i < historialMoviments.size(); i++) {
            String jugador = (i % 2 == 0) ? jugadorBlanques : jugadorNegres;
            String color = (i % 2 == 0) ? "BLANQUES" : "NEGRES";
            System.out.println((i + 1) + ". " + jugador + " (" + color + "): " + historialMoviments.get(i));
        }

        System.out.println();
    }

    // BUCLE PRINCIPAL

    /**
     * Bucle principal del joc que alterna torns entre blanques i negres.
     * Mostra el tauler, demana moviments, valida i executa fins que el joc
     * finalitza.
     * Detecta escac, escac i mat, i taules per rei ofegat.
     */
    private void bucleJoc() {
        while (jocEnCurs) {
            if (comprovarEstatPartida())
                return;

            mostrarTorn();
            String moviment = demanarMoviment();
            processarMoviment(moviment);
        }
    }

    /**
     * Comprova l'estat actual de la partida i determina si hi ha escac, escac i mat
     * o taules.
     * 
     * Aquest mètode analitza la situació del jugador actual i comprova:
     * - Si el rei està en escac
     * - Si el jugador té moviments legals disponibles
     * - Determina si hi ha escac i mat (escac sense moviments legals)
     * - Determina si hi ha taules (rei bloquejat sense estar en escac)
     * 
     * @return true si el joc continua, false si la partida ha acabat (escac i mat o
     *         taules)
     * 
     *         Actualitza les variables de la classe:
     *         - jocEnCurs: false si la partida acaba
     *         - guanyador: nom del jugador guanyador (null en cas de taules)c
     */
    private boolean comprovarEstatPartida() {
        if (esEscacIMat(!tornBlanques)) {
            mostrarTauler();
            mostrarPecesCapturades();
            guanyador = tornBlanques ? jugadorBlanques : jugadorNegres;
            System.out.println("\nESCAC I MAT!");
            System.out.println(guanyador + " guanya la partida!");
            jocEnCurs = false;
            finalitzarJoc();
            return true;
        }

        if (esStalemate(tornBlanques)) {
            mostrarTauler();
            mostrarPecesCapturades();
            System.out.println("\nTAULES PER STALEMATE!");
            System.out.println("El jugador no té moviments legals però el seu rei no està en escac.");
            jocEnCurs = false;
            finalitzarJoc();
            return true;
        }

        if (estaReiEnEscac(tornBlanques))
            System.out.println("\nESCAC! El teu rei està amenaçat");

        return false;
    }

    private void mostrarTorn() {
        mostrarTauler();
        mostrarPecesCapturades();

        if (tornBlanques) {
            System.out.println("Torn de " + jugadorBlanques + " (BLANQUES)\n");
        } else {
            System.out.println("Torn de " + jugadorNegres + " (NEGRES)\n");
        }
    }

    /**
     * Demana un moviment al jugador actual.
     * 
     * @return String amb el moviment en format "origen destí" (ex: "e2 e4") o
     *         "Abandonar" per finalitzar la partida
     */
    private String demanarMoviment() {
        String jugadorActual = tornBlanques ? jugadorBlanques : jugadorNegres;
        String moviment = demanarString(
                jugadorActual + ", introdueix el teu moviment (ex: e2 e4) o 'exit' per sortir: ");

        // Comprovar si abandona
        if (moviment.equalsIgnoreCase("exit")) {
            if (tornBlanques) {
                System.out.println(jugadorBlanques + " ha abandonat la partida.");
                guanyador = jugadorNegres;
            } else {
                System.out.println(jugadorNegres + " ha abandonat la partida.");
                guanyador = jugadorBlanques;
            }
            jocEnCurs = false;
            finalitzarJoc();
            return null;
        }
        return moviment;
    }

    /**
     * Processa i executa un moviment vàlid.
     * Valida el format, l'origen, el destí, el moviment de la peça,
     * comprova escacs i executa el moviment si tot és correcte.
     * 
     * @param moviment String amb el moviment en format "e2 e4"
     * @return true si el moviment s'ha executat correctament, false si és invàlid
     */
    private void processarMoviment(String moviment) {
        if (moviment == null)
            return; // El jugador ha abandonat

        if (!validarEntradaMoviment(moviment))
            return; // Tornar a demanar moviment

        if (!validarMoviment(moviment))
            return; // Validar si el moviment és permès segons les regles

        // Si tot és correcte, actualitzar historial i canviar torn
        historialMoviments.add(moviment);
        tornBlanques = !tornBlanques;
    }

    // VALIDACIÓ D'ENTRADA

    /**
     * Valida que el format del moviment sigui correcte.
     * 
     * @param moviment String amb el moviment introduït per l'usuari
     * @return true si el format és vàlid (ex: "e2 e4"), false altrament
     * 
     *         Format esperat: lletra(a-h) + número(1-8) + espai + lletra(a-h) +
     *         número(1-8)
     */
    private boolean validarEntradaMoviment(String moviment) {

        // 1. Validar format (ha de ser: lletra+nombre espai lletra+nombre)
        String[] parts = moviment.trim().split("\\s+");

        if (parts.length != 2) {
            System.out.println("Format incorrecte. Usa: origen destí (ex: e2 e4)");
            return false;
        }

        String origen = parts[0];
        String desti = parts[1];

        // 2. Validar que origen i destí tenen exactament 2 caràcters
        if (origen.length() != 2 || desti.length() != 2) {
            System.out.println("Coordenades invàlides. Cada posició ha de tenir una lletra (a-h) i un número (1-8)");
            return false;
        }

        // 3. Validar origen
        char columnaOrigen = origen.charAt(0);
        char filaOrigen = origen.charAt(1);

        if (columnaOrigen < 'a' || columnaOrigen > 'h') {
            System.out.println("Columna d'origen invàlida. Ha de ser entre 'a' i 'h'");
            return false;
        }

        if (filaOrigen < '1' || filaOrigen > '8') {
            System.out.println("Fila d'origen invàlida. Ha de ser entre '1' i '8'");
            return false;
        }

        // 4. Validar destí
        char columnaDesti = desti.charAt(0);
        char filaDesti = desti.charAt(1);

        if (columnaDesti < 'a' || columnaDesti > 'h') {
            System.out.println("Columna de destí invàlida. Ha de ser entre 'a' i 'h'");
            return false;
        }

        if (filaDesti < '1' || filaDesti > '8') {
            System.out.println("Fila de destí invàlida. Ha de ser entre '1' i '8'");
            return false;
        }

        // 5. Validar que origen i destí no siguin la mateixa casella
        if (origen.equals(desti)) {
            System.out.println("L'origen i el destí no poden ser la mateixa casella");
            return false;
        }

        return true;
    }

    public String demanarString(String missatge) {
        String input;
        do {
            System.out.print(missatge);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("El camp no pot estar buit.");
            }
        } while (input.isEmpty());
        return input;
    }

    public boolean gestionarSiNo(String missatge) {
        do {
            String resposta = demanarString(missatge).toLowerCase();
            if (resposta.equals("si") || resposta.equals("sí") || resposta.equals("s")) {
                return true;
            } else if (resposta.equals("no") || resposta.equals("n")) {
                return false;
            } else {
                System.out.println("Resposta no vàlida, introdueix 'si' o 'no'.");
            }
        } while (true);
    }

    // VALIDACIÓ DE MOVIMENTS

    private boolean validarMoviment(String moviment) {
        String[] parts = moviment.trim().split("\\s+");
        int[] origen = parsejarCoordenada(parts[0]);
        int[] desti = parsejarCoordenada(parts[1]);

        int filaOrigen = origen[0];
        int colOrigen = origen[1];
        int filaDesti = desti[0];
        int colDesti = desti[1];

        // Comprovar si és un enroc
        if (esIntentEnroc(parts[0], parts[1]))
            return validarIExecutarEnroc(parts[0], parts[1]);

        if (!validarPeçaOrigen(filaOrigen, colOrigen))
            return false;
        if (!validarPeçaDesti(filaOrigen, colOrigen, filaDesti, colDesti))
            return false;

        char peça = tauler[filaOrigen][colOrigen];

        if (!esMovimentValidPerPeça(peça, filaOrigen, colOrigen, filaDesti, colDesti, true))
            return false;

        if (!validarEscacDesprésMoviment(peça, filaOrigen, colOrigen, filaDesti, colDesti))
            return false;

        executarMoviment(peça, filaOrigen, colOrigen, filaDesti, colDesti);
        return true;
    }

    /**
     * Valida que hi hagi una peça del color correcte a la posició d'origen.
     * 
     * @param filaOrigen Índex de fila (0-7) de la posició d'origen
     * @param colOrigen  Índex de columna (0-7) de la posició d'origen
     * @return true si hi ha una peça del jugador actual a l'origen, false altrament
     * 
     *         Errors detectats:
     *         - Casella buida
     *         - Peça del color contrari
     */
    private boolean validarPeçaOrigen(int filaOrigen, int colOrigen) {
        char peça = tauler[filaOrigen][colOrigen];

        if (peça == BUIT) {
            System.out.println("No hi ha cap peça a la posició d'origen");
            return false;
        }

        if (tornBlanques && !esPeçaBlanca(peça)) {
            System.out.println("No pots moure peces negres");
            return false;
        }

        if (!tornBlanques && !esPeçaNegra(peça)) {
            System.out.println("No pots moure peces blanques");
            return false;
        }

        return true;
    }

    /**
     * Valida que el destí no contingui una peça del mateix color.
     * 
     * @param filaOrigen Índex de fila d'origen
     * @param colOrigen  Índex de columna d'origen
     * @param filaDesti  Índex de fila de destí
     * @param colDesti   Índex de columna de destí
     * @return true si el destí és vàlid (buit o amb peça rival), false si té peça
     *         pròpia
     * 
     *         Errors detectats:
     *         - Intent de capturar una peça del mateix color
     */
    private boolean validarPeçaDesti(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        char peçaDesti = tauler[filaDesti][colDesti];

        if (peçaDesti != BUIT) {
            if ((tornBlanques && esPeçaBlanca(peçaDesti)) || (!tornBlanques && esPeçaNegra(peçaDesti))) {
                System.out.println("No pots capturar una peça del teu propi color");
                return false;
            }
        }

        return true;
    }

    private boolean validarEscacDesprésMoviment(char peça, int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        char peçaCapturada = tauler[filaDesti][colDesti];
        tauler[filaDesti][colDesti] = peça;
        tauler[filaOrigen][colOrigen] = BUIT;

        boolean reiEnEscac = estaReiEnEscac(tornBlanques);
        tauler[filaOrigen][colOrigen] = peça;
        tauler[filaDesti][colDesti] = peçaCapturada;

        if (reiEnEscac) {
            System.out.println("Aquest moviment deixaria el teu rei en escac");
            return false;
        }

        return true;
    }

    // EXECUCIÓ DE MOVIMENTS

    /**
     * Executa un moviment vàlid al tauler, actualitzant l'estat del joc.
     * 
     * Aquest mètode realitza totes les operacions necessàries per completar un
     * moviment:
     * - Captura de peces enemigues
     * - Promoció de peons
     * - Execució d'enrocs
     * - Actualització de l'historial
     * - Control de peces mogudes (per enrocs futurs)
     * 
     * @param filaOrigen Índex de fila d'origen (0-7)
     * @param colOrigen  Índex de columna d'origen (0-7)
     * @param filaDesti  Índex de fila de destí (0-7)
     * @param colDesti   Índex de columna de destí (0-7)
     * 
     *                   Operacions realitzades:
     *                   1. Captura: Si hi ha una peça enemiga al destí, s'afegeix a
     *                   la llista de captures
     *                   2. Promoció: Si un peó arriba a la última fila, es demana
     *                   la promoció
     *                   3. Enroc: Si és un moviment d'enroc, mou també la torre
     *                   corresponent
     *                   4. Actualització: Mou la peça i actualitza l'historial de
     *                   moviments
     *                   5. Control: Marca reis i torres com a moguts per prevenir
     *                   enrocs futurs
     * 
     *                   Format de l'historial:
     *                   - Normal: "e2-e4"
     *                   - Captura: "e4xd5 (peça capturada)"
     *                   - Enroc curt: "0-0"
     *                   - Enroc llarg: "0-0-0"
     *                   - Promoció: "e7-e8=Q"
     * 
     *                   Actualitza variables globals:
     *                   - reiBlancMogut / reiNegreMogut
     *                   - torreBlancaA8Moguda / torreBlancaH8Moguda /
     *                   torreNegraA1Moguda / torreNegraH1Moguda
     */
    private void executarMoviment(char peça, int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        char peçaCapturada = tauler[filaDesti][colDesti];

        if (peçaCapturada != BUIT) {
            if (tornBlanques) {
                pecesCapturadesBlanques.add(peçaCapturada);
            } else {
                pecesCapturadesNegres.add(peçaCapturada);
            }
        }

        tauler[filaDesti][colDesti] = peça;
        tauler[filaOrigen][colOrigen] = BUIT;

        actualitzarEstatEnroc(peça, filaOrigen, colOrigen);

        char tipusPeça = Character.toUpperCase(peça);
        if (tipusPeça == 'P') {
            if ((tornBlanques && filaDesti == 0) || (!tornBlanques && filaDesti == 7)) {
                char peçaPromocio = demanarPromocio(tornBlanques);
                tauler[filaDesti][colDesti] = peçaPromocio;
                System.out.println("Peó promocionat a " + peçaPromocio + "!");
            }
        }
    }

    private void actualitzarEstatEnroc(char peça, int filaOrigen, int colOrigen) {
        char tipusPeça = Character.toUpperCase(peça);

        if (tipusPeça == 'K') {
            if (tornBlanques) {
                reiBlancMogut = true;
            } else {
                reiNegreMogut = true;
            }
        }

        if (tipusPeça == 'T') {
            if (tornBlanques) {
                if (filaOrigen == 7 && colOrigen == 0)
                    torreBlancaA8Moguda = true;
                if (filaOrigen == 7 && colOrigen == 7)
                    torreBlancaH8Moguda = true;
            } else {
                if (filaOrigen == 0 && colOrigen == 0)
                    torreNegraA1Moguda = true;
                if (filaOrigen == 0 && colOrigen == 7)
                    torreNegraH1Moguda = true;
            }
        }
    }

    // REGLES DE PECES

    /**
     * Valida si un moviment és vàlid segons les regles de la peça.
     * Delega la validació al mètode específic de cada tipus de peça.
     * 
     * @param peça          Caràcter que representa la peça ('P','T','C','A','Q','K'
     *                      majúscula=blanc, minúscula=negre)
     * @param filaOrigen    Índex de fila d'origen (0-7)
     * @param colOrigen     Índex de columna d'origen (0-7)
     * @param filaDesti     Índex de fila de destí (0-7)
     * @param colDesti      Índex de columna de destí (0-7)
     * @param mostrarErrors Si true, mostra missatges d'error per consola
     * @return true si el moviment segueix les regles de la peça, false altrament
     */
    private boolean esMovimentValidPerPeça(char peça, int filaOrigen, int colOrigen, int filaDesti, int colDesti,
            boolean mostrarErrors) {
        char tipusPeça = Character.toUpperCase(peça);

        switch (tipusPeça) {
            case 'P':
                return esMovimentValidPeo(peça, filaOrigen, colOrigen, filaDesti, colDesti, mostrarErrors);
            case 'T':
                return esMovimentValidTorre(filaOrigen, colOrigen, filaDesti, colDesti, mostrarErrors);
            case 'C':
                return esMovimentValidCavall(filaOrigen, colOrigen, filaDesti, colDesti, mostrarErrors);
            case 'A':
                return esMovimentValidAlfil(filaOrigen, colOrigen, filaDesti, colDesti, mostrarErrors);
            case 'Q':
                return esMovimentValidReina(filaOrigen, colOrigen, filaDesti, colDesti, mostrarErrors);
            case 'K':
                return esMovimentValidRei(filaOrigen, colOrigen, filaDesti, colDesti, mostrarErrors);
            default:
                return false;
        }
    }

    /**
     * Valida el moviment d'un peó segons les regles dels escacs.
     * 
     * @param peo           Caràcter del peó ('P' blanc, 'p' negre)
     * @param filaOrigen    Índex de fila d'origen
     * @param colOrigen     Índex de columna d'origen
     * @param filaDesti     Índex de fila de destí
     * @param colDesti      Índex de columna de destí
     * @param mostrarErrors Si true, mostra missatges d'error
     * @return true si el moviment és vàlid, false altrament
     * 
     *         Regles implementades:
     *         - Avançar 1 casella endavant si està buida
     *         - Avançar 2 caselles des de posició inicial si camí lliure
     *         - Capturar en diagonal només si hi ha peça rival
     *         - No pot moure enrere ni lateralment
     */
    private boolean esMovimentValidPeo(char peo, int filaOrigen, int colOrigen, int filaDesti, int colDesti,
            boolean mostrarErrors) {
        boolean esBlanc = Character.isUpperCase(peo);
        int direccio = esBlanc ? -1 : 1; // Els peons blancs es mouen cap amunt (direcció -1), els negres cap avall (+1)
        int filaInicial = esBlanc ? 6 : 1;

        // Moviment endavant una casella
        if (colOrigen == colDesti && filaDesti == filaOrigen + direccio) {
            if (tauler[filaDesti][colDesti] == BUIT) {
                return true;
            }
        }

        // Moviment endavant dues caselles des de la posició inicial
        if (colOrigen == colDesti && filaOrigen == filaInicial && filaDesti == filaOrigen + (2 * direccio)) {
            if (tauler[filaDesti][colDesti] == BUIT && tauler[filaOrigen + direccio][colOrigen] == BUIT) {
                return true;
            }
        }

        // Captura en diagonal
        if (Math.abs(colDesti - colOrigen) == 1 && filaDesti == filaOrigen + direccio) {
            if (tauler[filaDesti][colDesti] != BUIT) {
                // Hi ha una peça enemiga
                return true;
            }
        }

        if (mostrarErrors)
            System.out.println("Moviment no vàlid per al peó");
        return false;
    }

    /**
     * Valida el moviment d'una torre segons les regles dels escacs.
     * 
     * @param filaOrigen    Índex de fila d'origen
     * @param colOrigen     Índex de columna d'origen
     * @param filaDesti     Índex de fila de destí
     * @param colDesti      Índex de columna de destí
     * @param mostrarErrors Si true, mostra missatges d'error
     * @return true si el moviment és vàlid, false altrament
     * 
     *         Regles implementades:
     *         - Moviment horitzontal o vertical
     *         - Camí lliure (sense peces bloquejant)
     */
    private boolean esMovimentValidTorre(int filaOrigen, int colOrigen, int filaDesti, int colDesti,
            boolean mostrarErrors) {
        // La torre es mou només horitzontalment o verticalment
        if (filaOrigen != filaDesti && colOrigen != colDesti) {
            if (mostrarErrors) {
                System.out.println("La torre només es pot moure horitzontalment o verticalment");
            }
            return false;
        }

        // Comprovar que no hi hagi cap peça al camí
        if (!camiLliure(filaOrigen, colOrigen, filaDesti, colDesti)) {
            if (mostrarErrors) {
                System.out.println("Hi ha peces bloquejant el camí de la torre");
            }
            return false;
        }

        return true;
    }

    /**
     * Valida el moviment d'un cavall segons les regles dels escacs.
     * 
     * @param filaOrigen    Índex de fila d'origen
     * @param colOrigen     Índex de columna d'origen
     * @param filaDesti     Índex de fila de destí
     * @param colDesti      Índex de columna de destí
     * @param mostrarErrors Si true, mostra missatges d'error
     * @return true si el moviment és vàlid, false altrament
     * 
     *         Regles implementades:
     *         - Moviment en forma de L: 2 caselles en una direcció + 1 en l'altra
     *         - Pot saltar per sobre d'altres peces
     */
    private boolean esMovimentValidCavall(int filaOrigen, int colOrigen, int filaDesti, int colDesti,
            boolean mostrarErrors) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);

        // El cavall es mou en forma de "L": 2 caselles en una direcció i 1 en l'altra
        if ((deltaFila == 2 && deltaCol == 1) || (deltaFila == 1 && deltaCol == 2)) {
            return true;
        }

        if (mostrarErrors)
            System.out.println("Moviment no vàlid per al cavall (ha de moure's en forma de L)");
        return false;
    }

    /**
     * Valida el moviment d'un alfil segons les regles dels escacs.
     * 
     * @param filaOrigen    Índex de fila d'origen
     * @param colOrigen     Índex de columna d'origen
     * @param filaDesti     Índex de fila de destí
     * @param colDesti      Índex de columna de destí
     * @param mostrarErrors Si true, mostra missatges d'error
     * @return true si el moviment és vàlid, false altrament
     * 
     *         Regles implementades:
     *         - Moviment diagonal
     *         - Camí lliure (sense peces bloquejant)
     */
    private boolean esMovimentValidAlfil(int filaOrigen, int colOrigen, int filaDesti, int colDesti,
            boolean mostrarErrors) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);

        // L'alfil es mou només en diagonal
        if (deltaFila != deltaCol) {
            if (mostrarErrors) {
                System.out.println("L'alfil només es pot moure en diagonal");
            }
            return false;
        }

        // Comprovar que no hi hagi cap peça al camí
        if (!camiLliure(filaOrigen, colOrigen, filaDesti, colDesti)) {
            if (mostrarErrors) {
                System.out.println("Hi ha peces bloquejant el camí de l'alfil");
            }
            return false;
        }

        return true;

    }

    /**
     * Valida si un moviment de la reina és correcte segons les regles d'escacs.
     * 
     * La reina es pot moure en qualsevol direcció (vertical, horitzontal o
     * diagonal)
     * qualsevol nombre de caselles, sempre que el camí estigui lliure.
     * 
     * @param filaOrigen Índex de fila d'origen (0-7)
     * @param colOrigen  Índex de columna d'origen (0-7)
     * @param filaDesti  Índex de fila de destí (0-7)
     * @param colDesti   Índex de columna de destí (0-7)
     * @return true si el moviment és vàlid segons les regles de la reina, false
     *         altrament
     * 
     *         Validacions realitzades:
     *         - Verifica que el moviment sigui en línia recta (mateix fila o
     *         columna) o diagonal
     *         - Comprova que el camí entre origen i destí estigui lliure de peces
     */
    private boolean esMovimentValidReina(int filaOrigen, int colOrigen, int filaDesti, int colDesti,
            boolean mostrarErrors) {
        // La reina es mou com una torre o com un alfil
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);

        // Moviment de torre (horitzontal o vertical)
        boolean movimentTorre = (filaOrigen == filaDesti || colOrigen == colDesti);

        // Moviment d'alfil (diagonal)
        boolean movimentAlfil = (deltaFila == deltaCol);

        if (!movimentTorre && !movimentAlfil) {
            System.out.println("La reina es pot moure horitzontalment, verticalment o en diagonal");
            return false;
        }

        // Comprovar que no hi hagi cap peça al camí
        if (!camiLliure(filaOrigen, colOrigen, filaDesti, colDesti)) {
            if (mostrarErrors) {
                System.out.println("Hi ha peces bloquejant el camí de la reina");
            }
            return false;
        }

        return true;
    }

    /**
     * Valida si un moviment del rei és correcte segons les regles d'escacs.
     * 
     * El rei es pot moure una casella en qualsevol direcció (vertical, horitzontal
     * o diagonal).
     * També gestiona els moviments especials d'enroc (curt i llarg).
     * 
     * @param filaOrigen  Índex de fila d'origen (0-7)
     * @param colOrigen   Índex de columna d'origen (0-7)
     * @param filaDesti   Índex de fila de destí (0-7)
     * @param colDesti    Índex de columna de destí (0-7)
     * @param esSimulacio true si és una simulació (per comprovar escac), false si
     *                    és un moviment real
     * @return true si el moviment és vàlid segons les regles del rei, false
     *         altrament
     * 
     *         Validacions realitzades:
     *         - Moviment normal: una casella en qualsevol direcció
     *         - Enroc curt: rei es mou 2 caselles cap a la dreta (columna e→g)
     *         - Enroc llarg: rei es mou 2 caselles cap a l'esquerra (columna e→c)
     */
    private boolean esMovimentValidRei(int filaOrigen, int colOrigen, int filaDesti, int colDesti,
            boolean mostrarErrors) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);

        // El rei es mou una casella en qualsevol direcció
        if (deltaFila <= 1 && deltaCol <= 1) {
            return true;
        }

        if (mostrarErrors)
            System.out.println("El rei només es pot moure una casella en qualsevol direcció");
        return false;
    }

    // DETECCIÓ D'ATAC

    /**
     * Determina si una peça pot atacar una casella determinada del tauler.
     * 
     * Aquest mètode és fonamental per comprovar si el rei està en escac i per
     * validar els moviments d'enroc. Verifica si una peça en una posició pot
     * atacar (capturar) una altra casella segons les seves regles de moviment.
     * 
     * @param peça         Caràcter que representa la peça ('P','T','C','A','Q','K'
     *                     o minúscules)
     * @param filaOrigen   Índex de fila on es troba la peça atacant (0-7)
     * @param colOrigen    Índex de columna on es troba la peça atacant (0-7)
     * @param filaObjectiu Índex de fila de la casella a atacar (0-7)
     * @param colObjectiu  Índex de columna de la casella a atacar (0-7)
     * @return true si la peça pot atacar la casella objectiu, false altrament
     * 
     *         Lògica d'atac per peça:
     *         - Peó: Pot atacar en diagonal (una casella)
     *         - Torre: Pot atacar en línia recta si el camí està lliure
     *         - Alfil: Pot atacar en diagonal si el camí està lliure
     *         - Cavall: Pot atacar en "L" (ignora peces intermèdies)
     *         - Reina: Pot atacar com torre o alfil
     *         - Rei: Pot atacar una casella adjacent en qualsevol direcció
     */
    private boolean potAtacar(char peça, int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        char tipusPeça = Character.toUpperCase(peça);

        switch (tipusPeça) {
            case 'P':
                return potAtacarPeo(peça, filaOrigen, colOrigen, filaDesti, colDesti);
            case 'T':
                return potAtacarTorre(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'C':
                return potAtacarCavall(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'A':
                return potAtacarAlfil(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'Q':
                return potAtacarReina(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'K':
                return potAtacarRei(filaOrigen, colOrigen, filaDesti, colDesti);
            default:
                return false;
        }
    }

    private boolean potAtacarPeo(char peo, int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        boolean esBlanc = Character.isUpperCase(peo);
        int direccio = esBlanc ? -1 : 1;

        // Peó només ataca en diagonal
        if (Math.abs(colDesti - colOrigen) == 1 && filaDesti == filaOrigen + direccio) {
            return true;
        }

        return false;
    }

    private boolean potAtacarTorre(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        if (filaOrigen != filaDesti && colOrigen != colDesti) {
            return false;
        }

        return camiLliure(filaOrigen, colOrigen, filaDesti, colDesti);
    }

    private boolean potAtacarCavall(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);

        return (deltaFila == 2 && deltaCol == 1) || (deltaFila == 1 && deltaCol == 2);
    }

    private boolean potAtacarAlfil(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);

        if (deltaFila != deltaCol) {
            return false;
        }

        return camiLliure(filaOrigen, colOrigen, filaDesti, colDesti);
    }

    private boolean potAtacarReina(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);

        boolean movimentTorre = (filaOrigen == filaDesti || colOrigen == colDesti);
        boolean movimentAlfil = (deltaFila == deltaCol);

        if (!movimentTorre && !movimentAlfil) {
            return false;
        }

        return camiLliure(filaOrigen, colOrigen, filaDesti, colDesti);
    }

    private boolean potAtacarRei(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);

        return deltaFila <= 1 && deltaCol <= 1;
    }

    // DETECCIÓ D'ESCAC I MAT

    /**
     * Determina si el rei d'un color està en escac.
     * 
     * @param colorBlanc true per comprovar el rei blanc, false pel rei negre
     * @return true si el rei està amenaçat per una peça rival, false altrament
     * 
     *         Algorisme:
     *         1. Localitza la posició del rei del color especificat
     *         2. Comprova si alguna peça rival pot capturar-lo
     */
    private boolean estaReiEnEscac(boolean colorBlanc) {
        // 1. Trobar la posició del rei
        char rei = colorBlanc ? REIBLANC : REINEGRE;
        int filaRei = -1, colRei = -1;

        for (int i = 0; i < MIDA_TAULER; i++) {
            for (int j = 0; j < MIDA_TAULER; j++) {
                if (tauler[i][j] == rei) {
                    filaRei = i;
                    colRei = j;
                    break;
                }
            }
            if (filaRei != -1)
                break;
        }

        // Retornar false si no es troba el rei (no hauria de passar)
        if (filaRei == -1) {
            return false;
        }

        // 2. Comprovar si alguna peça enemiga pot atacar el rei
        for (int i = 0; i < MIDA_TAULER; i++) {
            for (int j = 0; j < MIDA_TAULER; j++) {
                char peça = tauler[i][j];

                if (peça == BUIT)
                    continue;

                boolean esPeçaEnemiga = colorBlanc ? esPeçaNegra(peça) : esPeçaBlanca(peça);
                if (!esPeçaEnemiga)
                    continue;

                if (potAtacar(peça, i, j, filaRei, colRei)) {
                    return true; // El rei està en escac
                }
            }
        }

        return false;
    }

    /**
     * Determina si un jugador està en escac i mat.
     * 
     * @param colorBlanc true per comprovar les blanques, false per les negres
     * @return true si està en escac i mat, false altrament
     * 
     *         Condicions:
     *         - El rei està en escac
     *         - No hi ha cap moviment legal que tregui el rei de l'escac
     */
    private boolean esEscacIMat(boolean colorBlanc) {
        return estaReiEnEscac(colorBlanc) && !teMovimentsLegals(colorBlanc);
    }

    private boolean esStalemate(boolean colorBlanc) {
        return !estaReiEnEscac(colorBlanc) && !teMovimentsLegals(colorBlanc);
    }

    // ENROCS

    private boolean esIntentEnroc(String origen, String desti) {
        if (tornBlanques) {
            return origen.equals("e8") && (desti.equals("g8") || desti.equals("c8"));
        } else {
            return origen.equals("e1") && (desti.equals("g1") || desti.equals("c1"));
        }
    }

    /**
     * Valida si un moviment d'enroc és legal segons les regles d'escacs.
     * 
     * L'enroc és un moviment especial que involucra el rei i una torre.
     * Hi ha dues variants: enroc curt (rei cap a la dreta) i enroc llarg (rei cap a
     * l'esquerra).
     * 
     * @param filaRei     Fila on es troba el rei (7 per blanques, 0 per negres)
     * @param esEnrocCurt true per enroc curt (rei cap a h), false per enroc llarg
     *                    (rei cap a a)
     * @param esBlanc     true si és el rei blanc, false si és el rei negre
     * @return true si l'enroc és vàlid, false si no es pot realitzar
     * 
     *         Condicions que invaliden l'enroc:
     *         1. El rei ja s'ha mogut prèviament
     *         2. La torre corresponent ja s'ha mogut
     *         3. El rei està actualment en escac
     *         4. Hi ha peces entre el rei i la torre
     *         5. El rei passaria per una casella atacada
     *         6. La casella de destí del rei està atacada
     * 
     *         Enroc curt (0-0):
     *         - Rei: e1→g1 (negres) o e8→g8 (blanques)
     *         - Torre: h1→f1 (negres) o h8→f8 (blanques)
     * 
     *         Enroc llarg (0-0-0):
     *         - Rei: e1→c1 (negres) o e8→c8 (blanques)
     *         - Torre: a1→d1 (negres) o a8→d8 (blanques)
     */
    private boolean validarIExecutarEnroc(String origen, String desti) {
        // L'enroc curt és quan el rei es mou 2 caselles cap a la torre del costat del
        // rei (columna h)
        // L'enroc llarg és quan el rei es mou 2 caselles cap a la torre del costat de
        // la dama (columna a)

        boolean esEnrocCurt = tornBlanques ? desti.equals("g8") : desti.equals("g1");

        if (!validarCondicionsPreviesEnroc(esEnrocCurt))
            return false;

        int fila = tornBlanques ? 7 : 0;

        if (!validarCamiEnroc(fila, esEnrocCurt))
            return false;

        executarEnroc(fila, esEnrocCurt);

        return true;
    }

    private boolean casellaBaixAtac(int fila, int col, boolean colorAtacat) {
        for (int i = 0; i < MIDA_TAULER; i++) {
            for (int j = 0; j < MIDA_TAULER; j++) {
                char peça = tauler[i][j];

                if (peça == BUIT)
                    continue;

                boolean esPeçaEnemiga = colorAtacat ? esPeçaNegra(peça) : esPeçaBlanca(peça);
                if (!esPeçaEnemiga)
                    continue;

                if (potAtacar(peça, i, j, fila, col))
                    return true;
            }
        }

        return false;
    }

    private boolean validarCondicionsPreviesEnroc(boolean esEnrocCurt) {
        if (tornBlanques) {
            if (reiBlancMogut) {
                System.out.println("No pots fer l'enroc: el rei ja s'ha mogut");
                return false;
            }
            if (esEnrocCurt && torreBlancaH8Moguda) {
                System.out.println("No pots fer l'enroc curt: la torre ja s'ha mogut");
                return false;
            }
            if (!esEnrocCurt && torreBlancaA8Moguda) {
                System.out.println("No pots fer l'enroc llarg: la torre ja s'ha mogut");
                return false;
            }
            if (estaReiEnEscac(true)) {
                System.out.println("No pots fer l'enroc: el rei està en escac");
                return false;
            }
        } else {
            if (reiNegreMogut) {
                System.out.println("No pots fer l'enroc: el rei ja s'ha mogut");
                return false;
            }
            if (esEnrocCurt && torreNegraH1Moguda) {
                System.out.println("No pots fer l'enroc curt: la torre ja s'ha mogut");
                return false;
            }
            if (!esEnrocCurt && torreNegraA1Moguda) {
                System.out.println("No pots fer l'enroc llarg: la torre ja s'ha mogut");
                return false;
            }
            if (estaReiEnEscac(false)) {
                System.out.println("No pots fer l'enroc: el rei està en escac");
                return false;
            }
        }
        return true;
    }

    private boolean validarCamiEnroc(int fila, boolean esEnrocCurt) {
        boolean colorAtacat = tornBlanques;

        if (esEnrocCurt) {
            if (tauler[fila][COLUMNA_TORRE_ENROC_CURT] != BUIT || tauler[fila][COLUMNA_REI_ENROC_CURT] != BUIT) {
                System.out.println("No pots fer l'enroc curt: hi ha peces bloquejant el camí");
                return false;
            }

            if (casellaBaixAtac(fila, COLUMNA_TORRE_ENROC_CURT, colorAtacat)
                    || casellaBaixAtac(fila, COLUMNA_REI_ENROC_CURT, colorAtacat)) {
                System.out.println("No pots fer l'enroc: el rei passaria per una casella amenaçada");
                return false;
            }

        } else {
            if (tauler[fila][COLUMNA_TORRE_ENROC_LLARG] != BUIT || tauler[fila][COLUMNA_REI_ENROC_LLARG] != BUIT
                    || tauler[fila][1] != BUIT) {
                System.out.println("No pots fer l'enroc llarg: hi ha peces bloquejant el camí");
                return false;
            }

            if (casellaBaixAtac(fila, COLUMNA_TORRE_ENROC_LLARG, colorAtacat)
                    || casellaBaixAtac(fila, COLUMNA_REI_ENROC_LLARG, colorAtacat)) {
                System.out.println("No pots fer l'enroc: el rei passaria per una casella amenaçada");
                return false;
            }
        }

        return true;
    }

    /**
     * Executa físicament un moviment d'enroc al tauler.
     * 
     * Mou tant el rei com la torre a les seves noves posicions segons
     * el tipus d'enroc (curt o llarg).
     * 
     * @param filaRei     Fila on es troba el rei (7 per blanques, 0 per negres)
     * @param esEnrocCurt true per enroc curt, false per enroc llarg
     * @param esBlanc     true si són peces blanques, false si són negres
     * 
     *                    Moviments realitzats:
     * 
     *                    Enroc curt:
     *                    - Rei: columna 4 → columna 6
     *                    - Torre: columna 7 → columna 5
     * 
     *                    Enroc llarg:
     *                    - Rei: columna 4 → columna 2
     *                    - Torre: columna 0 → columna 3
     * 
     *                    Nota: Aquest mètode només s'executa després que
     *                    esEnrocValid()
     *                    hagi confirmat que l'enroc és legal. No fa validacions.
     */
    private void executarEnroc(int fila, boolean esEnrocCurt) {
        char rei = tornBlanques ? REIBLANC : REINEGRE;
        char torre = tornBlanques ? TORREBLANC : TORRENEGRE;

        if (esEnrocCurt) {
            tauler[fila][COLUMNA_REI_INICIAL] = BUIT;
            tauler[fila][COLUMNA_REI_ENROC_CURT] = rei;
            tauler[fila][COLUMNA_TORRE_REI] = BUIT;
            tauler[fila][COLUMNA_TORRE_ENROC_CURT] = torre;
            System.out.println("Enroc curt realitzat!");
        } else {
            tauler[fila][COLUMNA_REI_INICIAL] = BUIT;
            tauler[fila][COLUMNA_REI_ENROC_LLARG] = rei;
            tauler[fila][COLUMNA_TORRE_DAMA] = BUIT;
            tauler[fila][COLUMNA_TORRE_ENROC_LLARG] = torre;
            System.out.println("Enroc llarg realitzat!");
        }

        if (tornBlanques) {
            reiBlancMogut = true;
            if (esEnrocCurt)
                torreBlancaH8Moguda = true;
            else
                torreBlancaA8Moguda = true;
        } else {
            reiNegreMogut = true;
            if (esEnrocCurt)
                torreNegraH1Moguda = true;
            else
                torreNegraA1Moguda = true;
        }
    }

    // UTILITATS

    /**
     * Converteix una coordenada en notació d'escacs (ex: "e2") a índexs del tauler.
     * 
     * @param coord String amb la coordenada en format lletra+número (ex: "e2",
     *              "h8")
     * @return Array d'enters [fila, columna] amb els índexs (0-7)
     * 
     *         Conversió:
     *         - Lletra 'a'-'h' -> columna 0-7
     *         - Número '1'-'8' -> fila 0-7
     * 
     *         Exemple: "e2" -> [6, 4] (fila 7 en notació, índex 6 en array)
     */
    private int[] parsejarCoordenada(String coord) {
        char columna = coord.charAt(0);
        char fila = coord.charAt(1);

        int filaIndex = fila - '1';
        int colIndex = columna - 'a';

        return new int[] { filaIndex, colIndex };
    }

    private boolean esPeçaBlanca(char peça) {
        return Character.isUpperCase(peça);
    }

    private boolean esPeçaNegra(char peça) {
        return Character.isLowerCase(peça);
    }

    /**
     * Comprova si el camí entre origen i destí està lliure de peces.
     * Útil per torres, alfils i reines que no poden saltar peces.
     * 
     * @param filaOrigen Índex de fila d'origen
     * @param colOrigen  Índex de columna d'origen
     * @param filaDesti  Índex de fila de destí
     * @param colDesti   Índex de columna de destí
     * @return true si el camí està lliure, false si hi ha peces bloquejant
     * 
     *         Nota: No inclou la casella de destí en la comprovació
     */
    private boolean camiLliure(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        // Utilitzem Integer.compare per obtenir la direcció del moviment (-1, 0, o 1)
        int deltaFila = Integer.compare(filaDesti - filaOrigen, 0);
        int deltaCol = Integer.compare(colDesti - colOrigen, 0);

        int filaActual = filaOrigen + deltaFila;
        int colActual = colOrigen + deltaCol;

        // Recórrer el camí fins arribar al destí (sense incloure'l)
        while (filaActual != filaDesti || colActual != colDesti) {
            if (tauler[filaActual][colActual] != BUIT) {
                return false; // Hi ha una peça bloquejant
            }
            filaActual += deltaFila;
            colActual += deltaCol;
        }

        return true;
    }

    private boolean teMovimentsLegals(boolean colorBlanc) {
        for (int filaOrigen = 0; filaOrigen < MIDA_TAULER; filaOrigen++) {
            for (int colOrigen = 0; colOrigen < MIDA_TAULER; colOrigen++) {
                char peça = tauler[filaOrigen][colOrigen];

                if (peça == BUIT)
                    continue;

                boolean esPeçaDelColor = colorBlanc ? esPeçaBlanca(peça) : esPeçaNegra(peça);
                if (!esPeçaDelColor)
                    continue;

                for (int filaDesti = 0; filaDesti < MIDA_TAULER; filaDesti++) {
                    for (int colDesti = 0; colDesti < MIDA_TAULER; colDesti++) {
                        if (filaOrigen == filaDesti && colOrigen == colDesti)
                            continue;

                        char peçaDesti = tauler[filaDesti][colDesti];
                        if (peçaDesti != BUIT) {
                            boolean peçaDestiMateixColor = colorBlanc ? esPeçaBlanca(peçaDesti)
                                    : esPeçaNegra(peçaDesti);
                            if (peçaDestiMateixColor)
                                continue;
                        }

                        if (!esMovimentValidPerPeça(peça, filaOrigen, colOrigen, filaDesti, colDesti, false))
                            continue;

                        char peçaCapturada = tauler[filaDesti][colDesti];
                        tauler[filaDesti][colDesti] = peça;
                        tauler[filaOrigen][colOrigen] = BUIT;

                        boolean reiEnEscac = estaReiEnEscac(colorBlanc);

                        tauler[filaOrigen][colOrigen] = peça;
                        tauler[filaDesti][colDesti] = peçaCapturada;

                        if (!reiEnEscac)
                            return true; // Té almenys un moviment legal
                    }
                }
            }
        }

        return false; // No té moviments legals
    }

    /**
     * Demana al jugador a quina peça vol promocionar el seu peó.
     * 
     * @param esBlanc true si és un peó blanc, false si és negre
     * @return Caràcter de la peça escollida ('Q','T','A','C' o minúscula si és
     *         negre)
     * 
     *         Opcions disponibles:
     *         - Q: Reina
     *         - T: Torre
     *         - A: Alfil
     *         - C: Cavall
     */
    private char demanarPromocio(boolean esBlanc) {
        System.out.println("\nPROMOCIÓ DEL PEÓ");
        System.out.println("El teu peó ha arribat a la última fila!");
        System.out.println("A quina peça vols promocionar?");
        System.out.println("Q - Reina");
        System.out.println("T - Torre");
        System.out.println("A - Alfil");
        System.out.println("C - Cavall");

        char peçaEscollida = ' ';
        boolean peçaValida = false;

        while (!peçaValida) {
            String input = demanarString("Escull una peça (Q/T/A/C): ").toUpperCase();

            if (input.length() != 1) {
                System.out.println("Introdueix només una lletra.");
                continue;
            }

            char lletra = input.charAt(0);

            if (lletra == 'Q' || lletra == 'T' || lletra == 'A' || lletra == 'C') {
                if (esBlanc) {
                    peçaEscollida = lletra;
                } else {
                    peçaEscollida = Character.toLowerCase(lletra);
                }
                peçaValida = true;
            } else {
                System.out.println("Opció no vàlida. Escull Q, T, A o C.");
            }
        }

        return peçaEscollida;
    }

    // FI DE JOC

    private void finalitzarJoc() {
        if (guanyador != null) {
            System.out.println(guanyador + " guanya la partida!");
        } else {
            System.out.println("Partida acabada en taules");
        }

        System.out.println("\n=== Fi de la partida ===");

        mostrarHistorialMoviments();

        System.out.println("=== RESUM DE PECES CAPTURADES ===");
        mostrarPecesCapturades();

        boolean tornarAJugar = gestionarSiNo("\nVoleu tornar a jugar? ");

        if (tornarAJugar) {
            boolean mantenirJugadors = gestionarSiNo("Voleu mantenir els mateixos jugadors? ");

            if (mantenirJugadors) {
                demanarJugadors = false;

                // Només intercanviar jugadors si hi ha un guanyador (no en cas de taules)
                if (guanyador != null && !guanyador.equals(jugadorBlanques)) {
                    String aux = jugadorBlanques;
                    jugadorBlanques = jugadorNegres;
                    jugadorNegres = aux;
                }
            } else {
                demanarJugadors = true;
            }

            jocEnCurs = true;
            tornBlanques = true;
            historialMoviments.clear();
            pecesCapturadesBlanques.clear();
            pecesCapturadesNegres.clear();
            guanyador = null;
            reiBlancMogut = false;
            reiNegreMogut = false;
            torreBlancaA8Moguda = false;
            torreBlancaH8Moguda = false;
            torreNegraA1Moguda = false;
            torreNegraH1Moguda = false;
            iniciar();

        } else {
            System.out.println("\nGràcies per jugar!");
            scanner.close();
        }
    }
}