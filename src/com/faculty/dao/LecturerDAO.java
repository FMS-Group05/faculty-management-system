package com.faculty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.faculty.util.DBConnection;

public class LecturerDAO {

    public Object[][] loadLecturers() throws SQLException {
        String sql = "SELECT Name, dpt, Ccode, email, mobile, userName FROM LDetails";
        return executeQuery(sql);
    }

    public List<String> getAllDepartments() throws SQLException {
        List<String> depts = new ArrayList<>();
        String sql = "SELECT dptN FROM Departments";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                depts.add(rs.getString("dptN"));
            }
        }
        return depts;
    }

    public List<String> getAllCourses() throws SQLException {
        List<String> courses = new ArrayList<>();
        String sql = "SELECT ccode FROM courses";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                courses.add(rs.getString("ccode"));
            }
        }
        return courses;
    }

    public boolean addLecturer(String username, String password, String name, String dept, String course, String email,
            String mobile) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Insert User
            String sqlUser = "INSERT INTO Users (userName, pws, Role) VALUES (?, ?, 'Lecturer')";
            try (PreparedStatement ps = con.prepareStatement(sqlUser)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.executeUpdate();
            }

            // 2. Insert Lecturer Details
            String sqlDetails = "INSERT INTO LDetails (userName, Name, dpt, Ccode, email, mobile) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sqlDetails)) {
                ps.setString(1, username);
                ps.setString(2, name);
                ps.setString(3, dept);
                ps.setString(4, course);
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

    public boolean updateLecturer(String username, String name, String dept, String course, String email, String mobile)
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
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteLecturer(String username) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // Delete from CourseLec if exists
            String sqlCourseLec = "DELETE FROM CourseLec WHERE Luser=?";
            try (PreparedStatement ps = con.prepareStatement(sqlCourseLec)) {
                ps.setString(1, username);
                ps.executeUpdate();
            }

            String sqlDetails = "DELETE FROM LDetails WHERE userName=?";
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
