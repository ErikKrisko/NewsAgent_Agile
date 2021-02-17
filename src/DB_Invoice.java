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

    public DB_Invoice() throws DB_CustomerExceptionHandler, DB_InvoiceExceptionHandler {
/*
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
        catch (SQLException | JDBCExceptionHandler | DB_CustomerExceptionHandler e) {
            throw new DB_InvoiceExceptionHandler(e.getMessage());
        }*/
        //Delete invoice
        /*
        public static void deleteInvoice() throws SQLException {
         displayinvoice_id();
         System.out.println("Please enter what invoice you want to remove");
            int id = in.nextInt();
         Statement sta = con.createStatement();
            sta.executeUpdate("DELETE From invoice Where invoice_id = '" + id +
             "';");// updates the database with the new student
             displayAllInvoice();
}
          */

        /*
        //Add new invoice
        public static void addNewInvoice() throws SQLException {
        Statement addNewInvoice = con.createStatement();
        addNewinvoice_id.executeUpdate("insert into Invoice( invoice_id, issue_date,
        invoice_status,invoice_total) values ('7','2021-03-02','1','13,49')");
        }
         */
        /*
        // update invoice
        displayAllStudents();
         System.out.println("Please enter what student you want to edit");//it
        asks the user what invoice it wants to edit
        int edit = in.nextInt();// takes in the input that the user enters
        System.out.println("Please enter the new invoice you want to edit");// asks what name the user wants to change
        String invoice_id = in.next();// takes in entry
        Statement sta5 = con.createStatement();//Creates statment and takes the
        sql below and updates what is below
         sta5.executeUpdate("update student set invoice_id ='"+ newinvoice_id + "'
        where invoice = '"+edit+"'" );
        System.out.println("Please enter what you want the new invoice status");//this changes last name of student
        String invoice_status = in.next();
        Statement sta6 = con.createStatement();
        // this updates the student with the choices they have put in
        sta5.executeUpdate("update invoice set invoice_id ='"+ invoice_status + "'
        where studentId = '"+edit+"'" );
        System.out.println("Please enter the new year");
        String year = in.next();
        System.out.println("Please enter the new month");
        String month = in.next();
        System.out.println("Please enter the new day");
         String day = in.next();
        String issue_date = year+"-"+month+"-"+day;
        Statement sta7 = con.createStatement();
         sta7.executeUpdate("update invoice set date ='"+ issue_date + "' where
s       invoice_id = '"+edit+"'" );
        }

    }*/
    public DB_Invoice(String issue_date, Boolean invoice_status, String invoice_total, DB_Customer customer) throws DB_InvoiceExceptionHandler {
            this.issue_date = vEntry( Att_Invoice.issue_date, issue_date);
            this.invoice_status = vEntry( Att_Invoice.invoice_status, invoice_status);
            this.invoice_total = vEntry( Att_Invoice.invoice_total, invoice_total);
            this.customer = customer;
        }

    public DB_Invoice(ResultSet rs) throws DB_InvoiceExceptionHandler {
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
        private String vEntry(Att_Invoice type, String entry) throws DB_InvoiceExceptionHandler {
            switch (type) {
                case issue_date -> {
                    if (entry.length() > 0 && entry.length() <= 20)
                        return entry;
                    else
                        throw new DB_InvoiceExceptionHandler("Invalid issue date.");
                }
                case issue_status -> {
                    if (entry.length() > 0 && entry.length() <= 20)
                        return entry;
                    else
                        throw new DB_InvoiceExceptionHandler("Invalid issue status.");
                }
                case issue_total -> {
                    if (entry.length() == 10)
                        return entry;
                    else
                        throw new DB_InvoiceExceptionHandler("Invalid issue total.");
                }
                default -> throw new DB_CustomerExceptionHandler("Internal error. Unhandled attribute.");
            }
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
