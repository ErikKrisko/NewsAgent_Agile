public class DB_Invoice {
    private int invoice_id;
    private String issue_date,invoice_status,invoice_total;
    private DB_Customer customer;
    public DB_Invoice(){

    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(String invoice_status) {
        this.invoice_status = invoice_status;
    }

    public String getInvoice_total() {
        return invoice_total;
    }

    public void setInvoice_total(String invoice_total) {
        this.invoice_total = invoice_total;
    }

    public DB_Customer getCustomer() {
        return customer;
    }

    public void setCustomer(DB_Customer customer) {
        this.customer = customer;
    }
}

