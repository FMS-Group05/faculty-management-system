package com.faculty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.faculty.util.DBConnection;

public class DegreeDAO {

    public Object[][] loadDegrees() throws SQLException {
        // Degree, Department, No of Students
        // Degree table has NoOfStd. Department comes from deptDegrees.
        String sql = "SELECT d.degree, dep.name, d.NoOfStd " +
                "FROM Degrees d " +
                "LEFT JOIN deptDegrees dd ON d.degree = dd.dgree " +
                "LEFT JOIN Departments dep ON dd.dptN = dep.dptN";
        return executeQuery(sql);
    }

    public List<String> getAllDepartments() throws SQLException {
        List<String> depts = new ArrayList<>();
        String sql = "SELECT dptN FROM Departments"; // Or name? deptDegrees uses dptN. UI usually shows Name?
        // But for insertion into deptDegrees we need dptN.
        // Let's return dptN for simplicity or implement a map if needed.
        // Given previous dropdowns used Names (but codes are stored)?
        // StudentDAO `getAllDegrees` returned degree names (which are PKs).
        // DeptDAO uses dptN as PK but name as display.
        // For simplicity let's use dptN (Code) in dropdown or Name if we look it up.
        // Let's use dptN for now as it's the FK.
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                depts.add(rs.getString("dptN"));
            }
        }
        return depts;
    }

    public boolean addDegree(String degreeName, String deptCode, int noOfStd) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Insert into Degrees
            String sqlDegree = "INSERT INTO Degrees (degree, NoOfStd) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sqlDegree)) {
                ps.setString(1, degreeName);
                ps.setInt(2, noOfStd);
                ps.executeUpdate();
            }

            // 2. Insert into deptDegrees
            if (deptCode != null && !deptCode.isEmpty()) {
                String sqlLink = "INSERT INTO deptDegrees (dptN, dgree) VALUES (?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sqlLink)) {
                    ps.setString(1, deptCode);
                    ps.setString(2, degreeName);
                    ps.executeUpdate();
                }
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

    public boolean updateDegree(String degreeName, String deptCode, int noOfStd) {
        // DegreeName is PK, so we update NoOfStd and Department link.
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Update Degree info
            String sqlDegree = "UPDATE Degrees SET NoOfStd=? WHERE degree=?";
            try (PreparedStatement ps = con.prepareStatement(sqlDegree)) {
                ps.setInt(1, noOfStd);
                ps.setString(2, degreeName);
                ps.executeUpdate();
            }

            // 2. Update Dept Link (Delete old and Insert new seems safest for M:N but here
            // simplistic 1:1 view)
            // Or UPDATE if exists.
            // Let's DELETE for this degree then INSERT.
            String sqlDelLink = "DELETE FROM deptDegrees WHERE dgree=?";
            try (PreparedStatement ps = con.prepareStatement(sqlDelLink)) {
                ps.setString(1, degreeName);
                ps.executeUpdate();
            }

            if (deptCode != null && !deptCode.isEmpty()) {
                String sqlLink = "INSERT INTO deptDegrees (dptN, dgree) VALUES (?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sqlLink)) {
                    ps.setString(1, deptCode);
                    ps.setString(2, degreeName);
                    ps.executeUpdate();
                }
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

    public boolean deleteDegree(String degreeName) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Delete from deptDegrees
            String sqlLink = "DELETE FROM deptDegrees WHERE dgree=?";
            try (PreparedStatement ps = con.prepareStatement(sqlLink)) {
                ps.setString(1, degreeName);
                ps.executeUpdate();
            }

            // 2. Check/Delete Students with this degree?
            // SDetails has `degree` FK. Simple update of FK to NULL?
            // Foreign Key might prevent deletion.
            // Update SDetails set degree=NULL
            String sqlStud = "UPDATE SDetails SET degree=NULL WHERE degree=?";
            try (PreparedStatement ps = con.prepareStatement(sqlStud)) {
                ps.setString(1, degreeName);
                ps.executeUpdate();
            }

            // 3. Delete from Degrees
            String sqlDegree = "DELETE FROM Degrees WHERE degree=?";
            try (PreparedStatement ps = con.prepareStatement(sqlDegree)) {
                ps.setString(1, degreeName);
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
