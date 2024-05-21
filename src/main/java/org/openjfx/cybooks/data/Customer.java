package org.openjfx.cybooks.data;

import org.openjfx.cybooks.database.DBHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Customer {
    private final int id;
    private String firstName;
    private String lastName;
    private String tel;
    private String email;
    private String address;
    private int nbLoans;


    public Customer(int id, String firstName, String lastName, String tel, String email, String address, int nbLoans) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.nbLoans = nbLoans;
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }


    public int getNbLoans() {
        return nbLoans;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setNbLoans(int nbLoans) {
        this.nbLoans = nbLoans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return id == customer.id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "id: " + id
            + "\nfirst name: " + firstName
            + "\nlast name: " + lastName
            + "\ntel: " + tel
            + "\nemail: " + email
            + "\naddress: " + address
            + "\nnumber of loans: " + nbLoans;
    }
}
