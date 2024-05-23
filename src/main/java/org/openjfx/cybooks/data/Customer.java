package org.openjfx.cybooks.data;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class used to represent a customer in the database
 */
public class Customer {
    /**
     * The customer's ID (unique)
     */
    private final int id;
    /**
     * The customer's first name
     */
    private String firstName;
    /**
     * The customer's last name
     */
    private String lastName;
    /**
     * The customer's phone number
     */
    private String tel;
    /**
     * The customer's e-mail adress
     */
    private String email;
    /**
     * The customer's adress
     */
    private String address;
    /**
     * The customer's current loan count
     */
    private int loanCount;

    /**
     * Constructor for a Customer object
     * @param id The customer's ID
     * @param firstName The customer's first name
     * @param lastName The customer's last name
     * @param tel The customer's phone number
     * @param email The customer's e-mail adress
     * @param address The customer's adress
     * @param loanCount The customer's current loan count
     * @throws NoSuchElementException
     */
    public Customer(int id, String firstName, String lastName, String tel, String email, String address, int loanCount) throws NoSuchElementException {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.loanCount = loanCount;
    }

    /**
     * Getter for the ID attribute
     * @return The customer's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the first name attribute
     * @return The customer's first name (String)
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for the last name attribute
     * @return The customer's last name (String)
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the first name attribute
     * @param firstName The customer's first name (String)
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * Setter for the last name attribute
     * @param lastName  The customer's last name (String)
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for the phone number attribute
     * @return The customer's phone number (String)
     */
    public String getTel() {
        return tel;
    }

    /**
     * Setter for the phone number attribute
     * @param tel The customer's phone number (String)
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * Getter for the email attribute
     * @return The customer's email (String)
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the email attribute
     * @param email The customer's email (String)
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the adress attribute
     * @return The customer's adress (String)
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for the adress attribute
     * @param address The customer's adress (String)
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for the loan count attribute
     * @return The customer's loan count (int)
     */
    public int getLoanCount() {
        return loanCount;
    }


    /**
     * Equals method for a Customer object. Tests if the customer's ID is equal to the other's
     * @param o An object
     * @return true if o is equal to the Customer object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }


    /**
     * Hash code method for a Customer object
     * @return The hash of the customer's ID (int)
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    /**
     * ToString method for a Customer object
     * @return All the customer's information as a String
     */
    @Override
    public String toString() {
        return "id: " + id
            + "\nfirst name: " + firstName
            + "\nlast name: " + lastName
            + "\ntel: " + tel
            + "\nemail: " + email
            + "\naddress: " + address
            + "\nloan count: " + loanCount;
    }
}
