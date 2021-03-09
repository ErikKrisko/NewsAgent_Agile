import junit.framework.TestCase;

public class DB_EmployeeTestDAO extends TestCase
{
    DB_Employee employee = new DB_Employee();
    private DAO dao;

    public DB_EmployeeTestDAO() {
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
            employee = dao.getEmployee(1);
            assertEquals(1, employee.getEmployee_id());
            assertEquals("John", employee.getFirst_name());
            assertEquals("Doyle", employee.getLast_name());
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
            employee = dao.getEmployee(ID);
            fail("Exception expected.");
        }catch (DAOExceptionHandler e){
            assertEquals("No employee with 'employee_id = " + ID + " found.", e.getMessage());
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
            employee.setEmployee_id(0);
            employee.setFirst_name("Benjamin");
            employee.setLast_name("Sire");
            dao.updateEmployee(employee);
            assertEquals(6, employee.getEmployee_id());
            assertEquals("Benjamin", employee.getFirst_name());
            assertEquals("Sire", employee.getLast_name());
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
            employee = dao.getEmployee(2);
            employee.setFirst_name("Benjamin");
            employee.setLast_name("Sire");
            dao.updateEmployee(employee);
            DB_Employee emp2 = dao.getEmployee(1);

            //Comparing the changed object and a new object that took the data after the change
            assertEquals(emp2.getEmployee_id(), employee.getEmployee_id());
            assertEquals(emp2.getFirst_name(), employee.getFirst_name());
            assertEquals(emp2.getLast_name(), employee.getLast_name());

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
            employee = dao.getEmployee(9);
            employee.setFirst_name("Benjamin");
            employee.setLast_name("Sire");
            dao.updateEmployee(employee);

            fail("Exception expected.");
        }catch (DAOExceptionHandler | DB_EmployeeExceptionHandler e){
            assertEquals("No employee with 'employee_id = 9 found.", e.getMessage());
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
            employee.setEmployee_id(9);

            dao.deleteEmployee(employee);

            fail("Exception expected.");
        }catch (DAOExceptionHandler | DB_EmployeeExceptionHandler e){
            assertEquals("No employee with 'employee_id = " + employee.getEmployee_id() + " found.", e.getMessage());
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
            employee.setEmployee_id(5);
            dao.deleteEmployee(employee);

        }catch (DAOExceptionHandler | DB_EmployeeExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

}
