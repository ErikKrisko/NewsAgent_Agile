import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Address {
    //  Base address attributes
    private int address_id = 0;
    private String full_address, area_code, eir_code;

    /** Blank constructor */
    public DB_Address() { }

    public DB_Address(String full_address, String area_code, String eir_code) throws DB_AddressExceptionHandler {
        this.full_address = vEntry( Att_Address.full_address, full_address);
        this.area_code = vEntry( Att_Address.area_code, area_code);
        this.eir_code = vEntry( Att_Address.eir_code, eir_code);
    }

    public DB_Address(ResultSet rs) throws DB_AddressExceptionHandler {
        try {
            address_id = rs.getInt( Att_Address.address_id.column);
            full_address = rs.getString( Att_Address.full_address.column);
            area_code = rs.getString( Att_Address.area_code.column);
            eir_code = rs.getString( Att_Address.eir_code.column);
        }
        catch (SQLException e){
            throw new DB_AddressExceptionHandler(e.getMessage());
        }
    }

    private String vEntry(Att_Address attribute, String entry) throws DB_AddressExceptionHandler {
        switch (attribute) {
            case full_address -> {
                if (entry.length() > 0 && entry.length() <= 50)
                    return entry;
                else
                    throw new DB_AddressExceptionHandler( "Invalid full_address.");
            }
            case area_code -> {
                if (entry.length() > 0 && entry.length() <= 10)
                    return entry;
                else
                    throw new DB_AddressExceptionHandler( "Invalid area_code.");
            }
            case eir_code -> {
                if (entry.length() > 0 && entry.length() <= 8)
                    return entry;
                else
                    throw new DB_AddressExceptionHandler( "Invalid eir_code.");
            }
            default -> {
                throw new DB_AddressExceptionHandler("Internal error. Unhandled attribute.");
            }
        }
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
    //  AUTO GENERATED getters and setters
    public int getAddress_id() {    return address_id; }
    public String getFull_address() {   return full_address; }
    public String getArea_code() {  return area_code; }
    public String getEir_code() {   return eir_code; }
    public void setFull_address(String full_address) throws DB_AddressExceptionHandler {  this.full_address = vEntry( Att_Address.full_address, full_address); }
    public void setArea_code(String area_code) throws DB_AddressExceptionHandler {    this.area_code = vEntry( Att_Address.area_code, area_code); }
    public void setEir_code(String eir_code) throws DB_AddressExceptionHandler {  this.eir_code = vEntry( Att_Address.eir_code, eir_code); }
}

enum Att_Address {
    address_id(1, "address_id"),
    full_address(2, "full_address"),
    area_code(3, "area_code"),
    eir_code(4, "eir_code");

    public final int column;
    public final String name;

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

class DB_AddressExceptionHandler extends Exception {
    String message;

    public DB_AddressExceptionHandler(String errMessage){ message = errMessage; }

    public String getMessage() { return message; }
}