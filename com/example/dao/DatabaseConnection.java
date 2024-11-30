package com.example.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DatabaseConnection {
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    private String USER;
    private String PASSWORD;
    private String JDBC_DRIVER;
    private String DB_URL;


    public DatabaseConnection(String USER, String PASSWORD, String JDBC_DRIVER, String DB_URL) {
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.JDBC_DRIVER = JDBC_DRIVER;
        this.DB_URL = DB_URL;
    }

    public  Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Class.forName(JDBC_DRIVER);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred while connecting to the database", e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "JDBC Driver not found", e);
        }
        return conn;
    }

    public  void disconnect(Connection conn ) {
        try {
            conn.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred while closing the connection", e);
        }
    }

    public void createDb(Connection conn) {
        // Using try-with-resources for Statement to ensure it gets closed
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(Scripts.sql);
        } catch (SQLException se) {
            // Log SQL exceptions at SEVERE level
            logger.log(Level.SEVERE, "SQL Exception occurred while creating the database", se);
        } catch (Exception e) {
            // Log general exceptions at WARNING level
            logger.log(Level.WARNING, "An unexpected error occurred", e);
        }
    }
}


