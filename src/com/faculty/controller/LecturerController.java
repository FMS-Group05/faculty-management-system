package com.faculty.controller;

import com.faculty.dao.LecturerDAO;
import com.faculty.view.AdminDashboardView;
import javax.swing.*;
import java.util.List;

public class LecturerController {

    private final AdminDashboardView view;
    private final LecturerDAO dao;

    public LecturerController(AdminDashboardView view) {
        this.view = view;
        this.dao = new LecturerDAO();
        initActions();
        refreshPanel();
    }

    private void initActions() {
        view.getLecturerPanel().getAddButton().addActionListener(e -> addLecturer());
        view.getLecturerPanel().getEditButton().addActionListener(e -> editLecturer());
        view.getLecturerPanel().getDeleteButton().addActionListener(e -> deleteLecturer());
    }

    private void addLecturer() {
        try {
            List<String> depts = dao.getAllDepartments();
            List<String> courses = dao.getAllCourses();
            JComboBox<String> deptCombo = new JComboBox<>(depts.toArray(new String[0]));
            JComboBox<String> courseCombo = new JComboBox<>(courses.toArray(new String[0]));

            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField mobileField = new JTextField();

            Object[] message = {
                    "Username:", usernameField,
                    "Password:", passwordField,
                    "Full Name:", nameField,
                    "Department:", deptCombo,
                    "Course:", courseCombo,
                    "Email:", emailField,
                    "Mobile Number:", mobileField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Add New Lecturer", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                if (dao.addLecturer(
                        usernameField.getText(),
                        new String(passwordField.getPassword()),
                        nameField.getText(),
                        (String) deptCombo.getSelectedItem(),
                        (String) courseCombo.getSelectedItem(),
                        emailField.getText(),
                        mobileField.getText())) {
                    JOptionPane.showMessageDialog(view, "Lecturer added successfully!");
                    refreshPanel();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add lecturer. Username might already exist.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void editLecturer() {
        String username = view.getLecturerPanel().getSelectedUsername();
        if (username == null) {
            JOptionPane.showMessageDialog(view, "Please select a lecturer to edit.");
            return;
        }

        try {
            JTable table = view.getLecturerPanel().getLecturerTable();
            int viewRow = table.getSelectedRow();

            Object nameObj = table.getValueAt(viewRow, 0);
            Object deptObj = table.getValueAt(viewRow, 1);
            Object courseObj = table.getValueAt(viewRow, 2);
            Object emailObj = table.getValueAt(viewRow, 3);
            Object mobileObj = table.getValueAt(viewRow, 4);

            String currentName = nameObj != null ? nameObj.toString() : "";
            String currentDept = deptObj != null ? deptObj.toString() : "";
            String currentCourse = courseObj != null ? courseObj.toString() : "";
            String currentEmail = emailObj != null ? emailObj.toString() : "";
            String currentMobile = mobileObj != null ? mobileObj.toString() : "";

            List<String> depts = dao.getAllDepartments();
            List<String> courses = dao.getAllCourses();
            JComboBox<String> deptCombo = new JComboBox<>(depts.toArray(new String[0]));
            JComboBox<String> courseCombo = new JComboBox<>(courses.toArray(new String[0]));

            deptCombo.setSelectedItem(currentDept);
            courseCombo.setSelectedItem(currentCourse);

            JTextField nameField = new JTextField(currentName);
            JTextField emailField = new JTextField(currentEmail);
            JTextField mobileField = new JTextField(currentMobile);

            Object[] message = {
                    "Username: " + username,
                    "Full Name:", nameField,
                    "Department:", deptCombo,
                    "Course:", courseCombo,
                    "Email:", emailField,
                    "Mobile Number:", mobileField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Edit Lecturer", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                if (dao.updateLecturer(
                        username,
                        nameField.getText(),
                        (String) deptCombo.getSelectedItem(),
                        (String) courseCombo.getSelectedItem(),
                        emailField.getText(),
                        mobileField.getText())) {
                    JOptionPane.showMessageDialog(view, "Lecturer updated successfully!");
                    refreshPanel();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update lecturer.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void deleteLecturer() {
        String username = view.getLecturerPanel().getSelectedUsername();
        if (username == null) {
            JOptionPane.showMessageDialog(view, "Please select a lecturer to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete lecturer '" + username + "'?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteLecturer(username)) {
                JOptionPane.showMessageDialog(view, "Lecturer deleted successfully!");
                refreshPanel();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete lecturer.");
            }
        }
    }

    public void refreshPanel() {
        try {
            Object[][] values = dao.loadLecturers();
            view.getLecturerPanel().setData(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
