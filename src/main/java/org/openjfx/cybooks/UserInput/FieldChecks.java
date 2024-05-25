package org.openjfx.cybooks.UserInput;


/**
 * This class' goal is to manage the user's inputs, not allowing them to enter invalid symbols and values in the app's fields
 */
public class FieldChecks {


    /**
     * This method verifies a 'firstname' field. Only unaccentuated letters are allowed
     * @param firstname The String to check
     * @return true if the String is valid
     * @throws IncorrectFirstnameException If the String is invalid
     */
    public static boolean isValidFirstname(String firstname) throws IncorrectFirstnameException {
        String format = "[a-zA-Z]+";
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
     * This method verifies a 'lastname' field. Only unaccentuated letters are allowed
     * @param lastname The String to check
     * @return true if the String is valid
     * @throws IncorrectLastnameException If the String is invalid
     */
    public static boolean isValidLastname(String lastname) throws IncorrectLastnameException {
        String format = "[a-zA-Z]+";
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
     * This method verifies a 'login' field. Only unaccentuated letters are allowed
     * @param login The login String to check
     * @return true if the String is valid
     * @throws IncorrectFirstnameException If the String is invalid
     */
    public static boolean isValidLogin(String login) throws IncorrectLoginException {
        String format = "[a-zA-Z0-9]+";
        String newLogin = login.trim();

        if (newLogin.isEmpty()) {
            throw new IncorrectLoginException("Le LOGIN ne peut pas être vide");
        }

        if (!newLogin.matches(format)) {
            throw new IncorrectLoginException("Le format du login est incorrect");
        }
        return true;
    }

    public static boolean isValidBookIdentifier(String identifier) throws IncorrectFieldException{
        return true;
    }

    public static boolean isValidDate(int day, int month, int year) throws IncorrectFieldException{
        return true;
    }


    /**
     * This method verifies all the fields needed to create a new user in the database
     * @param firstname Only unaccentuated letters are allowed
     * @param lastname Only unaccentuated letters are allowed
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
     */
    public static boolean isValidSignUp(String login, String lastname, String firstname, String password, String confirmedPassword) throws IncorrectFieldException{
        if(isValidFirstname(firstname) && isValidLastname(lastname) && isValidLogin(login)){
            // Assuming a valid sign up requires a unique login and matching passwords
            return !login.isEmpty() && !lastname.isEmpty() && !firstname.isEmpty() && !password.isEmpty() && password.equals(confirmedPassword);
        }
        return false;
    }
}
