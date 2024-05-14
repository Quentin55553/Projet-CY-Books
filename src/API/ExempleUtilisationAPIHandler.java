package API;

public class ExempleUtilisationAPIHandler {
    public static void main(String[] args){
        APIHandler API=new APIHandler();
        // Query 1 : time interval version
        System.out.println("QUERY 1");
        API.generateQueryTimeInterval("","","2000","2020","","","","");

        try {
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
            System.out.println("Error "+e.getErrorCode());
        }

        // Query 2 : standard version
        System.out.println("QUERY 2");
        API.generateQueryStandard("","Franz Kafka","","","","","");
        try {
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
            System.out.println("Error "+e.getErrorCode());
        }
    }
}
