package org.openjfx.cybooks.UserInput;


/**
 * This exception arises when the user tries to enter an incorrect phone number in one of the app's fields
 */
public class IncorrectPhoneNumberException extends IncorrectFieldException {

    /**
     * Constructor for the IncorrectPhoneNumberException exception
     * @param message String. The error message
     */
    public IncorrectPhoneNumberException(String message) {
        super(message);
    }
}
