package com.faculty.view;

import com.faculty.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LecturerProfilePanel extends JPanel {

    private final Color PURPLE = new Color(132, 84, 255);

    public LecturerProfilePanel(User user) {

        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 250));
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(10, 10, 10, 10);
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.anchor = GridBagConstraints.CENTER;

        // ===== TITLE OUTSIDE CARD =====
        JLabel title = new JLabel("Lecturer Profile");
        title.setFont(new Font("Segoe UI", Font.BOLD, 35));
        title.setForeground(PURPLE);
        add(title, mainGbc);

        // ===== CARD =====
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(520, 380));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PURPLE, 3, true),
                new EmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== FORM FIELDS =====
        addField(card, gbc, 0, "Full Name", new JTextField());
        addField(card, gbc, 1, "Department", new JTextField());
        addField(card, gbc, 2, "Course Teaching", new JTextField());
        addField(card, gbc, 3, "Email", new JTextField());
        addField(card, gbc, 4, "Mobile Number", new JTextField());

        // ===== SAVE BUTTON (DECREASED WIDTH) =====
        JButton saveBtn = new JButton("Save Profile");
        saveBtn.setBackground(PURPLE);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveBtn.setFocusPainted(false);
        saveBtn.setPreferredSize(new Dimension(180, 40)); // narrower button

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(saveBtn, gbc);

        // ===== ADD CARD TO MAIN PANEL =====
        mainGbc.gridy = 1;
        add(card, mainGbc);
    }

    // ===== HELPER METHOD =====
    private void addField(JPanel panel, GridBagConstraints gbc, int row,
                          String labelText, JTextField textField) {

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(PURPLE);

        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textField.setBorder(new LineBorder(PURPLE, 2, true));

        // Label column (smaller width)
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        // Text field column (more width)
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(textField, gbc);
    }

}
