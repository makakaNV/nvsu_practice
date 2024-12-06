package AutoServiceNew.GUI;

import javax.swing.*;
import java.awt.*;

public class ControlPanelForm extends JFrame {

    public ControlPanelForm() {
        setTitle("Панель управления");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton addButton = new JButton("Добавить заявку");
        JButton deleteButton = new JButton("Удалить заявку");
        JButton findOrderButton = new JButton("Найти заявку");
        JButton findCustomerButton = new JButton("Найти клиента");

        add(addButton);
        add(deleteButton);
        add(findOrderButton);
        add(findCustomerButton);

        addButton.addActionListener(e -> new AddRepairOrderForm());
        deleteButton.addActionListener(e -> new DeleteRepairOrderForm());
        findOrderButton.addActionListener(e -> new FindRepairOrderForm());
        findCustomerButton.addActionListener(e -> new FindCustomerForm());
        setVisible(true);
    }
}
