package com.faculty.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
// Database credentials..
    private static final String URL = "jdbc:mysql://localhost:3306/login_db";
    private static final String USER = "root";
    private static final String PASS = "Ravindu22520";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found!");
        }
    }

}
