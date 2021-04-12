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
