package org.openjfx.cybooks.UserInput;


/**
 * This exception arises when the user tries to enter an incorrect value in one of the app's fields
 */
public class IncorrectFieldException extends Exception {

    /**
     * Constructor for the IncorrectFieldException exception
     * @param message String. The error message
     */
    public IncorrectFieldException(String message) {
        super(message);
    }
}
