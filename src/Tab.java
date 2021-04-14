import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Tab {
    private final JTabbedPane pane;
    private final JButton swap_customer = new JButton("Customer"), swap_invoice = new JButton("Invoice"), swap_delivery = new JButton("Delivery"), swap_subscription = new JButton("Subscription"), swap_employee = new JButton("Employee");
    private JPanel component;
    private final DAO dao;
    private JFrame parent;

    public Tab(JTabbedPane pane, DAO dao) {
        this.pane = pane;
        this.dao = dao;
    }

    public JPanel blank(){ return component = new blankTab(); }
    public JPanel customer() { return component = new customerTab(); }
    public JPanel invoice() throws DAOExceptionHandler { return component = new invoiceTab(); }
    public JPanel delivery(){ return component = new deliveryTab(); }
    public JPanel employee(){ return component = new employeeTab(); }


    //  Number checker
    public boolean isInt(String num) {
        Pattern pattern = Pattern.compile("\\d+");
        if (num == null) {
            return false;
        }
        return pattern.matcher(num).matches();
    }

    public JPanel subscription(){
        return component = new subscriptionTab();
    }

    //  Blank Tab
    private class blankTab extends JPanel implements ActionListener{
        private blankTab() {
            setLayout(new FlowLayout());
            //  Change to customer
            swap_customer.addActionListener(this);
            add(swap_customer);
            //  Change to invoice
            swap_invoice.addActionListener(this);
            add(swap_invoice);
            //  Change to invoice
            swap_delivery.addActionListener(this);
            add(swap_delivery);
            swap_subscription.addActionListener(this);
            add(swap_subscription);
            swap_employee.addActionListener(this);
            add(swap_employee);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //  Replace tab with new Tab
            if (e.getSource() == swap_customer) {
                int pos = pane.indexOfComponent(component);
                pane.setComponentAt(pos, new customerTab());
                pane.setTitleAt(pos, "Customer");
            } else if (e.getSource() == swap_invoice) {
                int pos = pane.indexOfComponent(component);
                try {
                    pane.setComponentAt(pos, new invoiceTab());
                } catch (DAOExceptionHandler daoExceptionHandler) {
                    daoExceptionHandler.printStackTrace();
                }
                pane.setTitleAt(pos, "Invoice");
            } else if(e.getSource() == swap_delivery){
                int pos = pane.indexOfComponent(component);
                pane.setComponentAt(pos, new deliveryTab());
                pane.setTitleAt(pos, "Delivery");
            }else if(e.getSource() == swap_subscription) {
                int pos = pane.indexOfComponent(component);
                pane.setComponentAt(pos, new subscriptionTab());
                pane.setTitleAt(pos, "Subscription");
            }
            else if(e.getSource() == swap_employee) {
                int pos = pane.indexOfComponent(component);
                pane.setComponentAt(pos, new employeeTab());
                pane.setTitleAt(pos, "Employee");
            }
        }
    }

    //  ========================================================================================================================
    //  CUSTOMER TAB
    //  ========================================================================================================================
    private class customerTab extends JPanel implements ActionListener {
        private final JButton button_search = new JButton("Search"), button_add = new JButton("+");
        //  Top panel to put search functionality into
        private final JPanel searchPanel = new JPanel();
        //  ScrollPane to be used by JTable
        private final JScrollPane customer_tablePane = new JScrollPane();
        //  JTable and TableModel for it
        private final JTable customer_table = new JTable() {
            //  Disable direct editing of the table will need to implement it separately
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        private DefaultTableModel customer_tableModel;
        //  ArrayList for customers
        private ArrayList<DB_Customer> customers;
        //  Context menu items
        private final JMenuItem menu_edit = new JMenuItem("Edit");
        private int rowSelected;
        //  Search boxes
        private final JTextField search_id = new JTextField(3),
            search_fName = new JTextField(10),
            search_lName = new JTextField(10),
            search_phoneNo = new JTextField(10);

        //  Constructor WIP
        private customerTab() {
            //  Set layout
            setLayout(new BorderLayout());
            //  Add both panes
            add(searchPanel, BorderLayout.NORTH);
            add(customer_tablePane, BorderLayout.CENTER);
            //  Search pane
            buildSearchBox();
            //  Table build
            buildTableModel();
            //  Table listener
            tableListener();

            //  Menu_edit listener
            menu_edit.addActionListener(this);
        }

        private void buildSearchBox() {
            searchPanel.setLayout(new BorderLayout());
            //  Add search button
            searchPanel.add(button_search, BorderLayout.EAST);
            button_search.addActionListener(this);
            searchPanel.add(button_add, BorderLayout.WEST);
            button_add.addActionListener(this);
            //  Add search boxes
            JPanel sList = new JPanel(new FlowLayout());
            sList.add(new JLabel("ID: "));
            sList.add(search_id);
            sList.add(new JLabel("First Name: "));
            sList.add(search_fName);
            sList.add(new JLabel("Last Name: "));
            sList.add(search_lName);
            sList.add(new JLabel("Phone: "));
            sList.add(search_phoneNo);
            searchPanel.add(sList);
        }

        //  Builds the table headers (columns)
        private void buildTableModel() {
            customer_tablePane.getViewport().add(customer_table);
            //  Table model
            customer_tableModel = new DefaultTableModel();
            //  Do the headers
            customer_tableModel.addColumn("ID");
            customer_tableModel.addColumn("First Name");
            customer_tableModel.addColumn("Last Name");
            customer_tableModel.addColumn("Phone no.");
            customer_tableModel.addColumn("Address");
            customer_tableModel.addColumn("EirCode");
            customer_tableModel.addColumn("AreaCode");
            //  set table to use the model
            customer_table.setModel(customer_tableModel);
            //  disable moving columns around
            customer_table.getTableHeader().setReorderingAllowed(false);
            //  Reorder widths
            //  https://www.tutorialspoint.com/how-to-change-each-column-width-of-a-jtable-in-java#:~:text=By%20default%20the%20width%20of,()%20method%20of%20JTable%20class.
            customer_table.getColumnModel().getColumn(0).setPreferredWidth(10);
            customer_table.getColumnModel().getColumn(1).setPreferredWidth(80);
            customer_table.getColumnModel().getColumn(2).setPreferredWidth(80);
            customer_table.getColumnModel().getColumn(3).setPreferredWidth(80);
            customer_table.getColumnModel().getColumn(4).setPreferredWidth(200);
            customer_table.getColumnModel().getColumn(5).setPreferredWidth(50);
            customer_table.getColumnModel().getColumn(6).setPreferredWidth(10);
        }

        private void tableListener() {
            customer_table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        rowSelected = customer_table.rowAtPoint(e.getPoint());
                        customer_table.setRowSelectionInterval(rowSelected, rowSelected);
                        JPopupMenu pop = new JPopupMenu();
                        pop.add(menu_edit);
                        pop.show(customer_table, e.getX(), e.getY());
                    }
                }
            });
        }

        //  Populates data from customers ArrayList
        private void updateTableModel() {
            customer_tableModel.setRowCount(0);
            for (DB_Customer cus : customers) {
                customer_tableModel.addRow(cus.getRowData());
            }
        }

        private Search_Customer[] constructSearch() {
            Search_Customer[] search = new Search_Customer[0];
            if (search_id.getText().length() > 0 && isInt(search_id.getText())){
                search = Arrays.copyOf(search, search.length + 1);
                search[search.length - 1] = new Search_Customer(Att_Customer.customer_id, search_id.getText(), true);
            }
            if (search_fName.getText().length() > 0) {
                search = Arrays.copyOf(search, search.length + 1);
                search[search.length - 1] = new Search_Customer(Att_Customer.first_name, search_fName.getText(), false);
            }
            if (search_lName.getText().length() > 0) {
                search = Arrays.copyOf(search, search.length + 1);
                search[search.length - 1] = new Search_Customer(Att_Customer.last_name, search_lName.getText(), false);
            }
            if (search_phoneNo.getText().length() > 0) {
                search = Arrays.copyOf(search, search.length + 1);
                search[search.length - 1] = new Search_Customer(Att_Customer.phone_no, search_phoneNo.getText(), false);
            }
            return search;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //  If search button is pressed
            if (e.getSource() == button_search) {
                try {
                    //  Get new data (no search criteria for now)
                    customers = dao.getCustomers(constructSearch());
                    //  Update table
                    updateTableModel();
                } catch (DAOExceptionHandler exc) {
                    exc.printStackTrace();
                }
            } else if (e.getSource() == menu_edit) {
                //  Wild magic to get an integer out of a table field
                int id = Integer.parseInt((String) customer_table.getValueAt(rowSelected, 0));
                try {
                    //  Got this here
                    //  https://stackoverflow.com/questions/9650874/java-swing-obtain-window-jframe-from-inside-a-jpanel
                    parent = (JFrame) SwingUtilities.windowForComponent(this);

                    new Editor(dao).customer(dao.getCustomer(id), parent);
                } catch (DAOExceptionHandler exc) {
                    exc.printStackTrace();
                }
            } else if (e.getSource() == button_add) {
                parent = (JFrame) SwingUtilities.windowForComponent(this);
                new Editor(dao).customer(new DB_Customer(), parent);
            }
        }

    }

    //  ========================================================================================================================
    //  DELIVERY TAB
    //  ========================================================================================================================
    private class deliveryTab extends JPanel implements ActionListener{
        private final JButton button_search = new JButton("Search"), button_add = new JButton("+");
        //  Top panel to put search functionality into
        private final JPanel searchPanel = new JPanel();
        //  ScrollPane to be used by JTable
        private final JScrollPane delivery_tablePane = new JScrollPane();
        //  JTable and TableModel for it
        private final JTable delivery_table = new JTable() {
            //  Disable direct editing of the table will need to implement it separately
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        private DefaultTableModel delivery_tableModel;
        //  ArrayList for deliveries
        private ArrayList<DB_Delivery> deliveries;
        //  Context menu items
        private final JMenuItem menu_edit = new JMenuItem("Edit");
        private int rowSelected;
        //Search Box with comboBox attribute selector
        JTextField search_box = new JTextField(10);

        String[] strings = {"All", "ID", "Date", "Status", "Customer ID", "Invoice ID", "Prod ID"};
        JComboBox search_combobox = new JComboBox(strings);

        //  Constructor WIP
        private deliveryTab() {
            //  Set layout
            setLayout(new BorderLayout());
            //  Add both panes
            add(searchPanel, BorderLayout.NORTH);
            add(delivery_tablePane, BorderLayout.CENTER);
            //  Search pane
            buildSearchBox();
            //  Table pane
            delivery_tablePane.getViewport().add(delivery_table);
            buildTableModel();
            //  Table listener
            tableListener();
            //  Menu_edit listener
            menu_edit.addActionListener(this);
        }
        private void buildSearchBox(){
            searchPanel.setLayout(new BorderLayout());
            searchPanel.add(button_search, BorderLayout.EAST);
            button_search.addActionListener(this);
            searchPanel.add(button_add, BorderLayout.WEST);
            button_add.addActionListener(this);

            JPanel sList = new JPanel(new FlowLayout());
            sList.add(search_combobox);
            sList.add(search_box);

            searchPanel.add(sList);
        }

        //  Builds the table headers (columns)
        private void buildTableModel() {
            //  Table model
            delivery_tableModel = new DefaultTableModel();
            //  Do the headers
            delivery_tableModel.addColumn("ID");
            delivery_tableModel.addColumn("Delivery Date");
            delivery_tableModel.addColumn("Delivery Status");
            delivery_tableModel.addColumn("Customer ID");
            delivery_tableModel.addColumn("Invoice ID");
            delivery_tableModel.addColumn("Publication ID");
            //  set table to use the model
            delivery_table.setModel(delivery_tableModel);
            //  disable moving columns around
            delivery_table.getTableHeader().setReorderingAllowed(false);
        }

        private void tableListener() {
            delivery_table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        rowSelected = delivery_table.rowAtPoint(e.getPoint());
                        delivery_table.setRowSelectionInterval(rowSelected, rowSelected);
                        JPopupMenu pop = new JPopupMenu();
                        pop.add(menu_edit);
                        pop.show(delivery_tablePane, e.getX()+1, e.getY()+16);
                    }
                }
            });
        }

        //  Populates data from deliveries ArrayList
        private void updateTableModel() {
            delivery_tableModel.setRowCount(0);
            for (DB_Delivery del : deliveries) {
                delivery_tableModel.addRow(del.getRowData());
            }
        }

        private ArrayList<DB_Delivery> constructSearch() throws DAOExceptionHandler {
            ArrayList<DB_Delivery> search = new ArrayList<>();
            DB_Delivery search1 = new DB_Delivery();

            if(search_combobox.getSelectedItem() == "All"){
                search = dao.getDeliveries();
            }
            if(search_combobox.getSelectedItem() == "ID"){
                try {
                    search1 = dao.getDelivery(Integer.parseInt(search_box.getText()));
                    search.clear();
                    search.add(search1);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Enter a numerical ID or ID doesn't exist", "ID Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(search_combobox.getSelectedItem() == "Date"){
                try {
                    search = dao.getDeliveriesByDate(Date.valueOf(search_box.getText()));
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Enter a Date yyyy-mm-dd", "Date Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(search_combobox.getSelectedItem() == "Status"){
                try {
                    search = dao.getDeliveriesByStatus(Boolean.valueOf(search_box.getText()));
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Enter a status true/false", "Status Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(search_combobox.getSelectedItem() == "Customer ID"){
                try {
                    search = dao.getDeliveriesByCustomer(Integer.parseInt(search_box.getText()));
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Enter a numerical ID or ID doesn't exist", "Customer ID Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(search_combobox.getSelectedItem() == "Invoice ID"){
                try {
                    search = dao.getDeliveriesByInvoice(Integer.parseInt(search_box.getText()));
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Enter a numerical ID or ID doesn't exist", "Invoice ID Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            if(search_combobox.getSelectedItem() == "Prod ID"){
                try {
                    search = dao.getDeliveriesByPublication(Integer.parseInt(search_box.getText()));
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Enter a numerical ID or ID doesn't exist", "Prod ID Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            return search;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //  If search button is pressed
            if (e.getSource() == button_search) {
                try {
                        //  Get new data (no search criteria for now)
                        deliveries = constructSearch();
                        //  Update table
                        updateTableModel();
                } catch (DAOExceptionHandler exception) {
                    exception.printStackTrace();
                }
            }else if (e.getSource() == menu_edit) {
                //  Wild magic to get an integer out of a table field
                int id = Integer.parseInt((String) delivery_table.getValueAt(rowSelected, 0));
                try {
                    parent = (JFrame) SwingUtilities.windowForComponent(this);

                    JDialog memory = new Editor(dao).delivery(dao.getDelivery(id), parent);
                    System.out.println();
                } catch (DAOExceptionHandler exc) {
                    exc.printStackTrace();
                }
            }else if (e.getSource() == button_add) {
                parent = (JFrame) SwingUtilities.windowForComponent(this);
                new Editor(dao).delivery(new DB_Delivery(), parent);
            }
        }
    }


    //  ========================================================================================================================
    //  INVOICE TAB
    //  ========================================================================================================================
    private class invoiceTab extends JPanel implements ActionListener {
        private final JButton button_search = new JButton("Search");
        //  Top panel to put search functionality into
        private final JPanel searchPanel = new JPanel();
        //  ScrollPane to be used by JTable
        private final JScrollPane invoice_tablePane = new JScrollPane();
        //  JTable and TableModel for it
        private final JTable invoice_table = new JTable() {
            //  Disable direct editing of the table will need to implement it separately
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        private DefaultTableModel invoice_tableModel;
        //  ArrayList for customers
        private ArrayList<DB_Invoice> invoices;
        //Search Box with comboBox attribute selector
        JTextField search_box = new JTextField(10);

        String[] strings = {"All", "ID", "Date", "Latest Date","Status", "Customer ID"};
        JComboBox search_combobox = new JComboBox(strings);


        //  Constructor WIP
        private invoiceTab() throws DAOExceptionHandler {
            //  Set layout
            setLayout(new BorderLayout());
            //  Add both panes
            add(searchPanel, BorderLayout.NORTH);
            add(invoice_tablePane, BorderLayout.CENTER);
            //  Search pane
            searchPanel.add(button_search);
            button_search.addActionListener(this);
            //  Table pane
            invoice_tablePane.getViewport().add(invoice_table);
            buildTableModel();
            // CONSTRUCT SEARCH
            constructSearch();
            buildSearchBox();
        }

        //  Builds the table headers (columns)
        private void buildTableModel() {
            //  Table model
            invoice_tableModel = new DefaultTableModel();
            //  Do the headers
            invoice_tableModel.addColumn("ID");
            invoice_tableModel.addColumn("Customer ID");
            invoice_tableModel.addColumn("Issue Date");
            invoice_tableModel.addColumn("Invoice Status");
            invoice_tableModel.addColumn("Invoice Total");
            //  set table to use the model
            invoice_table.setModel(invoice_tableModel);
            //  disable moving columns around
            invoice_table.getTableHeader().setReorderingAllowed(false);
        }

        //  Populates data from invoice ArrayList
        private void updateTableModel() {
            invoice_tableModel.setRowCount(0);
            for (DB_Invoice invoice : invoices) {
                invoice_tableModel.addRow(invoice.getRowData());
            }
        }

        private void buildSearchBox(){
            searchPanel.setLayout(new BorderLayout());

            searchPanel.add(button_search, BorderLayout.EAST);
            button_search.addActionListener(this);

            JPanel sList = new JPanel(new FlowLayout());
            sList.add(search_combobox);
            sList.add(search_box);

            searchPanel.add(sList);
        }

        private ArrayList<DB_Invoice> constructSearch() throws DAOExceptionHandler {
            ArrayList<DB_Invoice> search = new ArrayList<>();
            DB_Invoice search2 = new DB_Invoice();

            if(search_combobox.getSelectedItem() == "All"){
                search = dao.getInvoices();
            }
            if(search_combobox.getSelectedItem() == "ID"){
                search2 = dao.getInvoice(Integer.parseInt(search_box.getText()));
                search.clear();
                search.add(search2);
            }
            if(search_combobox.getSelectedItem() == "Date"){
                search = dao.getinvoicesByDate(Date.valueOf(search_box.getText()));
            }
            if(search_combobox.getSelectedItem() == "Latest Date"){
                search = dao.getLatestInvoiceByDate();
            }
            if(search_combobox.getSelectedItem() == "Status"){
                search = dao.getinvoicesByStatus(Boolean.valueOf(search_box.getText()));
            }
            if(search_combobox.getSelectedItem() == "Invoice total"){
                search = dao.getinvoicesByTotal(Double.valueOf(search_box.getText()));
            }
            if(search_combobox.getSelectedItem() == "Customer ID"){
                search = dao.getinvoicesByCustomer(Integer.parseInt(search_box.getText()));
            }


            return search;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //  If search button is pressed
            if (e.getSource() == button_search) {
                try {
                    //  Get new data (no search criteria for now)
                    invoices = constructSearch();
                    //  Update table
                    updateTableModel();
                } catch (DAOExceptionHandler exception) {
                    exception.printStackTrace();
                }
            }
        }
    }


    //  ========================================================================================================================
    //  SUBSCRIPTION TAB
    //  ========================================================================================================================
    private class subscriptionTab extends JPanel implements ActionListener{
        private final JButton button_search = new JButton("Search"), button_add = new JButton("+");
        //  Top panel to put search functionality into
        private final JPanel searchPanel = new JPanel();
        //  ScrollPane to be used by JTable
        private final JScrollPane subscription_tablePane = new JScrollPane();
        //  JTable and TableModel for it
        private final JTable subscription_table = new JTable() {
            //  Disable direct editing of the table will need to implement it separately
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        private DefaultTableModel subscription_tableModel;
        //  ArrayList for subscription
        private ArrayList<DB_Subscription> subscriptions;
        //Content menu items
        private final JMenuItem menu_edit = new JMenuItem("Edit");
        private int rowSelected;
        //Search Box with comboBox for attribute selection
        JTextField search_box = new JTextField(10);

        String[] titles = {"All", "Customer ID", "Publication ID"};
        JComboBox search_comboBox = new JComboBox(titles);

        //  Constructor WIP
        private subscriptionTab() {
            //  Set layout
            setLayout(new BorderLayout());
            //  Add both panes
            add(searchPanel, BorderLayout.NORTH);
            add(subscription_tablePane, BorderLayout.CENTER);
            //  Search pane
            searchPanel.add(button_search);
            buildSubscriptionSearch();
            button_search.addActionListener(this);
            //  Table pane
            subscription_tablePane.getViewport().add(subscription_table);
            //Table listener
            tableListens();
            buildTableModel();
            //Menu_edit listener
            menu_edit.addActionListener(this);
        }

        private void buildSubscriptionSearch(){
            searchPanel.setLayout(new BorderLayout());

            searchPanel.add(button_search, BorderLayout.EAST);
            button_search.addActionListener(this);

            searchPanel.add(button_add, BorderLayout.WEST);
            button_add.addActionListener(this);

            JPanel listing = new JPanel(new FlowLayout());
            listing.add(search_comboBox);
            listing.add(search_box);

            searchPanel.add(listing);
        }

        //  Builds the table headers (columns)
        private void buildTableModel() {
            //  Table model
            subscription_tableModel = new DefaultTableModel();
            //  Do the headers
            subscription_tableModel.addColumn("Customer ID");
            subscription_tableModel.addColumn("Publication ID");
            subscription_tableModel.addColumn("Count");
            //  set table to use the model
            subscription_table.setModel(subscription_tableModel);
            //  disable moving columns around
            subscription_table.getTableHeader().setReorderingAllowed(false);
        }

        private void tableListens(){
            subscription_table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                   super.mouseClicked(e);
                   if(e.getButton() == MouseEvent.BUTTON3){
                       rowSelected = subscription_table.rowAtPoint(e.getPoint());
                       subscription_table.setRowSelectionInterval(rowSelected, rowSelected);
                       JPopupMenu popup = new JPopupMenu();
                       popup.add(menu_edit);
                       popup.show(subscription_tablePane, e.getX()+1, e.getY()+16);
                   }
                }
            });
        }

        //  Populates data from subscriptions ArrayList
        private void updateTableModel() {
            subscription_tableModel.setRowCount(0);
            for (DB_Subscription sub : subscriptions) {
                subscription_tableModel.addRow(sub.getRowData());
            }
        }

        private ArrayList<DB_Subscription> constructionSearch() throws DAOExceptionHandler {
            ArrayList<DB_Subscription> search = new ArrayList<>();


            if (search_comboBox.getSelectedItem() == "All") {
                search = dao.getSubscriptions();
            }

            if (search_comboBox.getSelectedItem() == "Customer ID") {
                try{
                    search = dao.getSubscriptionByCustomer(Integer.parseInt(search_box.getText()));
                }catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Please enter a numerical ID or an ID that does not exist", "Customer ID error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (search_comboBox.getSelectedItem() == "Publication ID") {
                try {
                    search = dao.getSubscriptionByPublication(Integer.parseInt(search_box.getText()));
                }catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Please enter a numerical ID or an ID that does not exist", "Publication ID error", JOptionPane.ERROR_MESSAGE);

                }
            }
            return search;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            //  If search button is pressed
            if (e.getSource() == button_search) {
                try {
                    //  Get new data (no search criteria for now)
                    subscriptions = constructionSearch();
                    //  Update table
                    updateTableModel();
                } catch (DAOExceptionHandler exception) {
                    exception.printStackTrace();
                }
            }else if(e.getSource() == menu_edit){
                long id = Integer.parseInt((String) subscription_table.getValueAt(rowSelected, 0));
                long id2 = Integer.parseInt((String) subscription_table.getValueAt(rowSelected, 1));
                try{
                    parent = (JFrame) SwingUtilities.windowForComponent(this);

                    JDialog memory = new Editor(dao).subscription(dao.getSubscription(id, id2), parent);
                    System.out.println();
                }catch (DAOExceptionHandler exception){
                    exception.printStackTrace();
                }
            }else if (e.getSource() == button_add){
                parent = (JFrame) SwingUtilities.windowForComponent(this);
                new Editor(dao).subscription(new DB_Subscription(), parent);
            }
        }
    }

    //  ========================================================================================================================
    //  Employee TAB
    //  ========================================================================================================================
    private class employeeTab extends JPanel implements ActionListener{
        private final JButton button_search = new JButton("Search"), button_add = new JButton("+");
        //  Top panel to put search functionality into
        private final JPanel searchPanel = new JPanel();
        //  ScrollPane to be used by JTable
        private final JScrollPane employee_tablePane = new JScrollPane();
        //  JTable and TableModel for it
        private final JTable employee_table = new JTable() {
            //  Disable direct editing of the table will need to implement it separately
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        private DefaultTableModel employee_tableModel;
        //  ArrayList for employees
        private ArrayList<DB_Employee> employees;
        //  Context menu items
        private final JMenuItem menu_edit = new JMenuItem("Edit");
        private int rowSelected;
        //Search Box with comboBox attribute selector
        JTextField search_box = new JTextField(10);

        String[] strings = {"All", "ID", "First Name", "Last Name"};
        JComboBox search_combobox = new JComboBox(strings);

        //  Constructor WIP
        private employeeTab() {
            //  Set layout
            setLayout(new BorderLayout());
            //  Add both panes
            add(searchPanel, BorderLayout.NORTH);
            add(employee_tablePane, BorderLayout.CENTER);
            //  Search pane
            buildSearchBox();
            //  Table pane
            employee_tablePane.getViewport().add(employee_table);
            buildTableModel();
            //  Table listener
            tableListener();
            //  Menu_edit listener
            menu_edit.addActionListener(this);
        }
        private void buildSearchBox(){
            searchPanel.setLayout(new BorderLayout());
            searchPanel.add(button_search, BorderLayout.EAST);
            button_search.addActionListener(this);
            searchPanel.add(button_add, BorderLayout.WEST);
            button_add.addActionListener(this);

            JPanel sList = new JPanel(new FlowLayout());
            sList.add(search_combobox);
            sList.add(search_box);

            searchPanel.add(sList);
        }

        //  Builds the table headers (columns)
        private void buildTableModel() {
            //  Table model
            employee_tableModel = new DefaultTableModel();
            //  Do the headers
            employee_tableModel.addColumn("ID");
            employee_tableModel.addColumn("First Name");
            employee_tableModel.addColumn("Last Name");
            //  set table to use the model
            employee_table.setModel(employee_tableModel);
            //  disable moving columns around
            employee_table.getTableHeader().setReorderingAllowed(false);
        }

        private void tableListener() {
            employee_table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        rowSelected = employee_table.rowAtPoint(e.getPoint());
                        employee_table.setRowSelectionInterval(rowSelected, rowSelected);
                        JPopupMenu pop = new JPopupMenu();
                        pop.add(menu_edit);
                        pop.show(employee_tablePane, e.getX()+1, e.getY()+16);
                    }
                }
            });
        }

        //  Populates data from deliveries ArrayList
        private void updateTableModel() {
            employee_tableModel.setRowCount(0);
            for (DB_Employee emp : employees) {
                employee_tableModel.addRow(emp.getRowData());
            }
        }

        private ArrayList<DB_Employee> constructSearch() throws DAOExceptionHandler {
            ArrayList<DB_Employee> search = new ArrayList<>();
            DB_Employee search1;

            if(search_combobox.getSelectedItem() == "All"){
                search = dao.getEmployees();
            }
            if(search_combobox.getSelectedItem() == "ID"){
                try {
                    search1 = dao.getEmployee(Integer.parseInt(search_box.getText()));
                    search.clear();
                    search.add(search1);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Enter a numerical ID or ID doesn't exist", "ID Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(search_combobox.getSelectedItem() == "First Name"){
                try {
                    search = dao.getEmployeesByFName(search_box.getText());
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Enter a valid first name", "First Name Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(search_combobox.getSelectedItem() == "Last Name"){
                try {
                    search = dao.getEmployeesByLName(search_box.getText());
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Enter a valid last name", "Last Name Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            return search;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //  If search button is pressed
            if (e.getSource() == button_search) {
                try {
                    //  Get new data (no search criteria for now)
                    employees = constructSearch();
                    //  Update table
                    updateTableModel();
                } catch (DAOExceptionHandler exception) {
                    exception.printStackTrace();
                }
            }else if (e.getSource() == menu_edit) {
                //  Wild magic to get an integer out of a table field
                int id = Integer.parseInt((String) employee_table.getValueAt(rowSelected, 0));
                try {
                    parent = (JFrame) SwingUtilities.windowForComponent(this);

                    JDialog memory = new Editor(dao).employee(dao.getEmployee(id), parent);
                    System.out.println();
                } catch (DAOExceptionHandler exc) {
                    exc.printStackTrace();
                }
            }else if (e.getSource() == button_add) {
                parent = (JFrame) SwingUtilities.windowForComponent(this);
                new Editor(dao).employee(new DB_Employee(), parent);
            }
        }
    }
}