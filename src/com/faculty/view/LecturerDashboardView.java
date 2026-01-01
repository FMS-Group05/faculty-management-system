package com.faculty.view;

import javax.swing.*;

public class LecturerDashboardView extends JFrame {

    public LecturerDashboardView(com.faculty.model.User user) {
        setTitle("Lecturer Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lbl = new JLabel(
                "Welcome Lecturer : " + user.getUsername(),
                SwingConstants.CENTER);

        add(lbl);
    }
}