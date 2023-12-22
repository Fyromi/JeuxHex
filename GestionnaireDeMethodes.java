import java.util.Scanner;

public class GestionnaireDeMethodes {

    /**
     * Affiche le menu, permet au joueur de choisir son mode de jeu ou de lire les règles
     */
    public static void menu(){

        Scanner scanner = new Scanner(System.in);

        String choixMenu;
        String affichageMenu =
                        "(1) Lancer le mode Classique (joueur contre joueur)\n" +
                        "(2) Lancer le mode Spécial (joueur contre bot)\n" +
                        "(3) Lancer le mode Aléatoire\n" +
                        "(4) Règles\n" +
                        "(5) Quitter\n";

        System.out.println("\nBienvenue sur le jeu Hex !");

        do{
            System.out.println();
            System.out.println(affichageMenu);
            System.out.print("Choisissez une option: ");
            choixMenu = scanner.nextLine();
            System.out.println();

            switch(choixMenu) {
                case "1":
                    Methodes.jeuClassique();
                    break;

                case "2":
                    Methodes.jeuSpecial();
                    break;

                case "3":
                    System.out.println("3");

                    Methodes.jeuAleatoire();
                    break;

                case "4":
                    Methodes.regles();
                    break;

                case "5":
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Erreur de saisie");
                    break;
            }
        } while(!choixMenu.equals("5"));
        scanner.close();
    }
}