import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

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
    public ArrayList<DB_Customer> getCustomers(Search_Customer[] search_list) throws DAOExceptionHandler {
        //  Create bew Linked list of customers
        ArrayList<DB_Customer> list = new ArrayList<>();
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
                        query += search.getAttribute().name + " LIKE '%" + search.getTerm() + "%'AND ";
                }
                //  Cut the last four characters off ( "AND " ) from the end
                query = query.substring(0, query.length() - 4 );
//                System.out.println(query);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                ArrayList<DB_Customer> tempList = new ArrayList<>();
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

    //  Could be made smaller by using customerIds found within delivery
    public int[] getAreasForDeliveries(ArrayList<DB_Delivery> deliveries) throws DAOExceptionHandler {
        try {
            if (deliveries != null && deliveries.size() > 0) {
                long[] delIDs = new long[0];
                for (DB_Delivery del : deliveries) {
                    boolean contains = false;
                    for (long i : delIDs) {
                        if (i == del.getDelivery_id()) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        delIDs = Arrays.copyOf(delIDs, delIDs.length + 1);
                        delIDs[delIDs.length - 1] = del.getDelivery_id();
                    }
                }
                String statement = "SELECT DISTINCT address.area_code FROM address, customer, delivery WHERE delivery_id IN (";
                //  Add numbers to the list
                for (long i : delIDs)
                    statement += i + ",";
                //  Cut the last comma and add closing parentheses
                statement = statement.substring(0, statement.length() - 1);
                statement += ") AND delivery.customer_id = customer.customer_id AND customer.address_id = address.address_id";
                //  Execute query
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(statement);
                int[] areaList = new int[0];
                if (rs.next()) {
                    do {
                        areaList = Arrays.copyOf(areaList, areaList.length + 1);
                        areaList[areaList.length - 1] = rs.getInt(1);
                    } while (rs.next());
                }
                return areaList;
            } else {
                throw new DAOExceptionHandler("No deliveries provided");
            }
        } catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public int getDeliveryArea(DB_Delivery delivery) throws DAOExceptionHandler {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT a.area_code FROM address AS a, customer AS c ,delivery AS d WHERE delivery_id = "+ delivery.getDelivery_id() +" AND d.customer_id = c.customer_id AND c.address_id = a.address_id;");
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new DAOExceptionHandler("No area could be found for delivery_id = "+delivery.getDelivery_id());
            }
        } catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public DB_Address getAddressByDelivery(DB_Delivery delivery) throws DAOExceptionHandler {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT a.address_id, a.full_address, a.area_code, a.eir_code FROM address AS a, customer AS c, delivery AS d WHERE delivery_id = "+ delivery.getDelivery_id() +" AND d.customer_id = c.customer_id AND c.address_id = a.address_id;");
            if (rs.next()) {
                return new DB_Address(
                        rs.getInt( Att_Address.address_id.column),
                        rs.getString( Att_Address.full_address.column),
                        rs.getInt( Att_Address.area_code.column),
                        rs.getString( Att_Address.eir_code.column)
                );
            } else {
                throw new DAOExceptionHandler("No address found for delivery_id = "+delivery.getDelivery_id());
            }
        } catch (SQLException | DB_AddressExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

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
                        rs.getInt( Att_Address.area_code.column),
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

    public ArrayList<DB_Address> getAddressesBySubscriptions(ArrayList<DB_Subscription> sub_list) throws DAOExceptionHandler {
        if (sub_list != null) {
            if (sub_list.size() > 0) {
                try {
                    //  Compile unique prod_ids to an array
                    long[] cus_ids = new long[0];
                    for (DB_Subscription sub : sub_list) {
                        boolean contains = false;
                        for (long i : cus_ids) {
                            if (i == sub.getCustomer_id()) {
                                contains = true;
                                break;
                            }
                        }
                        if (!contains) {
                            cus_ids = Arrays.copyOf(cus_ids, cus_ids.length + 1);
                            cus_ids[cus_ids.length - 1] = sub.getCustomer_id();
                        }
                    }
                    //  Query using ids
                    if (cus_ids.length > 0) {
                        ArrayList<DB_Address> addresses = new ArrayList<>();
                        String statement = "SELECT DISTINCT a.address_id, a.full_address, a.area_code, a.eir_code FROM address AS a, customer AS c WHERE c.customer_id IN (";
                        //  Add numbers to the list
                        for (long i : cus_ids)
                            statement += i + ",";
                        //  Cut the last comma and add closing parentheses
                        statement = statement.substring(0, statement.length() - 1);
                        statement += ") AND c.address_id = a.address_id";
                        //  Execute query
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(statement);
                        if (rs.next()) {
                            do {
                                addresses.add(new DB_Address(
                                        rs.getInt( Att_Address.address_id.column),
                                        rs.getString( Att_Address.full_address.column),
                                        rs.getInt( Att_Address.area_code.column),
                                        rs.getString( Att_Address.eir_code.column)
                                ));
                            } while ((rs.next()));
                            rs.close();
                            st.close();
                            return addresses;
                        } else {
                            rs.close();
                            st.close();
                            return null;
                        }
                    } else {
                        throw new DAOExceptionHandler("No product ids were found");
                    }
                } catch (SQLException | DB_AddressExceptionHandler e) {
                    throw new DAOExceptionHandler(e.getMessage());
                }
            } else {
                return null;
            }
        } else {
            throw new DAOExceptionHandler("A null list was provided.");
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
                ps.setInt( Att_Address.area_code.column - 1, address.getArea_code());
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
//                System.out.println("SELECT * FROM address WHERE " + Att_Address.address_id.name + " = " + address.getAddress_id());
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
     * @param customer_id to have holidays for
     * @return ArrayList of holiday objects
     * @throws DAOExceptionHandler if there was an error
     */
    public ArrayList<DB_Holiday> getHolidays(long customer_id) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Holiday> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM holiday WHERE " + Att_Customer.customer_id.name + " = " + customer_id);
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

    public DB_Holiday getHoliday(long holiday_id) throws  DAOExceptionHandler {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM holiday WHERE " + Att_Holiday.holiday_id.name + " = " + holiday_id);
            if ( rs.next()) {
                return new DB_Holiday(
                        rs.getLong(Att_Holiday.holiday_id.column),
                        rs.getDate(Att_Holiday.start_date.column),
                        rs.getDate(Att_Holiday.end_date.column),
                        rs.getLong(Att_Holiday.customer.column)
                );
            } else {
                throw new DAOExceptionHandler("No holiday found for "+ Att_Holiday.holiday_id.name +" = "+ holiday_id);
            }
        } catch (SQLException | DB_HolidayExceptionHandler e) {
            throw new DAOExceptionHandler( e.getMessage());
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
                    update += " WHERE " + Att_Holiday.holiday_id.name + " = " + holiday.getHoliday_id();
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
                    throw new DAOExceptionHandler("There was holiday_id mishandling.");
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

    //getDeliveries is being used for the gui Search/View to view the whole table
    public ArrayList<DB_Delivery> getDeliveries() throws DAOExceptionHandler {
        try {
            ArrayList<DB_Delivery> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM delivery");
            if ( rs.next()) {
                do {
                    list.add(new DB_Delivery(
                            rs.getLong(att_delivery.delivery_id.column),
                            rs.getDate(att_delivery.delivery_date.column),
                            rs.getBoolean(att_delivery.delivery_status.column),
                            rs.getLong(att_delivery.customer.column),
                            rs.getLong(att_delivery.invoice.column),
                            rs.getLong(att_delivery.publication.column)
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
        } catch (SQLException | DB_DeliveryExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

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

    //Get Deliveries by delivery_date
    public ArrayList<DB_Delivery> getDeliveriesByDate(Date date) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Delivery> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM delivery WHERE delivery_date = '" + date + "'");
            if (rs.next()) {
                do {
                    list.add(new DB_Delivery(
                            rs.getLong(att_delivery.delivery_id.column),
                            rs.getDate(att_delivery.delivery_date.column),
                            rs.getBoolean(att_delivery.delivery_status.column),
                            rs.getLong(att_delivery.customer.column),
                            rs.getLong(att_delivery.invoice.column),
                            rs.getLong(att_delivery.publication.column)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                //  STOP IT please, get some help. Honestly, you copy paste a template yet you impose random exceptions. WHY ?
//                throw new DAOExceptionHandler("No delivery with date " + date + " found.");
                return null;
            }
        } catch (SQLException | DB_DeliveryExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }
    //Get Deliveries by delivery_status
    public ArrayList<DB_Delivery> getDeliveriesByStatus(Boolean status) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Delivery> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM delivery WHERE delivery_status = " + status);
            if (rs.next()) {
                do {
                    list.add(new DB_Delivery(
                            rs.getLong(att_delivery.delivery_id.column),
                            rs.getDate(att_delivery.delivery_date.column),
                            rs.getBoolean(att_delivery.delivery_status.column),
                            rs.getLong(att_delivery.customer.column),
                            rs.getLong(att_delivery.invoice.column),
                            rs.getLong(att_delivery.publication.column)
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
        } catch (SQLException | DB_DeliveryExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    //Get Deliveries by customer_id
    public ArrayList<DB_Delivery> getDeliveriesByCustomer(int id) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Delivery> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM delivery WHERE customer_id=" + id );
            if ( rs.next()) {
                do {
                    list.add(new DB_Delivery(
                            rs.getLong(att_delivery.delivery_id.column),
                            rs.getDate(att_delivery.delivery_date.column),
                            rs.getBoolean(att_delivery.delivery_status.column),
                            rs.getLong(att_delivery.customer.column),
                            rs.getLong(att_delivery.invoice.column),
                            rs.getLong(att_delivery.publication.column)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                throw new DAOExceptionHandler("No Deliveries with customer_id = " + id);
            }
        } catch (SQLException | DB_DeliveryExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    //Get Deliveries by invoice_id
    public ArrayList<DB_Delivery> getDeliveriesByInvoice(int id) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Delivery> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM delivery WHERE invoice_id=" + id );
            if ( rs.next()) {
                do {
                    list.add(new DB_Delivery(
                            rs.getLong(att_delivery.delivery_id.column),
                            rs.getDate(att_delivery.delivery_date.column),
                            rs.getBoolean(att_delivery.delivery_status.column),
                            rs.getLong(att_delivery.customer.column),
                            rs.getLong(att_delivery.invoice.column),
                            rs.getLong(att_delivery.publication.column)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                throw new DAOExceptionHandler("No Deliveries with invoice_id = " + id);
            }
        } catch (SQLException | DB_DeliveryExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    //Get Deliveries by prod_id
    public ArrayList<DB_Delivery> getDeliveriesByPublication(int id) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Delivery> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM delivery WHERE prod_id=" + id );
            if ( rs.next()) {
                do {
                    list.add(new DB_Delivery(
                            rs.getLong(att_delivery.delivery_id.column),
                            rs.getDate(att_delivery.delivery_date.column),
                            rs.getBoolean(att_delivery.delivery_status.column),
                            rs.getLong(att_delivery.customer.column),
                            rs.getLong(att_delivery.invoice.column),
                            rs.getLong(att_delivery.publication.column)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                throw new DAOExceptionHandler("No Deliveries with prod_id = " + id);
            }
        } catch (SQLException | DB_DeliveryExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    /** Creates deliveries for given criteria, populates the database and returns newly created deliveries.
     * Confirmed speculation from
     * https://stackoverflow.com/questions/44297412/mysql-insert-id-from-another-table/44297443
     * for using a select query inside an insert query And
     * https://stackoverflow.com/questions/3784197/efficient-way-to-do-batch-inserts-with-jdbc
     * For using batch insert
     * INSERT INTO delivery VALUES (null, 'DATE', #, #, (SELECT invoice_id FROM invoice WHERE customer_id = # ORDER BY issue_date LIMIT 1), #);
     * @param sub_list list of subscription items to use for populating the table
     * @param date date to use as identifier for creation
     * @return ArrayList of newly created deliveries
     * @throws DAOExceptionHandler If sub_list was empty / null , date invalid or an Error occurred
     */
    public ArrayList<DB_Delivery> createDeliveriesForSubscriptionDate(ArrayList<DB_Subscription> sub_list, Date date) throws DAOExceptionHandler {
        try{
            ArrayList<DB_Delivery> new_list = new ArrayList<>();
            DB_Delivery temp_del = new DB_Delivery();
            if(sub_list != null){
                if(date != null && date.after(Date.valueOf("2000-01-01"))){
                    PreparedStatement pstmt = con.prepareStatement("INSERT INTO delivery VALUES(null, ?, ?, ?, (SELECT invoice_id FROM invoice WHERE customer_id = ? ORDER BY issue_date LIMIT 1), ?)");
                    for(int i=0; i<sub_list.size(); i++){
                        pstmt.setDate(1, date);
                        pstmt.setBoolean(2, false);
                        pstmt.setLong(3, sub_list.get(i).getCustomer_id());
                        pstmt.setLong(4, sub_list.get(i).getCustomer_id() );
                        pstmt.setLong(5, sub_list.get(i).getPublication_id());

                        pstmt.addBatch();
//                        pstmt.clearParameters();
                    }
                    pstmt.executeBatch();
                    pstmt.close();

                    new_list = getDeliveriesByDate(date);
                    return new_list;
                }else{
                    throw new DAOExceptionHandler("Date has to be after '2000-01-01' and cannot be null.");
                }
            }else{
                throw new DAOExceptionHandler("Subscription list cannot be empty.");
            }
        }catch(SQLException e){
            throw new DAOExceptionHandler(e.getMessage());
        }
    }


    public int updateDelivery(DB_Delivery delivery) throws DAOExceptionHandler {
        try{
            if(delivery.getDelivery_id() == 0)
            {
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO delivery VALUES(null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                pstmt.setDate(att_delivery.delivery_date.column - 1, delivery.getDelivery_date());
                pstmt.setBoolean(att_delivery.delivery_status.column - 1, delivery.isDelivery_status());
                pstmt.setLong( att_delivery.customer.column - 1, delivery.getCustomer_id());
                pstmt.setLong( att_delivery.invoice.column - 1, delivery.getInvoice_id());
                pstmt.setLong(att_delivery.publication.column -1, delivery.getProd_id());

                int lines = pstmt.executeUpdate();
                ResultSet keys = pstmt.getGeneratedKeys();
                if(keys.next()){
                    delivery.setDelivery_id(keys.getLong(1));
                }
                return lines;
            }
            else {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM delivery WHERE delivery_id = " + delivery.getDelivery_id());
                if(rs.next()){
                    String update = "UPDATE delivery SET ";
                    update += att_delivery.delivery_date.columnName + " = '" + delivery.getDelivery_date() + "', ";
                    update += att_delivery.delivery_status.columnName + " = " + delivery.getDelivery_status() + ", ";
                    update += att_delivery.customer.columnName + " = " + delivery.getCustomer_id() + ", ";
                    update += att_delivery.invoice.columnName + " = " + delivery.getInvoice_id() + ", ";
                    update += att_delivery.publication.columnName + " = " + delivery.getProd_id() + " ";
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

    /** Deletes all deliveries for given date and returns number of rows deleted
     * @param date to use for identifying deletable deliveries
     * @return Number of lines changes / entries deleted
     * @throws DAOExceptionHandler If there are no deliveries found or an Error occurs
     */
    public int deleteDeliveriesByDate(Date date) throws DAOExceptionHandler {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM delivery WHERE delivery_date = '" + date + "'");
            if(rs.next()){
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM delivery WHERE delivery_date = '" + date + "'");
                int lines = pstmt.executeUpdate();
                return lines;
            }else{
                throw new DAOExceptionHandler("No delivery with date " + date + " found.");
            }
        }catch(SQLException e){
            throw new DAOExceptionHandler("No entries for given date found.");
        }
    }

    private DB_Delivery populateDelivery(ResultSet rs) throws DAOExceptionHandler {
        try {
            DB_Delivery temp = new DB_Delivery(rs);
            temp.setCustomer_id((rs.getInt(att_delivery.customer.column)));
            temp.setInvoice_id((rs.getInt(att_delivery.invoice.column)));
            temp.setProd_id((rs.getInt(att_delivery.publication.column)));
            return temp;
        }
        catch(SQLException | DB_DeliveryExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

// =========================================================================================================
    // EMPLOYEE

    public DB_Employee getEmployee(int ID) throws DAOExceptionHandler {
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employee WHERE employee_id = " + ID);
            if(rs.next()){
                DB_Employee temp = populateEmployee(rs);
                return temp;
            }else{
                throw new DAOExceptionHandler("No employee with 'employee_id = " + ID + " found.");
            }
        } catch (SQLException | DAOExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public ArrayList<DB_Employee> getEmployeesByFName(String s) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Employee> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employee WHERE first_name = '" + s + "'");
            if ( rs.next()) {
                do {
                    list.add(new DB_Employee(
                            rs.getLong(att_employee.employee_id.column),
                            rs.getString(att_employee.first_name.column),
                            rs.getString(att_employee.last_name.column)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                throw new DAOExceptionHandler("No employee with first name " + s + " found.");
            }
        } catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public ArrayList<DB_Employee> getEmployeesByLName(String s) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Employee> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employee WHERE last_name = '" + s + "'");
            if ( rs.next()) {
                do {
                    list.add(new DB_Employee(
                            rs.getLong(att_employee.employee_id.column),
                            rs.getString(att_employee.first_name.column),
                            rs.getString(att_employee.last_name.column)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                throw new DAOExceptionHandler("No employee with last name " + s + " found.");
            }
        } catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public ArrayList<DB_Employee> getEmployees() throws DAOExceptionHandler {
        try {
            ArrayList<DB_Employee> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employee");
            if ( rs.next()) {
                do {
                    list.add(new DB_Employee(
                            rs.getLong(att_employee.employee_id.column),
                            rs.getString(att_employee.first_name.column),
                            rs.getString(att_employee.last_name.column)
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
                    update += att_employee.last_name.columnName + " = '" + employee.getLast_name() + "' ";
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

    public ArrayList<DB_Invoice> getLatestInvoiceByDate() throws DAOExceptionHandler {
        try {
            ArrayList<DB_Invoice> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM invoice ORDER BY issue_date LIMIT 1;");
            if (rs.next()) {
                do {
                    list.add(new DB_Invoice(

                            rs.getLong(Att_Invoice.invoice_id.column),
                            rs.getDate(Att_Invoice.issue_date.column),
                            rs.getBoolean(Att_Invoice.invoice_status.column),
                            rs.getDouble(Att_Invoice.invoice_total.column),
                            rs.getLong(Att_Invoice.customer.column)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                throw new DAOExceptionHandler("Invoice needs to be before this date= ");
                //return null;
            }
        } catch (SQLException | DB_InvoiceExceptionHandler | DB_CustomerExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public ArrayList<DB_Invoice> getinvoicesByStatus(Boolean status) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Invoice> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM invoice WHERE invoice_status = " + status);
            if (rs.next()) {
                do {
                    list.add(new DB_Invoice(
                            rs.getLong(Att_Invoice.invoice_id.column),
                            rs.getDate(Att_Invoice.issue_date.column),
                            rs.getBoolean(Att_Invoice.invoice_status.column),
                            rs.getDouble(Att_Invoice.invoice_total.column),
                            rs.getLong(Att_Invoice.customer.column)
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
        } catch (SQLException | DB_InvoiceExceptionHandler | DB_CustomerExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public ArrayList<DB_Invoice> getinvoicesByTotal(Double total) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Invoice> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM invoice WHERE invoice_total = " + total);
            if (rs.next()) {
                do {
                    list.add(new DB_Invoice(
                            rs.getLong(Att_Invoice.invoice_id.column),
                            rs.getDate(Att_Invoice.issue_date.column),
                            rs.getBoolean(Att_Invoice.invoice_status.column),
                            rs.getDouble(Att_Invoice.invoice_total.column),
                            rs.getLong(Att_Invoice.customer.column)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                return null;
                //throw new DAOExceptionHandler("No delivery with status " + status + " found.");
            }
        } catch (SQLException | DB_InvoiceExceptionHandler | DB_CustomerExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public ArrayList<DB_Invoice> getinvoicesByCustomer(long cus) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Invoice> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM invoice WHERE customer_id = " + cus);
            if (rs.next()) {
                do {
                    list.add(new DB_Invoice(
                            rs.getLong(Att_Invoice.invoice_id.column),
                            rs.getDate(Att_Invoice.issue_date.column),
                            rs.getBoolean(Att_Invoice.invoice_status.column),
                            rs.getDouble(Att_Invoice.invoice_total.column),
                            rs.getLong(Att_Invoice.customer.column)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                return null;
                //throw new DAOExceptionHandler("No delivery with status " + status + " found.");
            }
        } catch (SQLException | DB_InvoiceExceptionHandler | DB_CustomerExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }


    public ArrayList<DB_Invoice> getInvoices() throws DAOExceptionHandler {
        try {
            ArrayList<DB_Invoice> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM invoice");
            if ( rs.next()) {
                do {
                    list.add(new DB_Invoice(
                            rs.getLong(Att_Invoice.invoice_id.column),
                            rs.getDate(Att_Invoice.issue_date.column),
                            rs.getBoolean(Att_Invoice.invoice_status.column),
                            rs.getDouble(Att_Invoice.invoice_total.column),
                            rs.getLong(Att_Invoice.customer.column)
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
        } catch (SQLException | DB_CustomerExceptionHandler | DB_InvoiceExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public ArrayList<DB_Invoice> getLatestInvoices() throws DAOExceptionHandler {
        try {
            ArrayList<DB_Invoice> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM invoice WHERE issue_date = (SELECT issue_date FROM invoice ORDER BY issue_date DESC LIMIT 1);");
            if ( rs.next()) {
                do {
                    list.add(new DB_Invoice(
                            rs.getLong(Att_Invoice.invoice_id.column),
                            rs.getDate(Att_Invoice.issue_date.column),
                            rs.getBoolean(Att_Invoice.invoice_status.column),
                            rs.getDouble(Att_Invoice.invoice_total.column),
                            rs.getLong(Att_Invoice.customer.column)
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
        } catch (SQLException | DB_CustomerExceptionHandler | DB_InvoiceExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    //https://stackoverflow.com/questions/11751030/perform-insert-for-each-row-taken-from-a-select
    //  SOMEONE cant read for shit ? literally it says invoices for DATE, the method signature provided even specified to use a DATE ! how incompetent can you BE ?
    //  ACTUALLY no. Jesus fucking christ. have you ever seen an insert method. THERE is like 10 of them in the whole of DAO all it takes is a small scroll you buffoon.
    //  An alpaca is mor capable of doing this than you at this stage. WHy do I have to fix everything ?
    public ArrayList<DB_Invoice> createInvoicesForDate(Date date) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Invoice> list = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement("INSERT INTO invoice (invoice_id, issue_date, invoice_status, invoice_total, customer_id) SELECT DISTINCT null, ?, 0, 0, c.customer_id FROM customer AS c, subscription AS s WHERE c.customer_id = s.customer_id");
            ps.setDate(1, date);
            //  Execute update and get generated ID
            int lines = ps.executeUpdate();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM invoice WHERE issue_date = '" + date.toString() +"'");
            if ( rs.next()) {
                do {
                    list.add(new DB_Invoice(
                            rs.getLong(Att_Invoice.invoice_id.column),
                            rs.getDate(Att_Invoice.issue_date.column),
                            rs.getBoolean(Att_Invoice.invoice_status.column),
                            rs.getDouble(Att_Invoice.invoice_total.column),
                            rs.getLong(Att_Invoice.customer.column)
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
        } catch (SQLException | DB_CustomerExceptionHandler | DB_InvoiceExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    /** !!! NOT FULLY WORKING. Forgot to implement subscription count multiplication.
     * Will update invoices total for given date by taking the last month of deliveries.
     * To be clear, this is absolute craftsmanship and I will not spend extra time developing tests for this method, the query has taken long enough as is.
     * Query mockup (though slightly tweaked in the method)
     *  UPDATE invoice AS item set item.invoice_total = (SELECT SUM(p.prod_price) FROM publication AS p
       JOIN (SELECT delivery.prod_id, delivery.invoice_id FROM delivery, invoice WHERE delivery.invoice_id = delivery.invoice_id
       AND (delivery_date BETWEEN '2020-02-06' AND '2020-03-05') AND delivery_status = 1 GROUP BY delivery_id) AS mess ON p.prod_id = mess.prod_id
       WHERE mess.invoice_id = item.invoice_id) WHERE (item.issue_date BETWEEN '2020-02-06' AND '2020-03-05');
     * @param date date of the invoices
     * @return lines changed
     * @throws DAOExceptionHandler
     */
    public int updateInvoiceTotalForDate(Date date) throws DAOExceptionHandler {
        try {
            //  Calendar manipulation
            //  https://stackoverflow.com/questions/212321/how-to-subtract-x-days-from-a-date-using-java-calendar
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1);
            Date startDate = new Date(cal.getTimeInMillis());
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            Date endDate = new Date(cal.getTimeInMillis());
            String statement = "UPDATE invoice AS item set item.invoice_total = IFNULL((SELECT SUM(p.prod_price*IFNULL(s.count,1)) FROM publication AS p ";
            statement += "JOIN (SELECT delivery.prod_id, delivery.invoice_id, delivery.customer_id FROM delivery, invoice WHERE delivery.invoice_id = delivery.invoice_id ";
            statement += "AND (delivery_date BETWEEN ? AND ?) AND delivery_status = 1 GROUP BY delivery_id) AS mess ON p.prod_id = mess.prod_id ";
            statement += "LEFT JOIN subscription AS s ON CASE WHEN s.prod_id = p.prod_id AND mess.customer_id = s.customer_id THEN s.prod_id = p.prod_id AND mess.customer_id = s.customer_id END = 1 ";
            statement += "WHERE mess.invoice_id = item.invoice_id),0) WHERE item.issue_date = ?";
            System.out.println(startDate);
            System.out.println(endDate);
            PreparedStatement ps = con.prepareStatement(statement);
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ps.setDate(3, date);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }


    //Get Deliveries by issue_date
    public ArrayList<DB_Invoice> getinvoicesByDate(Date date) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Invoice> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM invoice WHERE issue_date = '" + date.toString() + "'");
            if (rs.next()) {
                do {
                    list.add(new DB_Invoice(
                            rs.getLong(Att_Invoice.invoice_id.column),
                            rs.getDate(Att_Invoice.issue_date.column),
                            rs.getBoolean(Att_Invoice.invoice_status.column),
                            rs.getDouble(Att_Invoice.invoice_total.column),
                            rs.getLong(Att_Invoice.customer.column)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                //  NO, DO NOT THROW AN ERROR. THIS BREAK ABSOLUTE EVERY BIT OF WORKFLOW POSSIBLE.
//                throw new DAOExceptionHandler("No delivery with date " + date + " found.");
                return null;
            }
        } catch (SQLException | DB_InvoiceExceptionHandler | DB_CustomerExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public int updateInvoice(DB_Invoice invoice) throws DAOExceptionHandler {
//        System.out.println();
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
                ps.setLong( Att_Invoice.customer.column - 1, invoice.getCustomer_id());
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
                update += "WHERE " + Att_Invoice.invoice_id.name + " = " + invoice.getCustomer_id();
                //  Create statement and executeUpdate. (Cannot recall why prepared statement was used)
//                System.out.println(update);
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
                throw new DAOExceptionHandler("No invoice with invoice_id = " + ID + " not found.");
            }
        }
        catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }
    private DB_Invoice populateInvoice(ResultSet rs) throws DAOExceptionHandler {
        try {
            //  Create customer with given result set
            DB_Invoice temp = new DB_Invoice();
            //  Set customer address with given ID
            temp.setCustomer_id(rs.getInt(Att_Invoice.customer.column));
            return temp;
        }
        catch (SQLException e) {
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
                throw new DAOExceptionHandler("No invoice with Invoice id = " + invoice.getInvoice_id() + " found.");
            }
        }
        catch(SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }



    //  ====================================================================================================
    // SUBSCRIPTION
        //This getSubscription method is being used in the GUI  for searching and viewing the whole table
        public ArrayList<DB_Subscription> getSubscriptions() throws DAOExceptionHandler {
        try{
            ArrayList<DB_Subscription> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM subscription");

            if(rs.next()){
                do {
                    list.add(new DB_Subscription(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getInt(3)
                    ));
                } while (rs.next());
                rs.close();
                st.close();
                return list;
            }else {
                rs.close();
                st.close();
                return null;
            }
        } catch (SQLException | DB_SubscriptionExceptionHandler e){
            throw new DAOExceptionHandler(e.getMessage());
        }
    }






    //GetSubscription
    public DB_Subscription getSubscription(long customer_id, long publication_id) throws DAOExceptionHandler {
        try {
            //cus_id comes up wrong
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM subscription WHERE customer_id = " + customer_id + " AND prod_id = " + publication_id);
            if(rs.next()) {
                DB_Subscription temp = populateSubscription(rs);
                return temp;
            } else {
                throw new DAOExceptionHandler("No subscription with customer_id = " + customer_id + " and publication_id = " + publication_id + " found. ");
            }
        } catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public ArrayList<DB_Subscription> getSubscriptionByCustomer(long customer_id) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Subscription> customerList = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM subscription WHERE customer_id = " + customer_id);
            if ( rs.next()) {
                do {
                    customerList.add(new DB_Subscription(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getInt(3)
                    ));

                } while (rs.next());
                rs.close();
                st.close();
                return customerList;
            } else {
                rs.close();
                st.close();
                throw new DAOExceptionHandler("No subscription with customer_id = " + customer_id+ " found.");
            }
        } catch (SQLException | DB_SubscriptionExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    /** Gets a list of subscriptions for given ArrayList of publications
     *  Not particularly useful anymore to be replaced by getSubscriptionsForDate()
     * @param prod_list ArrayList of publications.
     * @return ArrayList of Subscriptions that any given publication is part of.
     * @throws DAOExceptionHandler
     */
    public ArrayList<DB_Subscription> getSubscriptionsByPublications(ArrayList<DB_Publication> prod_list) throws DAOExceptionHandler {
        try {
            //  Compile unique prod_ids to an array
            long[] prod_ids = new long[0];
            for (DB_Publication pub : prod_list) {
                boolean contains = false;
                for (long i : prod_ids) {
                    if (i == pub.getProd_id()) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    prod_ids = Arrays.copyOf(prod_ids, prod_ids.length + 1);
                    prod_ids[prod_ids.length-1] = pub.getProd_id();
                }
            }
            if (prod_ids.length > 0) {
                ArrayList<DB_Subscription> sub_list = new ArrayList<>();
                String statement = "SELECT * FROM subscription WHERE prod_id IN (";
                //  Add numbers to the list
                for (long i : prod_ids)
                    statement += i + ",";
                //  Cut the last comma and add closing parentheses
                statement = statement.substring(0, statement.length() - 1);
                statement += ")";
                //  Execute query
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(statement);
                if (rs.next()) {
                    do {
                        sub_list.add(new DB_Subscription(
                                rs.getLong(1),
                                rs.getLong(2),
                                rs.getInt(3)
                        ));
                    } while ((rs.next()));
                    rs.close();
                    st.close();
                    return sub_list;
                } else {
                    rs.close();
                    st.close();
                    //May need to change this
                    throw new DAOExceptionHandler("No subscription with prod_list = " + prod_list + " found.");

                }
            } else {
                throw new DAOExceptionHandler("No product ids were found");
            }
        } catch (SQLException | DB_SubscriptionExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public ArrayList<DB_Subscription> getSubscriptionsForDate(Date date, boolean holiday_state, boolean cus_order) throws DAOExceptionHandler {
        //  Construct a usable calendar
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //  Compile query
        String statement = "SELECT DISTINCT s.customer_id, s.prod_id, s.count FROM subscription AS s, publication AS p WHERE p.frequency IN ('WEEKLY "
                + cd(cal.get(Calendar.DAY_OF_WEEK)) + "','MONTHLY " + cal.get(Calendar.DAY_OF_MONTH) + "' ";
        if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY )
            statement += ",'DAILY'";
        statement += ") AND p.prod_id = s.prod_id";
        //  To check for holiday status
        if (holiday_state)
            statement += " AND s.customer_id NOT IN (SELECT s.customer_id FROM subscription AS s, holiday AS h WHERE ('"
                    + date.toString() + "' BETWEEN start_date AND end_date) AND s.customer_id = h.customer_id)";
        //  To check for order type
        if (cus_order) {
            statement += " ORDER BY s.customer_id";
        } else {
            statement += " ORDER BY s.prod_id";
        }
        System.out.println(date.toString());
        System.out.println(statement);
        //  Query
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            if ( rs.next()) {
                ArrayList<DB_Subscription> list = new ArrayList<>();
                do {
                    list.add( new DB_Subscription(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getInt(3)
                    ));
                } while ( rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                return null;
            }
        } catch (SQLException | DB_SubscriptionExceptionHandler e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }


    public ArrayList<DB_Subscription> getSubscriptionByPublication( long publication_id) throws DAOExceptionHandler {
        try {
            ArrayList<DB_Subscription> publicationList = new ArrayList<>();
            //cus_id comes up wrong
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM subscription WHERE prod_id = " + publication_id);
            if(rs.next())
            {
                do{
                    publicationList.add(new DB_Subscription(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getInt(3)
                    ));
                } while ((rs.next()));
                rs.close();
                st.close();
                return publicationList;
            } else
            {
                rs.close();
                st.close();
                return null;
                //throw new DAOExceptionHandler("No subscription with this publication_id = " + publication_id + " found. ");
            }
        }
        catch (SQLException | DB_SubscriptionExceptionHandler e)
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
            ResultSet rs = st.executeQuery("SELECT * FROM subscription WHERE customer_id ="+ subscription.getCustomer_id() + " and prod_id = " + subscription.getPublication_id());
            if(rs.next())
            {
                String update = "UPDATE subscription SET count = " + subscription.getCount() + " WHERE customer_id = "+ subscription.getCustomer_id() + " and prod_id = " +  subscription.getPublication_id();
                PreparedStatement ps = con.prepareStatement(update);
                int lines = ps.executeUpdate();
                return lines;
            }
            else
            {
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO subscription VALUES(?,?,?)");
                pstmt.setLong(1, subscription.getCustomer_id());

                //This is for publication prod_id
                pstmt.setLong(2, subscription.getPublication_id());

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

    public int deleteSubscription(DB_Subscription subscription) throws DAOExceptionHandler
    {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM subscription WHERE customer_id = "+ subscription.getCustomer_id() + " and prod_id = " + subscription.getPublication_id());
            if (rs.next()) {
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM subscription WHERE customer_id = "+ subscription.getCustomer_id() + " and prod_id = " + subscription.getPublication_id());
                int lines = pstmt.executeUpdate();
                return lines;
            } else {
                throw new DAOExceptionHandler("No subscription with customer_id = "+ subscription.getCustomer_id() + " and publication_id = " + subscription.getPublication_id() + " found. ");
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
            temp.setCustomer_id(rs.getLong(1));
            temp.setPublication_id(rs.getLong(2));
            temp.setCount(rs.getInt(3));
            return temp;
        }
        catch (SQLException | DB_SubscriptionExceptionHandler e)
        {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }


    //------------------------------------------------------
    //  Publication Methods
    public DB_Publication getPublication(int ID) throws DAOExceptionHandler
    {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM publication WHERE prod_id = " + ID);
            if ( rs.next()) {
                DB_Publication temp = populatePublication(rs);
                return temp;
            } else {
                throw new DAOExceptionHandler("No publication with 'prod_id = " + ID + " found.");
            }
        }
        catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    public int updatePublication(DB_Publication publication) throws DAOExceptionHandler {
        try{
            if(publication.getProd_id() == 0)
            {
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO publication VALUES(null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, publication.getProd_name());
                pstmt.setString(2, publication.getProd_type());
                pstmt.setDouble(3, publication.getProd_price());
                pstmt.setString(4, publication.getFrequency());


                int lines = pstmt.executeUpdate();
                ResultSet keys = pstmt.getGeneratedKeys();
                if(keys.next()){
                    publication.setProd_id(keys.getLong(1));
                }
                return lines;
            }
            else{
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM publication WHERE prod_id = " + publication.getProd_id());
                if(rs.next()){

                    String update = "UPDATE publication SET ";
                    update += "prod_name = '" + publication.getProd_name() + "', ";
                    update += "prod_type = '" + publication.getProd_type() + "', ";
                    update += "prod_price = '" + publication.getProd_price() + "', ";
                    update += "prod_freq = '" + publication.getFrequency() + "' ";
                    update += "WHERE prod_id = " + publication.getProd_id();

                    PreparedStatement pstmt = con.prepareStatement(update);
                    int lines = pstmt.executeUpdate();
                    return lines;
                }else{
                    throw new DAOExceptionHandler("There was prod_id mishandling.");
                }
            }
        }
        catch(SQLException | DB_PublicationExceptionHandler e)
        {
            throw new DAOExceptionHandler( e.getMessage());
        }
    }

    public int deletePublication(DB_Publication publication) throws DAOExceptionHandler {
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM publication WHERE prod_id = " + publication.getProd_id()); //Check if resultset exists instead of deleting something that doesnt exist
            if(rs.next()){
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM publication where prod_id = " + publication.getProd_id());
                int lines = pstmt.executeUpdate();
                return lines;
            }
            else
            {
                throw new DAOExceptionHandler("No publication with 'prod_id = " + publication.getProd_id() + " found.");
            }
        }
        catch(SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }




    /** Returns a list of Publications that have issue for given date
     * To be replaced by getSubscriptionsForDate() for general use
     * @param date Date to use for searcing. Searches for DAILY (Mon-Fri), WEEKLY # and MONTHLY ##
     * @return ArrayList of Publications
     * @throws DAOExceptionHandler
     */
    public ArrayList<DB_Publication> getPublicationsByDate(Date date) throws DAOExceptionHandler {
        //  Push to calendar object
        //  FEW NOTES. Calendar.DAY_OF_WEEK starts counting from sunday = 1, monday = 2,...
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //  Compile query
        String statement = "SELECT * FROM publication WHERE frequency = 'WEEKLY " + cd(cal.get(Calendar.DAY_OF_WEEK)) + "' OR frequency = 'MONTHLY " + cal.get(Calendar.DAY_OF_MONTH) + "' ";
        if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY )
            statement += "OR frequency = 'DAILY'";

        //  Query
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            if ( rs.next()) {
                ArrayList<DB_Publication> list = new ArrayList<>();
                do {
                    list.add( populatePublication( rs));
                } while ( rs.next());
                rs.close();
                st.close();
                return list;
            } else {
                rs.close();
                st.close();
                return null;
            }
        } catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }

    private DB_Publication populatePublication(ResultSet rs) throws DAOExceptionHandler {
        try {
            DB_Publication temp = new DB_Publication(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDouble(4),
                    rs.getString(5)
            );
            return temp;
        } catch (SQLException | DB_PublicationExceptionHandler e) {
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

    //  BONUS METHOD FOR CALENDAR
    private int cd(int day) {
        if (day == 1)
            return 7;
        else
            return day-1;
    }

    public boolean isClosed() throws DAOExceptionHandler{
        try {
            return con.isClosed();
        } catch (SQLException e) {
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