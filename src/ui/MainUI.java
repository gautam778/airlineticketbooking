package ui;

import db.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MainUI extends JPanel {
    private MainWindow parent;
    private String flightType;
    private JTable flightTable;
    private JComboBox<String> sourceDropdown;
    private JComboBox<String> destDropdown;
    private DefaultTableModel tableModel;

    public MainUI(String flightType, MainWindow parent) {
        this.parent = parent;
        this.flightType = flightType;

        setLayout(new BorderLayout());

        // --- Filter Section ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Source:"));
        sourceDropdown = new JComboBox<>();
        filterPanel.add(sourceDropdown);

        filterPanel.add(new JLabel("Destination:"));
        destDropdown = new JComboBox<>();
        filterPanel.add(destDropdown);

        JButton filterBtn = new JButton("Filter");
        filterBtn.addActionListener(e -> loadFlights((String) sourceDropdown.getSelectedItem(),
                                                     (String) destDropdown.getSelectedItem()));
        filterPanel.add(filterBtn);

        add(filterPanel, BorderLayout.NORTH);

        // --- Flights Table ---
        String[] columns = {"Flight No", "Source", "Destination", "Date", "Seats", "Type"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // Table is read-only
            }
        };
        flightTable = new JTable(tableModel);
        flightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(flightTable), BorderLayout.CENTER);

        // --- Book Button ---
        JButton bookBtn = new JButton("Book Selected Flight");
        bookBtn.addActionListener(e -> bookSelectedFlight());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(bookBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load flights + dropdown values
        loadFilters();
        loadFlights(null, null);
    }

    // --- Load unique source/destination into dropdowns ---
    private void loadFilters() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();

            // Sources
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT source FROM flights WHERE type='" + flightType + "'");
            sourceDropdown.addItem("All");
            while (rs.next()) {
                sourceDropdown.addItem(rs.getString("source"));
            }

            // Destinations
            rs = stmt.executeQuery("SELECT DISTINCT destination FROM flights WHERE type='" + flightType + "'");
            destDropdown.addItem("All");
            while (rs.next()) {
                destDropdown.addItem(rs.getString("destination"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // --- Load flights from DB into table ---
    public void loadFlights(String source, String dest) {
        tableModel.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM flights WHERE type=?";
            if (source != null && !"All".equals(source)) {
                query += " AND source=?";
            }
            if (dest != null && !"All".equals(dest)) {
                query += " AND destination=?";
            }

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, flightType);
            int index = 2;
            if (source != null && !"All".equals(source)) {
                ps.setString(index++, source);
            }
            if (dest != null && !"All".equals(dest)) {
                ps.setString(index, dest);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("flight_number"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getString("date"),
                        rs.getInt("seats_available"),
                        rs.getString("type")
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // --- Book selected row ---
    private void bookSelectedFlight() {
        int selectedRow = flightTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a flight to book!");
            return;
        }

        String flightNo = (String) tableModel.getValueAt(selectedRow, 0);
        String source = (String) tableModel.getValueAt(selectedRow, 1);
        String dest = (String) tableModel.getValueAt(selectedRow, 2);
        String date = (String) tableModel.getValueAt(selectedRow, 3);
        int seats = (int) tableModel.getValueAt(selectedRow, 4);
        String type = (String) tableModel.getValueAt(selectedRow, 5);

        parent.showBookingPage(flightNo, source, dest, date, seats, type);
    }
}
