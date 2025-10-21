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
                    controlWindows(windows);
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
                String inputLine = scanner.nextLine(); 
                return Integer.parseInt(inputLine); 
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
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

    public static void controlPercentageSystem(int[] percentages, String[] names, String systemName, String itemName) {
        boolean menuRunning = true;
        while (menuRunning) {
            System.out.println("\n=== " + systemName + " Control ===");
            System.out.println("1. Show current " + itemName + " settings");
            System.out.println("2. Set " + itemName + " percentage for a room");
            System.out.println("3. Set " + itemName + " percentage for all rooms");
            System.out.println("4. Back to main menu");
            System.out.print("Enter your choice (1-4): ");
            
            int choice = readIntInput();
            
            switch (choice) {
                case 1:
                    showPercentageSettings(percentages, names, "Current " + systemName + " Settings");
                    break;
                case 2:
                    setPercentageForRoom(percentages, names, itemName);
                    break;
                case 3:
                    setPercentageForAllRooms(percentages, names, itemName);
                    break;
                case 4:
                    menuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public static void showPercentageSettings(int[] percentages, String[] names, String title) {
        System.out.println("\n=== " + title + " ===");
        for (int i = 0; i < NUM_ROOMS; i++) {
            System.out.printf("%d. %s: %d%%\n", i + 1, names[i], percentages[i]);
        }
    }

    public static void setPercentageForRoom(int[] percentages, String[] names, String itemName) {
        try {
            System.out.println("\n=== Set " + itemName + " for Room ===");
            System.out.println("Available rooms:");
            for (int i = 0; i < NUM_ROOMS; i++) {
                System.out.printf("%d. %s\n", i + 1, names[i]);
            }
        
            System.out.print("\nEnter room number (1-" + NUM_ROOMS + "): ");
            int roomNumber = readIntInput();
        
            if (roomNumber < 1 || roomNumber > NUM_ROOMS) {
                throw new IllegalArgumentException("Invalid room number. Please enter a number between 1 and " + NUM_ROOMS);
            }
        
            System.out.print("Enter " + itemName + " percentage (0-100) for " + names[roomNumber - 1] + ": ");
            int percentage = readIntInput();
        
            if (percentage < 0 || percentage > 100) {
                throw new IllegalArgumentException("Invalid percentage. Please enter a value between 0 and 100");
            }
        
            percentages[roomNumber - 1] = percentage;
            System.out.println(names[roomNumber - 1] + " " + itemName + " set to " + percentage + "%");
        
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void setPercentageForAllRooms(int[] percentages, String[] names, String itemName) {
        try {
            System.out.println("\n=== Set " + itemName + " for All Rooms ===");
            System.out.print("Enter " + itemName + " percentage (0-100) for all rooms: ");
            int percentage = readIntInput();
        
            if (percentage < 0 || percentage > 100) {
                throw new IllegalArgumentException("Invalid percentage. Please enter a value between 0 and 100");
            }
        
            for (int i = 0; i < NUM_ROOMS; i++) {
                percentages[i] = percentage;
            }
            
            System.out.println("All rooms " + itemName + " set to " + percentage + "%");
        
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void controlLights(int[] lights) {
        controlPercentageSystem(lights, ROOM_NAMES, "Lights", "light");
    }

    public static void controlWindows(int[] windows) {
        controlPercentageSystem(windows, ROOM_NAMES, "Windows", "window");
    }
}