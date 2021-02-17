import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Invoice
{
    private int invoice_id;
    private String issue_date;
    private String invoice_status;
    private String invoice_total;
    private DB_Customer customer;

    public DB_Invoice() { }

    public DB_Invoice(String issue_date, Boolean invoice_status, String invoice_total, DB_Customer customer) throws DB_InvoiceExceptionHandler, DB_CustomerExceptionHandler {
            this.issue_date = vEntry( Att_Invoice.issue_date, issue_date);
            //  TYPES ! test your TYPES
//            this.invoice_status = vEntry( Att_Invoice.invoice_status, invoice_status);
            this.invoice_total = vEntry( Att_Invoice.invoice_total, invoice_total);
            this.customer = customer;
        }

    public DB_Invoice(ResultSet rs) throws DB_InvoiceExceptionHandler, DB_CustomerExceptionHandler {
            try {
                invoice_id = rs.getInt( Att_Invoice.invoice_id.column);
                issue_date = rs.getString( Att_Invoice.issue_date.column);
                invoice_status = rs.getString( Att_Invoice.invoice_status.column);
                invoice_total = rs.getString( Att_Invoice.invoice_total.column);
            } catch (SQLException e) {
                throw new DB_CustomerExceptionHandler(e.getMessage());
            }
        }

        //  Validate attributes
        private String vEntry(Att_Invoice type, String entry) throws DB_InvoiceExceptionHandler, DB_CustomerExceptionHandler {
            switch (type) {
                case issue_date -> {
                    if (entry.length() > 0 && entry.length() <= 20)
                        return entry;
                    else
                        throw new DB_InvoiceExceptionHandler("Invalid issue date.");
                }
                case invoice_status -> {
                    if (entry.length() > 0 && entry.length() <= 20)
                        return entry;
                    else
                        throw new DB_InvoiceExceptionHandler("Invalid issue status.");
                }
                case invoice_total -> {
                    if (entry.length() == 10)
                        return entry;
                    else
                        throw new DB_InvoiceExceptionHandler("Invalid issue total.");
                }
                default -> throw new DB_CustomerExceptionHandler("Internal error. Unhandled attribute.");
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
    //  TYPES AGAIN
//    public Date getIssue_date() { return issue_date; }
    //  AND AGAIN
//    public void setIssue_date(Date issue_date) { this.issue_date = issue_date; }
    //  AND AGAIN
//    public boolean isInvoice_status() { return invoice_status; }
    //  DO YOU EVEN KNOW WHAT BOOLEAN IS ?
//    public void setInvoice_status(boolean invoice_status) { this.invoice_status = invoice_status; }
    //  FOR REAL THO ? WHY STORE THEM AS A STRING AND USE SOME OTHER TYPE ?
//    public double getInvoice_total() { return invoice_total; }
    //  IF YOU STORE A STRING TAKE A STRING... JESSUS.
//    public void setInvoice_total(double invoice_total) { this.invoice_total = invoice_total; }
    public DB_Customer getCustomer() { return customer; }
    public void setCustomer(DB_Customer customer) { this.customer = customer; }
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
