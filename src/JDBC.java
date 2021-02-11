import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class JDBC {
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public JDBC() {}

    /**
     * @param url
     * @param user
     * @param pass
     * @throws JDBCExceptionHandler
     */
    public boolean Connect(String url, String user, String pass) throws JDBCExceptionHandler {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            return true;
        }
        catch(SQLException | ClassNotFoundException e) {
            throw new JDBCExceptionHandler(e.getMessage());
        }
    }

    /** Executes specified .sql script file located in the resources folder.
     * @param file_name of the file to execute
     */
    public void ExecuteScript(String file_name) throws JDBCExceptionHandler {
        //  Portions of this code were found and used from here : https://stackoverflow.com/questions/1497569/how-to-execute-sql-script-file-using-jdbc
        try {
            File file = new File(".\\resources\\" + file_name);
            Scanner sc = ScanFile(file);
            sc.useDelimiter("(;(\r)?\n)|(--\n)");
            while (sc.hasNext()) {
                String line = sc.next();
                if (line.startsWith("/*!") && line.endsWith("*/")) {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }
                if (line.trim().length() > 0) {
                    stmt.execute(line);
                }
            }
        }
        catch (SQLException e) {
            throw new JDBCExceptionHandler("SQL error.");
        }
    }

    public void nothing() {

    }

    /** Closes the current connection.
     * @throws JDBCExceptionHandler
     */
    public void close() throws JDBCExceptionHandler {
        try {
            con.close();
        }
        catch (Exception e) {
            throw new JDBCExceptionHandler(e.getMessage());
        }
    }

    //  PRIVATE METHODS
    //  file scanner
    private static Scanner ScanFile(File f) throws JDBCExceptionHandler {
        try {
            return new Scanner(f);
        }
        catch (FileNotFoundException e) {
            throw new JDBCExceptionHandler(e.getMessage());
        }
    }
}

class JDBCExceptionHandler extends Exception {
    String message;

    public JDBCExceptionHandler(String errMessage){
        message = errMessage;
    }

    public String getMessage() {
        return message;
    }
}