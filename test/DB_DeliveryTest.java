import junit.framework.TestCase;
import java.sql.Date;

public class DB_DeliveryTest extends TestCase
{
    DB_Delivery delivery = new DB_Delivery();

    //Test Delivery Date
    /** TEST 001
     *  Test delivery_date for null
     *  ==========
     *  Inputs: ""
     *  ==========
     *  Expected Outputs:   DB_DeliveryExceptionHandler = "Invalid delivery_date."
     */
    public void testDB_Delivery001() {
        try {
            delivery.validateDevDate(null);
            fail("Exception expected.");
        } catch (DB_DeliveryExceptionHandler e) {
            assertEquals("delivery_date is null", e.getMessage());
        }
    }
    /** TEST 002
     *  Test delivery_date is older than just a day
     *  ==========
     *  Inputs: new Date(System.currentTimeMillis() - 24*60*60*1000)
     *  ==========
     *  Expected Outputs:   DB_DeliveryExceptionHandler = "delivery_date is older than a day"
     */
    public void testDB_Delivery002() {
        try {
            assertEquals(new Date(System.currentTimeMillis() - 24*60*60*1000),delivery.validateDevDate(new Date(System.currentTimeMillis() - 24*60*60*1000)));
            fail("Exception expected.");
        } catch (DB_DeliveryExceptionHandler e) {
            assertEquals("delivery_date is older than a day", e.getMessage());
        }
    }
    /** TEST 003
     *  Test delivery_date is older than 3 days
     *  ==========
     *  Inputs: new Date(System.currentTimeMillis() - 3*24*60*60*1000)
     *  ==========
     *  Expected Outputs:   DB_DeliveryExceptionHandler = "delivery_date is older than a day"
     */
    public void testDB_Delivery003() {
        try {
            assertEquals(new Date(System.currentTimeMillis() - 3*24*60*60*1000),delivery.validateDevDate(new Date(System.currentTimeMillis() - 3*24*60*60*1000)));
            fail("Exception expected.");
        } catch (DB_DeliveryExceptionHandler e) {
            assertEquals("delivery_date is older than a day", e.getMessage());
        }
    }
    /** TEST 004
     *  Test delivery_date is now
     *  ==========
     *  Inputs: new Date(System.currentTimeMillis())
     *  ==========
     *  Expected Outputs: new Date(System.currentTimeMillis())
     */
    public void testDB_Delivery004() {
        try {
            assertEquals(new Date(System.currentTimeMillis()),delivery.validateDevDate(new Date(System.currentTimeMillis())));
        } catch (DB_DeliveryExceptionHandler e){
            fail("Exception not expected.");
        }
    }
    /** TEST 005
     *  Test delivery_date is exactly one day old
     *  ==========
     *  Inputs: new Date(System.currentTimeMillis() - 24*60*60*999)"
     *  ==========
     *  Expected Outputs: new Date(System.currentTimeMillis()- 24*60*60*999) "
     */
    public void testDB_Delivery005() {
        try {
            assertEquals(new Date(System.currentTimeMillis() - 24*60*60*999),delivery.validateDevDate(new Date(System.currentTimeMillis() - 24*60*60*999)));
        } catch (DB_DeliveryExceptionHandler e){
            fail("Exception not expected.");
        }
    }
    /** TEST 006
     *  Test delivery_date is exactly one day prior to delivery
     *  ==========
     *  Inputs: new Date(System.currentTimeMillis() + 24*60*60*999)"
     *  ==========
     *  Expected Outputs: new Date(System.currentTimeMillis() + 24*60*60*999) "
     */
    public void testDB_Delivery006() {
        try {
            assertEquals(new Date(System.currentTimeMillis() + 24*60*60*999),delivery.validateDevDate(new Date(System.currentTimeMillis() + 24*60*60*999)));
        } catch (DB_DeliveryExceptionHandler e){
            fail("Exception not expected.");
        }
    }

    //Test Delivery Id
    /** TEST 007
     *  Test delivery_Id is correct
     *  ==========
     *  Inputs: 0
     *  ==========
     *  Expected Outputs: 0
     */
    public void testDB_Delivery007() {
        try {
            assertEquals(0, delivery.validateDevID(0));
        } catch (DB_DeliveryExceptionHandler e){
            fail("Exception not expected.");
        }
    }
    /** TEST 008
     *  Test delivery_Id is correct
     *  ==========
     *  Inputs: 6
     *  ==========
     *  Expected Outputs: 6
     */
    public void testDB_Delivery008() {
        try {
            assertEquals(6, delivery.validateDevID(6));
        } catch (DB_DeliveryExceptionHandler e){
            fail("Exception not expected.");
        }
    }
    /** TEST 009
     *  Test delivery_Id is incorrect
     *  ==========
     *  Inputs: -1
     *  ==========
     *  Expected Outputs: delivery_id has to be greater than 0
     */
    public void testDB_Delivery009() {
        try {
            delivery.validateDevID(-1);
            fail("Exception expected.");
        } catch (DB_DeliveryExceptionHandler e){
            assertEquals("delivery_id has to be greater than or equal to 0", e.getMessage());
        }
    }
    /** TEST 010
     *  Test delivery_Id is incorrect
     *  ==========
     *  Inputs: -10
     *  ==========
     *  Expected Outputs: delivery_id has to be greater than or equal to 0
     */
    public void testDB_Delivery010() {
        try {
            delivery.validateDevID(-10);
            fail("Exception expected.");
        } catch (DB_DeliveryExceptionHandler e){
            assertEquals("delivery_id has to be greater than or equal to 0", e.getMessage());
        }
    }

    /** Test 011
     *  Test for delivery constructor fail due to delivery_id out of bounds
     *  ==========
     *  Inputs: new DB_Delivery( -1,Date.delivery.validateDevDate(new Date(System.currentTimeMillis())), true, new DB_Customer(), new DB_Invoice())
     *  ==========
     *  Expected Outputs:   DB_DeliveryExceptionHandler = "delivery_id has to be greater than or equal to 0"
     */
    public void testDB_Delivery011() {
        try {
            new DB_Delivery(-1,delivery.validateDevDate(new Date(System.currentTimeMillis())),true, new DB_Customer(), new DB_Invoice());
            fail("Exception expected.");
        } catch (DB_DeliveryExceptionHandler e) {
            assertEquals("delivery_id has to be greater than or equal to 0", e.getMessage());
        }
    }

    /** Test 012
     *  Test for delivery constructor fail due to delivery_date out of bounds
     *  ==========
     *  Inputs: new DB_Delivery( 0, delivery.validateDevDate(new Date(System.currentTimeMillis() - 2*24*60*60*1000)), false, new DB_Customer(), new DB_Invoice());
     *  ==========
     *  Expected Outputs:   delivery_date is older than a day
     */
    public void testDB_Delivery012() {
        try {
            new DB_Delivery(0,delivery.validateDevDate(new Date(System.currentTimeMillis() - 2*24*60*60*1000)), false, new DB_Customer(), new DB_Invoice());
            fail("Exception expected.");
        } catch (DB_DeliveryExceptionHandler e) {
            assertEquals("delivery_date is older than a day", e.getMessage());

        }
    }

    /** Test 013
     *  Test for delivery constructor (and getters) and delivery_id in bounds
     *  ==========
     *  Inputs: new DB_Delivery( 0, delivery.validateDevDate(new Date(System.currentTimeMillis())), true, new DB_Customer(), new DB_Invoice())
     *  ==========
     *  Expected Outputs:   getDelivery_date() = 2021-03-04
     *                      getDelivery_status() = true
     */
    public void testDB_Customer013() {
        try {
            Date now = new Date(System.currentTimeMillis());
            DB_Delivery testDelivery = new DB_Delivery(0, delivery.validateDevDate(now), true, new DB_Customer(), new DB_Invoice());
            assertEquals( 0, testDelivery.getDelivery_id());
            assertEquals( delivery.validateDevDate(now), testDelivery.getDelivery_date());
            assertEquals( true, testDelivery.isDelivery_status());
        } catch (DB_DeliveryExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /**DAO METHOD TESTS*/
    /** Test 014
     *  Test for delivery constructor (and getters) and delivery_id in bounds
     *  ==========
     *  Inputs: new DB_Delivery( 0, delivery.validateDevDate(new Date(System.currentTimeMillis())), true, new DB_Customer(), new DB_Invoice())
     *  ==========
     *  Expected Outputs:   getDelivery_date() = 2021-03-04
     *                      getDelivery_status() = true
     */
    public void testDB_Customer014() {
        try {
            Date now = new Date(System.currentTimeMillis());
            DB_Delivery testDelivery = new DB_Delivery(0, delivery.validateDevDate(now), true, new DB_Customer(), new DB_Invoice());
            assertEquals( 0, testDelivery.getDelivery_id());
            assertEquals( delivery.validateDevDate(now), testDelivery.getDelivery_date());
            assertEquals( true, testDelivery.isDelivery_status());
        } catch (DB_DeliveryExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

}
