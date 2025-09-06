package ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private LoginUI loginUI;
    private FlightTypeUI flightTypeUI;
    private MainUI mainUI;

    public MainWindow() {
        setTitle("Airline Booking System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initialize pages
        loginUI = new LoginUI(this);
        flightTypeUI = new FlightTypeUI(this);
        mainUI = new MainUI("", this); // initially empty type

        cardPanel.add(loginUI, "Login");
        cardPanel.add(flightTypeUI, "FlightType");
        cardPanel.add(mainUI, "Main");

        add(cardPanel);

        showLoginUI();
    }

    // --- Navigation methods ---
    public void showLoginUI() {
        cardLayout.show(cardPanel, "Login");
    }

    public void showFlightTypeUI() {
        cardLayout.show(cardPanel, "FlightType");
    }

    public void showMainUI(String flightType) {
        mainUI = new MainUI(flightType, this);
        cardPanel.add(mainUI, "Main");
        cardLayout.show(cardPanel, "Main");
    }

    public void showBookingPage(String flightNo, String source, String dest, String date, int seats, String type) {
        BookingUI bookingUI = new BookingUI(this, flightNo, source, dest, date, seats);
        cardPanel.add(bookingUI, "Booking");
        cardLayout.show(cardPanel, "Booking");
    }

    public void showTicketPage(String ticketText) {
        TicketUI ticketUI = new TicketUI(ticketText);
        cardPanel.add(ticketUI, "Ticket");
        cardLayout.show(cardPanel, "Ticket");
    }
}
