import junit.framework.TestCase;

public class DB_EmployeeTest extends TestCase {
    DB_Employee employee = new DB_Employee();

    //Employee_id tests
    /** TEST 001
     *  Test emoployee_id for successful lowest
     *  ==========
     *  Inputs: 0
     *  ==========
     *  Expected Outputs:   0
     */
    public void testDB_Employee001() {
        try {
            assertEquals(0, employee.validateEmpID(0));
        } catch (DB_EmployeeExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }
    /** TEST 002
     *  Test emoployee_id for successful greater
     *  ==========
     *  Inputs: 5
     *  ==========
     *  Expected Outputs:   5
     */
    public void testDB_Employee002() {
        try {
            assertEquals(5, employee.validateEmpID(5));
        } catch (DB_EmployeeExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }
    /** TEST 003
     *  Test emoployee_id for unsuccessful lowest
     *  ==========
     *  Inputs: -1
     *  ==========
     *  Expected Outputs: "employee_id has to be greater than or equal to 0"
     */
    public void testDB_Employee003() {
        try {
            employee.validateEmpID(-1);
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("employee_id has to be greater than or equal to 0", e.getMessage());
        }
    }
    /** TEST 004
     *  Test emoployee_id for unsuccessful greater
     *  ==========
     *  Inputs: -5
     *  ==========
     *  Expected Outputs: "employee_id has to be greater than or equal to 0"
     */
    public void testDB_Employee004() {
        try {
            employee.validateEmpID(-5);
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("employee_id has to be greater than or equal to 0", e.getMessage());
        }
    }

    //First_name Test
    /** TEST 005
     *  Test first_name for empty string
     *  ==========
     *  Inputs: employee.validateFName("");
     *  ==========
     *  Expected Outputs: "Entry = " + entry + " cannot be an empty String."
     */
    public void testDB_Employee005() {
        String entry = "";
        try {
            employee.validateFName(entry);
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("first_name = " + entry + " cannot be an empty String.", e.getMessage());
        }
    }
    /** TEST 006
     *  Test first_name for too long string
     *  ==========
     *  Inputs: employee.validateFName("Benjaminsmallppdumbss");
     *  ==========
     *  Expected Outputs: "Entry = " + entry + " cannot be an empty String."
     */
    public void testDB_Employee006() {
        String entry = "Benjaminsmallppdumbss";
        try {
            employee.validateFName(entry);
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("first_name = " + entry + " cannot be that longer than 20 characters.", e.getMessage());
        }
    }
    /** TEST 007
     *  Test first_name for containing numbers
     *  ==========
     *  Inputs: employee.validateFName("Benj4min");
     *  ==========
     *  Expected Outputs: "Entry = " + entry + " cannot be an empty String."
     */
    public void testDB_Employee007() {
        String entry = "Benj4min";
        try {
            employee.validateFName(entry);
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("first_name = " + entry + " cannot contain numbers.", e.getMessage());
        }
    }
    /** TEST 008
     *  Test first_name for shortest possible string
     *  ==========
     *  Inputs: employee.validateFName("B");
     *  ==========
     *  Expected Outputs: "B"
     */
    public void testDB_Employee008() {
        String entry = "B";
        try {
            assertEquals("B", employee.validateFName("B"));
        } catch (DB_EmployeeExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }
    /** TEST 009
     *  Test first_name for shortest possible string
     *  ==========
     *  Inputs: employee.validateFName("Benjaminsmallppdumbs");
     *  ==========
     *  Expected Outputs: "Benjaminsmallppdumbs"
     */
    public void testDB_Employee009() {
        String entry = "Benjaminsmallppdumbs";
        try {
            assertEquals("Benjaminsmallppdumbs", employee.validateFName(entry));
        } catch (DB_EmployeeExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    //First_name Test
    /** TEST 010
     *  Test last_name for empty string
     *  ==========
     *  Inputs: employee.validateLName("");
     *  ==========
     *  Expected Outputs: "Entry = " + entry + " cannot be an empty String."
     */
    public void testDB_Employee010() {
        String entry = "";
        try {
            employee.validateLName(entry);
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("last_name = " + entry + " cannot be an empty String.", e.getMessage());
        }
    }
    /** TEST 011
     *  Test last_name for too long string
     *  ==========
     *  Inputs: employee.validateLName("Benjaminsmallppdumbss");
     *  ==========
     *  Expected Outputs: "Entry = " + entry + " cannot be an empty String."
     */
    public void testDB_Employee011() {
        String entry = "Benjaminsmallppdumbss";
        try {
            employee.validateLName(entry);
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("last_name = " + entry + " cannot be that longer than 20 characters.", e.getMessage());
        }
    }
    /** TEST 012
     *  Test last_name for containing numbers
     *  ==========
     *  Inputs: employee.validateLName("Benj4min");
     *  ==========
     *  Expected Outputs: "Entry = " + entry + " cannot be an empty String."
     */
    public void testDB_Employee012() {
        String entry = "Benj4min";
        try {
            employee.validateLName(entry);
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("last_name = " + entry + " cannot contain numbers.", e.getMessage());
        }
    }
    /** TEST 013
     *  Test last_name for shortest possible string
     *  ==========
     *  Inputs: employee.validateLName("B");
     *  ==========
     *  Expected Outputs: "B"
     */
    public void testDB_Employee013() {
        String entry = "B";
        try {
            assertEquals("B", employee.validateLName("B"));
        } catch (DB_EmployeeExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }
    /** TEST 014
     *  Test last_name for shortest possible string
     *  ==========
     *  Inputs: employee.validateLName("Benjaminsmallppdumbs");
     *  ==========
     *  Expected Outputs: "Benjaminsmallppdumbs"
     */
    public void testDB_Employee014() {
        String entry = "Benjaminsmallppdumbs";
        try {
            assertEquals("Benjaminsmallppdumbs", employee.validateLName(entry));
        } catch (DB_EmployeeExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

    //  Constructor Test
    /** TEST 015
     *  Test for employee constructor fail due to employee_id out of bounds
     *  ==========
     *  Inputs: new DB_Employee(employee.validateEmpID(-1), employee.validateNames("Benjamin"), employee.validateNames("Sire"))
     *  ==========
     *  Expected Outputs: "employee_id has to be greater than or equal to 0"
     */
    public void testDB_Employee015() {
        try {
            new DB_Employee(employee.validateEmpID(-1), employee.validateFName("Benjamin"), employee.validateLName("Sire"));
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("employee_id has to be greater than or equal to 0", e.getMessage());
        }
    }
    /** TEST 016
     *  Test for employee constructor fail due to last_name out of bounds
     *  ==========
     *  Inputs: new DB_Employee(employee.validateEmpID(1), employee.validateNames("Benjamin"), employee.validateNames("SireSireSireSireSireSire"))
     *  ==========
     *  Expected Outputs: "last_name = " + entry + " cannot be that longer than 20 characters."
     */
    public void testDB_Employee016() {
        String entry = "SireSireSireSireSireSire";
        try {
            new DB_Employee(employee.validateEmpID(0), employee.validateFName("Benjamin"), employee.validateLName("SireSireSireSireSireSire"));
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("last_name = " + entry + " cannot be that longer than 20 characters.", e.getMessage());
        }
    }
    /** TEST 017
     *  Test for employee constructor fail due to first_name out of bounds
     *  ==========
     *  Inputs: new DB_Employee(employee.validateEmpID(1), employee.validateNames("BenjaminHappyJustToday"), employee.validateNames("Sire"))
     *  ==========
     *  Expected Outputs: "first_name = " + entry + " cannot be that longer than 20 characters."
     */
    public void testDB_Employee017() {
        String entry = "BenjaminHappyJustToday";
        try {
            new DB_Employee(employee.validateEmpID(0), employee.validateFName("BenjaminHappyJustToday"), employee.validateLName("Sire"));
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("first_name = " + entry + " cannot be that longer than 20 characters.", e.getMessage());
        }
    }
    /** TEST 018
     *  Test for employee constructor fail due to first_name being empty
     *  ==========
     *  Inputs: new DB_Employee(employee.validateEmpID(1), employee.validateNames(""), employee.validateNames("Sire"))
     *  ==========
     *  Expected Outputs: "last_name = " + entry + " cannot be an empty String."
     */
    public void testDB_Employee018() {
        String entry= "";
        try {
            new DB_Employee(employee.validateEmpID(0), employee.validateFName(""), employee.validateLName("Sire"));
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("first_name = " + entry + " cannot be an empty String.", e.getMessage());
        }
    }
    /** TEST 019
     *  Test for employee constructor fail due to last_name being empty
     *  ==========
     *  Inputs: new DB_Employee(employee.validateEmpID(1), employee.validateNames("Benjamin"), employee.validateNames(""))
     *  ==========
     *  Expected Outputs: "last_name = " + entry + " cannot be an empty String."
     */
    public void testDB_Employee019() {
        String entry= "";
        try {
            new DB_Employee(employee.validateEmpID(0), employee.validateFName("Benjamin"), employee.validateLName(""));
            fail("Exception expected.");
        } catch (DB_EmployeeExceptionHandler e){
            assertEquals("last_name = " + entry + " cannot be an empty String.", e.getMessage());
        }
    }
    /** TEST 020
     *  Test for employee constructor successful
     *  ==========
     *  Inputs: new DB_Employee(employee.validateEmpID(1), employee.validateNames("Benjamin"), employee.validateNames("Sire"))
     *  ==========
     *  Expected Outputs: "last_name = " + entry + " cannot be an empty String."
     */
    public void testDB_Employee020() {
        try {
            DB_Employee testEmployee = new DB_Employee(1, employee.validateFName("Benjamin"), employee.validateLName("Sire"));
            assertEquals(1, testEmployee.getEmployee_id());
            assertEquals("Benjamin", testEmployee.getFirst_name());
            assertEquals("Sire", testEmployee.getLast_name());
        } catch (DB_EmployeeExceptionHandler e){
            e.printStackTrace();
            fail("Exception not expected.");
        }
    }

}