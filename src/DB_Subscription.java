public class DB_Subscription {
    private static JDBC connection;
    // Handler for storing initialized elements
    private static DAO handler;
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
/*
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
    }*/


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
