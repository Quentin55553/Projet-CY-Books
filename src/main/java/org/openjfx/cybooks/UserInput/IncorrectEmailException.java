package org.openjfx.cybooks.UserInput;


/**
 * This exception arises when the user tries to enter an incorrect e-mail adress in one of the app's fields
 */
public class IncorrectEmailException extends IncorrectFieldException {
    /**
     * Constructor for the IncorrectEmailException exception
     * @param message String. The error message
     */
    public IncorrectEmailException(String message) {
        super(message);
    }
}
