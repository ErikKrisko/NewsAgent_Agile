import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

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
                bUpdate.setText("Insert new");
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
            if (customer.getAddress()!= null)
                addressID.setText("" + customer.getAddress().getAddress_id());
            else
                addressID.setText("");

            setSize(800,120);
            setResizable(false);
            setVisible(true);
            setLocation(parentFrame.getLocation());
        }

        //  Read textFields and put them into customer, if an error occurs show an error message
        private boolean readCustomer() {
            try {
                customer.setFirst_name(fName.getText());
                customer.setLast_name(lName.getText());
                customer.setPhone_no(phoneNo.getText());
                try {
                    customer.setAddress(dao.getAddress(Integer.parseInt(addressID.getText())));
                } catch (DAOExceptionHandler daoExceptionHandler) {
                    JOptionPane.showMessageDialog(this, daoExceptionHandler.getMessage(), "Address Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                return true;
            } catch (DB_CustomerExceptionHandler exc) {
                JOptionPane.showMessageDialog(this, exc.getMessage(), "Information Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //  Make sure the button press goes through after reading the data
            if (e.getSource() == bUpdate && readCustomer()) {
                try {
                    dao.updateCustomer(customer);
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                } catch (DAOExceptionHandler exc) {
                    JOptionPane.showMessageDialog(this, exc.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == bDelete) {
                try {
                    dao.deleteCustomer(customer);
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                } catch (DAOExceptionHandler exc) {
                    JOptionPane.showMessageDialog(this, exc.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
                bUpdate.setText("Insert new");
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
            if(delivery.getDelivery_status() == 1){
                Status.setText("true");
            }else{
                Status.setText("false");
            }
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

        private boolean readDelivery(){
            try{
                delivery.setDelivery_date(java.sql.Date.valueOf(Date.getText()));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Enter a Date yyyy-mm-dd", "Date Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            try{
                delivery.setDelivery_status(Boolean.parseBoolean(Status.getText()));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Enter a Status true/false", "Date Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            try{
                delivery.setCustomer_id(Integer.parseInt(CustomerID.getText()));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Enter a numerical ID", "Date Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            try{
                delivery.setInvoice_id(Integer.parseInt(InvoiceID.getText()));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Enter a numerical ID", "Date Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            try{
                delivery.setProd_id(Integer.parseInt(ProdID.getText()));
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Enter a numerical ID", "Date Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bUpdate && readDelivery()) {
                try {
                    dao.updateDelivery(delivery);
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                } catch (DAOExceptionHandler ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == bDelete) {
                try {
                    dao.deleteDelivery(delivery);
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                } catch (DAOExceptionHandler ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == bCancel) {
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        }
    }
}
