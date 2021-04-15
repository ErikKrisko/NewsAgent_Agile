import java.util.ArrayList;

//  For summarised viewing information eg. printing.
public class DeliveryStat {
    private DB_Address address;
    private ArrayList<DB_Subscription> subscriptions;
    private ArrayList<DB_Publication> publications;

    public DeliveryStat(DB_Address address) {
        this.address = address;
        subscriptions = new ArrayList<>();
        publications = new ArrayList<>();
    }

    public void add(DB_Subscription subscription){
        subscriptions.add(subscription);
    }
    public void add(DB_Publication publication) {
        publications.add(publication);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(address.getFull_address() + " " + address.getEir_code() + "\n");
        for (int i = 0; i < subscriptions.size(); i++) {
            result.append("     ").append(subscriptions.get(i).getCount()).append(" * ").append(publications.get(i).getProd_name()).append(" (").append(publications.get(i).getProd_type()).append(")\n");
        }
        return result.toString();
    }

    public DB_Address getAddress() { return address; }
    public void setAddress(DB_Address address) { this.address = address; }
}
