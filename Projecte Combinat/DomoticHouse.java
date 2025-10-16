import java.util.Scanner;

public class DomoticHouse{
    
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
    
        boolean programRunning = true;
        
        while (programRunning) {
            int choice = Menu();

            switch (choice) {
                case 1:
                    
                    break;
                case 2:
                    
                    break;
                case 3:
                    
                    break;
                case 4:
                    
                    break;
                case 5:
                    
                    break;
                case 6:
                    System.out.println("Exiting the program. Goodbye!");
                    programRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }



    public static int Menu() {
        System.out.println("\nDomotic House Control Menu:");
        System.out.println("1. Control lights");
        System.out.println("2. Control windows");
        System.out.println("3. Control roomba");
        System.out.println("4. Control thermostat");
        System.out.println("5. Show overview");
        System.out.println("6. Exit");
        System.out.print("Please enter your choice (1-6): ");
        return scanner.nextInt();
    }
}