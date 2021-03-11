import junit.framework.TestCase;

public class DAOTestSubscription extends TestCase {
    //Global DAO object reference
    private DAO dao;
    DB_Subscription subscription = new DB_Subscription();

    public DAOTestSubscription() {
        try{
//            //Initialize DAO
//            dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");
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
        try{
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
     *  DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
     *  ==========================================
     *  Expected Output(s):
     *  test_subscription.getCustomer().getCustomer_id());
     *  test_subscription.getCount());
     *  test_subscription.getPublication_id());
     */
    public void testGetSubscriptionByCustomer002() {
        int ID = 1;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
            assertEquals(1, test_subscription.getCustomer_id());
            assertEquals(3, test_subscription.getPublication_id());
            assertEquals(1, test_subscription.getCount());
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
     * DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
     * =======================================
     * Expected Output(s):
     * test_subscription.getCustomer().getCustomer_id());
     * test_subscription.getCount());
     * test_subscription.getPublication_id());
     */
    public void testGetSubscriptionByCustomer003(){
        int ID = 2;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
            assertEquals(2, test_subscription.getCustomer_id());
            assertEquals(1, test_subscription.getCount());
            assertEquals(2, test_subscription.getPublication_id());
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
     * DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
     * =========================================
     * Expected Output(s):
     * test_subscription.getCustomer().getCustomer_id());
     * test_subscription.getCount());
     * test_subscription.getPublication_id());
     */
    public void testGetSubscriptionByCustomer004(){
        int ID= 3;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
            assertEquals(3, test_subscription.getCustomer_id());
            assertEquals(1, test_subscription.getCount());
            assertEquals(4, test_subscription.getPublication_id());
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
     * DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
     * ========================================
     * Expected Output(s):
     * test_subscription.getCustomer();
     * test_subscription.getCount());
     * test_subscription.getPublication_id());
     */
    public void testGetSubscriptionByCustomer005(){
        int ID= 4;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
            assertEquals(4,test_subscription.getCustomer_id());
            assertEquals(1, test_subscription.getCount());
            assertEquals(5, test_subscription.getPublication_id());
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
        }
    }


//    public void testGetSubscriptionByCustomer006(){
//        int ID = 5;
//        try{
//            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
//            assertEquals(5,test_subscription.getCustomer_id());
//            assertEquals(1, test_subscription.getCount());
//            assertEquals(1, test_subscription.getPublication_id());
//        }catch (DAOExceptionHandler e){
//            e.printStackTrace();
//            fail("Exception not excepted");
//        }
//    }

    /** Test 006 for subscription by publication which is equal to -1
     * Test subscription by publication = -1 to
     * ===============================
     * Input(s):
     * int prod_id = -1
     * DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_id);
     * ================================
     * Expected Output(s):
     * No subscription with this publication_id = -1 found.
     */
    public void testGetSubscriptionByPublication001() {
        int prod_ID = -1;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(-1,test_subscription.getPublication_id());
        }catch(DAOExceptionHandler e){
            assertEquals("No subscription with this publication_id = -1 found. ", e.getMessage());
            //fail("Exception not excepted");
        }
    }

    /** Test 013 for subscription by publication which is equal to 3
     * Test subscription by publication_id = 3
     * ===============================
     * Input(s):
     * int prod_id 3
     * DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_id);
     * ================================
     * Expected Output(s):
     * 1,test_subscription.getPublication_id()
     * 1,test_subscription.getPublication_id()
     * test_subscription.getCustomer_id()
     */
    public void testGetSubscriptionByPublication002(){
        int prod_ID = 3;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(3,test_subscription.getPublication_id());
            assertEquals(1,test_subscription.getCount());
            assertEquals(1, test_subscription.getCustomer_id());
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
            //assertEquals("No subscription with this publication_id = 3 found. ", e.getMessage());
        }
    }

    /** Test 014 for subscription by publication which is equal to 2
     * Test subscription by publication_id = 2
     * ===============================
     * Input(s):
     * int prod_id 2
     * DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
     * ================================
     * Expected Output(s):
     * test_subscription.getPublication());
     * test_subscription.getCount());
     * test_subscription.getCustomer_id());
     */
    public void testGetSubscriptionByPublication003(){
        int prod_ID = 2;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(2,test_subscription.getPublication_id());
            assertEquals(1,test_subscription.getCount());
            assertEquals(2,test_subscription.getCustomer_id());
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
        }
    }

    /** Test 015 for subscription by count  which is equal to 3
     * Test subscription by publication_id = 3
     * ===============================
     * Input(s):
     * int prod_id 3
     * DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
     * ================================
     * Expected Output(s):
     * test_subscription.getPublication());
     * test_subscription.getCount());
     * test_subscription.getCustomer_id());
     */
    public void testGetSubscriptionByPublication004(){
        int prod_ID = 4;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(4,test_subscription.getPublication_id());
            assertEquals(1, test_subscription.getCount());
            assertEquals(3,test_subscription.getCustomer_id());
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
            //assertEquals("No subscription with this publication_id = 1 found. ", e.getMessage());
        }
    }

    /** Test 016 for subscription by count  which is equal to 5
     * Test subscription by count = 5
     * ===============================
     * Input(s):
     * int count 5
     * DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
     * ================================
     * Expected Output(s):
     * test_subscription.getPublication());
     * test_subscription.getCount());
     * test_subscription.getCustomer_id());
     */

    public void testGetSubscriptionByPublication005() {
        int prod_ID = 5;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(5,test_subscription.getPublication_id());
            assertEquals(1,test_subscription.getCount());
            assertEquals(4, test_subscription.getCustomer_id());
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
/**            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
           assertEquals("No subscription with this publication_id = " + prod_ID + " found. ", e.getMessage());*/
        }
    }

    /** Test 016 for subscription by count  which is equal to 1
     * Test subscription by count = 1
     * ===============================
     * Input(s):
     * int count 1
     * DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
     * ================================
     * Expected Output(s):
     * test_subscription.getPublication());
     * test_subscription.getCount());
     * test_subscription.getCustomer_id());
     */

//    public void testGetSubscriptionByPublication006() {
//        int prod_ID = 1;
//        try{
//            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
//            assertEquals(1,test_subscription.getPublication_id());
//            assertEquals(1,test_subscription.getCount());
//            assertEquals(5, test_subscription.getCustomer_id());
//        }catch(DAOExceptionHandler e){
//            e.printStackTrace();
//            fail("Exception not excepted");
//           DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
// assertEquals("No subscription with this publication_id = " + prod_ID + " found. ", e.getMessage());*/
//        }
//    }

    /** Test 017 for updateSubscription()
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

    /** Test 017 for updateSubscription()
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

    /** Test 017 for updateSubscription()
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

    /**Test 019 deleteSubscription()
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
            DB_Subscription delete_subscription = new DB_Subscription(1,6,2);
            dao.deleteSubscription(delete_subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler | DB_SubscriptionExceptionHandler e){
            assertEquals("No subscription with customer_id = " + 6 + " and publication_id = " + 2  +  " found. ", e.getMessage());
        }
    }

    /**Test 020 deleteSubscription()
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

    /**Test 021 deleteSubscription()
     * Test subscription deleting failing for no count found
     * =====================================================
     * Input(s):
     *  subscription.setCount(2);
     * =====================================================
     * Expected Output(s):
     * "No subscription with 'count=" + subscription.getCount() + "found"
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

    /**Test 022 deleteSubscription()
     * Test subscription deleting failing for no count found
     * =====================================================
     * Input(s):
     *  subscription.setCount(4);
     * =====================================================
     * Expected Output(s):
     *"No subscription with 'count=" + subscription.getCount() + "found"
     */

//    public void testDeleteSubscription004(){
//        try{
//            //subscription.setCount(4);
//            subscription.setCustomer_id(4);
//            subscription.setPublication_id(1);
//            dao.deleteSubscription(subscription);
//            fail("Exception expected");
//        }catch (DAOExceptionHandler e){
//            assertEquals("No subscription with customer_id = 4 and publication_id = 1 found. ", e.getMessage());
//        }
//    }


    /**Test 023 deleteSubscription()
     * Test subscription deleting failing for no count found
     * =====================================================
     * Input(s):
     *  subscription.setCount(5);
     * =====================================================
     * Expected Output(s):
     * "No subscription with 'count=" + subscription.getCount() + "found"
     */
    public void testDeleteSubscription005(){
        try{
            subscription.setCustomer_id(5);
            subscription.setPublication_id(1);
            dao.deleteSubscription(subscription);

        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    public void testDeleteSubscription006(){
        try{
            subscription.setCustomer_id(0);
            subscription.setPublication_id(0);
            dao.deleteSubscription(subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("No subscription with customer_id = "  + 0 + " and publication_id = "  + 0 +  " found. ", e.getMessage());
        }
    }
}