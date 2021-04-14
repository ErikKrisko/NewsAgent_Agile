import junit.framework.TestCase;

import java.util.ArrayList;

public class DAOTestEmployee extends TestCase
{
    DB_Employee employee = new DB_Employee();
    private DAO dao;

    public void DAOTestEmployee() {
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
     * Test 001
     * Test for DAO getEmployee method successful
     * ==========
     * Inputs: Employee object of id=1
     * ==========
     * Expected Outputs: Employee object of id=1
     */
    public void testDB_getEmployeeTestDAO001(){
        try {
            DAOTestEmployee();
            employee = dao.getEmployee(1);
            assertEquals(1, employee.getEmployee_id());
            assertEquals("John", employee.getFirst_name());
            assertEquals("Doyle", employee.getLast_name());
            dao.close();
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }
    /**
     * Test 002
     * Test for DAO getEmployee method unsuccessful
     * ==========
     * Inputs: Employee object of id=6
     * ==========
     * Expected Outputs: "No employee with 'employee_id = " + ID + " found."
     */
    public void testDB_getEmployeeTestDAO002(){
        int ID = 6;
        try {
            DAOTestEmployee();
            employee = dao.getEmployee(ID);
            fail("Exception expected.");
        }catch (DAOExceptionHandler e){
            assertEquals("No employee with 'employee_id = " + ID + " found.", e.getMessage());
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
        }
    }

    /**
     * Test 001
     * Test for DAO updateEmployee method successful insert
     * ==========
     * Inputs: Employee object of id=0
     * ==========
     * Expected Outputs: Employee object of id=0 (new object)
     */
    public void testDB_insertEmployeeTestDAO001(){
        try {
            DAOTestEmployee();
            employee.setEmployee_id(0);
            employee.setFirst_name("Benjamin");
            employee.setLast_name("Sire");
            dao.updateEmployee(employee);
            assertEquals(6, employee.getEmployee_id());
            assertEquals("Benjamin", employee.getFirst_name());
            assertEquals("Sire", employee.getLast_name());
            dao.close();
        }catch (DAOExceptionHandler | DB_EmployeeExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /**
     * Test 001
     * Test for DAO updateEmployee method successful update
     * ==========
     * Inputs: Employee object of id=1
     * ==========
     * Expected Outputs: Employee object of id=1
     */
    public void testDB_updateEmployeeTestDAO001(){
        try {
            DAOTestEmployee();
            employee = dao.getEmployee(1);
            employee.setFirst_name("Benjamin");
            employee.setLast_name("Sire");
            dao.updateEmployee(employee);
            DB_Employee emp2 = dao.getEmployee(1);

            //Comparing the changed object and a new object that took the data after the change
            assertEquals(emp2.getEmployee_id(), employee.getEmployee_id());
            assertEquals(emp2.getFirst_name(), employee.getFirst_name());
            assertEquals(emp2.getLast_name(), employee.getLast_name());
            dao.close();
        }catch (DAOExceptionHandler | DB_EmployeeExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }
    /**
     * Test 002
     * Test for DAO updateEmployee method unsuccessful update
     * ==========
     * Inputs: Employee object of id=9
     * ==========
     * Expected Outputs: "There was employee_id mishandling."
     */
    public void testDB_updateEmployeeTestDAO002(){
        try {
            DAOTestEmployee();
            employee = dao.getEmployee(9);
            employee.setFirst_name("Benjamin");
            employee.setLast_name("Sire");
            dao.updateEmployee(employee);

            fail("Exception expected.");
        }catch (DAOExceptionHandler | DB_EmployeeExceptionHandler e){
            assertEquals("No employee with 'employee_id = 9 found.", e.getMessage());
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
        }
    }

    /**
     * Test 001
     * Test for DAO deleteEmployee method unsuccessful
     * ==========
     * Inputs: Employee object of id=9
     * ==========
     * Expected Outputs: "No employee with 'employee_id = " + employee.getEmployee_id() + " found."
     */
    public void testDB_deleteEmployeeTestDAO001(){
        try {
            DAOTestEmployee();
            employee.setEmployee_id(9);

            dao.deleteEmployee(employee);

            fail("Exception expected.");
        }catch (DAOExceptionHandler | DB_EmployeeExceptionHandler e){
            assertEquals("No employee with 'employee_id = " + employee.getEmployee_id() + " found.", e.getMessage());
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
        }
    }
    /**
     * Test 002
     * Test for DAO deleteEmployee method successful
     * ==========
     * Inputs: Employee object of id=5
     * ==========
     * Expected Outputs: Object deleted
     */
    public void testDB_deleteEmployeeTestDAO002(){
        try {
            DAOTestEmployee();
            employee.setEmployee_id(5);
            dao.deleteEmployee(employee);
            dao.close();
        }catch (DAOExceptionHandler | DB_EmployeeExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /**
     * Test 001
     * Test for DAO getEmployeesByFName method successful
     * ==========
     * Inputs: Employee first_name='John';
     * ==========
     * Expected Outputs:
     * emp_list.get(0).getEmployee_id() = 1
     * emp_list.get(0).getFirst_name() = "John"
     * emp_list.get(0).getLast_name() = "Doyle"
     */
    public void test_getEmployeesByFName001(){
        String fname = "John";
        try{
            DAOTestEmployee();
            ArrayList<DB_Employee> emp_list = dao.getEmployeesByFName(fname);

            assertEquals(1, emp_list.size());
            DB_Employee emp = emp_list.get(0);

            assertEquals(1, emp.getEmployee_id());
            assertEquals("John", emp.getFirst_name());
            assertEquals("Doyle", emp.getLast_name());

            dao.close();
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /**
     * Test 002
     * Test for DAO getEmployeesByFName method unsuccessful
     * ==========
     * Inputs: Employee first_name='Eric';
     * ==========
     * Expected Outputs: "No employee with first name " + s + " found."
     */
    public void test_getEmployeesByFName002(){
        String fname = "Eric";
        try{
            DAOTestEmployee();
            ArrayList<DB_Employee> emp_list = dao.getEmployeesByFName(fname);

            fail("Exception expected.");

        }catch (DAOExceptionHandler e){
            assertEquals("No employee with first name " + fname + " found.", e.getMessage());
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
        }
    }

    /**
     * Test 001
     * Test for DAO getEmployeesByLName method successful
     * ==========
     * Inputs: Employee first_name='Soul';
     * ==========
     * Expected Outputs:
     * emp_list.get(0).getEmployee_id() = 3
     * emp_list.get(0).getFirst_name() = "Joey"
     * emp_list.get(0).getLast_name() = "Soul"
     */
    public void test_getEmployeesByLName001(){
        String lname = "Soul";
        try{
            DAOTestEmployee();
            ArrayList<DB_Employee> emp_list = dao.getEmployeesByLName(lname);

            assertEquals(1, emp_list.size());
            DB_Employee emp = emp_list.get(0);

            assertEquals(3, emp.getEmployee_id());
            assertEquals("Joey", emp.getFirst_name());
            assertEquals("Soul", emp.getLast_name());

            dao.close();
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /**
     * Test 002
     * Test for DAO getEmployeesByLName method unsuccessful
     * ==========
     * Inputs: Employee first_name='Ispas';
     * ==========
     * Expected Outputs: "No employee with last name " + lname + " found."
     */
    public void test_getEmployeesByLName002(){
        String lname = "Eric";
        try{
            DAOTestEmployee();
            ArrayList<DB_Employee> emp_list = dao.getEmployeesByLName(lname);

            fail("Exception expected.");
        }catch (DAOExceptionHandler e){
            assertEquals("No employee with last name " + lname + " found.", e.getMessage());
            try {
                dao.close();
            } catch (DAOExceptionHandler daoExceptionHandler) {
                daoExceptionHandler.printStackTrace();
            }
        }
    }

    /**
     * Test 001
     * Test for DAO getEmployees method successful first entry
     * ==========
     * Inputs: getEmployees();
     * ==========
     * Expected Outputs:
     * emp_list.get(0).getEmployee_id() = 1
     * emp_list.get(0).getFirst_name() = "John"
     * emp_list.get(0).getLast_name() = "Doyle"
     */
    public void test_getEmployees001(){
        try{
            DAOTestEmployee();
            ArrayList<DB_Employee> emp_list = dao.getEmployees();

            assertEquals(5, emp_list.size());

            DB_Employee emp = emp_list.get(0);

            assertEquals(1, emp.getEmployee_id());
            assertEquals("John", emp.getFirst_name());
            assertEquals("Doyle", emp.getLast_name());

            dao.close();
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /**
     * Test 002
     * Test for DAO getEmployees method successful last entry
     * ==========
     * Inputs: getEmployees();
     * ==========
     * Expected Outputs:
     * emp_list.get(0).getEmployee_id() = 1
     * emp_list.get(0).getFirst_name() = "John"
     * emp_list.get(0).getLast_name() = "Doyle"
     */
    public void test_getEmployees002(){
        try{
            DAOTestEmployee();
            ArrayList<DB_Employee> emp_list = dao.getEmployees();

            assertEquals(5, emp_list.size());

            DB_Employee emp = emp_list.get(4);

            assertEquals(5, emp.getEmployee_id());
            assertEquals("Kerum", emp.getFirst_name());
            assertEquals("Asul", emp.getLast_name());

            dao.close();
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    /**
     * Test 003
     * Test for DAO getEmployees method successful middle entry
     * ==========
     * Inputs: getEmployees(); 3rd entry
     * ==========
     * Expected Outputs:
     * emp_list.get(0).getEmployee_id() = 3
     * emp_list.get(0).getFirst_name() = "Joey"
     * emp_list.get(0).getLast_name() = "Soul"
     */
    public void test_getEmployees003(){
        try{
            DAOTestEmployee();
            ArrayList<DB_Employee> emp_list = dao.getEmployees();

            assertEquals(5, emp_list.size());

            DB_Employee emp = emp_list.get(2);

            assertEquals(3, emp.getEmployee_id());
            assertEquals("Joey", emp.getFirst_name());
            assertEquals("Soul", emp.getLast_name());

            dao.close();
        }catch (DAOExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

}
