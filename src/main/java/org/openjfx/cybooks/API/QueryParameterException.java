package org.openjfx.cybooks.API;

/**
 *  Exception thrown when the query parameters of an APIHandler object contain forbidden characters
 */
public class QueryParameterException extends Exception{

    /**
     * Constructor for the QueryParameterException
     * @param message The error message
     */
    public QueryParameterException(String message){
        super(message);
    }
}
