public class testClass {
    public static void main(String[] args) throws JDBCExceptionHandler {
        JDBC con = new JDBC();
        con.Connect("jdbc:mysql://localhost:3306/", "root", "admin");
        con.ExecuteScript("NewsAgent_Database.sql");
//        connection.test();
//        connection.ReadScript("NewsAgent_Database.sql");
//        connection.close();
        DB_Customer cus1 = new DB_Customer();
    }
}
