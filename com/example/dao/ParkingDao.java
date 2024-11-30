package com.example.dao;

import com.example.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParkingDao {

    private Connection conn;
    private static final Logger logger = Logger.getLogger(ParkingDao.class.getName());

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void insertParking(Parking parking) {
        // Use try-with-resources to automatically close the PreparedStatement
        String sql = "INSERT INTO parkings(name, address, capacity) VALUES (?, ?, ?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, parking.getName());
            pstmt.setString(2, parking.getAddress());
            pstmt.setInt(3, parking.getCapacity());

            pstmt.executeUpdate();

        } catch (SQLException se) {
            // Log SQL exceptions at SEVERE level
            logger.log(Level.SEVERE, "SQL Exception occurred while inserting parking", se);
        } catch (Exception e) {
            // Log general exceptions at WARNING level
            logger.log(Level.WARNING, "An unexpected error occurred", e);
        }
    }
}
