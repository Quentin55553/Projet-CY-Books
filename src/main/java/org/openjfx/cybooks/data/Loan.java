package org.openjfx.cybooks.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 * Class used to represent a book loan by a customer in the database
 */
public class Loan {
    /**
     * The ID of the loan (unique)
     */
    private final int id;
    /**
     * The customer's ID (unique)
     */
    private final int customerId;
    /**
     * The book's identifier (unique)
     */
    private final String bookId;
    /**
     * The beginning date of the loan
     */
    private final Date beginDate;
    /**
     * The expiration date of the loan
     */
    private Date expirationDate;
    /**
     * Boolean indicating if the customer has returned the book
     */
    private boolean completed;
    /**
     * Boolean indicating if the loan is past its expiration date
     */
    private boolean expired;


    /**
     * Constructor for a Loan object
     * @param id The loan ID
     * @param bookId The book's identifier (unique)
     * @param customerId The customer's ID (unique)
     * @param beginDate The beginning date of the loan
     * @param expirationDate The expiration date of the loan
     * @param completed Status of the loan
     */
    public Loan(int id, String bookId, int customerId, Date beginDate, Date expirationDate, boolean completed) {
        this.id = id;
        this.bookId = bookId;
        this.customerId = customerId;
        this.beginDate = beginDate;
        this.expirationDate = expirationDate;
        this.expired = expirationDate.before(new Date());
        this.completed = completed;
    }

    /**
     * Getter for the loan's ID attribute
     * @return The loan's ID (int)
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the book identifier attribute
     * @return The book's identifier (String)
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * Getter for the start date attribute
     * @return The beginning date as a string (String)
     */
    public String getBeginDate() {
        return toStringDate(beginDate);
    }

    /**
     * Getter for the customer's ID attribute
     * @return The customer's ID (int)
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Getter for the expiration date attribute
     * @return The expiration date as a string (String)
     */
    public String getExpirationDate() {
        return toStringDate(expirationDate);
    }

    /**
     * Getter for the expired attribute
     * @return expired (boolean)
     */
    public boolean hasExpired() {
        return expired;
    }

    /**
     * Getter for the completed attribute
     * @return completed (boolean)
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * This method sets the expiration date of a Loan object using a Date object and updates expired as needed
     * @param date The expiration date of the loan (Date)
     */
    public void setDuration (Date date) {
        this.expirationDate = date;
        this.expired = expirationDate.before(new Date());
    }

    /**
     * Setter for the completed attribute
     * @param completed The new value of the attribute (boolean)
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * This method transforms a Date object into a String
     * @param date The Date object to transform
     * @return The date as a String
     */
    public String toStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    /**
     * Equals method for a Loan object. Tests if the loan's ID is equal to the other's
     * @param o An object
     * @return true if o is equal to the Loan object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return id == loan.id;
    }


    /**
     * Hash code method for a Loan object
     * @return The hash of the loan's ID (int)
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    /**
     * ToString method for a Loan object
     * @return All the loan's information as a String
     */
    public String toString () {
        return "id : " + id
            + "\nbook id: " + bookId
            + "\ncustomer: " + customerId
            + "\nbegin date: " + beginDate
            + "\nexpiration date: " + expirationDate
            + "\ncompleted:" + completed
            + "\nhas expired: " + hasExpired();
    }
}
