package com.faculty.dao;

import com.faculty.util.DBConnection;
import java.sql.*;

public class StudentDAO {
    String sql = "UPDATE SDetails SET Name=?, STDID=?, degree=?, email=?, mobile=? WHERE userName=?";

    public int updateProfile(String username, String fullName, String studentId, String degree, String email,
            String mobileNumber) throws SQLException {
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, studentId);
            ps.setString(3, degree);
            ps.setString(4, email);
            ps.setString(5, mobileNumber);
            ps.setString(6, username);
            return ps.executeUpdate();
        }
    }

    public String[] loadProfile(String username) throws SQLException {
        String sql = "SELECT * FROM SDetails WHERE userName=?";
        String[] values = new String[5];
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    values[0] = rs.getString("Name");
                    values[1] = rs.getString("STDID");
                    values[2] = rs.getString("degree");
                    values[3] = rs.getString("email");
                    values[4] = rs.getString("mobile");
                }
                return values;
            }
        }
    }
}
