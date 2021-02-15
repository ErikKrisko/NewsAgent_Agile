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

    /** Blank constructor */
    public DB_Customer() { }

    public DB_Customer(String first_name, String last_name, String phone_no, DB_Address address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_no = phone_no;
        this.address = address;
    }

    public DB_Customer(ResultSet rs) throws JDBCExceptionHandler {
        try {
            customer_id = rs.getInt( Att_Customer.customer_id.column);
            first_name = rs.getString( Att_Customer.first_name.column);
            last_name = rs.getString( Att_Customer.last_name.column);
            phone_no = rs.getString( Att_Customer.phone_no.column);
        } catch (SQLException e) {
            throw new JDBCExceptionHandler(e.getMessage());
        }
    }

    //  Validate attributes
    private String vEntry(Att_Customer type, String entry) throws DB_CustomerExceptionHandler {
        switch (type) {
            case first_name -> {
                if (entry.length() > 0 && entry.length() <= 20)
                    return entry;
                else
                    throw new DB_CustomerExceptionHandler("Invalid first_name.");
            }
            case last_name -> {
                if (entry.length() > 0 && entry.length() <= 20)
                    return entry;
                else
                    throw new DB_CustomerExceptionHandler("Invalid last_name.");
            }
            case phone_no -> {
                if (entry.length() == 8)
                    return entry;
                else
                    throw new DB_CustomerExceptionHandler("Invalid phone_no.");
            }
            default -> throw new DB_CustomerExceptionHandler("Internal error. Attribute unspecified.");
        }
    }
    //  AUTO GENERATED toString
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

/** List of customer attributes */
enum Att_Customer {
    customer_id(1),
    first_name(2),
    last_name(3),
    phone_no(4),
    address(5);

    public final int column;

    Att_Customer(int i) { this.column = i; }
}

/** Search object for customer */
class Search_Customer {
    //  !TEMP code will need to be changed

    //  Attribute reference
    private Att_Customer[] attributes;
    //  Search term
    private String[] term;

    public Search_Customer (Att_Customer[] attributes, String[] term) {
        this.attributes = attributes;
        this.term = term;
    }
}


class DB_CustomerExceptionHandler extends Exception {
    String message;

    public DB_CustomerExceptionHandler(String errMessage){  message = errMessage; }

    public String getMessage() {    return message; }
}