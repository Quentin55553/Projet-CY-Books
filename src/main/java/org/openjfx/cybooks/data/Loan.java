package org.openjfx.cybooks.data;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.time.LocalDate;

public class Loan {
    private final int id;
    private final int customerId;
    private final String bookISBN;
    private final Date beginDate;

    private int duration;
    private boolean completed;

    private boolean expired;

    public boolean hasExpired() {
        return expired;
    }

    public Loan(int id, String ISBN, int customerId, Date beginDate, int duration, boolean completed) {
        this.id = id;
        this.bookISBN = ISBN;
        this.customerId = customerId;
        this.beginDate = beginDate;
        this.duration = duration;
        this.expired = false;
        this.completed = completed;
    }
    public void setDuration (int duration) {
        this.duration = duration;
        // modify expired
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
                + "\nISBN: " + bookISBN
                + "\ncustomer: " + customerId
                + "\nbegin date: " + beginDate
                + "\nduration: " + duration
                + "\ncompleted:" + completed
                + "\nhas expired: " + hasExpired();
    }
}
