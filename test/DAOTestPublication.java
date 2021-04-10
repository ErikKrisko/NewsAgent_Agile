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
    public DAOTestPublication() {
        try {
            //  Reset Database
            JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
            connection.executeScript("NewsAgent_Database.sql");
            connection.setDbName("newsagent");
            connection.executeScript("NewsAgent_Data.sql");
            connection.close();
            //  Initialize DAO
            dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");
        } catch (DAOExceptionHandler | JDBCExceptionHandler e) {
            e.printStackTrace();
            fail("Test initialization failed.");
        }
    }

    //  GET DAILY, Weekly 3 and Monthly 14 publications
    public void testGetPublications001() {
        try {
            ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-14"));
            assertEquals(3, list.size());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    //  GET DAILY and Weekly 4 publications
    public void testGetPublications002() {
        try {
            ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-15"));
            assertEquals(2, list.size());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    //  GET DAILY and Monthly 5 publications
    public void testGetPublications003() {
        try {
            ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-05"));
            assertEquals(2, list.size());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    //  Get daily only publication
    public void testGetPublications004() {
        try {
            ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-06"));
            assertEquals(1, list.size());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    //  Get no publications (Saturday / Sunday)
    public void testGetPublications005() {
        try {
            ArrayList<DB_Publication> list = dao.getPublicationsByDate(Date.valueOf("2021-04-03"));
            assertNull( list);
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }
}