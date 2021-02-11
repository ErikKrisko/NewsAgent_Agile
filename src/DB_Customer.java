public class DB_Customer {
    private int customer_id;
    private String first_name, last_name, phone_no;
    private DB_Address address;

    public DB_Customer() {

    }



    //  AUTO GENERATED getters and setters
    public int getCustomer_id() {
        return customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public DB_Address getAddress() {
        return address;
    }

    public void setAddress(DB_Address address) {
        this.address = address;
    }
}
