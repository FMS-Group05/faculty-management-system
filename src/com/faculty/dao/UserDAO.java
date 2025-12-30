package com.faculty.dao;

import com.faculty.model.User;
import com.faculty.util.DBConnection;

import java.sql.*;

public class UserDAO {


    public User login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean register(User user) {

        String sql = "INSERT INTO users(username,password,role) VALUES(?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
