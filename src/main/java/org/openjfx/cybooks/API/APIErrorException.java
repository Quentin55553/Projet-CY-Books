package org.openjfx.cybooks.API;

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
