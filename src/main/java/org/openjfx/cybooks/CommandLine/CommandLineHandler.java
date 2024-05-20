package org.openjfx.cybooks.CommandLine;

import org.openjfx.cybooks.API.APIHandler;
import org.openjfx.cybooks.API.SearchResult;
import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Librarian;
import org.openjfx.cybooks.data.Loan;
import org.openjfx.cybooks.database.DBHandler;

import java.nio.file.Paths;
import java.util.*;

import static java.lang.Math.min;

public class CommandLineHandler {
    public static void main(String[] args){

        // Database creation
        DBHandler.executeSQLFile(Paths.get("").toAbsolutePath().toString() + "/src/main/resources/org/openjfx/cybooks/database/CY-Books.sql");

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
        Scanner in = new Scanner(System.in);

        Librarian user = null;
        String login;
        String password;
        Colors.printlnWhite("Veuillez entrer votre identifiant :");
        login=in.nextLine();
        Colors.printlnWhite("Veuillez entrer votre mot de passe :");
        password=in.nextLine();

        try {
            user = DBHandler.librarianAuthentication(login, password);
        }
        catch (Exception e){
            Colors.printlnRed("Identifiant ou mot de passe incorrect.");
        }

        if(user==null){
            Colors.printlnRed("Arrêt de l'application.");
            System.exit(0);
        }

        // User actions

        try{
            int action=0;
            while(action!=17){

                // Choices
                Colors.printlnWhite("\nChoisissez une action :\n");
                // Books
                Colors.printlnPurple("\nI. Livres :");
                Colors.printlnWhite("1 - Consulter les livres");
                Colors.printlnWhite("2 - Rechercher un livre");
                Colors.printlnWhite("3 - Voir les livres les plus populaires");
                // Customers
                Colors.printlnPurple("\nII. Membres :");
                Colors.printlnWhite("4 - Inscrire un nouveau membre");
                Colors.printlnWhite("5 - Mettre à jour le profil d'un membre");
                Colors.printlnWhite("6 - Rechercher un membre");
                Colors.printlnWhite("7 - Afficher la liste des membres");
                // Loans
                Colors.printlnPurple("\nIII. Emprunts :");
                Colors.printlnWhite("8 - Créer un nouvel emprunt");
                Colors.printlnWhite("9 - Mettre à jour un emprunt");
                Colors.printlnWhite("10 - Voir les emprunts d'un membre");
                Colors.printlnWhite("11 - Voir les emprunts d'un livre");
                Colors.printlnWhite("12 - Voir les emprunts en cours");
                Colors.printlnWhite("13 - Afficher la liste des emprunts");
                Colors.printlnWhite("14 - Afficher la liste des problèmes d'emprunts");
                // Miscellaneous
                Colors.printlnPurple("\nIV. Autres :");
                Colors.printlnWhite("15 - Créer un nouveau compte bibliothécaire");
                Colors.printlnWhite("16 - Consulter son compte");
                Colors.printlnWhite("17 - Se déconnecter (fermera l'application)");

                action = in.nextInt();
                switch (action) {
                    // Books
                    case 1 -> consultBooks();
                    case 2 -> searchBook();
                    case 3 -> printMostPopularBooks();
                    // Members
                    case 4 -> addNewMember();
                    case 5 -> updateMemberProfile();
                    case 6 -> searchForMember();
                    case 7 -> printMembers();
                    // Loans
                    case 8 -> addNewLoan();
                    case 9 -> updateOngoingLoan();
                    case 10-> printMemberLoans();
                    case 11-> printBookLoans();
                    case 12-> printOngoingLoans();
                    case 13-> printLoans();
                    case 14-> printLoanProblems();
                    // Miscellaneous
                    case 15-> createAccount();
                    case 16-> {
                        Colors.printlnCyan("Voici les informations de votre compte :");
                        Colors.printlnWhite(user.toString());
                    }
                    case 17 -> disconnectUser();
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


    // -------------------------------------
    // -------------BOOKS-------------------
    // -------------------------------------
    private static void consultBooks() {
        try {
            APIHandler API = new APIHandler();
            Random random = new Random();
            int randomDate;
            do {
                randomDate = random.nextInt(1000) + 1000;
                API.generateQueryStandard("", "", Integer.toString(randomDate), "", "", "", "");
                API.exec();
            } while (API.getNumberOfResults() == 0);
            Colors.printlnCyan("\nVoici quelques livres de l'année " + Integer.toString(randomDate)+" :");
            for (int i = 0; i < min(API.getNumberOfResults(), 3); i++) {
                Colors.printlnWhite(API.getResults().get(i).toString());
            }
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur de communication est survenue !");
        }
    }

    private static void searchBook(){
        try {
            APIHandler API = new APIHandler();
            String title;
            String author;
            String identifier;
            String date;
            String publisher;
            String subject;

            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("(Pour chaque champ ci-dessous, appuyez directement sur entrée si vous ne voulez pas l'utiliser.)");
            Colors.printlnWhite("Entrez un titre :");
            title = sc.nextLine();
            Colors.printlnWhite("Entrez un auteur :");
            author = sc.nextLine();
            Colors.printlnWhite("Entrez une année de publication (AAAA) :");
            date = sc.nextLine();
            Colors.printlnWhite("Entrez un identifiant :");
            identifier = sc.nextLine();
            Colors.printlnWhite("Entrez un éditeur :");
            publisher = sc.nextLine();
            Colors.printlnWhite("Entrez un sujet :");
            subject = sc.nextLine();

            API.generateQueryStandard(title,author,date,identifier,publisher,"",subject);
            API.exec();

            if(API.getNumberOfResults()==0){
                Colors.printlnCyan("Aucun résultat n'a été trouvé !");
            }
            else{
                Colors.printlnCyan("Voici quelques livres correspondant à la recherche : ");
                for (int i = 0; i < min(API.getNumberOfResults(), 3); i++) {
                    Colors.printlnWhite(API.getResults().get(i).toString());
                }
            }
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue !");
        }
    }

    private static void printMostPopularBooks(){
        try {
            List<Book> bookList;
            bookList = DBHandler.getMostPopularBooks();
            for (int i = 0; i < bookList.size(); i++) {
                Colors.printlnWhite(bookList.get(i).toString());
            }
        }
        catch(Exception e){
            Colors.printlnRed("Pas encore de livres populaires !");
        }
    }

    // -------------------------------------
    // -----------MEMBERS-------------------
    // -------------------------------------
    private static void addNewMember(){
        try {
            String firstname;
            String lastname;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez son prénom :");
            firstname = sc.nextLine();
            Colors.printlnWhite("Entrez son nom :");
            lastname = sc.nextLine();
            Colors.printlnCyan("Ajout du membre " + firstname + " " + lastname + " aux adhérents.");
            DBHandler.addCustomer(lastname, firstname);
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    private static void updateMemberProfile(){
        try {
            String firstname;
            String lastname;
            String member_id_string;
            int member_id;
            Scanner sc = new Scanner(System.in);

            Colors.printlnWhite("Entrez l'ID du membre à modifier :");
            member_id_string = sc.nextLine();
            member_id=Integer.parseInt(member_id_string);
            Colors.printlnWhite("Entrez son nouveau prénom :");
            firstname = sc.nextLine();
            Colors.printlnWhite("Entrez son nouveau nom :");
            lastname = sc.nextLine();
            Colors.printlnCyan("Mise à jour du profil du membre " + firstname + " " + lastname + ".");
            DBHandler.updateCustomer(member_id,lastname, firstname);
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    private static void printMembers() {
        try {
            List<Customer> customersList;
            customersList = DBHandler.getCustomers("");
            for (int i = 0; i < customersList.size(); i++) {
                Colors.printlnWhite(customersList.get(i).toString());
            }
        }
        catch(NoSuchElementException e){
            Colors.printlnRed("Aucun membre n'est encore inscrit !");
        }
    }
    private static void searchForMember() {
        try {
            String lastname;
            List<Customer> customersList;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez un nom de membre à rechercher :");
            lastname = sc.nextLine();
            customersList = DBHandler.getCustomers(lastname);
            Colors.printlnCyan("Voici la liste des membres trouvés : ");
            for (int i = 0; i < customersList.size(); i++) {
                Colors.printlnWhite(customersList.get(i).toString());
            }
        }
        catch(NoSuchElementException e){
            Colors.printlnRed("Aucun membre n'a été trouvé !");
        }
    }

    // -------------------------------------
    // -------------LOANS-------------------
    // -------------------------------------
    private static void addNewLoan() {
        try {
            String book_identifier;
            String member_id_string;
            int member_id;
            int year;
            int month;
            int day;
            Date expirationDate;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez l'ID du membre :");
            member_id_string = sc.nextLine();
            member_id=Integer.parseInt(member_id_string);
            Colors.printlnWhite("Entrez l'identifiant du livre :");
            book_identifier = sc.nextLine();
            Colors.printlnWhite("Entrez l'année d'expiration :");
            year = Integer.parseInt(sc.nextLine());
            Colors.printlnWhite("Entrez le mois d'expiration :");
            month = Integer.parseInt(sc.nextLine());
            Colors.printlnWhite("Entrez le jour d'expiration :");
            day = Integer.parseInt(sc.nextLine());
            expirationDate=new java.sql.Date(year,month+1,day);
            Colors.printlnCyan("Ajout de l'emprunt du livre " + book_identifier+" par le membre d'ID + " + member_id_string);
            DBHandler.addLoan(book_identifier,member_id,expirationDate);
        }
        catch(Exception e){
            Colors.printlnRed(e.getMessage());
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    private static void updateOngoingLoan(){
        try {
            int action = 0;
            Scanner in = new Scanner(System.in);
            // Choices
            Colors.printlnWhite("\nQue souhaitez-vous faire ? :\n");
            Colors.printlnWhite("1 - Indiquer comme terminé un emprunt");
            Colors.printlnWhite("2 - Changer la date d'expiration d'un emprunt");
            switch (action) {
                case 1 -> {
                    String loan_id_string;
                    int loan_id;
                    Colors.printlnWhite("Entrez l'ID de l'emprunt terminé :");
                    loan_id_string = in.nextLine();
                    loan_id = Integer.parseInt(loan_id_string);
                    DBHandler.updateLoan(loan_id, true);
                    Colors.printlnCyan("L'emprunt a bien été terminé.");
                }
                case 2 -> {
                    String loan_id_string;
                    int loan_id;
                    int year;
                    int month;
                    int day;
                    Colors.printlnWhite("Entrez l'ID de l'emprunt à modifier :");
                    loan_id_string = in.nextLine();
                    loan_id = Integer.parseInt(loan_id_string);
                    Colors.printlnWhite("Entrez la nouvelle année :");
                    year = Integer.parseInt(in.nextLine());
                    Colors.printlnWhite("Entrez le nouveau mois :");
                    month = Integer.parseInt(in.nextLine());
                    Colors.printlnWhite("Entrez le nouveau jour :");
                    day = Integer.parseInt(in.nextLine());
                    java.sql.Date newDate=new java.sql.Date(year,month+1,day);
                    DBHandler.updateExpirationDate(loan_id_string,newDate);
                }
                default -> {
                    Colors.printlnRed("Mauvaise valeur. Sélectionnez 1 ou 2.");
                }
            }
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    private static void printLoans() {
        try {
            List<Loan> loanList;
            loanList = DBHandler.getLoans();
            for (int i = 0; i < loanList.size(); i++) {
                Colors.printlnWhite(loanList.get(i).toString());
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnRed("\nAucun emprunt n'a encore été déclaré !");
        }
    }

    private static void printMemberLoans(){
        try {
            String string_id;
            int id;
            List<Loan> loanList;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez son ID :");
            string_id = sc.nextLine();
            id=Integer.parseInt(string_id);
            Colors.printlnCyan("Recherche des emprunts liés au membre d'ID "+string_id+".");
            loanList = DBHandler.getLoansByCustomer(id);
            for (int i = 0; i < loanList.size(); i++) {
                Colors.printlnWhite(loanList.get(i).toString());
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnRed("Aucun emprunt ne concerne ce membre pour le moment.");
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    private static void printBookLoans(){
        try {
            String identifier;
            List<Loan> loanList;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez son identifiant :");
            identifier = sc.nextLine();
            Colors.printlnCyan("Recherche des emprunts liés au livre d'identifiant "+identifier+".");
            loanList = DBHandler.getLoansByBookId(identifier);
            for (int i = 0; i < loanList.size(); i++) {
                Colors.printlnWhite(loanList.get(i).toString());
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnRed("Aucun emprunt ne concerne ce livre pour le moment.");
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    private static void printOngoingLoans() {
        try {
            List<Loan> ongoingLoansList;
            ongoingLoansList = DBHandler.getOngoingLoans();
            for (int i = 0; i < ongoingLoansList.size(); i++) {
                Colors.printlnWhite(ongoingLoansList.get(i).toString());
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnRed("\nAucun emprunt n'est en cours !");
        }
    }

    private static void printLoanProblems() {
        try {
            List<Loan> problematicLoansList;
            problematicLoansList = DBHandler.getExpiredLoans();
            for (int i = 0; i < problematicLoansList.size(); i++) {
                Colors.printlnWhite(problematicLoansList.get(i).toString());
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnGreen("\nPas de problème d'emprunt pour le moment !");
        }
    }

    // -------------------------------------
    // ------------MISCELLANEOUS------------
    // -------------------------------------

    private static void createAccount(){
        try {
            String login;
            String password;
            String firstname;
            String lastname;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez son prénom :");
            firstname = sc.nextLine();
            Colors.printlnWhite("Entrez son nom :");
            lastname = sc.nextLine();
            Colors.printlnWhite("Entrez son login :");
            login = sc.nextLine();
            Colors.printlnWhite("Entrez son mot de passe :");
            password = sc.nextLine();
            Colors.printlnCyan("Ajout du biblothécaire " + firstname + " " + lastname + ".");
            DBHandler.addLibrarian(login,lastname, firstname,password);
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }
    }
    // Nothing needed in this method
    private static void disconnectUser() {

    }



}
