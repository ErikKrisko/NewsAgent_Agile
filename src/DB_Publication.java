import javax.swing.*;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
    public DB_Publication(long prod_id, String prod_name, String prod_type, Double prod_price, String frequency/*, int day*/) throws DB_PublicationExceptionHandler {
        this.prod_id = validateID(prod_id);
        this.prod_name = validateName( prod_name);
        this.prod_type = validateType(prod_type);
        this.prod_price = validatePrice(prod_price);
        //  CHANGES
        this.frequency = frequency;
        /*this.frequency = validateFrequency(frequency, day);*/
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
            throw new DB_PublicationExceptionHandler("Name = " + name + ", cannot be empty.");
        }
    }

    //Test for two type anything else throw an error
    public String validateType(String type) throws DB_PublicationExceptionHandler {
        if(type.equals("Tabloid"))
            return type;
        else if (type.equals("Broadsheet"))
            return type;
        else if(type.equals("Magazine"))
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
        String [] freq = frequency.split(" ");

        if(freq[0].equals("DAILY")){
            this.frequency = freq[0];
        }
        else if(freq[0].equals("WEEKLY")){
            if(Integer.parseInt(freq[1]) > 0 && Integer.parseInt(freq[1]) < 8){
                this.frequency = frequency;
            }else{
                throw new DB_PublicationExceptionHandler("Weekly frequency has to have a day of 1-7(MON-SUN)");
            }
        }
        else if(freq[0].equals("MONTHLY")){
            if(Integer.parseInt(freq[1]) > 0 && Integer.parseInt(freq[1]) < 29){
                this.frequency = frequency;
            }else{
                throw new DB_PublicationExceptionHandler("Monthly frequency has to have a day of 1-28");
            }
        }
        else{
            throw new DB_PublicationExceptionHandler("Frequency has to be 'DAILY/WEEKLY/MONTHLY DAY(if WEEKLY/MONTHLY)'");
        }
    }
}

class DB_PublicationExceptionHandler extends Exception {
    String message;
    public DB_PublicationExceptionHandler(String errMessage) { message = errMessage;}
    public String getMessage() { return message;}
}