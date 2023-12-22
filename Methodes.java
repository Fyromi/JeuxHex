import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class Methodes {

    static final String ROUGE = "\u001B[31m";
    static final String BLEU = "\u001B[34m";
    static final String RESET = "\u001B[0m";
    static final char VIDE = '◯';
    static final String PION_J1 = ROUGE + '⬤' + RESET;
    static final String PION_J2 = BLEU + '⬤' + RESET;



    public static void regles(){ //Règles du jeu

        System.out.print("A tour de rôle, chaque joueur place un pion de sa couleur sur n'importe\n" +
                "laquelle des cases encore libres. Deux des côtés opposés du plateau sont rouges\n" +
                "et les deux autres bleus. Pour gagner, il faut relier les deux côtés à sa couleur\n" +
                "par une chaîne ininterrompue de pions.\n" +
                "Pour placer un pion, il faut donner les coordoonées en donnant le numéro de la ligne\n" +
                "puis, la lettre de la colonne.\n");
    }

    /**
     * Lance le mode de jeu classique
     */
    public static void jeuClassique(){

        int[][] plateau = choixTaillePlateau();
        int joueur = 1;

        boolean victoireJ1 = false, victoireJ2 = false;

        while (!victoireJ1 && !victoireJ2){
            afficherPlateau(plateau);
            placerPion(plateau, joueur);
            victoireJ1 = verifVictoire(plateau, joueur);
            if(!victoireJ1){
                joueur++;
                afficherPlateau(plateau);
                placerPion(plateau, joueur);
                victoireJ2 = verifVictoire(plateau, joueur);
                if(!victoireJ2) joueur--;
            }
        }
        afficherPlateau(plateau);
        System.out.println("T'as gagné joueur " + joueur + ", bien joué !");
    }

    public static void afficherEnLigne(int occurence, char caractere) {

        for (int i = 0; i < occurence; i++) {

            System.out.print(caractere);

        }
    }


    public static void afficherPlateau(int[][] plateau){

        //affiche une ligne de lettre
        char Lettre = 'A';

        System.out.print(" ");//décale d'un espace le début de la ligne
        for(int i = 0; i<plateau.length;i++){
            System.out.print(ROUGE + " "+Lettre+" " + RESET);
            Lettre++;
        }

        System.out.println();//retour a la ligne

        //affiche une ligne d'underscore
        afficherEnLigne(3,' ');//décale de 3 espace le début de la ligne

        for(int i = 0; i<plateau.length;i++){
            System.out.print(ROUGE+'─'+"  "+RESET);
        }

        System.out.println();//retour a la ligne

        //affiche le tableau avec déclage
        for(int ligne=0;ligne<plateau.length;ligne++){
            if(ligne+1<10)//décale chaque ligne du tableau pour incliner le plateau
                afficherEnLigne(ligne,' ');
            else
                afficherEnLigne(ligne-1,' ');
            System.out.print(BLEU);
            System.out.print(ligne+1+" ");//affiche le numéro de la ligne
            System.out.print((char)92+RESET);//affiche un slash

            for(int colonne =0; colonne<plateau[ligne].length;colonne++){
                if (plateau[ligne][colonne] == 0)
                    System.out.print(" " + VIDE + " ");
                else if (plateau[ligne][colonne] == 1)
                    System.out.print(" " + PION_J1 + " ");
                else
                    System.out.print(" " + PION_J2 + " ");
            }

            System.out.print(BLEU);
            System.out.print((char)92);//affiche un slash
            System.out.println(" " +(ligne+1) + RESET);//affiche le numéro de la ligne
        }
        
        //affiche une ligne d'underscore en fin de plateau
        afficherEnLigne(plateau.length+2,' ');
        for(int i = 0; i<plateau.length;i++){
            System.out.print(ROUGE + "  "+'─' + RESET);
        }

        System.out.println();//retour a la ligne

        //affiche une ligne de lettre en fin de plateau
        afficherEnLigne(plateau.length+3,' ');
        Lettre = 'A';
        for(int i = 0; i<plateau.length;i++){
            System.out.print(ROUGE + "  "+Lettre + RESET);
            Lettre++;
        }
        System.out.println("\n");
    }

    public static void jeuSpecial(){

        System.out.println("Lancement du jeu spécial");
    }


    public static void jeuAleatoire(){

        System.out.println("Lancement du jeu aléatoire");
    }


    public static void placerPion(int[][] plateau, int joueur){

        Scanner scanner = new Scanner(System.in);
        int numeroLigne, numeroColonne;
        String numeroLigneString = "", lettreColonne = "";

        do{
            if (numeroLigneString.length() != 0)
                System.out.println("Erreur de saisie");
            System.out.print("Entrez le numéro de la ligne: ");
            numeroLigneString = scanner.nextLine();
        } while(!verfiSaisie(plateau, numeroLigneString, 1));
        numeroLigne = parseInt(numeroLigneString) - 1;
        System.out.println(numeroLigne);

        do{
            if (lettreColonne.length() != 0)
                System.out.println("Erreur de saisie");
            System.out.print("Entrez la lettre de la colonne: ");
            lettreColonne = scanner.nextLine();
        } while(!verfiSaisie(plateau, lettreColonne, 2));
        numeroColonne = lettreColonne.charAt(0) % 65;
        System.out.println(numeroColonne);

        modifierPlateau(plateau, numeroLigne, numeroColonne, joueur);
    }

    /**
     * Modifie le plateau en paramètres avec les valeus données en paramètre, si un pion est déjà placé là,
     * la méthode affiche un message d'erreur et renvoie à la méthode placerPion
     */
    public static void modifierPlateau(int[][] plateau , int ligne, int colonne, int joueur){

        if (plateau[ligne][colonne] == 0){
            for (int l = 0; l < plateau.length; l++){
                for (int c = 0; c < plateau[l].length; c++){
                    plateau[ligne][colonne] = joueur;
                }
            }
        }
        else {
            System.out.println("Oups ! Un pion est déjà placé à cet endroit");
            placerPion(plateau, joueur);
        }
    }

    /**
     * Demande à l'utilisateur la taille du tableau qu'il veut, appelle la fonction creerPlateau
     */
    public static int[][] choixTaillePlateau(){

        Scanner scanner = new Scanner(System.in);

        String choixTaille;
        String affichageTailles =
                        "(1) 9x9\n" +
                        "(2) 10x10\n" +
                        "(3) 11x11\n" +
                        "(4) 12x12\n" +
                        "(5) 13x13\n" +
                        "(6) 14x14\n";

        do{
            System.out.println(affichageTailles);
            System.out.print("Choisissez une taille de plateau: ");
            choixTaille = scanner.nextLine();

            switch (choixTaille){

                case "1":
                    return creerPlateau(9);

                case "2":
                    return creerPlateau(10);

                case "3":
                    return creerPlateau(11);

                case "4":
                    return creerPlateau(12);

                case "5":
                    return creerPlateau(13);

                case "6":
                    return creerPlateau(14);

                default:
                    System.out.println("Erreur de saisie");
                    break;
            }
        } while(!(choixTaille.equals("1") || choixTaille.equals("2") || choixTaille.equals("3") || choixTaille.equals("4") || choixTaille.equals("5") || choixTaille.equals("6")));
        return new int[0][0];
    }


    public static int[][] creerPlateau(int taille){

        int[][] plateau = new int[taille][taille];
        for(int l = 0; l < plateau.length; l++){
            for(int c = 0; c < plateau[l].length; c++){

                plateau[l][c] = 0;
            }
        }
        return plateau;
    }


    public static boolean verfiSaisie(int[][] plateau, String saisie, int choixMode){


        if (choixMode == 1){
            if (saisie.length() == 1  && saisie.charAt(0) - '0' < 10 || saisie.length() == 2 && saisie.charAt(0) - '0' < 10 && saisie.charAt(1) - '0' < 10){
                if (parseInt(saisie) <= plateau.length || parseInt(saisie) > 0){
                    return true;
                }
            }
        }

        else{
            if (saisie.length() == 1){
                if (saisie.charAt(0) % 65 <= 25){
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean testChemin(int[][] tab, int ligne , int colonne, int joueur) {

        boolean test = false;
        tab[ligne][colonne] = 0;

        if (ligne == tab.length-1 && joueur==1)
            return true;
        else if (colonne == tab.length-1 && joueur==2)
            return true;

        /*haut*/
        if (ligne - 1 > 0 && tab[ligne-1][colonne] == joueur)
            test = testChemin(tab, ligne-1,colonne, joueur);
        /*haut droite*/
        if (ligne - 1 > 0 && colonne + 1 < tab.length && tab[ligne-1][colonne+1] == joueur)
            test =
                    test || testChemin(tab, ligne-1, colonne+1, joueur);
        /*gauche*/
        if (colonne - 1 > 0 && tab[ligne-1][colonne] == joueur)
            test = test || testChemin(tab, ligne, colonne-1, joueur);
        /*droite*/
        if (colonne + 1 < tab.length && tab[ligne][colonne+1] == joueur)
            test = test || testChemin(tab, ligne, colonne+1, joueur);
        /*bas*/
        if (ligne + 1 < tab.length && tab[ligne+1][colonne] == joueur)
            test = test || testChemin(tab, ligne+1,colonne, joueur);
        /*bas gauche*/
        if (ligne + 1 < tab.length && colonne - 1 >= 0 && tab[ligne+1][colonne-1] == joueur)
            test = test || testChemin(tab, ligne+1, colonne-1, joueur);
        return test;
    }


    public static int[][] copieTableau(int[][] tab, int colonne){
        int[][] ntab = new int[tab.length][tab.length];
        for (int i = 0 ; i < tab.length ; i++){
            for (int j = 0 ; j < tab.length ; j++){
                ntab[i][j] = tab[i][j];
            }
        }
        return ntab;
    }


    public static boolean verifVictoire(int[][] tab, int joueur){
        for (int colonne = 0 ; colonne < tab.length ; colonne++){
            if (joueur == 1){
                if(tab[0][colonne] == 1){
                    if (testChemin(copieTableau(tab,colonne),0,colonne, joueur))
                        return true;
                }
            }
            else{
                if(tab[colonne][0] == 2){
                    if (testChemin(copieTableau(tab,colonne),0,colonne, joueur))
                        return true;
                }
            }
        }
        return false;
    }
}