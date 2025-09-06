package ui;

import javax.swing.*;
import java.awt.*;

public class TicketUI extends JPanel {
    private JTextArea ticketArea;

    public TicketUI(String ticketText) {
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Your Ticket", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        add(header, BorderLayout.NORTH);

        ticketArea = new JTextArea(ticketText);
        ticketArea.setEditable(false);
        ticketArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(ticketArea), BorderLayout.CENTER);
    }
}
