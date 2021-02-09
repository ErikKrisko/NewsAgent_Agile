import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class JDBC {
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public JDBC(String user, String pass) {
        establishConnection(user, pass);
    }
    public JDBC() {
        establishConnection("root", "user");
    }

    private void establishConnection(String user, String pass) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/";
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
        }
        catch(Exception e){
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }

    public Connection getConnection() {
        return con;
    }

    public void test() {
        try{
            stmt.execute("CREATE SCHEMA newsagent");
            System.out.println(stmt.getWarnings());
//            stmt.executeQuery("SELECT count(*) as total FROM employees");
//            rs.next();//move to first, the query above only produces 1 tuple
//            int myTotal = rs.getInt("total");
//            System.out.println("Total employees: "+myTotal);
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public void ReadScript(String file_name) throws SQLException {
        File file = new File(".\\resources\\" + file_name);
        Scanner sc = ScanFile(file);
        sc.useDelimiter("(;(\r)?\n)|(--\n)");
        try {
            while (sc.hasNext()) {
                String line = sc.next();
                if (line.startsWith("/*!") && line.endsWith("*/")) {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }

                if (line.trim().length() > 0) {
                    System.out.println("Executing : '" + line + "'");
                    stmt.execute(line);
                    System.out.println(stmt.getWarnings());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally
        {
            System.out.println("Closing connection");
            if (stmt != null) stmt.close();
        }
    }

    public static Scanner ScanFile(File f) {
        try {
            return new Scanner(f);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
