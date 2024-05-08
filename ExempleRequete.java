import java.net.*;
import java.util.*;

import static java.lang.System.exit;
/*
Note pour les URLs :
( = %28
) = %29
" = %22
Espace = %20
...etc
 */
public class ExempleRequete {
    public static void main(String[] args) {
        try{
            URL url = new URL("https://gallica.bnf.fr/SRU?operation=searchRetrieve&version=1.2&query=dc.creator%20any%20Kafka");

            HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
            connexion.setRequestMethod("GET");
            connexion.connect();

            int responseCode= connexion.getResponseCode();

            if(responseCode!=200){
                System.out.println("Erreur : "+responseCode);
                exit(0);
            }
            else{
                StringBuilder informationString = new StringBuilder();
                Scanner scanner= new Scanner(url.openStream());
                while(scanner.hasNext()){
                    informationString.append("\n"+scanner.nextLine());
                }
                scanner.close();

                System.out.println(informationString);

            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            exit(0);
        }
    }
}