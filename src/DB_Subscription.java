import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Subscription {
    //Private count that's an integer
    private int count = 0;
    private long customer_id;
    private long publication_id;
    //private DB_Product product[];


    public DB_Subscription() {

    }


    public DB_Subscription(int count, long customer_id, long publication_id) throws DB_SubscriptionExceptionHandler {
        this.customer_id = customer_id;
        this.count = count;
        this.publication_id = publication_id;
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
                "customer=" + customer_id +
                "count=" + count +
        "publication=" + publication_id;
    }
    public String [] getRowData(){
        return new String[]{
                String.valueOf(customer_id),
                String.valueOf(publication_id),
                String.valueOf(count)
        };
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public long getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(long publication_id) {
        this.publication_id = publication_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
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
