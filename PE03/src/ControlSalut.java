package PE03.src;
import java.util.Scanner;
import java.time.Year;
import java.text.DecimalFormat;
import java.util.Locale;

public class ControlSalut {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        DecimalFormat df2 = new DecimalFormat("#.##");
        DecimalFormat df0 = new DecimalFormat("#");
        char choice;
        boolean program = true;
        String fullName;
        int age;
        double weight, height;
        boolean enteredData = false;
        

        while (program){
            System.out.println("--- CONTROL DE SALUT ---");
            System.out.println("a) Introduir dades");
            System.out.println("b) Modificar dades");
            System.out.println("c) Visualitzar dades");
            System.out.println("d) Sortir");
            System.out.print("Tria una opcio: ");
            choice = scanner.next().toLowerCase().charAt(0);

            switch (choice) {
                case 'a':
                    System.out.println("Has triat introduir dades.");
                    scanner.nextLine(); // Consumir el salt de línia pendent

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
                            System.out.println("Nom registrat correctament");
            
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

                    try {
                        int modifyChoice = Integer.parseInt(scanner.nextLine().trim()); //S'agafa com a string per controlar errors i es converteix a int ja que el switch no funciona amb strings
                        
                        switch(modifyChoice) {
                            case 1:
                                // Modificar nom
                                break;
                            case 2:
                                // Modificar edat
                                break;
                            case 3:
                                // Modificar pes
                                break;
                            case 4:
                                // Modificar alçada
                                break;
                            default:
                                System.out.println("Error: Opció no vàlida. Tria un número entre 1 i 4.");
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
                    // Code to visualize data


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
