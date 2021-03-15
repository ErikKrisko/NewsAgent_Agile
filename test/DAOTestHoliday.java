import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;

public class DAOTestHoliday extends TestCase {
    //  Global dao object reference
    private DAO dao;

    /** Preemptive method for resetting database to be used as an initial starting point of each test.
     *  Will need to change existing test to use this system too for be, most reliable results.
     */
    private void initializeDatabase() {
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
            initializeDatabase();
            ArrayList<DB_Holiday> holiday_list = dao.getHolidays( 0);
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
            initializeDatabase();
            ArrayList<DB_Holiday> holiday_list = dao.getHolidays( 1);
            //  Check return size
            assertEquals( 1, holiday_list.size());
            //  Get first entry
            DB_Holiday test_holiday = holiday_list.get(0);
            //  Compare information inside of it
            assertEquals( 1, test_holiday.getHoliday_id());
            assertEquals( Date.valueOf("2021-02-10"), test_holiday.getStart_date());
            assertEquals( Date.valueOf("2021-02-15"), test_holiday.getEnd_date());
            assertEquals( 1, test_holiday.getCustomer_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 003    updateHoliday()
     *  Testing for new holiday insertion
     *
     */
    public void testUpdateHoliday001() {
        try {
            initializeDatabase();
            DB_Holiday test_holiday = new DB_Holiday(0, Date.valueOf("2021-03-01"), Date.valueOf("2021-03-08"), 1);
            dao.updateHoliday(test_holiday);
            //  Check for newly assigned ID
            assertEquals( 6, test_holiday.getHoliday_id());
            ArrayList<DB_Holiday> holiday_list = dao.getHolidays(1);
            //  Check for the array size
            assertEquals( 2, holiday_list.size());
            //  Isolate newest entry
            DB_Holiday test_holiday2 = holiday_list.get( holiday_list.size() - 1);
            //  Test properties
            assertEquals( 6, test_holiday2.getHoliday_id());
            assertEquals( Date.valueOf("2021-03-01"), test_holiday2.getStart_date());
            assertEquals( Date.valueOf("2021-03-08"), test_holiday2.getEnd_date());
            assertEquals( 1, test_holiday2.getCustomer_id());
        } catch (DB_HolidayExceptionHandler | DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 004    updateHoliday()
     *  Testing for holiday update
     *
     */
    public void testUpdateHoliday002() {
        try {
            initializeDatabase();
            //  Get existing entry
            DB_Holiday test_holiday = dao.getHoliday( 1);
            //  Change values
            test_holiday.setStart_date( Date.valueOf( "2021-02-09"));
            test_holiday.setEnd_date( Date.valueOf( "2021-02-16"));
            test_holiday.setCustomer_id( 2);
            //  Issue update
            dao.updateHoliday( test_holiday);
            //  Get the new holiday
            DB_Holiday test_holiday2 = dao.getHoliday( 1);
            assertTrue( test_holiday2.equals( test_holiday));
        } catch (DB_HolidayExceptionHandler | DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 005    updateHoliday()
     *  Testing for holiday update ID mismanagement
     *
     */
    public void testUpdateHoliday003() {
        try {
            initializeDatabase();
            //  Get existing entry
            DB_Holiday test_holiday = dao.getHoliday( 1);
            //  Change id
            test_holiday.setHoliday_id( 6);
            //  Issue update
            dao.updateHoliday( test_holiday);
            fail("Exception expected");
        } catch (DB_HolidayExceptionHandler | DAOExceptionHandler e) {
            assertEquals("There was holiday_id mishandling.", e.getMessage());
        }
    }

    /** TEST 006    getHoliday()
     *  Testing for get holiday id not found
     */
    public void testGetHoliday001() {
        try {
            initializeDatabase();
            DB_Holiday test_holiday = dao.getHoliday( 6);
            fail("Exception expected.");
        } catch (DAOExceptionHandler e) {
            assertEquals( "No holiday found for holiday_id = 6", e.getMessage());
        }
    }

    /** TEST 007    getHoliday()
     *  Testing for get holiday id invalid
     */
    public void testGetHoliday002() {
        try {
            initializeDatabase();
            DB_Holiday test_holiday = dao.getHoliday( 1);
            //  Test properties
            assertEquals( 1, test_holiday.getHoliday_id());
            assertEquals( Date.valueOf("2021-02-10"), test_holiday.getStart_date());
            assertEquals( Date.valueOf("2021-02-15"), test_holiday.getEnd_date());
            assertEquals( 1, test_holiday.getCustomer_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 008    deleteHoliday()
     *  Testing for delete fail
     *
     */
    public void testDeleteHoliday001() {
        try {
            initializeDatabase();
            DB_Holiday test_holiday = new DB_Holiday();
            test_holiday.setHoliday_id( 6);
            //  Issue delete
            dao.deleteHoliday( test_holiday);
            fail("Exception expected.");
        } catch (DB_HolidayExceptionHandler | DAOExceptionHandler e) {
            assertEquals( "Cannot delete, holiday with ID = '6', does not exist in the database.", e.getMessage());
        }
    }

    /** TEST 009    deleteHoliday()
     *  Testing for successful delete
     */
    public void testDeleteHoliday002() {
        try {
            initializeDatabase();
            DB_Holiday test_holiday = dao.getHoliday( 1);
            //  Issue delete
            dao.deleteHoliday( test_holiday);
            //  Try obtaining the holiday again
            dao.getHoliday( 1);
            fail("Exception expected.");
        } catch (DAOExceptionHandler e) {
            assertEquals( "No holiday found for holiday_id = 1", e.getMessage());
        }
    }

    /*
    public void testGetHolidays() { }
    public void testUpdateHoliday() { }
    public void testDeleteHoliday() { }
     */
}