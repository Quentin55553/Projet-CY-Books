package org.openjfx.cybooks.UserInput;

public class FieldChecks {


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

    public static boolean isValidCustomer(String firstname, String lastname, String telephone, String email, String address) throws IncorrectFieldException {
        return isValidFirstname(firstname) && isValidLastname(lastname) && isValidPhoneNumber(telephone) && isValidEmail(email) && isValidAddress(address);
    }
}
