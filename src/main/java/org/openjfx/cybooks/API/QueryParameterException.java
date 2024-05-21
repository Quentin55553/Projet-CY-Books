package org.openjfx.cybooks.API;

/**
 *  Exception thrown when the query parameters of an APIHandler object contain forbidden characters
 */
public class QueryParameterException extends Exception{
    public QueryParameterException(String message){
        super(message);
    }
}
