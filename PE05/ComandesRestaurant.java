package PE05;

import java.util.Scanner;

/**
 * Restaurant Order Management System
 * Handles order creation, modification, and ticket generation with VAT calculation
 */
public class ComandesRestaurant {

    // ========== CONSTANTS ==========
    private static final double VAT_RATE = 0.10; // 10% VAT rate
    
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
                    System.out.println("\n------------------------------------");
                    System.out.println("========= FINS LA PROPERA! =========");
                    System.out.println("------------------------------------");
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
        System.out.println("\n------------------------------------");
        System.out.println("==== GESTIÓ COMANDES RESTAURANT ====");
        System.out.println("------------------------------------");
        System.out.println("1. Crear nova comanda");
        System.out.println("2. Actualitzar comanda anterior");
        System.out.println("3. Visualitzar últim tiquet");
        System.out.println("4. Sortir");

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
                System.out.print("> Tria una opció: ");
                String inputLine = scanner.nextLine();
                int value = Integer.parseInt(inputLine);

                if (value < minValue || value > maxValue) {
                    System.out.print("ERROR: Entra un número entre " + minValue + " i " + maxValue + ".\n");
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.print("ERROR: Entra un número vàlid.\n");
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
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("ERROR: El camp no pot estar buit.");
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
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();
            if (!input.equals("s") && !input.equals("n")) {
                System.out.println("ERROR: Introdueix 's' o 'n'.");
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
                System.out.print(prompt);
                String input = scanner.nextLine().trim().replace(',', '.');
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("ERROR: El preu ha de ser major que 0.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Introdueix un número decimal vàlid.");
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
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("ERROR: La quantitat ha de ser major que 0.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Introdueix un número enter vàlid.");
            }
        }
    }

    // ========== ORDER MANAGEMENT METHODS ==========
    
    /**
     * Creates a new order - main order creation workflow
     * Resets order state and collects customer information
     */
    private void createNewOrder() {
        System.out.println("\n------------------------------------");
        System.out.println("=========== NOVA COMANDA ===========");
        System.out.println("------------------------------------");

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
        System.out.println("\nS'està generant el tiquet...");
        System.out.println("\n------------------------------------");
        System.out.println("============== TIQUET ==============");
        System.out.println("------------------------------------");
        showTicket();

        System.out.println("Comanda enregistrada correctament.");
    }

    /**
     * Updates existing order by adding additional products
     * Requires an active order to be present
     */
    private void updateOrder() {
        if (!hasOrder) {
            System.out.println("No hi ha cap comanda enregistrada");
            return;
        }

        System.out.println("\n------------------------------------");
        System.out.println("======= ACTUALITZAR COMANDA ========");
        System.out.println("------------------------------------");

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
        System.out.println("\nS'està actualitzant la comanda...");
        System.out.println("\n------------------------------------");
        System.out.println("======== TIQUET ACTUALITZAT ========");
        System.out.println("------------------------------------");
        showTicket();

        System.out.println("Comanda actualitzada correctament.");
    }

    /**
     * Displays the most recent order ticket
     * Requires an active order to be present
     */
    private void showLastTicket() {
        if (!hasOrder) {
            System.out.println("No hi ha cap comanda enregistrada");
            return;
        }

        System.out.println("\n----------------------------------");
        System.out.println("=========== ÚLTIM TIQUET ===========");
        System.out.println("------------------------------------");
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
        System.out.println("Client: " + clientName);
        System.out.println();

        // Header with currency indicator
        System.out.printf("%-15s %-10s %-12s %-10s%n",
                "Producte", "Quantitat", "Preu unit.", "Subtotal");
        System.out.println("-----------------------------------------------");

        // Display products (without currency symbol)
        System.out.print(orderDetails);

        // Separator and totals with currency
        System.out.println("-----------------------------------------------");
        System.out.printf("%-15s %25.2f €%n", "Total sense IVA:", totalWithoutVat);
        System.out.printf("%-15s %25.2f €%n", "IVA (" + (int) (VAT_RATE * 100) + "%):", vat);
        System.out.printf("%-15s %25.2f €%n", "TOTAL A PAGAR:", totalWithVat);
        System.out.println("-----------------------------------------------");
    }
}