# Airline Booking App

A Java Swing application with MySQL backend to book airline tickets.

## Features
- Login screen
- Domestic / International flight selection
- Filter by source/destination
- Booking form with passenger details
- Ticket generated and shown in-app and as `.txt`
- Stores bookings in MySQL `tickets` table

## Setup
1. Clone repository  
2. Add MySQL connector JAR to `lib/`  
3. Import SQL schema and sample data into `airline_db`  
4. Compile & run:
   ```sh
   cd src
   javac -cp ".;../lib/mysql-connector-j-9.0.0.jar" Main.java ui\*.java db\*.java model\*.java
   java -cp ".;../lib/mysql-connector-j-9.0.0.jar" Main
