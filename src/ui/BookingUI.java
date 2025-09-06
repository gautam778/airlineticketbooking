package ui;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import db.DatabaseConnection;

public class BookingUI extends JPanel {
    private JTextField nameField, ageField;
    private JComboBox<String> genderBox;
    private MainWindow parent;

    private String flightNo, source, dest, date;
    private int seats;

    public BookingUI(MainWindow parent, String flightNo, String source, String dest, String date, int seats) {
        this.parent = parent;
        this.flightNo = flightNo;
        this.source = source;
        this.dest = dest;
        this.date = date;
        this.seats = seats;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // === Flight Details Section ===
        JPanel flightInfoPanel = new JPanel();
        flightInfoPanel.setLayout(new BoxLayout(flightInfoPanel, BoxLayout.Y_AXIS));

        JLabel flightLabel = new JLabel("Flight Number: " + flightNo);
        flightLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel routeLabel = new JLabel("Route: " + source + " → " + dest);
        routeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel dateLabel = new JLabel("Date: " + date);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        flightInfoPanel.add(flightLabel);
        flightInfoPanel.add(routeLabel);
        flightInfoPanel.add(dateLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(flightInfoPanel, gbc);

        // === Passenger Details Section ===
        gbc.gridwidth = 1;

        JLabel nameLabel = new JLabel("Passenger Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nameField, gbc);

        JLabel ageLabel = new JLabel("Age:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(ageLabel, gbc);

        ageField = new JTextField(5);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(ageField, gbc);

        JLabel genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(genderLabel, gbc);

        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(genderBox, gbc);

        JButton bookBtn = new JButton("Book Ticket");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(bookBtn, gbc);

        // === Button Action ===
        bookBtn.addActionListener(e -> bookTicket());
    }

    private void bookTicket() {
        String name = nameField.getText().toUpperCase();
        String gender = genderBox.getSelectedItem().toString();
        String ageText = ageField.getText();

        if (name.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all details.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a valid number.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO tickets (flight_number, passenger_name, gender, age) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, flightNo);
            stmt.setString(2, name);
            stmt.setString(3, gender);
            stmt.setInt(4, age);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                // Ticket text
                String ticketText = "==== Airline Ticket ====\n"
                        + "Flight Number: " + flightNo + "\n"
                        + "Source: " + source + "\n"
                        + "Destination: " + dest + "\n"
                        + "Date: " + date + "\n"
                        + "-----------------------\n"
                        + "Passenger: " + name + "\n"
                        + "Age: " + age + "\n"
                        + "Gender: " + gender + "\n"
                        + "=======================\n";

                saveTicketToFile(name, ticketText);

                // Switch to ticket page
                parent.showTicketPage(ticketText);
            } else {
                JOptionPane.showMessageDialog(this, "Booking failed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void saveTicketToFile(String name, String ticketText) {
        try (FileWriter writer = new FileWriter("Ticket_" + flightNo + "_" + name + ".txt")) {
            writer.write(ticketText);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving ticket file: " + ex.getMessage());
        }
    }
}
