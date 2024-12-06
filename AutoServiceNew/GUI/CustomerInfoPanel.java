package AutoServiceNew.GUI;

import AutoServiceNew.*;
import javax.swing.*;
import java.awt.*;

public class CustomerInfoPanel extends JPanel {

    public CustomerInfoPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Информация о клиенте"));
        setPreferredSize(new Dimension(800, 100));
        setVisible(false);
    }

    public void displayCustomerInfo(String vin) {
        CustomerDAOImpl customerDAO = new CustomerDAOImpl(DatabaseConnection.getConnection());
        Customer customer = customerDAO.getCustomerByVin(vin);

        removeAll();

        if (customer != null) {
            JLabel customerInfoLabel = new JLabel(
                    "Собственник: " + customer.getFullName() +
                            ", Телефон: " + customer.getPhoneNumber() +
                            ", VIN: " + customer.getVin()
            );
            add(customerInfoLabel, BorderLayout.CENTER);
        } else {
            JLabel noCustomerLabel = new JLabel("Информация о клиенте не найдена для VIN: " + vin);
            add(noCustomerLabel, BorderLayout.CENTER);
        }

        setVisible(true);
        revalidate();
        repaint();
    }
}

