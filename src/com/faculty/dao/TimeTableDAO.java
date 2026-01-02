package com.faculty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.faculty.util.DBConnection;

public class TimeTableDAO {

    public Object[][] loadTimeTables() throws SQLException {
        // SELECT degree, year, ImagePath FROM TimeTables
        String sql = "SELECT degree, year, ImagePath FROM TimeTables ORDER BY degree, year";
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

    public boolean addTimeTable(String degree, int year) {
        String sql = "INSERT INTO TimeTables (degree, year, ImagePath) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, degree);
            ps.setInt(2, year);
            ps.setString(3, ""); // Initial empty image path
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTimeTableImage(String degree, int year, String imagePath) {
        String sql = "UPDATE TimeTables SET ImagePath=? WHERE degree=? AND year=?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, imagePath);
            ps.setString(2, degree);
            ps.setInt(3, year);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTimeTable(String degree, int year) {
        String sql = "DELETE FROM TimeTables WHERE degree=? AND year=?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, degree);
            ps.setInt(2, year);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getImagePath(String degree, int year) {
        String sql = "SELECT ImagePath FROM TimeTables WHERE degree=? AND year=?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, degree);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("ImagePath");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
