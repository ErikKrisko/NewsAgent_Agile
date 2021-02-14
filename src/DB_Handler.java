import java.util.LinkedList;

public class DB_Handler {
    private LinkedList<DB_Customer> customers = new LinkedList<>();
    private LinkedList<DB_Address> addresses = new LinkedList<>();

    /** Blank constructor */
    public DB_Handler () { }

    public DB_Handler init() {
        DB_Customer.setHandler(this);
        DB_Address.setHandler(this);
        return this;
    }

    public void addCustomer(DB_Customer customer) {
        if (!customers.contains(customer))
            this.customers.add(customer);
    }
    public boolean checkCustomer(int id) {
        for (DB_Customer cus : customers) {
            if (cus.getCustomer_id() == id )
                return true;
        }
        return false;
    }
    public DB_Customer getCustomer(int id) {
        for (DB_Customer cus : customers) {
            if (cus.getCustomer_id() == id )
                return cus;
        }
        return null;
    }
}
