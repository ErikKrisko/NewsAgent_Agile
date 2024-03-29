import junit.framework.TestCase;

import java.sql.Date;

public class DB_HolidayTest extends TestCase {
    DB_Holiday holiday = new DB_Holiday();

    /** TEST 001
     *  Testing for invalid holiday start date
     *  ==========
     *  Inputs: holiday.validateStartDate( Date.valueOf("1999-12-31"));
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "Date cannot be before the year 2000."
     */
    public void testDB_Holiday001() {
        try {
            holiday.validateStartDate( Date.valueOf("1999-12-31"));
            fail("Exception expected.");
        } catch (DB_HolidayExceptionHandler e) {
            assertEquals("Date cannot be before the year 2000.", e.getMessage());
        }
    }

    /** TEST 002
     *  Testing for valid holiday start date
     *  ==========
     *  Inputs: holiday.validateStartDate( Date.valueOf("2000-01-01"));
     *  ==========
     *  Expected Outputs:   Date = "2000-01-01"
     */
    public void testDB_Holiday002() {
        try {
            assertEquals( Date.valueOf("2000-01-01"), holiday.validateStartDate( Date.valueOf("2000-01-01")));
        } catch (DB_HolidayExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

    /** TEST 003
     *  Testing for invalid end date
     *  ==========
     *  Inputs:    holiday.setStart_date( Date.valueOf("2000-01-10"));
     *             holiday.validateEndDate( Date.valueOf("2000-01-01"));
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "End date has to come after Start date."
     */
    public void testDB_Holiday003() {
        try {
            holiday.setStart_date( Date.valueOf("2000-01-10"));
            holiday.validateEndDate( Date.valueOf("2000-01-01"));
            fail("Exception expected");
        } catch (DB_HolidayExceptionHandler e) {
            assertEquals("End date has to come after Start date.", e.getMessage());
        }
    }

    /** TEST 004
     *  Testing for valid end date
     *  ==========
     *  Inputs:    holiday.setStart_date( Date.valueOf("2000-01-01"));
     *             holiday.validateEndDate( Date.valueOf("2000-01-10"));
     *  ==========
     *  Expected Outputs:   Date = "2000-01-10"
     */
    public void testDB_Holiday004() {
        try {
            holiday.setStart_date( Date.valueOf("2000-01-01"));
            assertEquals( Date.valueOf("2000-01-10"), holiday.validateEndDate( Date.valueOf("2000-01-10")));
        } catch (DB_HolidayExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    /** TEST 005
     *  Testing for lower invalid id
     *  ==========
     *  Inputs:    holiday.validateID(-Long.MAX_VALUE);
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "ID must be 0 or greater."
     */
    public void testDB_Holiday005() {
        try {
            holiday.validateID(-Long.MAX_VALUE);
        } catch (DB_HolidayExceptionHandler e) {
            assertEquals( "ID must be 0 or greater.", e.getMessage());
        }
    }

    /** TEST 006
     *  Testing for upper invalid id and constructor
     *  ==========
     *  Inputs:    new DB_Holiday( -1, Date.valueOf("2000-01-01"), Date.valueOf("2000-01-10"), new DB_Customer());
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "ID must be 0 or greater."
     */
    public void testDB_Holiday006() {
        try {
            new DB_Holiday( -1, Date.valueOf("2000-01-01"), Date.valueOf("2000-01-10"), 1);
            fail("Exception expected");
        } catch (DB_HolidayExceptionHandler e) {
            assertEquals( "ID must be 0 or greater.", e.getMessage());
        }
    }

    /** TEST 007
     *  Testing for lower valid id and constructor and some getters
     *  ==========
     *  Inputs:    DB_Holiday test = new DB_Holiday( 0, Date.valueOf("2000-01-01"), Date.valueOf("2000-01-10"), new DB_Customer());
     *  ==========
     *  Expected Outputs:   test.getHoliday_id() = 0
     *                      test.getStart_date() = "2000-01-01"
     *                      test.getEnd_date() = "2000-01-10"
     */
    public void testDB_Holiday007() {
        try {
            DB_Holiday test = new DB_Holiday( 0, Date.valueOf("2000-01-01"), Date.valueOf("2000-01-10"), 1);
            assertEquals( 0, test.getHoliday_id());
            assertEquals( Date.valueOf("2000-01-01"), test.getStart_date());
            assertEquals( Date.valueOf("2000-01-10"), test.getEnd_date());
        } catch (DB_HolidayExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    /** TEST 008
     *  Testing for upper valid id
     *  ==========
     *  Inputs:    holiday.validateID(Long.MAX_VALUE)
     *  ==========
     *  Expected Outputs:   Long.MAX_VALUE
     */
    public void testDB_Holiday008() {
        try {
            assertEquals( Long.MAX_VALUE, holiday.validateID(Long.MAX_VALUE));
        } catch (DB_HolidayExceptionHandler e) {
            fail("Exception not expected");
        }
    }

}