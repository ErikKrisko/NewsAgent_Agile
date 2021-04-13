import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class newsagent_interface {
    //  Static scanner
    static Scanner sc = new Scanner(System.in);
    //  Global dao object
    static DAO dao;
    //  Global customer object
    static DB_Customer customer = null;
    static DB_Delivery delivery = null;
    static DB_Invoice invoice = null;
    static DB_Employee employee = null;
    static DB_Publication publication = null;
    static DB_Subscription subscription = null;

    public static void main(String[] args){
        try {
            //  Set the foundation
            JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
            connection.executeScript("NewsAgent_Database.sql");
            connection.setDbName("newsagent");
            connection.executeScript("NewsAgent_Data.sql");
            connection.close();

            //  Initialize dao connection
            dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");

            //  Main menu controller values
            int menuChoice = 0;
            final int MENU_EXIT = 8;

            //  Main menu loop
            while (menuChoice != MENU_EXIT) {
                printMenu(0);
                if ( sc.hasNextInt()) {
                    menuChoice = sc.nextInt();
                    switch (menuChoice) {
                        //  If customer menu selected
                        case 1 -> customerMenu();

                        //  Invoice menu selected
                        case 3 -> invoiceMenu();

                        //  Delivery menu selected
                        case 4 -> deliveryMenu();

                        //Subscription menu selected
                        case 5 -> subscriptionMenu();

                        //  Employee menu selected
                        case 6 -> employeeMenu();

                        //Publication Menu Selected
                        case 7 -> publicationMenu();

                        //  Close
                        case 8 -> System.out.println("Exiting...");
                        //  Error for unused menus / inputs
                        default -> System.out.println("Invalid Choice.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void customerMenu() {
        try {
            //  Customer Menu controller values
            int menuChoice = 0;
            final int CUS_EXIT = 8;
            //  Customer menu loop
            while (menuChoice != CUS_EXIT) {
                //  Display menu
                printMenu(1);
                if ( sc.hasNextInt()) {
                    menuChoice = sc.nextInt();
                    switch (menuChoice) {
                        //  If create new customer
                        case 1 -> {
                            customer = new DB_Customer();
                            System.out.print("Enter customer first name: ");
                            customer.setFirst_name(sc.next());
                            System.out.print("Enter customer last name: ");
                            customer.setLast_name(sc.next());
                            System.out.print("Enter customer phone no: ");
                            customer.setPhone_no(sc.next());
                            System.out.print("Enter address id: ");
                            customer.setAddress(dao.getAddress(sc.nextInt()));
                            System.out.println(customer);
                        }
                        //  If load customer
                        case 2 -> {
                            System.out.println("Enter customer ID: ");
                            int cusID = sc.nextInt();
                            customer = dao.getCustomer(cusID);
                            System.out.println(customer);
                        }
                        //  Find by search
                        case 3 -> {
                            Search_Customer[] searchElements = new Search_Customer[3];
                            //  First name search
                            System.out.print("Enter first name for search: ");
                            searchElements[0] = new Search_Customer( Att_Customer.first_name, sc.next(), false);
                            //  Last name search
                            System.out.print("Enter last name for search: ");
                            searchElements[1] = new Search_Customer( Att_Customer.last_name, sc.next(), false);
                            //  Phone no
                            System.out.print("Enter phone no for search: ");
                            searchElements[2] = new Search_Customer( Att_Customer.phone_no, sc.next(), false);
                            ArrayList<DB_Customer> cList = dao.getCustomers(searchElements);
                            for (DB_Customer cus : cList) {
                                System.out.println(cus);
                            }
                        }
                        //  Edit customer
                        case 4 -> {
                            if (customer == null) {
                                System.out.println("Need to load customer first.");
                            } else {
                                System.out.println("Editing: " + customer);
                                System.out.print("First name: " + customer.getFirst_name() + " -> ");
                                if ( sc.hasNextLine() && sc.hasNext()) {
                                    customer.setFirst_name( sc.next());
                                }
                                System.out.print("Last name: " + customer.getLast_name() + " -> ");
                                if ( sc.hasNextLine() && sc.hasNext()) {
                                    customer.setLast_name( sc.next());
                                }
                                System.out.print("Phone no: " + customer.getPhone_no() + " -> ");
                                if ( sc.hasNextLine() && sc.hasNext()) {
                                    customer.setPhone_no( sc.next());
                                }
                                System.out.print("Address_id: " + customer.getAddress().getAddress_id() + " -> ");
                                if ( sc.hasNextInt()) {
                                    customer.setAddress( dao.getAddress( sc.nextInt()));
                                }
                                System.out.println("Changed customer: " + customer);
                            }
                        }
                        //  Update customer
                        case  5 -> {
                            if (customer == null) {
                                System.out.println("Need to load customer first.");
                            } else {
                                System.out.println("Updating customer: " + customer);
                                boolean idChange = customer.getCustomer_id() == 0;
                                dao.updateCustomer(customer);
                                if (idChange)
                                    System.out.println("Id changed: " + customer);
                            }
                        }
                        //  View
                        case 6 -> {
                            if (customer == null) {
                                System.out.println("Need to load customer first.");
                            } else {
                                System.out.println(customer);
                            }
                        }
                        case 7 -> {
                            if (customer == null)
                                System.out.println("Need to load customer first.");
                            else if (customer.getCustomer_id() == 0)
                                System.out.println("Customer must be loaded from database.");
                            else {
                                System.out.println("Deleting: " + customer);
                                dao.deleteCustomer(customer);
                                customer = null;
                            }
                        }
                        //  exit
                        case 8 -> {
                            System.out.println("Returning to Main Menu...");
                            customer = null;
                        }
                        default -> System.out.println("Invalid Choice.");
                    }
                }
            }
            customer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deliveryMenu()
    {
        try {
            //Controller values
            int menuChoice = 0;
            final int menuExit = 7;

            while(menuChoice != menuExit) {
                printMenu(4); //Display Menu
                if(sc.hasNextInt()) {
                    menuChoice = sc.nextInt();
                    switch (menuChoice){
                        //Create new delivery
                        case 1 -> {
                            delivery = new DB_Delivery();
                            System.out.println("Enter delivery date (yyyy-mm-dd): ");
                            delivery.setDelivery_date(Date.valueOf(sc.next()));
                            System.out.println("Enter delivery status (true or false): ");
                            delivery.setDelivery_status(Boolean.valueOf(sc.next()));
                            System.out.println("Enter the customer id: ");
                            delivery.setCustomer_id(sc.nextLong());
                            System.out.println("Enter the invoice id: ");
                            delivery.setInvoice_id(sc.nextLong());
                            System.out.println("Enter the publication id: ");
                            delivery.setProd_id(sc.nextLong());
                            System.out.println(delivery);
                        }
                        //Load existing delivery
                        case 2 -> {
                            System.out.println("Enter delivery id: ");
                            int devID = sc.nextInt();
                            delivery = dao.getDelivery(devID);
                            System.out.println(delivery);
                        }
                        //Edit Delivery
                        case 3 -> {
                            if(delivery == null){
                                System.out.println("Need to load delivery first.");
                            }else{
                                System.out.println("Editing: " + delivery);
                                System.out.println("Delivery date(yyyy-mm-dd): " + delivery.getDelivery_date() + " -> ");
                                if(sc.hasNextLine() && sc.hasNext()){
                                    delivery.setDelivery_date(Date.valueOf(sc.next()));
                                }
                                System.out.println("Delivery status(true or false): " + delivery.getDelivery_status() + " -> ");
                                if(sc.hasNextLine() && sc.hasNext()){
                                    delivery.setDelivery_status(Boolean.valueOf(sc.next()));
                                }
                                System.out.println("Customer id: " + delivery.getCustomer_id());
                                if(sc.hasNextLine() && sc.hasNext()){
                                    delivery.setCustomer_id(sc.nextLong());
                                }
                                System.out.println("Invoice id: " + delivery.getInvoice_id());
                                if(sc.hasNextLine() && sc.hasNext()){
                                    delivery.setInvoice_id(sc.nextInt());
                                }
                                System.out.println("Publication id: " + delivery.getProd_id());
                                if(sc.hasNextLine() && sc.hasNext()){
                                    delivery.setProd_id(sc.nextInt());
                                }
                                System.out.println("Changed delivery: " + delivery);
                            }
                        }
                        //Insert/Update Delivery
                        case 4 -> {
                            if(delivery == null){
                                System.out.println("Need to load or create delivery first.");
                            }else{
                                System.out.println("Updating delivery: " + delivery);
                                boolean idChange = delivery.getDelivery_id() == 0;
                                dao.updateDelivery(delivery);
                                if(idChange){
                                    System.out.println("Id changed: " + delivery);
                                }
                            }
                        }
                        //View selected delivery
                        case 5 -> {

                            if(delivery == null){
                                System.out.println("Need to load or create delivery first.");
                            }else{
                                System.out.println(delivery);
                            }
                        }
                        //Delete delivery
                        case 6 -> {
                            if(delivery == null){
                                System.out.println("Need to load delivery first.");
                            }
                            else if(delivery.getDelivery_id() == 0){
                                System.out.println("Delivery must be loaded from database.");
                            }
                            else{
                                System.out.println("Deleting: " + delivery);
                                dao.deleteDelivery(delivery);
                                delivery = null;
                            }
                        }
                        //Exit
                        case 7 -> {
                            System.out.println("Returning to Main Menu.....");
                            delivery = null;
                        }
                        default -> System.out.println("Invalid Choice.");
                    }
                }
            }
            delivery = null;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void invoiceMenu()
    {
        try {
            //Controller values
            int menuChoice = 0;
            final int menuExit = 7;

            while(menuChoice != menuExit) {
                printMenu(3); //Display Menu
                if(sc.hasNextInt()) {
                    menuChoice = sc.nextInt();
                    switch (menuChoice){
                        //Create new Invoice
                        case 1 -> {
                            invoice = new DB_Invoice();
                            System.out.println("Enter invoice date (yyyy-mm-dd): ");
                            invoice.setIssue_date(Date.valueOf(sc.next()));
                            System.out.println("Enter invoice status (true or false): ");
                            invoice.setInvoice_status(Boolean.valueOf(sc.next()));
                            System.out.println("Enter invoice total (??.?): ");
                            invoice.setInvoice_total(sc.nextDouble());
                            System.out.println("Enter a customer id: ");
                            invoice.setCustomer(dao.getCustomer(sc.nextInt()));
                            System.out.println(invoice);
                        }
                        //Load existing Invoice
                        case 2 -> {
                            System.out.println("Enter invoice id: ");
                            int invoice_id = sc.nextInt();
                            invoice = dao.getInvoice(invoice_id);
                            System.out.println(invoice);
                        }
                        //Edit Invoice
                        case 3 -> {
                            if(invoice == null){
                                System.out.println("Need to load invoice first.");
                            }else{
                                System.out.println("Editing: " + invoice);
                                System.out.println("invoice date(yyyy-mm-dd): " + invoice.getIssue_date() + " -> ");
                                if(sc.hasNextLine() && sc.hasNext()){
                                    invoice.setIssue_date(Date.valueOf(sc.next()));
                                }
                                System.out.println("Invoice status(true or false): " + invoice.getInvoice_status() + " -> ");
                                if(sc.hasNextLine() && sc.hasNext()){
                                    invoice.setInvoice_status(Boolean.valueOf(sc.next()));
                                }
                                System.out.println("Invoice total: " + invoice.getInvoice_total() + " -> ");
                                if(sc.hasNextLine() && sc.hasNext()) {
                                    invoice.setInvoice_total(Double.valueOf(sc.next()));
                                }
                                System.out.println("Customer id: " + invoice.getCustomer().getCustomer_id());
                                if(sc.hasNextLine() && sc.hasNext()){
                                    invoice.setCustomer(dao.getCustomer(sc.nextInt()));
                                }
                                System.out.println("Changed invoice: " + invoice);
                            }
                        }
                        //Insert/Update Invoice
                        case 4 -> {
                            if(invoice == null){
                                System.out.println("Need to load or create invoice first.");
                            }else{
                                System.out.println("Updating invoice: " + invoice);
                                boolean idChange = invoice.getInvoice_id() == 0;
                                dao.updateInvoice(invoice);
                                if(idChange){
                                    System.out.println("Id changed: " + invoice);
                                }
                            }
                        }
                        //View selected invoice
                        case 5 -> {

                            if(invoice == null){
                                System.out.println("Need to load or create invoice first.");
                            }else{
                                System.out.println(invoice);
                            }
                        }
                        //Delete invoice
                        case 6 -> {
                            if(invoice == null){
                                System.out.println("Need to load invoice first.");
                            }
                            else if(invoice.getInvoice_id() == 0){
                                System.out.println("Invoice must be loaded from database.");
                            }
                            else{
                                System.out.println("Deleting: " + invoice);
                                dao.deleteInvoice(invoice);
                                invoice = null;
                            }
                        }
                        //Exit
                        case 7 -> {
                            System.out.println("Returning to Main Menu.....");
                            invoice = null;
                        }
                        default -> System.out.println("Invalid Choice.");
                    }
                }
            }
            invoice = null;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void employeeMenu()
    {
        try {
            //Controller values
            int menuChoice = 0;
            final int menuExit = 7;

            while(menuChoice != menuExit) {
                printMenu(4); //Display Menu
                if(sc.hasNextInt()) {
                    menuChoice = sc.nextInt();
                    switch (menuChoice){
                        //Create new Employee
                        case 1 -> {
                            employee = new DB_Employee();
                            System.out.println("Enter Employee first name: ");
                            employee.setFirst_name(sc.next());
                            System.out.println("Enter Employee last name: ");
                            employee.setLast_name(sc.next());

                            System.out.println(employee);
                        }
                        //Load existing Employee
                        case 2 -> {
                            System.out.println("Enter Employee id: ");
                            int devID = sc.nextInt();
                            employee = dao.getEmployee(devID);
                            System.out.println(employee);
                        }
                        //Edit Employee
                        case 3 -> {
                            if(employee == null){
                                System.out.println("Need to load Employee first.");
                            }else{
                                System.out.println("Editing: " + employee);
                                System.out.println("Employee First Name: " + employee.getFirst_name() + " -> ");
                                if(sc.hasNextLine() && sc.hasNext()){
                                    employee.setFirst_name(sc.next());
                                }
                                System.out.println("Employee Last Name: " + employee.getLast_name() + " -> ");
                                if(sc.hasNextLine() && sc.hasNext()){
                                    employee.setLast_name(sc.next());
                                }
                                System.out.println("Changed Employee: " + employee);
                            }
                        }
                        //Insert/Update Employee
                        case 4 -> {
                            if(employee == null){
                                System.out.println("Need to load or create Employee first.");
                            }else{
                                System.out.println("Updating Employee: " + employee);
                                boolean idChange = employee.getEmployee_id() == 0;
                                dao.updateEmployee(employee);
                                if(idChange){
                                    System.out.println("Id changed: " + employee);
                                }
                            }
                        }
                        //View selected Employee
                        case 5 -> {

                            if(employee == null){
                                System.out.println("Need to load or create Employee first.");
                            }else{
                                System.out.println(employee);
                            }
                        }
                        //Delete Employee
                        case 6 -> {
                            if(employee == null){
                                System.out.println("Need to load Employee first.");
                            }
                            else if(employee.getEmployee_id() == 0){
                                System.out.println("Employee must be loaded from database.");
                            }
                            else{
                                System.out.println("Deleting: " + employee);
                                dao.deleteEmployee(employee);
                                employee = null;
                            }
                        }
                        //Exit
                        case 7 -> {
                            System.out.println("Returning to Main Menu.....");
                            employee = null;
                        }
                        default -> System.out.println("Invalid Choice.");
                    }
                }
            }
            employee = null;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void subscriptionMenu() {
        try {
            //Controller values
            int menuChoice = 0;
            final int menuExit = 7;

            while (menuChoice != menuExit) {
                printMenu(5);
                if (sc.hasNextInt()) {
                    menuChoice = sc.nextInt();
                    switch (menuChoice) {
                        //Create new subscription
                        case 1 -> {
                            subscription = new DB_Subscription();
                            System.out.println("Enter customer_id: ");
                            subscription.setCustomer_id(sc.nextLong());
                            System.out.println("Enter publication_id: ");
                            subscription.setPublication_id(sc.nextLong());
//                            System.out.println("Enter the customer id: ");
//                            delivery.setCustomer_id((sc.nextLong()));
//                            System.out.println("Enter the invoice id: ");
//                            delivery.setInvoice_id((sc.nextLong()));
                            System.out.println(subscription);
                        }

                        //Load existing subscription
                        case 2 -> {
                            System.out.println("Enter customer_id: ");
                            long customer_id = sc.nextInt();
                            System.out.println("Enter publication_id: ");
                            long publication_id = sc.nextInt();
                            subscription = dao.getSubscription(customer_id, publication_id);
                            System.out.println(delivery);
                        }
                        //Edit subscription
                        case 3 -> {
                            if (subscription == null) {
                                System.out.println("Must load subscription first.");
                            } else {
                                System.out.println("Edit: " + subscription);
                                System.out.println("Customer id: " + subscription.getCustomer_id());
                                if (sc.hasNextLine() && sc.hasNext()) {
                                    subscription.setCustomer_id(sc.nextLong());
                                }
                                System.out.println("Publication id:" + subscription.getPublication_id());
                                if (sc.hasNextLine() && sc.hasNext()) {
                                    subscription.setPublication_id(sc.nextLong());
                                }
                                System.out.println("Count: " + subscription.getCount());
                                if (sc.hasNextLine() && sc.hasNext()) {
                                    subscription.setCount(sc.nextInt());
                                }
                            }
                        }
                        //Inserting/Updating subscription
                        case 4 -> {
                            if (subscription == null) {
                                System.out.println("Must load or create a subscription.");
                            } else {
                                System.out.println("Update subscription: " + subscription);
                                boolean idChange = subscription.getCustomer_id() == 0;
                                dao.updateSubscription(subscription);
                                if (idChange) {
                                    System.out.println("ID has been changed: " + subscription);
                                }
                            }
                        }

                        //View a selected subscription
                        case 5 -> {
                            if (subscription == null) {
                                System.out.println("Must load or create a subscription first.");
                            } else {
                                System.out.println(subscription);
                            }
                        }
                        //Deleting subscription
                        case 6 -> {
                            if (subscription == null) {
                                System.out.println("Must load subscription first.");
                            } else if (subscription.getCustomer_id() == 0) {
                                System.out.println("Subscription must be loaded from database.");
                            } else {
                                System.out.println("Deleting: " + subscription);
                                dao.deleteSubscription(subscription);
                                subscription = null;
                            }
                        }

                        //Exit
                        case 7 -> {
                            System.out.println("Return to Main Menu.");
                            subscription = null;
                        }
                        default -> System.out.println("Invalid Choice.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public static void publicationMenu()
    {
        try {
            //Controller values
            int menuChoice = 0;
            final int menuExit = 7;

            while(menuChoice != menuExit) {
                printMenu(7); // display menu
                if (sc.hasNextInt()) {
                    menuChoice = sc.nextInt();
                    switch (menuChoice){
                        //create new publication
                        case 1 -> {
                            publication = new DB_Publication();
                            System.out.println("Enter Product Name: ");
                            publication.setProd_name(sc.nextLine());
                            System.out.println("Enter Product Type: (Broadsheet,Tabloid & Magazine)");
                            publication.setProd_type(sc.next());
                            System.out.println("Enter Product Price: ");
                            publication.setProd_price(sc.nextDouble());
                            System.out.println("Enter Product Frequency: 'DAILY' or 'WEEKLY/MONTHLY 1-7/1-28'");
                            publication.setFrequency(sc.nextLine());
                            System.out.println(publication);
                        }
                        //load existing Publication
                        case 2-> {
                            System.out.println("Enter Product ID: ");
                            int prodID = sc.nextInt();
                            publication = dao.getPublication(prodID);
                            System.out.println(publication);
                        }
                        //Edit publication
                        case 3 -> {
                            if(publication == null){
                                System.out.println("Need to load existing publication first.");
                            }else{
                                System.out.println("Editing: " + publication);
                                System.out.println("Product Name: " + publication.getProd_name() + " -> ");
                                if (sc.hasNextLine() && sc.hasNext()) {
                                    publication.setProd_name(sc.nextLine());
                                }
                                System.out.println("Product Type: (Broadsheet,Tabloid & Magazine)" + publication.getProd_type() + " -> ");
                                if (sc.hasNextLine() && sc.hasNext()) {
                                    publication.setProd_type(sc.next());
                                }
                                System.out.println("Product Price: " + publication.getProd_price() + " -> ");
                                if (sc.hasNextLine() && sc.hasNext()) {
                                    publication.setProd_price(sc.nextDouble());
                                }
                                System.out.println("Product Frequency ('DAILY' or 'WEEKLY/MONTHLY 1-7/1-28'): " + publication.getFrequency() + " -> ");
                                if(sc.hasNextLine() && sc.hasNext()){
                                    publication.setFrequency(sc.nextLine());
                                }
                                System.out.println("Changed Publication: " + publication);
                        }
                    }
                        //Update Publication
                        case 4 -> {
                            if (publication == null){
                                System.out.println("need to load or create publication first.");
                            }else{
                                System.out.println("updating publication: " + publication);
                                boolean idChange = publication.getProd_id() == 0;
                                dao.updatePublication(publication);
                                if(idChange){
                                    System.out.println("ID Changed: " + publication);
                                }
                            }
                        }
                        //view selected Publication
                        case 5 -> {

                            if(publication == null){
                                System.out.println("Need to load or create publication first.");
                            }else{
                                System.out.println(publication);
                            }
                        }
                        //Delete Publication
                        case 6 -> {
                            if (publication == null){
                                System.out.println("Need to load Publication first.");
                            }
                            else if (publication.getProd_id() == 0){
                                System.out.println("Publication must be loaded from database.");
                            }
                            else{
                                System.out.println("Deleting: " + publication);
                                dao.deletePublication(publication);
                                publication = null;
                            }
                        }
                        //Exit
                        case 7 -> {
                            System.out.println("Returning to main menu.....");
                            publication = null;
                        }
                        default -> System.out.println("invalid Choice");
                    }
                }
            }
            publication = null;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printMenu(int menu) {
        switch (menu) {
            //  Main Menu
            case 0 -> {
                System.out.println("\n=====Main Menu=====");
                System.out.println("1. Customer");
                System.out.println("2. Address (unimplemented)");
                System.out.println("3. Invoice");
                System.out.println("4. Delivery");
                System.out.println("5. Subscription");
                System.out.println("6. Employees ");
                System.out.println("7. Publication ");
                System.out.println("8. Exit");
            }
            //  Customer menu
            case 1 -> {
                System.out.println("\n=====Customer Menu=====");
                System.out.println("1. Create new");
                System.out.println("2. Load ID");
                System.out.println("3. Find by attributes (view only)");
                System.out.println("4. Edit");
                System.out.println("5. Update database");
                System.out.println("6. Check");
                System.out.println("7. Delete");
                System.out.println("8. To Main Menu");
            }
            //  Invoice menu
            case 3 -> {
                System.out.println("\n=====Invoice Menu=====");
                System.out.println("1. Create new invoice");
                System.out.println("2. Load existing ID");
                System.out.println("3. Edit");
                System.out.println("4. Update database");
                System.out.println("5. Check");
                System.out.println("6. Delete");
                System.out.println("7. To Main Menu");
            }
            //  Delivery menu
            case 4 -> {
                System.out.println("\n=====Delivery Menu=====");
                System.out.println("1. Create new delivery");
                System.out.println("2. Load existing ID");
                System.out.println("3. Edit");
                System.out.println("4. Update database");
                System.out.println("5. Check");
                System.out.println("6. Delete");
                System.out.println("7. To Main Menu");
            }

            //  Subscription menu
            case 5 -> {
                System.out.println("\n=====Subscription Menu=====");
                System.out.println("1. Create new subscription");
                System.out.println("2. Load existing ID");
                System.out.println("3. Edit");
                System.out.println("4. Update database");
                System.out.println("5. Check");
                System.out.println("6. Delete");
                System.out.println("7. To Main Menu");
            }

            //  Employee menu
            case 6 -> {
                System.out.println("\n=====Employee Menu=====");
                System.out.println("1. Create new employee");
                System.out.println("2. Load existing ID");
                System.out.println("3. Edit");
                System.out.println("4. Update database");
                System.out.println("5. Check");
                System.out.println("6. Delete");
                System.out.println("7. To Main Menu");
            }

            // Publication Menu
            case 7 -> {
                System.out.println("\n=====Publication Menu=====");
                System.out.println("1. Create new Publication");
                System.out.println("2. Load existing ID");
                System.out.println("3. Edit");
                System.out.println("4. Update database");
                System.out.println("5. Check");
                System.out.println("6. Delete");
                System.out.println("7. To Main Menu");
            }


            default -> {
                System.out.println("Invalid menu");
            }
        }
    }
}
