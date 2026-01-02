package com.faculty.controller;

import com.faculty.view.AdminDashboardView;
import javax.swing.*;

public class AdminController {
    private final AdminDashboardView view;

    public AdminController(AdminDashboardView view) {
        this.view = view;
        initActions();
    }

    private void initActions() {
        // Courses Panel Actions
        view.getCoursePanel().getAddButton().addActionListener(e ->
                JOptionPane.showMessageDialog(view, "Add New Course")
        );

        // Departments Panel Actions
        view.getDepartmentPanel().getAddButton().addActionListener(e ->
                JOptionPane.showMessageDialog(view, "Add New Department")
        );

        // Degrees Panel Actions
        view.getDegreePanel().getAddButton().addActionListener(e ->
                JOptionPane.showMessageDialog(view, "Add New Degree")
        );

        // Logout Action
        view.getLogoutButton().addActionListener(e -> {
            view.dispose();
            JOptionPane.showMessageDialog(null, "Logged out successfully");
        });
    }
}