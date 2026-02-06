# Projecte Joc d'Escacs ♟️

Implementació d'un joc d'escacs complet en Java amb proves unitàries automatitzades i documentació UML completa.

## 📋 Taula de continguts

- [Execució del joc](#-com-executar-el-joc)
- [Execució dels tests](#-com-executar-els-tests)
- [Diagrames UML](#-diagrames-uml)
- [Estructura de carpetes](#-estructura-de-carpetes)
- [Decisions de disseny](#-decisions-importants-de-disseny)

---

## 🎮 Com executar el joc

### Opció 1: Des de l'IDE (IntelliJ IDEA / Eclipse)

1. Obre el projecte
2. Localitza la classe `Escacs.java` a `src/main/java/`
3. Fes clic dret sobre la classe
4. Selecciona **Run 'Escacs.main()'**

### Opció 2: Des de la terminal

```bash
# Compilar
javac src/main/java/Escacs.java

# Executar
java -cp src/main/java Escacs
```

### Opció 3: Amb Maven

```bash
# Compilar
mvn compile

# Executar
mvn exec:java -Dexec.mainClass="Escacs"
```

---

## 🧪 Com executar els tests

Els tests utilitzen **JUnit 5** i **Maven**.

### Executar tots els tests

```bash
mvn test
```

### Executar només els tests del Peó

```bash
mvn test -Dtest=PeoTest
```

### Executar només els tests del Cavall

```bash
mvn test -Dtest=CavallTest
```

### Veure cobertura de tests (opcional)

```bash
mvn test jacoco:report
```

L'informe es generarà a: `target/site/jacoco/index.html`

---

## 📊 Diagrames UML

Aquest projecte inclou documentació visual completa del comportament del sistema.

### 🔄 Diagrama de Flux del Programa

**Fitxer:** [`docs/diagrama_flux.mermaid`](docs/diagrama_flux.mermaid)

Aquest diagrama mostra el **flux complet del joc** des de l'inici fins al final, incloent:

- Inicialització del tauler
- Bucle principal del joc
- Gestió de torns
- Validació de moviments per cada tipus de peça
- Detecció d'escac, escac i mat i taules
- Gestió d'enrocs, promocions i captures
- Opció de tornar a jugar

**Com visualitzar-lo:**
- Obre https://mermaid.live/
- Copia el contingut del fitxer `.mermaid`
- Visualitza i descarrega com PNG/SVG

![Diagrama de Flux](docs/diagrama_flux.png)

---

### 🔀 Diagrama de Seqüència: Validació de Moviment Il·legal

**Fitxer:** [`docs/diagrama_moviment_illegal.puml`](docs/diagrama_moviment_illegal.puml)

Aquest diagrama de seqüència UML documenta **l'Acció 4** del projecte i mostra com el sistema gestiona els moviments il·legals:

**Què documenta:**
- 10 tipus d'errors diferents detectats pel sistema
- Missatges específics per cada tipus d'error
- Garantia que el tauler NO es modifica si hi ha error
- Bucle de reintentar fins obtenir un moviment vàlid
- Simulació de moviments per prevenir escacs

**Participants del sistema:**
- `bucleJoc` - Control del flux principal
- `demanarMoviment` - Interacció amb l'usuari
- `validarEntradaMoviment` - Validació de format
- `validarMoviment` - Validació de regles
- `validarPeçaOrigen` / `validarPeçaDesti` - Validacions específiques
- `esMovimentValidPerPeça` - Regles per cada tipus de peça
- `validarEscacDesprésMoviment` - Simulació i comprovació d'escac
- `tauler` - Estat del joc

**Com visualitzar-lo:**
- Obre https://www.plantuml.com/plantuml/uml/
- Copia el contingut del fitxer `.puml`
- Visualitza i descarrega com PNG/PDF

![Diagrama de Validació](docs/diagrama_moviment_illegal.png)

**Documentació detallada:** Veure [`docs/EXPLICACIO_DIAGRAMA.md`](docs/EXPLICACIO_DIAGRAMA.md)

---

### 📚 Tipus d'errors detectats

El sistema detecta i informa específicament de 10 tipus d'errors:

#### Errors de Format (Fase 1):
1. ❌ Format incorrecte (ex: `"e2e4"` sense espai)
2. ❌ Coordenades invàlides (ex: `"e22 e4"`)
3. ❌ Columna fora de rang (ex: `"z2 e4"`)
4. ❌ Fila fora de rang (ex: `"e0 e4"`)
5. ❌ Origen i destí iguals (ex: `"e2 e2"`)

#### Errors de Lògica (Fase 2):
6. ❌ Casella origen buida
7. ❌ Peça no correspon al jugador actual
8. ❌ Destí té peça pròpia
9. ❌ Moviment invàlid per la peça (ex: peó lateral)
10. ❌ Moviment deixa el rei en escac

---

## 📁 Estructura de carpetes

```
projecte-escacs/
│
├── docs/                                    # 📊 Documentació i diagrames UML
│   ├── diagrama_flux.mermaid                # Diagrama de flux del programa
│   ├── diagrama_flux.png                    # Imatge del diagrama de flux
│   ├── diagrama_moviment_illegal.puml       # Diagrama UML Acció 4
│   ├── diagrama_moviment_illegal.png        # Imatge del diagrama UML
│
├── src/                                     # ⭐ Codi font
│   ├── main/java/
│   │   └── Escacs.java                      # Classe principal del joc
│   └── test/java/
│       ├── PeoTest.java                     # 6 tests del Peó
│       └── CavallTest.java                  # 6 tests del Cavall
│
├── pom.xml                                  # Configuració Maven
└── README.md                                # ⭐ Aquest fitxer
```

---

## 🎯 Decisions importants de disseny


### Nota sobre l'arquitectura procedural

Aquest projecte segueix una **arquitectura procedural** dins d'una única classe Java, tal com requereixen els requisits del mòdul de Programació (no es pot utilitzar POO avançada amb múltiples classes). La separació de responsabilitats s'aconsegueix mitjançant:

- **Seccions lògiques** clarament delimitades amb comentaris (`// GESTIÓ DEL TAULER`, `// VALIDACIÓ`, etc.)
- **Funcions específiques** amb responsabilitat única i noms descriptius
- **Correspondència amb participants UML** (Acció 4):
  - `bucleJoc()` → **GameController** (bucle principal / torns)
  - `demanarMoviment()` + `parsejarCoordenada()` → **UIConsola** (entrada i missatges)
  - `validarMoviment()` + `esMovimentValidPerPeça()` → **MoveValidator** (validació)
  - `tauler[][]` + mètodes associats → **Board** (lectura estat tauler)

---

### 1. **Representació del tauler**

El tauler s'implementa com una **matriu bidimensional** `char[8][8]`:

```java
private char[][] tauler;
```

**Justificació:**
- **Simplicitat**: Accés directe a qualsevol casella amb `tauler[fila][columna]`
- **Eficiència**: Operacions O(1) per llegir/escriure
- **Convenció**: Peces blanques en MAJÚSCULES (`P`, `T`, `C`, `A`, `Q`, `K`), negres en minúscules (`p`, `t`, `c`, `a`, `q`, `k`)
- **Caselles buides**: Representades amb el caràcter `'.'`

**Correspondència amb notació d'escacs:**
- Columnes: `'a'` a `'h'` → índexs `0` a `7`
- Files: `'1'` a `'8'` → índexs `0` a `7`
  - Fila `'1'` (peces negres) → índex `0`
  - Fila `'8'` (peces blanques) → índex `7`

**Exemple:** La casella `"e2"` correspon a `tauler[1][4]`

---

### 2. **Validació de moviments (arquitectura modular)**

La validació de moviments segueix un **patró en cadena** amb validacions progressives:

```
Moviment entrada usuari
    ↓
1. validarEntradaMoviment()        → Sintaxi correcta ("e2 e4")?
    ↓
2. validarPeçaOrigen()             → Hi ha peça del color correcte?
    ↓
3. validarPeçaDesti()              → Destí no té peça pròpia?
    ↓
4. esMovimentValidPerPeça()        → Segueix regles de la peça?
    ↓                                 (delega a mètodes específics)
    ├─ esMovimentValidPeo()
    ├─ esMovimentValidTorre()
    ├─ esMovimentValidCavall()
    ├─ esMovimentValidAlfil()
    ├─ esMovimentValidReina()
    └─ esMovimentValidRei()
    ↓
5. validarEscacDesprésMoviment()   → El moviment deixa el rei en escac?
    ↓
✅ MOVIMENT VÀLID → executarMoviment()
```

**Justificació:**
- **Separació de responsabilitats**: Cada mètode valida un aspecte concret
- **Reutilització**: Les validacions de peces es poden testejar independentment
- **Missatges d'error clars**: Cada validació mostra un error específic
- **Mantenibilitat**: Fàcil afegir noves regles (enroc, captura al pas, etc.)

---

### 3. **Detecció d'escac i escac i mat**

**Escac:**
```java
boolean estaReiEnEscac(boolean colorBlanc)
```
- Localitza el rei del color especificat
- Comprova si **qualsevol peça rival** pot capturar-lo
- Utilitza `potAtacar()` per validar amenaces

**Escac i mat:**
```java
boolean esEscacIMat(boolean colorBlanc)
```
- Comprova que el rei estigui en escac
- Prova **tots els moviments possibles** de totes les peces del jugador
- Si **cap moviment** treu el rei de l'escac → Escac i mat

**Justificació:**
- **Algorisme exhaustiu**: Garanteix detecció correcta sense falsos positius
- **Simulació de moviments**: Cada moviment es prova temporalment i es desfà
- **Rendiment acceptable**: Per a un tauler 8×8 amb màxim 16 peces, la complexitat és manejable

---

### 4. **Gestió de l'enroc (regla especial)**

L'enroc és una **jugada especial** que implica moure el rei i una torre simultàniament.

**Condicions implementades:**
1. ✅ El rei no s'ha mogut mai
2. ✅ La torre no s'ha mogut mai
3. ✅ El camí entre rei i torre està lliure
4. ✅ El rei no està en escac
5. ✅ El rei no passa per una casella amenaçada
6. ✅ El rei no acaba en una casella amenaçada

**Variables de control:**
```java
private boolean reiBlancMogut;
private boolean reiNegreMogut;
private boolean torreBlancaA8Moguda;  // Torre costat dama
private boolean torreBlancaH8Moguda;  // Torre costat rei
private boolean torreNegraA1Moguda;
private boolean torreNegraH1Moguda;
```

**Justificació:**
- **Variables booleanes**: Més eficients que comprovar l'historial de moviments
- **Validació completa**: Implementa totes les regles oficials dels escacs
- **Separació**: Mètodes específics per validar i executar enrocs

---

## 🛠️ Tecnologies utilitzades

- **Java 17**
- **JUnit 5.10.1** (proves unitàries)
- **Maven 3.x** (gestió de dependències i build)
- **PlantUML** (diagrames UML de seqüència)
- **Mermaid** (diagrames de flux)

---

## ✨ Funcionalitats implementades

- ✅ Moviment de totes les peces segons regles oficials
- ✅ Captura de peces
- ✅ Detecció d'escac
- ✅ Detecció d'escac i mat
- ✅ Detecció de taules per rei ofegat (stalemate)
- ✅ Enroc curt i llarg
- ✅ Promoció de peons
- ✅ Historial de moviments
- ✅ Registre de peces capturades
- ✅ Validació exhaustiva de moviments (10 tipus d'errors)
- ✅ Missatges d'error descriptius i específics
- ✅ Opció de tornar a jugar
- ✅ Intercanvi de colors entre partides

---

## 📝 Proves unitàries

El projecte inclou **12 tests automatitzats** (6 per al Peó + 6 per al Cavall) que validen:

### Tests del Peó ♟️
1. ✅ Moviment 1 casella endavant
2. ✅ Moviment 2 caselles des de posició inicial
3. ✅ Bloqueig per peça davant
4. ✅ Captura diagonal de peça rival
5. ✅ No captura diagonal sense peça
6. ✅ No pot moure enrere

### Tests del Cavall ♞
1. ✅ Moviment en L a casella buida
2. ✅ Captura en moviment L
3. ✅ No pot moure com alfil
4. ✅ No pot fer moviments invàlids
5. ✅ No pot capturar peça pròpia
6. ✅ Pot saltar per sobre de peces

**Cobertura:** Les proves validen tant casos vàlids com invàlids per assegurar la robustesa del codi.

**Executar tests:** `mvn test`

---

## 📚 Documentació del projecte

### Diagrames UML
- **Diagrama de flux:** Visió general del programa complet
- **Diagrama de seqüència:** Validació de moviments il·legals (Acció 4)

### Javadoc
Tots els mètodes públics i clau estan documentats amb Javadoc complet que inclou:
- Descripció de la funcionalitat
- Paràmetres amb tipus i descripció
- Valor de retorn
- Casos especials i condicions d'error


---

## 👥 Autors

- **Assignatura:** Programació i Entorns de Desenvolupament
- **Curs:** DAM1
- **Professors:** Carles Bonet, Cristian González Delgado, Isabel Bosch Millastre
- **Data:** Gener 2026

---

## 📄 Llicència

Aquest projecte és un exercici acadèmic desenvolupat per a l'assignatura de Programació i d'Entorns de Desenvolupament.

---

## 🔗 Referències

- [Regles oficials dels escacs (FIDE)](https://www.fide.com/FIDE/handbook/LawsOfChess.pdf)
- [Documentació JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Getting Started Guide](https://maven.apache.org/guides/getting-started/)
- [PlantUML Documentation](https://plantuml.com/)
- [Mermaid Documentation](https://mermaid.js.org/)

---

## 🎓 Requisits acadèmics completats

### Acció 1: Clean Code i Modularitat ✅
- Separació clara de responsabilitats
- Noms significatius de variables i mètodes
- Funcions petites amb responsabilitat única
- Constants en lloc de "magic numbers"
- Missatges d'error específics i útils

### Acció 2: Proves automatitzades ✅
- 12 tests (6 Peó + 6 Cavall)
- Execució amb una única comanda: `mvn test`
- Noms descriptius i assertions clares
- Cobertura de casos vàlids i invàlids

### Acció 3: Documentació del codi ✅
- README.md complet amb instruccions
- Javadoc a tots els mètodes clau
- Documentació de decisions de disseny
- Guies d'execució i testing

### Acció 4: Diagrama de comportament ✅
- Diagrama UML de seqüència complet
- Escenari: "Validació d'un moviment il·legal"
- Participants, flux normal, errors i bucle de reintentar
- Format `.puml` i imatge PNG disponibles

---

**Gaudeix del joc! ♔♕♖♗♘♙**