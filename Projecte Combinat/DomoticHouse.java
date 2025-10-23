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
        boolean autoMode = true;
        
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
                    roombaStatus = controlRoomba(roombaStatus);
                    break;
                case 4:
                    Object[] newThermostatData = controlThermostat(thermostatTemp, targetTemp, autoMode);
                    thermostatTemp = (int) newThermostatData[0];
                    targetTemp = (int) newThermostatData[1];
                    autoMode = (boolean) newThermostatData[2];
                    break;
                case 5:
                    showHouseOverview(lights, windows, roombaStatus, thermostatTemp, targetTemp, autoMode);
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

    public static void showRoombaStatus(boolean roombaStatus) {
        System.out.println("\n=== Roomba Status ===");
        if (roombaStatus) {
            System.out.println("Status: ON - Cleaning in progress");
        } else {
            System.out.println("Status: OFF - Stationary at charging dock");
        }
    }

    public static boolean controlRoomba(boolean roombaStatus) {
        boolean roombaMenuRunning = true;
        boolean currentStatus = roombaStatus;
    
        while (roombaMenuRunning) {
            System.out.println("\n=== Roomba Control ===");
            System.out.println("1. Show Roomba status");
            System.out.println("2. Turn Roomba ON");
            System.out.println("3. Turn Roomba OFF");
            System.out.println("4. Back to main menu");
            System.out.print("Enter your choice (1-4): ");
        
            int roombaChoice = readIntInput();
        
            switch (roombaChoice) {
                case 1:
                    showRoombaStatus(currentStatus);
                    break;
                case 2:
                    if (currentStatus) {
                        System.out.println("Roomba is already ON");
                    } else {
                        currentStatus = true;
                        System.out.println("Roomba turned ON - Cleaning started");
                    }
                    break;
                case 3:
                    if (!currentStatus) {
                        System.out.println("Roomba is already OFF");
                    } else {
                        currentStatus = false;
                        System.out.println("Roomba turned OFF - Returning to dock");
                    }
                    break;
                case 4:
                    roombaMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    
        return currentStatus;
    }

    public static void showThermostatStatus(int currentTemp, int targetTemp, boolean autoMode) {
        System.out.println("\n=== Thermostat Status ===");
        System.out.println("Current temperature: " + currentTemp + "°C");
        System.out.println("Target temperature: " + targetTemp + "°C");
    
        if (autoMode) {
            if (currentTemp < targetTemp) {
                System.out.println("Heating: ON - Warming to " + targetTemp + "°C");
            } else if (currentTemp > targetTemp) {
                System.out.println("Cooling: ON - Cooling to " + targetTemp + "°C");
            } else {
                System.out.println("Temperature: Optimal (" + targetTemp + "°C)");
            }
        } else {
            if (currentTemp < targetTemp) {
                System.out.println("Status: " + (targetTemp - currentTemp) + "°C below target");
            } else if (currentTemp > targetTemp) {
                System.out.println("Status: " + (currentTemp - targetTemp) + "°C above target");
            } else {
                System.out.println("Temperature: At target (" + targetTemp + "°C)");
            }
        }
    
        System.out.println("Mode: " + (autoMode ? "AUTO" : "MANUAL"));
    }

    public static int setTargetTemperature(int currentTarget) {
        try {
            System.out.println("\n=== Set Target Temperature ===");
            System.out.println("Current target: " + currentTarget + "°C");
            System.out.print("Enter new target temperature (" + MIN_TEMP + "-" + MAX_TEMP + "°C): ");
            int newTarget = readIntInput();
        
            if (newTarget < MIN_TEMP || newTarget > MAX_TEMP) {
                throw new IllegalArgumentException("Temperature must be between " + MIN_TEMP + " and " + MAX_TEMP + "°C");
            }
        
            System.out.println("Target temperature set to " + newTarget + "°C");
            return newTarget;
        
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return currentTarget;
        }
    }

    public static boolean toggleAutoMode(boolean currentMode) {
        boolean newMode = !currentMode;
        System.out.println("\n=== Toggle Mode ===");
        if (newMode) {
            System.out.println("Mode changed to: AUTO");
            System.out.println("System will automatically adjust temperature");
        } else {
            System.out.println("Mode changed to: MANUAL");
            System.out.println("Temperature will remain fixed");
        }
        return newMode;
    }

    public static Object[] controlThermostat(int currentTemp, int targetTemp, boolean autoMode) {
        boolean thermostatMenuRunning = true;
        int currentTemperature = currentTemp;
        int targetTemperature = targetTemp;
        boolean currentAutoMode = autoMode;
    
        while (thermostatMenuRunning) {
            System.out.println("\n=== Thermostat Control ===");
            System.out.println("1. Show current temperature settings");
            System.out.println("2. Set target temperature");
            System.out.println("3. Toggle auto/manual mode");
            System.out.println("4. Back to main menu");
            System.out.print("Enter your choice (1-4): ");
        
            int thermostatChoice = readIntInput();
        
            switch (thermostatChoice) {
                case 1:
                    showThermostatStatus(currentTemperature, targetTemperature, autoMode);
                    break;
                case 2:
                    targetTemperature = setTargetTemperature(targetTemperature);
                    break;
                case 3:
                    autoMode = toggleAutoMode(autoMode);
                    break;
                case 4:
                    thermostatMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        return new Object[]{currentTemperature, targetTemperature, currentAutoMode};
    }

    public static void showHouseOverview(int[] lights, int[] windows, boolean roombaStatus, int thermostatTemp, int targetTemp, boolean autoMode) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("HOUSE OVERVIEW");
        System.out.println("=".repeat(50));
    
        // Rooms
        System.out.println("\nROOM STATUS:");
        for (int i = 0; i < NUM_ROOMS; i++) {
            System.out.printf("  %d. %s: Light %d%% | Window %d%%\n", 
                            i + 1, ROOM_NAMES[i], lights[i], windows[i]);
        }
    
        // Roomba
        System.out.println("\nROOMBA:");
        if (roombaStatus) {
            System.out.println("  Status: ON - Cleaning in progress");
        } else {
            System.out.println("  Status: OFF - Stationary at dock");
        }

        // Thermostat
        System.out.println("\nTHERMOSTAT:");
        String tempStatus = "";
        if (thermostatTemp < targetTemp) {
            tempStatus = "Heating";
        } else if (thermostatTemp > targetTemp) {
            tempStatus = "Cooling";
        } else {
            tempStatus = "Optimal";
        }
    
        System.out.printf("  Current: %d°C | Target: %d°C", thermostatTemp, targetTemp);
        if (autoMode) {
            System.out.printf(" (AUTO - %s)\n", tempStatus);
        } else {
            System.out.printf(" (MANUAL)\n");
        }
    
        System.out.println("=".repeat(50));
    }

    public static void controlLights(int[] lights) {
        controlPercentageSystem(lights, ROOM_NAMES, "Lights", "light");
    }

    public static void controlWindows(int[] windows) {
        controlPercentageSystem(windows, ROOM_NAMES, "Windows", "window");
    }
}