package AutoServiceNew.GUI;

import AutoServiceNew.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ContentPanel extends JPanel {

    private JList<RepairOrder> ordersList;
    private DefaultListModel<RepairOrder> ordersModel;
    private JButton changeStatusButton;
    private String currentStatus;
    private JButton deleteButton;

    public ContentPanel(AutoServiceGUI mainFrame) {
        setLayout(new BorderLayout());

        ordersModel = new DefaultListModel<>();
        ordersList = new JList<>(ordersModel);
        ordersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(ordersList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        changeStatusButton = new JButton("Изменить статус");
        changeStatusButton.setEnabled(false);
        changeStatusButton.addActionListener(e -> changeOrderStatus());

        ordersList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                RepairOrder selectedOrder = ordersList.getSelectedValue();
                if (selectedOrder != null) {
                    mainFrame.getCustomerInfoPanel().displayCustomerInfo(selectedOrder.getVin());
                    updateButtonStatus(selectedOrder.getStatus());
                }
            }
        });

        deleteButton = new JButton("Удалить");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(e -> deleteOrder());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(changeStatusButton);
        buttonPanel.add(deleteButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void loadOrders(String status) {
        currentStatus = status;
        RepairOrderDAOImpl repairOrderDAO = new RepairOrderDAOImpl(DatabaseConnection.getConnection());
        List<RepairOrder> orders = repairOrderDAO.getRepairOrderByStatus(status);
        displayOrders(orders);
        changeStatusButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private void displayOrders(List<RepairOrder> orders) {
        ordersModel.clear();
        for (RepairOrder order : orders) {
            ordersModel.addElement(order);
        }
    }

    private void updateButtonStatus(String status) {
        if ("New".equals(status)) {
            changeStatusButton.setText("В работу");
            changeStatusButton.setEnabled(true);
            deleteButton.setEnabled(true);
        } else if ("In Progress".equals(status)) {
            changeStatusButton.setText("Завершено");
            changeStatusButton.setEnabled(true);
            deleteButton.setEnabled(true);
        } else {
            changeStatusButton.setEnabled(false);
            deleteButton.setEnabled(true);
        }
    }

    private void changeOrderStatus() {
        RepairOrder selectedOrder = ordersList.getSelectedValue();
        if (selectedOrder == null) {
            return;
        }

        RepairOrderDAOImpl repairOrderDAO = new RepairOrderDAOImpl(DatabaseConnection.getConnection());
        String newStatus;

        if ("New".equals(selectedOrder.getStatus())) {
            newStatus = "In Progress";
        } else if ("In Progress".equals(selectedOrder.getStatus())) {
            newStatus = "Completed";
        } else {
            return;
        }

        boolean success = repairOrderDAO.updateRepairOrderStatus(selectedOrder.getOrderId(), newStatus);
        if (success) {
            JOptionPane.showMessageDialog(this, "Статус заявки изменён на " + newStatus);
            loadOrders(currentStatus);
        } else {
            JOptionPane.showMessageDialog(this, "Не удалось изменить статус заявки", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteOrder() {
        RepairOrder selectedOrder = ordersList.getSelectedValue();
        if (selectedOrder == null) return;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Вы уверены, что хотите удалить заявку и связанные с ней данные?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            RepairOrderDAOImpl repairOrderDAO = new RepairOrderDAOImpl(DatabaseConnection.getConnection());
            CarDAOImpl carDAO = new CarDAOImpl(DatabaseConnection.getConnection());
            CustomerDAOImpl customerDAO = new CustomerDAOImpl(DatabaseConnection.getConnection());

            try {
                String vin = selectedOrder.getVin();
                int orderId = selectedOrder.getOrderId();

                repairOrderDAO.deleteRepairOrder(orderId);
                carDAO.deleteCar(vin);
                customerDAO.deleteCustomer(vin);

                JOptionPane.showMessageDialog(this, "Данные успешно удалены!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                loadOrders(currentStatus);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка при удалении данных: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
