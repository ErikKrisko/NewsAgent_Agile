import junit.framework.TestCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DB_HolidayTest extends TestCase {
    DB_Holiday holiday = new DB_Holiday();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /** TEST 001
     *  Testing for invalid holiday start date
     */
    public void testDB_Holiday001() {
        try {
            holiday.validateStartDate( sdf.parse("1999-12-31"));
            fail("Exception expected.");
        } catch (DB_HolidayExceptionHandler | ParseException e) {
            assertEquals("Date cannot be before the year 2000.", e.getMessage());
        }
    }

    /** TEST 002
     *  Testing for valid holiday start date
     */
    public void testDB_Holiday002() {
        try {
            assertEquals( sdf.parse("2000-01-01"), holiday.validateStartDate( sdf.parse("2000-01-01")));
        } catch (DB_HolidayExceptionHandler | ParseException e) {
            fail("Exception not expected.");
        }
    }

    /** TEST 003
     *  Testing for invalid end date
     */
    public void testDB_Holiday003() {
        try {
            holiday.setStart_date( sdf.parse("2000-01-10"));
            holiday.validateEndDate( sdf.parse("2000-01-01"));
            fail("Exception expected");
        } catch (DB_HolidayExceptionHandler | ParseException e) {
            assertEquals("End date has to come after Start date.", e.getMessage());
        }
    }

    /** TEST 004
     *  Testing for valid end date
     */
    public void testDB_Holiday004() {
        try {
            holiday.setStart_date( sdf.parse("2000-01-01"));
            assertEquals( sdf.parse("2000-01-10"), holiday.validateEndDate( sdf.parse("2000-01-10")));
        } catch (DB_HolidayExceptionHandler | ParseException e) {
            fail("Exception not expected");
        }
    }

    /** TEST 005
     *  Testing for lower invalid id
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
     */
    public void testDB_Holiday006() {
        try {
            new DB_Holiday( -1, sdf.parse("2000-01-01"), sdf.parse("2000-01-10"), new DB_Customer());
            fail("Exception expected");
        } catch (DB_HolidayExceptionHandler | ParseException e) {
            assertEquals( "ID must be 0 or greater.", e.getMessage());
        }
    }

    /** TEST 007
     *  Testing for lower valid id and constructor and some getters
     */
    public void testDB_Holiday007() {
        try {
            DB_Holiday test = new DB_Holiday( 0, sdf.parse("2000-01-01"), sdf.parse("2000-01-10"), new DB_Customer());
            assertEquals( 0, test.getHoliday_id());
            assertEquals( sdf.parse("2000-01-01"), test.getStart_date());
            assertEquals( sdf.parse("2000-01-10"), test.getEnd_date());
        } catch (DB_HolidayExceptionHandler | ParseException e) {
            fail("Exception not expected");
        }
    }

    /** TEST 008
     *  Testing for upper valid id
     */
    public void testDB_Holiday008() {
        try {
            assertEquals( 0, holiday.validateID(Long.MAX_VALUE));
        } catch (DB_HolidayExceptionHandler e) {
            fail("Exception not expected");
        }
    }

}