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
     * Type of the document
     */
    private String doctype;
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
     * Constructor for a SearchResult object
     * @param id String. Identifier of the document
     * @param title String. Title of the document
     * @param authors ArrayList of String. Authors of the document
     * @param date String. Date of the document
     * @param doctype String. Type of the document
     * @param language String. Language of the document
     * @param subjects ArrayList of String. Subjects of the document
     * @param description String. Description of the document
     */
    public SearchResult(String id, String title, ArrayList<String> authors, String date, String doctype, String language, ArrayList<String> subjects, String description){
        this.identifier=id;
        this.title=title;
        this.authors=authors;
        this.date=date;
        this.doctype=doctype;
        this.language=language;
        this.subjects=subjects;
        this.description=description;
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
        str+="Type : "+this.doctype+"\n";
        str+="Description : "+this.description+"\n";
        str+="Subjects : "+this.subjects+"\n";
        str+="Language : "+this.language+"\n";
        return str;
    }
}
