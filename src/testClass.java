import java.sql.SQLException;

public class testClass {
    public static void main(String[] args) throws SQLException {
        JDBC connection = new JDBC();
        System.out.println(connection.getConnection().getSchema());
//        connection.test();
        connection.ReadScript("NewsAgent_Database.sql");
        connection.close();
    }
}
