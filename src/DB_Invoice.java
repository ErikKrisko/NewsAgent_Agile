import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Invoice
{
    private long invoice_id = 0;
    private Date issue_date;
    private boolean invoice_status;
    private double invoice_total;
    private DB_Customer customer;

    public DB_Invoice() { }

    public DB_Invoice(Date issue_date, boolean invoice_status, double invoice_total, DB_Customer customer) throws DB_InvoiceExceptionHandler, DB_CustomerExceptionHandler {
        this.issue_date = validateDate( issue_date);
        this.invoice_status = invoice_status;
        this.invoice_total = validateTotal( invoice_total);
        this.customer = customer;
    }

    public DB_Invoice(ResultSet rs) throws DB_InvoiceExceptionHandler, DB_CustomerExceptionHandler {
        try {
            invoice_id = rs.getInt( Att_Invoice.invoice_id.column);
            issue_date = rs.getDate( Att_Invoice.issue_date.column);
            invoice_status = rs.getBoolean( Att_Invoice.invoice_status.column);
            invoice_total = rs.getDouble( Att_Invoice.invoice_total.column);
        } catch (SQLException e) {
            throw new DB_CustomerExceptionHandler(e.getMessage());
        }
    }
    //  Validate attributes

    public double validateTotal(double total) throws DB_InvoiceExceptionHandler {
        if (total >= 100 || total < 0) {

            throw new DB_InvoiceExceptionHandler("Invoice total cant more than 99.99.");
        }
        else {
            return total;
        }
    }


    public Date validateDate(Date entry) throws DB_InvoiceExceptionHandler
    {
        if (entry.after(new Date(System.currentTimeMillis() - 86400000))) //86400000 one day in milli seconds
        {
            return entry;
        }
        else
        {
            throw new DB_InvoiceExceptionHandler("Invalid invoice date.");
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

    public long getInvoice_id() { return invoice_id; }
    public void setInvoice_total(double invoice_total) { this.invoice_total = invoice_total; }
    public DB_Customer getCustomer() { return customer; }
    public void setCustomer(DB_Customer customer) { this.customer = customer; }

    public void setInvoice_id(long invoice_id) {
        this.invoice_id = invoice_id;
    }

    public Date getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(Date issue_date) {
        this.issue_date = issue_date;
    }

    public boolean isInvoice_status() {
        return invoice_status;
    }

    public int getInvoice_status() {
        if (invoice_status)
            return 1;
        else
            return 0;
    }

    public void setInvoice_status(boolean invoice_status) {
        this.invoice_status = invoice_status;
    }

    public double getInvoice_total() {
        return invoice_total;
    }
}

/** List of customer attributes */
enum Att_Invoice {
    //  Customer table attributes
    invoice_id(1, "invoice_id"),
    issue_date(2, "issue_date"),
    invoice_status(3, "invoice_status"),
    invoice_total(4, "invoice_total"),
    customer(5, "customer");

    //  Column in which the given attribute appears by default
    public final int column;
    //  Name of column in which te attribute appears by default
    public final String name;

    Att_Invoice(int column, String columnName) {
        this.column = column;
        this.name = columnName;
    }
}
/** Search object for Invoice WIP! */
class Search_Invoice {
    private Att_Invoice attribute;
    private String term;
    private boolean strong;

    public Search_Invoice(Att_Invoice  attribute, String term, boolean strong) {
        this.attribute = attribute;
        this.term = term;
        this.strong = strong;
    }

    //  AUTO GENERATED getters and setters
    public Att_Invoice  getAttribute() { return attribute; }
    public void setAttribute(Att_Invoice  attribute) { this.attribute = attribute; }
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    public boolean isStrong() { return strong; }
    public void setStrong(boolean strong) { this.strong = strong; }
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
