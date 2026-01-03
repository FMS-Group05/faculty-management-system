package com.faculty.controller;

import com.faculty.dao.CourseDAO;
import com.faculty.view.AdminDashboardView;
import javax.swing.*;

public class CourseController {

    private final AdminDashboardView view;
    private final CourseDAO dao;

    public CourseController(AdminDashboardView view) {
        this.view = view;
        this.dao = new CourseDAO();
        initActions();
        refreshPanel();
    }

    private void initActions() {
        view.getCoursePanel().getAddButton().addActionListener(e -> addCourse());
        view.getCoursePanel().getEditButton().addActionListener(e -> editCourse());
        view.getCoursePanel().getDeleteButton().addActionListener(e -> deleteCourse());
    }

    private void addCourse() {
        JTextField ccodeField = new JTextField();
        JTextField cnameField = new JTextField();
        JTextField creditsField = new JTextField();

        Object[] message = {
                "Course Code:", ccodeField,
                "Course Name:", cnameField,
                "Credits (Number):", creditsField
        };

        int option = JOptionPane.showConfirmDialog(view, message, "Add New Course", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String ccode = ccodeField.getText();
                String cname = cnameField.getText();
                int credits = Integer.parseInt(creditsField.getText());

                if (dao.addCourse(ccode, cname, credits)) {
                    JOptionPane.showMessageDialog(view, "Course added successfully!");
                    refreshPanel();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add course. Code might already exist.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Credits must be a number.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
            }
        }
    }

    private void editCourse() {
        String ccode = view.getCoursePanel().getSelectedCourseCode();
        if (ccode == null) {
            JOptionPane.showMessageDialog(view, "Please select a course to edit.");
            return;
        }

        try {
            JTable table = view.getCoursePanel().getCourseTable();
            int viewRow = table.getSelectedRow();

            Object nameObj = table.getValueAt(viewRow, 1);
            Object creditsObj = table.getValueAt(viewRow, 2);

            String currentName = nameObj != null ? nameObj.toString() : "";
            String currentCredits = creditsObj != null ? creditsObj.toString() : "";

            JTextField cnameField = new JTextField(currentName);
            JTextField creditsField = new JTextField(currentCredits);

            Object[] message = {
                    "Course Code: " + ccode,
                    "Course Name:", cnameField,
                    "Credits:", creditsField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Edit Course", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String updateName = cnameField.getText();
                    int updateCredits = Integer.parseInt(creditsField.getText());

                    if (dao.updateCourse(ccode, updateName, updateCredits)) {
                        JOptionPane.showMessageDialog(view, "Course updated successfully!");
                        refreshPanel();
                    } else {
                        JOptionPane.showMessageDialog(view, "Failed to update course.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "Credits must be a number.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void deleteCourse() {
        String ccode = view.getCoursePanel().getSelectedCourseCode();
        if (ccode == null) {
            JOptionPane.showMessageDialog(view, "Please select a course to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete course '" + ccode
                        + "'?\nThis will remove student enrollments and unassign lecturers.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteCourse(ccode)) {
                JOptionPane.showMessageDialog(view, "Course deleted successfully!");
                refreshPanel();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete course.");
            }
        }
    }

    public void refreshPanel() {
        try {
            Object[][] values = dao.loadCourses();
            view.getCoursePanel().setData(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
