import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Customer {
    private int customer_id;
    private String first_name, last_name, phone_no;
    private DB_Address address;

    public DB_Customer() {}

    public void getByID(JDBC con, int id) throws DB_CustomerExceptionHandler {
        try {
            ResultSet rs = con.getSet("Select * from customer where customer_id = " + id);
            if (rs.next()) {
                customer_id = rs.getInt(1);
                first_name = rs.getString(2);
                last_name = rs.getString(3);
                phone_no = rs.getString(4);
                address = new DB_Address();
                address.getByID(con, rs.getInt(5));
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