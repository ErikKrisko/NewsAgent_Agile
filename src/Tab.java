import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tab {
    private JTabbedPane pane;
    private JButton blank_customer;
    private JPanel component;

    public Tab(JTabbedPane pane) {
        this.pane = pane;
    }


    public JPanel blank(){
        return component = new blankTab();
    }

    public JPanel customer() {
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
                pane.setComponentAt(pos, new Tab(pane).customer());
                pane.setTitleAt(pos, "Customer");
            }
        }
    }

    private class customerTab extends JPanel{
        private customerTab() {
            add(new JLabel("Customer"));
        }
    }
}
