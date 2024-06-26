package org.openjfx.cybooks.UserInput;

import org.openjfx.cybooks.data.Librarian;
import org.openjfx.cybooks.database.DBHandler;

import java.time.LocalDate;
import java.util.Date;
import java.util.NoSuchElementException;


/**
 * This class' goal is to manage the user's inputs, not allowing them to enter invalid symbols and values in the app's fields
 */
public class FieldChecks {
    /**
     * This method verifies a 'firstname' field. Only accentuated and unaccentuated letters are allowed
     * @param firstname The String to check
     * @return true if the String is valid
     * @throws IncorrectFirstnameException If the String is invalid
     */
    public static boolean isValidFirstname(String firstname) throws IncorrectFirstnameException {
        String format = "\\p{L}+";
        String newFirstname = firstname.trim();

        if (newFirstname.isEmpty()) {
            throw new IncorrectFirstnameException("Le prénom ne peut pas être vide");
        }

        if (!newFirstname.matches(format)) {
            throw new IncorrectFirstnameException("Le format du prénom est incorrect");
        }

        return true;
    }


    /**
     * This method verifies a 'lastname' field. Only accentuated and unaccentuated letters are allowed
     * @param lastname The String to check
     * @return true if the String is valid
     * @throws IncorrectLastnameException If the String is invalid
     */
    public static boolean isValidLastname(String lastname) throws IncorrectLastnameException {
        String format = "\\p{L}+";
        String newLastname = lastname.trim();

        if (newLastname.isEmpty()) {
            throw new IncorrectLastnameException("Le nom ne peut pas être vide");
        }

        if (!newLastname.matches(format)) {
            throw new IncorrectLastnameException("Le format du nom est incorrect");
        }

        return true;
    }


    /**
     * This method verifies a 'phone number' field. Exactly 10 numbers need to be written, the first one being 0
     * @param tel The String to check
     * @return true if the String is valid
     * @throws IncorrectPhoneNumberException If the String is invalid
     */
    public static boolean isValidPhoneNumber(String tel) throws IncorrectPhoneNumberException {
        String format = "^(0[1-9])\\d{8}$";
        String newTel = tel.trim();

        if (newTel.isEmpty()) {
            throw new IncorrectPhoneNumberException("Le numéro de téléphone ne peut pas être vide");
        }

        if (!newTel.matches(format)) {
            throw new IncorrectPhoneNumberException("Le format du numéro de téléphone est incorrect");
        }

        return true;
    }


    /**
     * This method verifies an 'email' field. Needs to be of the form XXXX@YYY.ZZZ
     * @param email The String to check
     * @return true if the String is valid
     * @throws IncorrectEmailException If the String is invalid
     */
    public static boolean isValidEmail(String email) throws IncorrectEmailException {
        String format = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        String newEmail = email.trim();

        if (newEmail.isEmpty()) {
            throw new IncorrectEmailException("L'email ne peut pas être vide");
        }

        if (!newEmail.matches(format)) {
            throw new IncorrectEmailException("Le format de l'adresse email est incorrect");
        }

        return true;
    }


    /**
     * This method verifies if an 'adress' field is valid. A number followed by a road / avenue / boulevard name need to be written
     * @param address The String to check
     * @return true if the String is valid
     * @throws IncorrectAddressException If the String is invalid
     */
    public static boolean isValidAddress(String address) throws IncorrectAddressException {
        String newAddress = address.trim();

        // Checks whether the address is empty or null
        if (newAddress.isEmpty()) {
            throw new IncorrectAddressException("L'adresse ne peut pas être vide");
        }

        // Splits the address into two parts at the first space
        String[] parts = newAddress.split("\\s+", 2);
        // Checks whether there are two distinct parts in the address and whether the first one only contains numbers
        if (parts.length != 2 || !parts[0].matches("\\d+")) {
            throw new IncorrectAddressException("Le format de l'adresse est incorrect");
        }

        return true;
    }


    /**
     * This method verifies a 'login' field. Only unaccentuated letters and numbers are allowed
     * @param login The login String to check
     * @return true if the String is valid
     * @throws IncorrectLoginException If the String is invalid
     */
    public static boolean isValidLogin(String login) throws IncorrectLoginException {
        String format = "[a-zA-Z0-9]+";
        String newLogin = login.trim();

        if (newLogin.isEmpty()) {
            throw new IncorrectLoginException("Le login ne peut pas être vide");
        }

        if (!newLogin.matches(format)) {
            throw new IncorrectLoginException("Le format du login est incorrect");
        }

        return true;
    }


    /**
     * This method verifies a 'password' field. Every character is allowed. There can't be only spaces.
     * @param password The password String to check
     * @return true if the String is valid
     * @throws IncorrectPasswordException If the String is invalid
     */
    public static boolean isValidPassword(String password) throws IncorrectPasswordException {
        String newPassword = password.trim();

        if (newPassword.isEmpty()) {
            throw new IncorrectPasswordException("Le mot de passe doit contenir au moins un caractère");
        }

        return true;
    }


    /**
     * This method verifies if two given passwords are the same.
     * @param password The first password String to check
     * @param confirmedPassword The second password String to check
     * @return true if both String are matching
     * @throws IncorrectPasswordException If both String don't match
     */
    public static boolean areMatchingPasswords(String password, String confirmedPassword) throws IncorrectPasswordException {
        if (!password.equals(confirmedPassword)) {
            throw new IncorrectPasswordException("Les mots de passe doivent être identiques");
        }

        return true;
    }


    /**
     * This method checks if a given book identifier is valid
     * The needed format is : [Some numbers]/[Some alphanumeric characters]
     * @param identifier The identifier to check
     * @return true if the identifier is valid
     * @throws IncorrectBookIdException If the identifier's format is not correct
     */
    public static boolean isValidBookIdentifier(String identifier) throws IncorrectBookIdException {
        String format = "^[0-9]+/[a-zA-Z0-9]+$";
        String newIdentifier = identifier.trim();

        if (!newIdentifier.matches(format)) {
            throw new IncorrectBookIdException("Le format de l'identifiant du livre est incorrect");
        }

        return true;
    }


    /**
     * This method checks if a given date is valid i.e. not before or equals to today's date and that it has correct month/days/years
     * @param day The day of the date
     * @param month The month of the date
     * @param year The year of the date
     * @return true if the date is valid
     * @throws IncorrectDateException If the date is not as specified
     */
    public static boolean isValidDate(int day, int month, int year) throws IncorrectDateException {
        Date today = new Date();
        Date date = new Date(year-1900, month-1, day);

        if (year>=2100) {
            throw new IncorrectDateException("L'année d'expiration est un peu trop lointaine !");

        }
        if (month<=0 || month >=13) {
            throw new IncorrectDateException("La date entrée n'est pas valide.");
        }

        int notvalid = 0;

        switch (month) {
            case 1,3,5,7,8,10,12 -> {
                if (day >= 32 || day <=0) {
                    notvalid = 1;
                }
            }
            case 4,6,9,11 -> {
                if (day >= 31 || day <=0) {
                    notvalid = 1;
                }
            }
            case 2 -> {
                if (day >= 29 || day <=0) {
                    notvalid = 1;
                }
            }
            default -> {
                notvalid = 1;
            }
        }

        if (notvalid == 1) {
            throw new IncorrectDateException("La date entrée n'est pas valide.");
        }
        if (date.before(today)) {
            throw new IncorrectDateException("La date ne peut pas être passée !");
        }
        else if (date.equals(today)) {
            throw new IncorrectDateException("La date ne peut pas être aujourd'hui !");
        }

        return true;
    }


    /**
     * This method checks the format of a book release year. It must contain only 4 numbers
     * @param year The string to check
     * @return true if the string is valid
     * @throws IncorrectDateException If the string is not valid
     */
    public static boolean isValidBookReleaseYear(String year) throws IncorrectDateException {
        String format = "^\\d{4}$";
        String newYear = year.trim();

        if (newYear.isEmpty()) {
            return true;
        }

        if (!newYear.matches(format)) {
            throw new IncorrectDateException("L'année de publication du livre est incorrecte");
        }

        int releaseYear = Integer.parseInt(newYear);

        int currentYear = LocalDate.now().getYear();

        if (releaseYear > currentYear) {
            throw new IncorrectDateException("L'année de publication du livre ne peut pas être dans le futur");
        }

        return true;
    }


    /**
     * This method checks if a given customer identifier is valid, i.e. if it only contains numbers
     * @param identifier The identifier to check
     * @return true if the identifier is valid
     * @throws IncorrectCustomerIdException If the identifier's format is not correct
     */
    public static boolean isValidCustomerIdentifier(String identifier) throws IncorrectCustomerIdException {
        String format = "^[0-9]+$";
        String newIdentifier = identifier.trim();

        if (newIdentifier.isEmpty()) {
            return true;
        }

        if (!newIdentifier.matches(format)) {
            throw new IncorrectCustomerIdException("Le format de l'identifiant du membre est incorrect");
        }

        return true;
    }


    /**
     * This method verifies all the fields needed to create a new user in the database
     * @param firstname Only letters are allowed
     * @param lastname Only letters are allowed
     * @param telephone Exactly 10 numbers need to be written, the first one being 0
     * @param email Needs to be of the form XXXX@YYY.ZZZ
     * @param address A number followed by the road / avenue / boulevard name
     * @return true if all fields are correct
     * @throws IncorrectFieldException If one of the verifications fails
     */
    public static boolean isValidCustomer(String firstname, String lastname, String telephone, String email, String address) throws IncorrectFieldException {
        return isValidFirstname(firstname) && isValidLastname(lastname) && isValidPhoneNumber(telephone) && isValidEmail(email) && isValidAddress(address);
    }


    /**
     * This method checks if the signup information entered is valid
     * @param login The login String
     * @param lastname The lastname String
     * @param firstname The firstname String
     * @param password The password field
     * @param confirmedPassword The password confirmation field
     * @return true if all the strings are valid, false if one of them is not
     * @throws IncorrectFieldException If one of the verifications fails
     */
    public static boolean isValidSignUp(String login, String lastname, String firstname, String password, String confirmedPassword) throws IncorrectFieldException {
        return isValidLogin(login) && isValidLastname(lastname) && isValidFirstname(firstname) && isValidPassword(password) && areMatchingPasswords(password, confirmedPassword);
    }


    /**
     * This method checks if a given number of loans is valid
     * @param loansNumber The number of loans
     * @return true if the number of loans is a whole number
     * @throws IncorrectLoansNumberException If the verification fails
     */
    public static boolean isValidNumbersOfLoans(String loansNumber) throws IncorrectLoansNumberException {
        String format = "^\\d+$";
        String newLoansNumber = loansNumber.trim();

        if (!newLoansNumber.matches(format)) {
            throw new IncorrectLoansNumberException("Le nombre d'emprunts doit être un nombre entier");
        }

        return true;
    }


    /**
     * This method checks if all parameters are valid for a book filter dialog
     * @param year The release year of the book
     * @param bookId The book's identifier
     * @return true if both parameters are valid
     * @throws IncorrectFieldException If one of the verifications fails
     */
    public static boolean isValidDialogBookFilter(String year, String bookId) throws IncorrectFieldException {
        boolean isValid = isValidBookReleaseYear(year);

        if (!bookId.trim().isEmpty() && !isValidBookIdentifier(bookId)) {
            isValid = false;
        }

        return isValid;
    }


    /**
     * This method checks if all parameters are valid for a loan filter dialog
     * @param customerId The customer's identifier
     * @param bookId The book's identifier
     * @return true if both parameters are valid
     * @throws IncorrectFieldException If one of the verifications fails
     */
    public static boolean isValidDialogLoanFilter(String customerId, String bookId) throws IncorrectFieldException {
        boolean isValid = isValidCustomerIdentifier(customerId);

        if (!bookId.trim().isEmpty() && !isValidBookIdentifier(bookId)) {
            isValid = false;
        }

        return isValid;
    }


    /**
     * This method checks if information entered for a customer filter dialog is valid
     * @param lastname The lastname of the customer
     * @param firstname The firstname of the customer
     * @param customerId The customer's ID
     * @param email Their email adress
     * @param tel Their phone number adress
     * @param address Their address
     * @param loansInferiorTo Upper bound for the number of loans they have
     * @param loansSuperiorTo Lower bound for the number of loans they have
     * @return true if all the formats are valid
     * @throws IncorrectFieldException If one of the verifications fails
     */
    public static boolean isValidDialogUserFilter(String lastname, String firstname, String customerId, String email, String tel, String address, String loansInferiorTo, String loansSuperiorTo) throws IncorrectFieldException {
        boolean isValid = true;

        if (!lastname.trim().isEmpty() && !isValidLastname(lastname)) {
            isValid = false;
        }

        if (!firstname.trim().isEmpty() && !isValidFirstname(firstname)) {
            isValid = false;
        }

        if (!customerId.trim().isEmpty() && !isValidCustomerIdentifier(customerId)) {
            isValid = false;
        }

        if (!email.trim().isEmpty() && !isValidEmail(email)) {
            isValid = false;
        }

        if (!tel.trim().isEmpty() && !isValidPhoneNumber(tel)) {
            isValid = false;
        }

        if (!address.trim().isEmpty() && !isValidAddress(address)) {
            isValid = false;
        }

        if (!loansInferiorTo.trim().isEmpty() && !isValidNumbersOfLoans(loansInferiorTo)) {
            isValid = false;
        }

        if (!loansSuperiorTo.trim().isEmpty() && !isValidNumbersOfLoans(loansSuperiorTo)) {
            isValid = false;
        }

        return isValid;
    }


    /**
     * This method checks if entered credentials are valid
     * @param login The login String
     * @param password The password String
     * @return true if the credentials are valid, false if not
     */
    public static boolean areValidCredentials(String login, String password) throws NoSuchElementException, IncorrectFieldException {
        Librarian librarian = null;

        isValidLogin(login);
        isValidPassword(password);
        librarian = DBHandler.librarianAuthentication(login, password);

        // Returns 'true' if the 'librarian' variable is not null, 'false' otherwise
        return librarian != null;
    }
}
