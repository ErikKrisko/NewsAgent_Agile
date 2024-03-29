import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;

public class DAOTestDelivery extends TestCase {
    DB_Delivery delivery = new DB_Delivery();
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
            //  Close any existing connection if it exists.
            if (dao != null && !dao.isClosed()) {
                dao.close();
            }
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
            initializeDatabase();
            delivery = dao.getDelivery(1);
            assertEquals(1, delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-01-05"), delivery.getDelivery_date());
            assertTrue(delivery.isDelivery_status());
            assertEquals(1, delivery.getCustomer_id());
            assertEquals(1, delivery.getInvoice_id());
            //  Close the DAO
            dao.close();
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Test initialization failed.");
        }
    }

    /**
     * Test 016
     * Test for DAO getDelivery method unsuccessful
     * ==========
     * Inputs: Delivery object of id=16
     * ==========
     * Expected Outputs: "No delivery with 'delivery_id = " + ID + " found."
     */
    public void testDB_Delivery016() {
        int ID = 12;
        try {
            initializeDatabase();
            delivery = dao.getDelivery(ID);
            fail("Exception expected.");
        } catch (DAOExceptionHandler e) {
            assertEquals("No delivery with 'delivery_id = " + ID + " found.", e.getMessage());
            //  Close the DAO
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
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
            initializeDatabase();
            delivery.setDelivery_id(0);
            delivery.setDelivery_date(Date.valueOf(String.valueOf(new Date(System.currentTimeMillis()))));
            delivery.setDelivery_status(true);
            delivery.setCustomer_id((1));
            delivery.setInvoice_id((3));
            delivery.setProd_id(2);
            dao.updateDelivery(delivery);
            assertEquals(12, delivery.getDelivery_id());
            assertEquals(Date.valueOf(String.valueOf(new Date(System.currentTimeMillis()))), delivery.getDelivery_date());
            assertTrue(delivery.isDelivery_status());
            //  Close the DAO
            dao.close();
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
            initializeDatabase();
            delivery = dao.getDelivery(1);
            delivery.setDelivery_date(Date.valueOf("2022-08-24"));
            delivery.setDelivery_status(false);
            delivery.setCustomer_id(3);
            delivery.setInvoice_id(3);
            delivery.setProd_id(3);
            dao.updateDelivery(delivery);
            DB_Delivery delivery2 = dao.getDelivery(1);


            //Comparing the changed object and a new object that took the data after the change
            assertEquals(delivery2.getDelivery_id(), delivery.getDelivery_id());
            assertEquals(delivery2.getDelivery_date(), delivery.getDelivery_date());
            assertEquals(delivery2.getDelivery_status(), delivery.getDelivery_status());
            assertEquals(delivery2.getCustomer_id(), delivery.getCustomer_id());
            assertEquals(delivery2.getInvoice_id(), delivery.getInvoice_id());
            assertEquals(delivery2.getProd_id(), delivery.getProd_id());
//  Close the DAO
            dao.close();
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
            initializeDatabase();
            delivery.setDelivery_id(12);
            delivery.setDelivery_date(Date.valueOf("2022-08-24"));
            delivery.setDelivery_status(false);
            delivery.setCustomer_id((3));
            delivery.setInvoice_id((3));
            delivery.setProd_id(2);

            dao.updateDelivery(delivery);

            fail("Exception expected.");
        } catch (DAOExceptionHandler | DB_DeliveryExceptionHandler e) {
            assertEquals("There was delivery_id mishandling.",e.getMessage());
            //  Close the DAO
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
        }
    }

    /**
     * Test 020
     * Test for DAO deleteDelivery method unsuccessful
     * ==========
     * Inputs: Delivery object of id = 14
     * ==========
     * Expected Outputs: "No delivery with 'delivery_id = " + delivery.getDelivery_id() + " found."
     */
    //
    public void testDB_Delivery020() {
        try {
            initializeDatabase();
            delivery.setDelivery_id(14);

            dao.deleteDelivery(delivery);

            fail("Exception expected.");
        } catch (DAOExceptionHandler | DB_DeliveryExceptionHandler e) {
            assertEquals("No delivery with 'delivery_id = " + delivery.getDelivery_id() + " found.",e.getMessage());
            //  Close the DAO
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
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
            initializeDatabase();
            delivery.setDelivery_id(5);
            dao.deleteDelivery(delivery);

            //  Close the DAO
            dao.close();
        } catch (DAOExceptionHandler | DB_DeliveryExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

    /**
     * Test 001 - getDeliveriesByDate
     * Test for DAO getDeliveriesByDate method successful first entry
     * ==========
     * Inputs: delivery_date=2022-01-05
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 1
     *                          delivery_list.get(0).getDelivery_date() = 2022-01-05
     *                          delivery_list.get(0).getDelivery_status() = 1
     *                          delivery_list.get(0).getCustomer_id() = 1
     *                          delivery_list.get(0).getInvoice_id() = 1
     *                          delivery_list.get(0).getProd_id() = 8
     */
    public void testDB_getDeliveriesByDate001(){
        try {
            initializeDatabase();
            Date date = Date.valueOf("2022-01-05");

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByDate(date);
            //Check del_list size
            assertEquals(5, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(0);
            //Compare delivery object information
            assertEquals(1, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-01-05"), test_delivery.getDelivery_date());
            assertEquals(1, test_delivery.getDelivery_status());
            assertEquals(1, test_delivery.getCustomer_id());
            assertEquals(1, test_delivery.getInvoice_id());
            assertEquals(8, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /**
     * Test 002 - getDeliveriesByDate
     * Test for DAO getDeliveriesByDate method successful last entry
     * ==========
     * Inputs: delivery_date=2022-01-05
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 11
     *                          delivery_list.get(0).getDelivery_date() = 2022-01-05
     *                          delivery_list.get(0).getDelivery_status() = 0
     *                          delivery_list.get(0).getCustomer_id() = 4
     *                          delivery_list.get(0).getInvoice_id() = 1
     *                          delivery_list.get(0).getProd_id() = 2
     */
    public void testDB_getDeliveriesByDate002(){
        try {
            initializeDatabase();
            Date date = Date.valueOf("2022-01-05");

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByDate(date);
            //Check del_list size
            assertEquals(5, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(4);
            //Compare delivery object information
            assertEquals(11, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-01-05"), test_delivery.getDelivery_date());
            assertEquals(0, test_delivery.getDelivery_status());
            assertEquals(4, test_delivery.getCustomer_id());
            assertEquals(1, test_delivery.getInvoice_id());
            assertEquals(2, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }
    /**
     * Test 003 - getDeliveriesByDate
     * Test for DAO getDeliveriesByDate method successful second entry
     * ==========
     * Inputs: delivery_date=2022-01-05
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 8
     *                          delivery_list.get(0).getDelivery_date() = 2022-01-05
     *                          delivery_list.get(0).getDelivery_status() = 1
     *                          delivery_list.get(0).getCustomer_id() = 1
     *                          delivery_list.get(0).getInvoice_id() = 3
     *                          delivery_list.get(0).getProd_id() = 1
     */
    public void testDB_getDeliveriesByDate003(){
        try {
            initializeDatabase();
            Date date = Date.valueOf("2022-01-05");

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByDate(date);
            //Check del_list size
            assertEquals(5, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(1);
            //Compare delivery object information
            assertEquals(8, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-01-05"), test_delivery.getDelivery_date());
            assertEquals(1, test_delivery.getDelivery_status());
            assertEquals(1, test_delivery.getCustomer_id());
            assertEquals(3, test_delivery.getInvoice_id());
            assertEquals(1, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }
    /**
     * Test 004 - getDeliveriesByDate
     * Test for DAO getDeliveriesByDate method successful fourth entry
     * ==========
     * Inputs: delivery_date=2022-01-05
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 10
     *                          delivery_list.get(0).getDelivery_date() = 2022-01-05
     *                          delivery_list.get(0).getDelivery_status() = 1
     *                          delivery_list.get(0).getCustomer_id() = 3
     *                          delivery_list.get(0).getInvoice_id() = 5
     *                          delivery_list.get(0).getProd_id() = 6
     */
    public void testDB_getDeliveriesByDate004(){
        try {
            initializeDatabase();
            Date date = Date.valueOf("2022-01-05");

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByDate(date);
            //Check del_list size
            assertEquals(5, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(3);
            //Compare delivery object information
            assertEquals(10, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-01-05"), test_delivery.getDelivery_date());
            assertEquals(1, test_delivery.getDelivery_status());
            assertEquals(3, test_delivery.getCustomer_id());
            assertEquals(5, test_delivery.getInvoice_id());
            assertEquals(6, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /**
     * Test 005 - getDeliveriesByDate
     * Test for DAO getDeliveriesByDate method unsuccessful non matching date
     * ==========
     * Inputs: delivery_date=2021-01-05
     * ==========
     * Expected Outputs: "No delivery with date " + date + " found"
     */
    public void testDB_getDeliveriesByDate005(){
        try {
            initializeDatabase();
            Date date = Date.valueOf("2021-01-05");

            dao.getDeliveriesByDate(date);

            fail("Exception Expected");
        }catch(DAOExceptionHandler e){
            assertEquals("No delivery with date " + "2021-01-05" + " found.", e.getMessage());
            //  Close the DAO
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
        }
    }

    /** Test 006 - createDeliveriesForSubscriptionDate
     *  Test for fail empty / null subscription list provided
     */
    public void testCreateDeliveriesForSubscriptionDate001() {
        try {
            initializeDatabase();
            dao.createDeliveriesForSubscriptionDate(null, Date.valueOf("2021-04-14"));
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("Subscription list cannot be empty.", e.getMessage());
        }
    }

    /** Test 007 - createDeliveriesForSubscriptionDate
     *  Test for existing delivery
     */
    public void testCreateDeliveriesForSubscriptionDate002() {
        try {
            initializeDatabase();
            Date date = Date.valueOf("2021-04-14");
            ArrayList<DB_Subscription> subscriptions = dao.getSubscriptionsForDate(date, true, true);
            ArrayList<DB_Delivery> deliveries = dao.createDeliveriesForSubscriptionDate(subscriptions, date);
            //  Tests individual entries to confirm
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
            fail("Exception not expected");
        }
    }

    /** Test 008 - createDeliveriesForSubscriptionDate
     *  Test for null date
     */
    public void testGetDeliveriesForSubscriptionDate003() {
        try {
            initializeDatabase();
            ArrayList<DB_Subscription> subscriptions = dao.getSubscriptionsForDate(Date.valueOf("2021-04-14"), true, true);
            dao.createDeliveriesForSubscriptionDate(subscriptions, null);
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("Date has to be after '2000-01-01' and cannot be null.", e.getMessage());
        }
    }

    /** Test 009 - deleteDeliveryByDate
     *  Test for deletion fail (no given date found)
     */
    public void testDeleteDeliveryByDate001() {
        try {
            initializeDatabase();
            dao.deleteDeliveriesByDate(Date.valueOf("2021-04-14"));
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("No delivery with date " + "2021-04-14" + " found.", e.getMessage());
        }
    }

    /** Test 010 - deleteDeliveryByDate
     *  Test for deletion success
     */
    public void testDeleteDeliveryByDate002() {
        try {
            initializeDatabase();
            assertEquals(5, dao.deleteDeliveriesByDate(Date.valueOf("2022-01-05")));
            dao.getDeliveriesByDate(Date.valueOf("2022-01-05"));
            fail("Exception expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("No delivery with date " + "2022-01-05" + " found.", e.getMessage());
        }
    }

    /**
     * Test 001 - getDeliveriesByCustomer
     * Test for DAO getDeliveriesByCustomer method successful cus id 1 first entry
     * ==========
     * Inputs: customer_id=1
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 1
     *                          delivery_list.get(0).getDelivery_date() = 2022-01-05
     *                          delivery_list.get(0).getDelivery_status() = 1
     *                          delivery_list.get(0).getCustomer_id() = 1
     *                          delivery_list.get(0).getInvoice_id() = 1
     *                          delivery_list.get(0).getProd_id() = 8
     */
    public void testDB_getDeliveriesByCustomer001(){
        try {
            initializeDatabase();
            int id = 1;

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByCustomer(id);
            //Check del_list size
            assertEquals(3, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(0);
            //Compare delivery object information
            assertEquals(1, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-01-05"), test_delivery.getDelivery_date());
            assertEquals(1, test_delivery.getDelivery_status());
            assertEquals(1, test_delivery.getCustomer_id());
            assertEquals(1, test_delivery.getInvoice_id());
            assertEquals(8, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /**
     * Test 002 - getDeliveriesByCustomer
     * Test for DAO getDeliveriesByCustomer method successful cus id 1 last entry
     * ==========
     * Inputs: customer_id=1
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 1
     *                          delivery_list.get(0).getDelivery_date() = 2022-01-05
     *                          delivery_list.get(0).getDelivery_status() = 1
     *                          delivery_list.get(0).getCustomer_id() = 1
     *                          delivery_list.get(0).getInvoice_id() = 1
     *                          delivery_list.get(0).getProd_id() = 8
     */
    public void testDB_getDeliveriesByCustomer002(){
        try {
            initializeDatabase();
            int id = 1;

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByCustomer(id);
            //Check del_list size
            assertEquals(3, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(2);
            //Compare delivery object information
            assertEquals(8, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-01-05"), test_delivery.getDelivery_date());
            assertEquals(1, test_delivery.getDelivery_status());
            assertEquals(1, test_delivery.getCustomer_id());
            assertEquals(3, test_delivery.getInvoice_id());
            assertEquals(1, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /**
     * Test 003 - getDeliveriesByCustomer
     * Test for DAO getDeliveriesByCustomer method successful cus id 1 middle entry
     * ==========
     * Inputs: customer_id=1
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 6
     *                          delivery_list.get(0).getDelivery_date() = 2022-02-05
     *                          delivery_list.get(0).getDelivery_status() = 1
     *                          delivery_list.get(0).getCustomer_id() = 1
     *                          delivery_list.get(0).getInvoice_id() = 1
     *                          delivery_list.get(0).getProd_id() = 8
     */
    public void testDB_getDeliveriesByCustomer003(){
        try {
            initializeDatabase();
            int id = 1;

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByCustomer(id);
            //Check del_list size
            assertEquals(3, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(1);
            //Compare delivery object information
            assertEquals(6, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-02-05"), test_delivery.getDelivery_date());
            assertEquals(1, test_delivery.getDelivery_status());
            assertEquals(1, test_delivery.getCustomer_id());
            assertEquals(1, test_delivery.getInvoice_id());
            assertEquals(8, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /**
     * Test 004 - getDeliveriesByCustomer
     * Test for DAO getDeliveriesByCustomer method unsuccessful non matching id
     * ==========
     * Inputs: id = dao.getDeliveries().size();
     * ==========
     * Expected Outputs: "No delivery with customer_id " + date
     */
    public void testDB_getDeliveriesByCustomer004() {
        int id = 0;
        try {
            initializeDatabase();
            id = dao.getDeliveries().size();


            dao.getDeliveriesByCustomer(id);

            fail("Exception Expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("No Deliveries with customer_id = " + id, e.getMessage());
            //  Close the DAO
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
        }
    }


    /**
     * Test 001 - getDeliveriesByInvoice
     * Test for DAO getDeliveriesByInvoice method successful inv id 1 first entry
     * ==========
     * Inputs: Invoice_id=1
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 1
     *                          delivery_list.get(0).getDelivery_date() = 2022-01-05
     *                          delivery_list.get(0).getDelivery_status() = 1
     *                          delivery_list.get(0).getCustomer_id() = 1
     *                          delivery_list.get(0).getInvoice_id() = 1
     *                          delivery_list.get(0).getProd_id() = 8
     */
    public void testDB_getDeliveriesByInvoice001(){
        try {
            initializeDatabase();
            int id = 1;

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByInvoice(id);
            //Check del_list size
            assertEquals(4, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(0);
            //Compare delivery object information
            assertEquals(1, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-01-05"), test_delivery.getDelivery_date());
            assertEquals(1, test_delivery.getDelivery_status());
            assertEquals(1, test_delivery.getCustomer_id());
            assertEquals(1, test_delivery.getInvoice_id());
            assertEquals(8, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /**
     * Test 002 - getDeliveriesByInvoice
     * Test for DAO getDeliveriesByInvoice method successful inv id 1 last entry
     * ==========
     * Inputs: Invoice_id=1
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 11
     *                          delivery_list.get(0).getDelivery_date() = 2022-01-05
     *                          delivery_list.get(0).getDelivery_status() = 0
     *                          delivery_list.get(0).getCustomer_id() = 4
     *                          delivery_list.get(0).getInvoice_id() = 1
     *                          delivery_list.get(0).getProd_id() = 2
     */
    public void testDB_getDeliveriesByInvoice002(){
        try {
            initializeDatabase();
            int id = 1;

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByInvoice(id);
            //Check del_list size
            assertEquals(4, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(3);
            //Compare delivery object information
            assertEquals(11, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-01-05"), test_delivery.getDelivery_date());
            assertEquals(0, test_delivery.getDelivery_status());
            assertEquals(4, test_delivery.getCustomer_id());
            assertEquals(1, test_delivery.getInvoice_id());
            assertEquals(2, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /**
     * Test 003 - getDeliveriesByInvoice
     * Test for DAO getDeliveriesByInvoice method successful inv id 1 middle entry
     * ==========
     * Inputs: Invoice_id=1
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 6
     *                          delivery_list.get(0).getDelivery_date() = 2022-02-05
     *                          delivery_list.get(0).getDelivery_status() = 1
     *                          delivery_list.get(0).getCustomer_id() = 1
     *                          delivery_list.get(0).getInvoice_id() = 1
     *                          delivery_list.get(0).getProd_id() = 8
     */
    public void testDB_getDeliveriesByInvoice003(){
        try {
            initializeDatabase();
            int id = 1;

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByInvoice(id);
            //Check del_list size
            assertEquals(4, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(1);
            //Compare delivery object information
            assertEquals(6, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-02-05"), test_delivery.getDelivery_date());
            assertEquals(1, test_delivery.getDelivery_status());
            assertEquals(1, test_delivery.getCustomer_id());
            assertEquals(1, test_delivery.getInvoice_id());
            assertEquals(8, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /**
     * Test 004 - getDeliveriesByInvoice
     * Test for DAO getDeliveriesByInvoice method unsuccessful non matching id
     * ==========
     * Inputs: invoice id = dao.getDeliveries().size();
     * ==========
     * Expected Outputs: "No delivery with invoice_id " + id
     */
    public void testDB_getDeliveriesByInvoice004() {
        int id = 0;
        try {
            initializeDatabase();
            id = dao.getDeliveries().size();


            dao.getDeliveriesByInvoice(id);

            fail("Exception Expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("No Deliveries with invoice_id = " + id, e.getMessage());
            //  Close the DAO
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
        }
    }

    /**
     * Test 001 - getDeliveriesByPublication
     * Test for DAO getDeliveriesByPublication method successful Publication id 8 first entry
     * ==========
     * Inputs: Publication_id=8
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 1
     *                          delivery_list.get(0).getDelivery_date() = 2022-01-05
     *                          delivery_list.get(0).getDelivery_status() = 1
     *                          delivery_list.get(0).getCustomer_id() = 1
     *                          delivery_list.get(0).getPublication_id() = 1
     *                          delivery_list.get(0).getPublication_id() = 8
     */
    public void testDB_getDeliveriesByPublication001(){
        try {
            initializeDatabase();
            int id = 8;

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByPublication(id);
            //Check del_list size
            assertEquals(3, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(0);
            //Compare delivery object information
            assertEquals(1, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-01-05"), test_delivery.getDelivery_date());
            assertEquals(1, test_delivery.getDelivery_status());
            assertEquals(1, test_delivery.getCustomer_id());
            assertEquals(1, test_delivery.getInvoice_id());
            assertEquals(8, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /**
     * Test 002 - getDeliveriesByPublication
     * Test for DAO getDeliveriesByPublication method successful Publication id 8 last entry
     * ==========
     * Inputs: Publication_id=8
     * ==========
     * Expected Outputs:
     *                          delivery_list.get(0).getDelivery_id() = 6
     *                          delivery_list.get(0).getDelivery_date() = 2022-02-05
     *                          delivery_list.get(0).getDelivery_status() = 1
     *                          delivery_list.get(0).getCustomer_id() = 1
     *                          delivery_list.get(0).getPublication_id() = 1
     *                          delivery_list.get(0).getPublication_id() = 8
     */
    public void testDB_getDeliveriesByPublication002(){
        try {
            initializeDatabase();
            int id = 8;

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByPublication(id);
            //Check del_list size
            assertEquals(3, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(2);
            //Compare delivery object information
            assertEquals(6, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-02-05"), test_delivery.getDelivery_date());
            assertEquals(1, test_delivery.getDelivery_status());
            assertEquals(1, test_delivery.getCustomer_id());
            assertEquals(1, test_delivery.getInvoice_id());
            assertEquals(8, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /**
     * Test 003 - getDeliveriesByPublication
     * Test for DAO getDeliveriesByPublication method successful Publication id 8 middle entry
     * ==========
     * Inputs: Publication_id=8
     * ==========
     * Expected Outputs:
     *                          del_list.get(0).getDelivery_id() = 3
     *                          del_list.get(0).getDelivery_date() = 2022-07-18
     *                          del_list.get(0).getDelivery_status() = 1
     *                          del_list.get(0).getCustomer_id() = 3
     *                          del_list.get(0).getPublication_id() = 3
     *                          del_list.get(0).getPublication_id() = 8
     */
    public void testDB_getDeliveriesByPublication003(){
        try {
            initializeDatabase();
            int id = 8;

            ArrayList<DB_Delivery> del_list = dao.getDeliveriesByPublication(id);
            //Check del_list size
            assertEquals(3, del_list.size());
            //Get first entry
            DB_Delivery test_delivery = del_list.get(1);
            //Compare delivery object information
            assertEquals(3, test_delivery.getDelivery_id());
            assertEquals(Date.valueOf("2022-07-18"), test_delivery.getDelivery_date());
            assertEquals(1, test_delivery.getDelivery_status());
            assertEquals(3, test_delivery.getCustomer_id());
            assertEquals(3, test_delivery.getInvoice_id());
            assertEquals(8, test_delivery.getProd_id());
            //  Close the DAO
            dao.close();
        }catch(DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not Expected");
        }
    }

    /**
     * Test 004 - getDeliveriesByPublication
     * Test for DAO getDeliveriesByDate method unsuccessful non matching id
     * ==========
     * Inputs: id = dao.getDeliveries().size();
     * ==========
     * Expected Outputs: "No delivery with prod_id " + id
     */
    public void testDB_getDeliveriesByPublication004() {
        int id = 0;
        try {
            initializeDatabase();
            id = dao.getDeliveries().size();


            dao.getDeliveriesByPublication(id);

            fail("Exception Expected");
        } catch (DAOExceptionHandler e) {
            assertEquals("No Deliveries with prod_id = " + id, e.getMessage());
            //  Close the DAO
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
        }
    }

}
