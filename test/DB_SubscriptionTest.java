import junit.framework.TestCase;
import static org.junit.Assert.*;

//Do invalid values first
public class DB_SubscriptionTest extends TestCase {
    DB_Subscription subscription = new DB_Subscription();

    /**
     * TEST 001
     * Test count for minimum lower invalid integer
     * ============================
     * Inputs: count
     * -1
     * ============================
     * Expected Outputs: DB_SubscriptionExceptionHandler = -1
     */

    public void testDB_Subscription001() {
        try {
            subscription.validateCount(-Integer.MIN_VALUE);
            fail("Exception expected");
        } catch (DB_SubscriptionExceptionHandler e) {
            assertEquals("Number must be between 0 and 99", e.getMessage());
        }
    }


    /**
     * TEST 002
     * Test count for maximum upper invalid integer
     * ==============================
     * Inputs: count
     * 99
     * ==============================
     * Expected Outputs: DB_SubscriptionExceptionHandler = 99
     */
    public void testDB_Subscription002() {
        try {
            subscription.validateCount(-Integer.MAX_VALUE);
            fail("Exception expected");
        } catch (DB_SubscriptionExceptionHandler e) {
            assertEquals("Number must be between 0 and 99", e.getMessage());
        }
    }


/**
 * TEST 003
 * Test count for minimum lower valid integer
 * ============================
 * Inputs: count
 * 1
 * ============================
 * Expected Outputs: DB_SubscriptionExceptionHandler = 1
 */

    public void testDB_Subscription003(){
        try{
            assertEquals(1, subscription.validateCount(1));
        }catch (DB_SubscriptionExceptionHandler e)
        {
            fail("Exception not expected.");
        }

    }

/**
 * Test 004
 * Test count for maximum upper valid integer
 * ============================
 * Inputs: count
 * 98
 * ===========================
 * Expected Outputs: DB_SubscriptionExceptionHandler = 98
 */

    public void testDB_Subscription004(){
        try{
        assertEquals(98, subscription.validateCount(98));
    }catch (DB_SubscriptionExceptionHandler e)
    {
        fail("Exception not expected.");
    }

}
/**
 *  Test 005
 * Test count for a valid integer
 * =============================
 * Inputs: count
 * 10
 * =============================
 * Excepted Outputs: DB_SubscriptionExceptionHandler = 10
 */

public void testDB_Subscription005(){
    try{
        assertEquals(10, subscription.validateCount(10));
    }catch (DB_SubscriptionExceptionHandler e)
    {
        fail("Exception not expected.");
    }

}

    /**
     *Test 006
     * Test count for invalid integer
     * ==============================
     * Inputs: count
     * 101
     * ==============================
     * Excepted Outputs: DB_SubscriptionExceptionHandler = 101
     */

    public void testDB_Subscription006(){
        try{
            subscription.validateCount(101);
            fail("Exception expected.");
        }catch (DB_SubscriptionExceptionHandler e)
        {
            assertEquals("Number must be between 0 and 99", e.getMessage());
        }

    }
}