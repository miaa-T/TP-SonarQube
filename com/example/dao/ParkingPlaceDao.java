package com.example.dao;

import com.example.entity.ParkingPlace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParkingPlaceDao {

    private Connection conn;
    private static final Logger logger = Logger.getLogger(ParkingPlaceDao.class.getName());

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void insertParkingPlace(ParkingPlace parkingPlace) {
        String sql = "INSERT INTO parking_places(place_name, place_status, parking_id) VALUES (?, ?, ?);";

        // Use try-with-resources to automatically close the PreparedStatement
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, parkingPlace.getPlaceName());
            pstmt.setString(2, parkingPlace.getPlaceStatus().name()); // Assuming PlaceStatus is an enum
            pstmt.setInt(3, parkingPlace.getParking().getParkingId()); // Assuming Parking has a getParkingId method
            pstmt.executeUpdate();

        } catch (SQLException se) {
            // Log SQL exceptions at SEVERE level
            logger.log(Level.SEVERE, "SQL Exception occurred while inserting parking place", se);
        } catch (Exception e) {
            // Log general exceptions at WARNING level
            logger.log(Level.WARNING, "An unexpected error occurred", e);
        }
    }
}
