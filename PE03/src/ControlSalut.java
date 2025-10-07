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
    
                     // NOM COMPLET amb try-catch
                    boolean validName = false;
                    while (!validName) {
                        try {
                            System.out.print("Nom complet: ");
                            fullName = scanner.nextLine().trim(); // Trim per netejar espais sobrants
            
                            if (fullName.isEmpty()) {
                                throw new Exception("Error: El nom no pot quedar buit.");
                            }
            
                            validName = true; // Si arriba aquí, el nom és vàlid
                            System.out.println("Nom registrat correctament");
            
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case 'b':
                    System.out.println("Has triat modificar dades.");
                    // Code to modify data
                    break;
                case 'c':
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
    }
}
