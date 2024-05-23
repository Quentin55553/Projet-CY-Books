package org.openjfx.cybooks.API;

/**
 * Exception thrown when connection to the BNF's API using APIHandler object is giving an error
 */
public class APIErrorException extends Exception{

    /**
     * The error code given by the HTTPUrlConnection object if connection to the API fails
     */
    private int errorCode;

    /**
     * Constructor for the APIErrorException exception
     * @param message String. The error message
     * @param errorCode int. The error code of the exception
     */
    public APIErrorException(String message,int errorCode){
        super(message);
        this.errorCode=errorCode;
    }

    /**
     * Getter for the error code attribute
     * @return The error code of the exception (int)
     */
    public int getErrorCode() {
        return errorCode;
    }
}
