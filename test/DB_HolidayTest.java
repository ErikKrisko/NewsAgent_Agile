import junit.framework.TestCase;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DB_HolidayTest extends TestCase {
    DB_Holiday holiday = new DB_Holiday();


    public void testDB_Holiday001() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            holiday.validateStartDate( (Date) sdf.parse("1999-12-31"));
            fail("Exception expected.");
        } catch (DB_HolidayExceptionHandler | ParseException e) {
            assertEquals("Date cannot be before the year 2000.", e.getMessage());
        }
    }

    public void testDB_Holiday002() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            assertEquals( sdf.parse("2000-01-01"), holiday.validateStartDate( (Date) sdf.parse("2000-01-01")));
        } catch (DB_HolidayExceptionHandler | ParseException e) {
            fail("Exception expected.");
        }
    }

}