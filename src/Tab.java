import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Tab {
    private final JTabbedPane pane;
    private JButton blank_customer;
    private JButton blank_invoice;
    private JPanel component;
    private final DAO dao;

    public Tab(JTabbedPane pane, DAO dao) {
        this.pane = pane;
        this.dao = dao;
    }


    public JPanel blank(){
        return component = new blankTab();
    }

    public JPanel customer() {
        return component = new customerTab();
    }

    public JPanel blank1(){
        return component = new blankTab();
    }

    public JPanel invoice() {
        return component = new customerTab();
    }

    //  Blank Tab
    private class blankTab extends JPanel implements ActionListener{
        private blankTab() {
            setLayout(new FlowLayout());
            blank_customer = new JButton("Customer");
            blank_customer.addActionListener(this);
            add(blank_customer);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //  Replace tab with new Tab
            if (e.getSource() == blank_customer) {
                int pos = pane.indexOfComponent(component);
                pane.setComponentAt(pos, new customerTab());
                pane.setTitleAt(pos, "Customer");
            }
        }
    }

    private class customerTab extends JPanel implements ActionListener {
        private final JButton button_search = new JButton("Search");
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

        //  Constructor WIP
        private customerTab() {
            //  Set layout
            setLayout(new BorderLayout());
            //  Add both panes
            add(searchPanel, BorderLayout.NORTH);
            add(customer_tablePane, BorderLayout.CENTER);
            //  Search pane
            searchPanel.add(button_search);
            button_search.addActionListener(this);
            //  Table pane
            customer_tablePane.getViewport().add(customer_table);
            buildTableModel();
        }

        //  Builds the table headers (columns)
        private void buildTableModel() {
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
        }

        //  Populates data from customers ArrayList
        private void updateTableModel() {
            for (DB_Customer cus : customers) {
                customer_tableModel.addRow(cus.getRowData());
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //  If search button is pressed
            if (e.getSource() == button_search) {
                try {
                    //  Get new data (no search criteria for now)
                    customers = dao.getCustomers(new Search_Customer[0]);
                    //  Update table
                    updateTableModel();
                } catch (DAOExceptionHandler exc) {
                    exc.printStackTrace();
                }
//                //Invoice
//                //  Blank Tab
//                private class blankTab1 extends JPanel implements ActionListener {
//                    private blankTab1() {
//                        setLayout(new FlowLayout());
//                        blank_invoice = new JButton("Invoice");
//                        blank_invoice.addActionListener(this);
//                        add(blank_invoice);
//                    }
//
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        //  Replace tab with new Tab
//                        if (e.getSource() == blank_invoice) {
//                            int pos = pane.indexOfComponent(component);
//                            pane.setComponentAt(pos, new invoiceTab());
//                            pane.setTitleAt(pos, "Invoice");
//                        }
//                    }
//                }
//
//                private class invoiceTab extends JPanel implements ActionListener {
//                    private final JButton button_search = new JButton("Search");
//                    //  Top panel to put search functionality into
//                    private final JPanel searchPanel = new JPanel();
//                    //  ScrollPane to be used by JTable
//                    private final JScrollPane invoice_tablePane = new JScrollPane();
//                    //  JTable and TableModel for it
//                    private final JTable invoice_table = new JTable() {
//                        //  Disable direct editing of the table will need to implement it separately
//                        public boolean isCellEditable(int row, int column) {
//                            return false;
//                        }
//                    };
//                    private DefaultTableModel customer_tableModel;
//                    //  ArrayList for customers
//                    private ArrayList<DB_Invoice> invoice;
//
//                    //  Constructor WIP
//                    private invoiceTab() {
//                        //  Set layout
//                        setLayout(new BorderLayout());
//                        //  Add both panes
//                        add(searchPanel, BorderLayout.NORTH);
//                        add(customer_tablePane, BorderLayout.CENTER);
//                        //  Search pane
//                        searchPanel.add(button_search);
//                        button_search.addActionListener(this);
//                        //  Table pane
//                        customer_tablePane.getViewport().add(invoice_table);
//                        buildTableModel();
//                    }
//
//                    //  Builds the table headers (columns)
//                    private void buildTableModel() {
//                        //  Table model
//                        invoice_tableModel = new DefaultTableModel();
//                        //  Do the headers
//                        invoice_tableModel.addColumn("ID");
//                        invoice_tableModel.addColumn("Issue Date");
//                        invoice_tableModel.addColumn("Invoice Status");
//                        invoice_tableModel.addColumn("Invoice Total");
//                        invoice_tableModel.addColumn("Customer");
//                        //  set table to use the model
//                        invoice_table.setModel(invoice_tableModel);
//                        //  disable moving columns around
//                       invoice_table.getTableHeader().setReorderingAllowed(false);
//                    }
//
//                    //  Populates data from customers ArrayList
//                    private void updateTableModel() {
//                        for (DB_Invoice inv : invoice) {
//                            invoice_tableModel.addRow(inv.getRowData());
//                        }
//                    }
//
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        //  If search button is pressed
//                        if (e.getSource() == button_search) {
//                            try {
//                                //  Get new data (no search criteria for now)
//                                invoice = dao.getInvoice(new Search_Invoice()[0]);
//                                //  Update table
//                                updateTableModel();
//                            } catch (DAOExceptionHandler exc) {
//                                exc.printStackTrace();
//                            }
                        }
                    }
                }
            }