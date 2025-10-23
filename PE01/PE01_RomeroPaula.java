

import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class PE01_RomeroPaula {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //PART 1 i 2: demanar i declarar variables
        //Utilitzem strings perquè tots son sèries de caracters
        System.out.println("---REGISTRE---");
        System.out.println("Introdueix el teu nom");
        String name = scanner.nextLine();
        System.out.println("Introdueix els teus cognom");
        String surname = scanner.nextLine();
        System.out.println("Introdueix la teva data de naixement (YYYY-MM-DD)");
        String birthdate = scanner.nextLine();
        System.out.println("Introdueix la teva ciutat");
        String city = scanner.nextLine();
        System.out.println("Introdueix el teu codi postal");
        String postalCode = scanner.nextLine();
        System.out.println("Introdueix el nom d'usuari");
        String username = scanner.nextLine();
        System.out.println("Introdueix la contrasenya");
        String password = scanner.nextLine();
        System.out.println("Ets estudiant?");
        char resposta = scanner.next().charAt(0); //Char per obtenir la resposta si/no, index 0 agafa el primer caracter, per tant ignorarà accents o altres coses
        boolean student = Character.toLowerCase(resposta) == 's'; //Fem que el char anterior sigui minúsucla, i si el char és igual a "s", el boolean student serà true

        System.out.println("\n El registre s'ha fet correctament");

        //PART 3: inici de sessió
        scanner.nextLine(); // Buida el nextLine, serveix per arreglar bug amb el scanner que no espera per introduir l'usuari
        System.out.println("\n ---INICI DE SESSIÓ---");
        System.out.println("Introdueix l'usuari");
        String userInput = scanner.nextLine();
        System.out.println("Introdueix la contrasenya");
        String passInput = scanner.nextLine();

        //PART 4: Comprovació de dades
        boolean login = (username.equals(userInput) && password.equals(passInput));

        //PART 5: Mostrar si l'inici de sessió ha sigut correcte
        System.out.println("\n Inici de sessió: " + login);

        //PART 6: Mostrar dades de l'usuari
        LocalDate dateOfBirth = LocalDate.parse(birthdate); //Converteix String a objecte LocalDate
        LocalDate actualDate = LocalDate.now(); //Obté la data actual

        //Calcula la diferència entre les dues dates
        int age = Period.between(dateOfBirth, actualDate).getYears(); //Extreu només els anys

        System.out.println("\n ---DADES DE L'USUARI---");
        System.out.println("Nom complet: " + name + " " + surname);
        System.out.println("Edat: " + age);
        System.out.println("Major d'edat: " + (age>=18));
        System.out.println("Estudiant: " + student);

        scanner.close();
    }
}
