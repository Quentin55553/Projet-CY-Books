package org.openjfx.cybooks.API;

import java.util.ArrayList;

/**
 * Class used to represent an entry in the BNF's database
 */
public class SearchResult {
    /**
     * Unique identifier (generally is a web link to the document's webpage)
     */
    private String identifier;
    /**
     * Title of the document
     */
    private String title;
    /**
     * Authors of the document
     */
    private ArrayList<String> authors;
    /**
     * Original release date of the document
     */
    private String date;
    /**
     * Publisher of the document
     */
    private String publisher;
    /**
     * Language of the document
     */
    private String language;
    /**
     * Subjects of the document
     */
    private ArrayList<String> subjects;
    /**
     * Description of the document
     */
    private String description;
    /**
     * Link to an image of the document
     */
    private String imageLink;

    /**
     * Constructor for a SearchResult object
     * @param id String. Identifier of the document
     * @param title String. Title of the document
     * @param authors ArrayList of String. Authors of the document
     * @param date String. Date of the document
     * @param publisher String. Publisher of the document
     * @param language String. Language of the document
     * @param subjects ArrayList of String. Subjects of the document
     * @param description String. Description of the document
     */
    public SearchResult(String id, String title, ArrayList<String> authors, String date, String publisher, String language, ArrayList<String> subjects, String description, String imageLink){
        this.identifier=id;
        this.title=title;
        this.authors=authors;
        this.date=date;
        this.publisher=publisher;
        this.language=language;
        this.subjects=subjects;
        this.description=description;
        this.imageLink=imageLink;
    }

    /**
     * Getter for the identifier attribute
     * @return The document's identifier (String)
     */
    public String getIdentifier(){
        return this.identifier;
    }

    /**
     * Getter for the title attribute
     * @return The document's title (String)
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the date attribute
     * @return The document's release date (String)
     */
    public String getDate() {
        return date;
    }

    /**
     * Getter for the authors attribute
     * @return The document's authors (ArrayList<String>)
     */
    public ArrayList<String> getAuthors() {
        return authors;
    }

    /**
     * Getter for the subjects attribute
     * @return The document's subjects (ArrayList<String>)
     */
    public ArrayList<String> getSubjects() {
        return subjects;
    }

    /**
     * Getter for the image link attribute
     * @return The document's image link (String)
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * Getter for the description attribute
     * @return The document's description (String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the publisher attribute
     * @return The document's publisher (String)
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Getter for the language attribute
     * @return The document's language (String)
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Converts a SearchResult object to a String
     * @return The String representing the object
     */
    @Override
    public String toString(){
        String str=new String();
        str+="\nSearch result :\n";
        str+="Title : "+this.title+"\n";
        str+="Authors : "+this.authors+"\n";
        str+="Date : "+this.date+"\n";
        str+="Identifier : "+this.identifier+"\n";
        str+="Publisher : "+this.publisher+"\n";
        str+="Description : "+this.description+"\n";
        str+="Subjects : "+this.subjects+"\n";
        str+="Language : "+this.language+"\n";
        str+="Image : "+this.imageLink+"\n";
        return str;
    }
}
