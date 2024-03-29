import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame implements MouseListener, ActionListener {
    private DAO dao;
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menu_file = new JMenu("File"), menu_debug = new JMenu("Debug");
    private final MenuItem popup_close = new MenuItem("Close");
    private int popup_close_id = -1;
    private final Container contentPane = this.getContentPane();
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JMenuItem menu_file_exit = new JMenuItem("Exit"),
            menu_debug_database = new JMenuItem("Reset DB"),
            menu_debug_clearData = new JMenuItem("Clear DB");

    public Window() {
        //  connect DAO
        try {
            dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC","root","admin");
        } catch (DAOExceptionHandler e) {
            e.printStackTrace();
        }
        //  Set application close
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //  Table name and dimensions
        this.setTitle("Newsagent interface");
        this.setSize(864,600);
        this.setResizable(true);
        //  Set to use BorderLayout
        contentPane.setLayout(new BorderLayout());
        initMenus();
        initTabbedPane();

        //  Show
        this.setVisible(true);
    }

    private void initMenus() {
        //  PopupMenu
        popup_close.addActionListener(this);
        //  Menu file
        menu_file.add(menu_file_exit);
        menu_file_exit.addActionListener(this);
        menu_debug.add(menu_debug_database);
        menu_debug_database.addActionListener(this);
        menu_debug.add(menu_debug_clearData);
        menu_debug_clearData.addActionListener(this);


        //  Populate menuBar
        menuBar.add(menu_file);
        menuBar.add(menu_debug);
        //  Add menu bar
        contentPane.add(menuBar, BorderLayout.NORTH);
    }

    private void initTabbedPane() {
        //  Add main focus
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        //  Add a mouse listener to tabs
        tabbedPane.addMouseListener(this);
        //  Add Placeholder Tab
        tabbedPane.addTab("new_tab", new Tab(tabbedPane, dao).blank());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == popup_close && popup_close_id != -1) {
            tabbedPane.remove(popup_close_id);
            if (tabbedPane.getTabCount() < 1)
                tabbedPane.addTab("new_tab", new Tab(tabbedPane, dao).blank());
        } else if (e.getSource() == menu_file_exit) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (e.getSource() == menu_debug_database) {
            try {
                //  Run scripts to reset the Database
                JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
                connection.executeScript("NewsAgent_Database.sql");
                connection.setDbName("newsagent");
                connection.executeScript("NewsAgent_Data_Extended.sql");
                connection.close();
                //  Re-establish dao connection
                dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC","root","admin");
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        } else if (e.getSource() == menu_debug_clearData) {
            try {
                JDBC connection = new JDBC("jdbc:mysql://localhost:3306/", "root", "admin");
                connection.setDbName("newsagent");
                connection.executeScript("NewsAgent_Data_Clean.sql");
                connection.close();
                //  Re-establish dao connection
                dao = new DAO("jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC", "root", "admin");
            } catch (JDBCExceptionHandler | DAOExceptionHandler exc) {
                exc.printStackTrace();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                if (tabbedPane.getUI().getTabBounds(tabbedPane, i).contains(e.getPoint())) {
                    PopupMenu popupMenu = new PopupMenu();
                    add(popupMenu);
                    popupMenu.add(popup_close);
                    //  Offset display to match mouse pos
                    popupMenu.show(this, e.getX()+8, e.getY()+54);
                    popup_close_id = i;
                }
            }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
