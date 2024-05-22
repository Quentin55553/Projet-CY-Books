package org.openjfx.cybooks.CommandLine;

import org.openjfx.cybooks.API.SearchResult;
import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Librarian;
import org.openjfx.cybooks.data.Loan;

/**
 * This class is used to print colored text on the UNIX terminal
 * It is made use of in CommandLineHandler
 */
public class Colors {

    /**
     * Color code of the red color.
     */
    public static final String RED="\u001B[31m";
    /**
     * Color code of the green color.
     */
    public static final String GREEN="\u001B[32m";
    /**
     * Color code of the blue color.
     */
    public static final String BLUE="\u001B[34m";
    /**
     * Color code of the yellow color.
     */
    public static final String YELLOW="\u001B[33m";
    /**
     * Color code of the purple color.
     */
    public static final String PURPLE="\u001B[35m";
    /**
     * Color code of the cyan color.
     */
    public static final String CYAN="\u001B[36m";
    /**
     * Color code of the black color.
     */
    public static final String BLACK="\u001B[30m";
    /**
     * Color code of the white color.
     */
    public static final String WHITE="\u001B[37m";
    /**
     * Code that can reset the output color to default.
     */
    public static final String RESET="\u001B[0m";

    /**
     * Equivalent to println for printing in red
     * @param str To be printed string
     */
    public static void printlnRed(String str){
        System.out.println(RED+str+RESET);
    }
    /**
     * Equivalent to print for printing in red
     * @param str To be printed string
     */
    public static void printRed(String str){
        System.out.print(RED+str+RESET);
    }

    /**
     * Equivalent to println for printing in green
     * @param str To be printed string
     */
    public static void printlnGreen(String str){
        System.out.println(GREEN+str+RESET);
    }
    /**
     * Equivalent to print for printing in green
     * @param str To be printed string
     */
    public static void printGreen(String str){
        System.out.print(GREEN+str+RESET);
    }

    /**
     * Equivalent to println for printing in blue
     * @param str To be printed string
     */
    public static void printlnBlue(String str){
        System.out.println(BLUE+str+RESET);
    }
    /**
     * Equivalent to print for printing in blue
     * @param str To be printed string
     */
    public static void printBlue(String str){
        System.out.print(BLUE+str+RESET);
    }

    /**
     * Equivalent to println for printing in purple
     * @param str To be printed string
     */
    public static void printlnPurple(String str){
        System.out.println(PURPLE+str+RESET);
    }
    /**
     * Equivalent to print for printing in purple
     * @param str To be printed string
     */
    public static void printPurple(String str){
        System.out.print(PURPLE+str+RESET);
    }

    /**
     * Equivalent to println for printing in yellow
     * @param str To be printed string
     */
    public static void printlnYellow(String str){
        System.out.println(YELLOW+str+RESET);
    }
    /**
     * Equivalent to print for printing in yellow
     * @param str To be printed string
     */
    public static void printYellow(String str){
        System.out.print(YELLOW+str+RESET);
    }

    /**
     * Equivalent to println for printing in cyan
     * @param str To be printed string
     */
    public static void printlnCyan(String str){
        System.out.println(CYAN+str+RESET);
    }
    /**
     * Equivalent to print for printing in cyan
     * @param str To be printed string
     */
    public static void printCyan(String str){
        System.out.print(CYAN+str+RESET);
    }

    /**
     * Equivalent to println for printing in black
     * @param str To be printed string
     */
    public static void printlnBlack(String str){
        System.out.println(BLACK+str+RESET);
    }
    /**
     * Equivalent to print for printing in black
     * @param str To be printed string
     */
    public static void printBlack(String str){
        System.out.print(BLACK+str+RESET);
    }
    /**
     * Equivalent to println for printing in white
     * @param str To be printed string
     */
    public static void printlnWhite(String str){
        System.out.println(WHITE+str+RESET);
    }
    /**
     * Equivalent to print for printing in white
     * @param str To be printed string
     */
    public static void printWhite(String str){
        System.out.print(WHITE+str+RESET);
    }

    /**
     * Command line method used to print the information of a Book object with colored output
     * @param B The Book object
     */
    public static void printColorfulBook(Book B){

    }

    /**
     * Command line method used to print the information of a SearchResult object with colored output
     * @param SR The SearchResult object
     */
    public static void printColorfulSearchResult(SearchResult SR){
        printCyan("Titre : ");
        printlnWhite(SR.getTitle());

        printCyan("Auteur(s) : ");
        printlnWhite(SR.getAuthors().toString());

        printCyan("Date de publication : ");
        printlnWhite(SR.getDate());

        printCyan("Identifiant : ");
        printlnWhite(SR.getIdentifier());

        printCyan("Thèmes : ");
        printlnWhite(SR.getSubjects().toString());

        printCyan("Description : ");
        printlnWhite(SR.getDescription());

        printCyan("Langue : ");
        printlnWhite(SR.getLanguage());

        printCyan("Image : ");
        printlnWhite(SR.getImageLink());
    }

    /**
     * Command line method used to print the information of a Customer object with colored output
     * @param C The Customer object
     */
    public static void printColorfulCustomer(Customer C){
        printBlue("ID : ");
        printlnWhite(String.valueOf(C.getId()));

        printCyan("Prénom : ");
        printlnWhite(C.getFirstName());

        printCyan("Nom : ");
        printlnWhite(C.getLastName());

        printCyan("E-mail : ");
        printlnWhite(C.getEmail());

        printCyan("Téléphone : ");
        printlnWhite(C.getTel());

        printCyan("Adresse : ");
        printlnWhite(C.getAddress());
    }

    /**
     * Command line method used to print the information of a Loan object with colored output
     * @param L The Loan object
     */
    public static void printColorfulLoan(Loan L){
        printBlue("ID : ");
        printlnWhite(String.valueOf(L.getId()));

        printCyan("ID du membre : ");
        printlnWhite(String.valueOf(L.getCustomerId()));

        printCyan("ID du livre : ");
        printlnWhite(L.getBookId());

        printCyan("Date de début : ");
        printlnWhite(L.getBeginDate().toString());

        printCyan("Date de fin : ");
        printlnWhite(L.getExpirationDate().toString());

        printCyan("Complété : ");
        if(L.isCompleted()){
            printlnGreen("Oui");
        }
        else{
            printlnWhite("Non");
            printCyan("En retard : ");
            if(L.hasExpired()){
                printlnRed("Oui");
            }
            else{
                printlnWhite("Non");
            }
        }
    }


    /**
     * Command line method used to print the information of a Librarian object with colored output
     * @param L The Librarian object to print
     * @param login Their login
     */
    public static void printColorfulLibrarian(Librarian L, String login){
        printBlue("ID : ");
        printlnWhite(String.valueOf(L.getId()));

        printCyan("Prénom : ");
        printlnWhite(L.getFirstName());

        printCyan("Nom : ");
        printlnWhite(L.getLastName());

        printCyan("Identifiant : ");
        printlnWhite(login);
    }
}
