import java.sql.*;
import java.util.Scanner;

public class JDBC_tester {
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void main(String[] args){
        String user, password;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username : ");
        user = sc.next();
        System.out.print("Enter password : ");
        password = sc.next();
        init_db(user, password);  // open the connection to the database
        try{
            rs = stmt.executeQuery("SELECT count(*) as total FROM employees");
            rs.next();//move to first, the query above only produces 1 tuple
            int myTotal = rs.getInt("total");
            System.out.println("Total employees: "+myTotal);
        }
        catch (SQLException sqle){
            System.out.println("Error: failed to get number of records");
        }
        try{
            con.close();
        }
        catch (SQLException sqle){
            System.out.println("Error: failed to close the database");
        }
    }
    public static void init_db(String user, String password){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/lab2sql?useTimezone=true&serverTimezone=UTC";
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
        }
        catch(Exception e){
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }
}
