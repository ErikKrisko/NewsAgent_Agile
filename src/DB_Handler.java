import java.sql.*;
import java.util.LinkedList;

public class DB_Handler {
    //  Connection properties
    private String url, user, pass, dbName;
    //  Connection objects
    private Connection con = null;
    private Statement stmt = null;
    //  List of loaded elements
    //  TO BE removed ASAP
    private LinkedList<DB_Delivery> deliveries = new LinkedList<>();

    /** Blank constructor */
    public DB_Handler() { }

    /** Creates DB_Handler with given connection
     * @param url should default to "jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC"
     * @param user should default to "root"
     * @param pass should default to "admin"
     */
    public DB_Handler (String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    /** Creates DB_Handler with given connection
     * @param url should default to "jdbc:mysql://localhost:3306/"
     * @param user should default to "root"
     * @param pass should default to "admin"
     * @param dbName should default to "newsagent"
     */
    public DB_Handler (String url, String user, String pass, String dbName) {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.dbName = dbName;
    }

    //  ====================================================================================================
    //  CUSTOMER

    /** Returns customer by specified search criteria or all of no search terms provided.
     * @param search_list Keywords to search for or null to return all customers.
     * @throws DB_HandlerExceptionHandler if an SQL error occured
     */
    public LinkedList<DB_Customer> getCustomers(Search_Customer[] search_list) throws DB_HandlerExceptionHandler {
        //  Create bew Linked list of customers
        LinkedList<DB_Customer> list = new LinkedList<>();
        //  Open connection
        open();
        try {
            //  If no search parameters are specified
            if (search_list == null || search_list.length <= 0) {
                //  Create new result set
                ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
                //  While there are results in the list create customer objects
                while (rs.next()) {
                    list.add( populateCustomer( rs));
                }
                close();
                return list;
            } else {
                //  Construct a query
                String query = "SELECT * FROM customer WHERE ";
                for (Search_Customer search : search_list) {
                    if (search.isStrong())
                        query += search.getAttribute().name + " = '" + search.getTerm() + "', ";
                    else
                        query += search.getAttribute().name + " LIKE '" + search.getTerm() + "', ";
                }
                //  Cut the last two characters off ( ", " ) from the end
                query = query.substring(0, query.length() - 2 );
                System.out.println(query);
                ResultSet rs = stmt.executeQuery(query);
                LinkedList<DB_Customer> tempList = new LinkedList<>();
                while (rs.next()) {
                    DB_Customer temp = populateCustomer(rs);
                    tempList.add(temp);
                }
                close();
                return tempList;
            }
        }
        catch (SQLException e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }
    }

    /** Get customer by specified ID.
     * @param ID of a customer on the database.
     * @return DB_Customer object that matches given ID
     * @throws DB_HandlerExceptionHandler If there was no customer with given ID or an SQL error occured.
     */
    public DB_Customer getCustomer(int ID) throws DB_HandlerExceptionHandler {
        try {
            //  Open connection and get new result set
            open();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer WHERE customer_id = " + ID);
            if ( rs.next()) {
                DB_Customer temp = populateCustomer(rs);
                close();
                return temp;
            } else {
                close();
                //  If somehow this is reached throw an error
                throw new DB_HandlerExceptionHandler("No customer with 'customer_id = " + ID + " found.");
            }
        }
        catch (SQLException e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }
    }

    /** Issues update for customer. Creates if ID is = 0 or updates an existing entry.
     * @param customer object which to update / create.
     * @return int number of lines changed
     * @throws DB_HandlerExceptionHandler if an SQL error occurred or there was customer_id misshandling.
     */
    public int updateCustomer(DB_Customer customer) throws DB_HandlerExceptionHandler {
        try {
            //  If customer ID == null , create new otherwise update
            if (customer.getCustomer_id() == 0) {
                open();
                //  Construct INSERT statement
                PreparedStatement ps = con.prepareStatement("INSERT INTO customer VALUES(null, ?, ?, ?, ?)");
                //  Populate values for the preparedStatement
                ps.setString( Att_Customer.first_name.column - 1, customer.getFirst_name());
                ps.setString( Att_Customer.last_name.column - 1, customer.getLast_name());
                ps.setString( Att_Customer.phone_no.column - 1, customer.getPhone_no());
                //! Need to handle new addresses (Check if address_id = 0)
                ps.setInt( Att_Customer.address.column - 1, customer.getAddress().getAddress_id());
                //  Execute update and return lines changed
                int lines = ps.executeUpdate();
                close();
                return lines;
            } else {
                //  Open connection
                open();
                //  Get original customer data
                ResultSet rs = stmt.executeQuery("SELECT * FROM customer WHERE customer_id = " + customer.getCustomer_id());
                //  Check if customer data exists
                if ( rs.next()) {
                    //  Create comparable object
                    DB_Customer original = populateCustomer(rs);
                    //  Set changed state to false
                    boolean changed = false;
                    //  Check each attribute
                    for (Att_Customer attribute : Att_Customer.values()) {
                        //  If attributes do not match change=true
                        if (! original.get( attribute).equals( customer.get( attribute))) {
                            System.out.println(attribute.name);
                            changed = true;
                        }
                    }
                    //  If changes detected
                    if (changed) {
                        //  Base of the update
                        String update = "UPDATE customer SET ";
                        //  Update each value
                        update += Att_Customer.first_name.name + " = '" + customer.getFirst_name() + "', ";
                        update += Att_Customer.last_name.name + " = '" + customer.getLast_name() + "', ";
                        update += Att_Customer.phone_no.name + " = '" + customer.getPhone_no() + "', ";
                        update += Att_Customer.address.name + " = " + customer.getAddress().getAddress_id() + " ";
                        //  Specify update target
                        update += "WHERE " + Att_Customer.customer_id.name + " = " + customer.getCustomer_id();
                        //  Create statement and executeUpdate.
                        PreparedStatement ps = con.prepareStatement(update);
                        int lines = ps.executeUpdate();
                        close();
                        return lines;
                    } else {
                        close();
                        return 0;
                    }
                } else {
                    close();
                    throw new DB_HandlerExceptionHandler("There was customer_id mishandling.");
                }
            }
        }
        catch (SQLException | DB_CustomerExceptionHandler e) {
            throw new DB_HandlerExceptionHandler( e.getMessage());
        }
    }

    /** Populates DB_Customer object with data from a result set and returns it.
     * @param rs result set containing customer information
     * @return new DB_Customer object
     * @throws DB_HandlerExceptionHandler if there was DB_Customer error or SQL error.
     */
    private DB_Customer populateCustomer(ResultSet rs) throws DB_HandlerExceptionHandler {
        try {
            //  Create customer with given result set
            DB_Customer temp = new DB_Customer(rs);
            //  Set customer address with given ID
            temp.setAddress( getAddress( rs.getInt(Att_Customer.address.column)));
            return temp;
        }
        catch (SQLException | DB_CustomerExceptionHandler e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }
    }


    //  ====================================================================================================
    //  ADDRESS

    /** Returns address with given address_id.
     * @param ID for address_id.
     * @return populated DB_Address object.
     * @throws DB_HandlerExceptionHandler
     */
    public DB_Address getAddress(int ID) throws DB_HandlerExceptionHandler {

        try {
            open();
            ResultSet rs = stmt.executeQuery("SELECT * FROM address WHERE address_id = " + ID);
            if ( rs.next()) {
                DB_Address temp = new DB_Address(rs);
                close();
                return temp;
            }
            else {
                close();
                //  If somehow this is reached throw an error
                throw new DB_HandlerExceptionHandler("No address with 'address_id = " + ID + " found.");
            }
        }
        catch (SQLException e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }
    }

    //  ====================================================================================================
    // DELIVERY

    public void addDelivery(DB_Delivery delivery) throws DB_HandlerExceptionHandler {
        if (checkDelivery(delivery.getDelivery_id())) {
            throw new DB_HandlerExceptionHandler("A delivery of ID: " + delivery.getDelivery_id() + " already exists.");
        }
        else {
            deliveries.add(delivery);
        }
    }/*
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
    }*/
    private boolean checkDelivery(int id) {
        for (DB_Delivery deli : deliveries) {
            if (deli.getDelivery_id() == id ) {
                return true;
            }
        }
        return false;
    }


    /** Connection controls */
    /** Closes the current connection.
     */
    private void close() throws DB_HandlerExceptionHandler {
        try {
            con.close();
        }
        catch (Exception e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }
    }

    /** Establishes database connection
     * @throws JDBCExceptionHandler
     */
    private void open() throws DB_HandlerExceptionHandler {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (dbName == null)
                con = DriverManager.getConnection( url, user, pass);
            else
                con = DriverManager.getConnection( url + dbName, user, pass);
            stmt = con.createStatement();
        }
        catch(SQLException | ClassNotFoundException e) {
            throw new DB_HandlerExceptionHandler(e.getMessage());
        }
    }

    //  AUTO GENERATED getters and setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }
    public String getDbName() { return dbName; }
    public void setDbName(String dbName) { this.dbName = dbName; }
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