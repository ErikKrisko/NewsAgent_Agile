import junit.framework.TestCase;

public class DAOTestSubscription extends TestCase {
    //Global DAO object reference
    private DAO dao;

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
        }catch (DAOExceptionHandler | JDBCExceptionHandler e){
            e.printStackTrace();
            fail("DAO initialization failed");
        }
    }

    /** Test 001 testGetSubscriptionByCustomer
     * Test for subscription by customer not found
     * ===============================================
     * Input(s):
     * ID = 0
     * dao.getSubscriptionByCustomer(ID);
     * ================================================
     * Excepted Output(s):
     * DAOExceptionHandler = "No Subscription with this customer ID =" + ID + "not found"
     */
    public void testGetSubscriptionByCustomer001() {
        int ID = -1;
        try{
            dao.getSubscriptionByCustomer(ID);
            fail("Exception Expected");
        }catch(DAOExceptionHandler e){
            assertEquals("No Subscription with this customer ID =" + ID + "not found", e.getMessage());
        }
    }

    /** Test 002 for subscription by customer id which is equal to 1
     *  Test subscription by customer for ID = 1
     *  =========================================
     *  Input(s):int ID = 1
     *  DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
     *  ==========================================
     *  Expected Output(s):
     *  test_subscription.getCustomer().getCustomer_id());
     */
    public void testGetSubscriptionByCustomer002() {
        int ID = 1;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
            assertEquals(1, test_subscription.getCustomer().getCustomer_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not excepted");
        }
    }

    public void testGetSubscriptionByCustomer003(){
        int ID = 2;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
            assertEquals(2, test_subscription.getCustomer().getCustomer_id());
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
        }
    }

    public void testGetSubscriptionByCustomer004(){
        int ID= 3;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
            assertEquals(3, test_subscription.getCustomer().getCustomer_id());
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
        }
    }

    public void testGetSubscriptionByCustomer005(){
        int ID= 4;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
            assertEquals(4,test_subscription.getCustomer().getCustomer_id());
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not excepted");
        }
    }

    public void testGetSubscriptionByCustomer006(){
        int ID = 5;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
            assertEquals(5,test_subscription.getCustomer().getCustomer_id());
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    public void testGetSubscriptionByCount001(){
        int count = -1;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
            assertEquals(2, test_subscription.getCustomer());
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    public void testGetSubscriptionByCount002(){
        int count = 2;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
            assertEquals(2,test_subscription.getCustomer());
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    public void testGetSubscriptionByCount003(){
        int count = 4;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
            assertEquals(4, test_subscription.getCustomer());
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    public void testGetSubscriptionByCount004(){
        int count = 5;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    public void  testGetSubscriptionByCount005(){
        int count = 1;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    /** Test 003 GetSubscriptionByCustomer()
     *  Test to get all customers ID
     *  ====================================
     *  Input(s):
     *  LinkedList<DB_customer> test_subscription = dao.getSubscription(null);
     *  ====================================
     *  Excepted Output(s):
     *  test_subscription1.getSubscriptionByCustomer() = 1
     *  --------------------------------------------------
     *  test_subscription1.getSubscriptionByCustomer() = 2
     *  --------------------------------------------------
     *  test_subscription1.getSubscriptionByCustomer() = 3
     *  --------------------------------------------------
     *  test_subscription1.getSubscriptionByCustomer() = 4
     *  --------------------------------------------------
     *  test_subscription1.getSubscriptionByCustomer() = 5
     */

    public void testGetSubscriptionByPublication001() {
        int prod_ID = -1;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(-1,test_subscription.getPublication().getPublication_id());
        }catch(DAOExceptionHandler e){
            assertEquals("No subscription with this publication ID =" + prod_ID + e.getMessage());
        }
    }

    public void testGetSubscriptionByPublication002(){
        int prod_ID = 1;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(1,test_subscription.getPublication().getPublication_id());
        }catch(DAOExceptionHandler e){
            assertEquals("No subscription with this publication ID =" + prod_ID + e.getMessage());
        }
    }

    public void testGetSubscriptionByPublication003(){
        int prod_ID = 1;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(1,test_subscription.getPublication().getPublication_id());
        }catch(DAOExceptionHandler e){
            assertEquals("No subscription with this publication ID =" + prod_ID + e.getMessage());
        }
    }

    public void testGetSubscriptionByPublication004(){
        int prod_ID = 1;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(1,test_subscription.getPublication().getPublication_id());
        }catch(DAOExceptionHandler e){
            assertEquals("No subscription with this publication ID =" + prod_ID + e.getMessage());
        }
    }

    public void testGetSubscriptionByPublication005(){
        int prod_ID = 1;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(1,test_subscription.get);
        }catch(DAOExceptionHandler e){
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals();

        }
    }
    public void testUpdateSubscription001() {
        try{
            DB_Subscription test_subscription = new DB_Subscription(-1,-1,-1);
            dao.updateSubscription(test_subscription);
            assertEquals(1,test_subscription.getSubscription());
            assertTrue(test_subscription.equals(dao.getSubscription(-1,-1)));
        }catch (DAOExceptionHandler | DB_SubscriptionExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    public void testUpdateSubscription002() {
        try{
            DB_Subscription test_subscription = new DB_Subscription(1,1,1);
            dao.updateSubscription(test_subscription);
            assertEquals(1,test_subscription.getSubscription());
            assertTrue(test_subscription.equals(dao.getSubscription(1,1)));
        }catch (DAOExceptionHandler | DB_SubscriptionExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    public void testUpdateSubscription003() {
        try{
            DB_Subscription test_subscription = new DB_Subscription(2,2,2);
            dao.updateSubscription(test_subscription);
            assertEquals(1,test_subscription.getSubscription());
            assertTrue(test_subscription.equals(dao.getSubscription(2,2)));
        }catch (DAOExceptionHandler | DB_SubscriptionExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected");
        }
    }
    public void testUpdateSubscription004() {
        try{
            DB_Subscription test_subscription = new DB_Subscription(3,3,3);
            dao.updateSubscription(test_subscription);
            assertEquals(1,test_subscription.getSubscription());
            assertTrue(test_subscription.equals(dao.getSubscription(1,1)));
        }catch (DAOExceptionHandler | DB_SubscriptionExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** Test UpdateSubscription()
     * Test update for subscription that does not exist
     * ================================================
     * Input(s):
     *  DB_Subscription test_subscription = new DB_Subscription();
     *  dao.updateSubscription(test_subscription)
     * =================================================
     * Expected Output(s):
     *
     */
    public void testUpdateSubscription005() {
        try{
            DB_Subscription test_subscription = new DB_Subscription();
            test_subscription.setCustomer(4);
            dao.updateSubscription(test_subscription);
            assertEquals(1,test_subscription.getSubscription());
            assertTrue(test_subscription.equals(dao.getSubscription(1,1)));
        }catch (DAOExceptionHandler | DB_SubscriptionExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /**Test deleteSubscription()
     * Test subscription deleting failing for no count found
     * =====================================================
     * Input(s):
     * DB_Subscription test_subscription = new DB_Subscription();
     *             test_subscription.setCount(-1);
     *             dao.deleteSubscription(test_subscription);
     * =====================================================
     * Expected Output(s):
     * DAOExceptionHandler = "Can't delete count with number = '-1', does not exist in database"
     */

    public void testDeleteSubscription001(){
        try{
            DB_Subscription test_subscription = new DB_Subscription();
            test_subscription.setCount(-1);
            dao.deleteSubscription(test_subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("Can't delete count with number = '-1', does not exist in database", e.getMessage());
            e.printStackTrace();
            fail("DB_SubscriptionExceptionHandler Exception not expected");
        }
    }

    /**Test deleteSubscription()
     * Test subscription deleting failing for no count found
     * =====================================================
     * Input(s):
     * DB_Subscription test_subscription = new DB_Subscription();
     *             test_subscription.setCount(1);
     *             dao.deleteSubscription(test_subscription);
     * =====================================================
     * Expected Output(s):
     * DAOExceptionHandler = "Can't delete count with number = '1', does not exist in database"
     */

    public void testDeleteSubscription002(){
        try{
            DB_Subscription test_subscription = new DB_Subscription();
            test_subscription.setCount(1);
            dao.deleteSubscription(test_subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("Can't delete count with number = '1', does not exist in database", e.getMessage());
            e.printStackTrace();
            fail("DB_SubscriptionExceptionHandler Exception not expected");
        }
    }

    /**Test deleteSubscription()
     * Test subscription deleting failing for no count found
     * =====================================================
     * Input(s):
     * DB_Subscription test_subscription = new DB_Subscription();
     *             test_subscription.setCount(2);
     *             dao.deleteSubscription(test_subscription);
     * =====================================================
     * Expected Output(s):
     * DAOExceptionHandler = "Can't delete count with number = '2', does not exist in database"
     */

    public void testDeleteSubscription003(){
        try{
            DB_Subscription test_subscription = new DB_Subscription();
            test_subscription.setCount(2);
            dao.deleteSubscription(test_subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("Can't delete count with number = '2', does not exist in database", e.getMessage());
            e.printStackTrace();
            fail("DB_SubscriptionExceptionHandler Exception not expected");
        }
    }
    /**Test deleteSubscription()
     * Test subscription deleting failing for no count found
     * =====================================================
     * Input(s):
     * DB_Subscription test_subscription = new DB_Subscription();
     *             test_subscription.setCount(3);
     *             dao.deleteSubscription(test_subscription);
     * =====================================================
     * Expected Output(s):
     * DAOExceptionHandler = "Can't delete count with number = '3', does not exist in database"
     */

    public void testDeleteSubscription004(){
        try{
            DB_Subscription test_subscription = new DB_Subscription();
            test_subscription.setCount(3);
            dao.deleteSubscription(test_subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("Can't delete count with number = '3', does not exist in database", e.getMessage());
            e.printStackTrace();
            fail("DB_SubscriptionExceptionHandler Exception not expected");
        }
    }

    /**Test deleteSubscription()
     * Test subscription deleting failing for no count found
     * =====================================================
     * Input(s):
     * DB_Subscription test_subscription = new DB_Subscription();
     *             test_subscription.setCount(4);
     *             dao.deleteSubscription(test_subscription);
     * =====================================================
     * Expected Output(s):
     * DAOExceptionHandler = "Can't delete count with number = '4', does not exist in database"
     */
    public void testDeleteSubscription005(){
        try{
            DB_Subscription test_subscription = new DB_Subscription();
            test_subscription.setCount(4);
            dao.deleteSubscription(test_subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("Can't delete count with number = '4', does not exist in database", e.getMessage());
            e.printStackTrace();
            fail("DB_SubscriptionExceptionHandler Exception not expected");
        }
    }
}