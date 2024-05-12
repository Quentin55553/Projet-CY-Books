
/*
Types :
manuscript / manuscrit (utiliser "manuscrit" pour les recherches)
text (ne marche pas pour les recherches)
monographie imprimée / printed monograph (utiliser "monographie" pour les recherches)
image (utiliser "image" pour les recherches)

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
        API.generateQuery("","","","","","fre","Ornithologie");
        try {
            System.out.println(API.getQuery());
            API.exec();
            for(int i=0; i<Math.min(API.getNumberOfResults(),15);i++){
                System.out.println(API.getResults().get(i));
            }
        }
        catch(APIErrorException e){
            System.out.println("Error "+e.getErrorCode());
        }
    }
}
