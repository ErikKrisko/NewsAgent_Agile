import junit.framework.TestCase;

public class DAOTestCustomer extends TestCase {
    //  Global dao object reference
    private DAO dao;

    public DAOTestCustomer() throws DAOExceptionHandler {
        try {
            //  Initialize DAO
            dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");
            //  Reset Database
            JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
            connection.executeScript("NewsAgent_Database.sql");
            connection.setDbName("newsagent");
            connection.executeScript("NewsAgent_Data.sql");
            connection.close();
        } catch (DAOExceptionHandler | JDBCExceptionHandler e) {
            e.printStackTrace();
            fail("DAO initialization failed.");
        }
    }

    /** Test for customer not found
     *
     */
    public void testGetCustomer001(){
        int ID = 0;
        try {
            dao.getCustomer(ID);
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("No customer with customer_id = " + ID + " not found.", e.getMessage());
        }
    }

    /** Test for customer id = 1
     *
     */
    public void testGetCustomer002() {
        int ID = 1;
        try {
            DB_Customer test_customer = dao.getCustomer(ID);
            assertEquals( 1, test_customer.getCustomer_id());
            assertEquals( "Bill", test_customer.getFirst_name());
            assertEquals( "Birr", test_customer.getLast_name());
            assertEquals( "0951078281", test_customer.getPhone_no());
            assertEquals( 1, test_customer.getAddress().getAddress_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }





/*
    public void testGetCustomers() {

    }

    public void testUpdateCustomer() {
    }

    public void testDeleteCustomer() {
    }
*/
}