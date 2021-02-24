import junit.framework.TestCase;

import static org.junit.Assert.*;

public class DB_InvoiceTest extends TestCase {
    DB_Invoice invoice = new DB_Invoice();

    /** TEST 001
     *  Test first_name, last_name, phone_no for an empty string / too short entry
     *  ==========
     *  Inputs: Att_Invoice.invoice_id
     *          ""
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "", cannot be an empty String."
     */
    public void testDB_Invoice001() {
        try {
            invoice.validateEntry( Att_Invoice.invoice_id, "");
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Entry = \"\", cannot be an empty String.", e.getMessage());
        }
    }

    /** TEST 002
     *  Test issue_date for too long entry.
     *  ==========
     *  Inputs: Att_Invoice.issue_date
     *          "2018-09-10"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "2018-09-10", is too long."
     */
    public void testDB_Invoice002() {
        try {
            invoice.validateEntry( Att_Invoice.issue_date, "2018-09-10");
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Entry = \"2018-09-10\", is too long.", e.getMessage());
        }
    }

    /** TEST 004
     *  Test issue date for contained letters
     *  ==========
     *  Inputs: Att_Invoice.issue_date
     *          "B1ll"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "wert/er/yt", cannot contain letters."
     */
    public void testDB_Invoice003() {
        try {
            invoice.validateEntry( Att_Invoice.issue_date, "wert/er/yt");
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Entry = \"wert/er/yt\", cannot contain letters.", e.getMessage());
        }
    }

    /** TEST 004
     *  Test invoice status for errors when inputting something thats not 0 or 1
     *  ==========
     *  Inputs: Att_Invoice.first_name
     *          "2"
     *  ==========
     *  Expected Outputs:   "error please say 0 or 1"
     */
    public void testDB_Invoice004() {
        try {
            assertEquals("", invoice.validateEntry( Att_Invoice.invoice_status, "2"));
        } catch (DB_InvoiceExceptionHandler e) {
            fail("error please say 0 or 1.");
        }
    }



    /** TEST 005
     *  Test invoice total for too long entry
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "100000000"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "100000000", has be below the length of 10000."
     */
    public void testDB_Invoice007() {
        try {
            invoice.validateEntry( Att_Invoice.invoice_total, "100000000");
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Entry = \"100000000\", has be below the length of 10000.", e.getMessage());
        }
    }

    /** TEST 005
     *  Test phone_no for too long entry
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "-1"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "0", has to be of length 0."
     */
    public void testDB_Invoice007() {
        try {
            invoice.validateEntry( Att_Invoice.invoice_total, "0");
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Entry = \"-1\", has be above 0.", e.getMessage());
        }
    }




}