import java.sql.ResultSet;
import java.sql.SQLException;


public class DB_Subscription {
    private static JDBC connection;
    //  Handler for storing initialized elements
    private static DB_Handler handler;
    //Private count thats an integer
    private int[] count = new int[0];
    private int size;
    private DB_Customer customer = null;

    //private DB_Product product[];


    /** A list of columnIndexes for resultSet */
    public enum attributes {
        customer(1),
        product(2),
        count(3);

        public final int index;

        attributes(int i) { this.index = i; }
    }

    public DB_Subscription() {

    }

    public void getByID(int cus_id) throws DB_SubscriptionExceptionHandler {
        try {
            ResultSet rs = connection.getSet(" SELECT * FROM subscription WHERE customer_id = " + cus_id);

            while (rs.next()) {
                int[] temp = new int[count.length + 1];
                System.arraycopy(count, 0, temp, 0, count.length);
                temp[rs.getRow() - 1] = rs.getInt(attributes.count.index);
                count = temp.clone();

//                System.arraycopy(count, 0, temp, 0, count.length);
//                temp[rs.getRow()-1] = rs.getInt(3);
//                count = temp.clone();
                //Product needs to go in here
                if (customer == null) {
                    customer = handler.getCustomer(rs.getInt(attributes.customer.index));
                }

            }
        } catch (JDBCExceptionHandler | SQLException | DB_HandlerExceptionHandler e) {
            throw new DB_SubscriptionExceptionHandler(e.getMessage());
        }
    }

    public static JDBC getConnection()
    {
        return connection;
    }

    public static void setConnection(JDBC connection)
    {
        DB_Subscription.connection = connection;
    }

    public static DB_Handler getHandler() {
        return handler;
    }

    public static void setHandler(DB_Handler handler) {
        DB_Subscription.handler = handler;
    }

    public DB_Customer getCustomer() {
        return customer;
    }

    public void setCustomer(DB_Customer customer) {
        this.customer = customer;
    }
}



class DB_SubscriptionExceptionHandler extends Exception
{
    String message;

    public DB_SubscriptionExceptionHandler(String errormessage)
    {
        message = errormessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
