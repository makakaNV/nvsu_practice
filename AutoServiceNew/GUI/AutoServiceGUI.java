package AutoServiceNew.GUI;

import javax.swing.*;
import java.awt.*;

public class AutoServiceGUI extends JFrame {

    private NavigationPanel navigationPanel;
    private ContentPanel contentPanel;
    private CustomerInfoPanel customerInfoPanel;

    public AutoServiceGUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 16));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Автосервис");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        navigationPanel = new NavigationPanel(this);
        contentPanel = new ContentPanel(this);
        customerInfoPanel = new CustomerInfoPanel();

        add(navigationPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(customerInfoPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

//    public void loadForm() {
//        new ControlPanelForm();
//    }

    public CustomerInfoPanel getCustomerInfoPanel() {
        return customerInfoPanel;
    }

    public ContentPanel getContentPanel() {
        return contentPanel;
    }

    public static void main(String[] args) {
        new AutoServiceGUI();
    }
}
