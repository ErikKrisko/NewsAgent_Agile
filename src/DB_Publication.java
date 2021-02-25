import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Publication {
    //  Base Publication attributes
    private long prod_id = 0;
    private String prod_name, prod_type, frequency;
    private Double prod_price;
    //  External customer attributes
    private DB_Frequency Frequency;

    /** Blank constructor */
    public DB_Publication() { }

    public DB_Publication(String prod_name, String type, Double prod_price) throws DB_PublicationExceptionHandler {
        this.prod_name = vEntry( Att_Publicaton.prod_name, prod_name);
        this.prod_type = vEntry( Att_Publicaton.prod_type, prod_type);
        this.prod_price = vEntry( Att_Publicaton.prod_price, prod_price);
        this.frequency = vEntry( Att_Publicaton.frequency, frequency);
    }

    public DB_Publication(ResultSet rs) throws DB_PublicationExceptionHandler {
        try {
            prod_id = rs.getInt( Att_Publicaton.prod_id.column);
            prod_name = rs.getString( Att_Publicaton.prod_name.column);
            prod_type = rs.getString( Att_Publicaton.prod_type.column);
            prod_price = rs.getDouble( Att_Publicaton.prod_price.column);
            frequency = rs.getString( Att_Publicaton.frequency.column);
        }
        catch (SQLException e) {
            throw new DB_PublicationExceptionHandler(e.getMessage());
        }
    }

    //  Validate attributes
    private String vEntry(Att_Publicaton type, String entry) throws DB_CustomerExceptionHandler {
        switch (type) {
            case prod_name -> {
                if (entry.length() > 0 && entry.length() <= 25)
                    return entry;
                else
                    throw new DB_CustomerExceptionHandler("Invalid prod_name.");
            }
            case prod_type -> {
                if (entry.length() > 0 && entry.length() <= 25)
                    return entry;
                else
                    throw new DB_CustomerExceptionHandler("Invalid prod_type.");
            }
            case prod_price -> {
                if (entry.length() > 2 && entry.length() <= 4)
                    return entry;
                else
                    throw new DB_CustomerExceptionHandler("Invalid prod_price.");
            }
            case frequency -> {
                if (entry.length() == 10)
                    return entry;
                else
                    throw new DB_CustomerExceptionHandler("Invalid frequency.");
            }
            default -> throw new DB_CustomerExceptionHandler("Internal error. Unhandled attribute.");
        }
    }

    /** Returns specified Att_Publicaton attribute as a String
     * @param attribute attribute type to be returned
     * @return an attribute value
     * @throws DB_CustomerExceptionHandler
     */
    public String get(Att_Publicaton attribute) throws DB_CustomerExceptionHandler {
        switch (attribute) {
            case prod_id -> { return "" + prod_id; }
            case prod_name -> { return prod_name; }
            case prod_type -> { return prod_type; }
            case prod_price -> {return prod_price; }
            // case frequency -> { return "" + frequency.getFreq_id(); } //
            default -> { throw new DB_CustomerExceptionHandler("Attribute error"); }
        }
    }

    //  AUTO GENERATED toString
    @Override
    public String toString() {
        return "DB_Publication{" +
                "prod_id=" + prod_id +
                ", prod_name='" + prod_name + '\'' +
                ", prod_type='" + prod_type + '\'' +
                ", prod_price='" + prod_price + '\'' +
                ", frequency=" + frequency.toString() +
                '}';
    }
    //  AUTO GENERATED getters and setters
    public long getprod_id() {   return prod_id; }
    public String getprod_name() { return prod_name; }
    public String getprod_type() {  return prod_type; }
    public Double getprod_price() {   return prod_price; }
    public String getfrequency() {    return frequency; }
    public void setprod_id(long prod_id) { this.prod_id = prod_id; }
    public void setprod_name(String prod_name) throws DB_CustomerExceptionHandler {  this.prod_name = vEntry( Att_Publicaton.prod_name, prod_name); }
    public void setprod_type(String prod_type) throws DB_CustomerExceptionHandler {    this.prod_type = vEntry( Att_Publicaton.prod_type, prod_type); }
    public void setprod_price(String prod_price) throws DB_CustomerExceptionHandler {  this.prod_price = vEntry( Att_Publicaton.prod_price, prod_price); }
}



/** Search object for customer WIP! */
class Search_Publication {
    private Att_Publicaton attribute;
    private String term;
    private boolean strong;

    public Search_Publication(Att_Publicaton attribute, String term, boolean strong) {
        this.attribute = attribute;
        this.term = term;
        this.strong = strong;
    }

    //  AUTO GENERATED getters and setters
    public Att_Publicaton getAttribute() { return attribute; }
    public void setAttribute(Att_Publicaton attribute) { this.attribute = attribute; }
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    public boolean isStrong() { return strong; }
    public void setStrong(boolean strong) { this.strong = strong; }
}


class DB_PublicationExceptionHandler extends Exception {
    String message;

    public DB_PublicationExceptionHandler(String errMessage){  message = errMessage; }

    public String getMessage() {    return message; }
}
/** List of customer attributes */
enum Att_Publicaton {
    //  Customer table attributes
    prod_id(1, "prod_id"),
    prod_name(2, "prod_name"),
    prod_type(3, "prod_type"),
    prod_price(4, "prod_price"),
    frequency(5, "frequency");

    //  Column in which the given attribute appears by default
    public final int column;
    //  prod_name of column in which te attribute appears by default
    public final String column_name;

    Att_Publicaton(int column, String column_name) {
        this.column = column;
        this.column_name = column_name;
    }
}
