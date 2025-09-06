package ui;

import db.DatabaseConnection;
import model.Flight;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PassengerDetailsUI extends JFrame {
    private Flight selectedFlight;
    private JTextField nameField, ageField, passportField;
    private JComboBox<String> genderBox;

    public PassengerDetailsUI(Flight flight) {
        this.selectedFlight = flight;

        setTitle("Enter Passenger Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Full Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Age:"));
        ageField = new JTextField();
        add(ageField);

        add(new JLabel("Gender:"));
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        add(genderBox);

        add(new JLabel("Passport No (International only):"));
        passportField = new JTextField();
        add(passportField);

        JButton submitButton = new JButton("Confirm Booking");
        add(new JLabel()); // filler
        add(submitButton);

        submitButton.addActionListener(e -> confirmBooking());
    }

    private void confirmBooking() {
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String gender = (String) genderBox.getSelectedItem();
        String passport = passportField.getText().trim();

        if (name.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Age are required!");
            return;
        }

        try {
            int age = Integer.parseInt(ageText);

            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);

                // Insert passenger into tickets table
                String insertSQL = "INSERT INTO tickets(passenger_name, age, gender, passport, flight_id) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, name);
                stmt.setInt(2, age);
                stmt.setString(3, gender);
                stmt.setString(4, passport.isEmpty() ? null : passport);
                stmt.setInt(5, selectedFlight.getId());
                stmt.executeUpdate();

                // Reduce seat count
                String updateSQL = "UPDATE flights SET seats_available = seats_available - 1 WHERE id=?";
                PreparedStatement stmt2 = conn.prepareStatement(updateSQL);
                stmt2.setInt(1, selectedFlight.getId());
                stmt2.executeUpdate();

                conn.commit();

                // Get generated ticket ID
                ResultSet rs = stmt.getGeneratedKeys();
                int ticketId = -1;
                if (rs.next()) {
                    ticketId = rs.getInt(1);
                }

                // Print ticket
                String ticket = "🎫 Airline Ticket\n\n"
                        + "Ticket ID: " + ticketId + "\n"
                        + "Passenger: " + name + "\n"
                        + "Age: " + age + "\n"
                        + "Gender: " + gender + "\n"
                        + (passport.isEmpty() ? "" : "Passport: " + passport + "\n")
                        + "Flight: " + selectedFlight.getFlightNumber() + "\n"
                        + "Route: " + selectedFlight.getSource() + " → " + selectedFlight.getDestination() + "\n"
                        + "Date: " + selectedFlight.getDate() + "\n";
                JOptionPane.showMessageDialog(this, ticket, "Ticket Confirmed", JOptionPane.INFORMATION_MESSAGE);

                this.dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a number!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error booking ticket: " + ex.getMessage());
        }
    }
}
