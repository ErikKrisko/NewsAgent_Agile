import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Publication {
    //  Base Publication attributes
    //Changed prod_id from equal to zero to just prod_id;
    private long prod_id;
    private String prod_name, prod_type, frequency;
    private Double prod_price;
    //  External customer attributes
    //Leave line 11 commented out for now until DB_Frequency is done. Donny
    //private DB_Frequency Frequency[];
    //Maybe have frequency as an array?

    /**
     * Blank constructor for DB_Publication
     */
    public DB_Publication() {
    }

    /** Creates DB_Publication object with provided specifications
     * All variables are validated
     * @param prod_id
     * @param prod_name
     * @param type
     * @param prod_price
     * @throws DB_PublicationExceptionHandler
     */
    public DB_Publication(long prod_id, String prod_name, String type, Double prod_price) throws DB_PublicationExceptionHandler {
        this.prod_id = validateID(prod_id);
        this.prod_name = validateEntry(Att_Publication.prod_name, prod_name);
        this.prod_type = validateEntry(Att_Publication.prod_type, prod_type);
        this.prod_price = validateEntry(Att_Publication.prod_price, prod_price);
        //this.frequency = validateEntry(Att_Frequency.frequency, frequency);
    }

    public long validateID(long id) throws DB_PublicationExceptionHandler {
        if (id >= 0)
            return id;
        else
            throw new DB_PublicationExceptionHandler("ID can be 0 or more than");
    }

    public String validateEntry(String name) throws DB_PublicationExceptionHandler{
        if(name)
            return name;
        else
            throw new DB_PublicationExceptionHandler("Name must be zero or greater");
    }

    public String ValidateEntry(String type) throws DB_PublicationExceptionHandler {

    }

    public Double validateEntry(Double price) throws DB_PublicationExceptionHandler {

    }

    //public String validateEntry(frequency)


    //ToString not auto generated Donny
    @Override
    public String toString() {
        return "DB_Publication{" +
                "prod_id =" + prod_id +
                ", prod_name =" + prod_name + '\'' +
                "prod_type =" + prod_type + '\'' +
                "prod_price =" + prod_price + '\'' +
                //"frequency =" + frequency +
                '}';
    }

    //Getter and Setters not auto generated Donny
    public long getProd_id() {
        return prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public String getProd_type() {
        return prod_type;
    }

    public Double getProd_price() {
        return prod_price;
    }

    //public int getFrequency() { return frequency; }

    public void setProd_id(long prod_id) throws DB_PublicationExceptionHandler {
        this.prod_id = validateID(prod_id);
    }

    public void setProd_name(String prod_name) throws DB_PublicationExceptionHandler {
        this.prod_name = validateEntry(Att_Publication.prod_name, prod_name);
    }

    public void setProd_type(String prod_type) throws DB_PublicationExceptionHandler {
        this.prod_type = validateEntry(Att_Publication.prod_type, prod_type);
    }

    public void setProd_price(Double prod_price) throws DB_PublicationExceptionHandler {
        this.prod_price = validateEntry(Att_Publication.prod_price, prod_price);
    }

}

enum Att_Publication {

}

class DB_PublicationExceptionHandler extends Exception {
    String message;
    public DB_PublicationExceptionHandler(String errMessage) { message = errMessage;}
    public String getMessage() { return message;}
}