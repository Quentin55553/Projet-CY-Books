package org.openjfx.cybooks.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class used to represent a book in the database
 */
public class Book {
    /**
     * Unique identifier (generally is a web link to the document's webpage)
     */
    private final String id;
    private int total;
    private int stock;


    /**
     * Title of the document
     */
    private String title;
    /**
     * Authors of the document
     */
    private List<String> authors;
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
     * Description of the document
     */
    private String description;
    /**
     * Subjects of the document
     */
    private List<String> subjects;
    /**
     * Link to an image of the document
     */
    private String imageLink;

    /**
     * Constructor for a Book object
     * @param id The book's identifier
     * @param total The book total value
     * @param stock The book stock value
     */
    public Book (String id, int total, int stock) {
        this.id = id;
        this.stock = stock;
        this.total = total;
        this.title = "";
        this.description = "";
        this.subjects = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.imageLink = "";
        this.publisher = "";
        this.date = "";
        this.language = "";
    }

    /**
     * Constructor for a Book object
     * @param id The book's identifier
     * @param title The book's title
     * @param authors The book's authors
     * @param date The book's release date
     * @param publisher The book's publisher
     * @param language The book's language
     * @param description The book's description
     * @param subjects The book's subjects
     * @param imageLink The book's image link
     */
    public Book(String id, String title, List<String> authors, String date, String publisher, String language, String description, List<String> subjects, String imageLink) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.date = date;
        this.publisher = publisher;
        this.language = language;
        this.description = description;
        this.subjects = subjects;
        this.imageLink = imageLink;
        this.stock = 0;
        this.total = 0;
    }

    /**
     * Getter for the identifier attribute
     * @return The book's identifier (String)
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the title attribute
     * @return The title of the book (String)
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the authors attribute
     * @return The document's authors (List<String>)
     */
    public List<String> getAuthors() {
        return authors;
    }


    /**
     * Getter for the total attribute
     * @return The book's total (int)
     */
    public int getTotal() {
        return total;
    }


    /**
     * Getter for the total attribute
     * @return The book's stock (int)
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for the stock attribute
     * @param stock The book's stock (int)
     */
    public void setStock (int stock) {
        this.stock = stock;
    }


    /**
     * Setter for the title attribute
     * @param title The title of the book (String)
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Setter for the authors attribute
     * @param authors The document's authors (List<String>)
     */
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    /**
     * Setter for the total attribute
     * @param total The book's total (int)
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Getter for the date attribute
     * @return The document's release date (String)
     */
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter for the publisher attribute
     * @return The document's publisher (String)
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Setter for the publisher attribute
     * @param publisher The document's publisher (String)
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Getter for the language attribute
     * @return The document's language (String)
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setter for the language attribute
     * @param language The document's language (String)
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Getter for the description attribute
     * @return The document's description (String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description attribute
     * @param description The document's description (String)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the subjects attribute
     * @return The document's subjects (ArrayList<String>)
     */
    public List<String> getSubjects() {
        return subjects;
    }

    /**
     * Setter for the subjects attribute
     * @param subjects The document's subjects (ArrayList<String>)
     */
    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    /**
     * Getter for the image link attribute
     * @return The document's image link (String)
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * Setter for the image link attribute
     * @param imageLink  The document's image link (String)
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    /**
     * Equals method for a Book object. Tests if the book's identifier is equal to the other's
     * @param o An object
     * @return true if o is equal to the Book object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id;
    }

    /**
     * Hash code method for a Book object
     * @return The hash of the book's identifier (int)
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    /**
     * ToString method for a Book object
     * @return All the book's information as a String
     */
    public String toString () {
        return "id: " + id
            + "\ntitle: " + title
            + "\nauthors: " + authors
            + "\npublisher: " + publisher
            + "\ndescription: " + description
            + "\ndate: " + date
            + "\nsubjects: " + subjects
            + "\nlanguage: " + language
            + "\nimage link: " + imageLink
            + "\nstock: " + stock
            + "\ntotal: " + total;
    }
}
