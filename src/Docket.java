import java.sql.Date;
import java.util.ArrayList;

public class Docket {
    private ArrayList<DB_Delivery> deliveries;
    private Date date;

    //  Blank constructor
    public Docket() {}

    //  Full constructor
    public Docket(ArrayList<DB_Delivery> deliveries, Date date) throws DocketExceptionHandler {
        this.date = date;
        this.deliveries = validateDeliveries(deliveries);
    }

    //  Validate date comparing to existing delivery list
    private Date validateDate(Date date) throws DocketExceptionHandler {
        throw new DocketExceptionHandler("NO CODE");
    }

    //  Validate deliveries comparing to date and area_code (dont think area_code is possible ?)
    private ArrayList<DB_Delivery> validateDeliveries(ArrayList<DB_Delivery> deliveries) throws DocketExceptionHandler {
        throw new DocketExceptionHandler("NO CODE");
    }

    //  validate single delivery entry
    private DB_Delivery validateDelivery(DB_Delivery delivery) throws DocketExceptionHandler {
        throw new DocketExceptionHandler("NO CODE");
    }

    //  Add delivery to the list and return a boolean depending if delivery exist already or not
    public boolean addDelivery(DB_Delivery delivery) throws DocketExceptionHandler {
        throw new DocketExceptionHandler("NO CODE");
    }

    //  AUTO GENERATED getters and setters
    public ArrayList<DB_Delivery> getDeliveries() { return deliveries; }
    public void setDeliveries(ArrayList<DB_Delivery> deliveries) throws DocketExceptionHandler { this.deliveries = validateDeliveries(deliveries); }
    public Date getDate() { return date; }
    public void setDate(Date date) throws DocketExceptionHandler { this.date = validateDate(date); }
}

class DocketExceptionHandler extends Exception {
    String message;

    public DocketExceptionHandler(String errMessage) { message = errMessage; }

    public String getMessage() { return message; }
}
