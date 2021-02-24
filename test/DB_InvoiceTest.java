import junit.framework.TestCase;

import java.sql.Date;

import static org.junit.Assert.*;

public class DB_InvoiceTest extends TestCase {
    DB_Invoice invoice = new DB_Invoice();


    /** TEST 001
     *  Test invoice_date for empty
     *  ==========
     *  Inputs: issue_date
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Invalid issue_date."
     */
    public void testDB_invoice001() {
        try {
            invoice.validateDate(Date.valueOf(""));
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Invalid issue date.", e.getMessage());
        }
    }


    /** TEST 002
     *  Test invoice total for value over 100
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "100"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "100", Invoice total cant more than 99.99."
     */
    public void testDB_Invoice002() {
        try {
            invoice.validateTotal(100);
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Entry = \"100\",Invoice total cant more than 99.99.", e.getMessage());
        }
    }

    /** TEST 003
     *  Test invoice total for too long entry
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "0.01"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "0.01", has be below the length of ."
     */
    public void testDB_Invoice003() {
        try {
            invoice.validateTotal(0.01);
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Entry = \"0.01\", has be above the length of 1.", e.getMessage());
        }
    }


}