import java.sql.*;
import java.util.LinkedList;

public class DAO {
    //  Connection properties
    private String url, user, pass, dbName;
    //  Connection objects
    private Connection con = null;
    //  List of loaded elements
    //  TO BE removed ASAP

    /** Blank constructor */
    public DAO() { }

    /** Creates DB_Handler with given connection
     * @param url should default to "jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC"
     * @param user should default to "root"
     * @param pass should default to "admin"
     */
    public DAO(String url, String user, String pass) throws DAOExceptionHandler {
        this.url = url;
        this.user = user;
        this.pass = pass;
        open();
    }

    /** Creates DB_Handler with given connection
     * @param url should default to "jdbc:mysql://localhost:3306/"
     * @param user should default to "root"
     * @param pass should default to "admin"
     * @param dbName should default to "newsagent"
     */
    public DAO(String url, String user, String pass, String dbName) throws DAOExceptionHandler {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.dbName = dbName;
        open();
    }

    //  ====================================================================================================
    //  CUSTOMER

    /** Returns customer by specified search criteria or all of no search terms provided.
     * @param search_list Keywords to search for or null to return all customers.
     * @throws DAOExceptionHandler if an SQL error occurred
     */
    public LinkedList<DB_Customer> getCustomers(Search_Customer[] search_list) throws DAOExceptionHandler {
        //  Create bew Linked list of customers
        LinkedList<DB_Customer> list = new LinkedList<>();
        try {
            //  If no search parameters are specified
            if (search_list == null || search_list.length <= 0) {
                //  Create new result set
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM customer");
                //  While there are results in the list create customer objects
                if ( rs.next()) {
                    do {
                        list.add( populateCustomer( rs));
                    } while ( rs.next());
                    return list;
                } else {
                    throw new DAOExceptionHandler("No Customers where found in the database.");
                }
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
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                LinkedList<DB_Customer> tempList = new LinkedList<>();
                while (rs.next()) {
                    DB_Customer temp = populateCustomer(rs);
                    tempList.add(temp);
                }
                return tempList;
            }
        }
        catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    /** Get customer by specified ID.
     * @param ID of a customer on the database.
     * @return DB_Customer object that matches given ID
     * @throws DAOExceptionHandler If there was no customer with given ID or an SQL error occured.
     */
    public DB_Customer getCustomer(int ID) throws DAOExceptionHandler {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM customer WHERE customer_id = " + ID);
            if ( rs.next()) {
                DB_Customer temp = populateCustomer(rs);
                return temp;
            } else {
                //  If somehow this is reached throw an error
                throw new DAOExceptionHandler("No customer with 'customer_id = " + ID + " not found.");
            }
        }
        catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    /** Issues update for customer. Creates if ID is = 0 or updates an existing entry.
     * @param customer object which to update / create.
     * @return int number of lines changed
     * @throws DAOExceptionHandler if an SQL error occurred or there was customer_id misshandling.
     */
    public int updateCustomer(DB_Customer customer) throws DAOExceptionHandler {
        try {
            //  If customer ID == null , create new otherwise update
            if (customer.getCustomer_id() == 0) {
                //  Construct INSERT statement and request generated keys
                PreparedStatement ps = con.prepareStatement("INSERT INTO customer VALUES(null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                //  Populate values for the preparedStatement
                ps.setString( Att_Customer.first_name.column - 1, customer.getFirst_name());
                ps.setString( Att_Customer.last_name.column - 1, customer.getLast_name());
                ps.setString( Att_Customer.phone_no.column - 1, customer.getPhone_no());
                //! Need to handle new addresses (Check if address_id = 0)
                ps.setLong( Att_Customer.address.column - 1, customer.getAddress().getAddress_id());
                //  Execute update and get generated ID
                int lines = ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                //  Get generated Key
                if ( keys.next())
                    customer.setCustomer_id( keys.getLong(1));
                return lines;
            } else {
                //  Get original customer data
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM customer WHERE customer_id = " + customer.getCustomer_id());
                //  Check if customer data exists
                if ( rs.next()) {
                    //  Base of the update
                    String update = "UPDATE customer SET ";
                    //  Update each value
                    update += Att_Customer.first_name.name + " = '" + customer.getFirst_name() + "', ";
                    update += Att_Customer.last_name.name + " = '" + customer.getLast_name() + "', ";
                    update += Att_Customer.phone_no.name + " = '" + customer.getPhone_no() + "', ";
                    update += Att_Customer.address.name + " = " + customer.getAddress().getAddress_id() + " ";
                    //  Specify update target
                    update += "WHERE " + Att_Customer.customer_id.name + " = " + customer.getCustomer_id();
                    //  Create statement and executeUpdate. (Cannot recall why prepared statement was used)
                    PreparedStatement ps = con.prepareStatement(update);
                    int lines = ps.executeUpdate();
                    return lines;
                } else {
                    throw new DAOExceptionHandler("There was customer_id mishandling.");
                }
            }
        }
        catch (SQLException e) {
            throw new DAOExceptionHandler( e.getMessage());
        }
    }

    /** Populates DB_Customer object with data from a result set and returns it.
     * @param rs result set containing customer information
     * @return new DB_Customer object
     * @throws DAOExceptionHandler if there was DB_Customer error or SQL error.
     */
    private DB_Customer populateCustomer(ResultSet rs) throws DAOExceptionHandler {
        try {
            //  Create customer with given result set
            return new DB_Customer(
                    rs.getInt( Att_Customer.customer_id.column),
                    rs.getString( Att_Customer.first_name.column),
                    rs.getString( Att_Customer.last_name.column),
                    rs.getString( Att_Customer.phone_no.column),
                    getAddress( rs.getInt(Att_Customer.address.column))
            );
        }
        catch (SQLException | DB_CustomerExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }


    //  ====================================================================================================
    //  ADDRESS

    /** Returns address with given address_id.
     * @param ID for address_id.
     * @return populated DB_Address object.
     * @throws DAOExceptionHandler
     */
    public DB_Address getAddress(int ID) throws DAOExceptionHandler {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM address WHERE address_id = " + ID);
            if ( rs.next()) {
                return new DB_Address(
                        rs.getInt( Att_Address.address_id.column),
                        rs.getString( Att_Address.full_address.column),
                        rs.getString( Att_Address.area_code.column),
                        rs.getString( Att_Address.eir_code.column)
                );
            } else {
                //  If somehow this is reached throw an error
                throw new DAOExceptionHandler("No address with 'address_id = " + ID + " found.");
            }
        }
        catch (SQLException | DB_AddressExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    //  ====================================================================================================
    // DELIVERY

    public DB_Delivery getDelivery(int ID) throws DAOExceptionHandler
    {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM delivery WHERE delivery_id = " + ID);
            if ( rs.next()) {
                DB_Delivery temp = populateDelivery(rs);
                return temp;
            } else {
                throw new DAOExceptionHandler("No delivery with 'delivery_id = " + ID + " found.");
            }
        }
        catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public int updateDelivery(DB_Delivery delivery) throws DAOExceptionHandler {
        try{
            if(delivery.getDelivery_id() == 0)
            {
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO customer VALUES(null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                pstmt.setDate(att_delivery.delivery_date.column - 1, delivery.getDelivery_date());
                pstmt.setBoolean(att_delivery.delivery_status.column - 1, delivery.isDelivery_status());
                pstmt.setLong( att_delivery.customer.column - 1, delivery.getCustomer().getCustomer_id());
                pstmt.setLong( att_delivery.invoice.column - 1, delivery.getInvoice().getInvoice_id());

                int lines = pstmt.executeUpdate();
                ResultSet keys = pstmt.getGeneratedKeys();
                if(keys.next()){
                    delivery.setDelivery_id(keys.getLong(1));
                }
                return lines;
            }
            else{
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM delivery WHERE delivery_id = " + delivery.getDelivery_id());
                if(rs.next()){

                    String update = "UPDATE delivery SET ";
                    update += att_delivery.delivery_date.columnName + " = '" + delivery.getDelivery_date() + "', ";
                    update += att_delivery.delivery_status.columnName + " = " + delivery.getDelivery_status() + ", ";
                    update += att_delivery.customer.columnName + " = " + delivery.getCustomer().getCustomer_id() + ", ";
                    update += att_delivery.invoice.columnName + " = " + delivery.getInvoice().getInvoice_id() + " ";
                    update += "WHERE " + att_delivery.delivery_id.columnName + " = " + delivery.getDelivery_id();

                    PreparedStatement pstmt = con.prepareStatement(update);
                    int lines = pstmt.executeUpdate();
                    return lines;
                }else{
                    throw new DAOExceptionHandler("There was delivery_id mishandling.");
                }
            }
        }
        catch(SQLException e)
        {
            throw new DAOExceptionHandler( e.getMessage());
        }
    }

    public int deleteDelivery(DB_Delivery delivery) throws DAOExceptionHandler {
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM delivery WHERE delivery_id = " + delivery.getDelivery_id()); //Check if resultset exists instead of deleting something that doesnt exist
            if(rs.next()){
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM delivery where delivery_id = " + delivery.getDelivery_id());
                PreparedStatement pstmt2 = con.prepareStatement("DELETE FROM prod_for_delivery where delivery_id = " + delivery.getDelivery_id());
                int lines =+ pstmt.executeUpdate();
                lines =+pstmt2.executeUpdate();
                return lines;
            }
            else
            {
                throw new DAOExceptionHandler("No delivery with 'delivery_id = " + delivery.getDelivery_id() + " found.");
            }
        }
        catch(SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    private DB_Delivery populateDelivery(ResultSet rs) throws DAOExceptionHandler {
        try {
            DB_Delivery temp = new DB_Delivery(rs);
            temp.setCustomer(getCustomer(rs.getInt(att_delivery.customer.column)));
            temp.setInvoice(getInvoice(rs.getInt(att_delivery.invoice.column)));
            return temp;
        }
        catch(SQLException | DB_DeliveryExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }




    //  ====================================================================================================
    // INVOICE
    /** Issues update for customer. Creates if ID is = 0 or updates an existing entry.
     * @param invoice object which to update / create.
     * @return int number of lines changed
     * @throws DAOExceptionHandler if an SQL error occurred or there was invoice_id misshandling.
     */
    public int updateInvoice(DB_Invoice invoice) throws DAOExceptionHandler {
        System.out.println();
        try {
            //  If customer ID == null , create new otherwise update
            if (invoice.getInvoice_id() == 0) {
                //  Construct INSERT statement and request generated keys
                PreparedStatement ps = con.prepareStatement("INSERT INTO invoice VALUES(null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                //  Populate values for the preparedStatement
                ps.setDate( Att_Invoice.issue_date.column - 1, invoice.getIssue_date());
                ps.setBoolean( Att_Invoice.invoice_status.column - 1, invoice.isInvoice_status());
                ps.setDouble( Att_Invoice.invoice_total.column - 1, invoice.getInvoice_total());
                //! Need to handle new addresses (Check if address_id = 0)
                ps.setLong( Att_Invoice.customer.column - 1, invoice.getCustomer().getCustomer_id());
                //  Execute update and get generated ID
                int lines = ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                //  Get generated Key
                if (keys.next())
                    invoice.setInvoice_id( keys.getLong(1));
                return lines;
            } else {
                //  Base of the update
                //  Update each value
                String update = "UPDATE invoice SET ";
                update += Att_Invoice.invoice_id.name + " = '" + invoice.getInvoice_id() + "', ";
                update += Att_Invoice.issue_date.name + " = '" + invoice.getIssue_date() + "', ";
                //! Removed quotes that would be present in string, used a getInvoice_status() method that returns 1 or 0
                update += Att_Invoice.invoice_status.name + " = " + invoice.getInvoice_status() + ", ";
                update += Att_Invoice.invoice_total.name + " = " + invoice.getInvoice_total() + " ";
                //  Specify update target
                update += "WHERE " + Att_Invoice.invoice_id.name + " = " + invoice.getCustomer().getCustomer_id();
                //  Create statement and executeUpdate. (Cannot recall why prepared statement was used)
                System.out.println(update);
                PreparedStatement ps = con.prepareStatement(update);
                int lines = ps.executeUpdate();
                return lines;
            }
        }
        catch (SQLException e) {
            throw new DAOExceptionHandler( e.getMessage());
        }
    }




    /** Get Invoice by specified ID.
     * @param ID of a customer on the database.
     * @return DB_Customer object that matches given ID
     * @throws DAOExceptionHandler If there was no customer with given ID or an SQL error occured.
     */
    public DB_Invoice getInvoice(int ID) throws DAOExceptionHandler {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM invoice WHERE invoice_id = " + ID);
            if ( rs.next()) {
                DB_Invoice temp = populateInvoice(rs);
                return temp;
            } else {
                //  If somehow this is reached throw an error
                throw new DAOExceptionHandler("No invoice with 'invoice_id = " + ID + " not found.");
            }
        }
        catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }
    private DB_Invoice populateInvoice(ResultSet rs) throws DAOExceptionHandler {
        try {
            //  Create customer with given result set
            DB_Invoice temp = new DB_Invoice(rs);
            //  Set customer address with given ID
            temp.setCustomer( getCustomer( rs.getInt(Att_Invoice.customer.column)));
            return temp;
        }
        catch (SQLException | DB_InvoiceExceptionHandler | DB_CustomerExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    /////Delete
    private int deleteInvoice(int ID) throws DAOExceptionHandler {
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM invoice WHERE invoice_id = " + ID); //Check if resultset exists instead of deleting something that doesnt exist
            if(rs.next()){
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM invoice where invoice_id = " + ID);
                int lines = pstmt.executeUpdate();
                return lines;
            }
            else
            {
                throw new DAOExceptionHandler("No invoice with 'invoice_id = " + ID + " found.");
            }
        }
        catch(SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }



    //  ====================================================================================================
    // SUBSCRIPTION


    /** Connection controls */
    /** Closes the current connection.
     */
    public void close() throws DAOExceptionHandler {
        try {
            con.close();
        }
        catch (Exception e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    /** Establishes database connection
     * @throws JDBCExceptionHandler
     */
    public void open() throws DAOExceptionHandler {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (dbName == null)
                con = DriverManager.getConnection( url, user, pass);
            else
                con = DriverManager.getConnection( url + dbName, user, pass);
        }
        catch(SQLException | ClassNotFoundException e) {
            throw new DAOExceptionHandler(e.getMessage());
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

class DAOExceptionHandler extends Exception {
    String message;

    public DAOExceptionHandler(String errMessage) { message = errMessage; }

    public String getMessage() { return message; }
}