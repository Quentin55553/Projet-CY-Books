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
 * To get results of a search :
 * 1. Create an APIHandler object
 * 2. Call generateQuery()
 * 3. Call exec()
 * 4. Results can be found in the results attribute
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
        this.query= "https://gallica.bnf.fr/SRU?operation=searchRetrieve&version=1.2&query=";
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
     * @param publisher String. A publisher of documents to search for
     * @param language String. A language to search for
     * @param subject String. A subject to search for
     */
    public void generateQuery(String title, String author, String date, String identifier, String publisher, String language, String subject){
        this.query= "https://gallica.bnf.fr/SRU?operation=searchRetrieve&version=1.2&query=";
        boolean andNeeded=false;

        if(!title.equals("")){
            this.query+="dc.title%20any%20"+title;
            andNeeded=true;
        }
        if(!author.equals("")){
            if(andNeeded){
                this.query+="%20and%20";
            }
            this.query+="dc.creator%20any%20"+author;
            andNeeded=true;
        }
        if(!date.equals("")){
            if(andNeeded){
                this.query+="%20and%20";
            }
            this.query+="dc.date%20any%20"+date;
            andNeeded=true;
        }
        if(!identifier.equals("")){
            if(andNeeded){
                this.query+="%20and%20";
            }
            this.query+="dc.title%20any%20"+identifier;
            andNeeded=true;
        }
        if(!publisher.equals("")){
            if(andNeeded){
                this.query+="%20and%20";
            }
            this.query+="dc.publisher%20any%20"+publisher;
            andNeeded=true;
        }
        if(!language.equals("")){
            if(andNeeded){
                this.query+="%20and%20";
            }
            this.query+="dc.language%20any%20"+language;
            andNeeded=true;
        }
        if(!subject.equals("")){
            if(andNeeded){
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
                this.numberOfResults=0;
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

            // NUMBER OF RESULTS (BEFORE TYPE TEXT FILTERING)
            NodeList numberOfResults = doc.getElementsByTagName("srw:numberOfRecords");
            Node number = numberOfResults.item(0);
            Element nor = (Element) number;


            // RESULTS
            if (Integer.parseInt(nor.getTextContent()) != 0) {
                ArrayList<SearchResult> results = new ArrayList<SearchResult>();
                NodeList nListDocInfo = doc.getElementsByTagName("oai_dc:dc");
                NodeList nListImageLinks = doc.getElementsByTagName("srw:extraRecordData");

                for (int temp = 0; temp < nListDocInfo.getLength(); temp++) {
                    Node nNode = nListDocInfo.item(temp);
                    String title;
                    String date;
                    String identifier;
                    String publisher;
                    String language;
                    String type;
                    String description;
                    String imageLink = "No information provided";
                    ArrayList<String> subjects = new ArrayList<String>();
                    ArrayList<String> authors = new ArrayList<String>();


                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        // TYPE CHECK (TEXT NEEDED)
                        boolean isText;
                        NodeList typesList=eElement.getElementsByTagName("dc:type");
                        if(typesList.getLength() > 0){
                            type= typesList.item(0).getTextContent();
                            if(type.equals("text") || type.equals("texte")){
                                isText=true;
                            }
                            else{
                                isText=false;
                            }
                        }
                        else{
                            isText=false;
                        }

                        // Texts are added to results
                        if(isText) {
                            // TITLE
                            NodeList titlesList = eElement.getElementsByTagName("dc:title");
                            if (titlesList.getLength() > 0) {
                                title = titlesList.item(0).getTextContent();
                            } else {
                                title = "No title provided";
                            }

                            // AUTHORS
                            NodeList authorsList = eElement.getElementsByTagName("dc:creator");
                            if (authorsList.getLength() > 0) {
                                for (int j = 0; j < authorsList.getLength(); j++) {
                                    authors.add(authorsList.item(j).getTextContent());
                                }
                            } else {
                                authors.add("Unknown author");
                            }

                            // DATE
                            NodeList datesList = eElement.getElementsByTagName("dc:date");
                            if (datesList.getLength() > 0) {
                                date = datesList.item(0).getTextContent();
                            } else {
                                date = "No date provided";
                            }

                            // IDENTIFIER
                            NodeList identifiersList = eElement.getElementsByTagName("dc:identifier");
                            if (identifiersList.getLength() > 0) {
                                identifier = identifiersList.item(0).getTextContent();
                            } else {
                                identifier = "No identifier provided";
                            }

                            // TYPE
                            NodeList doctypesList = eElement.getElementsByTagName("dc:publisher");
                            if (doctypesList.getLength() > 0) {
                                publisher = doctypesList.item(0).getTextContent();
                            } else {
                                publisher = "No information provided";
                            }

                            // SUBJECTS
                            NodeList subjectsList = eElement.getElementsByTagName("dc:subject");
                            if (subjectsList.getLength() > 0) {
                                for (int j = 0; j < subjectsList.getLength(); j++) {
                                    subjects.add(subjectsList.item(j).getTextContent());
                                }
                            } else {
                                subjects.add("Unspecified subject");
                            }

                            // DESCRIPTION
                            NodeList descriptionsList = eElement.getElementsByTagName("dc:description");
                            if (descriptionsList.getLength() > 0) {
                                description = descriptionsList.item(0).getTextContent();
                            } else {
                                description = "No description provided";
                            }

                            // LANGUAGE
                            NodeList languagesList = eElement.getElementsByTagName("dc:language");
                            if (languagesList.getLength() > 0) {
                                language = languagesList.item(0).getTextContent();
                            } else {
                                language = "No language provided";
                            }

                            // IMAGE LINK
                            nNode = nListImageLinks.item(temp);
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                eElement = (Element) nNode;
                                NodeList thumbnailsList = eElement.getElementsByTagName("thumbnail");
                                if (languagesList.getLength() > 0) {
                                    imageLink = thumbnailsList.item(0).getTextContent();
                                } else {
                                    imageLink = "No information provided";
                                }
                            }

                            this.numberOfResults+=1;
                            results.add(new SearchResult(identifier, title, authors, date, publisher, language, subjects, description, imageLink));
                        }
                    }
                }
                this.results=results;
            }
        }
        catch(Exception e){
                System.out.println("Parsing error");
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
