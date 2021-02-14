import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Customer {
    //  Connection for creating external attributes as required
    private static JDBC connection;
    //  Handler for storing initialized elements
    private static DB_Handler handler;
    //  Base customer attributes
    private int customer_id;
    private String first_name, last_name, phone_no;
    //  External customer attributes
    private DB_Address address;
    //  List of customer holidays
    //  NEED TO DO

    /** A list of columnIndexes for resultSet */
    public enum attributes {
        customer_id(1),
        first_name(2),
        last_name(3),
        phone_no(4),
        address(5);

        public final int index;

        attributes(int i) { this.index = i; }
    }

    /** Blank constructor */
    public DB_Customer() { }


    public void getByID(int id) throws DB_CustomerExceptionHandler {
        try {
            ResultSet rs = connection.getSet("Select * from customer where customer_id = " + id);
            if (rs.next()) {
                customer_id = rs.getInt(attributes.customer_id.index);
                first_name = rs.getString(attributes.first_name.index);
                last_name = rs.getString(attributes.last_name.index);
                phone_no = rs.getString(attributes.phone_no.index);
                address = new DB_Address();
                address.getByID(connection, rs.getInt(attributes.address.index));
            }
        }
        catch (SQLException | JDBCExceptionHandler | DB_AddressExceptionHandler e) {
            throw new DB_CustomerExceptionHandler(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "DB_Customer{" +
                "customer_id=" + customer_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", address=" + address.toString() +
                '}';
    }

    //  AUTO GENERATED getters and setters
    public int getCustomer_id() {   return customer_id; }
    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) {  this.first_name = first_name; }
    public String getLast_name() {  return last_name; }
    public void setLast_name(String last_name) {    this.last_name = last_name; }
    public String getPhone_no() {   return phone_no; }
    public void setPhone_no(String phone_no) {  this.phone_no = phone_no; }
    public DB_Address getAddress() {    return address; }
    public void setAddress(DB_Address address) {    this.address = address; }
    //  JDBC connection
    public static JDBC getConnection() {    return connection; }
    public static void setConnection(JDBC connection) { DB_Customer.connection = connection; }
    //  Handler
    public static DB_Handler getHandler() { return handler; }
    public static void setHandler(DB_Handler handler) { DB_Customer.handler = handler; }
}

class DB_CustomerExceptionHandler extends Exception {
    String message;

    public DB_CustomerExceptionHandler(String errMessage){
        message = errMessage;
    }

    public String getMessage() {
        return message;
    }
}