import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Invoice
{
    private int invoice_id;
    private Date issue_date;
    private boolean invoice_status;
    private double invoice_total;
    private DB_Customer customer;

    public DB_Invoice(){
    }

    public void getByID(JDBC con, int id) throws DB_InvoiceExceptionHandler {
        try {
            ResultSet rs = con.getSet("Select * from invoice where invoice_id = " + id);
            if (rs.next()) {
                invoice_id = rs.getInt(1);
                issue_date = rs.getDate(2);
                invoice_status = rs.getBoolean(3);
                invoice_total = rs.getDouble(4);
//                customer = new DB_Customer();
//                customer.getByID(con, rs.getInt(5));
            }
        }
        catch (SQLException | JDBCExceptionHandler /*| DB_CustomerExceptionHandler*/ e) {
            throw new DB_InvoiceExceptionHandler(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "DB_Invoice{" +
                "invoice_id=" + invoice_id +
                ", issue_date=" + issue_date +
                ", invoice_status=" + invoice_status +
                ", invoice_total=" + invoice_total +
                ", customer=" + customer +
                '}';
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
class DB_InvoiceExceptionHandler extends Exception {
    String message;

    public DB_InvoiceExceptionHandler(String errMessage){
        message = errMessage;
    }

    public String getMessage() {
        return message;
    }
}
