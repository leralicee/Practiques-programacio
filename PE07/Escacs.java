import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Escacs {
    private char[][] tauler;
    private String jugadorBlanques;
    private String jugadorNegres;
    private boolean jocEnCurs = true;
    private boolean tornBlanques; // true = blanques, false = negres
    private List<String> historialMoviments;
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

    public Escacs() {
        tauler = new char[8][8];
        historialMoviments = new ArrayList<>();
        scanner = new Scanner(System.in);
        tornBlanques = true; // blanques comencen
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

    public void iniciar() {
        System.out.println("=== JOC D'ESCACS ===\n");
        
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
            return;
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
        tauler[7][3] = REINABLANC;    // d8
        tauler[7][4] = REIBLANC;      // e8
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
    
    private void bucleJoc() {
        while (jocEnCurs){
            mostrarTorn();
            String moviment = demanarMoviment();
            processarMoviment(moviment);
        }
    }

    private void mostrarTorn() {
        mostrarTauler();

        if (tornBlanques) {
            System.out.println("Torn de " + jugadorBlanques + " (BLANQUES)");
        } else {
            System.out.println("Torn de " + jugadorNegres + " (NEGRES)");
        }
    }

    private String demanarMoviment() {
        String jugadorActual = tornBlanques ? jugadorBlanques : jugadorNegres;
        String moviment = demanarString(jugadorActual + ", introdueix el teu moviment o 'exit' per sortir: ");
        
        // Comprovar si abandona
        if (moviment.equalsIgnoreCase("exit")) {
            if (tornBlanques) {
                System.out.println(jugadorBlanques + " ha abandonat la partida. " + jugadorNegres + " guanya!");
            } else {
                System.out.println(jugadorNegres + " ha abandonat la partida. " + jugadorBlanques + " guanya!");
            }
            jocEnCurs = false;
            finalitzarJoc();
            return null;
        }
        return moviment;
    }

    private void processarMoviment(String moviment) {
        if (moviment == null) return; // El jugador ha abandonat

        boolean movimentValid = validarMoviment(moviment);
        if (movimentValid) {
            historialMoviments.add(moviment);
            tornBlanques = !tornBlanques;
        } else {
            System.out.println("Moviment no permès, torna-ho a intentar.");
        }
    }

    private boolean validarMoviment(String moviment) {
        // Validar moviment
        return true; //placeholder
    }

    private void finalitzarJoc() {
        System.out.println("\n=== Fi de la partida ===");
        // Mostrar historial de moviments
        // Demanar si volen tornar a jugar
    }
}