package AutoServiceNew.GUI;

import AutoServiceNew.*;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class FindRepairOrderForm extends JFrame {

    private final RepairOrderDAOImpl repairOrderDAO;

    public FindRepairOrderForm() {
        Connection connection = DatabaseConnection.getConnection();
        repairOrderDAO = new RepairOrderDAOImpl(connection);

        setTitle("Найти заявку");
        setSize(750, 200);
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
                    RepairOrder order = repairOrderDAO.getRepairOrderByVin(vin);
                    if (order != null) {
                        resultArea.setText(order.toString());
                    } else {
                        resultArea.setText("Заявка с VIN " + vin + " не найдена.");
                    }
                } else {
                    int orderId = Integer.parseInt(orderIdText);
                    RepairOrder order = repairOrderDAO.getRepairOrderById(orderId);
                    if (order != null) {
                        resultArea.setText(order.toString());
                    } else {
                        resultArea.setText("Заявка с Order ID " + orderId + " не найдена.");
                    }
                }
            } catch (Exception ex) {
                resultArea.setText("Ошибка при выполнении поиска: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}
