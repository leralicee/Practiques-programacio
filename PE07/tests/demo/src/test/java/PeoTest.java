import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Proves unitàries per validar el moviment del Peó
 */
@DisplayName("Proves del moviment del Peó")
class PeoTest {
    
    private Escacs joc;
    
    @BeforeEach
    void setUp() {
        joc = new Escacs();
        joc.inicialitzarTaulerBuit();
    }
    
    @Test
    @DisplayName("Test 1: Peó blanc pot avançar 1 casella si està buida")
    void testPeoBlanc1CasellaEndavant() {
        // Configurar: Peó blanc a e7
        char[][] tauler = joc.getTauler();
        tauler[6][4] = 'P'; // e7 (fila 7, columna e)
        joc.setTornBlanques(true);
        
        // Executar: Intentar moure de e7 a e6
        boolean resultat = joc.esMovimentValidPerPeça('P', 6, 4, 5, 4, true);
        
        // Verificar
        assertTrue(resultat, "El peó blanc hauria de poder avançar 1 casella endavant si està buida");
    }
    
    @Test
    @DisplayName("Test 2: Peó blanc pot avançar 2 caselles des de la posició inicial si el camí està lliure")
    void testPeoBlanc2CasellesInicial() {
        // Configurar: Peó blanc a la posició inicial e7
        char[][] tauler = joc.getTauler();
        tauler[6][4] = 'P'; // e7 (posició inicial dels peons blancs)
        joc.setTornBlanques(true);
        
        // Executar: Intentar moure de e7 a e5 (2 caselles)
        boolean resultat = joc.esMovimentValidPerPeça('P', 6, 4, 4, 4, true);
        
        // Verificar
        assertTrue(resultat, "El peó blanc hauria de poder avançar 2 caselles des de la posició inicial");
    }
    
    @Test
    @DisplayName("Test 3: Peó blanc NO pot avançar si hi ha una peça davant")
    void testPeoBlanc1CasellaBlocada() {
        // Configurar: Peó blanc a e7, peça negra a e6
        char[][] tauler = joc.getTauler();
        tauler[6][4] = 'P'; // e7
        tauler[5][4] = 'p'; // e6 (peó negre bloquejant)
        joc.setTornBlanques(true);
        
        // Executar: Intentar moure de e7 a e6 (blocada)
        boolean resultat = joc.esMovimentValidPerPeça('P', 6, 4, 5, 4, false);
        
        // Verificar
        assertFalse(resultat, "El peó blanc NO hauria de poder avançar si hi ha una peça davant");
    }
    
    @Test
    @DisplayName("Test 4: Peó blanc pot capturar en diagonal una peça rival")
    void testPeoBlancCapturaDiagonal() {
        // Configurar: Peó blanc a e7, peça negra a d6
        char[][] tauler = joc.getTauler();
        tauler[6][4] = 'P'; // e7
        tauler[5][3] = 'p'; // d6 (peó negre per capturar)
        joc.setTornBlanques(true);
        
        // Executar: Intentar capturar de e7 a d6
        boolean resultat = joc.esMovimentValidPerPeça('P', 6, 4, 5, 3, true);
        
        // Verificar
        assertTrue(resultat, "El peó blanc hauria de poder capturar en diagonal una peça rival");
    }
    
    @Test
    @DisplayName("Test 5: Peó blanc NO pot moure en diagonal si no hi ha peça rival")
    void testPeoBlancDiagonalSensePeca() {
        // Configurar: Peó blanc a e7, casella d6 buida
        char[][] tauler = joc.getTauler();
        tauler[6][4] = 'P'; // e7
        joc.setTornBlanques(true);
        
        // Executar: Intentar moure en diagonal a d6 (buida)
        boolean resultat = joc.esMovimentValidPerPeça('P', 6, 4, 5, 3, false);
        
        // Verificar
        assertFalse(resultat, "El peó blanc NO hauria de poder moure en diagonal si no hi ha peça rival");
    }
    
    @Test
    @DisplayName("Test 6: Peó blanc NO pot moure enrere")
    void testPeoBlancNoMoureEnrere() {
        // Configurar: Peó blanc a e6
        char[][] tauler = joc.getTauler();
        tauler[5][4] = 'P'; // e6
        joc.setTornBlanques(true);
        
        // Executar: Intentar moure enrere de e6 a e7
        boolean resultat = joc.esMovimentValidPerPeça('P', 5, 4, 6, 4, false);
        
        // Verificar
        assertFalse(resultat, "El peó blanc NO hauria de poder moure enrere");
    }
}