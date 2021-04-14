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

    /** TEST 001    getHolidays()
     *  Testing for empty holidays list for new / non-existing customer
     *  ==========
     *  Inputs: ArrayList<DB_Holiday> holiday_list = dao.getHolidays( 0);
     *  ==========
     *  Expected Outputs:   holiday_list = null;
     */
    public void testGetHolidays001() {
        try {
            initializeDatabase();
            ArrayList<DB_Holiday> holiday_list = dao.getHolidays( 0);
            //  Check if no holidays returned
            assertNull( holiday_list);
            //  Close the DAO
            dao.close();
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 002    getHolidays()
     *  Testing for holidays for given customer.
     *  ==========
     *  Inputs: ArrayList<DB_Holiday> holiday_list = dao.getHolidays( 1);
     *  ==========
     *  Expected Outputs:   holiday_list.get(0).getHoliday_id() = 1
     *                      holiday_list.get(0).getStart_date() = 2021-02-10
     *                      holiday_list.get(0).getEnd_date() = 2021-02-15
     *                      holiday_list.get(0).getCustomer_id() = 1
     *
     *                      holiday_list.get(0).getHoliday_id() = 2
     *                      holiday_list.get(0).getStart_date() = 2021-01-01
     *                      holiday_list.get(0).getEnd_date() = 2021-01-16
     *                      holiday_list.get(0).getCustomer_id() = 1
     */
    public void testGetHolidays002() {
        try {
            initializeDatabase();
            ArrayList<DB_Holiday> holiday_list = dao.getHolidays( 1);
            //  Check return size
            assertEquals( 2, holiday_list.size());
            //  Get first entry
            DB_Holiday test_holiday = holiday_list.get(0);
            //  Compare information inside of it
            assertEquals( 1, test_holiday.getHoliday_id());
            assertEquals( Date.valueOf("2021-02-10"), test_holiday.getStart_date());
            assertEquals( Date.valueOf("2021-02-15"), test_holiday.getEnd_date());
            assertEquals( 1, test_holiday.getCustomer_id());
            //  Get Second entry
            test_holiday = holiday_list.get(1);
            //  Compare information inside of it
            assertEquals( 2, test_holiday.getHoliday_id());
            assertEquals( Date.valueOf("2021-01-01"), test_holiday.getStart_date());
            assertEquals( Date.valueOf("2021-01-16"), test_holiday.getEnd_date());
            assertEquals( 1, test_holiday.getCustomer_id());
            //  Close the DAO
            dao.close();
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 003    updateHoliday()
     *  Testing for new holiday insertion
     *  ==========
     *  Inputs:     DB_Holiday test_holiday = new DB_Holiday(0, Date.valueOf("2021-03-01"), Date.valueOf("2021-03-08"), 1);
     *              dao.updateHoliday(test_holiday);
     *  ==========
     *  Expected Outputs:   Holiday in the database
     */
    public void testUpdateHoliday001() {
        try {
            initializeDatabase();
            DB_Holiday test_holiday = new DB_Holiday(0, Date.valueOf("2021-03-01"), Date.valueOf("2021-03-08"), 1);
            dao.updateHoliday(test_holiday);
            //  Check for newly assigned ID
            assertEquals( 11, test_holiday.getHoliday_id());
            ArrayList<DB_Holiday> holiday_list = dao.getHolidays(1);
            //  Check for the array size
            assertEquals( 3, holiday_list.size());
            //  Isolate newest entry
            DB_Holiday test_holiday2 = holiday_list.get( holiday_list.size() - 1);
            //  Test properties
            assertEquals( 11, test_holiday2.getHoliday_id());
            assertEquals( Date.valueOf("2021-03-01"), test_holiday2.getStart_date());
            assertEquals( Date.valueOf("2021-03-08"), test_holiday2.getEnd_date());
            assertEquals( 1, test_holiday2.getCustomer_id());
            //  Close the DAO
            dao.close();
        } catch (DB_HolidayExceptionHandler | DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 004    updateHoliday()
     *  Testing for holiday update
     *  ==========
     *  Inputs:     DB_Holiday test_holiday = dao.getHoliday( 1);
     *              test_holiday.setStart_date( Date.valueOf( "2021-02-09"));
     *              test_holiday.setEnd_date( Date.valueOf( "2021-02-16"));
     *              test_holiday.setCustomer_id( 2);
     *              dao.updateHoliday( test_holiday);
     *  ==========
     *  Expected Outputs:   Newly pulled holiday matches the inserted holiday
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
            //  Close the DAO
            dao.close();
        } catch (DB_HolidayExceptionHandler | DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 005    updateHoliday()
     *  Testing for holiday update ID mismanagement
     *  ==========
     *  Inputs:    DB_Holiday test_holiday = dao.getHoliday( 1);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "There was holiday_id mishandling."
     */
    public void testUpdateHoliday003() {
        try {
            initializeDatabase();
            //  Get existing entry
            DB_Holiday test_holiday = dao.getHoliday( 1);
            //  Change id
            test_holiday.setHoliday_id( 11);
            //  Issue update
            dao.updateHoliday( test_holiday);
            fail("Exception expected");
        } catch (DB_HolidayExceptionHandler | DAOExceptionHandler e) {
            assertEquals("There was holiday_id mishandling.", e.getMessage());
            //  Close the DAO
            try {
                dao.close();
            } catch (DAOExceptionHandler f) {
                f.printStackTrace();
                fail("Failed in closing dao.");
            }
        }
    }

    /** TEST 006    getHoliday()
     *  Testing for get holiday id not found
     *  ==========
     *  Inputs:    dao.getHoliday( 11);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "No holiday found for holiday_id = 11"
     */
    public void testGetHoliday001() {
        try {
            initializeDatabase();
            dao.getHoliday( 11);
            fail("Exception expected.");
        } catch (DAOExceptionHandler e) {
            assertEquals( "No holiday found for holiday_id = 11", e.getMessage());
            //  Close the DAO
            try {
                dao.close();
            } catch (DAOExceptionHandler f) {
                f.printStackTrace();
                fail("Failed in closing dao.");
            }
        }
    }

    /** TEST 007    getHoliday()
     *  Testing for get holiday by id
     *  ==========
     *  Inputs:    DB_Holiday test_holiday = dao.getHoliday( 1);
     *  ==========
     *  Expected Outputs:       test_holiday.getHoliday_id() = 1
     *                          test_holiday.getStart_date() = 2021-02-10
     *                          test_holiday.getEnd_date() = 2021-02-15
     *                          test_holiday.getCustomer_id() = 1
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
            //  Close the DAO
            dao.close();
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /** TEST 008    deleteHoliday()
     *  Testing for delete fail
     *  ==========
     *  Inputs:    DB_Holiday test_holiday = new DB_Holiday();
     *             test_holiday.setHoliday_id( 11);
     *  ==========
     *  Expected Outputs:       DAOExceptionHandler = "Cannot delete, holiday with ID = '11', does not exist in the database."
     */
    public void testDeleteHoliday001() {
        try {
            initializeDatabase();
            DB_Holiday test_holiday = new DB_Holiday();
            test_holiday.setHoliday_id( 11);
            //  Issue delete
            dao.deleteHoliday( test_holiday);
            fail("Exception expected.");
        } catch (DB_HolidayExceptionHandler | DAOExceptionHandler e) {
            assertEquals( "Cannot delete, holiday with ID = '11', does not exist in the database.", e.getMessage());
            //  Close the DAO
            try {
                dao.close();
            } catch (DAOExceptionHandler f) {
                f.printStackTrace();
                fail("Failed in closing dao.");
            }
        }
    }

    /** TEST 009    deleteHoliday()
     *  Testing for successful delete
     *  ==========
     *  Inputs:     DB_Holiday test_holiday = dao.getHoliday( 1);
     *              dao.deleteHoliday( test_holiday);
     *              dao.getHoliday( 1);
     *  ==========
     *  Expected Outputs:   DAOExceptionHandler = "No holiday found for holiday_id = 1"
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
            //  Close the DAO
            try {
                dao.close();
            } catch (DAOExceptionHandler f) {
                f.printStackTrace();
                fail("Failed in closing dao.");
            }
        }
    }
}