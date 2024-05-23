package org.openjfx.cybooks.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class Loan {
    private final int id;
    private final int customerId;
    private final String bookId;
    private final Date beginDate;
    private Date expirationDate;
    private boolean completed;
    private boolean expired;


    public String toStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBeginDate() {
        return toStringDate(beginDate);
    }

    public String getExpirationDate() {
        return toStringDate(expirationDate);
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean hasExpired() {
        return expired;
    }

    public Loan(int id, String bookId, int customerId, Date beginDate, Date expirationDate, boolean completed) {
        this.id = id;
        this.bookId = bookId;
        this.customerId = customerId;
        this.beginDate = beginDate;
        this.expirationDate = expirationDate;
        this.expired = expirationDate.before(new Date());
        this.completed = completed;
    }


    public void setDuration (Date date) {
        this.expirationDate = date;
        this.expired = expirationDate.before(new Date());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return id == loan.id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public String toString () {
        return "id : " + id
            + "\nID: " + bookId
            + "\ncustomer: " + customerId
            + "\nbegin date: " + beginDate
            + "\nexpiration date: " + expirationDate
            + "\ncompleted:" + completed
            + "\nhas expired: " + hasExpired();
    }
}
