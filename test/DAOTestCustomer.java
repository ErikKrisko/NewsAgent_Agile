import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.LinkedList;

public class DAOTestCustomer extends TestCase {
    //  Global dao object reference
    private DAO dao;

    /** Initialize test environment.
     *  ==========
     *  Inputs: JDBC initialization and script execution
     *          dao initialization
     *  ==========
     *  Expected Outputs:   None
     */
    public void initializeDatabase() {
        try {
            //  Reset Database
            JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
            connection.executeScript("NewsAgent_Database.sql");
            connection.setDbName("newsagent");
            connection.executeScript("NewsAgent_Data_Extended.sql");
            connection.close();
            //  Initialize DAO
            dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");
        } catch (DAOExceptionHandler | JDBCExceptionHandler e) {
            e.printStackTrace();
            fail("Test initialization failed.");
        }
    }

    /** TEST 001 getCustomer()
     *  Test for customer not found.
     *  ==========
     *  Inputs: int ID = 0
     *          dao.getCustomer(ID);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "No customer with customer_id = " + ID + " not found."
     */
    public void testGetCustomer001(){
        int ID = -1;
        try {
            initializeDatabase();
            dao.getCustomer(ID);
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("No customer with customer_id = " + ID + " not found.", e.getMessage());
        }
    }

    /** TEST 002 getCustomer()
     *  Test for customer id = 1
     *  ==========
     *  Inputs: int ID = 1
     *          DB_Customer test_customer = dao.getCustomer(ID);
     *  ==========
     *  Expected Outputs:   test_customer.getCustomer_id() = 1
     *                      test_customer.getFirst_name() = "Bill"
     *                      test_customer.getLast_name() = "Birr"
     *                      test_customer.getPhone_no() = "0875748271"
     *                      test_customer.getAddress().getAddress_id() = 1
     */
    public void testGetCustomer002() {
        int ID = 1;
        try {
            initializeDatabase();
            DB_Customer test_customer = dao.getCustomer(ID);
            assertEquals( 1, test_customer.getCustomer_id());
            assertEquals( "Bill", test_customer.getFirst_name());
            assertEquals( "Birr", test_customer.getLast_name());
            assertEquals( "0875748271", test_customer.getPhone_no());
            assertEquals( 1, test_customer.getAddress().getAddress_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 003 getCustomers()
     *  Test to get all customers
     *  ==========
     *  Inputs: LinkedList<DB_Customer> test_list = dao.getCustomers(null);
     *  ==========
     *  Expected Outputs:   test_customer1.getCustomer_id() = 1
     *                      test_customer1.getFirst_name() = "Bill"
     *                      test_customer1.getLast_name() = "Birr"
     *                      test_customer1.getPhone_no() = "0951078281"
     *                      test_customer1.getAddress().getAddress_id() = 1
     *                      -----------------------------------------------
     *                      test_customer2.getCustomer_id() = 23
     *                      test_customer2.getFirst_name() = "Neptune"
     *                      test_customer2.getLast_name() = "Sorin"
     *                      test_customer2.getPhone_no() = "0858673240"
     *                      test_customer2.getAddress().getAddress_id() = 21
     */
    public void testGetCustomers001() {
        try {
            initializeDatabase();
            ArrayList<DB_Customer> test_list = dao.getCustomers(null);
            //  Check the list size
            assertEquals( 23, test_list.size());
            //  Get 1st entry in the list
            DB_Customer test_customer1 = test_list.get(0);
            //  Check 1st entry
            assertEquals( 1, test_customer1.getCustomer_id());
            assertEquals( "Bill", test_customer1.getFirst_name());
            assertEquals( "Birr", test_customer1.getLast_name());
            assertEquals( "0875748271", test_customer1.getPhone_no());
            assertEquals( 1, test_customer1.getAddress().getAddress_id());
            //  Get last entry in the list
            DB_Customer test_customer2 = test_list.get(test_list.size()-1);
            assertEquals( 23, test_customer2.getCustomer_id());
            assertEquals( "Neptune", test_customer2.getFirst_name());
            assertEquals( "Sorin", test_customer2.getLast_name());
            assertEquals( "0858673240", test_customer2.getPhone_no());
            assertEquals( 21, test_customer2.getAddress().getAddress_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 004 getCustomers()
     *  Test weak search functionality
     *  ==========
     *  Inputs: Search_Customer[] search_criteria = {
     *                     new Search_Customer( Att_Customer.first_name, "%i%", false),
     *                     new Search_Customer( Att_Customer.last_name, "%r%", false),
     *                     new Search_Customer( Att_Customer.phone_no, "%61", false)
     *          };
     *          LinkedList<DB_Customer> test_list = dao.getCustomers(search_criteria);
     *  ==========
     *  Expected Outputs:   test_customer1.getCustomer_id() = 2
     *                      test_customer1.getFirst_name() = "Lib"
     *                      test_customer1.getLast_name() = "Tard"
     *                      test_customer1.getPhone_no() = "0541073261"
     *                      test_customer1.getAddress().getAddress_id() = 2
     */
    public void testGetCustomers002() {
        try {
            initializeDatabase();
            Search_Customer[] search_criteria = {
                    new Search_Customer( Att_Customer.first_name, "%i%", false),
                    new Search_Customer( Att_Customer.last_name, "%r%", false),
                    new Search_Customer( Att_Customer.phone_no, "%61", false)
            };
            ArrayList<DB_Customer> test_list = dao.getCustomers(search_criteria);
            //  Check the list size
            assertEquals( 1, test_list.size());
            //  Get 1st entry in the list
            DB_Customer test_customer1 = test_list.get(0);
            //  Check 1st entry
            assertEquals( 2, test_customer1.getCustomer_id());
            assertEquals( "Lib", test_customer1.getFirst_name());
            assertEquals( "Tard", test_customer1.getLast_name());
            assertEquals( "0541073261", test_customer1.getPhone_no());
            assertEquals( 2, test_customer1.getAddress().getAddress_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 005 getCustomers()
     *  Test strong search criteria first_name
     *  ==========
     *  Inputs: Search_Customer[] search_criteria = {
     *                     new Search_Customer( Att_Customer.first_name, "Bill", true)
     *          };
     *          LinkedList<DB_Customer> test_list = dao.getCustomers(search_criteria);
     *  ==========
     *  Expected Outputs:   test_customer1.getCustomer_id() = 1
     *                      test_customer1.getFirst_name() = "Bill"
     *                      test_customer1.getLast_name() = "Birr"
     *                      test_customer1.getPhone_no() = "0951078281"
     *                      test_customer1.getAddress().getAddress_id() = 1
     */
    public void testGetCustomers003() {
        try {
            initializeDatabase();
            Search_Customer[] search_criteria = {
                    new Search_Customer( Att_Customer.first_name, "Bill", true)
            };
            ArrayList<DB_Customer> test_list = dao.getCustomers(search_criteria);
            //  Check the list size
            assertEquals( 1, test_list.size());
            //  Get 1st entry in the list
            DB_Customer test_customer1 = test_list.get(0);
            //  Check 1st entry
            assertEquals( 1, test_customer1.getCustomer_id());
            assertEquals( "Bill", test_customer1.getFirst_name());
            assertEquals( "Birr", test_customer1.getLast_name());
            assertEquals( "0875748271", test_customer1.getPhone_no());
            assertEquals( 1, test_customer1.getAddress().getAddress_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 006 getCustomers()
     *  Test strong search criteria last_name
     *  ==========
     *  Inputs: Search_Customer[] search_criteria = {
     *                     new Search_Customer( Att_Customer.last_name, "Tard", true)
     *          };
     *          LinkedList<DB_Customer> test_list = dao.getCustomers(search_criteria);
     *  ==========
     *  Expected Outputs:   test_customer1.getCustomer_id() = 2
     *                      test_customer1.getFirst_name() = "Lib"
     *                      test_customer1.getLast_name() = "Tard"
     *                      test_customer1.getPhone_no() = "0541073261"
     *                      test_customer1.getAddress().getAddress_id() = 2
     */
    public void testGetCustomers004() {
        try {
            initializeDatabase();
            Search_Customer[] search_criteria = {
                    new Search_Customer( Att_Customer.last_name, "Tard", true)
            };
            ArrayList<DB_Customer> test_list = dao.getCustomers(search_criteria);
            //  Check the list size
            assertEquals( 1, test_list.size());
            //  Get 1st entry in the list
            DB_Customer test_customer1 = test_list.get(0);
            //  Check 1st entry
            assertEquals( 2, test_customer1.getCustomer_id());
            assertEquals( "Lib", test_customer1.getFirst_name());
            assertEquals( "Tard", test_customer1.getLast_name());
            assertEquals( "0541073261", test_customer1.getPhone_no());
            assertEquals( 2, test_customer1.getAddress().getAddress_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 007 getCustomers()
     *  Test strong search criteria phone_no
     *  ==========
     *  Inputs:     Search_Customer[] search_criteria = {
     *                      new Search_Customer( Att_Customer.phone_no, "0541073261", true)
     *              };
     *              LinkedList<DB_Customer> test_list = dao.getCustomers(search_criteria);
     *  ==========
     *  Expected Outputs:   test_customer1.getCustomer_id() = 2
     *                      test_customer1.getFirst_name() = "Lib"
     *                      test_customer1.getLast_name() = "Tard"
     *                      test_customer1.getPhone_no() = "0541073261"
     *                      test_customer1.getAddress().getAddress_id() = 2
     */
    public void testGetCustomers005() {
        try {
            initializeDatabase();
            Search_Customer[] search_criteria = {
                    new Search_Customer( Att_Customer.phone_no, "0541073261", true)
            };
            ArrayList<DB_Customer> test_list = dao.getCustomers(search_criteria);
            //  Check the list size
            assertEquals( 1, test_list.size());
            //  Get 1st entry in the list
            DB_Customer test_customer1 = test_list.get(0);
            //  Check 1st entry
            assertEquals( 2, test_customer1.getCustomer_id());
            assertEquals( "Lib", test_customer1.getFirst_name());
            assertEquals( "Tard", test_customer1.getLast_name());
            assertEquals( "0541073261", test_customer1.getPhone_no());
            assertEquals( 2, test_customer1.getAddress().getAddress_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 008 getCustomers()
     *  Test strong search criteria customer_id
     *  ==========
     *  Inputs:     Search_Customer[] search_criteria = {
     *                      new Search_Customer( Att_Customer.customer_id, "0", true)
     *              };
     *              LinkedList<DB_Customer> test_list = dao.getCustomers(search_criteria);
     *  ==========
     *  Expected Outputs:   test_list.size() = 0
     */
    public void testGetCustomers006() {
        try {
            initializeDatabase();
            Search_Customer[] search_criteria = {
                    new Search_Customer( Att_Customer.customer_id, "0", true)
            };
            ArrayList<DB_Customer> test_list = dao.getCustomers(search_criteria);
            //  Check the list size
            assertEquals( 0, test_list.size());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 009 updateCustomer()
     *  Test new customer insertion
     *  ==========
     *  Inputs:     DB_Customer test_customer = new DB_Customer(0, "Ben", "Dover", "0123456789", dao.getAddress(3));
     *  ==========
     *  Expected Outputs:   test_customer.equals( dao.getCustomer(6)) = true
     */
    public void testUpdateCustomer001() {
        try {
            initializeDatabase();
            //  Create new customer
            DB_Customer test_customer = new DB_Customer(0, "Ben", "Dover", "0123456789", dao.getAddress(3));
            //  Issue update
            dao.updateCustomer(test_customer);
            //  Asses the new ID
            assertEquals( 24, test_customer.getCustomer_id());
            //  Compare customer
            assertTrue(test_customer.equals( dao.getCustomer(24)));
        } catch (DAOExceptionHandler | DB_CustomerExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 010 updateCustomer()
     *  Test customer update
     *  ==========
     *  Inputs:     DB_Customer test_customer = dao.getCustomer(6);
     *              test_customer.setFirst_name("Bean");
     *              test_customer.setLast_name("Dever");
     *              test_customer.setPhone_no("0987654321");
     *              test_customer.setAddress( dao.getAddress(2));
     *  ==========
     *  Expected Outputs:   test_customer.equals( dao.getCustomer(6)) = true
     */
    public void testUpdateCustomer002() {
        try {
            initializeDatabase();
            //  Create new customer
            DB_Customer test_customer = dao.getCustomer(6);
            //  Make changes
            test_customer.setFirst_name("Bean");
            test_customer.setLast_name("Dever");
            test_customer.setPhone_no("0987654321");
            test_customer.setAddress( dao.getAddress(2));
            //  Issue update
            dao.updateCustomer(test_customer);
            //  Compare customer
            assertTrue(test_customer.equals( dao.getCustomer(6)));
        } catch (DAOExceptionHandler | DB_CustomerExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 011 updateCustomer()
     *  Test update for non-existing customer
     *  ==========
     *  Inputs:     DB_Customer test_customer = new DB_Customer();
     *              test_customer.setCustomer_id( 24);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "There was customer_id mishandling."
     */
    public void testUpdateCustomer003() {
        try {
            initializeDatabase();
            //  Create new customer
            DB_Customer test_customer = new DB_Customer();
            test_customer.setCustomer_id( 24);
            //  Issue update
            dao.updateCustomer(test_customer);
            fail("Exception not expected.");
        } catch (DAOExceptionHandler | DB_CustomerExceptionHandler e) {
            assertEquals( "There was customer_id mishandling.", e.getMessage());
        }
    }

    /** TEST 012 deleteCustomer()
     *  Test customer deletion
     *  ==========
     *  Inputs:    DB_Customer test_customer = new DB_Customer();
     *             test_customer.setCustomer_id( 6);
     *             dao.deleteCustomer( test_customer);
     *             dao.getCustomer( 6);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "No customer with customer_id = 6 not found."
     */
    public void testDeleteCustomer001() {
        try {
            initializeDatabase();
            DB_Customer test_customer = new DB_Customer();
            test_customer.setCustomer_id( 6);
            dao.deleteCustomer( test_customer);
            dao.getCustomer( 6);
            fail("Exception expected.");
        } catch (DAOExceptionHandler e) {
            assertEquals( "No customer with customer_id = 6 not found.", e.getMessage());
        } catch (DB_CustomerExceptionHandler e) {
            e.printStackTrace();
            fail("DB_CustomerExceptionHandler Exception not expected.");
        }
    }

    /** TEST 012 deleteCustomer()
     *  Test customer deletion failure for no customer found
     *  ==========
     *  Inputs:    DB_Customer test_customer = new DB_Customer();
     *             test_customer.setCustomer_id( 6);
     *             dao.deleteCustomer( test_customer);
     *             dao.getCustomer( 6);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "Cannot delete, customer with ID = '6', does not exist in the database."
     */
    public void testDeleteCustomer002() {
        try {
            initializeDatabase();
            DB_Customer test_customer = new DB_Customer();
            test_customer.setCustomer_id( 24);
            dao.deleteCustomer( test_customer);
            fail("Exception expected.");
        } catch (DAOExceptionHandler e) {
            assertEquals( "Cannot delete, customer with ID = '24', does not exist in the database.", e.getMessage());
        } catch (DB_CustomerExceptionHandler e) {
            e.printStackTrace();
            fail("DB_CustomerExceptionHandler Exception not expected.");
        }
    }
}