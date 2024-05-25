package org.openjfx.cybooks.database;

/**
 * Class used as a filter for loan research
 */
public class LoanFilter {

    /**
     * The customer's id
     */
    private Integer CustomerID;

    /**
     * The book's id
     */
    private String BookID;

    /**
     * Whether the book has been returned or not
     */
    private boolean completed;

    /**
     * Whether the loan has expired or not
     */
    private boolean expired;

    /**
     * Constructor for a LoanFilter object
     * @param customerID The customer's id
     * @param bookID The book's id
     * @param completed Whether the book has been returned or not
     * @param expired Whether the loan has expired or not
     */
    public LoanFilter(Integer customerID, String bookID, boolean completed, boolean expired) {
        CustomerID = customerID;
        BookID = bookID;
        this.completed = completed;
        this.expired = expired;
    }

    /**
     * Returns the customer's id
     * @return The customer's id
     */
    public Integer getCustomerID() {
        return CustomerID;
    }

    /**
     * Returns the book's id
     * @return The book's id
     */
    public String getBookID() {
        return BookID;
    }

    /**
     * Returns a boolean whether the book has been returned or not
     * @return A boolean whether the book has been returned or not
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Returns a boolean whether the loan has expired or not
     * @return A boolean whether the loan has expired or not
     */
    public boolean isExpired() {
        return expired;
    }

    /**
     * Setter for the customer's id
     * @param customerID The customer's id
     */
    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }

    /**
     * Setter for the book's id
     * @param bookID The book's id
     */
    public void setBookID(String bookID) {
        BookID = bookID;
    }

    /**
     * Setter for completed
     * @param completed A boolean whether the book has been returned or not
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Setter for expired
     * @param expired A boolean whether the loan has expired or not
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    /**
     * Returns a string representation of the object
     * @return A string representation of the object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LoanFilter{");

        if (CustomerID != null) {
            sb.append("CustomerID='").append(CustomerID).append('\'');
        } else {
            sb.append("CustomerID=unspecified");
        }

        if (BookID != null && !BookID.isEmpty()) {
            sb.append(", BookID='").append(BookID).append('\'');
        } else {
            sb.append(", BookID=unspecified");
        }
        sb.append(", completed=").append(completed);
        sb.append(", expired=").append(expired);
        sb.append('}');
        return sb.toString();
    }
}
