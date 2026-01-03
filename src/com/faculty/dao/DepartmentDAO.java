package com.faculty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.faculty.util.DBConnection;

public class DepartmentDAO {

    public Object[][] loadDepartments() throws SQLException {
        String sql = "SELECT d.name, d.HOD, dd.dgree, d.noOfStaf, d.dptN " +
                "FROM Departments d " +
                "LEFT JOIN deptDegrees dd ON d.dptN = dd.dptN";
        return executeQuery(sql);
    }

    public boolean addDepartment(String dptN, String name, String hod, int noOfStaf) {
        String sql = "INSERT INTO Departments (dptN, name, HOD, noOfStaf) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dptN);
            ps.setString(2, name);
            ps.setString(3, hod);
            ps.setInt(4, noOfStaf);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDepartment(String dptN, String name, String hod, int noOfStaf) {
        String sql = "UPDATE Departments SET name=?, HOD=?, noOfStaf=? WHERE dptN=?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, hod);
            ps.setInt(3, noOfStaf);
            ps.setString(4, dptN);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDepartment(String dptN) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Handle dependencies

            // deptDegrees
            String sqlDeptDegree = "DELETE FROM deptDegrees WHERE dptN=?";
            try (PreparedStatement ps = con.prepareStatement(sqlDeptDegree)) {
                ps.setString(1, dptN);
                ps.executeUpdate();
            }
            
            String sqlLec = "UPDATE LDetails SET dpt=NULL WHERE dpt=?";
            try (PreparedStatement ps = con.prepareStatement(sqlLec)) {
                ps.setString(1, dptN);
                ps.executeUpdate();
            }

            // 2. Delete Department
            String sqlDept = "DELETE FROM Departments WHERE dptN=?";
            try (PreparedStatement ps = con.prepareStatement(sqlDept)) {
                ps.setString(1, dptN);
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
