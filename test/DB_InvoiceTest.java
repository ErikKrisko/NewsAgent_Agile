import junit.framework.TestCase;

import java.sql.Date;

import static org.junit.Assert.*;

public class DB_InvoiceTest extends TestCase {
    DB_Invoice invoice = new DB_Invoice();


    /** TEST 001
     *  Test invoice_date for empty
     *  ==========
     *  Inputs: att_date
     *  ==========
     *  Expected Outputs:   DB_DeliveryExceptionHandler = "Invalid delivery_date."
     */
    public void testDB_invoice001() {
        try {
            invoice.validateDate(Date.valueOf(""));
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Invalid invoice date.", e.getMessage());
        }
    }


    /** TEST 002
     *  Test invoice total for too long entry
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "100"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "100", has be below the length of 10000."
     */
    public void testDB_Invoice002() {
        try {
            invoice.validateTotal(100);
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Entry = \"100\", has be below the length of 100.", e.getMessage());
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
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Entry = \"0.01\", has be above the length of 1.", e.getMessage());
        }
    }


}