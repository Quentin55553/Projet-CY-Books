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

/**
 * Class used to handle the command line version of the app.
 * The main function works much like the JavaFx app, asking the user to sign in and then giving access to all the features
 * It often asks input in the command line, and the output is command line also.
 */
public class CommandLineHandler {

    /**
     * The main function of the command line app.
     * Will briefly present the app and creators and give the user access to the app after signing in.
     * @param args Not used
     */
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
            while(action!=18){

                // Choices
                Colors.printlnWhite("\nChoisissez une action :\n");
                // Books
                Colors.printlnPurple("\nI. Livres :");
                Colors.printlnWhite("1 - Consulter les livres");
                Colors.printlnWhite("2 - Rechercher un livre");
                Colors.printlnWhite("3 - Ajouter un livre");
                Colors.printlnWhite("4 - Voir les livres les plus populaires");
                // Customers
                Colors.printlnPurple("\nII. Membres :");
                Colors.printlnWhite("5 - Inscrire un nouveau membre");
                Colors.printlnWhite("6 - Mettre à jour le profil d'un membre");
                Colors.printlnWhite("7 - Rechercher un membre");
                Colors.printlnWhite("8 - Afficher la liste des membres");
                // Loans
                Colors.printlnPurple("\nIII. Emprunts :");
                Colors.printlnWhite("9 - Créer un nouvel emprunt");
                Colors.printlnWhite("10 - Mettre à jour un emprunt");
                Colors.printlnWhite("11 - Voir les emprunts d'un membre");
                Colors.printlnWhite("12 - Voir les emprunts d'un livre");
                Colors.printlnWhite("13 - Voir les emprunts en cours");
                Colors.printlnWhite("14 - Afficher la liste des emprunts");
                Colors.printlnWhite("15 - Afficher la liste des problèmes d'emprunts");
                // Miscellaneous
                Colors.printlnPurple("\nIV. Autres :");
                Colors.printlnWhite("16 - Créer un nouveau compte bibliothécaire");
                Colors.printlnWhite("17 - Consulter son compte");
                Colors.printlnWhite("18 - Se déconnecter (fermera l'application)");

                action = in.nextInt();
                switch (action) {
                    // Books
                    case 1 -> consultBooks();
                    case 2 -> searchBook();
                    case 3 -> addNewBook();
                    case 4 -> printMostPopularBooks();
                    // Members
                    case 5 -> addNewMember();
                    case 6 -> updateMemberProfile();
                    case 7 -> searchForMember();
                    case 8 -> printMembers();
                    // Loans
                    case 9 -> addNewLoan();
                    case 10 -> updateOngoingLoan();
                    case 11-> printMemberLoans();
                    case 12-> printBookLoans();
                    case 13-> printOngoingLoans();
                    case 14-> printLoans();
                    case 15-> printLoanProblems();
                    // Miscellaneous
                    case 16-> createAccount();
                    case 17-> {
                        Colors.printlnCyan("Voici les informations de votre compte :\n");
                        Colors.printColorfulLibrarian(user,login);
                    }
                    case 18 -> disconnectUser();
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

    /**
     * This command line method is used to print random books on the terminal.
     * The random factor is the release date, which year is selected at random at each call of the method
     */
    private static void consultBooks() {
        try {
            APIHandler API = new APIHandler();
            Random random = new Random();
            int randomfieldchoice;
            randomfieldchoice=random.nextInt(5);
            switch(randomfieldchoice){
                // Random title
                case 0 -> {
                    int randomtitlechoice;
                    String randomTitle;
                    do {
                        randomtitlechoice=random.nextInt(10);
                        randomTitle=titleExamples(randomtitlechoice);
                        API.generateQueryStandard(randomTitle, "", "", "", "", "", "");
                        API.exec();
                    }while(API.getNumberOfResults()==0);
                    Colors.printlnCyan("Voici quelques livres dont le titre contient '"+randomTitle+"' :\n");
                }
                // Random author
                case 1 -> {
                    int randomauthorchoice;
                    String randomAuthor;
                    do {
                        randomauthorchoice=random.nextInt(10);
                        randomAuthor=authorExamples(randomauthorchoice);
                        API.generateQueryStandard("", randomAuthor, "", "", "", "", "");
                        API.exec();
                    }while(API.getNumberOfResults()==0);
                    Colors.printlnCyan("Voici quelques livres écrits par "+randomAuthor+" :\n");
                }
                // Random subject
                case 2 -> {
                    int randomsubjectchoice;
                    String randomSubject;
                    do {
                        randomsubjectchoice=random.nextInt(20);
                        randomSubject=subjectExamples(randomsubjectchoice);
                        API.generateQueryStandard("", "", "", "", "", "", randomSubject);
                        API.exec();
                    }while(API.getNumberOfResults()==0);
                    Colors.printlnCyan("Voici quelques livres qui parlent de "+randomSubject+" :\n");
                }
                // Random release date
                case 3 -> {
                    int randomDate;
                    do {
                        randomDate = random.nextInt(1000) + 1000;
                        API.generateQueryStandard("", "", Integer.toString(randomDate), "", "", "", "");
                        API.exec();
                    } while (API.getNumberOfResults() == 0);
                    Colors.printlnCyan("\nVoici quelques livres de l'année " + Integer.toString(randomDate)+" :\n");
                }
                // Random publisher
                case 4 -> {
                    int randompublisherchoice;
                    String randomPublisher;
                    do {
                        randompublisherchoice=random.nextInt(5);
                        randomPublisher=publisherExamples(randompublisherchoice);
                        API.generateQueryStandard("", "", "", "", randomPublisher, "", "");
                        API.exec();
                    }while(API.getNumberOfResults()==0);
                    Colors.printlnCyan("Voici quelques livres publiés par "+randomPublisher+" :\n");
                }
                default -> {
                    Colors.printlnRed("Une erreur est survenue.");
                }
            }
            for (int i = 0; i < min(API.getNumberOfResults(), 3); i++) {
                Colors.printColorfulSearchResult(API.getResults().get(i));
                Colors.printlnWhite("");
                //addNewBookAuto(API.getResults().get(i));
            }
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur de communication est survenue !");
        }
    }

    /**
     * Method used to get a subject example to use in an API search
     * @param number Integer between 0 and 19
     * @return A subject (String)
     */
    public static String subjectExamples(int number){
        switch (number){
            case 0 -> {return "informatique";}
            case 1 -> {return "ornithologie";}
            case 2 -> {return "histoire";}
            case 3 -> {return "cuisine";}
            case 4 -> {return "philosophie";}
            case 5 -> {return "mathématiques";}
            case 6 -> {return "science";}
            case 7 -> {return "astronomie";}
            case 8 -> {return "musique";}
            case 9 -> {return "physique";}
            case 10 -> {return "archéologie";}
            case 11 -> {return "médecine";}
            case 12 -> {return "peinture";}
            case 13 -> {return "architecture";}
            case 14 -> {return "zoologie";}
            case 15 -> {return "économie";}
            case 16 -> {return "droit";}
            case 17 -> {return "sport";}
            case 18 -> {return "programmation";}
            case 19 -> {return "danse";}
            default -> {return "";}
        }
    }

    /**
     * Method used to get an author example to use in an API search
     * @param number Integer between 0 and 9
     * @return An author (String)
     */
    public static String authorExamples(int number){
        switch (number){
            case 0 -> {return "Stendhal";}
            case 1 -> {return "Victor Hugo";}
            case 2 -> {return "Emile Zola";}
            case 3 -> {return "Guy de Maupassant";}
            case 4 -> {return "François Rabelais";}
            case 5 -> {return "Hérodote";}
            case 6 -> {return "Leonhard Euler";}
            case 7 -> {return "Jean de La Fontaine";}
            case 8 -> {return "Aristote";}
            case 9 -> {return "Dante Alighieri";}
            default -> {return "";}
        }
    }

    /**
     * Method used to get a title word example to use in an API search
     * @param number Integer between 0 and 9
     * @return A word (String)
     */
    public static String titleExamples(int number){
        switch (number){
            case 0 -> {return "oiseaux";}
            case 1 -> {return "histoire";}
            case 2 -> {return "plaidoirie";}
            case 3 -> {return "mathématiques";}
            case 4 -> {return "science";}
            case 5 -> {return "informatique";}
            case 6 -> {return "animaux";}
            case 7 -> {return "joie";}
            case 8 -> {return "java";}
            case 9 -> {return "projet";}
            default -> {return "";}
        }
    }

    /**
     * Method used to get a publisher example to use in an API search
     * @param number Integer between 0 and 4
     * @return A publisher (String)
     */
    public static String publisherExamples(int number){
        switch (number){
            case 0 -> {return "Editis";}
            case 1 -> {return "Flammarion";}
            case 2 -> {return "Gallimard";}
            case 3 -> {return "Hachette";}
            case 4 -> {return "Pathé";}
            default -> {return "";}
        }
    }



    /**
     * This command line method is used to search for a book using different fields. Can be used :
     * - Title
     * - Author
     * - Release Year
     * - Subject
     * - Publisher
     * - Identifier
     * Only pressing enter when asked for input makes the field not used in the search
     */
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
                Colors.printlnCyan("Voici quelques livres correspondant à la recherche : \n");
                for (int i = 0; i < min(API.getNumberOfResults(), 3); i++) {
                    Colors.printColorfulSearchResult(API.getResults().get(i));
                    Colors.printlnWhite("");
                    //addNewBookAuto(API.getResults().get(i));
                }
            }
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue !");
        }
    }

    /**
     * This command line method is used to add an unkown book to the databse using the terminal
     */
    private static void addNewBook(){
        try {
            String identifier;
            int stock;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez l'ID du livre à ajouter :");
            identifier = sc.nextLine();
            Colors.printlnWhite("Entrez le stock actuellement disponible :");
            stock = Integer.parseInt(sc.nextLine());
            DBHandler.addBook(identifier,stock,0);
            Colors.printlnCyan("Le livre a bien été ajouté.");
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    public static void addNewBookAuto(SearchResult sr){
        String identifier=sr.getIdentifier();
        try{
            DBHandler.getBook(identifier);
        }
        catch(NoSuchElementException e){
            DBHandler.addBook(identifier,1,0);
        }
        catch(Exception e){
            Colors.printRed("Attention : Le livre ");
            Colors.printCyan(sr.getTitle());
            Colors.printlnRed(" n'est pas empruntable.");
        }
    }

    /**
     * This command line method is used to print the most borrowed books of all time on the terminal
     */
    private static void printMostPopularBooks(){
        try {
            List<Book> bookList;
            bookList = DBHandler.getMostPopularBooks();
            for (int i = 0; i < bookList.size(); i++) {
                Colors.printlnWhite(bookList.get(i).toString());
                Colors.printlnWhite("");
            }
        }
        catch(Exception e){
            Colors.printlnRed("Pas encore de livres populaires !");
        }
    }

    // -------------------------------------
    // -----------MEMBERS-------------------
    // -------------------------------------

    /**
     * This command line method is used to create a new customer in the database. Their personal information will be asked
     */
    private static void addNewMember(){
        try {
            String firstname;
            String lastname;
            String phone;
            String email;
            String adress;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez son prénom :");
            firstname = sc.nextLine();
            Colors.printlnWhite("Entrez son nom :");
            lastname = sc.nextLine();
            Colors.printlnWhite("Entrez son numéro de téléphone :");
            phone = sc.nextLine();
            Colors.printlnWhite("Entrez son email :");
            email = sc.nextLine();
            Colors.printlnWhite("Entrez son adresse :");
            adress = sc.nextLine();
            Colors.printlnCyan("Ajout du membre " + firstname + " " + lastname + " aux adhérents.");
            DBHandler.addCustomer(firstname,lastname,phone,email,adress);
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    /**
     * This command line method is used to update the information of a customer (identified by their ID)
     */
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

    /**
     * This command line method is used to print the information of all the customers of the app.
     */
    private static void printMembers() {
        try {
            List<Customer> customersList;
            customersList = DBHandler.getCustomers("");
            Colors.printlnCyan("Voici la liste des membres inscrits :\n");
            for (int i = 0; i < customersList.size(); i++) {
                Colors.printColorfulCustomer(customersList.get(i));
                Colors.printlnWhite("");
            }
        }
        catch(NoSuchElementException e){
            Colors.printlnRed("Aucun membre n'est encore inscrit !");
        }
    }

    /**
     * This command line method is used to print all the possible customers related to a given lastname.
     */
    private static void searchForMember() {
        try {
            String lastname;
            List<Customer> customersList;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez un nom de membre à rechercher :");
            lastname = sc.nextLine();
            customersList = DBHandler.getCustomers(lastname);
            Colors.printlnCyan("Voici la liste des membres trouvés : \n");
            for (int i = 0; i < customersList.size(); i++) {
                Colors.printColorfulCustomer(customersList.get(i));
                Colors.printlnWhite("");
            }
        }
        catch(NoSuchElementException e){
            Colors.printlnRed("Aucun membre n'a été trouvé !");
        }
    }

    // -------------------------------------
    // -------------LOANS-------------------
    // -------------------------------------

    /**
     * This command line method is used to create a new loan in the database.
     * Customer ID and Book ID will be asked.
     */
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
            Colors.printlnCyan("Ajout de l'emprunt du livre " + book_identifier+" par le membre d'ID " + member_id_string);
            DBHandler.addLoan(book_identifier,member_id,expirationDate.toString());
        }
        catch(Exception e){
            Colors.printlnRed(e.getMessage());
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    /**
     * This command line method is used to update an existing loan.
     * Two ways of updating are possible :
     * - Marking the loan as ended / completed when the customer returned the book.
     * - Modifying the expiration date of an ongoing loan
     * The loan is identified by its ID
     */
    private static void updateOngoingLoan(){
        try {
            int action = 0;
            Scanner in = new Scanner(System.in);
            // Choices
            Colors.printlnWhite("\nQue souhaitez-vous faire ? :\n");
            Colors.printlnWhite("1 - Indiquer comme terminé un emprunt");
            Colors.printlnWhite("2 - Changer la date d'expiration d'un emprunt");
            action=Integer.parseInt(in.nextLine());
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
                    DBHandler.updateExpirationDate(loan_id_string,newDate.toString());
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

    /**
     * This command line method is used to print all the existing loans on the terminal
     */
    private static void printLoans() {
        try {
            List<Loan> loanList;
            loanList = DBHandler.getLoans();
            Colors.printlnCyan("Voici la liste de tous les emprunts :\n");
            for (int i = 0; i < loanList.size(); i++) {
                Colors.printColorfulLoan(loanList.get(i));
                Colors.printlnWhite("");
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnRed("\nAucun emprunt n'a encore été déclaré !");
        }
    }

    /**
     * This command line method is used to print all the loans related to a specific customer (identified by its ID) on the terminal
     */
    private static void printMemberLoans(){
        try {
            String string_id;
            int id;
            List<Loan> loanList;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez son ID :");
            string_id = sc.nextLine();
            id=Integer.parseInt(string_id);
            loanList = DBHandler.getLoansByCustomer(id);
            Colors.printlnCyan("Voici les  emprunts liés au membre d'ID "+string_id+" :\n");
            for (int i = 0; i < loanList.size(); i++) {
                Colors.printColorfulLoan(loanList.get(i));
                Colors.printlnWhite("");
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnRed("Aucun emprunt ne concerne ce membre pour le moment.");
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    /**
     * This command line method is used to print all the loans related to a specific book (identified by its ID) on the terminal
     */
    private static void printBookLoans(){
        try {
            String identifier;
            List<Loan> loanList;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez son identifiant :");
            identifier = sc.nextLine();
            loanList = DBHandler.getLoansByBookId(identifier);
            Colors.printlnCyan("Voici les emprunts liés au livre d'identifiant "+identifier+" :\n");
            for (int i = 0; i < loanList.size(); i++) {
                Colors.printColorfulLoan(loanList.get(i));
                Colors.printlnWhite("");
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnRed("Aucun emprunt ne concerne ce livre pour le moment.");
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    /**
     * This command line method is used to print all the current ongoing loans on the terminal.
     */
    private static void printOngoingLoans() {
        try {
            List<Loan> ongoingLoansList;
            ongoingLoansList = DBHandler.getOngoingLoans();
            Colors.printlnCyan("Voici la liste des emprunts en cours :\n");
            for (int i = 0; i < ongoingLoansList.size(); i++) {
                Colors.printColorfulLoan(ongoingLoansList.get(i));
                Colors.printlnWhite("");
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnRed("\nAucun emprunt n'est en cours !");
        }
    }

    /**
     * This command line method is used to print all the loans that are past their expiration date on the terminal.
     */
    private static void printLoanProblems() {
        try {
            List<Loan> problematicLoansList;
            problematicLoansList = DBHandler.getExpiredLoans();
            Colors.printlnCyan("Voici la liste des emprunts expirés :\n");
            for (int i = 0; i < problematicLoansList.size(); i++) {
                Colors.printColorfulLoan(problematicLoansList.get(i));
                Colors.printlnWhite("");
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnGreen("\nPas de problème d'emprunt pour le moment !");
        }
    }

    // -------------------------------------
    // ------------MISCELLANEOUS------------
    // -------------------------------------

    /**
     * This command line method is used to make the user create a new librarian account on the system. It asks them to input the needed information.
     */
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

    /**
     * This command line method is used to end the interaction between user and program.
     */
    private static void disconnectUser() {
        // Nothing needed in this method
    }



}
