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
             "';");// updates the dadatabse with the new student
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
        asks the user what students it wants to edit
        int edit = in.nextInt();// takes in the input that the user enters
        System.out.println("Please enter the new first name you want to
        change");// asks what name the user wants to change
        String newfname = in.next();// takes in entry
        Statement sta5 = con.createStatement();//Creates statment and takes the
        sql below and updates what is below
         sta5.executeUpdate("update student set firstname ='"+ newfname + "'
        where studentId = '"+edit+"'" );
        System.out.println("Please enter the new last name you want to
        change");//this changes last name of student
        String newlname = in.next();
        Statement sta6 = con.createStatement();
        // this updates the student with the choices they have put i
        sta5.executeUpdate("update student set lastname ='"+ newlname + "'
        where studentId = '"+edit+"'" );
         System.out.println("Please enter the new day");
         String day = in.next();
        System.out.println("Please enter the new month");
        String month = in.next();
        System.out.println("Please enter the new year");
        String year = in.next();
        String lastdob = year+"-"+month+"-"+day;
        Statement sta7 = con.createStatement();
         sta7.executeUpdate("update student set dob ='"+ lastdob + "' where
s       studentId = '"+edit+"'" );
        }

    }*/

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
