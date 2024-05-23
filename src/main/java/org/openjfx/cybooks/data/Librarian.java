package org.openjfx.cybooks.data;

/**
 * Class used to represent a librarian in the database
 */
public class Librarian {
    /**
     * The librarian's ID (unique)
     */
    private final int id;

    /**
     * The librarian's first name
     */
    private final String firstName;

    /**
     * The librarian's last name
     */
    private final String lastName;

    /**
     * Constructor for a Librarian object
     * @param id The librarian's ID (unique)
     * @param firstName The librarian's first name
     * @param lastName The librarian's last name
     */
    public Librarian(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Getter for the ID attribute
     * @return The librarian's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the first name attribute
     * @return The librarian's first name (String)
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for the last name attribute
     * @return The librarian's last name (String)
     */
    public String getLastName() {
        return lastName;
    }
}
