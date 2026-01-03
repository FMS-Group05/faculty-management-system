package com.faculty.controller;

import com.faculty.dao.StudentDetailsDAO;
import com.faculty.model.User;
import com.faculty.view.ProfileDetailsPanel;

import javax.swing.*;

public class StudentDetailsController {

    private final ProfileDetailsPanel view;
    private final StudentDetailsDAO dao = new StudentDetailsDAO();

    public StudentDetailsController(ProfileDetailsPanel view, User user) {
        this.view = view;

        loadProfile(user);
        initActions(user);
    }

    public void loadProfile(User user) {
        try {
            String[] values = dao.loadProfile(user.getUsername());

            // Null-safe
            for (int i = 0; i < values.length; i++) {
                if (values[i] == null)
                    values[i] = "";
            }

            view.setFields(values);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error loading profile: " + e.getMessage());
        }
    }

    private void initActions(User user) {
        view.getSaveButton().addActionListener(e -> {
            String[] values = view.getFieldValues();
            try {
                int rowsAffected = dao.updateProfile(user.getUsername(),
                        values[0], values[1], values[2], values[3], values[4]);

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(view, "Profile updated successfully");
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update profile");
                }
            } catch (java.sql.SQLException ex) {
                if (ex.getMessage().contains("foreign key constraint fails")) {
                    JOptionPane.showMessageDialog(view, "Invalid Degree: The specified degree does not exist.");
                } else {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Failed to update profile: " + ex.getMessage());
                }
            }
        });
    }
}
