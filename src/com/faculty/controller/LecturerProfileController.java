package com.faculty.controller;

import com.faculty.dao.LecturerProfileDAO;
import com.faculty.model.User;
import com.faculty.view.LecturerProfilePanel;

import javax.swing.*;
import java.sql.SQLException;

public class LecturerProfileController {

    private final LecturerProfilePanel view;
    private final LecturerProfileDAO dao;
    private final User user;

    public LecturerProfileController(LecturerProfilePanel view, User user) {
        this.view = view;
        this.user = user;
        this.dao = new LecturerProfileDAO();

        loadProfileData();
        initController();
    }

    private void loadProfileData() {
        try {
            String[] values = dao.loadProfile(user.getUsername());
            view.setFields(values);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error loading profile: " + e.getMessage());
        }
    }

    private void initController() {
        view.getSaveButton().addActionListener(e -> saveProfile());
    }

    private void saveProfile() {
        String[] values = view.getFieldValues();
        // values map: [0]=Name, [1]=Dept, [2]=Course, [3]=Email, [4]=Mobile

        try {
            int rows = dao.updateProfile(user.getUsername(), values[0], values[1], values[2], values[3], values[4]);
            if (rows > 0) {
                JOptionPane.showMessageDialog(view, "Profile saved successfully!");
            } else {
                JOptionPane.showMessageDialog(view, "Failed to save profile.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error saving profile: " + ex.getMessage());
        }
    }
}
