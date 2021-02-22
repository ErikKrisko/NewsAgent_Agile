import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Customer {
    //  Base customer attributes
    private long customer_id = 0;
    private String first_name, last_name, phone_no;
    //  External customer attributes
    private DB_Address address;
    //  List of customer holidays
    //! Holidays WIP

    /** Blank constructor */
    public DB_Customer() { }

    public DB_Customer(String first_name, String last_name, String phone_no, DB_Address address) throws DB_CustomerExceptionHandler {
        this.first_name = validateEntry( Att_Customer.first_name, first_name);
        this.last_name = validateEntry( Att_Customer.last_name, last_name);
        this.phone_no = validateEntry( Att_Customer.phone_no, phone_no);
        this.address = address;
    }

    public DB_Customer(ResultSet rs) throws DB_CustomerExceptionHandler {
        try {
            customer_id = rs.getInt( Att_Customer.customer_id.column);
            first_name = rs.getString( Att_Customer.first_name.column);
            last_name = rs.getString( Att_Customer.last_name.column);
            phone_no = rs.getString( Att_Customer.phone_no.column);
        } catch (SQLException e) {
            throw new DB_CustomerExceptionHandler(e.getMessage());
        }
    }

    //  Validate attributes
    public String validateEntry(Att_Customer type, String entry) throws DB_CustomerExceptionHandler {
        if (!entry.isBlank() || !entry.isEmpty()) {
            switch (type) {
                case first_name, last_name -> {
                    if (entry.length() <= 20)
                        if (!entry.matches(".*\\d.*"))
                            return entry;
                        else
                            throw new DB_CustomerExceptionHandler("Entry = \"" + entry + "\", cannot contain numbers.");
                    else
                        throw new DB_CustomerExceptionHandler("Entry = \"" + entry + "\", is too long.");
                }
                case phone_no -> {
                    if (entry.length() == 10)
                        if (entry.matches("\\d+"))
                            return entry;
                        else
                            throw new DB_CustomerExceptionHandler( "Entry = \"" + entry + "\", cannot contain letters.");
                    else
                        throw new DB_CustomerExceptionHandler("Entry = \"" + entry + "\", has to be of length 10.");
                }
                default -> throw new DB_CustomerExceptionHandler("Internal error. Unhandled attribute.");
            }
        } else {
            throw new DB_CustomerExceptionHandler("Entry = \"" + entry + "\", cannot be an empty String.");
        }
    }

    /** Returns specified Att_Customer attribute as a String
     * @param attribute attribute type to be returned
     * @return an attribute value
     * @throws DB_CustomerExceptionHandler
     */
    public String get(Att_Customer attribute) throws DB_CustomerExceptionHandler {
        switch (attribute) {
            case customer_id -> { return "" + customer_id; }
            case first_name -> { return first_name; }
            case last_name -> { return last_name; }
            case phone_no -> { return phone_no; }
            case address -> { return "" + address.getAddress_id(); }
            default -> { throw new DB_CustomerExceptionHandler("Attribute error"); }
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
    public long getCustomer_id() {   return customer_id; }
    public String getFirst_name() { return first_name; }
    public String getLast_name() {  return last_name; }
    public String getPhone_no() {   return phone_no; }
    public DB_Address getAddress() {    return address; }
    public void setCustomer_id(long customer_id) { this.customer_id = customer_id; }
    public void setFirst_name(String first_name) throws DB_CustomerExceptionHandler {  this.first_name = validateEntry( Att_Customer.first_name, first_name); }
    public void setLast_name(String last_name) throws DB_CustomerExceptionHandler {    this.last_name = validateEntry( Att_Customer.last_name, last_name); }
    public void setPhone_no(String phone_no) throws DB_CustomerExceptionHandler {  this.phone_no = validateEntry( Att_Customer.phone_no, phone_no); }
    public void setAddress(DB_Address address) {    this.address = address; }
}

/** List of customer attributes */
enum Att_Customer {
    //  Customer table attributes
    customer_id(1, "customer_id"),
    first_name(2, "first_name"),
    last_name(3, "last_name"),
    phone_no(4, "phone_no"),
    address(5, "address_id");

    //  Column in which the given attribute appears by default
    public final int column;
    //  Name of column in which te attribute appears by default
    public final String name;

    Att_Customer(int column, String columnName) {
        this.column = column;
        this.name = columnName;
    }
}

/** Search object for customer WIP! */
class Search_Customer {
    private Att_Customer attribute;
    private String term;
    private boolean strong;

    public Search_Customer(Att_Customer attribute, String term, boolean strong) {
        this.attribute = attribute;
        this.term = term;
        this.strong = strong;
    }

    //  AUTO GENERATED getters and setters
    public Att_Customer getAttribute() { return attribute; }
    public void setAttribute(Att_Customer attribute) { this.attribute = attribute; }
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    public boolean isStrong() { return strong; }
    public void setStrong(boolean strong) { this.strong = strong; }
}


class DB_CustomerExceptionHandler extends Exception {
    String message;

    public DB_CustomerExceptionHandler(String errMessage){  message = errMessage; }

    public String getMessage() {    return message; }
}