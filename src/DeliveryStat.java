import java.util.ArrayList;

//  For summarised viewing information eg. printing.
public class DeliveryStat {
    private String address;
    private ArrayList<String> publication;
    private ArrayList<Integer> publication_count;

    public DeliveryStat(String address, ArrayList<String> publication, ArrayList<Integer> publication_count) {
        this.address = address;
        this.publication = publication;
        this.publication_count = publication_count;
    }


}
