import java.sql.ResultSet;

public class DB_Employee
{
    //  Base Customer Attributes
    private long employee_id;
    private String first_name;
    private String last_name;

    public DB_Employee(){}

    public DB_Employee(long employee_id, String first_name, String last_name){
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }


    /** Validate Attributes */
    public long validateEmpID(long entry) throws DB_EmployeeExceptionHandler {
        if(entry >= 0)
        {
            return entry;
        }
        else
        {
            throw new DB_EmployeeExceptionHandler("employee_id has to be greater than or equal to 0");
        }
    }

    public String validateFName(String entry) throws DB_EmployeeExceptionHandler {
        if(!entry.isBlank() || !entry.isEmpty()){
            if(entry.length() <= 20){
                if(!entry.matches(".*\\d.*")){
                    return entry;
                }else{
                    throw new DB_EmployeeExceptionHandler("first_name = " + entry + " cannot contain numbers.");
                }
            }else{
                throw new DB_EmployeeExceptionHandler("first_name = " + entry + " cannot be longer than 20 characters.");
            }
        }else{
            throw new DB_EmployeeExceptionHandler("first_name = " + entry + " cannot be an empty String.");
        }
    }

    public String validateLName(String entry) throws DB_EmployeeExceptionHandler {
        if(!entry.isBlank() || !entry.isEmpty()){
            if(entry.length() <= 20){
                if(!entry.matches(".*\\d.*")){
                    return entry;
                }else{
                    throw new DB_EmployeeExceptionHandler("last_name = " + entry + " cannot contain numbers.");
                }
            }else{
                throw new DB_EmployeeExceptionHandler("last_name = " + entry + " cannot be longer than 20 characters.");
            }
        }else{
            throw new DB_EmployeeExceptionHandler("last_name = " + entry + " cannot be an empty String.");
        }
    }


    /** Auto Generated toString method  */
    @Override
    public String toString() {
        return "DB_Employee{" +
                "employee_id=" + employee_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }

    /** Auto Generated Getters and Setters  */
    public long getEmployee_id() { return employee_id; }
    public void setEmployee_id(long employee_id) throws DB_EmployeeExceptionHandler { this.employee_id = validateEmpID(employee_id); }
    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) throws DB_EmployeeExceptionHandler { this.first_name = validateFName(first_name); }
    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) throws DB_EmployeeExceptionHandler { this.last_name = validateLName(last_name); }

}

//Exception Handler
class DB_EmployeeExceptionHandler extends Exception
{
    String message;

    public DB_EmployeeExceptionHandler(String errMessage){
        message = errMessage;
    }

    public String getMessage() {
        return message;
    }
}

//Attribute Enum
enum att_employee {
    employee_id(1, "employee_id"),
    first_name(2, "first_name"),
    last_name(3 , "last_name");

    public final int column;
    public final String columnName;

    att_employee(int column, String columnName)
    {
        this.column = column;
        this.columnName = columnName;
    }
}
