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
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "100", Invoice total invalid."
     */
    public void testDB_Invoice002() {
        try {
            invoice.validateTotal(100);
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Invoice total invalid.", e.getMessage());
        }
    }

    /** TEST 003
     *  Test invoice total for value below 0
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "-0.01"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "-0.01", Invoice total invalid."
     */
    public void testDB_Invoice003() {
        try {
            invoice.validateTotal(-0.01);
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Invoice total invalid.", e.getMessage());
        }
    }

    /** TEST 004
     *  Test invoice for valid value 0.00 min
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "0.00"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "0.00", Invoice total valid."
     */
    public void testDB_Invoice004() {
        try {
            assertEquals(0.0, invoice.validateTotal(0.00));
        } catch (DB_InvoiceExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

    /** TEST 005
     *  Test invoice for valid value 99.9 max
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "99.9"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "99.9", Invoice total valid."
     */
    public void testDB_Invoice005() {
        try {
            assertEquals(99.9, invoice.validateTotal(99.9));
        } catch (DB_InvoiceExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

    /** TEST 006
     *  Test invoice total for random value below 0
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "-Double.MAX_VALUE"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "-Double.MAX_VALUE", Invoice total invalid."
     */
    public void testDB_Invoice006() {
        try {
            invoice.validateTotal(-Double.MAX_VALUE);
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Invoice total invalid.", e.getMessage());
        }
    }
    /** TEST 007
     *  Test invoice total for value above 100
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "-Double.MAX_VALUE"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "-Double.MAX_VALUE", Invoice total invalid."
     */
    public void testDB_Invoice007() {
        try {
            invoice.validateTotal(Double.MAX_VALUE);
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Invoice total invalid.", e.getMessage());
        }
    }

    /** TEST 008
     *  Test invoice id for valid value 0 min
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "0"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "0", Invoice id valid."
     */
    public void testDB_Invoice008() {
        try {
            assertEquals(0, invoice.validateId(0));
        } catch (DB_InvoiceExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

    /** TEST 009
     *  Test invoice for valid value max
     *  ==========
     *  Inputs: Att_Invoice.invoice_id
     *          "99.9"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "99.9", Invoice total valid."
     */
    public void testDB_Invoice009() {
        try {
            invoice.validateId(Long.MAX_VALUE);
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Invoice id invalid.", e.getMessage());
        }
    }

    /** TEST 0010
     *  Test invoice total for value below 0
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "-1"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "-1", Invoice id invalid."
     */
    public void testDB_Invoice0010() {
        try {
            invoice.validateId(-1);
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Invoice id invalid.", e.getMessage());
        }
    }

    /** TEST 006
     *  Test invoice total for random value below 0
     *  ==========
     *  Inputs: Att_Invoice.invoice_total
     *          "-Double.MAX_VALUE"
     *  ==========
     *  Expected Outputs:   DB_InvoiceExceptionHandler = "Entry = "-Double.MAX_VALUE", Invoice total invalid."
     */
    public void testDB_Invoice0011() {
        try {
            invoice.validateId(-Long.MAX_VALUE);
            fail("Exception expected.");
        } catch (DB_InvoiceExceptionHandler e) {
            assertEquals("Invoice id invalid.", e.getMessage());
        }
    }

}