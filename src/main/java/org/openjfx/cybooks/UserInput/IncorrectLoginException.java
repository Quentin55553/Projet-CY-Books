package org.openjfx.cybooks.UserInput;


/**
 * This exception arises when the user tries to enter an incorrect name in one of the app's fields
 */
public class IncorrectLoginException extends IncorrectFieldException {
    /**
     * Constructor for the IncorrectLoginException exception
     * @param message String. The error message
     */
    public IncorrectLoginException(String message) {
        super(message);
    }
}