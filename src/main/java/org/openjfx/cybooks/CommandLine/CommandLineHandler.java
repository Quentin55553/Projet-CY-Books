package org.openjfx.cybooks.CommandLine;

import org.openjfx.cybooks.API.APIErrorException;
import org.openjfx.cybooks.API.APIHandler;
import org.openjfx.cybooks.API.QueryParameterException;
import org.openjfx.cybooks.API.SearchResult;
import org.openjfx.cybooks.UserInput.FieldChecks;
import org.openjfx.cybooks.data.*;
import org.openjfx.cybooks.database.DBHandler;
import org.openjfx.cybooks.UserInput.IncorrectFieldException;

import java.io.Console;
import java.io.Console.*;

import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.ConsoleHandler;

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

        do {
            Colors.printlnWhite("Veuillez entrer votre identifiant :");
            login = in.nextLine();
            Colors.printlnWhite("Veuillez entrer votre mot de passe :");
            password = in.nextLine();

            try {
                if (FieldChecks.isValidLogin(login) && FieldChecks.isValidLogin(password)) {
                    user = DBHandler.librarianAuthentication(login, password);
                }
            }
            catch(IncorrectFieldException e){
                Colors.printlnRed(e.getMessage());
            }
            catch (Exception e) {
                Colors.printlnRed("Identifiant ou mot de passe incorrect.");
            }
        }while(user==null);

        Colors.printGreen("\nBonjour " );
        Colors.printCyan(user.getFirstName());
        Colors.printGreen(" !\n");

        // User actions

        int action=0;

        do {
            try {
                    // Choices
                    Colors.printlnWhite("\nChoisissez une action :\n");
                    // Books
                    Colors.printlnPurple("\nI. Livres :");
                    Colors.printlnWhite("1 - Consulter les livres");
                    Colors.printlnWhite("2 - Rechercher des livres");
                    Colors.printlnWhite("3 - Ajouter un livre");
                    Colors.printlnWhite("4 - Voir le stock d'un livre");
                    Colors.printlnWhite("5 - Mettre à jour le stock d'un livre");
                    Colors.printlnWhite("6 - Voir les livres de la bibliothèque");
                    Colors.printlnWhite("7 - Voir les livres les plus populaires");
                    // Customers
                    Colors.printlnPurple("\nII. Membres :");
                    Colors.printlnWhite("8 - Inscrire un nouveau membre");
                    Colors.printlnWhite("9 - Mettre à jour le profil d'un membre");
                    Colors.printlnWhite("10 - Rechercher un membre");
                    Colors.printlnWhite("11 - Afficher la liste des membres");
                    // Loans
                    Colors.printlnPurple("\nIII. Emprunts :");
                    Colors.printlnWhite("12 - Créer un nouvel emprunt");
                    Colors.printlnWhite("13 - Mettre à jour un emprunt");
                    Colors.printlnWhite("14 - Voir les emprunts d'un membre");
                    Colors.printlnWhite("15 - Voir les emprunts d'un livre");
                    Colors.printlnWhite("16 - Voir les emprunts en cours");
                    Colors.printlnWhite("17 - Afficher la liste des emprunts");
                    Colors.printlnWhite("18 - Afficher la liste des problèmes d'emprunts");
                    // Miscellaneous
                    Colors.printlnPurple("\nIV. Autres :");
                    Colors.printlnWhite("19 - Créer un nouveau compte bibliothécaire");
                    Colors.printlnWhite("20 - Consulter son compte");
                    Colors.printlnWhite("21 - Se déconnecter (fermera l'application)");

                    action = Integer.parseInt(in.nextLine());
                    switch (action) {
                        // Books
                        case 1 -> consultBooks();
                        case 2 -> searchBook();
                        case 3 -> addNewBook();
                        case 4 -> seeBookStock();
                        case 5 -> changeBookStock();
                        case 6 -> printBooks();
                        case 7 -> printMostPopularBooks();
                        // Members
                        case 8 -> addNewMember();
                        case 9 -> updateMemberProfile();
                        case 10 -> searchForMember();
                        case 11 -> printMembers();
                        // Loans
                        case 12 -> addNewLoan();
                        case 13 -> updateOngoingLoan();
                        case 14 -> printMemberLoans();
                        case 15 -> printBookLoans();
                        case 16 -> printOngoingLoans();
                        case 17 -> printLoans();
                        case 18 -> printLoanProblems();
                        // Miscellaneous
                        case 19 -> createAccount();
                        case 20 -> {
                            Colors.printlnCyan("Voici les informations de votre compte :\n");
                            Colors.printColorfulLibrarian(user, login);
                        }
                        case 21 -> disconnectUser();
                        default -> {
                            Colors.printlnRed("Valeur invalide.");
                        }
                    }
            } catch (Exception e) {
                Colors.printlnRed("Valeur invalide.");
            }
        }while(action!=21);


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
     * The random factor for the search is chosen randomly between date, title, subject, author, publisher.
     * It between 1 and 3 books to not overload the terminal view.
     */
    private static void consultBooks() {
        try {
            Colors.printlnCyan("\nConsultation des livres :\n");
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
                addNewBookAuto(API.getResults().get(i));
            }
        }
        catch(APIErrorException e){
            Colors.printlnRed(e.getMessage());
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
            Colors.printlnCyan("\nRecherche de livres :\n");
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

            if(title.equals("") && author.equals("") && date.equals("") && identifier.equals("") && publisher.equals("") && subject.equals("")){
                Colors.printlnRed("Vous devez au moins compléter un champ.");
                return;
            }

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
                    addNewBookAuto(API.getResults().get(i));
                }
            }
        }
        catch(QueryParameterException | APIErrorException e){
            Colors.printlnRed(e.getMessage());
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue !");
        }
    }

    /**
     * This command line method is used to add an unkown book to the databse using the terminal
     */
    private static void addNewBook() {
        Colors.printlnCyan("\nAjout d'un nouveau livre à la bibliothèque :\n");
        String identifier = null;
        int stock = 0;
        try {
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez l'ID du livre à ajouter (format : [Chiffres]/[Chiffres ou lettres]:");
            identifier = sc.nextLine();
            Colors.printlnWhite("Entrez le stock actuellement disponible :");
            stock = Integer.parseInt(sc.nextLine());
            if (FieldChecks.isValidBookIdentifier(identifier) && stock >= 0) {
                DBHandler.getBook(identifier);
                Colors.printlnRed("Le livre existe déjà dans la bibliothèque !");
            }
            else if(stock<0){
                Colors.printlnRed("Un stock négatif n'est pas autorisé.");
            }
        } catch (NoSuchElementException e) {
            DBHandler.addBook(identifier, stock, stock);
            Colors.printlnCyan("Le livre a bien été ajouté.");
        } catch (NumberFormatException e) {
            Colors.printlnRed("Veuillez entrer une valeur numérique.");
        } catch (IncorrectFieldException e) {
            Colors.printlnRed(e.getMessage());
        } catch (Exception e) {
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    /**
     * This method is used to automatically add a book found by the API to our database.
     * @param sr The SearchResult object found brought by an APIHandler object.
    */
    public static void addNewBookAuto(SearchResult sr){
        String identifier=sr.getIdentifier();

        try{
            identifier = identifier.replace("https://gallica.bnf.fr/ark:/", "");
            identifier = identifier.replace("/date", "");
            DBHandler.getBook(identifier);
        }
        catch(NoSuchElementException e){
            DBHandler.addBook(identifier, 1, 1);
            Colors.printlnGreen("Vous avez trouvé le livre :");
            Colors.printlnGreen(sr.getTitle());
            Colors.printlnGreen("Les membres peuvent maintenant l'emprunter !\n");
        }
        catch(Exception e){
            Colors.printRed("Attention : Le livre ");
            Colors.printYellow(sr.getTitle());
            Colors.printlnRed(" n'est pas empruntable.\n");
        }
    }

    /**
     * This command line method allows the user to see the stock of a book.
     * It will ask for the identifier. If the book is not in the library, it tells the user.
     */
    private static void seeBookStock(){
        try {
            Colors.printlnCyan("\nConsultation du stock d'un livre :\n");
            String identifier;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez l'ID du livre (format : [Chiffres]/[Chiffres ou lettres]):");
            identifier = sc.nextLine();
            if(FieldChecks.isValidBookIdentifier(identifier)){
                Book b = DBHandler.getBook(identifier);
                Colors.printlnCyan("\nVoici le stock du livre d'identifiant " + identifier +" : "+b.getTitle());
                if(b.getStock()<0){
                    Colors.printlnCyan("Stock : 0");
                }
                else{
                    Colors.printlnCyan("Stock : "+b.getStock());
                }
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnRed("Ce livre n'est pas dans la biliothèque !");
        }
        catch(NumberFormatException e){
            Colors.printlnRed("Veuillez entrer une valeur numérique.");
        }
        catch(IncorrectFieldException e){
            Colors.printlnRed(e.getMessage());
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }

    }

    /**
     * This command line method allows the user to change the stock of a book.
     * It will ask for the identifier and the new stock.
     */
    private static void changeBookStock(){
        try {
            Colors.printlnCyan("\nMise à jour du stock d'un livre :\n");
            String identifier;
            int stock;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez l'ID du livre (format : [Chiffres]/[Chiffres ou lettres]):");
            identifier = sc.nextLine();
            Colors.printlnWhite("Entrez le stock actuellement disponible :");
            stock = Integer.parseInt(sc.nextLine());
            if(FieldChecks.isValidBookIdentifier(identifier) && stock>=0){
                DBHandler.getBook(identifier);
                DBHandler.updateBookStock(identifier,stock);
                Colors.printlnCyan("Le stock a bien été mis à jour.");
            }
            else{
                Colors.printlnRed("Veuillez entrer une valeur numérique positive.");
            }
        }
        catch (NoSuchElementException e){
            Colors.printlnRed("Ce livre n'est pas dans la biliothèque !");
        }
        catch(NumberFormatException e){
            Colors.printlnRed("Veuillez entrer une valeur numérique.");
        }
        catch(IncorrectFieldException e){
            Colors.printlnRed(e.getMessage());
        }
        catch(Exception e){
            Colors.printlnRed("Une erreur est survenue.");
        }
    }

    /**
     * This command line method is used to print all the books present in the library on the terminal
     */
    private static void printBooks(){
        try {
            Colors.printlnCyan("\n :\n");
            APIHandler API=new APIHandler();
            List<Book> bookList;
            String identifier;
            bookList = DBHandler.getAllBooks();
            Colors.printlnCyan("\nVoici les livres de la bibliothèque :\n");
            for (int i = 0; i < min(5,bookList.size()); i++) {
                identifier=bookList.get(i).getId();
                API.generateQueryStandard("","","",identifier,"","","");
                API.exec();
                if(API.getNumberOfResults()>0){
                    Colors.printColorfulSearchResult(API.getResults().get(0));
                }
                Colors.printlnWhite("");
            }
            Colors.printlnCyan("\nEt bien d'autres...!");
        }
        catch(APIErrorException e){
            Colors.printlnRed(e.getMessage());
        }
        catch(Exception e){
            Colors.printlnRed("Pas de livres dans la bibliothèque !");
        }
    }

    /**
     * This command line method is used to print the most borrowed books of all time on the terminal
     */
    private static void printMostPopularBooks(){
        try {
            APIHandler API=new APIHandler();
            Colors.printlnCyan("\nVoici les livres les plus populaires :\n");
            Colors.printlnYellow("Attention : cette fonctionnalité peut ne pas être fonctionelle.\n");
            List<Book> bookList;
            bookList = DBHandler.getMostPopularBooks();

            for (int i = 0; i < bookList.size(); i++) {
                API.generateQueryStandard("","","",bookList.get(i).getId(),"","","");
                API.exec();
                if(API.getNumberOfResults()>0){
                    Colors.printColorfulSearchResult(API.getResults().get(0));
                    Colors.printlnWhite("");
                }
            }
        }
        catch(APIErrorException e){
            Colors.printlnRed("Un problème de communication est survenu.");
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
            Colors.printlnCyan("\nAjout d'un nouveau membre :\n");
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
            if(FieldChecks.isValidCustomer(firstname,lastname,phone,email,adress)){
                Colors.printlnCyan("Ajout du membre " + firstname + " " + lastname + " aux adhérents.");
                DBHandler.addCustomer(firstname,lastname,phone,email,adress);
            }
        }
        catch (IncorrectFieldException  | SQLException e){
            Colors.printlnRed(e.getMessage());
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
            Colors.printlnCyan("\nMise à jour du profil d'un membre :\n");
            String firstname;
            String lastname;
            String member_id_string;
            int member_id;
            String phone;
            String email;
            String adress;
            Scanner sc = new Scanner(System.in);

            Colors.printlnWhite("Entrez l'ID du membre à modifier :");
            member_id_string = sc.nextLine();
            member_id=Integer.parseInt(member_id_string);
            Colors.printlnWhite("Entrez son nouveau prénom :");
            firstname = sc.nextLine();
            Colors.printlnWhite("Entrez son nouveau nom :");
            lastname = sc.nextLine();
            Colors.printlnWhite("Entrez son nouveau numéro de téléphone :");
            phone = sc.nextLine();
            Colors.printlnWhite("Entrez son nouvel email :");
            email = sc.nextLine();
            Colors.printlnWhite("Entrez sa nouvelle adresse :");
            adress = sc.nextLine();
            if(FieldChecks.isValidCustomer(firstname,lastname,phone,email,adress)){
                DBHandler.updateEntireCustomer(member_id,lastname,firstname,phone,email,adress);
                Colors.printlnCyan("Mise à jour du profil du membre " + firstname + " " + lastname + ".");
            }
        }
        catch(NumberFormatException e){
            Colors.printlnRed("Veuillez entrer une valeur numérique valide.");
        }
        catch(IncorrectFieldException | SQLException e){
            Colors.printlnRed(e.getMessage());
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
            Colors.printlnCyan("\nVoici la liste des membres inscrits :\n");
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
            Colors.printlnCyan("\nRecherche d'un membre :\n");
            String lastname;
            List<Customer> customersList = null;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez un nom de membre à rechercher :");
            lastname = sc.nextLine();
            if(FieldChecks.isValidLastname(lastname)){
                customersList = DBHandler.getCustomers(lastname);
            }
            if(customersList.size()==0){
                Colors.printlnRed("Aucun membre n'a été trouvé.");
            }
            else {
                Colors.printlnCyan("Voici la liste des membres trouvés : \n");
                for (int i = 0; i < customersList.size(); i++) {
                    Colors.printColorfulCustomer(customersList.get(i));
                    Colors.printlnWhite("");
                }
            }
        }
        catch(IncorrectFieldException e){
            Colors.printlnRed(e.getMessage());
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
            Colors.printlnCyan("\nAjout d'un nouvel emprunt :\n");
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
            Colors.printlnWhite("Entrez l'identifiant du livre (format : [Chiffres]/[Chiffres ou lettres]):");
            book_identifier = sc.nextLine();
            Colors.printlnWhite("Entrez l'année d'expiration :");
            year = Integer.parseInt(sc.nextLine());
            Colors.printlnWhite("Entrez le mois d'expiration :");
            month = Integer.parseInt(sc.nextLine());
            Colors.printlnWhite("Entrez le jour d'expiration :");
            day = Integer.parseInt(sc.nextLine());
            expirationDate=new java.sql.Date(year-1900,month-1,day);
            if(FieldChecks.isValidBookIdentifier(book_identifier) && FieldChecks.isValidDate(day,month,year)){
                Book b = DBHandler.getBook(book_identifier);
                Customer c=findCustomer(member_id);
                if(b.getStock() <= 0){
                    Colors.printlnRed("Rupture de stock !");
                }
                else if(c.getLoanCount()>=3){
                    Colors.printlnRed("Ce membre a atteint la limite d'emprunts.");
                }
                else{
                    DBHandler.addLoan(book_identifier,member_id,expirationDate.toString());
                    Colors.printlnCyan("Ajout de l'emprunt du livre " + book_identifier+" par le membre d'ID " + member_id_string);
                }
            }
        }
        catch(NoSuchElementException e){
            Colors.printlnRed("L'identifiant du livre entré n'existe pas dans la base de données.");
        }
        catch(NumberFormatException e){
            Colors.printlnRed("Veuillez entrer une valeur numérique");
        }
        catch(IncorrectFieldException e){
            Colors.printlnRed(e.getMessage());
        }
        catch(Exception e){
            Colors.printlnRed(e.getMessage());
        }
    }

    /**
     * This method is used to find a customer in the database using his ID
     * @param ID The id of the customer
     * @return A found customer object
     * @throws Exception If the customer is not found
     */
    private static Customer findCustomer(int ID) throws Exception{
        try {
            List<Customer> customersList;
            customersList = DBHandler.getCustomers("");
            for (int i = 0; i < customersList.size(); i++) {
                if(customersList.get(i).getId()==ID){
                    return customersList.get(i);
                }
            }
            throw new Exception("Le membre n'a pas été trouvé dans la base de données.");
        }
        catch(NoSuchElementException e){
            throw new Exception("Le membre n'a pas été trouvé dans la base de données.");
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
            Colors.printlnCyan("\nMise à jour d'un emprunt :\n");
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
                    if(FieldChecks.isValidDate(day,month,year)){
                        java.sql.Date newDate=new java.sql.Date(year-1900,month-1,day);
                        DBHandler.updateExpirationDate(loan_id_string,newDate.toString());
                        Colors.printlnCyan("La date d'expiration a bien été mise à jour");
                    }
                }
                default -> {
                    Colors.printlnRed("Mauvaise valeur. Sélectionnez 1 ou 2.");
                }
            }
        }
        catch(IncorrectFieldException e){
            Colors.printlnRed(e.getMessage());
        }
        catch(NumberFormatException e){
            Colors.printlnRed("Veuillez entrer une valeur numérique.");
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
            Colors.printlnCyan("\nVoici la liste de tous les emprunts :\n");
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
            Colors.printlnCyan("\nListe des emprunts d'un membre :\n");
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
        catch(NumberFormatException e){
            Colors.printlnRed("Veuillez entrer une valeur numérique.");
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
            Colors.printlnCyan("\nListe des emprunts d'un livre :\n");
            String identifier;
            List<Loan> loanList;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez l'ID du livre (format : [Chiffres]/[Chiffres ou lettres]):");
            identifier = sc.nextLine();
            if(FieldChecks.isValidBookIdentifier(identifier)){
                loanList = DBHandler.getLoans();
                Colors.printlnCyan("\nVoici les emprunts liés au livre d'identifiant "+identifier+" :\n");
                for (int i = 0; i < loanList.size(); i++) {
                    if(loanList.get(i).getBookId().equals(identifier)){
                        Colors.printColorfulLoan(loanList.get(i));
                        Colors.printlnWhite("");
                    }
                }
            }
        }
        catch(IncorrectFieldException e){
            Colors.printlnRed("Veuillez entrer un identifiant valide pour le livre.");
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
            Colors.printlnCyan("\nVoici la liste des emprunts en cours :\n");
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
            Colors.printlnCyan("\nVoici la liste des emprunts expirés :\n");
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
            Colors.printlnCyan("\nCréation d'un nouveau compte :\n");
            String login;
            String password;
            String password2;
            String firstname;
            String lastname;
            Scanner sc = new Scanner(System.in);
            Colors.printlnWhite("Entrez le prénom :");
            firstname = sc.nextLine();
            Colors.printlnWhite("Entrez le nom :");
            lastname = sc.nextLine();
            Colors.printlnWhite("Entrez le login :");
            login = sc.nextLine();
            Colors.printlnWhite("Entrez le mot de passe :");
            password = sc.nextLine();
            Colors.printlnWhite("Confirmez le mot de passe :");
            password2 = sc.nextLine();
            if(password.equals(password2) && FieldChecks.isValidLogin(login) && FieldChecks.isValidLogin(password) && FieldChecks.isValidFirstname(firstname) && FieldChecks.isValidLastname(lastname)){
                DBHandler.addLibrarian(login,lastname, firstname,password);
                Colors.printlnCyan("Ajout du biblothécaire " + firstname + " " + lastname + ".");
            }
        }
        catch(IncorrectFieldException e){
            Colors.printlnRed(e.getMessage());
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
