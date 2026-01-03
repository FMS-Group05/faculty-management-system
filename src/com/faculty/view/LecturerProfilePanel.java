package com.faculty.view;

import com.faculty.controller.LecturerProfileController;
import com.faculty.dao.LecturerProfileDAO;
import com.faculty.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class LecturerProfilePanel extends JPanel {

    private final Color PURPLE = new Color(132, 84, 255);
    private final String[] labels = { "Full Name", "Department", "Course Teaching", "Email", "Mobile Number" };
    private final JComponent[] fields = new JComponent[labels.length];
    private final JButton saveBtn = new JButton("Save Profile");

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
                new EmptyBorder(25, 30, 25, 30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== FORM FIELDS =====
        LecturerProfileDAO dao = new LecturerProfileDAO();

        for (int i = 0; i < labels.length; i++) {
            JComponent field;
            if (labels[i].equals("Department")) {
                JComboBox<String> startCombo = new JComboBox<>();
                try {
                    List<String> depts = dao.getAllDepartmentCodes();
                    for (String d : depts)
                        startCombo.addItem(d);
                } catch (Exception e) {
                    e.printStackTrace();
                    startCombo.addItem("Error");
                }
                field = startCombo;
            } else if (labels[i].equals("Course Teaching")) {
                JComboBox<String> courseCombo = new JComboBox<>();
                try {
                    List<String> courses = dao.getAllCourseCodes();
                    for (String c : courses)
                        courseCombo.addItem(c);
                } catch (Exception e) {
                    e.printStackTrace();
                    courseCombo.addItem("Error");
                }
                field = courseCombo;
            } else {
                field = new JTextField();
            }

            addField(card, gbc, i, labels[i], field);
            fields[i] = field;
        }

        // ===== SAVE BUTTON (DECREASED WIDTH) =====
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

        // Init Controller
        new LecturerProfileController(this, user);
    }

    // ===== HELPER METHOD =====
    private void addField(JPanel panel, GridBagConstraints gbc, int row,
            String labelText, JComponent field) {

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(PURPLE);

        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(new LineBorder(PURPLE, 2, true));
        field.setPreferredSize(new Dimension(200, 30)); // Explicit size to match somewhat

        // Label column (smaller width)
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        // Input field column (more width)
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    public JButton getSaveButton() {
        return saveBtn;
    }

    public void setFields(String[] values) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof JTextField) {
                ((JTextField) fields[i]).setText(values[i]);
            } else if (fields[i] instanceof JComboBox) {
                ((JComboBox<?>) fields[i]).setSelectedItem(values[i]);
            }
        }
    }

    public String[] getFieldValues() {
        String[] values = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof JTextField) {
                values[i] = ((JTextField) fields[i]).getText();
            } else if (fields[i] instanceof JComboBox) {
                Object item = ((JComboBox<?>) fields[i]).getSelectedItem();
                values[i] = item != null ? item.toString() : "";
            }
        }
        return values;
    }
}
