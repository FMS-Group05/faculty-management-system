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
        Color customColor = new Color(132, 84, 255); // border + label color

        for (int i = 0; i < labels.length; i++) {
            gbc.gridy++;
            gbc.gridx = 0;

            // Label
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            label.setForeground(customColor);
            add(label, gbc);

            // TextField
            gbc.gridx = 1;
            fields[i] = new JTextField();
            fields[i].setFont(fieldFont);
            fields[i].setForeground(customColor);

            // Wider rectangular field
            fields[i].setPreferredSize(new Dimension(900, 40)); // much wider and taller
            fields[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(customColor, 2, true),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            add(fields[i], gbc);
        }

        // ---------- Save Button ----------
        saveButton.setBackground(customColor);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(180, 45));
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
