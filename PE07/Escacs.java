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

    public static void main(String[] args) {
        Escacs app = new Escacs();
        app.iniciar();
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

    public Escacs() {
        tauler = new char[8][8];
        historialMoviments = new ArrayList<>();
        pecesCapturadesBlanques = new ArrayList<>();
        pecesCapturadesNegres = new ArrayList<>();
        scanner = new Scanner(System.in);
        tornBlanques = true; // blanques comencen
        jocEnCurs = true;
        demanarJugadors = true;
    }

    public void iniciar() {
        System.out.println("\n=== JOC D'ESCACS ===\n");
        
        if (demanarJugadors) demanarNomsJugadors();
        
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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tauler[i][j] = BUIT;
            }
        }
        
        // Fila 1: Peces NEGRES
        tauler[0][0] = TORRENEGRE;    // a1
        tauler[0][1] = CAVALLNEGRE;   // b1
        tauler[0][2] = ALFILNEGRE;    // c1
        tauler[0][3] = REINANEGRE;    // d1
        tauler[0][4] = REINEGRE;      // e1
        tauler[0][5] = ALFILNEGRE;    // f1
        tauler[0][6] = CAVALLNEGRE;   // g1
        tauler[0][7] = TORRENEGRE;    // h1
        
        // Fila 2: Peons negres
        for (int j = 0; j < 8; j++) {
            tauler[1][j] = PEONEGRE;
        }
        
        // Fila 7: Peons blancs
        for (int j = 0; j < 8; j++) {
            tauler[6][j] = PEOBLANC;
        }
        
        // Fila 8: Peces BLANQUES
        tauler[7][0] = TORREBLANC;    // a8
        tauler[7][1] = CAVALLBLANC;   // b8
        tauler[7][2] = ALFILBLANC;    // c8
        tauler[7][3] = REIBLANC;      // d8
        tauler[7][4] = REINABLANC;    // e8
        tauler[7][5] = ALFILBLANC;    // f8
        tauler[7][6] = CAVALLBLANC;   // g8
        tauler[7][7] = TORREBLANC;    // h8
    
    }

    private void mostrarTauler() {
        System.out.println();
        
        // Mostrar coordenades de columnes (a-h)
        System.out.print("  ");
        for (char columna = 'a'; columna <= 'h'; columna++) {
            System.out.print(columna + " ");
        }
        System.out.println();
        
        for (int i = 0; i < 8; i++) {
            // Mostrar número de fila (1-8)
            System.out.print((i + 1) + " ");
            
            // Mostrar contingut de cada casella
            for (int j = 0; j < 8; j++) {
                System.out.print(tauler[i][j] + " ");
            }
            
            System.out.println();
        }
        
        System.out.println();
    }

    private void mostrarPecesCapturades() {
        System.out.print("Peces capturades per " + jugadorBlanques + ": ");
        if (pecesCapturadesBlanques.isEmpty()) {
            System.out.print("Cap");
        } else {
            for (char peca : pecesCapturadesBlanques) {
                System.out.print(peca + " ");
            }
        }
    
        System.out.println();
    
        System.out.print("Peces capturades per " + jugadorNegres + ": ");
        if (pecesCapturadesNegres.isEmpty()) {
            System.out.print("Cap");
        } else {
            for (char peca : pecesCapturadesNegres) {
                System.out.print(peca + " ");
            }
        }
        System.out.println();
    }
    
    private void bucleJoc() {
        while (jocEnCurs){
            mostrarTorn();
            String moviment = demanarMoviment();
            processarMoviment(moviment);
        }
    }

    private void mostrarTorn() {
        mostrarTauler();
        mostrarPecesCapturades();

        if (tornBlanques) {
            System.out.println("Torn de " + jugadorBlanques + " (BLANQUES)");
        } else {
            System.out.println("Torn de " + jugadorNegres + " (NEGRES)");
        }
    }

    private String demanarMoviment() {
        String jugadorActual = tornBlanques ? jugadorBlanques : jugadorNegres;
        String moviment = demanarString(jugadorActual + ", introdueix el teu moviment (ex: e2 e4) o 'exit' per sortir: ");
        
        // Comprovar si abandona
        if (moviment.equalsIgnoreCase("exit")) {
            if (tornBlanques) {
                System.out.println(jugadorBlanques + " ha abandonat la partida. ");
                guanyador = jugadorNegres;
            } else {
                System.out.println(jugadorNegres + " ha abandonat la partida. ");
                guanyador = jugadorBlanques;
            }
            jocEnCurs = false;
            finalitzarJoc();
            return null;
        }
        return moviment;
    }

    private void processarMoviment(String moviment) {
        if (moviment == null) return; // El jugador ha abandonat

        if (!validarEntradaMoviment(moviment)) return; // Tornar a demanar moviment

        if (!validarMoviment(moviment)) return; // Validar si el moviment és permès segons les regles

        // Si tot és correcte, actualitzar historial i canviar torn
        historialMoviments.add(moviment);
        tornBlanques = !tornBlanques;
    }

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

    private boolean validarMoviment(String moviment) {
        // 1. Parsejar coordenades
        String[] parts = moviment.trim().split("\\s+");
        int[] origen = parsejarCoordenada(parts[0]);
        int[] desti = parsejarCoordenada(parts[1]);
    
        int filaOrigen = origen[0];
        int colOrigen = origen[1];
        int filaDesti = desti[0];
        int colDesti = desti[1];

        // 2. Comprovar que hi ha una peça a l'origen
        char peça = tauler[filaOrigen][colOrigen];
        if (peça == BUIT) {
            System.out.println("No hi ha cap peça a la posició d'origen");
            return false;
        }

        // 3. Comprovar que la peça és del color correcte
        if (tornBlanques && !esPeçaBlanca(peça)) {
            System.out.println("No pots moure peces negres");
            return false;
        }

        if (!tornBlanques && !esPeçaNegra(peça)) {
            System.out.println("No pots moure peces blanques");
            return false;
        }

        // 4. Comprovar que no captures una peça del mateix color
        char peçaDesti = tauler[filaDesti][colDesti];
        if (peçaDesti != BUIT) {
            if ((tornBlanques && esPeçaBlanca(peçaDesti)) || (!tornBlanques && esPeçaNegra(peçaDesti))) {
                System.out.println("No pots capturar una peça del teu propi color");
                return false;
            }
        }

        // 5. Validar moviment segons el tipus de peça
        if (!esMovimentValidPerPeça(peça, filaOrigen, colOrigen, filaDesti, colDesti)) {
            return false;
        }

        // 6. Simular el moviment per comprovar si deixa el rei en escac
        char pecaCapturada = tauler[filaDesti][colDesti];
        tauler[filaDesti][colDesti] = peça;
        tauler[filaOrigen][colOrigen] = BUIT;
    
        boolean reiEnEscac = estaReiEnEscac(tornBlanques); // Comprovar si el rei propi està en escac després del moviment
    
        // Desfer el moviment temporal
        tauler[filaOrigen][colOrigen] = peça;
        tauler[filaDesti][colDesti] = pecaCapturada;
    
        if (reiEnEscac) {
            System.out.println("Aquest moviment deixaria el teu rei en escac");
            return false;
        }
    
        // Guardar la peça capturada si n'hi ha
        if (pecaCapturada != BUIT) {
            if (tornBlanques) {
                pecesCapturadesBlanques.add(pecaCapturada);
            } else {
                pecesCapturadesNegres.add(pecaCapturada);
            }
        }

        // 7. Si tot és correcte, executar el moviment definitivament
        tauler[filaDesti][colDesti] = peça;
        tauler[filaOrigen][colOrigen] = BUIT;
    
        return true;
    }

    // Converteix notació escacs a índexs de l'array
    private int[] parsejarCoordenada(String coord) {
        char columna = coord.charAt(0);
        char fila = coord.charAt(1);
    
        int filaIndex = fila - '1';
        int colIndex = columna - 'a';
    
        return new int[]{filaIndex, colIndex};
    }

    private boolean esPeçaBlanca(char peça) {
        return Character.isUpperCase(peça);
    }

    private boolean esPeçaNegra(char peça) {
        return Character.isLowerCase(peça);
    }

    private boolean estaReiEnEscac(boolean colorBlanc) {
        // 1. Trobar la posició del rei
        char rei = colorBlanc ? REIBLANC : REINEGRE;
        int filaRei = -1, colRei = -1;
    
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tauler[i][j] == rei) {
                    filaRei = i;
                    colRei = j;
                    break;
                }
            }
            if (filaRei != -1) break;
        }
    
        // Retornar false si no es troba el rei (no hauria de passar)
        if (filaRei == -1) {
            return false;
        }
    
        // 2. Comprovar si alguna peça enemiga pot atacar el rei
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char peça = tauler[i][j];
            
                if (peça == BUIT) continue;
            
                boolean esPeçaEnemiga = colorBlanc ? esPeçaNegra(peça) : esPeçaBlanca(peça);
                if (!esPeçaEnemiga) continue;
            
                if (potAtacar(peça, i, j, filaRei, colRei)) {
                    return true; // El rei està en escac
                }
            }
        }
    
        return false;
    }

    private boolean potAtacar(char peça, int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        char tipusPeça = Character.toUpperCase(peça);
    
        switch (tipusPeça) {
            case 'P': return potAtacarPeo(peça, filaOrigen, colOrigen, filaDesti, colDesti);
            case 'T': return potAtacarTorre(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'C': return potAtacarCavall(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'A': return potAtacarAlfil(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'Q': return potAtacarReina(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'K': return potAtacarRei(filaOrigen, colOrigen, filaDesti, colDesti);
            default: return false;
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

    private boolean esMovimentValidPerPeça(char peça, int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        char tipusPeça = Character.toUpperCase(peça);
    
        switch (tipusPeça) {
            case 'P': return esMovimentValidPeo(peça, filaOrigen, colOrigen, filaDesti, colDesti);
            case 'T': return esMovimentValidTorre(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'C': return esMovimentValidCavall(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'A': return esMovimentValidAlfil(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'Q': return esMovimentValidReina(filaOrigen, colOrigen, filaDesti, colDesti);
            case 'K': return esMovimentValidRei(filaOrigen, colOrigen, filaDesti, colDesti);
            default: return false;
        }
    }

    // PEÓ
    private boolean esMovimentValidPeo(char peo, int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
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
    
        System.out.println("Moviment no vàlid per al peó");
        return false;
    }

    // TORRE
    private boolean esMovimentValidTorre(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        // La torre es mou només horitzontalment o verticalment
        if (filaOrigen != filaDesti && colOrigen != colDesti) {
            System.out.println("La torre només es pot moure horitzontalment o verticalment");
            return false;
        }
    
        // Comprovar que no hi hagi cap peça al camí
        if (!camiLliure(filaOrigen, colOrigen, filaDesti, colDesti)) {
            System.out.println("Hi ha peces bloquejant el camí de la torre");
            return false;
        }

        return true;
    }

    // CAVALL
    private boolean esMovimentValidCavall(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);
    
        // El cavall es mou en forma de "L": 2 caselles en una direcció i 1 en l'altra
        if ((deltaFila == 2 && deltaCol == 1) || (deltaFila == 1 && deltaCol == 2)) {
            return true;
        }
    
        System.out.println("Moviment no vàlid per al cavall (ha de moure's en forma de L)");
        return false;
    }

    // ALFIL
    private boolean esMovimentValidAlfil(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);
    
        // L'alfil es mou només en diagonal
        if (deltaFila != deltaCol) {
            System.out.println("L'alfil només es pot moure en diagonal");
            return false;
        }
    
        // Comprovar que no hi hagi cap peça al camí
        if (!camiLliure(filaOrigen, colOrigen, filaDesti, colDesti)) {
            System.out.println("Hi ha peces bloquejant el camí de l'alfil");
            return false;
        }
    
        return true;
    }

    // REINA
    private boolean esMovimentValidReina(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
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
            System.out.println("Hi ha peces bloquejant el camí de la reina");
            return false;
        }
    
        return true;
    }

    // REI
    private boolean esMovimentValidRei(int filaOrigen, int colOrigen, int filaDesti, int colDesti) {
        int deltaFila = Math.abs(filaDesti - filaOrigen);
        int deltaCol = Math.abs(colDesti - colOrigen);
    
        // El rei es mou una casella en qualsevol direcció
        if (deltaFila <= 1 && deltaCol <= 1) {
            return true;
        }
    
        System.out.println("El rei només es pot moure una casella en qualsevol direcció");
        return false;
    }

    // Comprova que no hi ha cap peça bloquejant el camí entre origen i destí
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

    private void finalitzarJoc() {
        System.out.println(guanyador + " guanya la partida!");
        System.out.println("\n=== Fi de la partida ===");
        
        // Mostrar historial de moviments i peces capturades
        // Per implementar

        boolean tornarAJugar = gestionarSiNo("\nVoleu tornar a jugar? ");
        
        if (tornarAJugar) {
            boolean mantenirJugadors = gestionarSiNo("Voleu mantenir els mateixos jugadors? ");
            
            if (mantenirJugadors) {
                demanarJugadors = false;
                
                if (!guanyador.equals(jugadorBlanques)) {
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
            iniciar();

        } else {
            System.out.println("\nGràcies per jugar!");
            scanner.close();
        }
    }
}