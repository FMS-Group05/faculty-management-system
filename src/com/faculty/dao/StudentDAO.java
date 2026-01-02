package com.faculty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.faculty.util.DBConnection;

public class StudentDAO {

    public Object[][] loadStudentPanel() throws SQLException {
        // Includes username at the end for hidden column
        String sql = "SELECT Name, STDID, degree, email, mobile, userName FROM SDetails";
        return executeQuery(sql);
    }

    public List<String> getAllDegrees() throws SQLException {
        List<String> degrees = new ArrayList<>();
        String sql = "SELECT degree FROM Degrees";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                degrees.add(rs.getString("degree"));
            }
        }
        return degrees;
    }

    public boolean addStudent(String username, String password, String name, String stdId, String degree, String email,
            String mobile) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Insert User
            String sqlUser = "INSERT INTO Users (userName, pws, Role) VALUES (?, ?, 'Student')";
            try (PreparedStatement ps = con.prepareStatement(sqlUser)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.executeUpdate();
            }

            // 2. Insert Student Details
            String sqlDetails = "INSERT INTO SDetails (userName, Name, STDID, degree, email, mobile) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sqlDetails)) {
                ps.setString(1, username);
                ps.setString(2, name);
                ps.setString(3, stdId);
                ps.setString(4, degree);
                ps.setString(5, email);
                ps.setString(6, mobile);
                ps.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
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

    public boolean updateStudent(String username, String name, String stdId, String degree, String email, String mobile)
            throws SQLException {
        String sql = "UPDATE SDetails SET Name=?, STDID=?, degree=?, email=?, mobile=? WHERE userName=?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, stdId);
            ps.setString(3, degree);
            ps.setString(4, email);
            ps.setString(5, mobile);
            ps.setString(6, username);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteStudent(String username) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // Delete enrolled courses first (to be safe if no cascade)
            String sqlEnrolled = "DELETE FROM EnrolledCourses WHERE Uname=?";
            try (PreparedStatement ps = con.prepareStatement(sqlEnrolled)) {
                ps.setString(1, username);
                ps.executeUpdate();
            }

            String sqlDetails = "DELETE FROM SDetails WHERE userName=?";
            try (PreparedStatement ps = con.prepareStatement(sqlDetails)) {
                ps.setString(1, username);
                ps.executeUpdate();
            }

            // Delete from Users
            String sqlUser = "DELETE FROM Users WHERE userName=?";
            try (PreparedStatement ps = con.prepareStatement(sqlUser)) {
                ps.setString(1, username);
                ps.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
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

    private Object[][] executeQuery(String sql) throws SQLException {
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            List<Object[]> rows = new ArrayList<>();

            while (rs.next()) {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                rows.add(row);
            }
            return rows.toArray(new Object[0][]);
        }
    }
}
