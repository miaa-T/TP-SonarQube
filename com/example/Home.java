package com.example;

import com.DataInserter;
import com.example.dao.*;
import com.example.entity.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Logger;

public class Home {
    private static final Logger logger = Logger.getLogger(Home.class.getName());

    public static void main(String[] args) {
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            props.load(input);

            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            String jdbcDriver = props.getProperty("db.driver");
            String dbUrl = props.getProperty("db.url");

            DatabaseConnection db = new DatabaseConnection(user, password, jdbcDriver, dbUrl);
            Connection connection = db.connect();

            // Example usage of DataInserter
            DataInserter.insertParking(connection, "Parking 1", "Alger", 30);
            DataInserter.insertParkingPlace(connection, 1, 1, "F5", PlaceStatus.AVAILABLE);
            DataInserter.insertUser(connection, 1, "Ali", "055555555", "ali@gmail.com");

            // Insert a reservation
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            DataInserter.insertReservation(
                    connection,
                    1, // User ID
                    1, // Parking Place ID
                    "22-10-2023 10:00", // Start Time
                    "22-10-2023 11:45", // End Time
                    ReservationStatus.PENDING
            );

            logger.info("Data inserted successfully!");
        } catch (IOException e) {
            logger.severe("Error reading configuration file: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Error inserting data: " + e.getMessage());
        }
    }
}
