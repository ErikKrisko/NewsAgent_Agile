public class testClass {
    public static void main(String[] args) throws JDBCExceptionHandler, DB_CustomerExceptionHandler, DB_DeliveryExceptionHandler {
        JDBC con = new JDBC();
        con.Connect("jdbc:mysql://localhost:3306/", "root", "admin");
       con.ExecuteScript("NewsAgent_Database.sql");
       con.ExecuteScript("NewsAgent_Data.sql");
//        connection.test();
//        connection.ReadScript("NewsAgent_Database.sql");
//        connection.close();
        con.selectDB("newsagent");
        DB_Customer cus = new DB_Customer();
        cus.getByID(con, 1);
        System.out.println(cus.toString());

        DB_Delivery dev = new DB_Delivery();
        dev.getByID(con, 1);
        System.out.println(dev.toString());

//        DB_Order order = new DB_Order();
//        order.getByID(con, 1);
//        System.out.println(order.toString());

//        DB_Invoice inv = new DB_Invoice();
//        inv.getByID(con, 1);
//        System.out.println(inv.toString());
    }
}
