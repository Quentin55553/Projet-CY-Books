package org.openjfx.cybooks.API;
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
        this.numberOfResults=0;
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
     * Main languages to use :
     *     ara = Arabe
     *     chi = Chinois
     *     eng = Anglais
     *     fre = Français
     *     ger = Allemand
     *     grc = Grec
     *     ita = Italien
     *     jpn = Japonais
     *     lat = Latin
     *     spa = Espagnol
     *  Do not use accentuated or special characters in the parameters
     */
    public void generateQueryStandard(String title, String author, String date, String identifier, String publisher, String language, String subject) throws QueryParameterException{
        // The query is first initialized with the beginning of the URL
        this.query= "https://gallica.bnf.fr/SRU?operation=searchRetrieve&version=1.2&query=";
        // Variable used to know if a clause was written previously, and in that case we need to add an 'and' to the query
        boolean andNeeded=false;
        try {
            // Formatting all the input strings
            String normalizedTitle = urlFormatting(title);
            String normalizedAuthor = urlFormatting(author);
            String normalizedDate = urlFormatting(date);
            String normalizedIdentifier = urlFormatting(identifier);
            String normalizedPublisher = urlFormatting(publisher);
            String normalizedLanguage = urlFormatting(language);
            String normalizedSubject = urlFormatting(subject);

            // For each field (except first and last, we check if an 'and' is needed and then write the clause corresponding to the input string
            if(!title.equals("")){
                this.query+="dc.title%20any%20"+normalizedTitle;
                andNeeded=true;
            }
            if(!author.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                this.query+="dc.creator%20all%20"+normalizedAuthor;
                andNeeded=true;
            }
            if(!date.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                this.query+="dc.date%20any%20"+normalizedDate;
                andNeeded=true;
            }
            if(!identifier.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                this.query+="dc.identifier%20any%20"+normalizedIdentifier;
                andNeeded=true;
            }
            if(!publisher.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                this.query+="dc.publisher%20any%20"+normalizedPublisher;
                andNeeded=true;
            }
            if(!language.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                this.query+="dc.language%20any%20"+normalizedLanguage;
                andNeeded=true;
            }
            if(!subject.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                this.query+="dc.subject%20any%20"+normalizedSubject;
            }
            // This optional parameter allows us to increase the number of entries given by the API
            this.query+="&maximumRecords=30";
        }
        // Can occur when a special character is used in an input string
        catch(QueryParameterException e){
            // Reset the attributes
            this.numberOfResults=0;
            this.results=new ArrayList<SearchResult>();
            throw e;
        }
    }

    /**
     * This method generates a CQL query corresponding to the given parameters. The query attribute is updated accordingly
     * This version is for searching documents published within two years
     * @param title String. A title to search for
     * @param author String. An author to search for
     * @param startYear String. The starting year (included) of the time interval
     * @param endYear String. The ending year (included) of the time interval
     * @param identifier String. An identifier to search for
     * @param publisher String. A publisher of documents to search for
     * @param language String. A language to search for
     * @param subject String. A subject to search for
     */
    public void generateQueryTimeInterval(String title, String author, String startYear, String endYear, String identifier, String publisher, String language, String subject) throws QueryParameterException{
        // Same as the previous method, except for the date field
        this.query= "https://gallica.bnf.fr/SRU?operation=searchRetrieve&version=1.2&query=";
        boolean andNeeded=false;
        try {
            String normalizedTitle = urlFormatting(title);
            String normalizedAuthor = urlFormatting(author);
            String normalizedStartYear = checkYear(startYear);
            String normalizedEndYear = checkYear(endYear);
            String normalizedIdentifier = urlFormatting(identifier);
            String normalizedPublisher = urlFormatting(publisher);
            String normalizedLanguage = urlFormatting(language);
            String normalizedSubject = urlFormatting(subject);

            if(!title.equals("")){
                this.query+="dc.title%20any%20"+normalizedTitle;
                andNeeded=true;
            }
            if(!author.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                this.query+="dc.creator%20any%20"+normalizedAuthor;
                andNeeded=true;
            }
            if(!identifier.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                this.query+="dc.identifier%20any%20"+normalizedIdentifier;
                andNeeded=true;
            }
            if(!publisher.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                this.query+="dc.publisher%20any%20"+normalizedPublisher;
                andNeeded=true;
            }
            if(!language.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                this.query+="dc.language%20any%20"+normalizedLanguage;
                andNeeded=true;
            }
            if(!subject.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                this.query+="dc.subject%20any%20"+normalizedSubject;
            }
            if(!startYear.equals("") && !endYear.equals("")){
                if(andNeeded){
                    this.query+="%20and%20";
                }
                // Is equivalent to : dc.date >= startYear and dc.date <= endYear
                this.query+="dc.date%20%3E%3D%20%22"+normalizedStartYear+"%22%20and%20dc.date%20%3D%3C%20%22"+normalizedEndYear+"%22";
            }
            // This optional parameter allows us to increase the number of entries given by the API
            this.query+="&maximumRecords=30";
        }
        catch(QueryParameterException e){
            // Reset the attributes
            this.numberOfResults=0;
            this.results=new ArrayList<SearchResult>();
            throw e;
        }
    }

    /**
     * This method sends a request to Gallica using the query attribute and puts results in the corresponding attribute
     * @return Returns the number of results found for the search
     * @throws APIErrorException If a communication error occurs between Gallica and the program or if there is an error during parsing
     */
    public int exec() throws APIErrorException{
        try{
            // Reset the attributes
            this.numberOfResults=0;
            this.results=new ArrayList<SearchResult>();

            // New URL is built using the query attribute
            URL url = new URL(this.query);

            // Connection to the API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Response code
            int responseCode= connection.getResponseCode();

            // Anything other than 200 indicates an error
            if(responseCode!=200){
                throw new APIErrorException("There was an error during communication with the API. Error code : "+responseCode,responseCode);
            }
            else {
                // Reading the content of the webpage (an xml file)
                StringBuilder informationString = new StringBuilder();
                Scanner scanner= new Scanner(url.openStream());
                while(scanner.hasNext()){
                    informationString.append(scanner.nextLine()+"\n");
                }
                scanner.close();

                // Parsing the content of the xml string
                this.numberOfResults=0;
                this.parseResults(informationString);
                return this.numberOfResults;
            }
        }
        // In case one of the predefined methods goes wrong
        catch(Exception e){
            throw new APIErrorException("There was an error during parsing of the API results",501);
        }
    }

    /**
     * This method parses the content of the XML file sent by Gallica following the search request and puts the content in the results attribute
     * @param xml A StringBuilder object representing the XML file sent by Gallica
     * @throws APIErrorException If an error occurs during parsing
     */
    public void parseResults(StringBuilder xml) throws APIErrorException {
        try {

            // xml is the content sent by Gallica
            // It is converted to a Document object so that the XML parsers can be used

            // StringBuilder to String
            String xmlString = xml.toString();

            // String to Document
            Document doc = loadXMLFromString(xmlString);


            // Document to ArrayList<SearchResult>
            // The contents are then put in the APIHandler object attributes
            doc.getDocumentElement().normalize();

            // NUMBER OF RESULTS (BEFORE TYPE TEXT FILTERING)
            // We need to know if matching entries have been found in the database
            NodeList numberOfResults = doc.getElementsByTagName("srw:numberOfRecords");
            Node number = numberOfResults.item(0);
            Element nor = (Element) number;


            // RESULTS
            // To parse the XML string, we use a Document object which can give a list containing all elements that have a given tag (this is the Node Lists that are used below)

            // Sometimes and for random keywords, the API throws some sort of error and the output is different, in that case and to not interrupt flow of the app, we say that no results have been found
            if(nor==null){
                this.numberOfResults=0;
                return;
            }
            // Checking if results have been found by the API
            // If no results, then this method ends
            if (Integer.parseInt(nor.getTextContent()) != 0) {
                ArrayList<SearchResult> results = new ArrayList<SearchResult>();
                // A document's information is contained in the element tagged oai_dc:dc
                NodeList nListDocInfo = doc.getElementsByTagName("oai_dc:dc");
                // Except for the image links that are in srw:extraRecordData
                NodeList nListImageLinks = doc.getElementsByTagName("srw:extraRecordData");

                // For each entry, we get information from all the sub-elements and transfer it in a SearchResult object
                for (int temp = 0; temp < nListDocInfo.getLength(); temp++) {
                    // Current document
                    Node nNode = nListDocInfo.item(temp);

                    // All the needed information
                    String title;
                    String date;
                    String identifier;
                    String publisher;
                    String language;
                    String type;
                    String description;
                    String imageLink = "[N/A]";
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

                        // Texts are added to the results attribute
                        // The same sequence is used for each field : create a new NodeList containing the sub-element of oai_dc:dc, check if its length is not 0
                        if(isText) {
                            // TITLE
                            NodeList titlesList = eElement.getElementsByTagName("dc:title");
                            if (titlesList.getLength() > 0) {
                                title = titlesList.item(0).getTextContent();
                            } else {
                                title = "[N/A]";
                            }

                            // AUTHORS
                            NodeList authorsList = eElement.getElementsByTagName("dc:creator");
                            if (authorsList.getLength() > 0) {
                                for (int j = 0; j < authorsList.getLength(); j++) {
                                    authors.add(authorsList.item(j).getTextContent());
                                }
                            } else {
                                authors.add("N/A");
                            }

                            // DATE
                            NodeList datesList = eElement.getElementsByTagName("dc:date");
                            if (datesList.getLength() > 0) {
                                date = datesList.item(0).getTextContent();
                            } else {
                                date = "[N/A]";
                            }

                            // IDENTIFIER
                            NodeList identifiersList = eElement.getElementsByTagName("dc:identifier");
                            if (identifiersList.getLength() > 0) {
                                identifier = identifiersList.item(0).getTextContent();
                            } else {
                                identifier = "[N/A]";
                            }

                            // TYPE
                            NodeList doctypesList = eElement.getElementsByTagName("dc:publisher");
                            if (doctypesList.getLength() > 0) {
                                publisher = doctypesList.item(0).getTextContent();
                            } else {
                                publisher = "[N/A]";
                            }

                            // SUBJECTS
                            NodeList subjectsList = eElement.getElementsByTagName("dc:subject");
                            if (subjectsList.getLength() > 0) {
                                for (int j = 0; j < subjectsList.getLength(); j++) {
                                    subjects.add(subjectsList.item(j).getTextContent());
                                }
                            } else {
                                subjects.add("N/A");
                            }

                            // DESCRIPTION
                            NodeList descriptionsList = eElement.getElementsByTagName("dc:description");
                            if (descriptionsList.getLength() > 0) {
                                description = descriptionsList.item(0).getTextContent();
                            } else {
                                description = "[N/A]";
                            }

                            // LANGUAGE
                            NodeList languagesList = eElement.getElementsByTagName("dc:language");
                            if (languagesList.getLength() > 0) {
                                language = languagesList.item(0).getTextContent();
                            } else {
                                language = "[N/A]";
                            }

                            // IMAGE LINK
                            // For the image there are more steps as it is not in oai_dc:dc, but they are similar to above
                            nNode = nListImageLinks.item(temp);
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                eElement = (Element) nNode;
                                NodeList thumbnailsList = eElement.getElementsByTagName("thumbnail");
                                if (languagesList.getLength() > 0) {
                                    imageLink = thumbnailsList.item(0).getTextContent();
                                } else {
                                    imageLink = "[N/A]";
                                }
                            }

                            // For each entry found, the number of results is incremented
                            this.numberOfResults+=1;

                            // The entry is added to results
                            results.add(new SearchResult(identifier, title, authors, date, publisher, language, subjects, description, imageLink));
                        }
                    }
                }
                this.results=results;
            }
        }
        catch(Exception e){
            // Reset the attributes
            this.numberOfResults=0;
            this.results=new ArrayList<SearchResult>();
            throw new APIErrorException("There was an error during parsing of the API results",501);
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

    /**
     * This method is used to format a string for it to be used in an url (for example, a whitespace needs to be replaced by %20 to not cause errors).
     * @param str The string to be formatted
     * @return A string that is identical to the original, but all its special characters have been replaced by their % code
     * @throws QueryParameterException If an invalid symbol is present in the string.
     */
    public static String urlFormatting(String str) throws QueryParameterException{
        // The goal is to build a new string where every character of the original string is replaced by a url-friendly symbol
        String normalizedString = "";
        char currentChar;
        for(int i = 0; i<str.length();i++){
            currentChar=str.charAt(i);
            if(('A' <= currentChar && currentChar <= 'Z') || ('a' <= currentChar && currentChar <= 'z')){
                normalizedString+=currentChar;
            }
            else if('0' <= currentChar && currentChar <= '9'){
                normalizedString+=currentChar;
            }
            else if(currentChar=='.' || currentChar=='-'){
                normalizedString+=currentChar;
            }
            else if(currentChar==' '){
                normalizedString+="%20";
            }
            else if(currentChar=='('){
                normalizedString+="%28";
            }
            else if(currentChar==')'){
                normalizedString+="%29";
            }
            else if(currentChar=='['){
                normalizedString+="%5B";
            }
            else if(currentChar==']'){
                normalizedString+="%5D";
            }
            else if(currentChar=='{'){
                normalizedString+="%7B";
            }
            else if(currentChar=='}'){
                normalizedString+="%7D";
            }
            else if(currentChar==':'){
                normalizedString+="%3A";
            }
            else if(currentChar=='/'){
                normalizedString+="%2F";
            }
            else if(currentChar=='"'){
                normalizedString+="%22";
            }
            else if(currentChar=='é' || currentChar=='É'){
                normalizedString+="%C3%A9";
            }
            else if(currentChar=='è' || currentChar=='È'){
                normalizedString+="%C3%A8";
            }
            else if(currentChar=='ê' || currentChar=='Ê'){
                normalizedString+="%C3%AA";
            }
            else if(currentChar=='ù' || currentChar=='Ù'){
                normalizedString+="%C3%B9";
            }
            else if(currentChar=='à' || currentChar=='À'){
                normalizedString+="%C3%A0";
            }
            else if(currentChar=='â' || currentChar=='Â'){
                normalizedString+="%C3%A2";
            }
            else if(currentChar=='ô' || currentChar=='Ô'){
                normalizedString+="%C3%B4";
            }
            else if(currentChar=='ç' || currentChar=='Ç'){
                normalizedString+="%C3%A7";
            }
            else if(currentChar=='ï' || currentChar=='Ï'){
                normalizedString+="%C3%AF";
            }
            else if(currentChar=='î' || currentChar=='Î'){
                normalizedString+="%C3%AE";
            }
            else if(currentChar=='&'){
                normalizedString+="%26";
            }
            else if(currentChar==','){
                normalizedString+="%2C";
            }
            else if(currentChar=='%'){
                normalizedString+="%25";
            }
            else if(currentChar=='#'){
                normalizedString+="%23";
            }
            else if(currentChar=='€'){
                normalizedString+="%E2%82%AC";
            }
            else if(currentChar=='$'){
                normalizedString+="%24";
            }
            else if(currentChar=='!'){
                normalizedString+="%21";
            }
            else if(currentChar=='?'){
                normalizedString+="%3F";
            }
            else if(currentChar=='='){
                normalizedString+="%3D";
            }
            else{
                // If the character is not in the above cases, this means the string contains forbidden symbols
                throw new QueryParameterException("Invalid value for generateQuery() parameter : "+str);
            }
        }
        return normalizedString;
    }

    /**
     * This method ensures that a given string is containing exactly four numbers.
     * @param stringYear The string to check
     * @return The same string.
     * @throws QueryParameterException If the string is not containing exactly four numbers.
     */
    public static String checkYear(String stringYear) throws QueryParameterException{
        // Length check
        if(stringYear.length() != 4){
            throw new QueryParameterException("Invalid value for generateQuery() parameter : "+stringYear);
        }
        // Checking each of the 4 characters
        for(int i =0; i<4; i++){
            if(stringYear.charAt(i) > '9' || stringYear.charAt(i) < '0'){
                throw new QueryParameterException("Invalid value for generateQuery() parameter : "+stringYear);
            }
        }
        return stringYear;
    }
}
