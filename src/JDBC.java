import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class JDBC {
    //  Connection properties
    private String url, user, pass, dbName;
    //  Connection objects
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public JDBC() throws JDBCExceptionHandler {
        url = "jdbc:mysql://localhost:3306/";
        user = "root";
        pass = "admin";
        dbName = "newsagent";
        open();
    }
    public JDBC( String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    /** Executes specified .sql script file located in the resources folder.
     * @param file_name of the file to execute
     */
    public void executeScript(String file_name) throws JDBCExceptionHandler {
        //  Portions of this code were found and used from here : https://stackoverflow.com/questions/1497569/how-to-execute-sql-script-file-using-jdbc
        try {
            open();
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
            close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new JDBCExceptionHandler("SQL error.");
        }
    }

    public void executeQuery(String query) throws DAOExceptionHandler {
        try {
            rs = stmt.executeQuery(query);
        }
        catch (SQLException e) {
            throw new DAOExceptionHandler(e.getMessage());
        }
    }


    //  PRIVATE METHODS

    /** Scans specified file content or throws JDBCExceptionHandler error.
     * @param f file name
     * @return Scanner object
     * @throws JDBCExceptionHandler
     */
    private static Scanner ScanFile(File f) throws JDBCExceptionHandler {
        try {
            return new Scanner(f);
        }
        catch (FileNotFoundException e) {
            throw new JDBCExceptionHandler(e.getMessage());
        }
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

    /** Establishes database connection
     * @throws JDBCExceptionHandler
     */
    public void open() throws JDBCExceptionHandler {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (dbName == null)
                con = DriverManager.getConnection( url, user, pass);
            else
                con = DriverManager.getConnection( url + dbName, user, pass);
            stmt = con.createStatement();
        }
        catch(SQLException | ClassNotFoundException e) {
            throw new JDBCExceptionHandler(e.getMessage());
        }
    }

    //  AUTO GENERATED getters and setters
    public String getUrl() {    return url; }
    public void setUrl(String url) {    this.url = url; }
    public String getUser() {   return user; }
    public void setUser(String user) {  this.user = user; }
    public String getPass() {   return pass; }
    public void setPass(String pass) {  this.pass = pass; }
    public String getDbName() { return dbName; }
    public void setDbName(String dbName) {  this.dbName = dbName; }

    public ResultSet getRs() { return rs; }
}

/**
 * Exception handler for JDBC connector
 */
class JDBCExceptionHandler extends Exception {
    String message;

    public JDBCExceptionHandler(String errMessage){ message = errMessage; }

    public String getMessage() {    return message; }
}