import junit.framework.TestCase;

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
    public DAOTestAddress() {
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

    /** TEST 001    getAddress()
     *  Testing for address id 1
     *  ==========
     *  Inputs:    DB_Address test_address = dao.getAddress( 1);
     *  ==========
     *  Expected Outputs:   test_address.getAddress_id() = 1
     *                      test_address.getFull_address() = "103 Lana Road Clara Co.Offaly"
     *                      test_address.getArea_code() = "B1"
     *                      test_address.getEir_code() = "R45YW41"
     */
    public void testGetAddress001() {
        try {
            //  Get address
            DB_Address test_address = dao.getAddress( 2);
            //  Compare address
            assertEquals( 2, test_address.getAddress_id());
            assertEquals( "103 Lana Road Clara Co.Offaly", test_address.getFull_address());
            assertEquals( "B1", test_address.getArea_code());;
            assertEquals( "R45YW41", test_address.getEir_code());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** TEST 002    getAddress()
     *  Testing for address id not found
     *  ==========
     *  Inputs:    DB_Address test_address = dao.getAddress( 6);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = ""
     */
    public void testGetAddress002() {
        try {
            dao.getAddress( 6);
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals( "No address with 'address_id = 6 found.", e.getMessage());
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
            DB_Address test_address = new DB_Address(0, "31 Creak Way, Cork City", "B3", "RD54AW6");
            dao.updateAddress(test_address);
            assertEquals( 6, test_address.getAddress_id());
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
     *             test_address.setFull_address( "29 Crack Road, Dublin");
     *             test_address.setArea_code( "D4");
     *             test_address.setEir_code( "BL12W33");
     *  ==========
     *  Expected Outputs:   test_address.equals( dao.getAddress(6)) = true
     */
    public void testUpdateAddress002() {
        try {
            //  Get existing address
            DB_Address test_address = dao.getAddress( 6);
            //  Make changes
            test_address.setFull_address( "29 Crack Road, Dublin");
            test_address.setArea_code( "D4");
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
            //  Get existing address
            DB_Address test_address = new DB_Address();
            //  Make changes
            test_address.setAddress_id( 7);
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
            //  Get existing address
            DB_Address test_address = new DB_Address(0, "Some", "T1", "BL54D12");
            //  Try delete
            dao.deleteAddress( test_address);
        } catch (DAOExceptionHandler  e) {
            assertEquals( "Address was not inserted into the database.", e.getMessage());
        } catch (DB_AddressExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

}