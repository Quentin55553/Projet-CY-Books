package org.openjfx.cybooks.UserInput;


/**
 * This exception arises when the user tries to enter an incorrect adress in one of the app's fields
 */
public class IncorrectAddressException extends IncorrectFieldException {
    /**
     * Constructor for the IncorrectAdressException exception
     * @param message String. The error message
     */
    public IncorrectAddressException(String message) {
        super(message);
    }
}
