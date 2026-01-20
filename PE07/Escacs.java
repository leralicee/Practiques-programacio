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

    private void bucleJoc() {
        while (jocEnCurs) {
            if (comprovarEstatPartida()) return;

            mostrarTorn();
            String moviment = demanarMoviment();
            processarMoviment(moviment);
        }
    }

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

    private boolean gestionarSiNo(String missatge) {
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

    private boolean esMovimentValidPerPeça(char peça, int filaOrigen, int colOrigen, int filaDesti, int colDesti, boolean mostrarErrors) {
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

    private boolean esMovimentValidPeo(char peo, int filaOrigen, int colOrigen, int filaDesti, int colDesti, boolean mostrarErrors) {
        boolean esBlanc = Character.isUpperCase(peo);
        int direccio = esBlanc ? -1 : 1; // Blanques -1, negres +1
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

        if (mostrarErrors) System.out.println("Moviment no vàlid per al peó");
        return false;
    }

    private boolean esMovimentValidTorre(int filaOrigen, int colOrigen, int filaDesti, int colDesti, boolean mostrarErrors) {
        // La torre es mou només horitzontalment o verticalment
        if (filaOrigen != filaDesti && colOrigen != colDesti) {
            System.out.println("La torre només es pot moure horitzontalment o verticalment");
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

    private boolean esMovimentValidCavall(int filaOrigen, int colOrigen, int filaDesti, int colDesti, boolean mostrarErrors) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);

        // El cavall es mou en forma de "L": 2 caselles en una direcció i 1 en l'altra
        if ((deltaFila == 2 && deltaCol == 1) || (deltaFila == 1 && deltaCol == 2)) {
            return true;
        }

        if (mostrarErrors) System.out.println("Moviment no vàlid per al cavall (ha de moure's en forma de L)");
        return false;
    }

    private boolean esMovimentValidAlfil(int filaOrigen, int colOrigen, int filaDesti, int colDesti, boolean mostrarErrors) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);

        // L'alfil es mou només en diagonal
        if (deltaFila != deltaCol) {
            System.out.println("L'alfil només es pot moure en diagonal");
            return false;
        }

        // Comprovar que no hi hagi cap peça al camí
        if (filaOrigen != filaDesti && colOrigen != colDesti) {
            if (mostrarErrors) {
                System.out.println("La torre només es pot moure horitzontalment o verticalment");
            }
            return false;
        }   

        return true;
    }

    private boolean esMovimentValidReina(int filaOrigen, int colOrigen, int filaDesti, int colDesti, boolean mostrarErrors) {
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

    private boolean esMovimentValidRei(int filaOrigen, int colOrigen, int filaDesti, int colDesti, boolean mostrarErrors) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);

        // El rei es mou una casella en qualsevol direcció
        if (deltaFila <= 1 && deltaCol <= 1) {
            return true;
        }

        if (mostrarErrors) System.out.println("El rei només es pot moure una casella en qualsevol direcció");
        return false;
    }


    // DETECCIÓ D'ATAC

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

    private boolean validarIExecutarEnroc(String origen, String desti) {
        boolean esEnrocCurt = tornBlanques ? desti.equals("g8") : desti.equals("g1");
    
        if (!validarCondicionsPreviesEnroc(esEnrocCurt)) return false;
    
        int fila = tornBlanques ? 7 : 0;
    
        if (!validarCamiEnroc(fila, esEnrocCurt)) return false;
    
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

            if (casellaBaixAtac(fila, COLUMNA_TORRE_ENROC_CURT, colorAtacat) || casellaBaixAtac(fila, COLUMNA_REI_ENROC_CURT, colorAtacat)) {
                System.out.println("No pots fer l'enroc: el rei passaria per una casella amenaçada");
                return false;
            }

        } else {
            if (tauler[fila][COLUMNA_TORRE_ENROC_LLARG] != BUIT || tauler[fila][COLUMNA_REI_ENROC_LLARG] != BUIT || tauler[fila][1] != BUIT) {
                System.out.println("No pots fer l'enroc llarg: hi ha peces bloquejant el camí");
                return false;
            }

            if (casellaBaixAtac(fila, COLUMNA_TORRE_ENROC_LLARG, colorAtacat) || casellaBaixAtac(fila, COLUMNA_REI_ENROC_LLARG, colorAtacat)) {
                System.out.println("No pots fer l'enroc: el rei passaria per una casella amenaçada");
                return false;
            }
        }

        return true;
    }

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


   //  UTILITATS

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

    private boolean camiLliure(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
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