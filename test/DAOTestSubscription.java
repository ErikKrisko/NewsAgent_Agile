import junit.framework.TestCase;

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
     *  Input(s):
     *  int ID = 1
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

    /** Test 003 for subscription by customer id which is equal to 2
     * Test subscription by customer for ID = 2
     * =======================================
     * Input(s):
     * int ID =2
     * DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
     * =======================================
     * Expected Output(s):
     * test_subscription.getCustomer().getCustomer_id());
     */
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

    /** Test 004 for subscription by customer id which is equal to 3
     * Test subscription by customer for ID = 3
     * ========================================
     * Input(s):
     * int ID = 3
     * DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
     * =========================================
     * Expected Output(s):
     * test_subscription.getCustomer().getCustomer_id());
     */
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

    /** Test 005 for subscription by customer id which is equal to 4
     * Test subscription by customer for ID = 4
     * ========================================
     * Input(s):
     * int ID = 4
     * DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
     * ========================================
     * Expected Output(s):
     * test_subscription.getCustomer().getCustomer_id());
     */
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

    /** Test 006 for subscription by customer id which is equal to 5
     * Test subscription by customer for ID = 5
     * ========================================
     * Input(s):
     * int ID = 5
     * DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
     * ========================================
     * Expected Output(s):
     * test_subscription.getCustomer().getCustomer_id());
     */
    public void testGetSubscriptionByCustomer006(){
        int ID = 5;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
            assertEquals(5,test_subscription.getCustomer().getCustomer_id());
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    /** Test 007 for subscription by count  which is equal to -1
     * Test subscription by count = -1
     * ===============================
     * Input(s):
     * int count -1
     * DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
     * ================================
     * Expected Output(s):
     * test_subscription.getCount());
     */
    public void testGetSubscriptionByCount001(){
        int count = -1;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
            assertEquals(2, test_subscription.getCustomer());
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    /** Test 008 for subscription by count  which is equal to 2
     * Test subscription by count = 2
     * ===============================
     * Input(s):
     * int count 2
     * DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
     * ================================
     * Expected Output(s):
     * test_subscription.getCount());
     */
    public void testGetSubscriptionByCount002(){
        int count = 2;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
            assertEquals(2,test_subscription.getCustomer());
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    /** Test 009 for subscription by count  which is equal to 4
     * Test subscription by count = 4
     * ===============================
     * Input(s):
     * int count 4
     * DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
     * ================================
     * Expected Output(s):
     * test_subscription.getCount());
     */
    public void testGetSubscriptionByCount003(){
        int count = 4;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
            assertEquals(4, test_subscription.getCustomer());
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    /** Test 010 for subscription by count  which is equal to 5
     * Test subscription by count = 5
     * ===============================
     * Input(s):
     * int count 5
     * DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
     * ================================
     * Expected Output(s):
     * test_subscription.getCount());
     */
    public void testGetSubscriptionByCount004(){
        int count = 5;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    /** Test 011 for subscription by count  which is equal to 1
     * Test subscription by count = 1
     * ===============================
     * Input(s):
     * int count 1
     * DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
     * ================================
     * Expected Output(s):
     * test_subscription.getCount());
     */
    public void  testGetSubscriptionByCount005(){
        int count = 1;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByCount(count);
        }catch (DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    /** Test 012 for subscription by publication which is equal to -1
     * Test subscription by publication = -1
     * ===============================
     * Input(s):
     * int count -1
     * DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_id);
     * ================================
     * Expected Output(s):
     * test_subscription.getPublication());
     */
    public void testGetSubscriptionByPublication001() {
        int prod_ID = -1;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(-1,test_subscription.getPublication().getProd_id());
        }catch(DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

//    public void testGetSubscriptionByCustomer006(){
//        int ID = 5;
//        try{
//            DB_Subscription test_subscription = dao.getSubscriptionByCustomer(ID);
//            assertEquals(5,test_subscription.getCustomer().getCustomer_id());
//        }catch (DAOExceptionHandler e){
//            fail("Exception not excepted");
//        }
//    }

    /** Test 013 for subscription by publication which is equal to 1
     * Test subscription by publication = 1
     * ===============================
     * Input(s):
     * int count 1
     * DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_id);
     * ================================
     * Expected Output(s):
     * test_subscription.getPublication());
     */
    public void testGetSubscriptionByPublication002(){
        int prod_ID = 1;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(1,test_subscription.getPublication().getProd_id());
        }catch(DAOExceptionHandler e){
            fail("Exception not excepted");}
    }

    /** Test 014 for subscription by publication which is equal to 1
     * Test subscription by count = 1
     * ===============================
     * Input(s):
     * int count 1
     * DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
     * ================================
     * Expected Output(s):
     * test_subscription.getPublication());
     */
    public void testGetSubscriptionByPublication003(){
        int prod_ID = 1;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(1,test_subscription.getPublication().getProd_id());
        }catch(DAOExceptionHandler e){
            fail("Exception not excepted");
        }
    }

    /** Test 015 for subscription by count  which is equal to 1
     * Test subscription by count = 1
     * ===============================
     * Input(s):
     * int count 1
     * DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
     * ================================
     * Expected Output(s):
     * test_subscription.getPublication());
     */
    public void testGetSubscriptionByPublication004(){
        int prod_ID = 1;
        try {
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(1,test_subscription.getPublication().getProd_id());
        }catch(DAOExceptionHandler e){
            assertEquals("No subscription with this publication ID =" + prod_ID, e.getMessage());
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
     */

    public void testGetSubscriptionByPublication005() {
        int prod_ID = 1;
        try{
            DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals(1,test_subscription.getPublication().getProd_id());
        }catch(DAOExceptionHandler e){
            //DB_Subscription test_subscription = dao.getSubscriptionByPublication(prod_ID);
            assertEquals("No subscription with this publication ID=" + prod_ID, e.getMessage());

        }
    }

    /** Test 017 for updateSubscription()
     * Test a new insert into subscription
     * ===================================
     * Input(s):
     *  subscription = dao.getSubscription(1, 1);
     *   subscription.setCount(3);
     * ===================================
     * Expected Output(s):
     * subscription2.getCount(), subscription.getCount()
     */
    public void testUpdateSubscription001() {
        try{
           subscription = dao.getSubscription(1,1);
           subscription.setCount(3);
           dao.updateSubscription(subscription);

           DB_Subscription subscription2 = new DB_Subscription();
           subscription2 = dao.getSubscription(1,1);
           assertEquals(subscription2.getCount(), subscription.getCount());
        }catch (DAOExceptionHandler e ){
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** Test 017 for updateSubscription()
     * Test a new insert into subscription
     * ===================================
     * Input(s):
     * subscription = dao.getSubscription(2, 1)
     * subscription.setCount(2);
     * ===================================
     * Expected Output(s):
     * subscription2.getCount(), subscription.getCount()
     */
    public void testUpdateSubscription002() {
        try {
            subscription = dao.getSubscription(2, 1);
            subscription.setCount(2);
            dao.updateSubscription(subscription);

            DB_Subscription subscription2 = new DB_Subscription();
            subscription2 = dao.getSubscription(2, 1);
            assertEquals(subscription2.getCount(), subscription.getCount());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** Test 017 for updateSubscription()
     * Test a new insert into subscription
     * ===================================
     * Input(s):
     * subscription = dao.getSubscription(3, 1)
     * subscription.setCount(4);
     * ===================================
     * Expected Output(s):
     * subscription2.getCount(), subscription.getCount()
     */

        public void testUpdateSubscription003() {
            try {
                subscription = dao.getSubscription(3, 1);
                subscription.setCount(4);
                dao.updateSubscription(subscription);

                DB_Subscription subscription2 = new DB_Subscription();
                subscription2 = dao.getSubscription(3, 1);
                assertEquals(subscription2.getCount(), subscription.getCount());
            } catch (DAOExceptionHandler e) {
                e.printStackTrace();
                fail("Exception not expected");
            }
        }

    /** Test 017 for updateSubscription()
     * Test a new insert into subscription
     * ===================================
     * Input(s):
     * subscription = dao.getSubscription(4,1);
     * subscription.setCount(5);
     * dao.updateSubscription(subscription);
     * ===================================
     * Expected Output(s):
     * subscription2.getCount(), subscription.getCount()
     */

    public void testUpdateSubscription004(){
        try{
            subscription = dao.getSubscription(4,1);
            subscription.setCount(5);
            dao.updateSubscription(subscription);

            DB_Subscription subscription2 = new DB_Subscription();
            subscription2 = dao.getSubscription(4,1);
            assertEquals(subscription2.getCount(), subscription.getCount());
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** Test 018 UpdateSubscription()
     * Test update for subscription that does not exist
     * ================================================
     * Input(s):
     *  subscription = dao.getSubscription(5,1);
     *   subscription.setCount(1);
     *  dao.updateSubscription(test_subscription)
     * =================================================
     * Expected Output(s):
     *
     */
    public void testUpdateSubscription005() {
        try{
            subscription = dao.getSubscription(5,1);
            subscription.setCount(1);
            dao.updateSubscription(subscription);

            DB_Subscription subscription2 = new DB_Subscription();
            subscription2 = dao.getSubscription(5,1);
            assertEquals(subscription2.getCount(), subscription.getCount());
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /**Test 019 deleteSubscription()
     * Test subscription deleting failing for no count found
     * =====================================================
     * Input(s):
     * subscription.setCount(-1);
     * =====================================================
     * Expected Output(s):
     * "No subscription with 'count=" + subscription.getCount() + "found"
     */

    public void testDeleteSubscription001(){
        try{
            subscription.setCount(-1);
            dao.deleteSubscription(subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("No subscription with 'count=" + subscription.getCount() + "found", e.getMessage());
        }
    }

    /**Test 020 deleteSubscription()
     * Test subscription deleting failing for no count found
     * =====================================================
     * Input(s):
     * subscription.setCount(3);
     * =====================================================
     * Expected Output(s):
     * "No subscription with 'count=" + subscription.getCount() + "found"
     */

    public void testDeleteSubscription002(){
        try{
            subscription.setCount(3);
            dao.deleteSubscription(subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("No subscription with 'count=" + subscription.getCount() + "found", e.getMessage());
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
            subscription.setCount(2);
            dao.deleteSubscription(subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("No subscription with 'count=" + subscription.getCount() + "found", e.getMessage());
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

    public void testDeleteSubscription004(){
        try{
            subscription.setCount(4);
            dao.deleteSubscription(subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("No subscription with 'count=" + subscription.getCount() + "found", e.getMessage());
        }
    }


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
            subscription.setCount(5);
            dao.deleteSubscription(subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("No subscription with 'count=" + subscription.getCount() + "found", e.getMessage());
        }
    }

    public void testDeleteSubscription006(){
        try{
            subscription.setCount(1);
            dao.deleteSubscription(subscription);
            fail("Exception expected");
        }catch (DAOExceptionHandler e){
            assertEquals("No subscription with 'count=" + subscription.getCount() + "found", e.getMessage());
        }
    }
}