import junit.framework.TestCase;

public class DAOTest extends TestCase {
    DAO dao = new DAO();

    /** TEST 001
     *  Testing for null URL
     *  ======
     *  Inputs: none
     *  ======
     *  Expected Output: "The url cannot be null"
     */
    public void testDAO001() {
        try {
            dao.open();
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("The url cannot be null", e.getMessage());
        }
    }

    /** TEST 002
     *  Testing for invalid_url
     *  ======
     *  Inputs: Url:    "invalid_url"
     *  ======
     *  Expected Output: "No suitable driver found for invalid_url"
     */
    public void testDAO002() {
        try {
            dao.setUrl("invalid_url");
            dao.open();
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("No suitable driver found for invalid_url", e.getMessage());
        }
    }

    /** TEST 003
     *  Testing for invalid login details
     *  ======
     *  Inputs: Url:    "jdbc:mysql://localhost:3306/"
     *          User:   "test_user"
     *          Pass:   "test_pass"
     *  ======
     *  Expected Output: "Access denied for user 'test_user'@'localhost' (using password: YES)"
     */
    public void testDAO003() {
        try {
            dao.setUrl("jdbc:mysql://localhost:3306/");
            dao.setUser("test_user");
            dao.setPass("test_pass");
            dao.open();
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("Access denied for user 'test_user'@'localhost' (using password: YES)", e.getMessage());
        }
    }

    /** TEST 004
     *  Testing for valid connection opening
     *  ======
     *  Inputs: Url:    "jdbc:mysql://localhost:3306/"
     *          User:   "root"
     *          Pass:   "admin"
     *  ======
     *  Expected Output: No exception
     */
    public void testDAO004() {
        try {
            dao.setUrl("jdbc:mysql://localhost:3306/");
            dao.setUser("root");
            dao.setPass("admin");
            dao.open();
        } catch (DAOExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    /** TEST 005
     *  Testing for connection closing without opening
     *  ======
     *  Inputs: none
     *  ======
     *  Expected Output: "Access denied for user 'test_user'@'localhost' (using password: YES)"
     */
    public void testDAO005() {
        try {
            dao.close();
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("Cannot invoke \"java.sql.Connection.close()\" because \"this.con\" is null", e.getMessage());
        }
    }

    /** TEST 006
     *  Testing for successful connection closing
     *  ======
     *  Inputs: Url:    "jdbc:mysql://localhost:3306/"
     *          User:   "test_user"
     *          Pass:   "test_pass"
     *  ======
     *  Expected Output:
     */
    public void testDAO006() {
        try {
            dao.setUrl("jdbc:mysql://localhost:3306/");
            dao.setUser("root");
            dao.setPass("admin");
            dao.open();
            dao.close();
        } catch (DAOExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    /** TEST 007
     *  Testing for multiple connection closing (Probably unneeded)
     */
//    public void testDAO007() {
//        try {
//            dao.setUrl("jdbc:mysql://localhost:3306/");
//            dao.setUser("root");
//            dao.setPass("admin");
//            dao.open();
//            dao.close();
//            dao.close();
//            fail("Exception expected");
//        } catch (DAOExceptionHandler e) {
//            assertEquals("C", e.getMessage());
//        }
//    }


}