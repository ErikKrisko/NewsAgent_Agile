public class testClass {
    public static void main(String[] args) throws JDBCExceptionHandler, DB_CustomerExceptionHandler {
        JDBC con = new JDBC();
        con.Connect("jdbc:mysql://localhost:3306/", "root", "admin");
//        con.ExecuteScript("NewsAgent_Database.sql");
//        connection.test();
//        connection.ReadScript("NewsAgent_Database.sql");
//        connection.close();
        con.selectDB("newsagent");
        DB_Customer cus = new DB_Customer();
        cus.getByID(con, 1);
        System.out.println(cus.toString());
    }
}
