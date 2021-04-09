import java.sql.Date;

public class DAOGenerator {
    public Docket GenDocket(Date date) throws DAOGeneratorExceptionHandler {
        throw new DAOGeneratorExceptionHandler("No code written");
    }
    public Docket GenDocketArea(Date date, String area) throws DAOGeneratorExceptionHandler {
        throw new DAOGeneratorExceptionHandler("No code written");
    }
}

class DAOGeneratorExceptionHandler extends Exception {
    String message;

    public DAOGeneratorExceptionHandler(String errMessage) { message = errMessage; }

    public String getMessage() { return message; }
}