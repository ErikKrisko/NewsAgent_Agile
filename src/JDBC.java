import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class JDBC {
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public JDBC(String user, String pass) { establishConnection(user, pass); }
    public JDBC() { establishConnection("root", "user"); }

    private void establishConnection(String user, String pass) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/";
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
        }
        catch(Exception e) {
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }

    /**
     *
     * @param file_name
     */
    public void ReadScript(String file_name) {
        //  Portions of this code were found and used from here : https://stackoverflow.com/questions/1497569/how-to-execute-sql-script-file-using-jdbc
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
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static Scanner ScanFile(File f) {
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
