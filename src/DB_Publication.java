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
     * Blank constructor
     */
    public DB_Publication() {
    }

    public DB_Publication(long prod_id, String prod_name, String type, Double prod_price) throws DB_PublicationExceptionHandler {
        this.prod_id = validateID(prod_id);
        this.prod_name = validateEntry(Att_Publication.prod_name, prod_name);
        this.prod_type = validateEntry(Att_Publication.prod_type, prod_type);
        this.prod_price = validateEntry(Att_Publication.prod_price, prod_price);
        //this.frequency = validateEntry(Att_Frequency.frequency, frequency);
    }

    public String validateEntry(Att_Customer type, String entry) throws DB_PublicationException {
        if (!entry.isBlank() || !entry.isEmpty()) {
            switch (type) {
                case prod_name -> {
                    if (entry.length() <= 20)
                        if (!entry.matches(".*\\d.*"))
                            return entry;

                        else
                            throw new DB_PublicationExceptionHandler("Entry = \"" + entry + "\", cannot contain letters.");
                    else
                        throw new DB_PublicationExceptionHandler("Entry = \"" + entry + "\", has to be of length 10.");
                }
                default -> throw new DB_PublicationExceptionHandler("Internal error. Unhandled attribute.");
            }
        } else {
            throw new DB_PublicationExceptionHandler("Entry = \"" + entry + "\", cannot be an empty String.");
        }
    }

    public long validateID(long id) throws DB_PublicationExceptionHandler {
        if (id >= 0)
            return id;
        else
            throw new DB_PublicationExceptionHandler("ID can be 0 or more than");
    }

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
//    prod_id(1, "prod_id"),
//    prod_name(2, "prod_name"),
//    prod_type(3, "prod_type"),
//    prod_price(4, "prod_price"),
//    frequency(5, "frequency")
}

class DB_PublicationExceptionHandler extends Exception {
    String message;
    public DB_PublicationExceptionHandler(String errMessage) { message = errMessage;}
    public String getMessage() { return message;}
}