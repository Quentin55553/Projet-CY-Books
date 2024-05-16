package org.openjfx.cybooks.API;

import static java.lang.System.exit;

public class ExempleUtilisationAPIHandler {
    public static void main(String[] args){
        APIHandler API=new APIHandler();
        try {
            // Query 1 : time interval version (can (and will in most cases) give a few unmatching results)
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
            System.out.println(e.getMessage());
            exit(4);
        }
        catch(QueryParameterException e){
            System.out.println(e.getMessage());
            exit(3);
        }

        try {
            // Query 2 : standard version (recommended)
            System.out.println("QUERY 2");
            API.generateQueryStandard("","","","https://gallica.bnf.fr/ark:/12148/bpt6k33646735","","","");
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
            System.out.println(e.getMessage());
            exit(4);
        }
        catch(QueryParameterException e){
            System.out.println(e.getMessage());
            exit(3);
        }
    }
}
