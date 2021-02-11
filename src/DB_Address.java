import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Address {
    private int address_id;
    private String full_address, area_code, eir_code;

    public DB_Address() {

    }

    public void getByID(JDBC con, int id) throws DB_AddressExceptionHandler{
        try {
            ResultSet rs = con.getSet("Select * from address where address_id = " + id);
            rs.next();
            address_id = rs.getInt(1);
            full_address = rs.getString(2);
            area_code = rs.getString(3);
            eir_code = rs.getString(4);
        }
        catch (JDBCExceptionHandler | SQLException e) {
            throw new DB_AddressExceptionHandler(e.getMessage());
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