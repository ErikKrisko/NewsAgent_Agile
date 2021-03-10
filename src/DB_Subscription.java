import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Subscription {
    //Private count that's an integer
    private int count = 0;
    private DB_Customer customer;
    private DB_Publication publication;
    //private DB_Product product[];


    public DB_Subscription() {

    }


    public DB_Subscription(int count, DB_Customer customer, DB_Publication prod_id) throws DB_SubscriptionExceptionHandler {
        this.customer = customer;
        this.count = count;
        this.publication = prod_id;
    }

    public DB_Subscription(ResultSet rs) throws DB_SubscriptionExceptionHandler {
        try {
            count = rs.getInt(2);
        } catch (SQLException e) {
            throw new DB_SubscriptionExceptionHandler(e.getMessage());
        }
    }
    //Validate Attribute
    //Count
    public int validateCount(int count) throws DB_SubscriptionExceptionHandler{
        if(count > 0 && count < 99)
        {
           return count;
        }else{
            throw new DB_SubscriptionExceptionHandler("Number must be between 0 and 99");
        }
    }



    @Override
    public String toString() {
        return "DB_Subscription{" +
                "customer=" + customer.toString() +
                "count=" + count +
        "publication=" + publication.toString();
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DB_Customer getCustomer() {
        return customer;
    }

    public DB_Publication getPublication() {
        return publication;
    }

    public void setPublication(DB_Publication publication) {
        this.publication = publication;
    }

    public void setCustomer(DB_Customer customer) {
        this.customer = customer;
    }
}




class DB_SubscriptionExceptionHandler extends Exception {
    String message;

    public DB_SubscriptionExceptionHandler(String errormessage) {
        message = errormessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
