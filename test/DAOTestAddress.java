import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;

public class DAOTestAddress extends TestCase {
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
            //  Close any existing connection if it exists.
            if (dao != null && !dao.isClosed()) {
                dao.close();
            }
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

    /** TEST 001    getAddress()
     *  Testing for address id 1
     *  ==========
     *  Inputs:    DB_Address test_address = dao.getAddress( 1);
     *  ==========
     *  Expected Outputs:   test_address.getAddress_id() = 1
     *                      test_address.getFull_address() = "103 Lana Road"
     *                      test_address.getArea_code() = 1
     *                      test_address.getEir_code() = "A98CA34"
     */
    public void testGetAddress001() {
        try {
            initializeDatabase();
            //  Get address
            DB_Address test_address = dao.getAddress( 2);
            //  Compare address
            assertEquals( 2, test_address.getAddress_id());
            assertEquals( "103 Lana Road", test_address.getFull_address());
            assertEquals( 1, test_address.getArea_code());
            assertEquals( "A98CA34", test_address.getEir_code());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** TEST 002    getAddress()
     *  Testing for address id not found
     *  ==========
     *  Inputs:    DB_Address test_address = dao.getAddress( 22);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "No address with 'address_id = 22 found."
     */
    public void testGetAddress002() {
        try {
            initializeDatabase();
            dao.getAddress( 22);
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals( "No address with 'address_id = 22 found.", e.getMessage());
        }
    }

    /** TEST 003    updateAddress()
     *  Testing for new address insertion
     *  ==========
     *  Inputs:    DB_Address test_address = new DB_Address(0, "31 Creak Way, Cork City", "B3", "RD54AW6");
     *  ==========
     *  Expected Outputs:   test_address.equals( dao.getAddress(6)) = true
     */
    public void testUpdateAddress001() {
        try {
            initializeDatabase();
            DB_Address test_address = new DB_Address(0, "31 Creak Way", 2, "RD54AW6");
            dao.updateAddress(test_address);
            assertEquals( 22, test_address.getAddress_id());
            test_address.equals( dao.getAddress(6));
        } catch (DAOExceptionHandler | DB_AddressExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 004    updateAddress()
     *  Testing for address update
     *  ==========
     *  Inputs:    DB_Address test_address = dao.getAddress( 6);
     *             test_address.setFull_address( "29 Crack Road");
     *             test_address.setArea_code( "D4");
     *             test_address.setEir_code( "BL12W33");
     *  ==========
     *  Expected Outputs:   test_address.equals( dao.getAddress(6)) = true
     */
    public void testUpdateAddress002() {
        try {
            initializeDatabase();
            //  Get existing address
            DB_Address test_address = dao.getAddress( 6);
            //  Make changes
            test_address.setFull_address( "29 Crack Road");
            test_address.setArea_code( 3);
            test_address.setEir_code( "BL12W33");
            //  Issue update
            dao.updateAddress(test_address);
            //  Compare
            test_address.equals( dao.getAddress(6));
        } catch (DAOExceptionHandler | DB_AddressExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 005    updateAddress()
     *  Testing for address update id mishandling
     *  ==========
     *  Inputs:    DB_Address test_address = new DB_Address();
     *             test_address.setAddress_id( 7);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "There was address_id mishandling."
     */
    public void testUpdateAddress003() {
        try {
            initializeDatabase();
            //  Get existing address
            DB_Address test_address = new DB_Address();
            //  Make changes
            test_address.setAddress_id( 22);
            //  Issue update
            dao.updateAddress(test_address);
            fail("Exception expected.");
        } catch (DAOExceptionHandler | DB_AddressExceptionHandler e) {
            assertEquals( "There was address_id mishandling.", e.getMessage());
        }
    }

    /** TEST 006    deleteAddress()
     *  Testing for address deletion
     *  ==========
     *  Inputs:    DB_Address test_address = new DB_Address();
     *             test_address.setAddress_id( 6);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "No address with 'address_id = 6 found."
     */
    public void testDeleteAddress001() {
        try {
            initializeDatabase();
            //  Get existing address
            DB_Address test_address = dao.getAddress( 6);
            //  Issue delete
            dao.deleteAddress(test_address);
            //  Check for address
            dao.getAddress( 6);
        } catch (DAOExceptionHandler  e) {
            assertEquals( "No address with 'address_id = 6 found.", e.getMessage());
        }
    }

    /** TEST 007    deleteAddress()
     *  Testing for address deletion error
     *  ==========
     *  Inputs:    DB_Address test_address = new DB_Address();
     *             test_address.setAddress_id( 7);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "No address with 'address_id = 6 found."
     */
    public void testDeleteAddress002() {
        try {
            initializeDatabase();
            //  Get existing address
            DB_Address test_address = dao.getAddress( 6);
            //  Make changes
            test_address.setAddress_id( 6);
            //  Issue delete
            dao.deleteAddress(test_address);
        } catch (DAOExceptionHandler  e) {
            assertEquals( "No address with 'address_id = 6 found.", e.getMessage());
        } catch (DB_AddressExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 008    deleteAddress()
     *  Testing for address deletion of un-inserted address
     *  ==========
     *  Inputs:    DB_Address test_address = new DB_Address(0, "Some", "T1", "BL54D12");
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "Address was not inserted into the database."
     */
    public void testDeleteAddress003() {
        try {
            initializeDatabase();
            //  Get existing address
            DB_Address test_address = new DB_Address(0, "Some", 3, "BL54D12");
            //  Try delete
            dao.deleteAddress( test_address);
        } catch (DAOExceptionHandler  e) {
            assertEquals( "Address was not inserted into the database.", e.getMessage());
        } catch (DB_AddressExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 009    getAddressesBySubscriptions()
     *  Testing for getting addresses for given list of Subscriptions
     *  ==========
     *  Inputs:
     *              ArrayList<DB_Publication> prod_list = dao.getPublicationsByDate(Date.valueOf("2021-04-14"));
     *              ArrayList<DB_Subscription> sub_list = dao.getSubscriptionsByPublications(prod_list);
     *              ArrayList<DB_Address> addresses = dao.getAddressesBySubscriptions(sub_list);
     *  ==========
     *  Expected Outputs:   addresses.size() = 17
     *                      addresses.get(0).getAddress_id() = 1
     *                      addresses.get(4).getAddress_id() = 6
     *                      addresses.get(16).getAddress_id() = 21
     *
     *  Query that should execute: "SELECT DISTINCT a.address_id, a.full_address, a.area_code, a.eir_code FROM address AS a, customer AS c WHERE c.customer_id IN (1,2,3,4,7,8,9,10,10,10,12,14,15,17,18,18,20,21,22,23) && c.address_id = a.address_id;"
     */
    public void testGetAddressBySubscription001() {
        try {
            initializeDatabase();
            //  Pre variables
            ArrayList<DB_Publication> prod_list = dao.getPublicationsByDate(Date.valueOf("2021-04-14"));
            ArrayList<DB_Subscription> sub_list = dao.getSubscriptionsByPublications(prod_list);
            //  Get address list
            ArrayList<DB_Address> addresses = dao.getAddressesBySubscriptions(sub_list);
            //  Check ids
            assertEquals(17, addresses.size());
            assertEquals(1, addresses.get(0).getAddress_id());
            assertEquals(6, addresses.get(4).getAddress_id());
            assertEquals(21, addresses.get(16).getAddress_id());
        } catch (DAOExceptionHandler  e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /** TEST 010    getAddressesBySubscriptions()
     *  Testing for getting addresses from empty list
     *  ==========
     *  Inputs:     ArrayList<DB_Address> addresses = dao.getAddressesBySubscriptions(new ArrayList<DB_Subscription>);
     *  ==========
     *  Expected Outputs:
     */
    public void testGetAddressBySubscription002() {
        try {
            initializeDatabase();
            //  Pre variables
            ArrayList<DB_Address> addresses = dao.getAddressesBySubscriptions(new ArrayList<DB_Subscription>());
            //  Check for null
            assertNull(addresses);
        } catch (DAOExceptionHandler  e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /** TEST 011    getAddressesBySubscriptions()
     *  Testing for getting addresses from null list
     *  ==========
     *  Inputs:     ArrayList<DB_Address> addresses = dao.getAddressesBySubscriptions(new ArrayList<DB_Subscription>);
     *  ==========
     *  Expected Outputs:
     */
    public void testGetAddressBySubscription003() {
        try {
            initializeDatabase();
            //  Pre variables
            dao.getAddressesBySubscriptions(null);
            //  Check ids
            fail("Exception expected");
        } catch (DAOExceptionHandler  e) {
            assertEquals( "A null list was provided.", e.getMessage());
        }
    }

}