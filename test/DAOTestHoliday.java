import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;

public class DAOTestHoliday extends TestCase {
    //  Global dao object reference
    private DAO dao;

    /** Initialize test environment.
     *  ==========
     *  Inputs: JDBC initialization and script execution
     *          dao initialization
     *  ==========
     *  Expected Outputs:   None
     */
    public DAOTestHoliday() {
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

    /** TEST 001    getHolidays()
     *  Testing for empty holidays list for new / non-existing customer
     *
     */
    public void testGetHolidays001() {
        try {
            ArrayList<DB_Holiday> holiday_list = dao.getHolidays( new DB_Customer());
            //  Check if no holidays returned
            assertNull( holiday_list);
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 002    getHolidays()
     *  Testing for holidays for given customer.
     *
     */
    public void testGetHolidays002() {
        try {
            ArrayList<DB_Holiday> holiday_list = dao.getHolidays( dao.getCustomer(1));
            //  Check return size
            assertEquals( 1, holiday_list.size());
            //  Get first entry
            DB_Holiday test_holiday = holiday_list.get(0);
            //  Compare information inside of it
            assertEquals( 1, test_holiday.getHoliday_id());
            assertEquals( Date.valueOf("2021-02-10"), test_holiday.getStart_date());
            assertEquals( Date.valueOf("2021-02-15"), test_holiday.getEnd_date());
            assertTrue( dao.getCustomer( 1).equals( test_holiday.getCustomer()));
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 003    updateHoliday()
     *  Testing for new holiday insertion
     *
     */
    public void testUpdateHolidays002() {
        try {
            DB_Holiday test_holiday = new DB_Holiday(0, Date.valueOf("2021-03-01"), Date.valueOf("2021-03-08"), dao.getCustomer( 1));

        } catch (DAOExceptionHandler | DB_HolidayExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /*
    public void testGetHolidays() { }
    public void testUpdateHoliday() { }
    public void testDeleteHoliday() { }
     */
}