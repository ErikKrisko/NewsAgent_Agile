import java.util.LinkedList;

public class DB_Handler {
    private LinkedList<DB_Customer> customers = new LinkedList<>();
    private LinkedList<DB_Address> addresses = new LinkedList<>();
    private LinkedList<DB_Delivery> deliveries = new LinkedList<>();

    /** Blank constructor */
    public DB_Handler () { }

    public DB_Handler init() {
        DB_Customer.setHandler(this);
        DB_Address.setHandler(this);
        DB_Delivery.setHandler(this);
        return this;
    }

    //  CUSTOMER

    /** Adds a customer object to the list or throws and error if there is ID mishandling.
     * @param customer customer object to be added to the list
     * @throws DB_HandlerExceptionHandler
     */
    public void addACustomer(DB_Customer customer) throws DB_HandlerExceptionHandler {
        if (checkCustomer(customer.getCustomer_id())) {
            throw new DB_HandlerExceptionHandler("A customer of ID: " + customer.getCustomer_id() + " already exists.");
        }
        else {
            customers.add(customer);
        }
    }

    /** Gets a customer of specified ID. Either from existing list or creates a new object.
     * @param id id of a customer.
     * @return
     * @throws DB_HandlerExceptionHandler
     */
    public DB_Customer getCustomer(int id) throws DB_HandlerExceptionHandler {
        try {
            //  Check if given ID is in the list and return it
            if (checkCustomer(id)) {
                for (DB_Customer cus : customers) {
                    if (cus.getCustomer_id() == id) {
                        return cus;
                    }
                }
            } //    Else create a new customer and return it
            else {
                DB_Customer temp = new DB_Customer();
                temp.getByID(id);
                this.customers.add(temp);
                return temp;
            }
            //  If somehow this is reached throw an error
            throw new DB_HandlerExceptionHandler("Logical error with customer handling.");
        }
        catch (DB_CustomerExceptionHandler e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }
    }

    /** Private method for checking if given ID is in the list.
     * @param id to look for
     * @return true if found, false if not found
     */
    private boolean checkCustomer(int id) {
        for (DB_Customer cus : customers) {
            if (cus.getCustomer_id() == id ) {
                return true;
            }
        }
        return false;
    }


    //  ADDRESS
    /** Same layout as CUSTOMER */
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
            if (checkAddress(id)) {
                for (DB_Address add : addresses) {
                    if (add.getAddress_id() == id) {
                        return add;
                    }
                }
            } else {
                DB_Address temp = new DB_Address();
                temp.getByID(id);
                this.addresses.add(temp);
                return temp;
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

    // DELIVERY
    public void addDelivery(DB_Delivery delivery) throws DB_HandlerExceptionHandler {
        if (checkDelivery(delivery.getDelivery_id())) {
            throw new DB_HandlerExceptionHandler("A delivery of ID: " + delivery.getDelivery_id() + " already exists.");
        }
        else {
            deliveries.add(delivery);
        }
    }
    public DB_Delivery getDelivery(int id) throws DB_HandlerExceptionHandler {
        try {
            if (checkDelivery(id)) {
                for (DB_Delivery deli : deliveries) {
                    if (deli.getDelivery_id() == id) {
                        return deli;
                    }
                }
            } else {
                DB_Delivery temp = new DB_Delivery();
                temp.getByID(id);
                this.deliveries.add(temp);
                return temp;
            }
            throw new DB_HandlerExceptionHandler("Logical error with delivery handling.");
        }
        catch (DB_DeliveryExceptionHandler e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }
    }
    private boolean checkDelivery(int id) {
        for (DB_Delivery deli : deliveries) {
            if (deli.getDelivery_id() == id ) {
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