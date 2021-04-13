import java.sql.Date;
import java.util.ArrayList;

public class DAOGenerator {
    private DAO dao;
    public DAOGenerator(DAO dao) {
        this.dao = dao;
    }
    public Docket GenDocket(Date date) throws DAOGeneratorExceptionHandler {
        try {
            ArrayList<DB_Delivery> deliveries = dao.getDeliveries();
            if (deliveries != null && deliveries.size() > 0) {
                System.out.println("Delivery already exists");
            } else {
                //  Else populate create deliveries
                ArrayList<DB_Subscription> subscriptions = dao.getSubscriptionsForDate(date, true, true);
            }
            throw new DAOGeneratorExceptionHandler("WORKING");
        } catch (DAOExceptionHandler e) {
            throw new DAOGeneratorExceptionHandler(e.getMessage());
        }
    }
    public ArrayList<DeliveryDocket> GenDeliveryDocket(Docket docket) throws DAOGeneratorExceptionHandler {
        throw new DAOGeneratorExceptionHandler("No code written");
    }
}

class DAOGeneratorExceptionHandler extends Exception {
    String message;
    public DAOGeneratorExceptionHandler(String errMessage) { message = errMessage; }
    public String getMessage() { return message; }
}