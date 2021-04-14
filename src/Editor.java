import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Editor {
    private final DAO dao;

    public Editor(DAO dao) {
        this.dao = dao;
    }

    //  Specific panel constructor
    public JDialog customer(DB_Customer customer, JFrame parent) { return new customerEdit(customer, parent); }
    public JDialog delivery(DB_Delivery delivery, JFrame parent) { return new deliveryEdit(delivery, parent); }

    private class customerEdit extends JDialog implements ActionListener {
        private DB_Customer customer;
        //  TextFields
        private final JTextField fName = new JTextField(10),
                lName = new JTextField(10),
                phoneNo = new JTextField(10),
                addressID = new JTextField(2);
        //  Buttons
        private final JButton bUpdate = new JButton("Update"),
                bDelete = new JButton("Delete"),
                bCancel = new JButton("Cancel");

        //  Constructor
        private customerEdit(DB_Customer customer, JFrame parentFrame) {
            this.customer = customer;
            //  Disable main window when this is overplayed
            parentFrame.setEnabled(false);
            //  Add action listener to re-enable after closing
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    parentFrame.setEnabled(true);
                    super.windowClosed(e);
                    dispose();
                }
            });

            //  set border layout
            getContentPane().setLayout(new BorderLayout());
            //  add buttons to SOUTH
            JPanel buttonBar = new JPanel(new FlowLayout());
            buttonBar.add(bUpdate);
            bUpdate.addActionListener(this);
            buttonBar.add(bDelete);
            bDelete.addActionListener(this);
            buttonBar.add(bCancel);
            bCancel.addActionListener(this);
            getContentPane().add(buttonBar, BorderLayout.SOUTH);
            //  Add main panel
            JPanel view = new JPanel(new FlowLayout());
            getContentPane().add(view, BorderLayout.CENTER);
            //  populate view
            if (customer.getCustomer_id() == 0) {
                view.add(new JLabel("ID: "));
                JTextField idField = new JTextField(2);
                idField.setText("-");
                idField.setEditable(false);
                view.add(idField);
                setTitle("New Customer");
            } else {
                view.add(new JLabel("ID: "));
                JTextField idField = new JTextField(2);
                idField.setText("" + customer.getCustomer_id());
                idField.setEditable(false);
                view.add(idField);
                setTitle("Edit Customer");
            }
            view.add(new JLabel("First Name: "));
            view.add(fName);
            fName.setText(customer.getFirst_name());
            view.add(new JLabel("Last Name: "));
            view.add(lName);
            lName.setText(customer.getLast_name());
            view.add(new JLabel("Phone no: "));
            view.add(phoneNo);
            phoneNo.setText(customer.getPhone_no());
            view.add(new JLabel("Address ID: "));
            view.add(addressID);
            addressID.setText("" + customer.getAddress().getAddress_id());

            setSize(800,120);
            setResizable(false);
            setVisible(true);
            setLocation(parentFrame.getLocation());
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bUpdate) {
                try {
                    dao.updateCustomer(customer);
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                } catch (DAOExceptionHandler exc) {
                    exc.printStackTrace();
                }
            } else if (e.getSource() == bDelete) {
                try {
                    dao.deleteCustomer(customer);
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                } catch (DAOExceptionHandler exc) {
                    exc.printStackTrace();
                }
            } else if (e.getSource() == bCancel) {
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        }
    }

    private class deliveryEdit extends  JDialog implements ActionListener{
        private DB_Delivery delivery;
        //  TextFields
        private final JTextField Date = new JTextField(10),
                Status = new JTextField(10),
                CustomerID = new JTextField(2),
                InvoiceID = new JTextField(2),
                ProdID = new JTextField(2);
        //  Buttons
        private final JButton bUpdate = new JButton("Update"),
                bDelete = new JButton("Delete"),
                bCancel = new JButton("Cancel");

        //Constructor
        private deliveryEdit(DB_Delivery delivery, JFrame parentFrame){
            this.delivery = delivery;
            //  Disable main window when this is overplayed
            parentFrame.setEnabled(false);
            //  Add action listener to re-enable after closing
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    parentFrame.setEnabled(true);
                    super.windowClosed(e);
                    dispose();
                }
            });
            //  set border layout
            getContentPane().setLayout(new BorderLayout());
            //  add buttons to SOUTH
            JPanel buttonBar = new JPanel(new FlowLayout());
            buttonBar.add(bUpdate);
            bUpdate.addActionListener(this);
            buttonBar.add(bDelete);
            bDelete.addActionListener(this);
            buttonBar.add(bCancel);
            bCancel.addActionListener(this);
            getContentPane().add(buttonBar, BorderLayout.SOUTH);
            //  Add main panel
            JPanel view = new JPanel(new FlowLayout());
            getContentPane().add(view, BorderLayout.CENTER);
            //  populate view
            if (delivery.getDelivery_id() == 0) {
                view.add(new JLabel("ID: "));
                JTextField idField = new JTextField(2);
                idField.setText("-");
                idField.setEditable(false);
                view.add(idField);
                setTitle("New Delivery");
            }else {
                view.add(new JLabel("ID: "));
                JTextField idField = new JTextField(2);
                idField.setText("" + delivery.getDelivery_id());
                idField.setEditable(false);
                view.add(idField);
                setTitle("Edit Delivery");
            }
            view.add(new JLabel("Delivery Date: "));
            view.add(Date);
            Date.setText(String.valueOf(delivery.getDelivery_date()));
            view.add(new JLabel("Delivery Status: "));
            view.add(Status);
            Status.setText(String.valueOf(delivery.getDelivery_status()));
            view.add(new JLabel("Customer ID: "));
            view.add(CustomerID);
            CustomerID.setText("" + delivery.getCustomer_id());
            view.add(new JLabel("Invoice ID: "));
            view.add(InvoiceID);
            InvoiceID.setText("" + delivery.getInvoice_id());
            view.add(new JLabel("Prod ID: "));
            view.add(ProdID);
            ProdID.setText("" + delivery.getProd_id());

            setSize(800,120);
            setResizable(false);
            setVisible(true);
            setLocation(parentFrame.getLocation());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bUpdate) {
                try {
                    dao.updateDelivery(delivery);
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                } catch (DAOExceptionHandler exc) {
                    exc.printStackTrace();
                }
            } else if (e.getSource() == bDelete) {
                try {
                    dao.deleteDelivery(delivery);
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                } catch (DAOExceptionHandler exc) {
                    exc.printStackTrace();
                }
            } else if (e.getSource() == bCancel) {
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        }
    }
}
