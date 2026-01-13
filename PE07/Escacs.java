<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Escacs {
    private char[][] tauler;
    private String jugadorBlanques;
    private String jugadorNegres;
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
    
=======
public class Escacs {
>>>>>>> 2248721e94918acd8b44677f1bfc22b459f0a588
    public static void main(String[] args) {
        Escacs app = new Escacs();
        app.iniciar();
    }
<<<<<<< HEAD
    
    // Constructor
    public Escacs() {
        tauler = new char[8][8];
        historialMoviments = new ArrayList<>();
        scanner = new Scanner(System.in);
        tornBlanques = true; // blanques comencen
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
        
        System.out.println("\n" + jugadorBlanques + " jugarà amb les blanques (Majúscules)");
        System.out.println(jugadorNegres + " jugarà amb les negres (minúscules)\n");
    }
    
    private void inicialitzarTauler() {
    }
    
    private void bucleJoc() {
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
}
=======

    public void iniciar() {
        
    }
}
>>>>>>> 2248721e94918acd8b44677f1bfc22b459f0a588
