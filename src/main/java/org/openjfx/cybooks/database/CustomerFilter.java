package org.openjfx.cybooks.database;

/**
 * Class used as filter for customer research
 */
public class CustomerFilter {


    /**
     * The customer's last name
     */
    private String lastName;

    /**
     * The customer's first name
     */
    private String firstName;

    /**
     * The customer's id in the database
     */
    private Integer id;

    /**
     * The customer's email
     */
    private String email;

    /**
     * The customer's phone number
     */
    private String tel;

    /**
     * The customer's address
     */
    private String address;

    /**
     * Number of loans lesser than
     */
    private Integer inf;

    /**
     * Number of loans greater than
     */
    private Integer sup;

    /**
     * Constructor for the Customer filter object
     * @param lastName The customer's last name
     * @param firstName The customer's first name
     * @param id The customer's id in the database
     * @param email The customer's email
     * @param tel The customer's phone number
     * @param address The customer's address
     * @param inf Number of loans lesser than
     * @param sup Number of loans greater than
     */
    public CustomerFilter(String lastName, String firstName, Integer id, String email, String tel, String address, Integer inf, Integer sup) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
        this.email = email;
        this.tel = tel;
        this.inf = inf;
        this.sup = sup;
        this.address = address;
    }

    /**
     * Returns the last name filter
     * @return The last name filter
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the first name filter
     * @return The first name filter
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the id filter
     * @return The id filter
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns the email filter
     * @return The email filter
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the phone number filter
     * @return The phone number filter
     */
    public String getTel() {
        return tel;
    }

    /**
     * Returns the inf filter
     * @return The inf filter
     */
    public Integer getInf() {
        return inf;
    }

    /**
     * Returns the sup filter
     * @return The sup filter
     */
    public Integer getSup() {
        return sup;
    }

    /**
     * Returns the address filter
     * @return The address filter
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for the last name filter
     * @param lastName The last name filter
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Setter for the first name filter
     * @param firstName The first name filter
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter for the id filter
     * @param id The id filter
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Setter for the email filter
     * @param email The email filter
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter for the phone number filter
     * @param tel The phone number filter
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * Setter for the address filter
     * @param address The address filter
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Setter for the inf filter
     * @param inf The inf filter
     */
    public void setInf(Integer inf) {
        this.inf = inf;
    }

    /**
     * Setter for the sup filter
     * @param sup The sup filter
     */
    public void setSup(Integer sup) {
        this.sup = sup;
    }

    /**
     * Returns a string representation of the object
     * @return A string representation of the object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CustomerFilter{");

        if (lastName != null && !lastName.isEmpty()) {
            sb.append("lastName='").append(lastName).append('\'');
        } else {
            sb.append("lastName=unspecified");
        }

        if (firstName != null && !firstName.isEmpty()) {
            sb.append(", firstName='").append(firstName).append('\'');
        } else {
            sb.append(", firstName=unspecified");
        }

        if (id != null) {
            sb.append(", id=").append(id);
        } else {
            sb.append(", id=unspecified");
        }

        if (email != null && !email.isEmpty()) {
            sb.append(", email='").append(email).append('\'');
        } else {
            sb.append(", email=unspecified");
        }

        if (tel != null && !tel.isEmpty()) {
            sb.append(", tel='").append(tel).append('\'');
        } else {
            sb.append(", tel=unspecified");
        }

        if (address != null && !address.isEmpty()) {
            sb.append(", address='").append(address).append('\'');
        } else {
            sb.append(", address=unspecified");
        }

        if (inf != null) {
            sb.append(", inf=").append(inf);
        } else {
            sb.append(", inf=unspecified");
        }

        if (sup != null) {
            sb.append(", sup=").append(sup);
        } else {
            sb.append(", sup=unspecified");
        }

        sb.append('}');
        return sb.toString();
    }
}
