import java.sql.SQLOutput;
import java.util.LinkedList;

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

            DB_Customer customer1 = new DB_Customer("Buz","Smit","1234567890", handler.getAddress(2));
            DB_Customer customer2 = new DB_Customer("Bob", "Lid", "0987654321", handler.getAddress(2));
            handler.updateCustomer(customer1);
            handler.updateCustomer(customer2);





            LinkedList<DB_Customer> cusList = handler.getCustomers(new Search_Customer[] {new Search_Customer(Att_Customer.first_name, "%i%", false)});
            for (DB_Customer cust : cusList) {
                System.out.println(cust.toString());
            }


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
