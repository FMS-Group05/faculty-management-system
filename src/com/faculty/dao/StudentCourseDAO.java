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
}
