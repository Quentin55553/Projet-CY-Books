package org.openjfx.cybooks.database;

public class CustomerFilter {

    private String lastName;
    private String firstName;
    private int id;
    private String email;
    private String tel;
    private String address;
    private int inf;
    private int sup;


    public CustomerFilter(String lastName, String firstName, int id, String email, String tel, String address, int inf, int sup) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
        this.email = email;
        this.tel = tel;
        this.inf = inf;
        this.sup = sup;
        this.address = address;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public int getInf() {
        return inf;
    }

    public int getSup() {
        return sup;
    }

    public String getAddress() {
        return address;
    }
}
