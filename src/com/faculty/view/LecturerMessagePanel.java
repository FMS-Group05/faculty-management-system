package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LecturerMessagePanel extends JPanel {

    private final Color PURPLE = new Color(132, 84, 255);
    private final Color LIGHT_PURPLE = new Color(180, 160, 255);

    public LecturerMessagePanel() {

        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 250));
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(10, 10, 10, 10);
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.anchor = GridBagConstraints.CENTER;

        // ===== TITLE OUTSIDE CARD =====
        JLabel title = new JLabel("Send Message to Students");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(PURPLE);
        add(title, mainGbc);

        // ===== CARD =====
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(520, 420));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PURPLE, 2, true),
                new EmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== BATCH YEAR =====
        addField(card, gbc, 0, "Batch Year", new JComboBox<>(new String[]{"2022", "2024", "2025"}));

        // ===== DEGREE =====
        addField(card, gbc, 1, "Degree", new JComboBox<>(new String[]{"ET", "CT", "CS"}));

        // ===== MESSAGE LABEL =====
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JLabel messageLabel = new JLabel("Message");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        messageLabel.setForeground(PURPLE);
        card.add(messageLabel, gbc);

        // ===== MESSAGE TEXTAREA =====
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;   // allow horizontal growth
        gbc.weighty = 1.0;   // allow vertical growth to take extra space

        JTextArea message = new JTextArea(15, 20); // increased rows
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        message.setBorder(new LineBorder(PURPLE, 2, true));

        card.add(new JScrollPane(message), gbc);

// Reset weighty after adding the text area so other components are not stretched
        gbc.weighty = 0;


        // ===== SEND BUTTON =====
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        JButton sendBtn = new JButton("Send Message");
        sendBtn.setBackground(PURPLE);
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sendBtn.setFocusPainted(false);
        sendBtn.setPreferredSize(new Dimension(180, 40));

        // Add hover effect
        sendBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sendBtn.setBackground(LIGHT_PURPLE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                sendBtn.setBackground(PURPLE);
            }
        });

        card.add(sendBtn, gbc);

        // ===== ADD CARD TO MAIN PANEL =====
        mainGbc.gridy = 1;
        add(card, mainGbc);
    }

    // ===== HELPER METHOD TO ADD FIELDS =====
    private void addField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent input) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(PURPLE);

        if (input instanceof JComboBox) {
            input.setBorder(new LineBorder(PURPLE, 2, true));
        }

        // Label column
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        // Input column
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(input, gbc);
    }
}
