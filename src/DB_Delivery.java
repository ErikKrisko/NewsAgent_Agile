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

    public DB_Delivery(Date delivery_date, boolean delivery_status, DB_Customer customer, DB_Invoice invoice)
    {
        this.delivery_date = delivery_date;
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
    private Date vDevDate(Date entry) throws DB_DeliveryExceptionHandler
    {
        if (entry.after(new Date(System.currentTimeMillis() - 86400000))) //86400000 one day in milli seconds
        {
            return entry;
        }
        else
        {
            throw new DB_DeliveryExceptionHandler("Invalid delivery_date.");
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

    public Long getDelivery_id() { return delivery_id; }
    public Date getDelivery_date() { return delivery_date; }
    public boolean isDelivery_status() { return delivery_status; }
    public DB_Customer getCustomer() { return customer; }
    public DB_Invoice getInvoice() { return invoice; }
    //  Date it or string it
//    public void setDelivery_date(Date delivery_date) throws DB_DeliveryExceptionHandler { this.delivery_date = vEntry(att_delivery.delivery_date, delivery_date); }

    public void setDelivery_id(long delivery_id) { this.delivery_id = delivery_id; }
    public void setDelivery_status(boolean delivery_status) { this.delivery_status = delivery_status; }
    public void setCustomer(DB_Customer customer) { this.customer = customer; }
    public void setInvoice(DB_Invoice invoice) { this.invoice = invoice; }
}

enum att_delivery {
    delivery_id(1, "delivery_id"),
    delivery_date(2, "delivery_date"),
    delivery_status(3 , "delivery_status"),
    customer(4, "customer_id"),
    invoice(5, "invoice_id");

    public final int column;
    public final String columnName;

    att_delivery(int c, String cName){this.column = c; this.columnName = cName;}
}

class search_Delivery {
    private att_delivery attribute;
    private String term;
    private boolean strong;

    public search_Delivery(att_delivery attribute, String term, boolean strong) {
        this.attribute = attribute;
        this.term = term;
        this.strong = strong;
    }

    //  AUTO GENERATED getters and setters
    public att_delivery getAttribute() { return attribute; }
    public void setAttribute(att_delivery attribute) { this.attribute = attribute; }
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    public boolean isStrong() { return strong; }
    public void setStrong(boolean strong) { this.strong = strong; }
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



/*
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
*/