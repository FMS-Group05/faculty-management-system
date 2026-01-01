package com.faculty.view;

import com.faculty.model.User;

import javax.swing.*;

public class StudentDashboardView extends JFrame {

    public StudentDashboardView(User user) {

        setTitle("Student Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lbl = new JLabel(
                "Welcome Student : " + user.getUsername(),
                SwingConstants.CENTER);

        add(lbl);
    }
}