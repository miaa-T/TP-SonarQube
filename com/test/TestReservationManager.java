package com.test;

import com.DataInserter;
import com.example.entity.*;
import com.example.dao.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class TestReservationManager {
    private static final Logger logger = Logger.getLogger(TestReservationManager.class.getName());

    public static void main(String[] args) {
        try {
            // Setup database connection
            DatabaseConnection db = new DatabaseConnection("dbUser", "dbPassword", "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/parkingdb");
            Connection connection = db.connect();

            // Insert data for testing
            DataInserter.insertParking(connection, "Test Parking", "Test Address", 50);
            DataInserter.insertParkingPlace(connection, 1, 1, "T1", PlaceStatus.AVAILABLE);
            DataInserter.insertUser(connection, 1, "Test User", "0123456789", "testuser@example.com");

            // Create a test reservation
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            DataInserter.insertReservation(
                    connection,
                    1, // User ID
                    1, // Parking Place ID
                    "01-12-2024 09:00", // Start Time
                    "01-12-2024 10:30", // End Time
                    ReservationStatus.CONFIRMED
            );

            logger.info("Test data inserted successfully!");
        } catch (Exception e) {
            logger.severe("Error during test data insertion: " + e.getMessage());
        }
    }
}
