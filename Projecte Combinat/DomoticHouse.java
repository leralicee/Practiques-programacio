import java.util.Random;
import java.util.Scanner;

public class DomoticHouse{
    
    private static Scanner scanner = new Scanner(System.in);

    // Constants
    private static final int NUM_ROOMS = 5;
    private static final int MIN_TEMP = 15;
    private static final int MAX_TEMP = 30;
    private static final String[] ROOM_NAMES = {"Living Room", "Kitchen", "Bedroom", "Bathroom", "Office"};
    public static void main(String[] args) {
    
        boolean programRunning = true;

        // Initialitze local variables
        boolean[] lights = initializeLights();
        boolean[] windows = initializeWindows();
        boolean roombaStatus = initializeRoomba();
        int thermostatTemp = initializeThermostat();
        int targetTemp = 22;
        
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

    public static boolean[] initializeLights() {
        Random random = new Random();
        boolean[] lights = new boolean[NUM_ROOMS];
        for (int i = 0; i < NUM_ROOMS; i++) {
            lights[i] = random.nextBoolean();
        }
        return lights;
    }

    public static boolean[] initializeWindows() {
        Random random = new Random();
        boolean[] windows = new boolean[NUM_ROOMS];
        for (int i = 0; i < NUM_ROOMS; i++) {
            windows[i] = random.nextBoolean();
        }
        return windows;
    }

    public static boolean initializeRoomba() {
        Random random = new Random();
        return random.nextBoolean();
    }
    
    public static int initializeThermostat() {
        Random random = new Random();
        return random.nextInt(MAX_TEMP - MIN_TEMP + 1) + MIN_TEMP;
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