package com.faculty.controller;

import com.faculty.dao.StudentDAO;
import com.faculty.model.Student;
import com.faculty.view.*;

import javax.swing.*;

public class StudentController {
    private final StudentDashboardView view;
    private final StudentDAO dao = new StudentDAO();

    public StudentController(StudentDashboardView view) {
        this.view = view;
        try {
            String[] values = dao.loadProfile(view.getUser().getUsername());
            view.getProfilePanel().setFields(values);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error loading profile: " + e.getMessage());
        }
        initActions();
    }

    private void initActions() {
        view.getProfilePanel().getSaveButton().addActionListener(e -> {
            JTextField[] fields = view.getProfilePanel().getFields();
            String[] values = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                values[i] = fields[i].getText();
            }
            try {
                int rowsAffected = dao.updateProfile(view.getUser().getUsername(), values[0], values[1], values[2], values[3], values[4]);
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
                    JOptionPane.showMessageDialog(view, "Failed to update profile");
                }
            }
        });
    }
}