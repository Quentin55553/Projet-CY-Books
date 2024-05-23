package org.openjfx.cybooks.database;

public class CustomerFilter {

    private String lastName;
    private String firstName;
    private Integer id;
    private String email;
    private String tel;
    private String address;
    private Integer inf;
    private Integer sup;


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

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public Integer getInf() {
        return inf;
    }

    public Integer getSup() {
        return sup;
    }

    public String getAddress() {
        return address;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInf(Integer inf) {
        this.inf = inf;
    }

    public void setSup(Integer sup) {
        this.sup = sup;
    }

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
