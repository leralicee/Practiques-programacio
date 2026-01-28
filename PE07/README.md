# Projecte Joc d'Escacs ♟️

Implementació d'un joc d'escacs complet en Java amb proves unitàries automatitzades.

## 📋 Taula de continguts

- [Execució del joc](#-com-executar-el-joc)
- [Execució dels tests](#-com-executar-els-tests)
- [Estructura de carpetes](#-estructura-de-carpetes)
- [Decisions de disseny](#-decisions-importants-de-disseny)

---

## 🎮 Com executar el joc

### Opció 1: Des de l'IDE (IntelliJ IDEA / Eclipse)

1. Obre el projecte
2. Localitza la classe `Escacs.java` a `src/`
3. Fes clic dret sobre la classe
4. Selecciona **Run 'Escacs.main()'**

### Opció 2: Des de la terminal

```bash
# Compilar
javac src/Escacs.java

# Executar
java -cp src Escacs
```

### Opció 3: Amb Maven (des de tests/demo/)

```bash
cd tests/demo
mvn compile
mvn exec:java -Dexec.mainClass="Escacs"
```

---

## 🧪 Com executar els tests

Els tests es troben a la carpeta `tests/demo/` i utilitzen **JUnit 5** i **Maven**.

### Executar tots els tests

```bash
cd tests/demo
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

---

## 📁 Estructura de carpetes

```
PE07/
│
├── .github/              # Configuració de GitHub
├── .vscode/              # Configuració de VS Code
│
├── docs/                 # Documentació del projecte
│   └── (diagrames UML)
│
├── src/                  # ⭐ Codi font principal
│   └── Escacs.java       # Classe principal del joc
│
├── tests/                # ⭐ Proves unitàries
│   └── demo/
│       ├── pom.xml       # Configuració Maven
│       ├── src/
│       │   ├── main/java/
│       │   │   └── Escacs.java    # Còpia amb mètodes testables
│       │   └── test/java/
│       │       ├── PeoTest.java   # 6 tests del Peó
│       │       └── CavallTest.java # 6 tests del Cavall
│       └── target/       # Fitxers compilats (generat per Maven)
│
└── README.md             # ⭐ Aquest fitxer
```

### Notes sobre l'estructura

- **src/Escacs.java**: Codi principal del joc, executable directament
- **tests/demo/**: Projecte Maven independent per a proves unitàries
- Els tests requereixen una còpia d'`Escacs.java` amb mètodes públics per ser testables

---

## 🎯 Decisions importants de disseny

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
- Files: `'1'` a `'8'` → índexs `7` a `0` (INVERTIT!)
  - Fila `'1'` (peces negres) → índex `0`
  - Fila `'8'` (peces blanques) → índex `7`

**Exemple:** La casella `"e2"` correspon a `tauler[6][4]`

---

### 2. **Validació de moviments (arquitectura modular)**

La validació de moviments segueix un **patró en cadena** amb validacions progressives:

```
Moviment entrada usuari
    ↓
1. validarFormatMoviment()     → Sintaxi correcta ("e2 e4")?
    ↓
2. validarPeçaOrigen()         → Hi ha peça del color correcte?
    ↓
3. validarPeçaDesti()          → Destí no té peça pròpia?
    ↓
4. esMovimentValidPerPeça()    → Segueix regles de la peça?
    ↓                             (delega a mètodes específics)
    ├─ esMovimentValidPeo()
    ├─ esMovimentValidTorre()
    ├─ esMovimentValidCavall()
    ├─ esMovimentValidAlfil()
    ├─ esMovimentValidReina()
    └─ esMovimentValidRei()
    ↓
5. validarEscacDesprésMoviment() → El moviment deixa el rei en escac?
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
- Utilitza `casellaBaixAtac()` per validar amenaces

**Escac i mat:**
```java
boolean estaEnEscacIMat(boolean colorBlanc)
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
- **Separació**: Mètode `validarEnroc()` independent de la validació normal

---

## 🛠️ Tecnologies utilitzades

- **Java 17**
- **JUnit 5.10.1** (proves unitàries)
- **Maven 3.x** (gestió de dependències i build)

---

## ✨ Funcionalitats implementades

- ✅ Moviment de totes les peces segons regles oficials
- ✅ Captura de peces
- ✅ Detecció d'escac
- ✅ Detecció d'escac i mat
- ✅ Detecció de taules per rei ofegat
- ✅ Enroc curt i llarg
- ✅ Promoció de peons
- ✅ Historial de moviments
- ✅ Registre de peces capturades
- ✅ Validació exhaustiva de moviments
- ✅ Missatges d'error descriptius

---

## 📝 Proves unitàries

El projecte inclou **12 tests automatitzats** (6 per al Peó + 6 per al Cavall) que validen:

### Tests del Peó ♟️
1. Moviment 1 casella endavant
2. Moviment 2 caselles des de posició inicial
3. Bloqueig per peça davant
4. Captura diagonal de peça rival
5. No captura diagonal sense peça
6. No pot moure enrere

### Tests del Cavall ♞
1. Moviment en L a casella buida
2. Captura en moviment L
3. No pot moure com alfil
4. No pot fer moviments invàlids
5. No pot capturar peça pròpia
6. Pot saltar per sobre de peces

**Cobertura:** Les proves validen tant casos vàlids com invàlids per assegurar la robustesa del codi.

---

## 👥 Autors

- **Assignatura:** MP0487 - Entorns de Desenvolupament
- **Curs:** DAW1 / DAM1
- **Professors:** Cristian González Delgado, Isabel Bosch Millastre
- **Data:** Gener 2026

---

## 📄 Llicència

Aquest projecte és un exercici acadèmic desenvolupat per a l'assignatura d'Entorns de Desenvolupament.

---

## 🔗 Referències

- [Regles oficials dels escacs (FIDE)](https://www.fide.com/FIDE/handbook/LawsOfChess.pdf)
- [Documentació JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Getting Started Guide](https://maven.apache.org/guides/getting-started/)

---

**Gaudeix del joc! ♔♕♖♗♘♙**