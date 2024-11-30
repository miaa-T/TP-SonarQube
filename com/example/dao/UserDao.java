package com.example.dao;

import com.example.entity.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {

    private Connection conn;
    private static final Logger logger = Logger.getLogger(UserDao.class.getName());

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    // Insert a user into the database
    public void insertUser(User user) {
        String sql = "INSERT INTO users(name, email, phone) VALUES (?, ?, ?);";

        // Use try-with-resources to automatically close the PreparedStatement
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.executeUpdate();
        } catch (SQLException se) {
            // Log SQL exceptions at SEVERE level
            logger.log(Level.SEVERE, "SQL Exception occurred while inserting user", se);
        } catch (Exception e) {
            // Log general exceptions at WARNING level
            logger.log(Level.WARNING, "An unexpected error occurred while inserting user", e);
        }
    }

    // Retrieve a user by their ID
    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM Users WHERE user_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                }
            }
        } catch (SQLException se) {
            // Log SQL exceptions at SEVERE level
            logger.log(Level.SEVERE, "SQL Exception occurred while fetching user by ID", se);
        } catch (Exception e) {
            // Log general exceptions at WARNING level
            logger.log(Level.WARNING, "An unexpected error occurred while fetching user", e);
        }

        return user;
    }
}
