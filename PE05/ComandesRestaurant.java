package PE05;

import java.util.Scanner;

/**
 * Restaurant Order Management System
 * Handles order creation, modification, and ticket generation with VAT calculation
 */
public class ComandesRestaurant {

    // ========== CONSTANTS ==========
    private static final double VAT_RATE = 0.10; // 10% VAT rate
    
    // ========== COLOR CODES ==========
    private static final String RESET = "\u001B[0m";
    private static final String PRIMARY = "\u001B[38;5;75m";
    private static final String SECONDARY = "\u001B[38;5;110m";
    private static final String ACCENT = "\u001B[38;5;150m";
    private static final String WARNING = "\u001B[38;5;180m";
    private static final String TEXT = "\u001B[38;5;250m";
    private static final String PROMPT = "\u001B[38;5;117m";
    private static final String HIGHLIGHT = "\u001B[38;5;228m";
    
    // ========== SYSTEM COMPONENTS ==========
    private final Scanner scanner = new Scanner(System.in);
    
    // ========== ORDER STATE ==========
    private String clientName = "";                // Customer name
    private String orderDetails = "";              // Formatted order items string
    private double totalWithoutVat = 0.0;          // Subtotal before VAT
    private double vat = 0.0;                      // Calculated VAT amount
    private double totalWithVat = 0.0;             // Final total with VAT
    private boolean hasOrder = false;              // Order existence flag

    // ========== MAIN AND CORE METHODS ==========
    
    /**
     * Application entry point
     */
    public static void main(String[] args) {
        ComandesRestaurant restaurant = new ComandesRestaurant();
        restaurant.run();
    }

    /**
     * Main application loop - displays menu and processes user selections
     */
    public void run() {
        boolean programRunning = true;
        
        while (programRunning) {
            int choice = showMenu();
            
            switch (choice) {
                case 1:
                    createNewOrder();
                    break;
                case 2:
                    updateOrder();
                    break;
                case 3:
                    showLastTicket();
                    break;
                case 4:
                    System.out.println("\n" + ACCENT + "------------------------------------" + RESET);
                    System.out.println(ACCENT + "========= FINS LA PROPERA! =========" + RESET);
                    System.out.println(ACCENT + "------------------------------------" + RESET);
                    programRunning = false;
                    break;
            }
        }
    }

    // ========== MENU AND INPUT METHODS ==========
    
    /**
     * Displays main menu and captures user selection
     * @return Validated user choice between 1-4
     */
    public int showMenu() {
        System.out.println("\n" + PRIMARY + "------------------------------------" + RESET);
        System.out.println(PRIMARY + "==== GESTIÓ COMANDES RESTAURANT ====" + RESET);
        System.out.println(PRIMARY + "------------------------------------" + RESET);
        System.out.println(TEXT + "1. Crear nova comanda" + RESET);
        System.out.println(TEXT + "2. Actualitzar comanda anterior" + RESET);
        System.out.println(TEXT + "3. Visualitzar últim tiquet" + RESET);
        System.out.println(TEXT + "4. Sortir" + RESET);

        return readIntInput(1, 4);
    }

    /**
     * Reads and validates integer input within specified range
     * @param minValue Minimum acceptable value
     * @param maxValue Maximum acceptable value
     * @return Validated integer input
     */
    public int readIntInput(int minValue, int maxValue) {
        while (true) {
            try {
                System.out.print(PROMPT + "> Tria una opció: " + RESET);
                String inputLine = scanner.nextLine();
                int value = Integer.parseInt(inputLine);

                if (value < minValue || value > maxValue) {
                    System.out.print(WARNING + "ERROR: Entra un número entre " + minValue + " i " + maxValue + ".\n" + RESET);
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.print(WARNING + "ERROR: Entra un número vàlid.\n" + RESET);
            }
        }
    }

    /**
     * Reads and validates non-empty string input
     * @param prompt Message to display to user
     * @return Non-empty validated string
     */
    private String readNonEmptyString(String prompt) {
        String input;
        do {
            System.out.print(PROMPT + prompt + RESET);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(WARNING + "ERROR: El camp no pot estar buit." + RESET);
            }
        } while (input.isEmpty());
        return input;
    }

    /**
     * Reads and validates yes/no input
     * @param prompt Message to display to user
     * @return "s" for yes or "n" for no
     */
    private String readYesNo(String prompt) {
        String input;
        do {
            System.out.print(PROMPT + prompt + RESET);
            input = scanner.nextLine().trim().toLowerCase();
            if (!input.equals("s") && !input.equals("n")) {
                System.out.println(WARNING + "ERROR: Introdueix 's' o 'n'." + RESET);
            }
        } while (!input.equals("s") && !input.equals("n"));
        return input;
    }

    /**
     * Reads and validates positive decimal number
     * @param prompt Message to display to user
     * @return Validated positive double value
     */
    private double readPositiveDouble(String prompt) {
        while (true) {
            try {
                System.out.print(PROMPT + prompt + RESET);
                String input = scanner.nextLine().trim().replace(',', '.');
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println(WARNING + "ERROR: El preu ha de ser major que 0." + RESET);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println(WARNING + "ERROR: Introdueix un número decimal vàlid." + RESET);
            }
        }
    }

    /**
     * Reads and validates positive integer
     * @param prompt Message to display to user
     * @return Validated positive integer
     */
    private int readPositiveInt(String prompt) {
        while (true) {
            try {
                System.out.print(PROMPT + prompt + RESET);
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println(WARNING + "ERROR: La quantitat ha de ser major que 0." + RESET);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println(WARNING + "ERROR: Introdueix un número enter vàlid." + RESET);
            }
        }
    }

    // ========== ORDER MANAGEMENT METHODS ==========
    
    /**
     * Creates a new order - main order creation workflow
     * Resets order state and collects customer information
     */
    private void createNewOrder() {
        System.out.println("\n" + SECONDARY + "------------------------------------" + RESET);
        System.out.println(SECONDARY + "=========== NOVA COMANDA ===========" + RESET);
        System.out.println(SECONDARY + "------------------------------------" + RESET);

        // Collect customer information
        clientName = readNonEmptyString("> Introdueix el nom del client: ");

        // Reset order state for new order
        orderDetails = "";
        totalWithoutVat = 0.0;
        vat = 0.0;
        totalWithVat = 0.0;

        // Product addition loop
        boolean addProducts = true;
        while (addProducts) {
            addProductToOrder();

            String response = readYesNo("> Vols afegir un altre producte? (s/n): ");
            if (response.equals("n")) {
                addProducts = false;
            }
        }

        hasOrder = true;

        // Calculate final totals
        calculateTotals();

        // Display generated ticket
        System.out.println(ACCENT + "\nS'està generant el tiquet..." + RESET);
        System.out.println("\n" + HIGHLIGHT + "------------------------------------" + RESET);
        System.out.println(HIGHLIGHT + "============== TIQUET ==============" + RESET);
        System.out.println(HIGHLIGHT + "------------------------------------" + RESET);
        showTicket();

        System.out.println(ACCENT + "Comanda enregistrada correctament." + RESET);
    }

    /**
     * Updates existing order by adding additional products
     * Requires an active order to be present
     */
    private void updateOrder() {
        if (!hasOrder) {
            System.out.println(WARNING + "No hi ha cap comanda enregistrada" + RESET);
            return;
        }

        System.out.println("\n" + SECONDARY + "------------------------------------" + RESET);
        System.out.println(SECONDARY + "======= ACTUALITZAR COMANDA ========" + RESET);
        System.out.println(SECONDARY + "------------------------------------" + RESET);

        // Additional products loop
        boolean addingProducts = true;
        while (addingProducts) {
            addProductToOrder(); // Reuse product addition method

            String response = readYesNo("> Vols afegir més productes? (s/n): ");
            if (response.equals("n")) {
                addingProducts = false;
            }
        }

        // Recalculate totals with new items
        calculateTotals();

        // Display updated ticket
        System.out.println(ACCENT + "\nS'està actualitzant la comanda..." + RESET);
        System.out.println("\n" + HIGHLIGHT + "------------------------------------" + RESET);
        System.out.println(HIGHLIGHT + "======== TIQUET ACTUALITZAT ========" + RESET);
        System.out.println(HIGHLIGHT + "------------------------------------" + RESET);
        showTicket();

        System.out.println(ACCENT + "Comanda actualitzada correctament." + RESET);
    }

    /**
     * Displays the most recent order ticket
     * Requires an active order to be present
     */
    private void showLastTicket() {
        if (!hasOrder) {
            System.out.println(WARNING + "No hi ha cap comanda enregistrada" + RESET);
            return;
        }

        System.out.println("\n" + PRIMARY + "------------------------------------" + RESET);
        System.out.println(PRIMARY + "=========== ÚLTIM TIQUET ===========" + RESET);
        System.out.println(PRIMARY + "------------------------------------" + RESET);
        showTicket();
    }

    // ========== ORDER PROCESSING METHODS ==========
    
    /**
     * Adds a single product to the current order
     * Collects product details and updates order state
     */
    private void addProductToOrder() {
        System.out.println(); // Separator line

        String productName = readNonEmptyString("> Introdueix el producte: ");
        double unitPrice = readPositiveDouble("> Preu unitari (€): ");
        int quantity = readPositiveInt("> Quantitat: ");

        double subtotal = unitPrice * quantity;

        // Format and append product line to order details
        String formattedLine = String.format("%-15s %-10d %-10.2f %-10.2f%n",
                productName, quantity, unitPrice, subtotal);
        orderDetails += formattedLine;

        // Update running total
        totalWithoutVat += subtotal;
    }

    /**
     * Calculates VAT and final total based on current subtotal
     */
    private void calculateTotals() {
        vat = totalWithoutVat * VAT_RATE;
        totalWithVat = totalWithoutVat + vat;
    }

    /**
     * Displays formatted ticket with all order details and totals
     */
    private void showTicket() {
        System.out.println(TEXT + "Client: " + clientName + RESET);
        System.out.println();

        // Header with currency indicator
        System.out.printf(TEXT + "%-15s %-10s %-12s %-10s%n" + RESET,
                "Producte", "Quantitat", "Preu unit.", "Subtotal");
        System.out.println(TEXT + "-----------------------------------------------" + RESET);

        // Display products (without currency symbol)
        System.out.print(TEXT + orderDetails + RESET);

        // Separator and totals with currency
        System.out.println(TEXT + "-----------------------------------------------" + RESET);
        System.out.printf(ACCENT + "%-15s %25.2f €%n" + RESET, "Total sense IVA:", totalWithoutVat);
        System.out.printf(ACCENT + "%-15s %25.2f €%n" + RESET, "IVA (" + (int) (VAT_RATE * 100) + "%):", vat);
        System.out.printf(HIGHLIGHT + "%-15s %25.2f €%n" + RESET, "TOTAL A PAGAR:", totalWithVat);
        System.out.println(TEXT + "-----------------------------------------------" + RESET);
    }
}