import java.util.ArrayList;

public class Docket {
    private DB_Employee employee;
    private ArrayList<DB_Delivery> deliveries;
    public Docket() {

    }
}

class DocketExceptionHandler extends Exception {
    String message;

    public DocketExceptionHandler(String errMessage) { message = errMessage; }

    public String getMessage() { return message; }
}
