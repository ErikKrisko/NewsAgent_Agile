import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;

public class DAOTestSubscription extends TestCase {
    //Global DAO object reference
    private DAO dao;
    DB_Subscription subscription = new DB_Subscription();

    public DAOTestSubscription() {
        try{
            //Initialize DAO
            dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");
            //Reset the Database
            JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
            connection.executeScript("NewsAgent_Database.sql");
            connection.setDbName("newsagent");
            connection.executeScript("NewsAgent_Data.sql");
            connection.close();
            dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");
        }catch (DAOExceptionHandler | JDBCExceptionHandler e){
            e.printStackTrace();
            fail("DAO initialization failed");
        }
    }

    /** Preemptive method for resetting database to be used as an initial starting point of each test.
     *  Will need to change existing test to use this system too for be, most reliable results.
     */
    private void initializeDatabase() {
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

    /** Test 001 testGetSubscriptionByCustomer
     * Test for subscription by customer not found.
     * ===============================================
     * Input(s):
     * ID = -1
     * dao.getSubscriptionByCustomer(ID);
     * ================================================
     * Excepted Output(s):
     * DAOExceptionHandler = "No Subscription with this customer ID =" + ID + "not found"
     */
    public void testGetSubscriptionByCustomer001() {
        int ID = -1;
        try{
            ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(-1);
            assertNull(getSubscriptionByCustomer);
            dao.getSubscriptionByCustomer(-1);
            fail("Exception Expected");
        }catch(DAOExceptionHandler e){
            assertEquals("No subscription with customer_id = " + -1 + " found.", e.getMessage());
        }
    }

    /** Test 002 for subscription by customer id which is equal to 1
     *  Test subscription by customer for ID = 1
     *  =========================================
     *  Input(s):
     *  int ID = 1
     *  ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(1);
     *  ==========================================
     *  Expected Output(s):
     * subscriptionCustomer.getCustomer_id());
     * subscriptionCustomer.getPublication_id());
     */
    public void testGetSubscriptionByCustomer002() {
        int ID = 1;
        try {
            ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(1);
            assertEquals(1, getSubscriptionByCustomer.size());
            DB_Subscription subscriptionCustomer = getSubscriptionByCustomer.get(0);
            assertEquals(1, subscriptionCustomer.getCustomer_id());
            assertEquals(3, subscriptionCustomer.getPublication_id());
            dao.getSubscriptionByCustomer(1);
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            new DAOExceptionHandler("No subscription with customer_id = " + 1 + " found.");
            //fail("Exception not excepted");
        }
    }

    /** Test 003 for subscription by customer id which is equal to 2
     * Test subscription by customer for ID = 2
     * =======================================
     * Input(s):
     * int ID = 2
     * ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(2);
     * =======================================
     * Expected Output(s):
     * subscriptionCustomer.getCustomer_id());
     * subscriptionCustomer.getPublication_id());
     */
    public void testGetSubscriptionByCustomer003(){
        int ID = 2;
        try{
            ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(2);
            assertEquals(1, getSubscriptionByCustomer.size());
            DB_Subscription subscriptionCustomer = getSubscriptionByCustomer.get(0);
            assertEquals(2, subscriptionCustomer.getCustomer_id());
            assertEquals(2, subscriptionCustomer.getPublication_id());
            dao.getSubscriptionByCustomer(2);
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
        }
    }

    /** Test 004 for subscription by customer id which is equal to 3
     * Test subscription by customer for ID = 3
     * ========================================
     * Input(s):
     * int ID = 3
     * ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(3);
     * =========================================
     * Expected Output(s):
     * subscriptionCustomer.getCustomer_id());
     * subscriptionCustomer.getPublication_id());
     */
    public void testGetSubscriptionByCustomer004(){
        int ID= 3;
        try {
            ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(3);
            assertEquals(1, getSubscriptionByCustomer.size());
            DB_Subscription subscriptionCustomer = getSubscriptionByCustomer.get(0);
            assertEquals(3, subscriptionCustomer.getCustomer_id());
            assertEquals(4, subscriptionCustomer.getPublication_id());
            dao.getSubscriptionByCustomer(3);
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
        }
    }

    /** Test 005 for subscription by customer id which is equal to 4
     * Test subscription by customer for ID = 4
     * ========================================
     * Input(s):
     * int ID = 4
     * ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(4);
     * ========================================
     * Expected Output(s):
     * subscriptionCustomer.getCustomer_id());
     * subscriptionCustomer.getPublication_id());
     */
    public void testGetSubscriptionByCustomer005(){
        int ID= 4;
        try{
            ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(4);
            assertEquals(1, getSubscriptionByCustomer.size());
            DB_Subscription subscriptionCustomer = getSubscriptionByCustomer.get(0);
            assertEquals(4, subscriptionCustomer.getCustomer_id());
            assertEquals(5, subscriptionCustomer.getPublication_id());
            dao.getSubscriptionByCustomer(4);
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
        }
    }


    /** Test 006 for subscription by publication which is equal to -1
     * Test subscription by publication = -1 to
     * ===============================
     * Input(s):
     * int prod_id = -1
     * ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(-1);
     * ================================
     * Expected Output(s):
     * No subscription with this publication_id = -1 found.
     */
    public void testGetSubscriptionByPublication001() {
        int prod_ID = -1;
        try {

            ArrayList<DB_Subscription> getSubscriptionByPublication = dao.getSubscriptionByPublication(-1);

            assertNull(getSubscriptionByPublication);
//            assertEquals(1, getSubscriptionByPublication.size());
//            DB_Subscription subscriptionCustomer = getSubscriptionByPublication.get(0);
//            assertEquals(0, subscriptionCustomer.getCustomer_id());
//            assertEquals(-1, subscriptionCustomer.getPublication_id());
//            dao.getSubscriptionByPublication(-1);
//            fail("Exception expected");
        }catch(DAOExceptionHandler e){
            //assertEquals("No subscription with this publication_id = -1 found. ", e.getMessage());
            e.printStackTrace();
            fail("Exception not excepted");
        }
    }

    /** public void testGetSubscriptionByCustomer001() {
     int ID = -1;
     try{
     ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(-1);
     assertNull(getSubscriptionByCustomer);
     dao.getSubscriptionByCustomer(-1);
     fail("Exception Expected");
     }catch(DAOExceptionHandler e){
     assertEquals("No subscription with customer_id = " + -1 + " found.", e.getMessage());
     }
     **/

    /** Test 007 for subscription by publication which is equal to 3
     * Test subscription by publication_id = 3
     * ===============================
     * Input(s):
     * int prod_id 3
     * ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(3);
     * ================================
     * Expected Output(s):
     * subscriptionCustomer.getCustomer_id());
     * subscriptionCustomer.getPublication_id());
     */
    public void testGetSubscriptionByPublication002(){
        int prod_ID = 3;
        try {
            ArrayList<DB_Subscription> getSubscriptionByPublication = dao.getSubscriptionByPublication(3);
            assertEquals(1, getSubscriptionByPublication.size());
            DB_Subscription subscriptionCustomer = getSubscriptionByPublication.get(0);
            assertEquals(1, subscriptionCustomer.getCustomer_id());
            assertEquals(3, subscriptionCustomer.getPublication_id());
            dao.getSubscriptionByPublication(3);
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
            //assertEquals("No subscription with this publication_id = 3 found. ", e.getMessage());
        }
    }

    /** Test 008 for subscription by publication which is equal to 2
     * Test subscription by publication_id = 2
     * ===============================
     * Input(s):
     * int prod_id 2
     * ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(2);
     * ================================
     * Expected Output(s):
     * subscriptionCustomer.getCustomer_id());
     * subscriptionCustomer.getPublication_id());
     */
    public void testGetSubscriptionByPublication003(){
        int prod_ID = 2;
        try {
            ArrayList<DB_Subscription> getSubscriptionByPublication = dao.getSubscriptionByPublication(2);
            assertEquals(1, getSubscriptionByPublication.size());
            DB_Subscription subscriptionCustomer = getSubscriptionByPublication.get(0);
            assertEquals(2, subscriptionCustomer.getCustomer_id());
            assertEquals(2, subscriptionCustomer.getPublication_id());
            dao.getSubscriptionByPublication(2);
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
        }
    }

    /** Test 009 for subscription by publication which is equal to 4
     * Test subscription by publication_id = 4
     * ===============================
     * Input(s):
     * int prod_id 4
     * ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(4);
     * ================================
     * Expected Output(s):
     * subscriptionCustomer.getCustomer_id()
     * subscriptionCustomer.getPublication_id()
     */
    public void testGetSubscriptionByPublication004(){
        int prod_ID = 4;
        try {
            ArrayList<DB_Subscription> getSubscriptionByPublication = dao.getSubscriptionByPublication(4);
            assertEquals(1, getSubscriptionByPublication.size());
            DB_Subscription subscriptionCustomer = getSubscriptionByPublication.get(0);
            assertEquals(3, subscriptionCustomer.getCustomer_id());
            assertEquals(4, subscriptionCustomer.getPublication_id());
            dao.getSubscriptionByPublication(4);
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
            //assertEquals("No subscription with this publication_id = 1 found. ", e.getMessage());
        }
    }

    /** Test 010 subscription by publication which is equal to 5
     * Test subscription by publication = 5
     * ===============================
     * Input(s):
     * int prod_ID = 5
     * ArrayList<DB_Subscription> getSubscriptionByCustomer = dao.getSubscriptionByCustomer(5);
     * ================================
     * Expected Output(s):
     * subscriptionCustomer.getCustomer_id()
     * subscriptionCustomer.getPublication_id()
     */

    public void testGetSubscriptionByPublication005() {
        int prod_ID = 5;
        try{
            ArrayList<DB_Subscription> getSubscriptionByPublication = dao.getSubscriptionByPublication(5);
            assertEquals(1, getSubscriptionByPublication.size());
            DB_Subscription subscriptionCustomer = getSubscriptionByPublication.get(0);
            assertEquals(1, subscriptionCustomer.getCustomer_id());
            assertEquals(5, subscriptionCustomer.getPublication_id());
            dao.getSubscriptionByPublication(5);
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
/**            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
           assertEquals("No subscription with this publication_id = " + prod_ID + " found. ", e.getMessage());*/
        }
    }

    /** Test 011 for updateSubscription()
     * Test a new insert into subscription
     * ===================================
     * Input(s):
     * dao.getSubscription(2,3);
     * ===================================
     * Expected Output(s):
     * update_subscription.getCustomer_id(), get_subscription.getCustomer_id()
     * update_subscription.getPublication_id(), get_subscription.getPublication_id()
     * update_subscription.getCount(), get_subscription.getCount()
     */
    public void testUpdateSubscription001() {
        try {
            DB_Subscription update_subscription = new DB_Subscription(1,2,3);
            dao.updateSubscription(update_subscription);
            DB_Subscription get_subscription = dao.getSubscription(2,3);
            assertEquals(update_subscription.getCustomer_id(), get_subscription.getCustomer_id());
            assertEquals(update_subscription.getPublication_id(), get_subscription.getPublication_id());
            assertEquals(update_subscription.getCount(), get_subscription.getCount());
        } catch (DAOExceptionHandler | DB_SubscriptionExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** Test 012 for updateSubscription()
     * Testing to fail with inserting a wrong customer_id
     * ===================================
     * Input(s):
     * DB_Subscription update_subscription = new DB_Subscription(1,6,2);
     * dao.updateSubscription(update_subscription);
     * ===================================
     * Expected Output(s):
     * Cannot add or update a child row: a foreign key constraint fails (`newsagent`.`subscription`, CONSTRAINT `subscription_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE)
     */

        public void testUpdateSubscription002() {
            try {
                DB_Subscription update_subscription = new DB_Subscription(1,6,2);
                dao.updateSubscription(update_subscription);
                fail("Exception expected");
            } catch (DAOExceptionHandler e) {
                assertEquals("Cannot add or update a child row: a foreign key constraint fails (`newsagent`.`subscription`, CONSTRAINT `subscription_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE)", e.getMessage());
            }catch (DB_SubscriptionExceptionHandler e){
                e.printStackTrace();
                fail("DB_Subscription exception not expected");
            }
        }

    /** Test 013 for updateSubscription()
     * Test to fail with inserting a wrong publication_id
     * ===================================
     * Input(s):
     * DB_Subscription update_subscription = new DB_Subscription(1,2,6);
     * dao.updateSubscription(update_subscription);
     * ===================================
     * Expected Output(s):
     * Cannot add or update a child row: a foreign key constraint fails (`newsagent`.`subscription`, CONSTRAINT `subscription_ibfk_2` FOREIGN KEY (`prod_id`) REFERENCES `publication` (`prod_id`))
     */

    public void testUpdateSubscription003(){
        try {
            DB_Subscription update_subscription = new DB_Subscription(1,2,6);
            dao.updateSubscription(update_subscription);
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("Cannot add or update a child row: a foreign key constraint fails (`newsagent`.`subscription`, CONSTRAINT `subscription_ibfk_2` FOREIGN KEY (`prod_id`) REFERENCES `publication` (`prod_id`))", e.getMessage());
        }catch (DB_SubscriptionExceptionHandler e){
            e.printStackTrace();
            fail("DB_Subscription exception not expected");
        }
    }

    /**Test 014 deleteSubscription()
     * Test subscription by deleting customer 6
     * =====================================================
     * Input(s):
     * DB_Subscription delete_subscription = new DB_Subscription(1,6,2);
     * dao.deleteSubscription(delete_subscription);
     * =====================================================
     * Expected Output(s):
     * No subscription with customer_id = " + 6 + " and publication_id = " + 2  +  " found.
     */

    public void testDeleteSubscription001(){
        try{
            //May need an arraylist for all deleting
            DB_Subscription delete_subscription = new DB_Subscription(1,6,2);
            dao.deleteSubscription(delete_subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler | DB_SubscriptionExceptionHandler e){
            assertEquals("No subscription with customer_id = " + 6 + " and publication_id = " + 2  +  " found. ", e.getMessage());
        }
    }

    /**Test 015 deleteSubscription()
     * Test subscription by deleting publication 6
     * =====================================================
     * Input(s):
     * DB_Subscription delete_subscription = new DB_Subscription(1,2,6);
     * delete_subscription.setCustomer_id(2);
     * delete_subscription.setPublication_id(6);
     * =====================================================
     * Expected Output(s):
     * No subscription with customer_id = " + 2 +  " and publication_id = " + 6 +  " found.
     */

    public void testDeleteSubscription002(){
        try{
            DB_Subscription delete_subscription = new DB_Subscription(1,2,6);
            delete_subscription.setCustomer_id(2);
            delete_subscription.setPublication_id(6);
            dao.deleteSubscription(delete_subscription);
            dao.getSubscription(2,6);

            fail("Exception expected");
        }catch (DAOExceptionHandler | DB_SubscriptionExceptionHandler e){
            assertEquals("No subscription with customer_id = " + 2 +  " and publication_id = " + 6 +  " found. ", e.getMessage());
        }
    }

    /**Test 016 deleteSubscription()
     * Test subscription deleting customer 6
     * =====================================================
     * Input(s):
     * subscription.setCustomer_id(6);
     * subscription.setPublication_id(1);
     * dao.deleteSubscription(subscription);
     * =====================================================
     * Expected Output(s):
     * "No subscription with customer_id = " + 6 + " and publication_id = " + 1 + " found. "
     */

    public void testDeleteSubscription003(){
        try{
            subscription.setCustomer_id(6);
            subscription.setPublication_id(1);
            dao.deleteSubscription(subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("No subscription with customer_id = " + 6 + " and publication_id = " + 1 + " found. ", e.getMessage());
        }
    }

    /**Test 017 getSubscriptionsByPublications
     * Test for getting subscriptions from publication
     * =====================================================
     * Input(s):    ArrayList<DB_Publication> prod_list = dao.getPublicationsByDate(Date.valueOf("2021-04-14"));
     *              ArrayList<DB_Subscription> sub_list = dao.getSubscriptionsByPublications(prod_list);
     * =====================================================
     * Expected Output(s):  sub_list.size = 3;
     *                      sub_list.get(0).getCustomer_id() = 1;
     *                      sub_list.get(1).getCustomer_id() = 2;
     *                      sub_list.get(2).getCustomer_id() = 3;
     */
    public void testGetSubscriptionsByPublications001(){
        try{
            initializeDatabase();
            ArrayList<DB_Publication> prod_list = dao.getPublicationsByDate(Date.valueOf("2021-04-14"));
            ArrayList<DB_Subscription> sub_list = dao.getSubscriptionsByPublications(prod_list);
            assertEquals(4, sub_list.size());
            assertEquals(2, sub_list.get(0).getCustomer_id());
            assertEquals(1, sub_list.get(1).getCustomer_id());
            assertEquals(5, sub_list.get(2).getCustomer_id());
            assertEquals(3, sub_list.get(3).getCustomer_id());
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /**Test 018 getSubscriptionsByPublications
     * Test for getting subscriptions from publication
     * =====================================================
     * Input(s):    ArrayList<DB_Publication> prod_list = dao.getPublicationsByDate(Date.valueOf("2021-04-15"));
     *              ArrayList<DB_Subscription> sub_list = dao.getSubscriptionsByPublications(prod_list);
     * =====================================================
     * Expected Output(s):  sub_list.size = 1;
     *                      sub_list.get(0).getCustomer_id() = 3;
     */
    public void testGetSubscriptionsByPublications002(){
        try{
            initializeDatabase();
            ArrayList<DB_Publication> prod_list = dao.getPublicationsByDate(Date.valueOf("2021-04-15"));
            ArrayList<DB_Subscription> sub_list = dao.getSubscriptionsByPublications(prod_list);
            assertEquals(1, sub_list.size());
            assertEquals(3, sub_list.get(0).getCustomer_id());
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    //  Test for fail of empty list

}