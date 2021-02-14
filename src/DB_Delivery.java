import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Delivery
{
    //  Connection for creating external attributes as required
    private static JDBC connection;
    //  Handler for storing initialized elements
    private static DB_Handler handler;
    //  Base customer attributes
    private int delivery_id;
    private Date delivery_date;
    private boolean delivery_status;
    // External Attributes
    private DB_Customer customer;
    private DB_Invoice invoice;

    public enum attributes {
        delivery_id(1),
        delivery_date(2),
        delivery_status(3),
        customer(4),
        invoice(5);

        public final int index;

        attributes(int i){this.index = i;}
    }

    public DB_Delivery() { }

    public void getByID(int id) throws DB_DeliveryExceptionHandler {
        try {
            ResultSet rs = connection.getSet("Select * from delivery where delivery_id = " + id);
            if(rs.next())
            {
                delivery_id = rs.getInt(attributes.delivery_id.index);
                delivery_date = rs.getDate(attributes.delivery_date.index);
                delivery_status = rs.getBoolean(attributes.delivery_status.index);
                customer = handler.getCustomer(rs.getInt(attributes.customer.index));
                //invoice = handler.getInvoice(rs.getInt(attributes.invoice.index));
                handler.addDelivery(this);
            }
        }
        catch (JDBCExceptionHandler | SQLException | DB_HandlerExceptionHandler e)
        {
            throw new DB_DeliveryExceptionHandler(e.getMessage());
        }
    }

    public void createDelivery()
    {
        //INSERT INTO delivery VALUES(null,?,?,?,?);

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
    public int getDelivery_id() { return delivery_id; }
    public Date getDelivery_date() { return delivery_date; }
    public void setDelivery_date(Date delivery_date) { this.delivery_date = delivery_date; }
    public boolean isDelivery_status() { return delivery_status; }
    public void setDelivery_status(boolean delivery_status) { this.delivery_status = delivery_status; }
    public DB_Customer getCustomer() { return customer; }
    public void setCustomer(DB_Customer customer) { this.customer = customer; }
    public DB_Invoice getInvoice() { return invoice; }
    public void setInvoice(DB_Invoice invoice) {this.invoice = invoice; }

    //JDBC and Handler Connection

    public static JDBC getConnection() { return connection; }
    public static void setConnection(JDBC connection) { DB_Delivery.connection = connection; }
    public static DB_Handler getHandler() { return handler; }
    public static void setHandler(DB_Handler handler) { DB_Delivery.handler = handler; }
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