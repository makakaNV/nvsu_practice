package AutoServiceNew.GUI;

import AutoServiceNew.*;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class FindCustomerForm extends JFrame {

    private final CustomerDAOImpl customerDAO;
    private final RepairOrderDAOImpl repairOrderDAO;

    public FindCustomerForm() {
        Connection connection = DatabaseConnection.getConnection();
        customerDAO = new CustomerDAOImpl(connection);
        repairOrderDAO = new RepairOrderDAOImpl(connection);

        setTitle("Найти клиента");
        setSize(750, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField vinField = new JTextField();
        JTextField orderIdField = new JTextField();
        JButton searchButton = new JButton("Найти");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        inputPanel.add(new JLabel("Поиск по VIN:"));
        inputPanel.add(vinField);
        inputPanel.add(new JLabel("Поиск по Order ID:"));
        inputPanel.add(orderIdField);
        inputPanel.add(searchButton);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(inputPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String vin = vinField.getText().trim();
            String orderIdText = orderIdField.getText().trim();

            if (vin.isEmpty() && orderIdText.isEmpty()) {
                resultArea.setText("Введите VIN или Order ID для поиска.");
                return;
            }

            try {
                if (!vin.isEmpty()) {
                    Customer customer = customerDAO.getCustomerByVin(vin);
                    if (customer != null) {
                        resultArea.setText(customer.toString());
                    } else {
                        resultArea.setText("Клиент с VIN " + vin + " не найден.");
                    }
                } else {
                    int orderId = Integer.parseInt(orderIdText);
                    String vinByOrderId = repairOrderDAO.getVinByOrderId(orderId);

                    if (vinByOrderId != null) {
                        Customer customer = customerDAO.getCustomerByVin(vinByOrderId);
                        if (customer != null) {
                            resultArea.setText(customer.toString());
                        } else {
                            resultArea.setText("Клиент с VIN " + vinByOrderId + " не найден.");
                        }
                    } else {
                        resultArea.setText("VIN для Order ID " + orderId + " не найден.");
                    }
                }
            } catch (NumberFormatException ex) {
                resultArea.setText("Order ID должен быть числом.");
            } catch (Exception ex) {
                resultArea.setText("Ошибка при выполнении поиска: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}
