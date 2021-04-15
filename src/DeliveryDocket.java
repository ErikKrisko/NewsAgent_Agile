import java.sql.Date;
import java.util.ArrayList;

public class DeliveryDocket {
    private ArrayList<DB_Subscription> subscriptions;
    private ArrayList<DB_Delivery> deliveries;
    private ArrayList<String> deliveryStats;
    private Date date;
    private int area_code;

    public DeliveryDocket() {
        subscriptions = new ArrayList<>();
        deliveries = new ArrayList<>();
    }

    public DeliveryDocket(ArrayList<DB_Subscription> subscriptions, ArrayList<DB_Delivery> deliveries, Date date, int area_code) {
        this.subscriptions = subscriptions;
        this.deliveries = deliveries;
        this.date = date;
        this.area_code = area_code;
    }

    public DeliveryDocket(ArrayList<DB_Subscription> subscriptions, Date date, int area_code) {
        this.subscriptions = subscriptions;
        this.date = date;
        this.area_code = area_code;
    }

    public void add(DB_Delivery delivery) {
        deliveries.add(delivery);
    }

    public void add(DB_Subscription subscription) {
        subscriptions.add(subscription);
    }

    public void add(String stat) {
        deliveryStats.add(stat);
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

    //  AUTO GENERATED getters and setters
    public ArrayList<DB_Subscription> getSubscriptions() { return subscriptions; }
    public void setSubscriptions(ArrayList<DB_Subscription> subscriptions) { this.subscriptions = subscriptions; }
    public ArrayList<DB_Delivery> getDeliveries() { return deliveries; }
    public void setDeliveries(ArrayList<DB_Delivery> deliveries) { this.deliveries = deliveries; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public int getArea_code() { return area_code; }
    public void setArea_code(int area_code) { this.area_code = area_code; }
    public ArrayList<String> getDeliveryStats() { return deliveryStats; }
    public void setDeliveryStats(ArrayList<String> deliveryStats) { this.deliveryStats = deliveryStats; }
}
