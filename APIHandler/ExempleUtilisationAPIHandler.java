
/*
Langues :
fre : français
ara : arabe
ger : allemand
bre : breton
lat : latin
 */

public class ExempleUtilisationAPIHandler {
    public static void main(String[] args){
        APIHandler API=new APIHandler();
        // Query 1
        System.out.println("QUERY 1");
        API.generateQuery("Oiseau","","","","","fre","Ornithologie");

        try {
            System.out.println(API.getQuery());
            API.exec();
            System.out.println("Number of results : "+API.getNumberOfResults());
            if(API.getNumberOfResults()==0){
                System.out.println("No matching entries found.");
            }
            else {
                for (int i = 0; i < Math.min(API.getNumberOfResults(), 15); i++) {
                    System.out.println(API.getResults().get(i));
                }
            }
        }
        catch(APIErrorException e){
            System.out.println("Error "+e.getErrorCode());
        }

        // Query 2
        System.out.println("QUERY 2");
        API.generateQuery("","Kafka","","","","","");
        try {
            System.out.println(API.getQuery());
            API.exec();
            System.out.println("Number of results : "+API.getNumberOfResults());
            if(API.getNumberOfResults()==0){
                System.out.println("No matching entries found.");
            }
            else {
                for (int i = 0; i < Math.min(API.getNumberOfResults(), 15); i++) {
                    System.out.println(API.getResults().get(i));
                }
            }
        }
        catch(APIErrorException e){
            System.out.println("Error "+e.getErrorCode());
        }
    }
}
