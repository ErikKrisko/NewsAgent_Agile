import java.sql.Date;

public class DB_Holiday {
    //  Base holiday attributes
    private long holiday_id;
    private Date start_date, end_date;
    private DB_Customer customer;

    /** Blank Constructor for DB_Holiday.
     */
    public DB_Holiday() { }

    public DB_Holiday (long holiday_id, Date start_date, Date end_date, DB_Customer customer) throws DB_HolidayExceptionHandler {
        this.holiday_id = validateID( holiday_id);
        this.start_date = validateStartDate( start_date);
        this.end_date = validateEndDate( end_date);
        this.customer = customer;
    }

    /** Validates the starting holiday date to be after the year 2000
     * @param start_date to be verified
     * @return start_date if its valid
     * @throws DB_HolidayExceptionHandler if start_date is invalid
     */
    public Date validateStartDate (Date start_date) throws DB_HolidayExceptionHandler {
        if (start_date.after( Date.valueOf("2000-01-01")) || start_date.equals( Date.valueOf("2000-01-01")))
            return start_date;
        else
            throw new DB_HolidayExceptionHandler("Date cannot be before the year 2000.");
    }

    /** Validates the ending holiday date to be after the starting date.
     * @param end_date to be verified
     * @return end_date if its valid
     * @throws DB_HolidayExceptionHandler if end_date is invalid
     */
    public Date validateEndDate (Date end_date) throws DB_HolidayExceptionHandler {
        if (end_date.after( start_date))
            return end_date;
        else
            throw new DB_HolidayExceptionHandler("End date has to come after Start date.");
    }

    /** Validates ID to ensure no negative number is used.
     * @param id of the object, should be 0 if created internally.
     * @return id of the object if it is valid.
     * @throws DB_HolidayExceptionHandler if entry is invalid.
     */
    public long validateID (long id) throws DB_HolidayExceptionHandler {
        if (id >=0)
            return id;
        else
            throw new DB_HolidayExceptionHandler("ID must be 0 or greater.");
    }

    //  AUTO GENERATED toString
    @Override
    public String toString() {
        return "DB_Holiday{" +
                "holiday_id=" + holiday_id +
                ", start_date=" + start_date.toString() +
                ", end_date=" + end_date.toString() +
                '}';
    }

    //  AUTO GENERATED getters and setters
    public long getHoliday_id() { return holiday_id; }
    public void setHoliday_id(long holiday_id) throws DB_HolidayExceptionHandler { this.holiday_id = validateID( holiday_id); }
    public Date getStart_date() { return start_date; }
    public void setStart_date(Date start_date) throws DB_HolidayExceptionHandler { this.start_date = validateStartDate( start_date); }
    public Date getEnd_date() { return end_date; }
    public void setEnd_date(Date end_date) throws DB_HolidayExceptionHandler { this.end_date = validateEndDate( end_date); }
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