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

    //  CUSTOMER
    public void addACustomer(DB_Customer customer) throws DB_HandlerExceptionHandler {
        if (checkCustomer(customer.getCustomer_id())) {
            throw new DB_HandlerExceptionHandler("A customer of ID: " + customer.getCustomer_id() + " already exists.");
        }
        else {
            customers.add(customer);
        }
    }
    public DB_Customer getCustomer(int id) throws DB_HandlerExceptionHandler {
        try {
            if (!checkCustomer(id)) {
                DB_Customer temp = new DB_Customer();
                temp.getByID(id);
                this.customers.add(temp);
                return temp;
            } else {
                for (DB_Customer cus : customers) {
                    if (cus.getCustomer_id() == id) {
                        return cus;
                    }
                }
            }
            throw new DB_HandlerExceptionHandler("Logical error with customer handling.");
        }
        catch (DB_CustomerExceptionHandler e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }
    }
    private boolean checkCustomer(int id) {
        for (DB_Customer cus : customers) {
            if (cus.getCustomer_id() == id ) {
                return true;
            }
        }
        return false;
    }


    //  ADDRESS
    public void addAddress(DB_Address address) throws DB_HandlerExceptionHandler {
        if (checkAddress(address.getAddress_id())) {
            throw new DB_HandlerExceptionHandler("An address of ID: " + address.getAddress_id() + " already exists.");
        }
        else {
            addresses.add(address);
        }
    }
    public DB_Address getAddress(int id) throws DB_HandlerExceptionHandler {
        try {
            if (!checkAddress(id)) {
                DB_Address temp = new DB_Address();
                temp.getByID(id);
                this.addresses.add(temp);
                return temp;
            } else {
                for (DB_Address add : addresses) {
                    if (add.getAddress_id() == id) {
                        return add;
                    }
                }
            }
            throw new DB_HandlerExceptionHandler("Logical error with address handling.");
        }
        catch (DB_AddressExceptionHandler e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }
    }
    private boolean checkAddress(int id) {
        for (DB_Address add : addresses) {
            if (add.getAddress_id() == id ) {
                return true;
            }
        }
        return false;
    }
}

class DB_HandlerExceptionHandler extends Exception {
    String message;

    public DB_HandlerExceptionHandler(String errMessage){
        message = errMessage;
    }

    public String getMessage() {
        return message;
    }
}