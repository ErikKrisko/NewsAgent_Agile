import junit.framework.TestCase;

import java.sql.Date;

public class DAOTestDelivery extends TestCase {
    DB_Delivery delivery = new DB_Delivery();
    private DAO dao;

    public DAOTestDelivery() {
        try {
            //  Initialize DAO
            dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");
            //  Reset Database
            JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
            connection.executeScript("NewsAgent_Database.sql");
            connection.setDbName("newsagent");
            connection.executeScript("NewsAgent_Data.sql");
            connection.close();
        } catch (DAOExceptionHandler | JDBCExceptionHandler e) {
            e.printStackTrace();
            fail("DAO initialization failed.");
        }

    }

    /**DAO METHOD TESTS*/

    /**
     * Test 015
     * Test for DAO getDelivery method successful
     * ==========
     * Inputs: Delivery object of id=1
     * ==========
     * Expected Outputs: Delivery object of id=1
     */
    public void testDB_Delivery015() {
        try {
            delivery = dao.getDelivery(1);
            assertEquals(1, delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-01-05"), delivery.getDelivery_date());
            assertEquals(true, delivery.isDelivery_status());
            assertEquals(1, delivery.getCustomer_id());
            assertEquals(1, delivery.getInvoice_id());
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /**
     * Test 016
     * Test for DAO getDelivery method unsuccessful
     * ==========
     * Inputs: Delivery object of id=6
     * ==========
     * Expected Outputs: "No delivery with 'delivery_id = " + ID + " found."
     */
    public void testDB_Delivery016() {
        int ID = 6;
        try {
            delivery = dao.getDelivery(ID);
            fail("Exception expected.");
        } catch (DAOExceptionHandler e) {
            assertEquals("No delivery with 'delivery_id = " + ID + " found.", e.getMessage());
        }
    }

    /**
     * Test 017
     * Test for DAO updateDelivery method successful insert
     * ==========
     * Inputs: Delivery object of id=0
     * ==========
     * Expected Outputs: Delivery object of id=6 (new object)
     */
    //
    public void testDB_Delivery017() {
        try {
            delivery.setDelivery_id(0);
            delivery.setDelivery_date(Date.valueOf("2021-04-05"));
            delivery.setDelivery_status(true);
            delivery.setCustomer_id((1));
            delivery.setInvoice_id((3));
            dao.updateDelivery(delivery);
            assertEquals(6, delivery.getDelivery_id());
            assertEquals(Date.valueOf("2021-04-05"), delivery.getDelivery_date());
            assertEquals(true, delivery.isDelivery_status());
        } catch (DAOExceptionHandler | DB_DeliveryExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /**
     * Test 018
     * Test for DAO updateDelivery method successful update
     * ==========
     * Inputs: Delivery object of id = 1
     * ==========
     * Expected Outputs: Delivery object of id=1
     */
    //
    public void testDB_Delivery018() {
        try {
            delivery = dao.getDelivery(1);
            delivery.setDelivery_date(Date.valueOf("2022-08-24"));
            delivery.setDelivery_status(false);
            delivery.setCustomer_id(3);
            delivery.setInvoice_id(3);
            dao.updateDelivery(delivery);
            DB_Delivery delivery2 = dao.getDelivery(1);

            //assertTrue(delivery.equals(delivery2));

            //Comparing the changed object and a new object that took the data after the change
            assertEquals(delivery2.getDelivery_id(), delivery.getDelivery_id());
            assertEquals(delivery2.getDelivery_date(), delivery.getDelivery_date());
            assertEquals(delivery2.getDelivery_status(), delivery.getDelivery_status());
            assertEquals(delivery2.getCustomer_id(), delivery.getCustomer_id());
            assertEquals(delivery2.getInvoice_id(), delivery.getInvoice_id());

        } catch (DAOExceptionHandler | DB_DeliveryExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /**
     * Test 019
     * Test for DAO updateDelivery method unsuccessful update
     * ==========
     * Inputs: Delivery object of id = 9
     * ==========
     * Expected Outputs: "There was delivery_id mishandling."
     */
    //
    public void testDB_Delivery019() {
        try {
            delivery.setDelivery_id(9);
            delivery.setDelivery_date(Date.valueOf("2022-08-24"));
            delivery.setDelivery_status(false);
            delivery.setCustomer_id((3));
            delivery.setInvoice_id((3));

            dao.updateDelivery(delivery);

            fail("Exception expected.");
        } catch (DAOExceptionHandler | DB_DeliveryExceptionHandler e) {
            assertEquals("There was delivery_id mishandling.",e.getMessage());
        }
    }

    /**
     * Test 020
     * Test for DAO deleteDelivery method unsuccessful
     * ==========
     * Inputs: Delivery object of id = 9
     * ==========
     * Expected Outputs: "No delivery with 'delivery_id = " + delivery.getDelivery_id() + " found."
     */
    //
    public void testDB_Delivery020() {
        try {
            delivery.setDelivery_id(9);


            dao.deleteDelivery(delivery);

            fail("Exception expected.");
        } catch (DAOExceptionHandler | DB_DeliveryExceptionHandler e) {
            assertEquals("No delivery with 'delivery_id = " + delivery.getDelivery_id() + " found.",e.getMessage());
        }
    }

    /**
     * Test 021
     * Test for DAO deleteDelivery method successful
     * ==========
     * Inputs: Delivery object of id = 5
     * ==========
     * Expected Outputs: Object deleted
     */
    //
    public void testDB_Delivery021() {
        try {
            delivery.setDelivery_id(5);
            dao.deleteDelivery(delivery);

        } catch (DAOExceptionHandler | DB_DeliveryExceptionHandler e) {
            fail("Exception not expected.");

        }
    }


}
