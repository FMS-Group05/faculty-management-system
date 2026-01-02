package com.faculty.view;

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

    public ProfileDetailsPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 250)); // subtle background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        // ---------- Card Panel ----------
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);

        // Card border
        Color borderColor = new Color(132, 84, 255); // custom border color
        cardPanel.setBorder(new CompoundBorder(
                new LineBorder(borderColor, 3, true), // border thickness + rounded corners
                new EmptyBorder(25, 40, 15, 40) // reduced bottom padding to 15
        ));

        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.insets = new Insets(10, 10, 10, 10); // spacing between fields
        cardGbc.anchor = GridBagConstraints.WEST;
        cardGbc.fill = GridBagConstraints.HORIZONTAL;

        // ---------- Title ----------
        JLabel lblTitle = new JLabel("Profile Details", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32)); // slightly smaller
        lblTitle.setForeground(borderColor);

        cardGbc.gridx = 0;
        cardGbc.gridy = 0;
        cardGbc.gridwidth = 2;
        cardGbc.anchor = GridBagConstraints.CENTER;
        cardPanel.add(lblTitle, cardGbc);

        // Reset for fields
        cardGbc.gridwidth = 1;

        // ---------- Labels + Fields ----------

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);

        for (int i = 0; i < labels.length; i++) {
            cardGbc.gridy = i + 1;
            cardGbc.gridx = 0;

            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            label.setForeground(borderColor);
            cardPanel.add(label, cardGbc);

            cardGbc.gridx = 1;
            fields[i] = new JTextField();
            fields[i].setFont(fieldFont);
            fields[i].setForeground(borderColor);

            // Compact height for fields
            fields[i].setPreferredSize(new Dimension(800, 32)); // slightly smaller height
            fields[i].setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(borderColor, 2, true),
                    new EmptyBorder(6, 12, 6, 12)
            ));
            fields[i].setBackground(new Color(250, 250, 255));
            cardPanel.add(fields[i], cardGbc);
        }

        // ---------- Save Button ----------
        saveButton.setBackground(borderColor);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(180, 38)); // slightly smaller height
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(borderColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(borderColor);
            }
        });

        cardGbc.gridy = labels.length + 1;
        cardGbc.gridx = 1;
        cardGbc.anchor = GridBagConstraints.EAST;
        cardGbc.insets = new Insets(10, 10, 5, 0); // reduce bottom spacing
        cardPanel.add(saveButton, cardGbc);

        // ---------- Add card panel to main panel ----------
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(cardPanel, gbc);
    }

    // ---------- Main method ----------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Profile Details");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 550); // slightly smaller height
            frame.setLocationRelativeTo(null);
            frame.add(new ProfileDetailsPanel());
            frame.setVisible(true);
        });
    }

    public JButton getSaveButton() { return saveButton; }
    public JTextField[] getFields() { return fields; }

    public void setFields(String[] values) {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setText(values[i]);
        }
    }
}
