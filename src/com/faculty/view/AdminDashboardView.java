package com.faculty.view;

import com.faculty.model.User;

import javax.swing.*;

public class AdminDashboardView extends JFrame {

    public AdminDashboardView(User user) {

        setTitle("Admin Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lbl = new JLabel(
                "Welcome Admin : " + user.getUsername(),
                SwingConstants.CENTER);

        add(lbl);
    }
}
