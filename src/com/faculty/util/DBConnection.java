package com.faculty.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String FULL_URL = "jdbc:mysql://group5.mysql.database.azure.com:3306/fms?useSSL=true";
    private static final String USER = "fms";
    private static final String PASSWORD = "fms@12345";


    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(FULL_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found!", e);
        }
    }
}
