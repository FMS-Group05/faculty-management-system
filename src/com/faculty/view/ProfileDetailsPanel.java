package com.faculty.view;

import com.faculty.controller.StudentDetailsController;
import com.faculty.model.User;
//import com.faculty.util.ColorPalette;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProfileDetailsPanel extends JPanel {
    private final JButton saveButton = new JButton("Save changes");
    private final String[] labels = {"Full Name", "Student ID", "Degree", "Email", "Mobile Number"};
    private final JTextField[] fields = new JTextField[labels.length];
    private final Color PURPLE = new Color(132, 84, 255);

    public ProfileDetailsPanel(User user) {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 250));

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(10, 10, 10, 10);
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.anchor = GridBagConstraints.CENTER;

        // ===== TITLE LABEL =====
        JLabel titleLabel = new JLabel("Profile Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
        titleLabel.setForeground(PURPLE);
        add(titleLabel, mainGbc);

        // ===== CARD PANEL =====
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(new CompoundBorder(
                new LineBorder(PURPLE, 3, true),
                new EmptyBorder(25, 30, 25, 30) // same as LecturerProfilePanel
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== FORM FIELDS =====
        for (int i = 0; i < labels.length; i++) {
            addField(cardPanel, gbc, i, labels[i], new JTextField());
        }

        // ===== SAVE BUTTON =====
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

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        cardPanel.add(saveButton, gbc);

        // ===== ADD CARD PANEL TO MAIN PANEL =====
        mainGbc.gridy = 1;
        add(cardPanel, mainGbc);

        new StudentDetailsController(this, user);
    }

    // ===== HELPER METHOD =====
    private void addField(JPanel panel, GridBagConstraints gbc, int row,
                          String labelText, JTextField textField) {

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(PURPLE);

        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textField.setBorder(new LineBorder(PURPLE, 2, true));
        textField.setPreferredSize(new Dimension(400, 32)); // size similar to Lecturer panel
        textField.setBackground(new Color(250, 250, 255));

        // Label column
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        // Text field column
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(textField, gbc);

        // Store reference for controller
        fields[row] = textField;
    }

    // ===== GETTERS =====
    public JButton getSaveButton() { return saveButton; }
    public JTextField[] getFields() { return fields; }

    public void setFields(String[] values) {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setText(values[i]);
        }
    }
}
