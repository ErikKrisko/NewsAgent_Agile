import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class DB_Order
{
    private int order_id;
    private Date order_date;
    private boolean order_status;

    private DB_Customer customer;


    public DB_Order()
    {

    }

    public void getbyID(JDBC con, int id) throws DB_OrderExceptionHandler
    {
        try
        {
            ResultSet rs = con.getSet("Select * from order where order_id = " + order_id);
            if (rs.next())
            {
                order_id = rs.getInt(1);
                order_date = rs.getDate(2);
                order_status = rs.getBoolean(3);
                customer = new DB_Customer();
                customer.getByID(con, rs.getInt(4));
                //Would this go with publication?
            }
        }
        catch (JDBCExceptionHandler | SQLException | DB_CustomerExceptionHandler e)
        {
            throw new DB_OrderExceptionHandler(e.getMessage());
        }
    }

    @Override
    public String toString()
    {
        return "DB_Order" +
                "{" +
                "order_id = " + order_id +
                "order_date = " + order_date +
                "order_status = " + order_status +
                "customer = " +customer +
                "}";
    }

    public int getOrder_id()
    {
        return order_id;
    }

    public Date getOrder_date()
    {
        return order_date;
    }

    public void setOrder_date()
    {
        this.order_date = order_date;
    }

    public boolean isOrder_status()
    {
        return order_status;
    }

    public void setOrder_date(boolean order_status)
    {
        this.order_status = order_status;
    }

    public DB_Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(DB_Customer customer)
    {
        this.customer = customer;
    }




    class DB_OrderExceptionHandler extends Exception
    {
        String message;

        public DB_OrderExceptionHandler(String errormessage)
        {
            message = errormessage;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    }
