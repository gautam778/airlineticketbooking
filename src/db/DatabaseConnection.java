package db;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/airline_db";
    private static final String USER = "root";
    private static final String PASSWORD = "gautam";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Get all flights
    public static ArrayList<Object[]> getFlights() {
        ArrayList<Object[]> flights = new ArrayList<>();
        String query = "SELECT flight_number, source, destination, date, seats_available, type FROM flights";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("flight_number"),
                    rs.getString("source"),
                    rs.getString("destination"),
                    rs.getDate("date").toString(),
                    rs.getInt("seats_available"),
                    rs.getString("type")
                };
                flights.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
}
