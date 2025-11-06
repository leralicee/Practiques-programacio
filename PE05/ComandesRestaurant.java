package PE05;
import java.util.Scanner;

public class ComandesRestaurant {

    private final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        ComandesRestaurant restaurant = new ComandesRestaurant();
        restaurant.run();
    }

    public void run() {
        boolean programRunning = true;
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
                    System.out.println("\n------------------------------------");
                    System.out.println("========= FINS LA PROPERA! =========");
                    System.out.println("------------------------------------");
                    programRunning = false;
                    break;
            }
        }
    }

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

    public int readIntInput() {
        return readIntInput(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
}
