import javax.swing.*;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Publication {
    //  Base Publication attributes
    private long prod_id;
    private String prod_name, prod_type, frequency;
    private Double prod_price;

    /**
     * Blank constructor for DB_Publication
     */
    public DB_Publication() {
    }

    /** Creates DB_Publication object with provided specifications
     * All variables are validated
     * @param prod_id
     * @param prod_name
     * @param prod_type
     * @param prod_price
     * @throws DB_PublicationExceptionHandler
     */
    public DB_Publication(long prod_id, String prod_name, String prod_type, Double prod_price) throws DB_PublicationExceptionHandler {
        this.prod_id = validateID(prod_id);
        this.prod_name = validateName( prod_name);
        this.prod_type = validateType(prod_type);
        this.prod_price = validatePrice(prod_price);
        this.frequency = validateFrequency(frequency);
    }


    public long validateID(long id) throws DB_PublicationExceptionHandler {
        if (id >= 0)
            return id;
        else
            throw new DB_PublicationExceptionHandler("ID can be 0 or more than");
    }

    //Length of the name not empty string fits into database 20 max
    public String validateName(String name) throws DB_PublicationExceptionHandler{
        if(!name.isEmpty() || !name.isBlank()){
            if(name.length() <= 25){
                return name;
            }
            else{
                throw new DB_PublicationExceptionHandler("Name = " + name + ", is too long.");
            }
        }
        else{
            throw new DB_PublicationExceptionHandler("Name = " + name + ", is too long.");
        }
    }

    //Test for two type anything else throw an error
    public String validateType(String type) throws DB_PublicationExceptionHandler {
    if(type == "Tabloid")
        return type;
        else if (type == "Broadsheet")
            return type;
        else if(type == "Magazine")
            return type;
        else
        throw new DB_PublicationExceptionHandler("Type = " + type + ", must be either Tabloid, Broadsheet, or Magazine");
    }

    //greater than zero less than 10.00
    public Double validatePrice(Double price) throws DB_PublicationExceptionHandler {
        if(price <= 10.00)
            return price;
        else if(price >0)
            return price;
        else
        throw new DB_PublicationExceptionHandler("Price = " + price + ", must be greater than zero and less than ten.");
    }


    public String validateFrequency(String frequency) throws DB_PublicationExceptionHandler{
        if(frequency.length() <=25 && frequency.length() >0){
            return frequency;
        }
        else if(frequency.isEmpty() || frequency.isBlank())
            throw new DB_PublicationExceptionHandler("Frequency = " + frequency + " cannot be empty");
        else{
            throw new DB_PublicationExceptionHandler("Frequency " + frequency + " is too long");
        }
    }



    //ToString not auto generated Donny
    @Override
    public String toString() {
        return "DB_Publication{" +
                "prod_id =" + prod_id +
                ", prod_name =" + prod_name + '\'' +
                "prod_type =" + prod_type + '\'' +
                "prod_price =" + prod_price + '\'' +
                "frequency =" + frequency +  '\'' +
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

    public String getFrequency() {
        return frequency;
    }

    //Auto generated Setters
    public void setProd_id(long prod_id) throws DB_PublicationExceptionHandler {
        this.prod_id = validateID(prod_id);
    }

    public void setProd_name(String prod_name) throws DB_PublicationExceptionHandler {
        this.prod_name = validateName(prod_name);
    }

    public void setProd_type(String prod_type) throws DB_PublicationExceptionHandler {
        this.prod_type = validateType(prod_type);
    }

    public void setProd_price(Double prod_price) throws DB_PublicationExceptionHandler {
        this.prod_price = validatePrice(prod_price);
    }

    public void setFrequency(String frequency) throws DB_PublicationExceptionHandler {
        this.frequency = validateFrequency(frequency);
    }
}

class DB_PublicationExceptionHandler extends Exception {
    String message;
    public DB_PublicationExceptionHandler(String errMessage) { message = errMessage;}
    public String getMessage() { return message;}
}