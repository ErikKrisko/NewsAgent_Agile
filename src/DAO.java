import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class DAO {
    //  Connection properties
    private String url, user, pass, dbName;
    //  Connection objects
    private Connection con = null;

    /** Blank constructor */
    public DAO() { }

    /** Creates DB_Handler with given connection
     * @param url should default to "jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC"
     * @param user should default to "root"
     * @param pass should default to "admin"
     * @throws DAOExceptionHandler if opening connection was unsuccessful.
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
     * @throws DAOExceptionHandler if opening connection was unsuccessful.
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
                    rs.close();
                    st.close();
                    return list;
                } else {
                    rs.close();
                    st.close();
                    throw new DAOExceptionHandler("No Customers where found in the database.");
                }
            } else {
                //  Construct a query
                String query = "SELECT * FROM customer WHERE ";
                //  Martina extensively advised against expanding this methodology to include any more search criteria such as address or publication and told us to do it in separate methods.
                for (Search_Customer search : search_list) {
                    if (search.isStrong())
                        query += search.getAttribute().name + " = '" + search.getTerm() + "'AND ";
                    else
                        query += search.getAttribute().name + " LIKE '" + search.getTerm() + "'AND ";
                }
                //  Cut the last four characters off ( "AND " ) from the end
                query = query.substring(0, query.length() - 4 );
                System.out.println(query);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                LinkedList<DB_Customer> tempList = new LinkedList<>();
                while (rs.next()) {
                    DB_Customer temp = populateCustomer(rs);
                    tempList.add(temp);
                }
                rs.close();
                st.close();
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
                rs.close();
                st.close();
                return temp;
            } else {
                rs.close();
                st.close();
                //  If somehow this is reached throw an error
                throw new DAOExceptionHandler("No customer with customer_id = " + ID + " not found.");
            }
        }
        catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    /** Issues update for customer. Creates if ID is = 0 or updates an existing entry.
     * @param customer object which to update / create.
     * @return int number of lines changed
     * @throws DAOExceptionHandler if an SQL error occurred or there was customer_id mishandling.
     */
    public int updateCustomer(DB_Customer customer) throws DAOExceptionHandler {
        try {
            //  If customer ID == 0 , create new otherwise update
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
                    customer.setCustomer_id(keys.getLong(1));
                keys.close();
                ps.close();
                return lines;
            } else {
                //  Get original customer data
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM customer WHERE " + Att_Customer.customer_id.name + " = " + customer.getCustomer_id());
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
                    ps.close();
                    rs.close();
                    st.close();
                    return lines;
                } else {
                    rs.close();
                    st.close();
                    throw new DAOExceptionHandler("There was customer_id mishandling.");
                }
            }
        }
        catch (SQLException | DB_CustomerExceptionHandler e) {
            throw new DAOExceptionHandler( e.getMessage());
        }
    }

    /** Deletes given customer object if it exists in the database.
     * @param customer to be deleted
     * @return # lines affected by the deletion.
     * @throws DAOExceptionHandler if the customer was not inserted or does not exist in the database.
     */
    public int deleteCustomer(DB_Customer customer) throws DAOExceptionHandler {
        if (customer.getCustomer_id() == 0) {
            throw new DAOExceptionHandler("Customer was not inserted into the database.");
        } else {
            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM customer WHERE " + Att_Customer.customer_id.name + " = " + customer.getCustomer_id());
                if ( rs.next()) {
                    int lines = st.executeUpdate("DELETE FROM customer WHERE " + Att_Customer.customer_id.name + " = " + customer.getCustomer_id() );
                    rs.close();
                    st.close();
                    return lines;
                } else {
                    rs.close();
                    st.close();
                    throw new DAOExceptionHandler("Cannot delete, customer with ID = '" + customer.getCustomer_id() + "', does not exist in the database.");
                }
            } catch (SQLException e) {
                throw new DAOExceptionHandler(e.getMessage());
            }
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
            DB_Customer customer = new DB_Customer(
                    rs.getInt( Att_Customer.customer_id.column),
                    rs.getString( Att_Customer.first_name.column),
                    rs.getString( Att_Customer.last_name.column),
                    rs.getString( Att_Customer.phone_no.column),
                    getAddress( rs.getInt(Att_Customer.address.column))
            );
            return customer;
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
     * @throws DAOExceptionHandler if no address found or an SQL error occurs
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

    /** Issues update for address. Creates if ID is = 0 or updates an existing entry.
     * @param address object which to update / create.
     * @return int number of lines changed.
     * @throws DAOExceptionHandler if an SQL error occurred or there was customer_id mishandling.
     */
    public int updateAddress(DB_Address address) throws DAOExceptionHandler{
        try {
            //  If address ID == 0 , create new otherwise update
            if (address.getAddress_id() == 0) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO address VALUES(null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString( Att_Address.full_address.column - 1, address.getFull_address());
                ps.setString( Att_Address.area_code.column - 1, address.getArea_code());
                ps.setString( Att_Address.eir_code.column - 1, address.getEir_code());
                int lines = ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                if ( keys.next())
                    address.setAddress_id( keys.getLong( 1));
                keys.close();
                ps.close();
                return lines;
            } else {    //  Else update existing address
                //  Query address
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery( "SELECT * FROM address WHERE " + Att_Address.address_id.name + " = " + address.getAddress_id());
                //  If queried address exists update
                if ( rs.next()) {
                    String update = "UPDATE address SET ";
                    update += Att_Address.full_address.name + " = '" + address.getFull_address() + "', ";
                    update += Att_Address.area_code.name + " = '" + address.getArea_code() + "', ";
                    update += Att_Address.eir_code.name + " = '" + address.getEir_code() + "' ";
                    //  Specify update target
                    update += "WHERE " + Att_Address.address_id.name + " = " + address.getAddress_id();
                    //  Create statement and execute
                    PreparedStatement ps = con.prepareStatement(update);
                    int lines = ps.executeUpdate();
                    ps.close();
                    rs.close();
                    st.close();
                    return lines;
                } else {
                    rs.close();
                    st.close();
                    throw new DAOExceptionHandler("There was address_id mishandling.");
                }
            }
        } catch (SQLException | DB_AddressExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    /** Deletes given address from the database if it exists.
     * @param address to delete
     * @return int of lines changed
     * @throws DAOExceptionHandler if the address was not inserted or does not exist in the database.
     */
    public int deleteAddress(DB_Address address) throws DAOExceptionHandler {
        if (address.getAddress_id() == 0) {
            throw new DAOExceptionHandler("Address was not inserted into the database.");
        } else {
            try {
                Statement st = con.createStatement();
                System.out.println("SELECT * FROM address WHERE " + Att_Address.address_id.name + " = " + address.getAddress_id());
                ResultSet rs = st.executeQuery("SELECT * FROM address WHERE " + Att_Address.address_id.name + " = " + address.getAddress_id());
                if ( rs.next()) {
                    int lines = st.executeUpdate( "DELETE FROM address WHERE " + Att_Address.address_id.name + " = " + address.getAddress_id());
                    rs.close();
                    st.close();
                    return lines;
                } else {
                    rs.close();
                    st.close();
                    throw new DAOExceptionHandler( "Cannot delete address with ID = '" + address.getAddress_id() + "', does not exist in the database.");
                }
            } catch (SQLException e) {
                throw new DAOExceptionHandler(e.getMessage());
            }
        }
    }


    //  ====================================================================================================
    // HOLIDAY

    /** Returns array list of holidays for given customer object
     * @param customer to have holidays assigned for
     * @return ArrayList of holiday objects
     * @throws DAOExceptionHandler if there was an error
     */
    public ArrayList<DB_Holiday> getHolidays(DB_Customer customer) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Holiday> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM holiday WHERE " + Att_Customer.customer_id.name + " = " + customer.getCustomer_id());
            if ( rs.next()) {
                do {
                    list.add(new DB_Holiday(
                            rs.getLong(Att_Holiday.holiday_id.column),
                            rs.getDate(Att_Holiday.start_date.column),
                            rs.getDate(Att_Holiday.end_date.column),
                            rs.getLong(Att_Holiday.customer.column)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                return null;
            }
        } catch (SQLException | DB_HolidayExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    /** Updates DB_Holiday object or creates a new one.
     * @param holiday to be updated / inserted
     * @return int of lines changed
     * @throws DAOExceptionHandler if an SQL error occurred or there was holiday_id mishandling.
     */
    public int updateHoliday(DB_Holiday holiday) throws DAOExceptionHandler {
        try {
            //  if holiday ID == 0, create new
            if (holiday.getHoliday_id() == 0) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO holiday VALUES(null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setDate( Att_Holiday.start_date.column - 1, holiday.getStart_date());
                ps.setDate( Att_Holiday.end_date.column - 1, holiday.getEnd_date());
                ps.setLong( Att_Holiday.customer.column - 1, holiday.getCustomer_id());
                int lines = ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                if ( keys.next())
                    holiday.setHoliday_id( keys.getLong(1));
                keys.close();
                ps.close();
                return lines;
            } else {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery( "SELECT * FROM holiday WHERE " + Att_Holiday.holiday_id.name + " = " + holiday.getHoliday_id());
                //  Check if holiday exists
                if ( rs.next()) {
                    //  Base update
                    String update = "UPDATE holiday SET ";
                    //  Concatenate strings
                    update += Att_Holiday.start_date.name + " = '" + holiday.getStart_date() + "', ";
                    update += Att_Holiday.end_date.name + " = '" + holiday.getEnd_date() + "', ";
                    update += Att_Holiday.customer.name + " = " + holiday.getCustomer_id();
                    //  Specify update target
                    update += "WHERE " + Att_Holiday.holiday_id.name + " = " + holiday.getHoliday_id();
                    //  Execute update
                    PreparedStatement ps = con.prepareStatement(update);
                    int lines = ps.executeUpdate();
                    ps.close();
                    rs.close();
                    st.close();
                    return lines;
                } else {
                    rs.close();
                    st.close();
                    throw new DAOExceptionHandler("There was address_id mishandling.");
                }
            }
        } catch (SQLException | DB_HolidayExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    /** Deletes given holiday from the database if it exists.
     * @param holiday to be deleted
     * @return int of lines changed
     * @throws DAOExceptionHandler if the holiday was not inserted or does not exist in the database.
     */
    public int deleteHoliday(DB_Holiday holiday) throws DAOExceptionHandler {
        if (holiday.getHoliday_id() == 0) {
            throw new DAOExceptionHandler("Holiday was not inserted into the database.");
        } else {
            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM holiday WHERE " + Att_Holiday.holiday_id.name + " = " + holiday.getHoliday_id());
                if (rs.next()) {
                    int lines = st.executeUpdate("DELETE FROM holiday WHERE " + Att_Holiday.holiday_id.name + " = " + holiday.getHoliday_id());
                    rs.close();
                    st.close();
                    return lines;
                } else {
                    rs.close();
                    st.close();
                    throw new DAOExceptionHandler("Cannot delete, holiday with ID = '" + holiday.getHoliday_id() + "', does not exist in the database.");
                }
            } catch (SQLException e) {
                throw new DAOExceptionHandler(e.getMessage());
            }
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
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO delivery VALUES(null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
        catch(SQLException | DB_DeliveryExceptionHandler e)
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
                int lines = pstmt.executeUpdate();
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

// =========================================================================================================
    // EMPLOYEE

    public DB_Employee getEmployee(int ID) throws DAOExceptionHandler{
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employee WHERE employee_id = " + ID);
            if(rs.next()){
                DB_Employee temp = populateEmployee(rs);
                return temp;
            }else{
                throw new DAOExceptionHandler("No employee with 'employee_id = " + ID + " found.");
            }
        } catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public int updateEmployee(DB_Employee employee) throws DAOExceptionHandler {
        try{
            if(employee.getEmployee_id() == 0)
            {
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO employee VALUES(null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(att_employee.first_name.column - 1, employee.getFirst_name());
                pstmt.setString(att_employee.last_name.column - 1, employee.getLast_name());

                int lines = pstmt.executeUpdate();
                ResultSet keys = pstmt.getGeneratedKeys();
                if(keys.next()){
                    employee.setEmployee_id(keys.getLong(1));
                }
                return lines;
            }
            else{
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM employee WHERE employee_id = " + employee.getEmployee_id());
                if(rs.next()){

                    String update = "UPDATE employee SET ";
                    update += att_employee.first_name.columnName + " = '" + employee.getFirst_name() + "', ";
                    update += att_employee.last_name.columnName + " = " + employee.getLast_name() + ", ";
                    update += "WHERE " + att_employee.employee_id.columnName + " = " + employee.getEmployee_id();

                    PreparedStatement pstmt = con.prepareStatement(update);
                    int lines = pstmt.executeUpdate();
                    return lines;
                }else{
                    throw new DAOExceptionHandler("There was employee_id mishandling.");
                }
            }
        }
        catch(SQLException | DB_EmployeeExceptionHandler e)
        {
            throw new DAOExceptionHandler( e.getMessage());
        }
    }

    public int deleteEmployee(DB_Employee employee) throws DAOExceptionHandler {
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employee WHERE employee_id = " + employee.getEmployee_id()); //Check if resultset exists instead of deleting something that doesnt exist
            if(rs.next()){
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM employee where employee_id = " + employee.getEmployee_id());
                int lines = pstmt.executeUpdate();
                return lines;
            }
            else
            {
                throw new DAOExceptionHandler("No employee with 'employee_id = " + employee.getEmployee_id() + " found.");
            }
        }
        catch(SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    private DB_Employee populateEmployee(ResultSet rs) throws DAOExceptionHandler {
        try {
            DB_Employee temp = new DB_Employee(
                    rs.getInt(att_employee.employee_id.column),
                    rs.getString(att_employee.first_name.column),
                    rs.getString(att_employee.last_name.column)
            );
            return temp;
        }
        catch(SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }


    //  ====================================================================================================
    //Publication needs to go in here

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

    //Delete Invoice

    public int deleteInvoice(DB_Invoice invoice) throws DAOExceptionHandler {
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM invoice WHERE invoice_id = " + invoice.getInvoice_id()); //Check if resultset exists instead of deleting something that doesnt exist
            if(rs.next()){
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM invoice where invoice_id = " + invoice.getInvoice_id());
                int lines =+ pstmt.executeUpdate();
                return lines;
            }
            else
            {
                throw new DAOExceptionHandler("No invoice with 'Invoice id = " + invoice.getInvoice_id() + " found.");
            }
        }
        catch(SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }



    //  ====================================================================================================
    // SUBSCRIPTION
    //GetSubscription
    public DB_Subscription getSubscription(int cus_id, int prod_id) throws DAOExceptionHandler {
        try {
            //cus_id comes up wrong
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM subscription WHERE customer_id = " + cus_id + " AND prod_id = " + prod_id);
            if(rs.next()) {
                DB_Subscription temp = populateSubscription(rs);
                return temp;
            } else {
                throw new DAOExceptionHandler("No subscription with customer_id = " + cus_id + " AND prod_id = " + prod_id + "found");
            }
        } catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public DB_Subscription getSubscriptionByCustomer(int cus_id) throws DAOExceptionHandler {
        try {
            //cus_id comes up wrong
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM subscription WHERE customer_id = " + cus_id);
            if(rs.next()) {
                DB_Subscription temp = populateSubscription(rs);
                return temp;
            } else {
                throw new DAOExceptionHandler("No subscription with customer_id = " + cus_id + "found");
            }
        } catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public DB_Subscription getSubscriptionByPublication( int publication_id) throws DAOExceptionHandler {
        try {
            //cus_id comes up wrong
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM subscription WHERE publication_id = " + publication_id);
            if(rs.next())
            {
                DB_Subscription temp = populateSubscription(rs);
                return temp;
            }
            else
            {
                throw new DAOExceptionHandler("No subscription with publication_id = " + publication_id + "found");
            }
        }
        catch (SQLException e)
        {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    //---------------------------------------------------
    //Update Subscription

    public int updateSubscription(DB_Subscription subscription) throws DAOExceptionHandler
    {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM subscription WHERE customer_id ="+ subscription.getCustomer().getCustomer_id() + " and prod_id = " + 1 /* subscription.getPublication().getProd_id()*/);
            if(rs.next())
            {
                String update = "UPDATE subscription SET count =" + subscription.getCount() + "WHERE customer_id ="+ subscription.getCustomer().getCustomer_id() + " and prod_id = " + 1; /* subscription.getPublication().getProd_id()*/
                PreparedStatement ps = con.prepareStatement(update);
                int lines = ps.executeUpdate();
                return lines;
            }
            else
            {
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO subscription VALUES(?,?,?)");
                pstmt.setLong(1, subscription.getCustomer().getCustomer_id());

                //This is for publication prod_id
                //pstmt.setLong(2, subscription.getPublication().getProd_id());

                pstmt.setInt(3, subscription.getCount());
                int lines = pstmt.executeUpdate();
                return lines;
            }
        }
        catch (SQLException e) {
            throw new DAOExceptionHandler( e.getMessage());
        }
    }





    //-----------------------------------------------------
    //Delete Subscription

    private int deleteSubscription(DB_Subscription subscription) throws DAOExceptionHandler
    {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM subscription WHERE customer_id ="+ subscription.getCustomer().getCustomer_id() + " and prod_id = " + 1 /* subscription.getPublication().getProd_id()*/);
            if (rs.next()) {
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM subscription WHERE customer_id ="+ subscription.getCustomer().getCustomer_id() + " and prod_id = " + 1 /* subscription.getPublication().getProd_id()*/ );
                int lines = pstmt.executeUpdate();
                return lines;
            } else {
                throw new DAOExceptionHandler("No subscription with customer_id ="+ subscription.getCustomer().getCustomer_id() + " and prod_id = " + 1 /* subscription.getPublication().getProd_id()*/ + "found");
            }
        }
        catch(SQLException e)
        {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }
    //------------------------------------------------------
    //Populate

    private DB_Subscription populateSubscription(ResultSet rs) throws DAOExceptionHandler
    {
        try {
            DB_Subscription temp = new DB_Subscription(rs);
            temp.setCustomer(getCustomer(rs.getInt(1)));
            return temp;
        }
        catch (SQLException | DB_SubscriptionExceptionHandler e)
        {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

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