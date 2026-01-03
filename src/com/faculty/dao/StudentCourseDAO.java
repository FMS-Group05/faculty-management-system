package com.faculty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.faculty.util.DBConnection;

public class StudentCourseDAO {

    public Object[][] loadCourseEnrolled(String username) throws SQLException {
        String sql = "SELECT c.ccode, c.cname, c.credits, e.grade " +
                "FROM courses c " +
                "JOIN EnrolledCourses e ON c.ccode = e.Ccode " +
                "WHERE e.Uname = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                int columns = md.getColumnCount();
                java.util.List<Object[]> rows = new java.util.ArrayList<>();
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

    public boolean enrollCourse(String username, String ccode) throws SQLException {
        String sql = "INSERT INTO EnrolledCourses (Uname, Ccode, grade) VALUES (?, ?, '')";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, ccode);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean unenrollCourse(String username, String ccode) throws SQLException {
        String sql = "DELETE FROM EnrolledCourses WHERE Uname = ? AND Ccode = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, ccode);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateGrade(String username, String ccode, String grade) throws SQLException {
        String sql = "UPDATE EnrolledCourses SET grade = ? WHERE Uname = ? AND Ccode = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, grade);
            ps.setString(2, username);
            ps.setString(3, ccode);
            return ps.executeUpdate() > 0;
        }
    }

    public java.util.List<String> getAllCourseCodes() throws SQLException {
        String sql = "SELECT ccode, cname FROM courses";
        java.util.List<String> codes = new java.util.ArrayList<>();
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                codes.add(rs.getString("ccode") + " - " + rs.getString("cname"));
            }
        }
        return codes;
    }
}
