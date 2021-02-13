import java.sql.Date;
public class DB_Invoice
{
    private int invoice_id;
    private Date issue_date;
    private boolean invoice_status;
    private double invoice_total;
    private DB_Customer customer;

    public DB_Invoice(){
    }

    public int getInvoice_id() { return invoice_id; }
    public Date getIssue_date() { return issue_date; }
    public void setIssue_date(Date issue_date) { this.issue_date = issue_date; }
    public boolean isInvoice_status() { return invoice_status; }
    public void setInvoice_status(boolean invoice_status) { this.invoice_status = invoice_status; }
    public double getInvoice_total() { return invoice_total; }
    public void setInvoice_total(double invoice_total) { this.invoice_total = invoice_total; }
    public DB_Customer getCustomer() { return customer; }
    public void setCustomer(DB_Customer customer) { this.customer = customer; }
}

