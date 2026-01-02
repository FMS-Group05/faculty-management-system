package com.faculty.controller;

import com.faculty.dao.StudentDAO;
import com.faculty.view.AdminDashboardView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StudentController {

    private final AdminDashboardView view;
    private final StudentDAO dao;

    public StudentController(AdminDashboardView view) {
        this.view = view;
        this.dao = new StudentDAO();
        initActions();
        refreshStudentPanel();
    }

    private void initActions() {
        view.getStudentPanel().getAddButton().addActionListener(e -> addStudent());
        view.getStudentPanel().getEditButton().addActionListener(e -> editStudent());
        view.getStudentPanel().getDeleteButton().addActionListener(e -> deleteStudent());
    }

    private void addStudent() {
        try {
            List<String> degrees = dao.getAllDegrees();
            JComboBox<String> degreeCombo = new JComboBox<>(degrees.toArray(new String[0]));

            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            JTextField nameField = new JTextField();
            JTextField stdIdField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField mobileField = new JTextField();

            Object[] message = {
                    "Username:", usernameField,
                    "Password:", passwordField,
                    "Full Name:", nameField,
                    "Student ID:", stdIdField,
                    "Degree:", degreeCombo,
                    "Email:", emailField,
                    "Mobile Number:", mobileField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                if (dao.addStudent(
                        usernameField.getText(),
                        new String(passwordField.getPassword()),
                        nameField.getText(),
                        stdIdField.getText(),
                        (String) degreeCombo.getSelectedItem(),
                        emailField.getText(),
                        mobileField.getText())) {
                    JOptionPane.showMessageDialog(view, "Student added successfully!");
                    refreshStudentPanel();
                } else {
                    JOptionPane.showMessageDialog(view,
                            "Failed to add student. Username or Student ID might already exist.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void editStudent() {
        String username = view.getStudentPanel().getSelectedUsername();
        if (username == null) {
            JOptionPane.showMessageDialog(view, "Please select a student to edit.");
            return;
        }

        try {
            JTable table = view.getStudentPanel().getStudentTable();
            int viewRow = table.getSelectedRow();

            Object nameObj = table.getValueAt(viewRow, 0);
            Object stdIdObj = table.getValueAt(viewRow, 1);
            Object degreeObj = table.getValueAt(viewRow, 2);
            Object emailObj = table.getValueAt(viewRow, 3);
            Object mobileObj = table.getValueAt(viewRow, 4);

            String currentName = nameObj != null ? nameObj.toString() : "";
            String currentStdId = stdIdObj != null ? stdIdObj.toString() : "";
            String currentDegree = degreeObj != null ? degreeObj.toString() : "";
            String currentEmail = emailObj != null ? emailObj.toString() : "";
            String currentMobile = mobileObj != null ? mobileObj.toString() : "";

            List<String> degrees = dao.getAllDegrees();
            JComboBox<String> degreeCombo = new JComboBox<>(degrees.toArray(new String[0]));
            degreeCombo.setSelectedItem(currentDegree);

            JTextField nameField = new JTextField(currentName);
            JTextField stdIdField = new JTextField(currentStdId);
            JTextField emailField = new JTextField(currentEmail);
            JTextField mobileField = new JTextField(currentMobile);

            Object[] message = {
                    "Username: " + username, // Read-only label
                    "Full Name:", nameField,
                    "Student ID:", stdIdField,
                    "Degree:", degreeCombo,
                    "Email:", emailField,
                    "Mobile Number:", mobileField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Edit Student", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                if (dao.updateStudent(
                        username,
                        nameField.getText(),
                        stdIdField.getText(),
                        (String) degreeCombo.getSelectedItem(),
                        emailField.getText(),
                        mobileField.getText())) {
                    JOptionPane.showMessageDialog(view, "Student updated successfully!");
                    refreshStudentPanel();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update student.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        String username = view.getStudentPanel().getSelectedUsername();
        if (username == null) {
            JOptionPane.showMessageDialog(view, "Please select a student to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete student '" + username + "'?\nThis action cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteStudent(username)) {
                JOptionPane.showMessageDialog(view, "Student deleted successfully!");
                refreshStudentPanel();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete student.");
            }
        }
    }

    public void refreshStudentPanel() {
        try {
            Object[][] studentValues = dao.loadStudentPanel();
            view.getStudentPanel().setData(studentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
