package com.faculty.view;

import com.faculty.util.ColorPalette;

import javax.swing.*;
import java.awt.*;

public class ProfileDetailsPanel extends JPanel {
    private final JButton saveButton  = new JButton("Save changes");
    private final String[] labels = {"Full Name", "Student ID", "Degree", "Email", "Mobile Number"};
    private final JTextField[] fields = new JTextField[labels.length];

    public ProfileDetailsPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ---------- Title ----------
        JLabel lblTitle = new JLabel("Profile Details", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(ColorPalette.PRIMARY);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(30, 30, 30, 30); // top margin
        add(lblTitle, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10); // reset spacing

        // ---------- Labels + Fields ----------

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);
        Color customBorderColor = new Color(132, 84, 255); // new border color

        for (int i = 0; i < labels.length; i++) {
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            label.setForeground(Color.DARK_GRAY); // label color
            add(label, gbc);

            gbc.gridx = 1;
            fields[i] = new JTextField(20);
            fields[i].setFont(fieldFont);
            fields[i].setForeground(ColorPalette.PRIMARY); // text color
            fields[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(customBorderColor, 1, true), // new border color
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            add(fields[i], gbc);
        }

        // ---------- Save Button ----------
        // saveButton = new JButton("Save changes");
        saveButton.setBackground(ColorPalette.PRIMARY);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(160, 40));
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 10, 20, 30);
        add(saveButton, gbc);
    }

    public JButton getSaveButton() { return saveButton; }
    public JTextField[] getFields() { return fields; }

    public void setFields(String[] values) {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setText(values[i]);
        }
    }
}
