import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;

public class DaoTestInvoice extends DB_InvoiceTest {
    private DAO dao;


    /** TEST 001 getInvoice()
     *  Test for Invoice not found.
     *  ==========
     *  Inputs: int ID = 0
     *          dao.getInvoice(ID);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "No Invoice with invoice_id = " + ID + " not found."
     */
    public void testGetInvoice001(){
        int ID = -1;
        try {
            dao.getInvoice(ID);
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("No invoice with invoice_id = " + ID + " not found.", e.getMessage());
        }
    }

    /** TEST 002 getInvoice()
     *  Test for invoice id = 1
     *  ==========
     *  Inputs: int ID = 1
     *          DB_Invoice test_invoice = dao.getInvoice(ID);
     *  ==========
     *  Expected Outputs:   test_invoice.getInvoice_id() = 1
     *                      test_invoice.getIssue_date() = "2021-02-10"
     *                      test_invoice.Invoice_status() = "1"
     *                      test_invoice.getInvoice_total() = "20.2"
     */
    public void testGetInvoice002() {
        int ID = 1;
        try {
            DB_Invoice test_invoice = dao.getInvoice(ID);
            assertEquals( 1, test_invoice.getInvoice_id());
            assertEquals(Date.valueOf("2021-02-10"), test_invoice.getIssue_date());
            assertEquals( 1, test_invoice.getInvoice_status());
            assertEquals( "20.2", test_invoice.getInvoice_total());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 003 updateInvoice()
     *  Test new Invoice insertion
     *  ==========
     *  Inputs:     DB_Invoice test_invoice = new DB_Invoice(1);
     *  ==========
     *  Expected Outputs:   test_invoice.equals( dao.getInvoice(3)) = true
     */
    public void testUpdateInvoice001() {
        try {
            //  Create new invoice
            DB_Invoice test_invoice = new DB_Invoice();
            //  Issue update
            dao.updateInvoice(test_invoice);
            //  Asses the new ID
            assertEquals( 3, test_invoice.getInvoice_id());
            //  Compare invoice
            assertTrue(test_invoice.equals( dao.getInvoice(3)));
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

//    /** TEST 004 updateInvoice()
//     *  Test new invoice insertion
//     *  ==========
//     *  Inputs:     DB_Invoice test_Invoice = new DB_Invoice("2021-03-01", "true", "11.1", dao.getCustomer(6);
//     *  ==========
//     *  Expected Outputs:   test_Invoice.equals( dao.getInvoice(6)) = true
//     */
//    public void testUpdateInvoice002() {
//        try {
//            //  Create new Invoice
//            DB_Invoice test_Invoice = new DB_Invoice(Date.valueOf("2021-03-01"), true , 11.1, dao.getCustomer_ID(6));
//            //  Issue update
//            dao.updateInvoice(test_Invoice);
//            //  Asses the new ID
//            assertEquals( 6, test_Invoice.getInvoice_id());
//            //  Compare Invoice
//            assertTrue(test_Invoice.equals( dao.getInvoice(6)));
//        } catch (DAOExceptionHandler | DB_InvoiceExceptionHandler | DB_CustomerExceptionHandler e) {
//            e.printStackTrace();
//            fail("Exception not expected.");
//        }
//    }

    /** TEST 005 deleteInvoice()
     *  Test invoice deletion
     *  ==========
     *  Inputs:    DB_Invoice test_invoice = new DB_Invoice();
     *             test_invoice.setInvoice_id( 6);
     *             dao.deleteInvoice( test_invoice);
     *             dao.getInvoice( 6);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "No invoice with Invoice_id = 3 not found."
     */
    public void testDeleteInvoice001() {
        try {
            DB_Invoice test_invoice = new DB_Invoice();
            test_invoice.setInvoice_id( 3);
            dao.deleteInvoice( test_invoice);
            dao.getInvoice( 3);
            fail("Exception expected.");
        } catch (DAOExceptionHandler e) {
            assertEquals( "No invoice with invoice_id = 3 not found.", e.getMessage());
        }
    }

    /** TEST 006 deleteInvoice()
     *  Test Invoice deletion failure for no Invoice found
     *  ==========
     *  Inputs:    DB_Invoice test_Invoice = new DB_Invoice();
     *             test_Invoice.setInvoice_id( 6);
     *             dao.deleteInvoice( test_Invoice);
     *             dao.getInvoice( 6);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "Cannot delete, Invoice with ID = '8', does not exist in the database."
     */
    public void testDeleteInvoice002() {
        try {
            DB_Invoice test_Invoice = new DB_Invoice();
            test_Invoice.setInvoice_id(8);
            dao.deleteInvoice(test_Invoice);
            fail("Exception expected.");
        } catch (DAOExceptionHandler e) {
            assertEquals( "No invoice with Invoice id = 8 found.", e.getMessage());
        }
    }

}


