import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;

public class DAOTestPublication extends TestCase {
    //  Global dao object reference
    private DAO dao;

    /** Initialize test environment.
     *  ==========
     *  Inputs: JDBC initialization and script execution
     *          dao initialization
     *  ==========
     *  Expected Outputs:   None
     */
    public void initializeDatabase() {
        try {
            //  Reset Database
            JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
            connection.executeScript("NewsAgent_Database.sql");
            connection.setDbName("newsagent");
            connection.executeScript("NewsAgent_Data_Extended.sql");
            connection.close();
            //  Initialize DAO
            dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");
        } catch (DAOExceptionHandler | JDBCExceptionHandler e) {
            e.printStackTrace();
            fail("Test initialization failed.");
        }
    }

    /** TEST 001
     *  Testing for to get Daily, Weekly 3 and Monthly 14 publications.
     *  ==========
     *  Inputs:    ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-14"));
     *  ==========
     *  Expected Outputs:   list.size() = 3
     *                      list.get(0).getProd_id() = 2
     *                      list.get(1).getProd_id() = 3
     *                      list.get(2).getProd_id() = 4
     */
    public void testGetPublicationsByDate001() {
        try {
            initializeDatabase();
            ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-14"));
            assertEquals(4, list.size());
            assertEquals(2, list.get(0).getProd_id());
            assertEquals(3, list.get(1).getProd_id());
            assertEquals(4, list.get(2).getProd_id());
            assertEquals(8, list.get(3).getProd_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /** TEST 002
     *  Testing for to get Daily, Weekly 4
     *  ==========
     *  Inputs:    ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-15"));
     *  ==========
     *  Expected Outputs:   list.size() = 2
     *                      list.get(0).getProd_id() = 1
     *                      list.get(1).getProd_id() = 4
     */
    public void testGetPublicationsByDate002() {
        try {
            initializeDatabase();
            ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-15"));
            assertEquals(3, list.size());
            assertEquals(1, list.get(0).getProd_id());
            assertEquals(4, list.get(1).getProd_id());
            assertEquals(8, list.get(2).getProd_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /** TEST 003
     *  Testing for to get Daily, Monthly 5
     *  ==========
     *  Inputs:    ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-05"));
     *  ==========
     *  Expected Outputs:   list.size() = 2
     *                      list.get(0).getProd_id() = 4
     *                      list.get(1).getProd_id() = 5
     */
    public void testGetPublicationsByDate003() {
        try {
            initializeDatabase();
            ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-05"));
            assertEquals(3, list.size());
            assertEquals(4, list.get(0).getProd_id());
            assertEquals(5, list.get(1).getProd_id());
            assertEquals(8, list.get(2).getProd_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /** TEST 004
     *  Testing for to get Daily
     *  ==========
     *  Inputs:    ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-06"));
     *  ==========
     *  Expected Outputs:   list.size() = 1
     *                      list.get(0).getProd_id() = 4
     */
    public void testGetPublicationsByDate004() {
        try {
            initializeDatabase();
            ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-06"));
            assertEquals(2, list.size());
            assertEquals(4, list.get(0).getProd_id());
            assertEquals(8, list.get(1).getProd_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /** TEST 005
     *  Testing for to get no publications (Sunday)
     *  ==========
     *  Inputs:    ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-03"));
     *  ==========
     *  Expected Outputs:   list == null;
     */
    public void testGetPublicationsByDate005() {
        try {
            initializeDatabase();
            ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-04"));
            assertNull( list);
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }
}