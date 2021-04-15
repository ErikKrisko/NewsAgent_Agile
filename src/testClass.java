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
            connection.executeScript("NewsAgent_Clean.sql");
            connection.close();

            DAO dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC","root","admin");
            DAOGenerator dg = new DAOGenerator(dao);
            Docket doc1 = dg.GenDocket(Date.valueOf("2021-04-01"), true);
            doc1.setDeliveryDockets(dg.splitDocket(doc1));
            for (DeliveryDocket docket : doc1.getDeliveryDockets()) {
                docket.setDeliveryStats(dg.genStats(docket));
            }
//            Docket doc2 = dg.GenDocket(Date.valueOf("2021-04-02"), true);
//            Docket doc3 = dg.GenDocket(Date.valueOf("2021-04-03"), true);

            //  Update Invoice not working, likely will stay that way.
//            dao.updateInvoiceTotalForDate(Date.valueOf("2021-05-01"));
            System.out.println("DONE");

//            System.out.println(dao.updateInvoiceTotalForDate(Date.valueOf("2020-03-05")));


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
