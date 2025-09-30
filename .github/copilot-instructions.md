# Copilot Instructions for Practiques-programacio

## Project Overview
This repository contains Java programming practice exercises, organized by assignment. The structure is simple and flat, with each exercise in its own file or subdirectory. There is no complex architecture or cross-file dependency.

## Directory Structure
- `PE01_RomeroPaula.java`: Standalone Java file for a specific exercise.
- `PE02/`
  - `src/HistoriaVarisFinals.java`: Java file for another exercise.
  - `Documents/`: (Purpose not specified; likely for supporting materials.)

## Key Patterns & Conventions
- Each Java file is self-contained and does not import or depend on other project files.
- Naming convention: `PE##_<Name>.java` for main exercises, with subfolders for organization.
- No build scripts, test frameworks, or external dependencies are present.
- No package declarations; all classes are in the default package.

## Developer Workflows
- **Compiling:** Use `javac` directly on the `.java` files. Example:
  ```powershell
  javac PE01_RomeroPaula.java
  javac PE02\src\HistoriaVarisFinals.java
  ```
- **Running:** Use `java` with the class name (no `.class` extension). Example:
  ```powershell
  java PE01_RomeroPaula
  java -cp PE02\src HistoriaVarisFinals
  ```
- **No automated tests** or build tools (like Maven/Gradle) are used.

## AI Agent Guidance
- When adding new exercises, follow the existing naming and folder conventions.
- Do not introduce packages, frameworks, or dependencies unless explicitly requested.
- Keep each exercise self-contained.
- If adding documentation, place it in the relevant subfolder or in a new `README.md`.

## Example
To add a new exercise:
1. Create a file like `PE03_SurnameName.java` in the root or a new subfolder.
2. Write a standalone Java class with a `main` method.
3. Compile and run as shown above.

---
If any conventions or workflows are unclear, please provide feedback for clarification or updates.
