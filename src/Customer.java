public class Customer extends Core{

    protected String FirstName;
    protected String LastName;
    protected int id;

    @Override
    protected void getCustomers() {
        super.getCustomers();
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public int getId() {
        return id;
    }

}
