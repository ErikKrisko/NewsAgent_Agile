import java.sql.Date;

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

    public void setDelivery_status(boolean delivery_status) {
        this.delivery_status = delivery_status;
    }

    public DB_Customer getCustomer() {
        return customer;
    }

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
