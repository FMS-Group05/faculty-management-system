package com.faculty.dao;

import com.faculty.util.DBConnection;
import java.sql.*;

public class UserDAO {

    // REGISTER USER
    public boolean register(String username, String password, String role) {
        String sqlUser = "INSERT INTO Users (userName, pws, Role) VALUES (?, ?, ?)";
        String sqlDetails = null;

        if ("Student".equals(role)) {
            sqlDetails = "INSERT INTO SDetails (userName) VALUES (?)";
        } else if ("Lecturer".equals(role)) {
            sqlDetails = "INSERT INTO LDetails (userName) VALUES (?)";
        }

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // Start transaction

            // 1. Insert into Users table
            try (PreparedStatement psUser = con.prepareStatement(sqlUser)) {
                psUser.setString(1, username.trim());
                psUser.setString(2, password.trim());
                psUser.setString(3, role.trim());
                psUser.executeUpdate();
            }

            // 2. Insert into SDetails or LDetails table
            if (sqlDetails != null) {
                try (PreparedStatement psDetails = con.prepareStatement(sqlDetails)) {
                    psDetails.setString(1, username.trim());
                    psDetails.executeUpdate();
                }
            }

            con.commit(); // Save changes to both tables
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false; // username exists
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // LOGIN USER
    public String login(String username, String password) {
        String sql = "SELECT Role FROM Users WHERE userName=? AND pws=?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            ps.setString(2, password.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role"); // return role
                } else {
                    return null; // login failed
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getRole(String username, String password) {
        String sql = "SELECT Role FROM Users WHERE userName=? AND pws=?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            ps.setString(2, password.trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
