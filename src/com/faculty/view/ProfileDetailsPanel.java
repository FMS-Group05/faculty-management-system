package com.faculty.view;

import com.faculty.controller.StudentDetailsController;
import com.faculty.dao.StudentDetailsDAO;
import com.faculty.model.User;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ProfileDetailsPanel extends JPanel {

    private final JButton saveButton = new JButton("Save changes");
    private final String[] labels = {"Full Name", "Student ID", "Degree", "Email", "Mobile Number"};
    private final JComponent[] fields = new JComponent[labels.length];
    private final Color PURPLE = new Color(132, 84, 255);

    public ProfileDetailsPanel(User user) {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 250));

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(10, 10, 10, 10);
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Profile Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
        titleLabel.setForeground(PURPLE);
        add(titleLabel, mainGbc);

        // Card panel
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(new CompoundBorder(
                new LineBorder(PURPLE, 3, true),
                new EmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        StudentDetailsDAO dao = new StudentDetailsDAO();

        for (int i = 0; i < labels.length; i++) {
            JComponent field;

            if (labels[i].equals("Degree")) {
                JComboBox<String> comboBox = new JComboBox<>();
                try {
                    List<String> degrees = dao.getAllDegreeNames();
                    for (String d : degrees) comboBox.addItem(d);
                } catch (Exception e) {
                    comboBox.addItem("Error loading degrees");
                }
                field = comboBox;
            } else {
                JTextField textField = new JTextField();

                // Add placeholders for Student ID and Email
                if (labels[i].equals("Student ID")) {
                    addPlaceholder(textField, "e.g : CS/20xx/xx");
                } else if (labels[i].equals("Email")) {
                    addPlaceholder(textField, "e.g : name-CS22xx@stu.kln.ac.lk");
                }

                field = textField;
            }

            addField(cardPanel, gbc, i, labels[i], field);
        }

        // Save button
        saveButton.setBackground(PURPLE);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setContentAreaFilled(true);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(180, 42));
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PURPLE, 2, true),
                new EmptyBorder(8, 16, 8, 16)));

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

        mainGbc.gridy = 1;
        add(cardPanel, mainGbc);

        // Load profile data after login
        new StudentDetailsController(this, user);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(PURPLE);

        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(new LineBorder(PURPLE, 2, true));
        field.setPreferredSize(new Dimension(400, 32));
        field.setBackground(new Color(250, 250, 255));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(field, gbc);

        fields[row] = field;
    }

    private void addPlaceholder(JTextField field, String placeholder) {
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        // Set initial placeholder
        if (field.getText().isEmpty()) {
            field.setText(placeholder);
        }
    }

    // Null-safe setFields
    public void setFields(String[] values) {
        for (int i = 0; i < fields.length; i++) {
            String text = values[i] != null ? values[i] : "";

            if (fields[i] instanceof JTextField) {
                JTextField tf = (JTextField) fields[i];
                // If text is empty, leave placeholder
                if (!text.isEmpty()) {
                    tf.setText(text);
                    tf.setForeground(Color.BLACK);
                }
            } else if (fields[i] instanceof JComboBox) {
                ((JComboBox<?>) fields[i]).setSelectedItem(text);
            }
        }
    }

    public String[] getFieldValues() {
        String[] values = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof JTextField) {
                values[i] = ((JTextField) fields[i]).getText();
            } else if (fields[i] instanceof JComboBox) {
                Object selected = ((JComboBox<?>) fields[i]).getSelectedItem();
                values[i] = selected != null ? selected.toString() : "";
            }
        }
        return values;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JComponent[] getFields() {
        return fields;
    }
}
