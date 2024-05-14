public class Core {

    protected int[] loanList;
    protected String[] bookList;
    protected String[]  userList;

    protected void init(){
        system.out.println("init activé");
    }

    protected void loadCustomer(){
        system.out.println("loadCustomer activé");
    }

    protected void loadBook(){
        system.out.println("loadBook activé");
    }

    protected void loadLoan(){
        system.out.println("loadLoan activé");
    }

    protected void getCustomers(){
        system.out.println("getCustomers activé");
    }

    protected void getBooks(){
        system.out.println("getBooks activé");
    }

    protected void getLoans(){
        system.out.println("getLoans activé");
    }

    protected void addCustomers(){
        system.out.println("addCustomers activé");
    }

    protected void alterCustomers(){
        system.out.println("alterCustomers activé");
    }

    protected void addLoan(){
        system.out.println("addLoan activé");
    }

    protected void alterLoan(){
        system.out.println("alterLoan activé");
    }

    protected void addBooks(){
        system.out.println("addBooks activé");
    }

    protected void removeBook(){
        system.out.println("removeBook activé");
    }

    protected void librarianAuthentication(){
        system.out.println("librarianAuthentication activé");
    }
}
