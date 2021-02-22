import java.sql.Date;
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


            DAO handler = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");



            DB_Customer customer1 = new DB_Customer("Buz","Smit","1234567890", handler.getAddress(2));
            DB_Customer customer2 = new DB_Customer("Bob", "Lid", "0987654321", handler.getAddress(2));
            handler.updateCustomer( customer1);
            System.out.println( customer1.toString());
            handler.updateCustomer(customer2);
            System.out.println( customer2.toString());

            DB_Customer test1 = handler.getCustomer(3);





            LinkedList<DB_Customer> cusList = handler.getCustomers(new Search_Customer[] {new Search_Customer(Att_Customer.first_name, "%i%", false)});
            for (DB_Customer cust : cusList) {
                System.out.println(cust.toString());
            }

            DB_Invoice Invoice1 = handler.getInvoice(2);
            System.out.println(Invoice1.toString());
            //Invoice1.setInvoice_total(21.34);
            handler.updateInvoice(Invoice1);


//            DB_Delivery dev1 = handler.getDelivery(5);
//            System.out.println(dev1.toString());
//            dev1.setDelivery_id(0);
//            dev1.setDelivery_date(Date.valueOf("2022-05-05"));
//            dev1.setDelivery_status(true);
//            dev1.setCustomer(customer1);
//            dev1.setInvoice(Invoice1);
//            System.out.println(dev1.toString());
//            handler.updateDelivery(dev1);
//            System.out.println(dev1.toString());
//            handler.deleteDelivery(dev1);

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
