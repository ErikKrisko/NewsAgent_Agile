import java.sql.Date;
import java.util.ArrayList;

public class Docket {
    private DB_Employee employee;
    private ArrayList<DB_Delivery> deliveries;
    private Date date;
    public Docket() {}

    public Docket(DB_Employee employee, ArrayList<DB_Delivery> deliveries, Date date) throws DocketExceptionHandler {
        this.employee = employee;
        this.date = date;
        this.deliveries = validateDeliveries(deliveries);
    }

    private Date validateDate(Date date) throws DocketExceptionHandler {
        throw new DocketExceptionHandler("NO CODE");
    }

    private ArrayList<DB_Delivery> validateDeliveries(ArrayList<DB_Delivery> deliveries) throws DocketExceptionHandler {
        throw new DocketExceptionHandler("NO CODE");
    }

    private DB_Delivery validateDelivery(DB_Delivery delivery) throws DocketExceptionHandler {
        throw new DocketExceptionHandler("NO CODE");
    }

    public boolean addDelivery(DB_Delivery delivery) throws DocketExceptionHandler {
        throw new DocketExceptionHandler("NO CODE");
    }

    //  AUTO GENERATED getters and setters
    public DB_Employee getEmployee() { return employee; }
    public void setEmployee(DB_Employee employee) { this.employee = employee; }
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
