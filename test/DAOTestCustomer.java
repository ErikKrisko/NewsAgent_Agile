import junit.framework.TestCase;

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
    public DAOTestCustomer() {
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

    /** Test for customer not found.
     *  ==========
     *  Inputs: int ID = 0
     *          dao.getCustomer(ID);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "No customer with customer_id = " + ID + " not found."
     */
    public void testGetCustomer001(){
        int ID = -1;
        try {
            dao.getCustomer(ID);
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("No customer with customer_id = " + ID + " not found.", e.getMessage());
        }
    }

    /** Test for customer id = 1
     *  ==========
     *  Inputs: int ID = 1
     *          DB_Customer test_customer = dao.getCustomer(ID);
     *  ==========
     *  Expected Outputs:   test_customer.getCustomer_id() = 1
     *                      test_customer.getFirst_name() = "Bill"
     *                      test_customer.getLast_name() = "Birr"
     *                      test_customer.getPhone_no() = "0951078281"
     *                      test_customer.getAddress().getAddress_id() = 1
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

    /** Test to get all customers
     *  ==========
     *  Inputs: LinkedList<DB_Customer> test_list = dao.getCustomers(null);
     *  ==========
     *  Expected Outputs:   test_customer1.getCustomer_id() = 1
     *                      test_customer1.getFirst_name() = "Bill"
     *                      test_customer1.getLast_name() = "Birr"
     *                      test_customer1.getPhone_no() = "0951078281"
     *                      test_customer1.getAddress().getAddress_id() = 1
     *                      -----------------------------------------------
     *                      test_customer2.getCustomer_id() = 5
     *                      test_customer2.getFirst_name() = "Conor"
     *                      test_customer2.getLast_name() = "Demain"
     *                      test_customer2.getPhone_no() = "0725014729"
     *                      test_customer2.getAddress().getAddress_id() = 5
     */
    public void testGetCustomers001() {
        try {
            LinkedList<DB_Customer> test_list = dao.getCustomers(null);
            //  Check the list size
            assertEquals( 5, test_list.size());
            //  Get 1st entry in the list
            DB_Customer test_customer1 = test_list.get(0);
            //  Check 1st entry
            assertEquals( 1, test_customer1.getCustomer_id());
            assertEquals( "Bill", test_customer1.getFirst_name());
            assertEquals( "Birr", test_customer1.getLast_name());
            assertEquals( "0951078281", test_customer1.getPhone_no());
            assertEquals( 1, test_customer1.getAddress().getAddress_id());
            //  Get last entry in the list
            DB_Customer test_customer2 = test_list.getLast();
            assertEquals( 5, test_customer2.getCustomer_id());
            assertEquals( "Conor", test_customer2.getFirst_name());
            assertEquals( "Demain", test_customer2.getLast_name());
            assertEquals( "0725014729", test_customer2.getPhone_no());
            assertEquals( 5, test_customer2.getAddress().getAddress_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** Test weak search functionality
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
            Search_Customer[] search_criteria = {
                    new Search_Customer( Att_Customer.first_name, "%i%", false),
                    new Search_Customer( Att_Customer.last_name, "%r%", false),
                    new Search_Customer( Att_Customer.phone_no, "%61", false)
            };
            LinkedList<DB_Customer> test_list = dao.getCustomers(search_criteria);
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

    /** Test strong search criteria first_name
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
            Search_Customer[] search_criteria = {
                    new Search_Customer( Att_Customer.first_name, "Bill", true)
            };
            LinkedList<DB_Customer> test_list = dao.getCustomers(search_criteria);
            //  Check the list size
            assertEquals( 1, test_list.size());
            //  Get 1st entry in the list
            DB_Customer test_customer1 = test_list.get(0);
            //  Check 1st entry
            assertEquals( 1, test_customer1.getCustomer_id());
            assertEquals( "Bill", test_customer1.getFirst_name());
            assertEquals( "Birr", test_customer1.getLast_name());
            assertEquals( "0951078281", test_customer1.getPhone_no());
            assertEquals( 1, test_customer1.getAddress().getAddress_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** Test strong search criteria last_name
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
            Search_Customer[] search_criteria = {
                    new Search_Customer( Att_Customer.last_name, "Tard", true)
            };
            LinkedList<DB_Customer> test_list = dao.getCustomers(search_criteria);
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

    /** Test strong search criteria phone_no
     *  ==========
     *  Inputs:     Search_Customer[] search_criteria = {
     *                      new Search_Customer( Att_Customer.phone_no, "0911078281", true)
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
            Search_Customer[] search_criteria = {
                    new Search_Customer( Att_Customer.phone_no, "0911078281", true)
            };
            LinkedList<DB_Customer> test_list = dao.getCustomers(search_criteria);
            //  Check the list size
            assertEquals( 1, test_list.size());
            //  Get 1st entry in the list
            DB_Customer test_customer1 = test_list.get(0);
            //  Check 1st entry
            assertEquals( 3, test_customer1.getCustomer_id());
            assertEquals( "Joe", test_customer1.getFirst_name());
            assertEquals( "Joyce", test_customer1.getLast_name());
            assertEquals( "0911078281", test_customer1.getPhone_no());
            assertEquals( 3, test_customer1.getAddress().getAddress_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** Test strong search criteria customer_id
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
            Search_Customer[] search_criteria = {
                    new Search_Customer( Att_Customer.customer_id, "0", true)
            };
            LinkedList<DB_Customer> test_list = dao.getCustomers(search_criteria);
            //  Check the list size
            assertEquals( 0, test_list.size());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** Test new customer insertion
     *  ==========
     *  Inputs:     Search_Customer[] search_criteria = {
     *                      new Search_Customer( Att_Customer.customer_id, "0", true)
     *              };
     *              LinkedList<DB_Customer> test_list = dao.getCustomers(search_criteria);
     *  ==========
     *  Expected Outputs:   test_list.size() = 0
     */
    public void testUpdateCustomer001() {
        try {
            //  Create new customer
            DB_Customer test_customer = new DB_Customer(0, "Ben", "Dover", "0123456789", dao.getAddress(3));
            //  Issue update
            dao.updateCustomer(test_customer);
            //  Asses the new ID
            assertEquals( 6, test_customer.getCustomer_id());
            //  Get customer from database
            //  WIP
        } catch (DAOExceptionHandler | DB_CustomerExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }

    }



/*
    public void testUpdateCustomer() {
    }

    public void testDeleteCustomer() {
    }
*/
}