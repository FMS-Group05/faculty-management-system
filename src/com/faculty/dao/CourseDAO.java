package com.faculty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.faculty.util.DBConnection;

public class CourseDAO {

    public Object[][] loadCourses() throws SQLException {
        // Left join to get lecturer name if assigned, otherwise null
        // Columns: Code, Name, Credits, Lecturer Name
        String sql = "SELECT c.ccode, c.cname, c.credits, l.Name " +
                "FROM courses c " +
                "LEFT JOIN LDetails l ON c.ccode = l.Ccode";
        return executeQuery(sql);
    }

    public boolean addCourse(String ccode, String cname, int credits) {
        String sql = "INSERT INTO courses (ccode, cname, credits) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ccode);
            ps.setString(2, cname);
            ps.setInt(3, credits);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCourse(String ccode, String cname, int credits) {
        // Note: We are not allowing updating ccode as it is PK and used elsewhere.
        String sql = "UPDATE courses SET cname=?, credits=? WHERE ccode=?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cname);
            ps.setInt(2, credits);
            ps.setString(3, ccode);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCourse(String ccode) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // Set Ccode to null in LDetails (if any lecturer teaches this)
            String sqlLec = "UPDATE LDetails SET Ccode=NULL WHERE Ccode=?";
            try (PreparedStatement ps = con.prepareStatement(sqlLec)) {
                ps.setString(1, ccode);
                ps.executeUpdate();
            }

            // Delete from CourseLec (Junction table)
            String sqlCourseLec = "DELETE FROM CourseLec WHERE ccode=?";
            try (PreparedStatement ps = con.prepareStatement(sqlCourseLec)) {
                ps.setString(1, ccode);
                ps.executeUpdate();
            }

            // Delete from EnrolledCourses
            String sqlEnrolled = "DELETE FROM EnrolledCourses WHERE Ccode=?";
            try (PreparedStatement ps = con.prepareStatement(sqlEnrolled)) {
                ps.setString(1, ccode);
                ps.executeUpdate();
            }

            // Delete from courses
            String sqlCourse = "DELETE FROM courses WHERE ccode=?";
            try (PreparedStatement ps = con.prepareStatement(sqlCourse)) {
                ps.setString(1, ccode);
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
