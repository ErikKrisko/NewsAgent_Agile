import junit.framework.TestCase;
import java.sql.Date;

import java.sql.Date;

public class DB_DeliveryTest extends TestCase
{
    DB_Delivery delivery = new DB_Delivery();

    /** TEST 001
     *  Test delivery_date for empty
     *  ==========
     *  Inputs: att_delivery.first_name
     *  ==========
     *  Expected Outputs:   DB_DeliveryExceptionHandler = "Invalid delivery_date."
     */
    public void testDB_Delivery001() {
        try {
            delivery.validateDevDate(Date.valueOf(""));
            fail("Exception expected.");
        } catch (DB_DeliveryExceptionHandler e) {
            assertEquals("Invalid delivery_date.", e.getMessage());
        }
    }

}
