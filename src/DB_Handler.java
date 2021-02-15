import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class DB_Handler {
    //  JDBC Connection object
    private JDBC connection;
    //  List of loaded elements
    private LinkedList<DB_Customer> customers = new LinkedList<>();
    private LinkedList<DB_Address> addresses = new LinkedList<>();
    private LinkedList<DB_Delivery> deliveries = new LinkedList<>();

    /** Blank constructor */
    public DB_Handler () { }

    //  Temp print methods
    public void printAddress() {
        for (DB_Address add : addresses) {
            System.out.println(add.toString());
        }
    }
    public void printCustomers(){
        for (DB_Customer cus : customers) {
            System.out.println(cus.toString());
        }
    }

    /** Customer queries */

    public void getCustomers(Search_Customer search) throws DB_HandlerExceptionHandler {
        //  Clear two linked tables
        customers.clear();
        addresses.clear();
        try {
            if (search == null) {
                connection = new JDBC();
                connection.executeQuery("SELECT * FROM customer");
                ResultSet rs = connection.getRs();
                while (rs.next()) {
                    DB_Customer temp = new DB_Customer(rs);
                    temp.setAddress( getAddress( rs.getInt( Att_Customer.address.column)));
                    customers.add(temp);
                }
                connection.close();
            }
        }
        catch (SQLException | JDBCExceptionHandler e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }

    }

    public DB_Customer getCustomer(int ID) throws DB_HandlerExceptionHandler {
        try {
            //  Check if given ID is in the list and return it
            if ( checkCustomer(ID)) {
                for (DB_Customer cus : customers) {
                    if (cus.getCustomer_id() == ID)
                        return cus;
                }
            //  Else create a new Customer and return it
            } else {
                connection = new JDBC();
                connection.executeQuery("SELECT * FROM customer WHERE customer_id = " + ID);
                ResultSet rs = connection.getRs();
                if ( rs.next()) {
                    DB_Customer temp = new DB_Customer(rs);
                    temp.setAddress( getAddress( rs.getInt( Att_Customer.address.column)));
                    customers.add(temp);
                    return temp;
                }
                connection.close();
            }
            //  If somehow this is reached throw an error
            throw new DB_HandlerExceptionHandler("Logical error with customer handling.");
        }
        catch (JDBCExceptionHandler | SQLException e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }
    }

    /** Private method for checking if given ID is in the list.
     * @param ID to look for
     * @return true if found, false if not found
     */
    private boolean checkCustomer(int ID) {
        for (DB_Customer cus : customers) {
            if (cus.getCustomer_id() == ID ) {
                return true;
            }
        }
        return false;
    }



    //  ADDRESS
    /** Same layout as CUSTOMER */
    public DB_Address getAddress(int ID) throws DB_HandlerExceptionHandler {
        try {
            //  Check if given ID is in the list and return it
            if ( checkAddress(ID)) {
                for (DB_Address add : addresses) {
                    if (add.getAddress_id() == ID)
                        return add;
                }
                //  Else create a new Customer and return it
            } else {
                connection = new JDBC();
                connection.executeQuery("SELECT * FROM address WHERE address_id = " + ID);
                ResultSet rs = connection.getRs();
                if ( rs.next()) {
                    DB_Address temp = new DB_Address(rs);
                    addresses.add(temp);
                    return temp;
                }
                connection.close();
            }
            //  If somehow this is reached throw an error
            throw new DB_HandlerExceptionHandler("Logical error with customer handling.");
        }
        catch (JDBCExceptionHandler | SQLException e) {
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

    public DB_HandlerExceptionHandler(String errMessage) {
        message = errMessage;
    }

    public String getMessage() {
        return message;
    }
}