package model;

public class Flight {
    private int id;
    private String flightNumber;
    private String source;
    private String destination;
    private String date;
    private int seatsAvailable;

    public Flight(int id, String flightNumber, String source, String destination, String date, int seatsAvailable) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.seatsAvailable = seatsAvailable;
    }

    public int getId() { return id; }
    public String getFlightNumber() { return flightNumber; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getDate() { return date; }
    public int getSeatsAvailable() { return seatsAvailable; }

    @Override
    public String toString() {
        return flightNumber + " | " + source + " → " + destination + " | " + date + " | Seats: " + seatsAvailable;
    }
}
