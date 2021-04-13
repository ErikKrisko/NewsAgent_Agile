import java.sql.Date;
import java.util.ArrayList;

public class DeliveryDocket {
    private ArrayList<DB_Subscription> subscriptions;
    private ArrayList<DB_Delivery> deliveries;
    private ArrayList<DeliveryStat> deliveryStats;
    private Date date;
    private int area_code;

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

    //  AUTO GENERATED getters and setters
    public ArrayList<DB_Subscription> getSubscriptions() { return subscriptions; }
    public void setSubscriptions(ArrayList<DB_Subscription> subscriptions) { this.subscriptions = subscriptions; }
    public ArrayList<DB_Delivery> getDeliveries() { return deliveries; }
    public void setDeliveries(ArrayList<DB_Delivery> deliveries) { this.deliveries = deliveries; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public int getArea_code() { return area_code; }
    public void setArea_code(int area_code) { this.area_code = area_code; }
    public ArrayList<DeliveryStat> getDeliveryStats() { return deliveryStats; }
    public void setDeliveryStats(ArrayList<DeliveryStat> deliveryStats) { this.deliveryStats = deliveryStats; }
}
