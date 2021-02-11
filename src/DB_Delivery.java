import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Delivery
{
    private int delivery_id;
    private Date delivery_date;
    private boolean delivery_status;

    private DB_Customer customer;
    private DB_Invoice invoice;

    public DB_Delivery()
    {

    }

    public void getByID(JDBC con, int id) throws DB_DeliveryExceptionHandler {
        try {
            ResultSet rs = con.getSet("Select * from delivery where delivery_id = " + id);
            if(rs.next())
            {
                delivery_id = rs.getInt(1);
                delivery_date = rs.getDate(2);
                delivery_status = rs.getBoolean(3);
                customer = new DB_Customer();
                customer.getByID(con, rs.getInt(4));
//                invoice = new DB_Invoice();
//                invoice.getByID(con, rs.getInt(5));
            }
        }
        catch (JDBCExceptionHandler | SQLException | DB_CustomerExceptionHandler e)
        {
            throw new DB_DeliveryExceptionHandler(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "DB_Delivery{" +
                "delivery_id=" + delivery_id +
                ", delivery_date=" + delivery_date +
                ", delivery_status=" + delivery_status +
                ", customer=" + customer +
                ", invoice=" + invoice +
                '}';
    }

    //AUTO GENERATED GETTERS AND SETTERS
    public int getDelivery_id() {
        return delivery_id;
    }
    public Date getDelivery_date() {
        return delivery_date;
    }
    public void setDelivery_date(Date delivery_date) {
        this.delivery_date = delivery_date;
    }
    public boolean isDelivery_status() {
        return delivery_status;
    }
    public void setDelivery_status(boolean delivery_status) { this.delivery_status = delivery_status; }
    public DB_Customer getCustomer() { return customer; }
    public void setCustomer(DB_Customer customer) {
        this.customer = customer;
    }
    public DB_Invoice getInvoice() {
        return invoice;
    }
    public void setInvoice(DB_Invoice invoice) {
        this.invoice = invoice;
    }
}


class DB_DeliveryExceptionHandler extends Exception
{
    String message;

    public DB_DeliveryExceptionHandler(String errMessage){
        message = errMessage;
    }

    public String getMessage() {
        return message;
    }
}