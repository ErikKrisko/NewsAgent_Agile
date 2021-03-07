import java.util.ArrayList;

public class DB_Customer {
    //  Base customer attributes
    private long customer_id = 0;
    private String first_name, last_name, phone_no;
    //  External customer attributes
    private DB_Address address;
    //  List of customer holidays
    ArrayList<DB_Holiday> holidays;

    /** Blank Constructor for DB_Customer.
     */
    public DB_Customer() { }

    /** Creates DB_Customer object with provided specifications.
     *  All variables are validated.
     * @param customer_id should be 0 for internally created objects or returned from query.
     * @param first_name of the customer. Cannot contain number and has to be <= 20 in length.
     * @param last_name of the customer. Cannot contain number and has to be <= 20 in length.
     * @param phone_no of the customer. May only contain digits and has to be 10 in length.
     * @param address address object for customer.
     * @throws DB_CustomerExceptionHandler if any entry is invalid.
     */
    public DB_Customer(long customer_id,String first_name, String last_name, String phone_no, DB_Address address) throws DB_CustomerExceptionHandler {
        this.customer_id = validateID( customer_id);
        this.first_name = validateEntry( Att_Customer.first_name, first_name);
        this.last_name = validateEntry( Att_Customer.last_name, last_name);
        this.phone_no = validateEntry( Att_Customer.phone_no, phone_no);
        this.address = address;
    }

    /** Validation class to validate all string elements for DB_Customer.
     * @param type type of attribute to verify. Only applicable to first_name, last_name, phone_no.
     * @param entry String entry to verify.
     * @return returns entry if valid.
     * @throws DB_CustomerExceptionHandler if entry is invalid.
     */
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

    /** Validates ID to ensure no negative number is used.
     * @param id of the object, should be 0 if created internally.
     * @return id of the object if it is valid.
     * @throws DB_CustomerExceptionHandler if entry is invalid.
     */
    public long validateID(long id) throws DB_CustomerExceptionHandler {
        if (id >=0)
            return id;
        else
            throw new DB_CustomerExceptionHandler("ID must be 0 or greater.");
    }

    //  AUTO GENERATED toString
    @Override
    public String toString() {
        return "DB_Customer{" +
                "customer_id=" + customer_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", address=" + address +
                ", holidays=" + holidays +
                '}';
    }

    //  AUTO GENERATED getters and setters
    public long getCustomer_id() {   return customer_id; }
    public String getFirst_name() { return first_name; }
    public String getLast_name() {  return last_name; }
    public String getPhone_no() {   return phone_no; }
    public DB_Address getAddress() {    return address; }
    public ArrayList<DB_Holiday> getHolidays() { return holidays; }
    public void setCustomer_id(long customer_id) throws DB_CustomerExceptionHandler { this.customer_id = validateID( customer_id); }
    public void setFirst_name(String first_name) throws DB_CustomerExceptionHandler {  this.first_name = validateEntry( Att_Customer.first_name, first_name); }
    public void setLast_name(String last_name) throws DB_CustomerExceptionHandler {    this.last_name = validateEntry( Att_Customer.last_name, last_name); }
    public void setPhone_no(String phone_no) throws DB_CustomerExceptionHandler {  this.phone_no = validateEntry( Att_Customer.phone_no, phone_no); }
    public void setAddress(DB_Address address) {    this.address = address; }
    public void setHolidays(ArrayList<DB_Holiday> holidays) { this.holidays = holidays; }
}

/** This is the SQL table layout reference in java code.
 *  Allows easier access and guidance of variable locations.
 *      Column 1    Column 2     Column 3    Column 4     Column 5
 *  +-------------+------------+-----------+------------+------------+
 *  | customer_id | first_name | last_name | phone_no   | address_id |
 *  +-------------+------------+-----------+------------+------------+
 *  |           1 | Bill       | Birr      | 0951078281 |          1 |
 *  +-------------+------------+-----------+------------+------------+
 */
enum Att_Customer {
    //  Customer table attributes for column #, column Name
    customer_id(1, "customer_id"),
    first_name(2, "first_name"),
    last_name(3, "last_name"),
    phone_no(4, "phone_no"),
    address(5, "address_id");

    //  Column in which the given attribute appears by default
    public final int column;
    //  Name of column in which te attribute appears by default
    public final String name;

    //  Constructor (it just makes it work ? I think)
    Att_Customer(int column, String columnName) {
        this.column = column;
        this.name = columnName;
    }
}

/** A search object to help search for any criteria of customer.
 *  Fully able to handle customer_id, first_name, last_name, phone_no.
 *  Address implementation WIP
 */
class Search_Customer {
    //  Customer attribute type
    private Att_Customer attribute;
    //  Search term
    private String term;
    //  Search type to use : = || LIKE
    private boolean strong;
    //  Search_Address object
    private Search_Address search_address;


    /** Used as an object to more easily handle search criteria.
     * @param attribute the attribute to search for (customer_id, first_name,...)
     * @param search_term the term to search for ( "John", "%i%,...) to construct a search criteria ( first_name = "John" )
     * @param strong if the search is strong or weak. Strong uses "=", weak uses "LIKE" ( last_name LIKE "_oh%" )
     */
    public Search_Customer(Att_Customer attribute, String search_term, boolean strong) {
        this.attribute = attribute;
        this.term = search_term;
        this.strong = strong;
    }

    /** Extended constructor to pass Search_Address around. WIP
     * @param search_address object with its own set of criteria
     */
    public Search_Customer(Search_Address search_address) {
        attribute = Att_Customer.address;
        this.search_address = search_address;
    }

    //  AUTO GENERATED getters and setters

    public Search_Address getSearch_address() { return search_address; }
    public void setSearch_address(Search_Address search_address) { this.search_address = search_address; }
    public Att_Customer getAttribute() { return attribute; }
    public void setAttribute(Att_Customer attribute) { this.attribute = attribute; }
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    public boolean isStrong() { return strong; }
    public void setStrong(boolean strong) { this.strong = strong; }
}

/** A specific exception handler for DB_Customer.
 */
class DB_CustomerExceptionHandler extends Exception {
    String message;
    public DB_CustomerExceptionHandler(String errMessage){  message = errMessage; }
    public String getMessage() {    return message; }
}