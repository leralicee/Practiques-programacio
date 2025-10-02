package PE02.src;

import java.util.Scanner;

public class HistoriaVarisFinals {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        //Variables to store decisions
        boolean hasWatch = false;
        boolean hasConfessed = false;
        boolean hasAskedAbtMagic = false;
        boolean hasTriedWatch = false;
        boolean hasAcceptedCoin = false;
        boolean hasFollowedMap = false;
        String finalEnding = "";

        //Start of the story
        System.out.println("--- The Mystery of the Antique Watch ---\n");
        System.out.println("You are in an antique shop. The owner shows you a very old pocket watch with a strange engraved symbol.");
        System.out.println("Suddenly, he receives a phone call and has to leave, leaving you alone in the shop.\n");

        //Decision 0
        System.out.println("Do you want to take the watch without permission? (yes/no)");
        String decision0 = scanner.nextLine().toLowerCase();
        if (decision0.equals("yes")){
            hasWatch = true;
            System.out.println("\nYou took the watch. The owner returns and looks at you suspiciously.");

            //Decision 1.1
            System.out.println("Do you want to confess to taking the watch? (yes/no)");
            String decision1 = scanner.nextLine().toLowerCase();
            if (decision1.equals("yes")){
                hasConfessed = true;
                System.out.println("\nThe owner is surprised but appreciates your honesty. He tells you the watch is rumored to have magical properties.");

                //Decision 1.1.1
                System.out.println("Do you want to ask about the magic of the watch? (yes/no)");
                String decision2 = scanner.nextLine().toLowerCase();
                if (decision2.equals("yes")){
                    hasAskedAbtMagic = true;
                    System.out.println("\nThe owner explains that the watch can manipulate time, but only for 5 minutes.");

                    //Decision 1.1.1.1
                    System.out.println("Do you want to try using the watch's magic? (yes/no)");
                    String decision3 = scanner.nextLine().toLowerCase();
                    if (decision3.equals("yes")){
                        hasTriedWatch = true;
                        finalEnding = "Heroic";
                        System.out.println("You used the watch to go back in time and prevent an accident occurring outside. HEROIC ENDING!");
                    } else {
                        finalEnding = "Mysterious";
                        System.out.println("The owner gifts you the watch, but you never end up using it. MYSTERIOUS ENDING!");
                    }
                } else {
                    finalEnding = "Neutral";
                    System.out.println("You return the watch and leave the shop. NEUTRAL ENDING!");
                }
            } else {
                finalEnding = "Tragic";
                System.out.println("You try to hide it, but the owner notices and you are arrested for theft. TRAGIC ENDING!");
            }
        
        } else {
            System.out.println("\nYou decide not to take the watch. The owner returns and rewards you with an old coin.");
            
            //Decision 2.1
            System.out.println("Do you want to accept the coin? (yes/no)");
            String decision1Alt = scanner.nextLine().toLowerCase();
            if (decision1Alt.equals("yes")){
                hasAcceptedCoin = true;
                System.out.println("\nYou discover a map engraved on the coin.");

                //Decision 2.1.1
                System.out.println("Do you want to follow the map? (yes/no)");
                String decision2Alt = scanner.nextLine().toLowerCase();
                if (decision2Alt.equals("yes")){
                    hasFollowedMap = true;
                    finalEnding = "Adventurous";
                    System.out.println("You follow the map and find a hidden treasure. ADVENTUROUS ENDING!");
                } else {
                    finalEnding = "Neutral";
                    System.out.println("You decide not to follow the map and keep the coin, staying curious. NEUTRAL ENDING!");
                }
            } else {
                finalEnding = "NEW BEGINNING";
                System.out.println("You politely decline the coin and the shop owner asks you to be his apprentice. NEW BEGINNING ENDING!");
            }

        }
        
        // Results of the story
        System.out.println("\n\n--- Summary of your adventure ---\n");
        System.out.println("Ending obtained: " + finalEnding);
        System.out.println("Has taken the watch: " + hasWatch);
        
        if (hasWatch) {
            System.out.println("Has confessed: " + hasConfessed);
            if (hasConfessed) {
                System.out.println("Has asked about magic: " + hasAskedAbtMagic);
                if (hasAskedAbtMagic) {
                    System.out.println("Has tried the watch: " + hasTriedWatch);
                }
            }
        } else {
            System.out.println("Has accepted the coin: " + hasAcceptedCoin);
            if (hasAcceptedCoin) {
                System.out.println("Has followed the map: " + hasFollowedMap);
            }
        }

        if (hasConfessed && hasAskedAbtMagic && hasTriedWatch) {
        System.out.println("\nYou have achieved the best possible outcome! Congratulations!");
}

        scanner.close();
    }
}
