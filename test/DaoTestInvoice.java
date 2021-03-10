import junit.framework.TestCase;

import java.util.ArrayList;

public class DaoTestInvoice extends DB_InvoiceTest {
    private DAO dao;

    /**
     * Initialize test environment.
     * ==========
     * Inputs: JDBC initialization and script execution
     * dao initialization
     * ==========
     * Expected Outputs:   None
     */
    public DaoTestInvoice() {
        try {
            //  Reset Database
            JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
            connection.executeScript("NewsAgent_Database.sql");
            connection.setDbName("newsagent");
            connection.executeScript("NewsAgent_Data.sql");
            connection.close();
            //  Initialize DAO
            dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");
        } catch (DAOExceptionHandler | JDBCExceptionHandler e) {
            e.printStackTrace();
            fail("Test initialization failed.");
        }
    }

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
    /** TEST 002 updateInvoice()
     *  Test new Invoice insertion
     *  ==========
     *  Inputs:     DB_Customer test_invoice = new DB_Invoice(1);
     *  ==========
     *  Expected Outputs:   test_invoice.equals( dao.getInvoice(3)) = true
     */
    public void testUpdateInvoice001() {
        try {
            //  Create new invoice
            DB_Invoice test_invoice = new DB_Invoice(1);
            //  Issue update
            dao.updateInvoice(test_invoice);
            //  Asses the new ID
            assertEquals( 3, test_invoice.getInvoice_id());
            //  Compare customer
            assertTrue(test_invoice.equals( dao.getInvoice(3)));
        } catch (DAOExceptionHandler | DB_InvoiceExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }
    /** TEST 03 deleteInvoice()
     *  Test invoice deletion
     *  ==========
     *  Inputs:    DB_Customer test_invoice = new DB_Invoice();
     *             test_invoice.setInvoice_id( 6);
     *             dao.deleteInvoice( test_invoice);
     *             dao.getInvoice( 6);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "No customer with Invoice_id = 3 not found."
     */
    public void testDeleteCustomer001() {
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

}


