package org.openjfx.cybooks.data;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.time.LocalDate;

public class Loan {
    private final int id;
    private final int customerId;
    private final int bookISBN;
    private final Date beginDate;

    private Duration duration;

    private Boolean expired;

    public Boolean hasExpired() {
        return expired;
    }

    public Loan(int id, int ISBN, int customerId, Date beginDate, Duration duration) {
        this.id = id;
        this.bookISBN = ISBN;
        this.customerId = customerId;
        this.beginDate = beginDate;
        this.duration = duration;
        this.expired = false;
    }
    public void setDuration (Duration duration) {
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
                + "\n duration: " + duration
                + "\nhas expired: " + hasExpired();
    }
}
