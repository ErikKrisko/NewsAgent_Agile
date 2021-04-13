import java.sql.Date;
import java.util.ArrayList;

public class testClass {
    public static void main(String[] args){
        try {
            //  Section for creating new database

            JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
            connection.executeScript("NewsAgent_Database.sql");
            connection.setDbName("newsagent");
            connection.executeScript("NewsAgent_Data_Extended.sql");
            connection.close();

            DAO dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC","root","admin");

            ArrayList<DB_Publication> prod_list = dao.getPublicationsByDate(Date.valueOf("2021-04-14"));
            ArrayList<DB_Subscription> sub_list = dao.getSubscriptionsByPublications(prod_list);
            System.out.println();
            for (DB_Subscription s : sub_list) {
                System.out.print(s.getCount()+",");
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
