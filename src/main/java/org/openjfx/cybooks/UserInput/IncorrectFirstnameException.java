package org.openjfx.cybooks.UserInput;


/**
 * This exception arises when the user tries to enter an incorrect name in one of the app's fields
 */
public class IncorrectFirstnameException extends IncorrectFieldException {
    /**
     * Constructor for the IncorrectFirstnameException exception
     * @param message String. The error message
     */
    public IncorrectFirstnameException(String message) {
        super(message);
    }
}
