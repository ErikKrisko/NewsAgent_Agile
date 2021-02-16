import java.sql.SQLOutput;

public class testClass {
    public static void main(String[] args){
        try {
            //  Section for creating new database

            JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
            connection.executeScript("NewsAgent_Database.sql");
            connection.setDbName("newsagent");
            connection.executeScript("NewsAgent_Data.sql");
            connection.close();

            DB_Handler handler = new DB_Handler("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");

            DB_Customer test = new DB_Customer("jon","Smit","1234567890", handler.getAddress(2));

            DB_Customer cus1 = handler.getCustomer(1);
            cus1.setFirst_name("Bull");
            cus1.setLast_name("Burr");
            cus1.setPhone_no("0123456789");
            handler.updateCustomer(cus1);
            handler.updateCustomer(cus1);
            handler.updateCustomer(test);
            System.out.println(cus1.toString());


        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

//
//          DB_Invoice inv = new DB_Invoice();
//          inv.getByID(con, 1);
//          System.out.println(inv.toString());
    }
}
