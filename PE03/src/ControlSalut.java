package PE03.src;
import java.util.Scanner;
import java.time.Year;
import java.text.DecimalFormat;
import java.util.Locale;

public class ControlSalut {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        DecimalFormat df2 = new DecimalFormat("#.##");
        char choice;
        boolean program = true;
        String fullName = "";
        int age = 0;
        double weight = 0;
        double height = 0;
        boolean enteredData = false;
        

        while (program){
            System.out.println("--- CONTROL DE SALUT ---");
            System.out.println("a) Introduir dades");
            System.out.println("b) Modificar dades");
            System.out.println("c) Visualitzar dades");
            System.out.println("d) Sortir");
            System.out.print("Tria una opcio: ");
            choice = scanner.next().toLowerCase().charAt(0);
            scanner.nextLine(); // Consumir el salt de línia després de next()

            switch (choice) {
                case 'a':
                    System.out.println("Has triat introduir dades.");

                    // NOM COMPLET
                    boolean validName = false;
                    while (!validName) {
                        try {
                            System.out.print("Nom complet: ");
                            fullName = scanner.nextLine().trim(); // Trim per netejar espais sobrants
                            if (fullName.isEmpty()) {
                                throw new Exception("Error: El nom no pot quedar buit.");
                            }
                            validName = true; // Si arriba aqui nom = valid
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    // EDAT
                    boolean validAge = false;
                    while (!validAge) {
                        try {
                            System.out.print("Edat: ");
                            String ageInput = scanner.nextLine().trim();
                            age = Integer.parseInt(ageInput); //Si dona error vol dir que hi ha error d'entrada
                            if (age <= 0 || age > 120) {
                                throw new Exception("Error: L'edat ha de ser un enter positiu < o = a 120.");
                            }
                            validAge = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Format numèric invàlid.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    // PES
                    boolean validWeight = false;
                    while (!validWeight) {
                        try {
                            System.out.print("Pes (kg): ");
                            String weightInput = scanner.nextLine().trim().replace(',', '.'); //Accepta . i , com a decimals
                            weight = Double.parseDouble(weightInput);
                            if (weight <= 0 || weight > 400) {
                                throw new Exception("Error: El pes ha de ser un decimal positiu < 400.");
                            }
                            validWeight = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Format numèric invàlid.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    // ALTURA
                    boolean validHeight = false;
                    while (!validHeight) {
                        try {
                            System.out.print("Alçada (m): ");
                            String heightInput = scanner.nextLine().trim().replace(',', '.');
                            height = Double.parseDouble(heightInput);
                            if (height < 0.5 || height > 2.5) {
                                throw new Exception("Error: L'alçada ha de ser un decimal positiu entre 0.5 i 2.5 metres.");
                            }
                            validHeight = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Format numèric invàlid.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    enteredData = true;
                    break;
                case 'b':
                    if (!enteredData) {
                        System.out.println("Error: Primer has d'introduir les dades (opció a).");
                        break;
                    }
                    
                    System.out.println("Has triat modificar dades.");
                    System.out.println("1) Nom complet");
                    System.out.println("2) Edat");
                    System.out.println("3) Pes");
                    System.out.println("4) Alçada");
                    System.out.print("Quina dada vols modificar? (1-4): ");
                    String modifyInput = scanner.nextLine().trim();

                    try {
                        int modifyChoice = Integer.parseInt(modifyInput); //S'agafa com a string per controlar errors i es converteix a int ja que el switch no funciona amb strings
                        
                        switch(modifyChoice) {
                            // NOM
                            case 1:
                                boolean validNameMod = false;
                                while (!validNameMod) {
                                    try {
                                        System.out.print("Nou nom complet: ");
                                        String newName = scanner.nextLine().trim(); // Trim per netejar espais sobrants
                                        if (newName.isEmpty()) {
                                            throw new Exception("Error: El nom no pot quedar buit.");
                                        }
                                        fullName = newName; // Assignar el nou nom si és vàlid
                                        validNameMod = true; // Si arriba aqui nom = valid
                                        System.out.println("Nom modificat correctament");
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            // EDAT
                            case 2:
                                boolean validAgeMod = false;
                                while (!validAgeMod) {
                                    try {
                                        System.out.print("Nova edat: ");
                                        String newAgeInput = scanner.nextLine().trim();
                                        int newAge = Integer.parseInt(newAgeInput); //Si dona error vol dir que hi ha error d'entrada
                                        if (newAge <= 0 || newAge > 120) {
                                            throw new Exception("Error: L'edat ha de ser un enter positiu < o = a 120.");
                                        }
                                        age = newAge; // Assignar la nova edat si és vàlida
                                        validAgeMod = true;
                                        System.out.println("Edat actualitzada correctament.");
                                    } catch (NumberFormatException e) {
                                        System.out.println("Error: Format numèric invàlid.");
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            // PES
                            case 3:
                                boolean validWeightMod = false;
                                while (!validWeightMod) {
                                    try {
                                        System.out.print("Nou pes (kg): ");
                                        String newWeightInput = scanner.nextLine().trim().replace(',', '.'); //Accepta . i , com a decimals
                                        double newWeight = Double.parseDouble(newWeightInput);
                                        if (newWeight <= 0 || newWeight > 400) {
                                            throw new Exception("Error: El pes ha de ser un decimal positiu raonable.");
                                        }
                                        weight = newWeight; // Assignar el nou pes si és vàlid
                                        validWeightMod = true;
                                        System.out.println("Pes actualitzat correctament.");
                                    } catch (NumberFormatException e) {
                                        System.out.println("Error: Format numèric invàlid.");
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            // ALTURA
                            case 4:
                                boolean validHeightMod = false;
                                while (!validHeightMod) {
                                    try {
                                        System.out.print("Nova alçada (m): ");
                                        String newHeightInput = scanner.nextLine().trim().replace(',', '.');
                                        double newHeight = Double.parseDouble(newHeightInput);
                                        if (newHeight < 0.5 || newHeight > 2.5) {
                                            throw new Exception("Error: L'alçada ha de ser un decimal positiu entre 0.5 i 2.5 metres.");
                                        }
                                        height = newHeight; // Assignar la nova alçada si és vàlida
                                        validHeightMod = true;
                                        System.out.println("Alçada actualitzada correctament.");
                                    } catch (NumberFormatException e) {
                                        System.out.println("Error: Format numèric invàlid.");
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            default:
                                System.out.println("Error: Opció no vàlida.");
                        }
                    }catch (NumberFormatException e) {
                        System.out.println("Error: Format numèric invàlid.");
                    }
                    break;
                case 'c':
                    if (!enteredData) {
                        System.out.println("Error: Primer has d'introduir les dades (opció a).");
                        break;
                    }
                    System.out.println("Has triat visualitzar dades.");
                    // Normalitzar el NOM
                    String normalizedName = "";
                    String[] words = fullName.trim().split(" "); // Dividir el nom en paraules (per espais)
                    for (int i = 0; i < words.length; i++) { // Processar cada paraula
                        if (!words[i].isEmpty()) { // Primera lletra en majuscula, la resta en minuscula
                            String firstLetter = words[i].substring(0, 1).toUpperCase();
                            String restOfWord = words[i].substring(1).toLowerCase();
                            String normalizedWord = firstLetter + restOfWord;
                            if (normalizedName.isEmpty()) { // Afegir al resultat
                                normalizedName = normalizedWord;
                            } else {
                                normalizedName = normalizedName + " " + normalizedWord;
                            }
                        }
                    }

                    // Càlcul IMC
                    double imc = weight / (height * height);
                    String IMC_category = "";
                    if (imc < 18.5) {
                        IMC_category = "pes baix";
                    } else if (imc < 25) {
                        IMC_category = "pes normal";
                    } else if (imc < 30) {
                        IMC_category = "sobrepès";
                    } else {
                        IMC_category = "obesitat";
                    }

                    // Freqüència cardíaca
                    int fc_max = 220 - age;
                    int fc50 = (int) Math.round(fc_max * 0.5);
                    int fc85 = (int) Math.round(fc_max * 0.85);

                    // Aigua recomanada
                    double waterLiters = (weight * 35) / 1000.0;

                    // Any de naixement
                    int actualYear = Year.now().getValue();
                    int birthYear = actualYear - age;

                    // Mostrar resultats
                    System.out.println("Hola, " + normalizedName + "!");
                    System.out.println("Edat: " + age + " anys | Pes: " + df2.format(weight) + " kg | Alçada: " + df2.format(height) + " m");
                    System.out.println("IMC: " + df2.format(imc) + " (" + IMC_category + ")");
                    System.out.println("FC màxima estimada: " + fc_max + " bpm");
                    System.out.println("Zona FC objectiu: " + fc50 + "-" + fc85 + " bpm");
                    System.out.println("Aigua recomanada: " + df2.format(waterLiters) + " L/dia");
                    System.out.println("Any de naixement aproximat: " + birthYear);



                    break;
                case 'd':
                    System.out.println("Sortint del programa.");
                    program = false;
                    break;
                default:
                    System.out.println("Opcio no valida. Si us plau, tria una opcio entre a i d.");
            }

        }
        scanner.close();
    }
}
