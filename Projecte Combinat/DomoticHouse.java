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

        // Initialize local variables
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
                    controlLights(lights);
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

    public static int readIntInput() {
        while (true) { // In this case it breaks with return so we don't need extra variables
            try {
                return scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine(); // Limpiar el buffer
                System.out.print("Please try again: ");
            }
        }
    }

    public static int showMenu() {
        System.out.println("\nDomotic House Control Menu:");
        System.out.println("1. Control lights");
        System.out.println("2. Control windows");
        System.out.println("3. Control roomba");
        System.out.println("4. Control thermostat");
        System.out.println("5. Show overview");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1-6): ");
        return readIntInput();
    }

    public static void controlLights(int[] lights) {
        boolean lightsMenuRunning = true;
        while (lightsMenuRunning) {
            System.out.println("\n=== Lights Control ===");
            System.out.println("1. Show current light settings");
            System.out.println("2. Set light percentage for a room");
            System.out.println("3. Set light percentage for all rooms");
            System.out.println("4. Back to main menu");
            System.out.print("Enter your choice (1-4): ");
            
            int lightsChoice = readIntInput();
            
            switch (lightsChoice) {
                case 1:
                    showLightSettings(lights);
                    break;
                case 2:
                    setLightForRoom(lights);
                    break;
                case 3:
                    setLightForAllRooms(lights);
                    break;
                case 4:
                    lightsMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public static void showLightSettings(int[] lights) { // Show current settings
        System.out.println("\n=== Current Light Settings ===");
        for (int i = 0; i < NUM_ROOMS; i++) {
            System.out.printf("%d. %s: %d%%\n", i + 1, ROOM_NAMES[i], lights[i]);
        }
    }

    public static void setLightForRoom(int[] lights) {
        try {
            System.out.println("\n=== Set Light for Room ===");
            System.out.println("Available rooms:");
            for (int i = 0; i < NUM_ROOMS; i++) {
                System.out.printf("%d. %s\n", i + 1, ROOM_NAMES[i]);
            }
        
            System.out.print("\nEnter room number (1-" + NUM_ROOMS + "): ");
            int roomNumber = readIntInput();
        
            // Check if room number is valid
            if (roomNumber < 1 || roomNumber > NUM_ROOMS) {
                throw new IllegalArgumentException("Invalid room number. Please enter a number between 1 and " + NUM_ROOMS);
            }
        
            System.out.print("Enter light percentage (0-100) for " + ROOM_NAMES[roomNumber - 1] + ": ");
            int percentage = readIntInput();
        
            // Check if percentage is valid
            if (percentage < 0 || percentage > 100) {
                throw new IllegalArgumentException("Invalid percentage. Please enter a value between 0 and 100");
            }
        
            // Update value in array
            lights[roomNumber - 1] = percentage;
        
            System.out.println(ROOM_NAMES[roomNumber - 1] + " light set to " + percentage + "%");
        
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Please enter valid numeric values.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            scanner.nextLine();
        }
    }

    public static void setLightForAllRooms(int[] lights) {
        try {
            System.out.println("\n=== Set Light for All Rooms ===");
            System.out.print("Enter light percentage (0-100) for all rooms: ");
            int percentage = readIntInput();
        
            // Check if % is valid
            if (percentage < 0 || percentage > 100) {
                throw new IllegalArgumentException("Invalid percentage. Please enter a value between 0 and 100");
            }
        
            // Update all rooms
            for (int i = 0; i < NUM_ROOMS; i++) {
                lights[i] = percentage;
            }

            System.out.println("All rooms light set to " + percentage + "%");
        
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}