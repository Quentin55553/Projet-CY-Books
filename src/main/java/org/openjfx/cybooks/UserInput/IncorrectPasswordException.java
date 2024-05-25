package org.openjfx.cybooks.UserInput;


/**
 * This exception arises when the user enters an incorrect password
 */
public class IncorrectPasswordException extends IncorrectFieldException {
    /**
     * Constructor for the IncorrectPasswordException exception
     * @param message String. The error message
     */
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
