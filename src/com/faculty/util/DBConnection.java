package com.faculty.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String BASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "FMS";
    private static final String FULL_URL = BASE_URL + DB_NAME;
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // private static final String URL =
    //         "jdbc:mysql://localhost:3306/login_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    // private static final String USER = "root";
    // private static final String PASSWORD = "Ravindu22520";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            ResultSet resultSet = conn.getMetaData().getCatalogs();
            boolean dbExists = false;
            while (resultSet.next()) {
                if (DB_NAME.equalsIgnoreCase(resultSet.getString(1))) {
                    dbExists = true;
                    break;
                }
            }

            if (!dbExists) {
                System.out.println("Database '" + DB_NAME + "' not found. Initializing...");
                executeSchema(conn);
            } else {
                System.out.println("Database '" + DB_NAME + "' exists.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeSchema(Connection conn) throws SQLException, IOException {
        File schemaFile = new File("database/schema.sql");
        if (!schemaFile.exists()) {
            System.err.println("Schema file not found at: " + schemaFile.getAbsolutePath());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(schemaFile));
                Statement statement = conn.createStatement()) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip comments and empty lines
                if (line.isEmpty() || line.startsWith("--"))
                    continue;

                sql.append(line);
                if (line.endsWith(";")) {
                    String command = sql.toString();
                    // Remove semicolon at the end
                    command = command.substring(0, command.length() - 1);
                    System.out.println("Executing: " + command);
                    try {
                        statement.execute(command);
                    } catch (SQLException e) {
                        System.err.println("Error executing: " + command);
                        e.printStackTrace();
                    }
                    sql.setLength(0);
                } else {
                    sql.append(" ");
                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(FULL_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found!", e);
        }
    }
}
