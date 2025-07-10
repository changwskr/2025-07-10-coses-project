package com.chb.coses.foundation.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Database service class for database operations
 */
public class DBService {

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
        DBService.driver = driver;
        DBService.url = url;
        DBService.username = username;
        DBService.password = password;
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
            return DatabaseHelper.getConnection();
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found: " + driver, e);
        }
    }

    /**
     * Execute query
     * 
     * @param sql    SQL query
     * @param params query parameters
     * @return ResultSet
     * @throws SQLException if query fails
     */
    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        return stmt.executeQuery();
    }

    /**
     * Execute update
     * 
     * @param sql    SQL update
     * @param params update parameters
     * @return number of affected rows
     * @throws SQLException if update fails
     */
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeUpdate();
        }
    }

    /**
     * Close connection safely
     * 
     * @param connection connection to close
     */
    public static void closeConnection(Connection connection) {
        DatabaseHelper.closeConnection(connection);
    }

    /**
     * Test database connection
     * 
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        return DatabaseHelper.testConnection();
    }
}