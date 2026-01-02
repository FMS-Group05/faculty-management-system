package com.faculty.controller;

import com.faculty.dao.StudentCourseDAO;
import com.faculty.model.User;
import com.faculty.view.*;

import javax.swing.*;

public class StudentCourseController {
    private final CourseEntrolledPanel view;
    private final StudentCourseDAO dao = new StudentCourseDAO();

    public StudentCourseController(CourseEntrolledPanel view, User user) {
        this.view = view;

        try {
            Object[][] values = dao.loadCourseEnrolled(user.getUsername());
            view.setData(values);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error loading course enrolled: " + e.getMessage());
        }
    }

}
