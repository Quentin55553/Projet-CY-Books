package org.openjfx.cybooks.CommandLine;

import org.openjfx.cybooks.API.APIHandler;

import java.util.Scanner;

public class CommandLineHandler {
    public static void main(String[] args){

        // Beginning & Intro text
        for(int i=0;i<=50;i++){
            System.out.println();
        }
        Colors.printlnWhite("BIENVENUE SUR L'APPLICATION CY-BOOKS !");
        System.out.println();
        Colors.printlnWhite("CY-Books est une application programmée en Java permettant aux bibliothécaires de gérer leur bibliothèque.\n" +
                "\n" +
                "Pour cela, elle dispose de toutes les fonctionnalités les plus élémentaires telles que l'inscription des bibliothécaires et des usagers, la gestion des livres et du stock ainsi que celle des différents emprunts effectués par les usagers. \n" +
                "\n" +
                "Le bibliothécaire a par exemple également la possibilité de modifier les informations des usagers, d'effectuer des recherches multi-critères, d'afficher l'historique des emprunts ou la liste des retards de rendu d'un usager pour l'aider dans la gestion des livres et des emprunts.");

        // User login or signup

        // User actions

        try{
            int action=0;
            Scanner in = new Scanner(System.in);
            while(action!=8){
                Colors.printlnWhite("\nChoisissez une action :\n");
                Colors.printlnWhite("1 - Consulter les livres");
                Colors.printlnWhite("2 - Inscrire un nouveau membre");
                Colors.printlnWhite("3 - Rechercher un membre");
                Colors.printlnWhite("4 - Afficher la liste des membres");
                Colors.printlnWhite("5 - Créer un nouvel emprunt");
                Colors.printlnWhite("6 - Afficher la liste des emprunts");
                Colors.printlnWhite("7 - Afficher la liste des problèmes d'emprunts");
                Colors.printlnWhite("8 - Se déconnecter (fermera l'application)");
                action = in.nextInt();
                switch (action) {
                    case 1 -> consultBooks();
                    case 2 -> addNewMember();
                    case 3 -> searchForMember();
                    case 4 -> printMembers();
                    case 5 -> addNewLoan();
                    case 6 -> printLoans();
                    case 7 -> printLoanProblems();
                    case 8 -> disconnectUser();
                    default -> {
                        Colors.printlnRed("Valeur invalide saisie. Arrêt de l'application.");
                        System.exit(0);
                    }
                }
            }
        }
        catch(Exception e){
            Colors.printlnRed("Valeur invalide saisie. Arrêt de l'application.");
            System.exit(0);
        }


        // Ending
        Colors.printlnWhite("\nMerci d'avoir utilisé CY-BOOKS !");
        Colors.printlnWhite("Projet mené par : Théo BELLIERE, Quentin FILLION, Alexandre GRISEZ, Pauline MACEIRAS, Emilien MASSI");
        Colors.printlnWhite("ING1 CY Tech");
    }

    private static void consultBooks() {

    }
    private static void addNewMember() {

    }

    private static void printMembers() {

    }
    private static void searchForMember() {

    }
    private static void addNewLoan() {

    }
    private static void printLoans() {

    }
    private static void printLoanProblems() {

    }
    private static void disconnectUser() {

    }



}
