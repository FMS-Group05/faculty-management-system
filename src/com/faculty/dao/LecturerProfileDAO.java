package com.faculty.dao;

import com.faculty.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerProfileDAO {

    public String[] loadProfile(String username) throws SQLException {
        String sql = "SELECT Name, dpt, Ccode, email, mobile FROM LDetails WHERE userName=?";
        String[] values = new String[5];
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    values[0] = rs.getString("Name");
                    values[1] = rs.getString("dpt");
                    values[2] = rs.getString("Ccode");
                    values[3] = rs.getString("email");
                    values[4] = rs.getString("mobile");
                }
                for (int i = 0; i < values.length; i++) {
                    if (values[i] == null)
                        values[i] = "";
                }
                return values;
            }
        }
    }

    public int updateProfile(String username, String name, String dept, String course, String email, String mobile)
            throws SQLException {

        String sql = "UPDATE LDetails SET Name=?, dpt=?, Ccode=?, email=?, mobile=? WHERE userName=?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, dept);
            ps.setString(3, course);
            ps.setString(4, email);
            ps.setString(5, mobile);
            ps.setString(6, username);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                String insertSql = "INSERT INTO LDetails (userName, Name, dpt, Ccode, email, mobile) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps2 = con.prepareStatement(insertSql)) {
                    ps2.setString(1, username);
                    ps2.setString(2, name);
                    ps2.setString(3, dept);
                    ps2.setString(4, course);
                    ps2.setString(5, email);
                    ps2.setString(6, mobile);
                    return ps2.executeUpdate();
                }
            }
            return rows;
        }
    }

    public List<String> getAllDepartmentCodes() throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT dptN FROM Departments";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("dptN"));
            }
        }
        return list;
    }

    public List<String> getAllCourseCodes() throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT ccode FROM courses";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("ccode"));
            }
        }
        return list;
    }
}
