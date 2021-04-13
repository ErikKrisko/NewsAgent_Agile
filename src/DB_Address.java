public class DB_Address {
    //  Base address attributes
    private long address_id = 0;
    private int area_code;
    private String full_address, eir_code;

    /** Blank Constructor for DB_Address.
     */
    public DB_Address() { }

    /** Creates DB_Address object with provided specifications.
     * All variables are validated.
     * @param address_id should be 0 for internally created objects or returned from query.
     * @param full_address should be <= 50 characters in length.
     * @param area_code WIP
     * @param eir_code Has to be 7 characters in length.
     * @throws DB_AddressExceptionHandler
     */
    public DB_Address(long address_id, String full_address, int area_code, String eir_code) throws DB_AddressExceptionHandler {
        this.address_id = validateID( address_id);
        this.full_address = validateEntry( Att_Address.full_address, full_address);
        this.area_code = validateArea(area_code);
        this.eir_code = validateEntry( Att_Address.eir_code, eir_code);
    }

    /** Validation class to validate all string elements for DB_Address.
     * @param attribute type of attribute to verify. Only applicable to full_address, area_code, eir_code.
     * @param entry String entry to verify.
     * @return returns entry if valid.
     * @throws DB_AddressExceptionHandler if entry is invalid.
    */
    public String validateEntry(Att_Address attribute, String entry) throws DB_AddressExceptionHandler {
        if (!entry.isBlank() || !entry.isEmpty()) {
            switch (attribute) {
                case full_address -> {
                    if (entry.length() <= 50)
                        return entry;
                    else
                        throw new DB_AddressExceptionHandler("full_address too long.");
                }
                case eir_code -> {
                    if (entry.length() == 7)
                        return entry;
                    else
                        throw new DB_AddressExceptionHandler("eir_code invalid length.");
                }
                default -> throw new DB_AddressExceptionHandler("Internal error. Unhandled attribute.");
            }
        } else {
            throw new DB_AddressExceptionHandler( "Entry = \"" + entry + "\", cannot be an empty String.");
        }
    }

    public int validateArea(int area_code) throws DB_AddressExceptionHandler {
        if (area_code >= 0) {
            return area_code;
        } else {
            throw new DB_AddressExceptionHandler("Area code cannot be negative.");
        }
    }

    /** Validates ID to ensure no negative number is used.
     * @param id of the object, should be 0 if created internally.
     * @return id of the object if it is valid.
     * @throws DB_CustomerExceptionHandler if entry is invalid.
     */
    public long validateID(long id) throws DB_AddressExceptionHandler {
        if (id >=0)
            return id;
        else
            throw new DB_AddressExceptionHandler("ID must be 0 or greater.");
    }

    public boolean equals(DB_Address address) {
        boolean equals = true;
        //  Compare ID
        if (this.getAddress_id() != address.getAddress_id())
            equals = false;
        //  Compare full address
        if (equals && !this.getFull_address().equals( address.getFull_address()))
            equals = false;
        //  Compare Area_code
        if (equals && this.getArea_code() != address.getArea_code())
            equals = false;
        //  Compare Eir_code
        if (equals && !this.getEir_code().equals( address.getEir_code()))
            equals = false;
        return equals;
    }

    //  AUTO GENERATED toString
    @Override
    public String toString() {
        return "DB_Address{" +
                "address_id=" + address_id +
                ", full_address='" + full_address + '\'' +
                ", area_code='" + area_code + '\'' +
                ", eir_code='" + eir_code + '\'' +
                '}';
    }

    public String[] getRowData() {
        return new String[] {full_address, eir_code, String.valueOf(area_code)};
    }

    //  AUTO GENERATED getters and setters
    public long getAddress_id() {    return address_id; }
    public String getFull_address() {   return full_address; }
    public int getArea_code() {  return area_code; }
    public String getEir_code() {   return eir_code; }
    public void setAddress_id(long address_id) throws DB_AddressExceptionHandler { this.address_id = validateID( address_id); }
    public void setFull_address(String full_address) throws DB_AddressExceptionHandler {  this.full_address = validateEntry( Att_Address.full_address, full_address); }
    public void setArea_code(int area_code) throws DB_AddressExceptionHandler {    this.area_code = validateArea(area_code); }
    public void setEir_code(String eir_code) throws DB_AddressExceptionHandler {  this.eir_code = validateEntry( Att_Address.eir_code, eir_code); }
}

/** This is the SQL table layout reference in java code.
 *  Allows easier access and guidance of variable locations.
 *      Column 1    Column 2                                Column 3    Column 4
 *  +------------+---------------------------------------+-----------+----------+
 *  | address_id | full_address                          | area_code | eir_code |
 *  +------------+---------------------------------------+-----------+----------+
 *  |          1 | 123 black clover lane rhode Co.Offaly | B1        | R45YW71  |
 *  +------------+---------------------------------------+-----------+----------+
 */
enum Att_Address {
    //  Address table attributes for column #, column Name
    address_id(1, "address_id"),
    full_address(2, "full_address"),
    area_code(3, "area_code"),
    eir_code(4, "eir_code");

    //  Column in which the given attribute appears by default
    public final int column;
    //  Name of column in which te attribute appears by default
    public final String name;

    //  Constructor (it just makes it work ? I think)
    Att_Address(int column, String name) {
        this.column = column;
        this.name = name;
    }
}

/** Placeholder WIP */
class Search_Address {
    //  Attribute reference
    private Att_Customer[] attributes;
    //  Search term
    private String[] term;

    public Search_Address (Att_Customer[] attributes, String[] term) {
        this.attributes = attributes;
        this.term = term;
    }
}

/** A specific exception handler for DB_Address.
 */
class DB_AddressExceptionHandler extends Exception {
    String message;
    public DB_AddressExceptionHandler(String errMessage){ message = errMessage; }
    public String getMessage() { return message; }
}