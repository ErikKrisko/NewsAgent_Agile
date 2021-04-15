import java.sql.Date;
import java.util.ArrayList;

public class Docket {
    private ArrayList<DB_Subscription> subscriptions;
    private ArrayList<DB_Delivery> deliveries;
    private Date date;
    private ArrayList<DeliveryDocket> deliveryDockets;

    //  Blank constructor
    public Docket() {}

    //  Full constructor
    public Docket(ArrayList<DB_Subscription> subscriptions, ArrayList<DB_Delivery> deliveries, Date date) throws DocketExceptionHandler {
        this.subscriptions = subscriptions;
        this.date = date;
        this.deliveries = validateDeliveries(deliveries);
    }

    public DB_Subscription getMatch(DB_Delivery del) {
        for (DB_Subscription sub : subscriptions) {
            if (sub.getCustomer_id() == del.getCustomer_id() && sub.getPublication_id() == del.getProd_id())
                return sub;
        }
        return null;
    }

    public DB_Delivery getMatch(DB_Subscription sub) {
        for (DB_Delivery del : deliveries) {
            if (sub.getCustomer_id() == del.getCustomer_id() && sub.getPublication_id() == del.getProd_id())
                return del;
        }
        return null;
    }

    //  Validate date comparing to existing delivery list
    private Date validateDate(Date date) throws DocketExceptionHandler {
        if (deliveries != null && deliveries.size() > 0) {
            for (DB_Delivery del : deliveries)
                if (del.getDelivery_date().compareTo(date) != 0)
                    throw new DocketExceptionHandler("Invalid Date change. Docket contains existing deliveries of different date.");
        }
        return date;
    }

    //  Validate deliveries comparing to date and area_code (dont think area_code is possible ?)
    private ArrayList<DB_Delivery> validateDeliveries(ArrayList<DB_Delivery> deliveries) throws DocketExceptionHandler {
        if (date == null) {
            //  If docket has no date throw an error
            throw new DocketExceptionHandler("Docket does not have a date assigned.");
        } else {
            for (DB_Delivery del : deliveries)
                //  If a date mismatches throw an error
                validateDelivery(del);
            //  else return the list
            return deliveries;
        }
    }

    //  validate single delivery entry
    private DB_Delivery validateDelivery(DB_Delivery delivery) throws DocketExceptionHandler {
        if (delivery.getDelivery_date().compareTo(date) != 0) {
            throw new DocketExceptionHandler("A delivery does not match docket date.");
        } else {
            return delivery;
        }
    }

    //  Add delivery to the list and return a boolean depending if delivery exist already or not
    public boolean addDelivery(DB_Delivery delivery) throws DocketExceptionHandler {
        if (date == null)
            //  If docket has no date throw an error
            throw new DocketExceptionHandler("Docket does not have a date assigned.");
        if (deliveries == null) {
            deliveries = new ArrayList<>();
        }
        if (deliveries.contains(delivery))
            return false;
        else
            return deliveries.add(validateDelivery(delivery));
    }

    //  AUTO GENERATED getters and setters
    public ArrayList<DB_Delivery> getDeliveries() { return deliveries; }
    public void setDeliveries(ArrayList<DB_Delivery> deliveries) throws DocketExceptionHandler { this.deliveries = validateDeliveries(deliveries); }
    public Date getDate() { return date; }
    public void setDate(Date date) throws DocketExceptionHandler { this.date = validateDate(date); }
    public ArrayList<DB_Subscription> getSubscriptions() { return subscriptions; }
    public void setSubscriptions(ArrayList<DB_Subscription> subscriptions) { this.subscriptions = subscriptions; }
    public ArrayList<DeliveryDocket> getDeliveryDockets() { return deliveryDockets; }
    public void setDeliveryDockets(ArrayList<DeliveryDocket> deliveryDockets) { this.deliveryDockets = deliveryDockets; }
}

class DocketExceptionHandler extends Exception {
    String message;
    public DocketExceptionHandler(String errMessage) { message = errMessage; }
    public String getMessage() { return message; }
}
