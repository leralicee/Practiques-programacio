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
    
    // Colors for console (ANSI codes)
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";
    
    // =========================================================================
    // MAIN PROGRAM FLOW
    // =========================================================================
    
    public static void main(String[] args) {
        boolean programRunning = true;

        // Initialize house with random values
        Object[] houseData = initializeHouse();
        int[] lights = (int[]) houseData[0];
        int[] windows = (int[]) houseData[1];
        boolean roombaStatus = (boolean) houseData[2];
        int thermostatTemp = (int) houseData[3];
        int targetTemp = (int) houseData[4];
        boolean autoMode = true;
        
        // Main program loop
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
                    printColor(BLUE, "╔════════════════════════════════════════╗");
                    printColor(BLUE, "║          Exiting the program           ║");
                    printColor(BLUE, "║               Goodbye!                 ║");
                    printColor(BLUE, "╚════════════════════════════════════════╝");
                    programRunning = false;
                    break;
            }
        }
    }

    // =========================================================================
    // INITIALIZATION METHODS
    // =========================================================================
    
    /**
     * Initializes the house with random values for all systems
     * @return Object array containing: lights[], windows[], roombaStatus, thermostatTemp, targetTemp
     */
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

    // =========================================================================
    // INPUT HANDLING METHODS
    // =========================================================================
    
    /**
     * Reads and validates integer input from user with optional range validation
     * @param minValue Minimum allowed value (use Integer.MIN_VALUE for no minimum)
     * @param maxValue Maximum allowed value (use Integer.MAX_VALUE for no maximum)
     * @return Valid integer input within specified range
     */
    public static int readIntInput(int minValue, int maxValue) {
        while (true) {
            try {
                System.out.print(CYAN + "> " + RESET);
                String inputLine = scanner.nextLine(); 
                int value = Integer.parseInt(inputLine);
                
                // Validate range if specified
                if (value < minValue || value > maxValue) {
                    System.out.print(RED + "ERROR: Please enter a number between " + minValue + " and " + maxValue + PURPLE + BOLD + ".\nTry again: " + RESET);
                    continue;
                }
                
                return value;
            } catch (NumberFormatException e) {
                System.out.print(RED + "ERROR: Please enter a valid number." + PURPLE + BOLD + "\nTry again: " + RESET);
            }
        }
    }

    /**
     * Reads and validates integer input from user without range validation
     * @return Valid integer input
     */
    public static int readIntInput() {
        return readIntInput(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    // =========================================================================
    // VISUAL ENHANCEMENT METHODS
    // =========================================================================
    
    /**
     * Prints a message with specified color
     * @param color ANSI color code
     * @param message Message to display
     */
    public static void printColor(String color, String message) {
        System.out.println(color + message + RESET);
    }

    /**
     * Prints a formatted header with borders
     * @param title Header title
     */
    public static void printHeader(String title) {
        String border = "═".repeat(title.length() + 4);
        printColor(BLUE, "╔" + border + "╗");
        printColor(BLUE, "║  " + BOLD + title + RESET + BLUE + "  ║");
        printColor(BLUE, "╚" + border + "╝");
    }

    /**
     * Prints a section title with underline
     * @param title Section title
     */
    public static void printSection(String title) {
        printColor(CYAN, "\n" + BOLD + "> " + title + RESET);
        printColor(CYAN, "─".repeat(title.length() + 2));
    }

    /**
     * Creates a visual percentage bar
     * @param percentage Percentage value (0-100)
     * @return String representation of the percentage bar
     */
    public static String getPercentageBar(int percentage) {
        int bars = percentage / 10;
        String bar = "█".repeat(bars);
        String empty = "░".repeat(10 - bars);
        return "[" + GREEN + bar + RESET + empty + "]";
    }

    // =========================================================================
    // MENU AND NAVIGATION METHODS
    // =========================================================================
    
    /**
     * Displays the main menu and gets user choice
     * @return User menu choice (1-6)
     */
    public static int showMenu() {
        System.out.println();
        printHeader("DOMOTIC HOUSE CONTROL");
        
        System.out.println();
        printColor(GREEN, BOLD + "MAIN MENU:" + RESET);
        printColor(YELLOW, "1. Control Lights");
        printColor(YELLOW, "2. Control Windows");
        printColor(YELLOW, "3. Control Roomba");
        printColor(YELLOW, "4. Control Thermostat");
        printColor(YELLOW, "5. Show Overview");
        printColor(YELLOW, "6. Exit");
        
        System.out.println();
        printColor(PURPLE, BOLD + "Enter your choice (1-6): " + RESET);
        return readIntInput(1, 6);
    }

    // =========================================================================
    // LIGHTS CONTROL METHODS
    // =========================================================================
    
    /**
     * Main lights control interface
     * @param lights Array of light percentages for each room
     */
    public static void controlLights(int[] lights) {
        controlPercentageSystem(lights, ROOM_NAMES, "Lights", "light");
    }

    // =========================================================================
    // WINDOWS CONTROL METHODS
    // =========================================================================
    
    /**
     * Main windows control interface
     * @param windows Array of window percentages for each room
     */
    public static void controlWindows(int[] windows) {
        controlPercentageSystem(windows, ROOM_NAMES, "Windows", "window");
    }

    // =========================================================================
    // PERCENTAGE-BASED SYSTEMS (LIGHTS & WINDOWS)
    // =========================================================================
    
    /**
     * Generic control system for percentage-based devices (lights/windows)
     * @param percentages Array of percentage values to control
     * @param names Array of room names
     * @param systemName Name of the system being controlled
     * @param itemName Name of the item being controlled
     */
    public static void controlPercentageSystem(int[] percentages, String[] names, String systemName, String itemName) {
        boolean menuRunning = true;
        
        while (menuRunning) {
            System.out.println();
            printHeader(systemName + " CONTROL");
            
            printColor(GREEN, BOLD + "OPTIONS:" + RESET);
            printColor(YELLOW, "1. Show current " + itemName + " settings");
            printColor(YELLOW, "2. Set " + itemName + " percentage for a room");
            printColor(YELLOW, "3. Set " + itemName + " percentage for all rooms");
            printColor(YELLOW, "4. Back to main menu");
            
            System.out.println();
            printColor(PURPLE, BOLD + "Enter your choice (1-4): " + RESET);
            
            int choice = readIntInput(1, 4);
            
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
            }
        }
    }

    /**
     * Displays current percentage settings for all rooms
     * @param percentages Array of percentage values
     * @param names Array of room names
     * @param title Display title
     */
    public static void showPercentageSettings(int[] percentages, String[] names, String title) {
        System.out.println();
        printHeader(title);
        for (int i = 0; i < NUM_ROOMS; i++) {
            String percentageBar = getPercentageBar(percentages[i]);
            printColor(CYAN, String.format("%d. %s: %s %d%%", 
                i + 1, names[i], percentageBar, percentages[i]));
        }
    }

    /**
     * Sets percentage for a specific room
     * @param percentages Array of percentage values to modify
     * @param names Array of room names
     * @param itemName Name of the item being set
     */
    public static void setPercentageForRoom(int[] percentages, String[] names, String itemName) {
        try {
            System.out.println();
            printHeader("SET " + itemName.toUpperCase() + " FOR ROOM");
            
            printColor(GREEN, BOLD + "AVAILABLE ROOMS:" + RESET);
            for (int i = 0; i < NUM_ROOMS; i++) {
                printColor(YELLOW, String.format("%d. %s", i + 1, names[i]));
            }
        
            System.out.println();
            printColor(PURPLE, BOLD + "Enter room number (1-" + NUM_ROOMS + "): " + RESET);
            int roomNumber = readIntInput(1, NUM_ROOMS);
        
            System.out.println();
            printColor(PURPLE, BOLD + "Enter " + itemName + " percentage (0-100) for " + names[roomNumber - 1] + ": " + RESET);
            int percentage = readIntInput(0, 100);
        
            percentages[roomNumber - 1] = percentage;
            printColor(GREEN, "SUCCESS: " + names[roomNumber - 1] + " " + itemName + " set to " + percentage + "%");
        
        } catch (IllegalArgumentException e) {
            printColor(RED, "ERROR: " + e.getMessage());
        }
    }

    /**
     * Sets the same percentage for all rooms
     * @param percentages Array of percentage values to modify
     * @param names Array of room names
     * @param itemName Name of the item being set
     */
    public static void setPercentageForAllRooms(int[] percentages, String[] names, String itemName) {
        try {
            System.out.println();
            printHeader("SET " + itemName.toUpperCase() + " FOR ALL ROOMS");
            
            System.out.println();
            printColor(PURPLE, BOLD + "Enter " + itemName + " percentage (0-100) for all rooms: " + RESET);
            int percentage = readIntInput(0, 100);
        
            for (int i = 0; i < NUM_ROOMS; i++) {
                percentages[i] = percentage;
            }
            
            printColor(GREEN, "SUCCESS: All rooms " + itemName + " set to " + percentage + "%");
        
        } catch (IllegalArgumentException e) {
            printColor(RED, "ERROR: " + e.getMessage());
        }
    }

    // =========================================================================
    // ROOMBA CONTROL METHODS
    // =========================================================================
    
    /**
     * Displays current Roomba status
     * @param roombaStatus Current Roomba status (true = ON, false = OFF)
     */
    public static void showRoombaStatus(boolean roombaStatus) {
        System.out.println();
        printHeader("ROOMBA STATUS");
        if (roombaStatus) {
            printColor(GREEN, "Status: ON - Cleaning in progress");
        } else {
            printColor(YELLOW, "Status: OFF - Stationary at charging dock");
        }
    }

    /**
     * Main Roomba control interface
     * @param roombaStatus Current Roomba status
     * @return Updated Roomba status
     */
    public static boolean controlRoomba(boolean roombaStatus) {
        boolean roombaMenuRunning = true;
        boolean currentStatus = roombaStatus;
    
        while (roombaMenuRunning) {
            System.out.println();
            printHeader("ROOMBA CONTROL");
            
            printColor(GREEN, BOLD + "OPTIONS:" + RESET);
            printColor(YELLOW, "1. Show Roomba status");
            printColor(YELLOW, "2. Turn Roomba ON");
            printColor(YELLOW, "3. Turn Roomba OFF");
            printColor(YELLOW, "4. Back to main menu");
            
            System.out.println();
            printColor(PURPLE, BOLD + "Enter your choice (1-4): " + RESET);
        
            int roombaChoice = readIntInput(1, 4);
        
            switch (roombaChoice) {
                case 1:
                    showRoombaStatus(currentStatus);
                    break;
                case 2:
                    if (currentStatus) {
                        printColor(YELLOW, "INFO: Roomba is already ON");
                    } else {
                        currentStatus = true;
                        printColor(GREEN, "SUCCESS: Roomba turned ON - Cleaning started");
                    }
                    break;
                case 3:
                    if (!currentStatus) {
                        printColor(YELLOW, "INFO: Roomba is already OFF");
                    } else {
                        currentStatus = false;
                        printColor(GREEN, "SUCCESS: Roomba turned OFF - Returning to dock");
                    }
                    break;
                case 4:
                    roombaMenuRunning = false;
                    break;
            }
        }
    
        return currentStatus;
    }

    // =========================================================================
    // THERMOSTAT CONTROL METHODS
    // =========================================================================
    
    /**
     * Displays current thermostat status and settings
     * @param currentTemp Current temperature
     * @param targetTemp Target temperature
     * @param autoMode Current mode (true = AUTO, false = MANUAL)
     */
    public static void showThermostatStatus(int currentTemp, int targetTemp, boolean autoMode) {
        System.out.println();
        printHeader("THERMOSTAT STATUS");
        
        printColor(CYAN, "Current temperature: " + BOLD + currentTemp + "°C" + RESET);
        printColor(CYAN, "Target temperature: " + BOLD + targetTemp + "°C" + RESET);
    
        if (autoMode) {
            if (currentTemp < targetTemp) {
                printColor(GREEN, "Heating: ON - Warming to " + targetTemp + "°C");
            } else if (currentTemp > targetTemp) {
                printColor(BLUE, "Cooling: ON - Cooling to " + targetTemp + "°C");
            } else {
                printColor(GREEN, "Temperature: Optimal (" + targetTemp + "°C)");
            }
        } else {
            if (currentTemp < targetTemp) {
                printColor(YELLOW, "Status: " + (targetTemp - currentTemp) + "°C below target");
            } else if (currentTemp > targetTemp) {
                printColor(YELLOW, "Status: " + (currentTemp - targetTemp) + "°C above target");
            } else {
                printColor(GREEN, "Temperature: At target (" + targetTemp + "°C)");
            }
        }
    
        printColor(PURPLE, "Mode: " + (autoMode ? BOLD + GREEN + "AUTO" : BOLD + YELLOW + "MANUAL") + RESET);
    }

    /**
     * Sets a new target temperature
     * @param currentTarget Current target temperature
     * @return New target temperature
     */
    public static int setTargetTemperature(int currentTarget) {
        System.out.println();
        printHeader("SET TARGET TEMPERATURE");
        
        printColor(CYAN, "Current target: " + BOLD + currentTarget + "°C" + RESET);
        System.out.println();
        printColor(PURPLE, BOLD + "Enter new target temperature (" + MIN_TEMP + "-" + MAX_TEMP + "°C): " + RESET);
        int newTarget = readIntInput(MIN_TEMP, MAX_TEMP);
    
        printColor(GREEN, "SUCCESS: Target temperature set to " + newTarget + "°C");
        return newTarget;
    }

    /**
     * Toggles between AUTO and MANUAL mode
     * @param currentMode Current mode
     * @return New mode
     */
    public static boolean toggleAutoMode(boolean currentMode) {
        boolean newMode = !currentMode;
        System.out.println();
        printHeader("TOGGLE MODE");
        if (newMode) {
            printColor(GREEN, "Mode changed to: " + BOLD + "AUTO");
            printColor(GREEN, "System will automatically adjust temperature");
        } else {
            printColor(YELLOW, "Mode changed to: " + BOLD + "MANUAL");
            printColor(YELLOW, "Temperature will remain fixed");
        }
        return newMode;
    }

    /**
     * Main thermostat control interface
     * @param currentTemp Current temperature
     * @param targetTemp Target temperature
     * @param autoMode Current mode
     * @return Object array containing: currentTemp, targetTemp, autoMode
     */
    public static Object[] controlThermostat(int currentTemp, int targetTemp, boolean autoMode) {
        boolean thermostatMenuRunning = true;
        int currentTemperature = currentTemp;
        int targetTemperature = targetTemp;
        boolean currentAutoMode = autoMode;
    
        while (thermostatMenuRunning) {
            System.out.println();
            printHeader("THERMOSTAT CONTROL");
            
            printColor(GREEN, BOLD + "OPTIONS:" + RESET);
            printColor(YELLOW, "1. Show current temperature settings");
            printColor(YELLOW, "2. Set target temperature");
            printColor(YELLOW, "3. Toggle auto/manual mode");
            printColor(YELLOW, "4. Back to main menu");
            
            System.out.println();
            printColor(PURPLE, BOLD + "Enter your choice (1-4): " + RESET);
        
            int thermostatChoice = readIntInput(1, 4);
        
            switch (thermostatChoice) {
                case 1:
                    showThermostatStatus(currentTemperature, targetTemperature, currentAutoMode);
                    break;
                case 2:
                    targetTemperature = setTargetTemperature(targetTemperature);
                    break;
                case 3:
                    currentAutoMode = toggleAutoMode(currentAutoMode);
                    break;
                case 4:
                    thermostatMenuRunning = false;
                    break;
            }
        }

        return new Object[]{currentTemperature, targetTemperature, currentAutoMode};
    }

    // =========================================================================
    // HOUSE OVERVIEW METHOD
    // =========================================================================
    
    /**
     * Displays complete overview of all house systems
     * @param lights Array of light percentages
     * @param windows Array of window percentages
     * @param roombaStatus Roomba status
     * @param thermostatTemp Current thermostat temperature
     * @param targetTemp Target temperature
     * @param autoMode Thermostat mode
     */
    public static void showHouseOverview(int[] lights, int[] windows, boolean roombaStatus, int thermostatTemp, int targetTemp, boolean autoMode) {
        System.out.println();
        printHeader("HOUSE OVERVIEW");
    
        // Rooms
        printSection("ROOM STATUS");
        for (int i = 0; i < NUM_ROOMS; i++) {
            String lightBar = getPercentageBar(lights[i]);
            String windowBar = getPercentageBar(windows[i]);
            printColor(CYAN, String.format("  %d. %s:", i + 1, ROOM_NAMES[i]));
            printColor(CYAN, String.format("     Light: %s %d%%", lightBar, lights[i]));
            printColor(CYAN, String.format("     Window: %s %d%%", windowBar, windows[i]));
            System.out.println();
        }
    
        // Roomba
        printSection("ROOMBA STATUS");
        if (roombaStatus) {
            printColor(GREEN, "  Status: ON - Cleaning in progress");
        } else {
            printColor(YELLOW, "  Status: OFF - Stationary at dock");
        }

        // Thermostat
        printSection("THERMOSTAT STATUS");
        String tempStatus = "";
        if (thermostatTemp < targetTemp) {
            tempStatus = "Heating";
        } else if (thermostatTemp > targetTemp) {
            tempStatus = "Cooling";
        } else {
            tempStatus = "Optimal";
        }
    
        printColor(CYAN, "  Current: " + BOLD + thermostatTemp + "°C" + RESET + CYAN + " | Target: " + BOLD + targetTemp + "°C");
        if (autoMode) {
            printColor(GREEN, "  Mode: AUTO - " + tempStatus);
        } else {
            printColor(YELLOW, "  Mode: MANUAL");
        }
    
        System.out.println();
        printColor(BLUE, "=".repeat(50));
    }
}