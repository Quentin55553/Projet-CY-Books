import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import static java.lang.System.exit;

/**
 * Class used to manage searches done using Gallica and their results
 *
 * To get results of a search :
 * 1. Create an APIHandler object
 * 2. Call generateQuery()
 * 3. Call exec()
 * 4. Results can be found in the results attribute
 *
 * (Internet connection required)
 */
public class APIHandler {
    /**
     * String representing the URL with which we will connect to Gallica
     */
    private String query;
    /**
     * Integer representing the number of database entries found for a search
     * Updated by the exec() method
     */
    private int numberOfResults;
    /**
     * The list of entries found for a search
     * Updated by the exec() method
     * A search result is represented by the SearchResult class, which contains information about the document (title, authors...)
     */
    private ArrayList<SearchResult> results;

    /**
     * Constructor for an APIHandler object
     * After using this method to create the object, the query is generated using generateQuery() and the search is done with exec()
     */
    public APIHandler(){
        this.query=new String("https://gallica.bnf.fr/SRU?operation=searchRetrieve&version=1.2&query=");
        this.results=new ArrayList<SearchResult>();
    }

    /**
     * Getter for the query attribute
     * @return The current query (String)
     */
    public String getQuery(){
        return this.query;
    }

    /**
     * Getter for the numberOfResults attribute
     * @return The number of results found for the last search
     */
    public int getNumberOfResults() {
        return numberOfResults;
    }

    /**
     * Getter for the results attribute
     * @return An ArrayList containing numberOfResults SearchResult objects.
     */
    public ArrayList<SearchResult> getResults() {
        return results;
    }

    /**
     * This method generates a CQL query corresponding to the given parameters. The query attribute is updated accordingly
     * @param title String. A title to search for
     * @param author String. An author to search for
     * @param date String. A date to search for
     * @param identifier String. An identifier to search for
     * @param doctype String. A type of document to search for
     * @param language String. A language to search for
     * @param subject String. A subject to search for
     */
    public void generateQuery(String title, String author, String date, String identifier, String doctype, String language, String subject){
        boolean andNeeded=false;

        if(!title.equals("")){
            this.query+="dc.title%20any%20"+title;
            andNeeded=true;
        }
        if(!author.equals("")){
            if(andNeeded==true){
                this.query+="%20and%20";
            }
            this.query+="dc.creator%20any%20"+author;
            andNeeded=true;
        }
        if(!date.equals("")){
            if(andNeeded==true){
                this.query+="%20and%20";
            }
            this.query+="dc.date%20any%20"+date;
            andNeeded=true;
        }
        if(!identifier.equals("")){
            if(andNeeded==true){
                this.query+="%20and%20";
            }
            this.query+="dc.title%20any%20"+identifier;
            andNeeded=true;
        }
        if(!doctype.equals("")){
            if(andNeeded==true){
                this.query+="%20and%20";
            }
            this.query+="dc.type%20any%20"+doctype;
            andNeeded=true;
        }
        if(!language.equals("")){
            if(andNeeded==true){
                this.query+="%20and%20";
            }
            this.query+="dc.language%20any%20"+language;
            andNeeded=true;
        }
        if(!subject.equals("")){
            if(andNeeded==true){
                this.query+="%20and%20";
            }
            this.query+="dc.subject%20any%20"+subject;
        }

    }

    /**
     * This method sends a request to Gallica using the query attribute and puts results in the corresponding attribute
     * @return Returns the number of results found for the search
     * @throws APIErrorException If a communication error occurs between Gallica and the program
     */
    public int exec() throws APIErrorException{
        try{
            // Connexion à Gallica avec la requête
            URL url = new URL(this.query);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode= connection.getResponseCode();

            if(responseCode!=200){
                throw new APIErrorException(responseCode);
            }
            else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner= new Scanner(url.openStream());
                while(scanner.hasNext()){
                    informationString.append(scanner.nextLine()+"\n");
                }
                scanner.close();
                this.parseResults(informationString);
                return this.numberOfResults;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            exit(111);
            return -1;
        }
    }

    /**
     * This method parses the content of the XML file sent by Gallica following the search request and puts the content in the results attribute
     * @param xml A StringBuilder object representing the XML file sent by Gallica
     */
    public void parseResults(StringBuilder xml) {
        try {
            //System.out.println(xml);

            // StringBuilder to String
            String xmlString = xml.toString();
            // String to Document
            Document doc = loadXMLFromString(xmlString);


            // Document to ArrayList<SearchResult>
            doc.getDocumentElement().normalize();

            // NUMBER OF RESULTS
            NodeList numberOfResults = doc.getElementsByTagName("srw:numberOfRecords");
            Node number = numberOfResults.item(0);
            Element nor = (Element) number;
            this.numberOfResults = Integer.parseInt(nor.getTextContent());

            // RESULTS
            if (this.numberOfResults != 0) {
                ArrayList<SearchResult> results = new ArrayList<SearchResult>();
                NodeList nList = doc.getElementsByTagName("oai_dc:dc");

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    String title;
                    String date;
                    ArrayList<String> authors = new ArrayList<String>();
                    String identifier;
                    String doctype;
                    String language;
                    ArrayList<String> subjects = new ArrayList<String>();
                    String description;

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        // TITLE
                        NodeList titlesList=eElement.getElementsByTagName("dc:title");
                        if(titlesList.getLength() > 0){
                            title=new String(titlesList.item(0).getTextContent());
                        }
                        else{
                            title=new String("No title provided");
                        }

                        // AUTHORS
                        NodeList authorsList = eElement.getElementsByTagName("dc:creator");
                        if(authorsList.getLength()>0) {
                            for (int j = 0; j < authorsList.getLength(); j++) {
                                authors.add(new String(authorsList.item(j).getTextContent()));
                            }
                        }
                        else{
                            authors.add("Unknown author");
                        }

                        // DATE
                        NodeList datesList=eElement.getElementsByTagName("dc:date");
                        if(datesList.getLength() > 0){
                            date=new String(datesList.item(0).getTextContent());
                        }
                        else{
                            date=new String("No date provided");
                        }

                        // IDENTIFIER
                        NodeList identifiersList=eElement.getElementsByTagName("dc:identifier");
                        if(identifiersList.getLength() > 0){
                            identifier=new String(identifiersList.item(0).getTextContent());
                        }
                        else{
                            identifier=new String("No identifier provided");
                        }

                        // TYPE
                        NodeList doctypesList=eElement.getElementsByTagName("dc:type");
                        if(doctypesList.getLength() > 0){
                            doctype=new String(doctypesList.item(0).getTextContent());
                        }
                        else {
                            doctype = new String("No information provided");
                        }

                        // SUBJECTS
                        NodeList subjectsList = eElement.getElementsByTagName("dc:subject");
                        if(subjectsList.getLength() > 0) {
                            for (int j = 0; j < subjectsList.getLength(); j++) {
                                subjects.add(new String(subjectsList.item(j).getTextContent()));
                            }
                        }
                        else{
                            subjects.add("Unspecified subject");
                        }

                        // DESCRIPTION
                        NodeList descriptionsList=eElement.getElementsByTagName("dc:description");
                        if(descriptionsList.getLength() > 0){
                            description=new String(descriptionsList.item(0).getTextContent());
                        }
                        else{
                            description=new String("No description provided");
                        }

                        // LANGUAGE
                        NodeList languagesList=eElement.getElementsByTagName("dc:language");
                        if(languagesList.getLength() > 0){
                            language=new String(languagesList.item(0).getTextContent());
                        }
                        else{
                            language=new String("No language provided");
                        }

                        results.add(new SearchResult(identifier,title,authors,date,doctype,language,subjects,description));
                    }
                }
                this.results=results;
            }
        }
        catch(Exception e){
                System.out.println("Erreur sur le parsing");
                e.printStackTrace();
        }
    }

    /**
     * This method is used to convert a String object to a Document object
     * @param xml The string to convert
     * @return Returns the correponding Document object
     * @throws Exception If an indetermined error occurs during the process
     */
    public static Document loadXMLFromString(String xml) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is=new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
}
