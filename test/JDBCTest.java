import junit.framework.TestCase;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class JDBCTest extends TestCase {
    JDBC jdbc = new JDBC();

    /**TEST 001
     * Testing for failed driver connection for JDBC connector.
     * Inputs:
     *  url: "invalid"  //  Can be any kind of invalid connection source but will reflect in the expected output
     *  user: ""        //  Irrelevant for the test
     *  pass: ""        //  Irrelevant for the test
     * Expected Output:
     *  "No suitable driver found for invalid"  //  the world invalid reflects the url input
     */
    public void testJDBC001() {
        try {
            jdbc.Connect("invalid","","");
            fail("Exception expected");
        }
        catch (JDBCExceptionHandler e) {
            assertEquals("No suitable driver found for invalid",e.getMessage());
        }
    }

    /**TEST 002
     * Testing for invalid username and password combination.
     * Inputs:
     *  url: "jdbc:mysql://localhost:3306/" //  Set valid connection url
     *  user: "username"                    //  Any username will do but it will reflect in the expected output
     *  pass: "password"                    //  Has to contain a some String otherwise it will reflect in the expected output
     * Expected Output:
     *  "Access denied for user 'username'@'localhost' (using password: YES)"
     * 'username' reflects the user entry used in the input
     * (using password: YES) reflects if the password string was empty or not
     */
    public void testJDBC002() {
        try {
            jdbc.Connect("jdbc:mysql://localhost:3306/", "username", "password");
            fail("Exception expected");
        }
        catch (JDBCExceptionHandler e) {
            assertEquals("Access denied for user 'username'@'localhost' (using password: YES)", e.getMessage());
        }
    }

    /**TEST 003
     * Testing for correct database connection
     * Inputs:
     *  ulr: "jdbc:mysql://localhost:3306/" //  Set valid connection url
     *  user: "root"                        //  Correct username for database
     *  pass: "admin"                       //  Correct password associated with above username
     * Expected Output:
     *  ???
     */
    public void testJDBC003() {
        try {
            assertTrue(jdbc.Connect("jdbc:mysql://localhost:3306/", "root", "admin"));
        }
        catch (JDBCExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    /**TEST 004
     * Testing for connection closing
     * Inputs:
     *  Connect("jdbc:mysql://localhost:3306/", "root", "admin")    //  Establish successful connection as in TEST 003
     * Expected Output:
     *  ???
     *
     */
    public void testJDBC004() {
        try {
            jdbc.Connect("jdbc:mysql://localhost:3306/", "root", "admin");
            jdbc.close();
        }
        catch (JDBCExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    /**TEST 005
     * Testing for invalid script file
     * Inputs:
     *  Connect("jdbc:mysql://localhost:3306/", "root", "admin")    //  Establish successful connection as in TEST 003
     *  file_name: "NotHere.sql"    //  Any file_name can be used but it will reflect on the expected output
     * Expected Output:
     *  ".\\resources\\NotHere.sql (The system cannot find the file specified)" //  'NotHere.sql' part reflects file_name input
     *
     */
    public void testJDBC005() {
        try {
            jdbc.Connect("jdbc:mysql://localhost:3306/", "root", "admin");
            jdbc.ExecuteScript("NotHere.sql");
            fail("Exception expected");
        }
        catch (JDBCExceptionHandler e) {
            assertEquals(".\\resources\\NotHere.sql (The system cannot find the file specified)", e.getMessage());
        }
    }

    /**TEST 006
     * Inputs:
     *  Connect("jdbc:mysql://localhost:3306/", "root", "admin")    //  Establish successful connection as in TEST 003
     *  file_name: "Test_Script_Invalid.sql"    //  Name of sql script that has an SQL error
     * Expected Output:
     *  ???
     */
    public void testJDBC006() {
        try {
            jdbc.Connect("jdbc:mysql://localhost:3306/", "root", "admin");
            jdbc.ExecuteScript("Test_Script_Invalid.sql");
            fail("Exception expected");
        }
        catch (JDBCExceptionHandler e) {
            assertEquals("SQL error.", e.getMessage());
        }
    }

    /**TEST 007
     * Inputs:
     *  Connect("jdbc:mysql://localhost:3306/", "root", "admin")    //  Establish successful connection as in TEST 003
     *  file_name: "Test_Script_Valid.sql"  //  Name of sql script that has no errors
     * Expected Output:
     *  ???
     */
    public void testJDBC007(){
        try {
            jdbc.Connect("jdbc:mysql://localhost:3306/", "root", "admin");
            jdbc.ExecuteScript("Test_Script_Valid.sql");
        }
        catch (JDBCExceptionHandler e) {
            fail("Exception not expected");
        }
    }
}