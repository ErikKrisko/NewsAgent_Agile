import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;

public class DocketTest extends TestCase {
    private DAO dao;

    public DocketTest() {
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

    /** TEST 001
     *  Test constructor
     *  ==========
     *  Inputs:    DB_Address test_address = dao.getAddress( 1);
     *  ==========
     *  Expected Outputs:   test_address.getAddress_id() = 1
     *                      test_address.getFull_address() = "103 Lana Road Clara Co.Offaly"
     *                      test_address.getArea_code() = "B1"
     *                      test_address.getEir_code() = "R45YW41"
     */
    public void testDocket001() {
        try {
            Docket test = new Docket(new DB_Employee(), new ArrayList<DB_Delivery>(), Date.valueOf("2000-01-01"));
            assertEquals( Date.valueOf("2000-01-01"), test.getDate());
            assertNull( test.getDeliveries());
        } catch (DocketExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** TEST 002
     *  Test inserting incorrect date delivery
     *  ==========
     *  Inputs:    DB_Address test_address = dao.getAddress( 1);
     *  ==========
     *  Expected Outputs:   test_address.getAddress_id() = 1
     *                      test_address.getFull_address() = "103 Lana Road Clara Co.Offaly"
     *                      test_address.getArea_code() = "B1"
     *                      test_address.getEir_code() = "R45YW41"
     */
    public void testDocket002() {
        try {
            Docket test = new Docket();
            test.setDate(Date.valueOf("2022-01-06"));
            test.addDelivery(dao.getDelivery(1));
            fail("Exception expected");
        } catch (DocketExceptionHandler | DAOExceptionHandler e) {
            e.printStackTrace();
        }
    }

    /** TEST 005    addDelivery()
     *  Testing for address id 1
     *  ==========
     *  Inputs:    DB_Address test_address = dao.getAddress( 1);
     *  ==========
     *  Expected Outputs:   test_address.getAddress_id() = 1
     *                      test_address.getFull_address() = "103 Lana Road Clara Co.Offaly"
     *                      test_address.getArea_code() = "B1"
     *                      test_address.getEir_code() = "R45YW41"
     */
    public void testDocket005() {
        try {
            Docket test = new Docket();
            test.setDate(Date.valueOf("2022-01-05"));
            test.addDelivery(dao.getDelivery(1));
        } catch (DocketExceptionHandler | DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected");
        }
    }


}