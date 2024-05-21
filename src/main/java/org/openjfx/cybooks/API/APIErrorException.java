package org.openjfx.cybooks.API;

/**
 * Exception thrown when connection to the BNF's API using APIHandler object is giving an error
 */
public class APIErrorException extends Exception{
    private int errorCode;
    public APIErrorException(String message,int errorCode){
        super(message);
        this.errorCode=errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
