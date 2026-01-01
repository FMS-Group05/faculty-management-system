package com.faculty.dao;

import com.faculty.util.DBConnection;
import java.sql.*;

public class UserDAO {

    // REGISTER USER
    public boolean register(String username, String password, String role) {
        String sql = "INSERT INTO Users (userName, pws, Role) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            ps.setString(2, password.trim());
            ps.setString(3, role.trim());
            ps.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            return false; // username exists
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
            if (rs.next()) return rs.getString("role");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
