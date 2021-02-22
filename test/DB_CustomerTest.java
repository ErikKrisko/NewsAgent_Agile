import junit.framework.TestCase;

import static org.junit.Assert.*;

public class DB_CustomerTest extends TestCase {
    DB_Customer customer = new DB_Customer();

    /** TEST 001
     *  Test first_name, last_name, phone_no for an empty string / too short entry
     *  ==========
     *  Inputs: Att_Customer.first_name
     *          ""
     *  ==========
     *  Expected Outputs:   DB_CustomerExceptionHandler = "Entry = "", cannot be an empty String."
     */
    public void testDB_Customer001() {
        try {
            customer.validateEntry( Att_Customer.first_name, "");
            fail("Exception expected.");
        } catch (DB_CustomerExceptionHandler e) {
            assertEquals("Entry = \"\", cannot be an empty String.", e.getMessage());
        }
    }

    /** TEST 002
     *  Test first_name, last_name for too long entry.
     *  ==========
     *  Inputs: Att_Customer.first_name
     *          "Joebilljollyrobbertson"
     *  ==========
     *  Expected Outputs:   DB_CustomerExceptionHandler = "Entry = "Joebilljollyrobbertson", is too long."
     */
    public void testDB_Customer002() {
        try {
            customer.validateEntry( Att_Customer.first_name, "Joebilljollyrobbertson");
            fail("Exception expected.");
        } catch (DB_CustomerExceptionHandler e) {
            assertEquals("Entry = \"Joebilljollyrobbertson\", is too long.", e.getMessage());
        }
    }

    /** TEST 004
     *  Test first_name, last_name for contained numbers
     *  ==========
     *  Inputs: Att_Customer.first_name
     *          "B1ll"
     *  ==========
     *  Expected Outputs:   DB_CustomerExceptionHandler = "Entry = "B1ll", cannot contain numbers."
     */
    public void testDB_Customer003() {
        try {
            customer.validateEntry( Att_Customer.first_name, "B1ll");
            fail("Exception expected.");
        } catch (DB_CustomerExceptionHandler e) {
            assertEquals("Entry = \"B1ll\", cannot contain numbers.", e.getMessage());
        }
    }

    /** TEST 004
     *  Test first_name, last_name for shortest possible entry
     *  ==========
     *  Inputs: Att_Customer.first_name
     *          "J"
     *  ==========
     *  Expected Outputs:   "J"
     */
    public void testDB_Customer004() {
        try {
            assertEquals("J", customer.validateEntry( Att_Customer.first_name, "J"));
        } catch (DB_CustomerExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

    /** TEST 005
     *  Test first_name, last_name for longest possible entry
     *  ==========
     *  Inputs: Att_Customer.first_name
     *          "Billandersonwillfred"
     *  ==========
     *  Expected Outputs:   "Billandersonwillfred"
     */
    public void testDB_Customer005() {
        try {
            assertEquals("Billandersonwillfred", customer.validateEntry( Att_Customer.first_name, "Billandersonwillfred"));
        } catch (DB_CustomerExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

    /** TEST 006
     *  Test phone_no for too short entry
     *  ==========
     *  Inputs: Att_Customer.phone_no
     *          "123456789"
     *  ==========
     *  Expected Outputs:   DB_CustomerExceptionHandler = "Entry = "123456789", has to be of length 10."
     */
    public void testDB_Customer006() {
        try {
            customer.validateEntry( Att_Customer.phone_no, "123456789");
            fail("Exception expected.");
        } catch (DB_CustomerExceptionHandler e) {
            assertEquals("Entry = \"123456789\", has to be of length 10.", e.getMessage());
        }
    }

    /** TEST 007
     *  Test phone_no for too long entry
     *  ==========
     *  Inputs: Att_Customer.phone_no
     *          "12345678901"
     *  ==========
     *  Expected Outputs:   DB_CustomerExceptionHandler = "Entry = "12345678901", has to be of length 10."
     */
    public void testDB_Customer007() {
        try {
            customer.validateEntry( Att_Customer.phone_no, "12345678901");
            fail("Exception expected.");
        } catch (DB_CustomerExceptionHandler e) {
            assertEquals("Entry = \"12345678901\", has to be of length 10.", e.getMessage());
        }
    }


    /** TEST 008
     *  Test phone_no for containing letters
     *  ==========
     *  Inputs: Att_Customer.phone_no
     *          "i234567890"
     *  ==========
     *  Expected Outputs:   DB_CustomerExceptionHandler = "Entry = "i234567890", cannot contain letters."
     */
    public void testDB_Customer008() {
        try {
            customer.validateEntry( Att_Customer.phone_no, "i234567890");
            fail("Exception expected.");
        } catch (DB_CustomerExceptionHandler e) {
            assertEquals("Entry = \"i234567890\", cannot contain letters.", e.getMessage());
        }
    }

    /** TEST 008
     *  Test phone_no for valid entry
     *  ==========
     *  Inputs: Att_Customer.phone_no
     *          "1234567890"
     *  ==========
     *  Expected Outputs:   "1234567890"
     */
    public void testDB_Customer009() {
        try {
            assertEquals( "1234567890", customer.validateEntry( Att_Customer.phone_no, "1234567890"));
        } catch (DB_CustomerExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 010
     *  Test for unhandled attribute
     *  ==========
     *  Inputs: Att_Customer.address
     *          "D"
     *  ==========
     *  Expected Outputs:   DB_CustomerExceptionHandler = "Internal error. Unhandled attribute."
     */
    public void testDB_Customer010() {
        try {
            customer.validateEntry( Att_Customer.address, "D");
            fail("Exception expected.");
        } catch (DB_CustomerExceptionHandler e) {
            assertEquals("Internal error. Unhandled attribute.", e.getMessage());
        }
    }

    /** Test 011
     *  Test for customer constructor (and getters)
     *  ==========
     *  Inputs: new DB_Customer( "Jo", "Bar", "1234567890", new DB_Address())
     *  ==========
     *  Expected Outputs:   getFirst_name() = "Jo"
     *                      getLast_name() = "Bar"
     *                      getPhone_no() = "1234567890"
     */
    public void testDB_Customer011() {
        try {
            DB_Customer testCustomer = new DB_Customer(0, "Jo", "Bar", "1234567890", new DB_Address());
            assertEquals( "Jo", testCustomer.getFirst_name());
            assertEquals( "Bar", testCustomer.getLast_name());
            assertEquals( "1234567890", testCustomer.getPhone_no());
        } catch (DB_CustomerExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

}