package com.faculty.view;

import javax.swing.*;
import java.awt.*;

public class TimeTablePanel extends JPanel {

    public TimeTablePanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("Time Table Section", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lbl, BorderLayout.CENTER);
    }
}
