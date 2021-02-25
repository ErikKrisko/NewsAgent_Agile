import java.sql.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class newsagent_interface {
    //  Static scanner
    static Scanner sc = new Scanner(System.in);
    //  Global dao object
    static DAO dao;
    //  Global customer object
    static DB_Customer customer = null;
    static DB_Delivery delivery = null;

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
            final int MENU_EXIT = 6;

            //  Main menu loop
            while (menuChoice != MENU_EXIT) {
                printMenu(0);
                if ( sc.hasNextInt()) {
                    menuChoice = sc.nextInt();
                    switch (menuChoice) {
                        //  If customer menu selected
                        case 1 -> customerMenu();

                        //  Delivery menu selected
                        case 4 -> deliveryMenu();

                        //  Close
                        case 6 -> System.out.println("Exiting...");
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
                            LinkedList<DB_Customer> cList = dao.getCustomers(searchElements);
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
                            delivery.setCustomer(dao.getCustomer(sc.nextInt()));
                            System.out.println("Enter the invoice id: ");
                            delivery.setInvoice(dao.getInvoice(sc.nextInt()));
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
                                System.out.println("Customer id: " + delivery.getCustomer().getCustomer_id());
                                if(sc.hasNextLine() && sc.hasNext()){
                                    delivery.setCustomer(dao.getCustomer(sc.nextInt()));
                                }
                                System.out.println("Invoice id: " + delivery.getInvoice().getInvoice_id());
                                if(sc.hasNextLine() && sc.hasNext()){
                                    delivery.setInvoice(dao.getInvoice(sc.nextInt()));
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

    public static void printMenu(int menu) {
        switch (menu) {
            //  Main Menu
            case 0 -> {
                System.out.println("\n=====Main Menu=====");
                System.out.println("1. Customer");
                System.out.println("2. Address (unimplemented)");
                System.out.println("3. Invoice (unimplemented)");
                System.out.println("4. Delivery");
                System.out.println("5. Subscription (unimplemented)");
                System.out.println("6. Exit");
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
            default -> {
                System.out.println("Invalid menu");
            }
        }
    }
}
