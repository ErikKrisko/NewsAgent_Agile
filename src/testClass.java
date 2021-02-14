public class testClass {
    public static void main(String[] args){
        try {
            //  Section for creating new database
            {
                //  Separate connections needed for each scripts (for some god forsaken reason)
                JDBC con = new JDBC();
                con.connect("jdbc:mysql://localhost:3306/", "root", "admin");
                con.executeScript("NewsAgent_Database.sql");
                con.close();
                con = new JDBC();
                con.connect("jdbc:mysql://localhost:3306/newsagent", "root", "admin");
                con.executeScript("NewsAgent_Data.sql");
                con.close();
            }

            JDBC connection = new JDBC().init();
            DB_Handler handler = new DB_Handler().init();
            DB_Customer cus = new DB_Customer();
            cus.getByID(1);
            System.out.println(cus.toString());

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
//
//        DB_Delivery dev = new DB_Delivery();
//        dev.getByID(con, 1);
//        System.out.println(dev.toString());
//
//        DB_Order order = new DB_Order();
//        order.getByID(con, 1);
//        System.out.println(order.toString());
//
//          DB_Invoice inv = new DB_Invoice();
//          inv.getByID(con, 1);
//          System.out.println(inv.toString());
    }
}
