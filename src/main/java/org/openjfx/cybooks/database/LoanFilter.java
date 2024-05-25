package org.openjfx.cybooks.database;

public class LoanFilter {
    private Integer CustomerID;
    private String BookID;
    private boolean completed;
    private boolean expired;

    public LoanFilter(Integer customerID, String bookID, boolean completed, boolean expired) {
        CustomerID = customerID;
        BookID = bookID;
        this.completed = completed;
        this.expired = expired;
    }

    public Integer getCustomerID() {
        return CustomerID;
    }

    public String getBookID() {
        return BookID;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

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
