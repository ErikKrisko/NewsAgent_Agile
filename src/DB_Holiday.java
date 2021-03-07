import java.text.DateFormat;
import java.util.Date;

public class DB_Holiday {
    //  Base holiday attributes
    private long holiday_id;
    private Date start_date, end_date;
    private DB_Customer customer;

    /** Blank Constructor for DB_Holiday.
     */
    public DB_Holiday() { }

    public DB_Holiday (long holiday_id, Date start_date, Date end_date, DB_Customer customer) throws DB_HolidayExceptionHandler {
        throw new DB_HolidayExceptionHandler("No product code.");

    }

    public Date validateStartDate (Date start_date) throws DB_HolidayExceptionHandler {
        if (start_date.after( new Date()))
            return start_date;
        else
            throw new DB_HolidayExceptionHandler("Date cannot be before the year 2000.");
    }

    public Date validateEndDate (Date end_date) throws DB_HolidayExceptionHandler {
        throw new DB_HolidayExceptionHandler("No product code.");

    }

    public long validateID (long id) throws DB_HolidayExceptionHandler {
        throw new DB_HolidayExceptionHandler("No product code.");
    }

    //  AUTO GENERATED toString
    @Override
    public String toString() {
        return "DB_Holiday{" +
                "holiday_id=" + holiday_id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", customer=" + customer +
                '}';
    }

    //  AUTO GENERATED getters and setters
    public long getHoliday_id() { return holiday_id; }
    public void setHoliday_id(long holiday_id) { this.holiday_id = holiday_id; }
    public Date getStart_date() { return start_date; }
    public void setStart_date(Date start_date) { this.start_date = start_date; }
    public Date getEnd_date() { return end_date; }
    public void setEnd_date(Date end_date) { this.end_date = end_date; }
    public DB_Customer getCustomer() { return customer; }
    public void setCustomer(DB_Customer customer) { this.customer = customer; }
}

/** This is the SQL table layout reference in java code.
 *  Allows easier access and guidance of variable locations.
 *      Column 1    Column 2    Column 3     Column 4
 *  +------------+------------+------------+-------------+
 *  | holiday_id | start_date | end_date   | customer_id |
 *  +------------+------------+------------+-------------+
 *  |          1 | 2021-02-10 | 2021-02-15 |           1 |
 *  +------------+------------+------------+-------------+
 */
enum Att_Holiday {
    //  Address table attributes for column #, column Name
    holiday_id(1, "holiday_id"),
    start_date(2, "start_date"),
    end_date(3, "end_date"),
    customer(4, "customer_id");

    //  Column in which the given attribute appears by default
    public final int column;
    //  Name of column in which te attribute appears by default
    public final String name;

    //  Constructor (it just makes it work ? I think)
    Att_Holiday(int column, String name) {
        this.column = column;
        this.name = name;
    }
}

/** A specific exception handler for DB_Holiday.
 */
class DB_HolidayExceptionHandler extends Exception {
    String message;
    public DB_HolidayExceptionHandler(String errMessage){ message = errMessage; }
    public String getMessage() { return message; }
}