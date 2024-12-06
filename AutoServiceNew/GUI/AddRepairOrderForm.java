package AutoServiceNew.GUI;

import AutoServiceNew.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class AddRepairOrderForm extends JFrame {

    public AddRepairOrderForm() {
        setTitle("Добавление заявки");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(10, 2, 5, 5));

        JTextField fullNameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField vinField = new JTextField();
        JTextField brandField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField orderIdField = new JTextField();
        JTextField descriptionField = new JTextField();
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"New", "In Progress", "Completed"});

        add(new JLabel("ФИО:"));
        add(fullNameField);
        add(new JLabel("Телефон:"));
        add(phoneField);
        add(new JLabel("VIN:"));
        add(vinField);
        add(new JLabel("Марка:"));
        add(brandField);
        add(new JLabel("Модель:"));
        add(modelField);
        add(new JLabel("Год выпуска:"));
        add(yearField);
        add(new JLabel("ID заказа:"));
        add(orderIdField);
        add(new JLabel("Описание:"));
        add(descriptionField);
        add(new JLabel("Статус:"));
        add(statusBox);

        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");
        add(saveButton);
        add(cancelButton);

        saveButton.addActionListener(e -> {
            try {
                String fullName = fullNameField.getText().trim();
                String phone = phoneField.getText().trim();
                String vin = vinField.getText().trim();
                String brand = brandField.getText().trim();
                String model = modelField.getText().trim();
                int year = Integer.parseInt(yearField.getText().trim());
                int orderId = Integer.parseInt(orderIdField.getText().trim());
                String description = descriptionField.getText().trim();
                String status = (String) statusBox.getSelectedItem();

                if (fullName.isEmpty() || phone.isEmpty() || vin.isEmpty() || brand.isEmpty() || model.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Все поля обязательны для заполнения!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CustomerDAO customerDao = new CustomerDAOImpl(DatabaseConnection.getConnection());
                customerDao.addCustomer(new Customer(fullName, phone, vin));

                CarDAO carDao = new CarDAOImpl(DatabaseConnection.getConnection());
                carDao.addCar(new Car(vin, brand, model, year));

                RepairOrderDAO orderDao = new RepairOrderDAOImpl(DatabaseConnection.getConnection());
                orderDao.addRepairOrder(new RepairOrder(orderId, vin, description, status, LocalDate.now()));

                JOptionPane.showMessageDialog(this, "Заявка успешно добавлена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка при добавлении данных: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }
}
