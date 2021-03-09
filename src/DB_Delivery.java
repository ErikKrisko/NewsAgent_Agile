import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Delivery
{
    //  Base customer attributes
    private long delivery_id;
    private Date delivery_date;
    private boolean delivery_status;
    // External Attributes
    private DB_Customer customer;
    private DB_Invoice invoice;


    public DB_Delivery() { }

    public DB_Delivery(long delivery_id,Date delivery_date, boolean delivery_status, DB_Customer customer, DB_Invoice invoice) throws DB_DeliveryExceptionHandler {
        this.delivery_id = validateDevID(delivery_id);
        this.delivery_date = validateDevDate(delivery_date);
        this.delivery_status = delivery_status;
        this.customer = customer;
        this.invoice = invoice;
    }

    public DB_Delivery(ResultSet rs) throws DB_DeliveryExceptionHandler
    {
        try
        {
            delivery_id = rs.getLong(att_delivery.delivery_id.column);
            delivery_date = rs.getDate(att_delivery.delivery_date.column);
            delivery_status = rs.getBoolean(att_delivery.delivery_status.column);
        }
        catch (SQLException e)
        {
            throw new DB_DeliveryExceptionHandler(e.getMessage());
        }
    }

    /**Validate Attributes*/
    public Date validateDevDate(Date entry) throws DB_DeliveryExceptionHandler
    {
        if(entry != null)
        {
            if (entry.after(new Date(System.currentTimeMillis() - 24*60*60*1000))) //24*60*60*1000 one day in milli seconds. hours*mins*secs*millisecs 24 hours
            {
                return entry;
            }
            else
            {
                throw new DB_DeliveryExceptionHandler("delivery_date is older than a day");
            }
        }
        else
        {
            throw new DB_DeliveryExceptionHandler("delivery_date is null");
        }

    }
    public long validateDevID(long entry) throws DB_DeliveryExceptionHandler {
        if(entry >= 0)
        {
            return entry;
        }
        else
        {
            throw new DB_DeliveryExceptionHandler("delivery_id has to be greater than or equal to 0");
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

    public long getDelivery_id() { return delivery_id; }
    public Date getDelivery_date() { return delivery_date; }
    public boolean isDelivery_status() { return delivery_status; }
    public DB_Customer getCustomer() { return customer; }
    public DB_Invoice getInvoice() { return invoice; }

    public int getDelivery_status() {
        if (delivery_status){
            return 1;
        }
        else{
            return 0;
        }
    }

    public void setDelivery_id(long delivery_id) { this.delivery_id = delivery_id; }
    public void setDelivery_date(Date delivery_date) throws DB_DeliveryExceptionHandler { this.delivery_date = validateDevDate(delivery_date); }
    public void setDelivery_status(boolean delivery_status) { this.delivery_status = delivery_status; }
    public void setCustomer(DB_Customer customer) { this.customer = customer; }
    public void setInvoice(DB_Invoice invoice) { this.invoice = invoice; }

    public void replaceDelivery_date(){this.delivery_date = delivery_date;}

}

enum att_delivery {
    delivery_id(1, "delivery_id"),
    delivery_date(2, "delivery_date"),
    delivery_status(3 , "delivery_status"),
    customer(4, "customer_id"),
    invoice(5, "invoice_id");

    public final int column;
    public final String columnName;

    att_delivery(int column, String columnName)
    {
        this.column = column;
        this.columnName = columnName;
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


