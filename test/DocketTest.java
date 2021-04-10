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
     *  Inputs:    Docket test = new Docket(new DB_Employee(), new ArrayList<>(), Date.valueOf("2000-01-01"));
     *  ==========
     *  Expected Outputs:   Not a fail
     */
    public void testDocket001() {
        try {
            Docket test = new Docket(new ArrayList<>(), Date.valueOf("2000-01-01"));
            assertEquals( Date.valueOf("2000-01-01"), test.getDate());
            assertEquals(0, test.getDeliveries().size());
        } catch (DocketExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** TEST 002
     *  Test inserting incorrect date delivery with failed constructor
     *  ==========
     *  Inputs:     ArrayList<DB_Delivery> testList = new ArrayList<>();
     *              testList.add(dao.getDelivery(1));
     *              Docket test = new Docket(new DB_Employee(), testList, Date.valueOf("2022-01-06"));
     *  ==========
     *  Expected Outputs:   DocketExceptionHandler = "A delivery does not match docket date."
     */
    public void testDocket002() {
        try {
            ArrayList<DB_Delivery> testList = new ArrayList<>();
            testList.add(dao.getDelivery(1));
            Docket test = new Docket(testList, Date.valueOf("2022-01-06"));
            fail("Exception expected");
        } catch (DocketExceptionHandler | DAOExceptionHandler e) {
            assertEquals("A delivery does not match docket date.", e.getMessage());
        }
    }

    /** TEST 003
     *  Testing for inserting delivery id 1 of matching date
     *  ==========
     *  Inputs:    Docket test = new Docket();
     *             test.setDate(Date.valueOf("2022-01-05"));
     *             test.setArea_code(0);
     *             test.addDelivery(dao.getDelivery(1));
     *  ==========
     *  Expected Outputs:   Not fail
     */
    public void testDocket003() {
        try {
            Docket test = new Docket();
            test.setDate(Date.valueOf("2022-01-05"));
            test.addDelivery(dao.getDelivery(1));
        } catch (DocketExceptionHandler | DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** TEST 004
     *  Testing for setting of new valid Delivery list
     *  ==========
     *  Inputs:    Docket test = new Docket(new DB_Employee(), new ArrayList<>(), Date.valueOf("2022-01-05"));
     *             ArrayList<DB_Delivery> testList = new ArrayList<>();
     *             testList.add(dao.getDelivery(1));
     *             testList.add(dao.getDelivery(2));
     *             test.setDeliveries(testList);
     *  ==========
     *  Expected Outputs:   Not fail
     */
    public void testDocket004() {
        try {
            Docket test = new Docket(new ArrayList<>(), Date.valueOf("2022-01-05"));
            ArrayList<DB_Delivery> testList = new ArrayList<>();
            testList.add(dao.getDelivery(1));
            testList.add(dao.getDelivery(6));
            test.setDeliveries(testList);
        } catch (DocketExceptionHandler | DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** TEST 005
     *  Testing for setting of new invalid Delivery list
     *  ==========
     *  Inputs:    Docket test = new Docket(new DB_Employee(), new ArrayList<>(), Date.valueOf("2022-01-05"), 0);
     *             ArrayList<DB_Delivery> testList = new ArrayList<>();
     *             testList.add(dao.getDelivery(1));
     *             testList.add(dao.getDelivery(3));
     *             test.setDeliveries(testList);
     *  ==========
     *  Expected Outputs:   DocketExceptionHandler = "A delivery does not match docket date."
     */
    public void testDocket005() {
        try {
            Docket test = new Docket(new ArrayList<>(), Date.valueOf("2022-01-05"));
            ArrayList<DB_Delivery> testList = new ArrayList<>();
            testList.add(dao.getDelivery(1));
            testList.add(dao.getDelivery(2));
            test.setDeliveries(testList);
            fail("Exception expected");
        } catch (DocketExceptionHandler | DAOExceptionHandler e) {
            assertEquals("A delivery does not match docket date.", e.getMessage());
        }
    }

    /** TEST 006
     *  Testing for changing date with delivery list present
     *  ==========
     *  Inputs:    DB_Address test_address = dao.getAddress( 1);
     *  ==========
     *  Expected Outputs:   DocketExceptionHandler = "Invalid Date change. Docket contains existing deliveries of different date."
     */
    public void testDocket006() {
        try {
            ArrayList<DB_Delivery> testList = new ArrayList<>();
            testList.add(dao.getDelivery(1));
            testList.add(dao.getDelivery(6));
            Docket test = new Docket(testList, Date.valueOf("2022-01-05"));
            test.setDate(Date.valueOf("2022-01-06"));
            fail("Exception expected");
        } catch (DocketExceptionHandler | DAOExceptionHandler e) {
            assertEquals("Invalid Date change. Docket contains existing deliveries of different date.", e.getMessage());
        }
    }

    /** TEST 007
     *  Testing for adding of deliveries for docket without a Date
     *  ==========
     *  Inputs:    DB_Address test_address = dao.getAddress( 1);
     *  ==========
     *  Expected Outputs:   DocketExceptionHandler = "Docket does not have a date assigned."
     */
    public void testDocket007() {
        try {
            Docket test = new Docket();
            test.addDelivery(dao.getDelivery(1));
            fail("Exception expected");
        } catch (DocketExceptionHandler | DAOExceptionHandler e) {
            assertEquals("Docket does not have a date assigned.", e.getMessage());
        }
    }

}