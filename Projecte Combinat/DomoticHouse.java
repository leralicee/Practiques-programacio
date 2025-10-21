import java.util.Random;
import java.util.Scanner;

public class DomoticHouse{
    
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    // Constants
    private static final int NUM_ROOMS = 5;
    private static final int MIN_TEMP = 15;
    private static final int MAX_TEMP = 30;
    private static final String[] ROOM_NAMES = {"Living Room", "Kitchen", "Bedroom", "Bathroom", "Office"};
    public static void main(String[] args) {
    
        boolean programRunning = true;

        // Initialitze local variables
        Object[] houseData = initializeHouse();
        int[] lights = (int[]) houseData[0];
        int[] windows = (int[]) houseData[1];
        boolean roombaStatus = (boolean) houseData[2];
        int thermostatTemp = (int) houseData[3];
        int targetTemp = (int) houseData[4];
        
        while (programRunning) {
            int choice = showMenu();

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

    public static Object[] initializeHouse() {
        int[] lightPercentages = new int[NUM_ROOMS];
        int[] blindPercentages = new int[NUM_ROOMS];
    
        for (int i = 0; i < NUM_ROOMS; i++) {
            lightPercentages[i] = random.nextInt(101);
            blindPercentages[i] = random.nextInt(101);
        }
    
        boolean roombaStatus = random.nextBoolean();
        int thermostatTemp = random.nextInt(MAX_TEMP - MIN_TEMP + 1) + MIN_TEMP;
        int targetTemp = 22;
    
        return new Object[]{lightPercentages, blindPercentages, roombaStatus, thermostatTemp, targetTemp};
    }

    public static int showMenu() {
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