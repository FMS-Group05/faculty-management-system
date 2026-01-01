package com.faculty.view;

import javax.swing.*;
import java.awt.*;

public class CourseEntrolledPanel extends JPanel {

    public CourseEntrolledPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("Course Enrolled Section", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lbl, BorderLayout.CENTER);
    }
}
