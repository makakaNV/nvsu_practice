package AutoServiceNew.GUI;

import AutoServiceNew.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class DeleteRepairOrderForm extends JFrame {

    private final RepairOrderDAOImpl repairOrderDAO;
    private final CustomerDAOImpl customerDAO;
    private final CarDAOImpl carDAO;

    public DeleteRepairOrderForm() {
        // Инициализируем DAO-объекты
        Connection connection = DatabaseConnection.getConnection();
        repairOrderDAO = new RepairOrderDAOImpl(connection);
        customerDAO = new CustomerDAOImpl(connection);
        carDAO = new CarDAOImpl(connection);

        setTitle("Удалить заявку");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        JTextField vinField = new JTextField();
        JTextField orderIdField = new JTextField();

        JButton deleteButton = new JButton("Удалить");
        JButton cancelButton = new JButton("Отмена");

        add(new JLabel("Введите VIN:"));
        add(vinField);
        add(new JLabel("Введите Order ID:"));
        add(orderIdField);
        add(deleteButton);
        add(cancelButton);

        deleteButton.addActionListener(e -> {
            String vin = vinField.getText().trim();
            String orderIdText = orderIdField.getText().trim();

            if (vin.isEmpty() && orderIdText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Введите VIN или Order ID для удаления!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (!vin.isEmpty()) {
                    int orderId = repairOrderDAO.getOrderIdByVin(vin); // Получаем orderId из VIN
                    if (orderId != -1) {
                        deleteRecords(vin, orderId);
                    } else {
                        JOptionPane.showMessageDialog(this, "Заявка с VIN " + vin + " не найдена.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    int orderId = Integer.parseInt(orderIdText);
                    String vinFromOrder = repairOrderDAO.getVinByOrderId(orderId); // Получаем VIN из orderId
                    if (vinFromOrder != null) {
                        deleteRecords(vinFromOrder, orderId);
                    } else {
                        JOptionPane.showMessageDialog(this, "Заявка с Order ID " + orderId + " не найдена.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка при удалении данных: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void deleteRecords(String vin, int orderId) {
        try {
            repairOrderDAO.deleteRepairOrder(orderId);
            carDAO.deleteCar(vin);
            customerDAO.deleteCustomer(vin);

            JOptionPane.showMessageDialog(this, "Данные успешно удалены!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ошибка при удалении данных: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
