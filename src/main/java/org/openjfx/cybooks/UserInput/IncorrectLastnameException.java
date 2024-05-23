package org.openjfx.cybooks.UserInput;


/**
 * This exception arises when the user tries to enter an incorrect name in one of the app's fields
 */
public class IncorrectLastnameException extends IncorrectFieldException {

    /**
     * Constructor for the IncorrectLastnameException exception
     * @param message String. The error message
     */
    public IncorrectLastnameException(String message) {
        super(message);
    }
}
