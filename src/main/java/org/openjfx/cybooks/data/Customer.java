package org.openjfx.cybooks.data;

import java.util.Objects;


public class Customer {
    private final int id;
    private String firstName;
    private String lastName;

    private String tel;

    private String email;

    private String address;


    public Customer(int id, String firstName, String lastName, String tel, String email, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tel = tel;
        this.email = email;
        this.address = address;
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


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                + "\naddress: " + address;
    }
}
