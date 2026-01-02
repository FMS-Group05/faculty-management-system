package com.faculty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.faculty.util.DBConnection;

public class AdminPanelsDAO {

    public Object[][] loadStudentPanel() throws SQLException {
        // Includes username at the end for hidden column
        String sql = "SELECT Name, STDID, degree, email, mobile, userName FROM SDetails";
        return executeQuery(sql);
    }

    public Object[][] loadLecturers() throws SQLException {
        String sql = "SELECT Name, dpt, Ccode, email, mobile FROM LDetails";
        return executeQuery(sql);
    }

    public Object[][] loadCourses() throws SQLException {
        // Left join to get lecturer name if assigned, otherwise null
        String sql = "SELECT c.ccode, c.cname, c.credits, l.Name " +
                "FROM courses c " +
                "LEFT JOIN LDetails l ON c.ccode = l.Ccode";
        return executeQuery(sql);
    }

    public Object[][] loadDepartments() throws SQLException {
        // Departments table has basic info.
        // HOD is just a string name in the table schema provided.
        // For 'Degree', we need to check deptDegrees table.
        // Note: A department might offer multiple degrees, but the UI table design
        // implies one line
        // per department or maybe we just show one or aggregate.
        // For simplicity based on current UI "Degree" column, let's just pick one or
        // group.
        // But looking at UI mock data {"Applied Computing", "Kumar Sanga", "Engineering
        // Technology", "15"}
        // It seems to expect: Name, HOD, Degree(s), NoOfStaff.
        // Let's do a basic join.
        String sql = "SELECT d.name, d.HOD, dd.dgree, d.noOfStaf " +
                "FROM Departments d " +
                "LEFT JOIN deptDegrees dd ON d.dptN = dd.dptN";
        return executeQuery(sql);
    }

    public Object[][] loadDegrees() throws SQLException {
        // UI Expects: Degree, Department, No of Students
        // We join Degrees -> deptDegrees -> Departments to get Dept Name.
        String sql = "SELECT deg.degree, d.name, deg.NoOfStd " +
                "FROM Degrees deg " +
                "LEFT JOIN deptDegrees dd ON deg.degree = dd.dgree " +
                "LEFT JOIN Departments d ON dd.dptN = d.dptN";
        return executeQuery(sql);
    }

    private Object[][] executeQuery(String sql) throws SQLException {
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

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

    // ---------- Student CRUD Operations ----------

    public java.util.List<String> getAllDegrees() throws SQLException {
        java.util.List<String> degrees = new java.util.ArrayList<>();
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

            // 1. Delete from SDetails
            // Note: If you have other tables like CourseEnrolled referencing this user,
            // you might need to delete from those first or use CASCADE in DB.
            // Assuming for now simple delete logic or DB handles cascade.

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

            // 2. Delete from Users
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
}
