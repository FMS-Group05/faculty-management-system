package com.faculty.view;

import com.faculty.controller.StudentDetailsController;
import com.faculty.model.User;
import com.faculty.util.ColorPalette;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProfileDetailsPanel extends JPanel {
    private final JButton saveButton  = new JButton("Save changes");
    private final String[] labels = {"Full Name", "Student ID", "Degree", "Email", "Mobile Number"};
    private final JTextField[] fields = new JTextField[labels.length];
    private final Color PURPLE = new Color(132, 84, 255);

    public ProfileDetailsPanel(User user) {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;






        // ---------- TITLE LABEL ----------
        JLabel titleLabel = new JLabel("Profile Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(PURPLE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH; // title above card
        gbc.insets = new Insets(10, 50, 10, 10); // leave space for icon
        add(titleLabel, gbc);

        // ---------- CARD PANEL ----------
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(new CompoundBorder(
                new LineBorder(PURPLE, 3, true),
                new EmptyBorder(25, 40, 15, 40)
        ));

        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.insets = new Insets(10, 10, 10, 10);
        cardGbc.anchor = GridBagConstraints.WEST;
        cardGbc.fill = GridBagConstraints.HORIZONTAL;

        // ---------- LABELS + FIELDS ----------
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);
        for (int i = 0; i < labels.length; i++) {
            cardGbc.gridy = i;
            cardGbc.gridx = 0;

            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            label.setForeground(PURPLE);
            cardPanel.add(label, cardGbc);

            cardGbc.gridx = 1;
            fields[i] = new JTextField();
            fields[i].setFont(fieldFont);
            fields[i].setForeground(PURPLE);
            fields[i].setPreferredSize(new Dimension(800, 32));
            fields[i].setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(PURPLE, 2, true),
                    new EmptyBorder(6, 12, 6, 12)
            ));
            fields[i].setBackground(new Color(250, 250, 255));
            cardPanel.add(fields[i], cardGbc);
        }

        // ---------- SAVE BUTTON ----------
        saveButton.setBackground(PURPLE);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setContentAreaFilled(true);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(180, 42));
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PURPLE, 2, true),
                new EmptyBorder(8, 16, 8, 16)
        ));

        // Smooth hover and click effects
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(new Color(50, 180, 50));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(PURPLE);
            }
        
        });

        cardGbc.gridy = labels.length;
        cardGbc.gridx = 1;
        cardGbc.anchor = GridBagConstraints.EAST;
        cardGbc.insets = new Insets(10, 10, 5, 0);
        cardPanel.add(saveButton, cardGbc);

        // ---------- ADD CARD PANEL TO MAIN PANEL ----------
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(cardPanel, gbc);

        new StudentDetailsController(this, user);
    }

    // ---------- GETTERS ----------
    public JButton getSaveButton() { return saveButton; }
    public JTextField[] getFields() { return fields; }

    public void setFields(String[] values) {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setText(values[i]);
        }
    }
}
