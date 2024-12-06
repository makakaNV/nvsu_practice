package AutoServiceNew.GUI;

import javax.swing.*;
import java.awt.*;

public class NavigationPanel extends JPanel {

    public NavigationPanel(AutoServiceGUI mainFrame) {
        setLayout(new GridLayout(1, 4));

        JButton controlPanelButton = new JButton("Панель управления");
        JButton ordersPanelButton = new JButton("Заявки");
        JButton overviewPanelButton = new JButton("Обзор");
        JButton completedPanelButton = new JButton("Готовые авто");

        add(controlPanelButton);
        add(ordersPanelButton);
        add(overviewPanelButton);
        add(completedPanelButton);

        controlPanelButton.addActionListener(e -> new ControlPanelForm());
        ordersPanelButton.addActionListener(e -> mainFrame.getContentPanel().loadOrders("New"));
        overviewPanelButton.addActionListener(e -> mainFrame.getContentPanel().loadOrders("In Progress"));
        completedPanelButton.addActionListener(e -> mainFrame.getContentPanel().loadOrders("Completed"));
    }
}
