package ui;

import javax.swing.*;
import java.awt.*;

public class FlightTypeUI extends JPanel {
    private MainWindow parent;

    public FlightTypeUI(MainWindow parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Select Flight Type", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        JButton domesticBtn = new JButton("Domestic Flights");
        JButton internationalBtn = new JButton("International Flights");

        domesticBtn.setPreferredSize(new Dimension(180, 40));
        internationalBtn.setPreferredSize(new Dimension(180, 40));

        gbc.gridy = 1; gbc.gridwidth = 1;
        add(domesticBtn, gbc);

        gbc.gridx = 1;
        add(internationalBtn, gbc);

        domesticBtn.addActionListener(e -> parent.showMainUI("Domestic"));
        internationalBtn.addActionListener(e -> parent.showMainUI("International"));
    }
}
