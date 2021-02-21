import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Subscription {
    //Private count thats an integer
    private int count = 0;
    private int size;
    private DB_Customer customer;
    //private DB_Publication  prod;
    //private DB_Product product[];


    public DB_Subscription() {

    }


    public DB_Subscription(int count, DB_Customer customer /*,DB_Publication prod*/) throws DB_SubscriptionExceptionHandler {
        this.customer = customer;
        this.count = count;
        //this.publication = prod;
    }

    public DB_Subscription(ResultSet rs) throws DB_SubscriptionExceptionHandler {
        try {
            count = rs.getInt(2);
        } catch (SQLException e) {
            throw new DB_SubscriptionExceptionHandler(e.getMessage());
        }
    }
    //Validate attributes will do after
//    public String vEntry(Att_Subscription type, String entry) throws DB_SubscriptionExceptionHandler
//    {
//        switch(type)
//        {
//            case customer:
//        }
//    }


    @Override
    public String toString() {
        return "DB_Subscription{" +
                "customer=" + customer +
                "count=" + count;
        //"publication=" + prod +
    }


    /**
     * A list of columnIndexes for resultSet
     */
//    public enum attributes {
//        customer(1),
//        product(2),
//        count(3);
//
//        public final int index;
//
//        attributes(int i) {
//            this.index = i;
//        }
   // }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public DB_Customer getCustomer() {
        return customer;
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
