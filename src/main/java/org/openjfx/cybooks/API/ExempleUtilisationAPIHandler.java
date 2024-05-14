package org.openjfx.cybooks.API;

import static java.lang.System.exit;

public class ExempleUtilisationAPIHandler {
    public static void main(String[] args){
        APIHandler API=new APIHandler();
        try {
            // Query 1 : time interval version
            System.out.println("QUERY 1");
            API.generateQueryTimeInterval("","","2000","2020","","","","");
            System.out.println(API.getQuery());
            API.exec();
            System.out.println("Number of results : "+API.getNumberOfResults());
            if(API.getNumberOfResults()==0){
                System.out.println("No matching entries found.");
            }
            else {
                for (int i = 0; i < API.getNumberOfResults(); i++) {
                    System.out.println(API.getResults().get(i));
                }
            }
        }
        catch(APIErrorException e){
            System.out.println("There was an error during communication with the API. Error code : "+e.getErrorCode());
            exit(4);
        }
        catch(QueryParameterException e){
            System.out.println(e.getMessage());
            exit(3);
        }

        try {
            // Query 2 : standard version
            System.out.println("QUERY 2");
            API.generateQueryStandard("","Franz Kafka","","","","","");
            System.out.println(API.getQuery());
            API.exec();
            System.out.println("Number of results : "+API.getNumberOfResults());
            if(API.getNumberOfResults()==0){
                System.out.println("No matching entries found.");
            }
            else {
                for (int i = 0; i < API.getNumberOfResults(); i++) {
                    System.out.println(API.getResults().get(i));
                }
            }
        }
        catch(APIErrorException e){
            System.out.println("There was an error during communication with the API. Error code : "+e.getErrorCode());
            exit(4);
        }
        catch(QueryParameterException e){
            System.out.println(e.getMessage());
            exit(3);
        }
    }
}
