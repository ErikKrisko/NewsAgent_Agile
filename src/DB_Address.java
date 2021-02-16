import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Address {
    //  Connection for creating external attributes as required
    private static JDBC connection;
    //  Handler for storing initialized elements
    private static DB_Handler handler;
    //  Base address attributes
    private int address_id = 0;
    private String full_address, area_code, eir_code;

    /** Blank constructor */
    public DB_Address() { }

    public DB_Address(String full_address, String area_code, String eir_code) {
        this.full_address = full_address;
        this.area_code = area_code;
        this.eir_code = eir_code;
    }

    public DB_Address(ResultSet rs) {
        try {
            address_id = rs.getInt( Att_Address.address_id.column);
            full_address = rs.getString( Att_Address.full_address.column);
            area_code = rs.getString( Att_Address.area_code.column);
            eir_code = rs.getString( Att_Address.eir_code.column);
        }
        catch (SQLException e){

        }
    }

    @Override
    public String toString() {
        return "DB_Address{" +
                "address_id=" + address_id +
                ", full_address='" + full_address + '\'' +
                ", area_code='" + area_code + '\'' +
                ", eir_code='" + eir_code + '\'' +
                '}';
    }

    //  AUTO GENERATED getters and setters
    public int getAddress_id() {    return address_id; }
    public String getFull_address() {   return full_address; }
    public void setFull_address(String full_address) {  this.full_address = full_address; }
    public String getArea_code() {  return area_code; }
    public void setArea_code(String area_code) {    this.area_code = area_code; }
    public String getEir_code() {   return eir_code; }
    public void setEir_code(String eir_code) {  this.eir_code = eir_code; }
    //  JDBC connection
    public static JDBC getConnection() {    return connection; }
    public static void setConnection(JDBC connection) { DB_Address.connection = connection; }
    //  Handler
    public static DB_Handler getHandler() { return handler; }
    public static void setHandler(DB_Handler handler) { DB_Address.handler = handler; }
}

enum Att_Address {
    address_id(1),
    full_address(2),
    area_code(3),
    eir_code(4);

    public final int column;

    Att_Address(int i) { this.column = i; }
}

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

class DB_AddressExceptionHandler extends Exception {
    String message;

    public DB_AddressExceptionHandler(String errMessage){
        message = errMessage;
    }

    public String getMessage() {
        return message;
    }
}