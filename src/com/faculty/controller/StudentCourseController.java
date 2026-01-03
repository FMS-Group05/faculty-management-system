package com.faculty.controller;

import com.faculty.dao.StudentCourseDAO;
import com.faculty.model.User;
import com.faculty.view.*;

import javax.swing.*;

public class StudentCourseController {
    private final CourseEntrolledPanel view;
    private final StudentCourseDAO dao = new StudentCourseDAO();

    private final User user;

    public StudentCourseController(CourseEntrolledPanel view, User user) {
        this.view = view;
        this.user = user;

        loadData();

        view.getEnrollButton().addActionListener(e -> handleEnroll());
        view.getUnenrollButton().addActionListener(e -> handleUnenroll());
        view.getGradeButton().addActionListener(e -> handleUpdateGrade());
    }

    public void loadData() {
        try {
            Object[][] values = dao.loadCourseEnrolled(user.getUsername());
            view.setData(values);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error loading course enrolled: " + e.getMessage());
        }
    }

    private void handleEnroll() {
        try {
            java.util.List<String> options = dao.getAllCourseCodes();
            if (options.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No courses available to enroll.");
                return;
            }

            JComboBox<String> courseBox = new JComboBox<>(options.toArray(new String[0]));
            int result = JOptionPane.showConfirmDialog(view, courseBox, "Select Course to Enroll",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String selected = (String) courseBox.getSelectedItem();
                if (selected != null) {
                    String[] parts = selected.split(" - ");
                    String cCode = parts[0];

                    if (dao.enrollCourse(user.getUsername(), cCode)) {
                        JOptionPane.showMessageDialog(view, "Enrolled successfully!");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(view, "Enrollment failed.");
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void handleUnenroll() {
        String cCode = view.getSelectedCourseCode();
        if (cCode == null) {
            JOptionPane.showMessageDialog(view, "Please select a course to unenroll.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to unenroll from " + cCode + "?",
                "Confirm Unenroll", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (dao.unenrollCourse(user.getUsername(), cCode)) {
                    JOptionPane.showMessageDialog(view, "Unenrolled successfully!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(view, "Unenroll failed.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error unenrolling: " + e.getMessage());
            }
        }
    }

    private void handleUpdateGrade() {
        String cCode = view.getSelectedCourseCode();
        if (cCode == null) {
            JOptionPane.showMessageDialog(view, "Please select a course to update grade.");
            return;
        }

        String grade = JOptionPane.showInputDialog(view, "Enter new Grade for " + cCode + ":");
        if (grade != null) {
            try {
                if (dao.updateGrade(user.getUsername(), cCode, grade.trim())) {
                    JOptionPane.showMessageDialog(view, "Grade updated successfully!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(view, "Update failed.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error updating grade: " + e.getMessage());
            }
        }
    }

}
