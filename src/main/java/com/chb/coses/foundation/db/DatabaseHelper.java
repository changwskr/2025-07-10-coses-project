package com.chb.coses.foundation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database helper class for database operations
 */
public class DatabaseHelper {

    private static final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/eplaton";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "password";

    private static String driver = DEFAULT_DRIVER;
    private static String url = DEFAULT_URL;
    private static String username = DEFAULT_USERNAME;
    private static String password = DEFAULT_PASSWORD;

    /**
     * Set database configuration
     * 
     * @param driver   database driver
     * @param url      database URL
     * @param username database username
     * @param password database password
     */
    public static void setConfiguration(String driver, String url, String username, String password) {
        DatabaseHelper.driver = driver;
        DatabaseHelper.url = url;
        DatabaseHelper.username = username;
        DatabaseHelper.password = password;
    }

    /**
     * Get database connection
     * 
     * @return database connection
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found: " + driver, e);
        }
    }

    /**
     * Close connection safely
     * 
     * @param connection connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Log error but don't throw
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Test database connection
     * 
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    private DatabaseHelper() {
        // Utility class - prevent instantiation
    }
}