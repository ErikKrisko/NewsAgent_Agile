import java.sql.Date;

public class DB_Holiday {
    private long holiday_id;
    private Date start_date, end_date;
    private DB_Customer customer;

    /** Blank Constructor for DB_Holiday.
     */
    public DB_Holiday() { }

    public DB_Holiday (long holiday_id, Date start_date, Date end_date, DB_Customer customer) {

    }

    public Date validateStartDate (Date start_date) throws DB_HolidayExceptionHandler {
        throw new DB_HolidayExceptionHandler("No product code.");

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

/** A specific exception handler for DB_Holiday.
 */
class DB_HolidayExceptionHandler extends Exception {
    String message;
    public DB_HolidayExceptionHandler(String errMessage){ message = errMessage; }
    public String getMessage() { return message; }
}