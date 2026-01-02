package com.faculty.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TimeTablePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public TimeTablePanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        Color themeColor = new Color(132, 84, 255);

        // ---------- Title ----------
        JLabel lbl = new JLabel("Time Table Section", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(themeColor);
        lbl.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lbl, BorderLayout.NORTH);

        // ---------- Table Columns ----------
        String[] columns = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        // ---------- Table Data ----------
        String[][] data = {
                {"08:00", "", "", "", "", ""},
                {"10:00", "", "", "", "", ""},
                {"INTERVAL", "", "", "", "", ""},
                {"13:00", "", "", "", "", ""},
                {"15:00", "", "", "", "", ""}
        };

        // ---------- Table Model ----------
        model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Interval row not editable
                return row != 2;
            }
        };

        // ---------- JTable ----------
        table = new JTable(model) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Interval row custom rendering
                int intervalRow = 2;

                Rectangle rect = getCellRect(intervalRow, 0, true);
                for (int i = 1; i < getColumnCount(); i++) {
                    rect = rect.union(getCellRect(intervalRow, i, true));
                }

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(themeColor);
                g2.fillRect(rect.x, rect.y, rect.width, rect.height);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));

                String spacedText = String.join(" ", "INTERVAL".split(""));
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(spacedText);
                int textHeight = fm.getAscent();
                int textX = rect.x + (rect.width - textWidth) / 2;
                int textY = rect.y + (rect.height + textHeight) / 2 - 4;
                g2.drawString(spacedText, textX, textY);
                g2.dispose();
            }
        };

        table.setRowHeight(50);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        table.setGridColor(themeColor);
        table.setForeground(themeColor);
        table.setShowGrid(true);

        // ---------- Header ----------
        JTableHeader header = table.getTableHeader();
        header.setBackground(themeColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // ---------- Center cells ----------
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(scrollPane, BorderLayout.CENTER);

        // ---------- Input Panel ----------
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblDay = new JLabel("Day:");
        lblDay.setForeground(themeColor);
        JLabel lblTime = new JLabel("Time:");
        lblTime.setForeground(themeColor);
        JLabel lblSubject = new JLabel("Subject:");
        lblSubject.setForeground(themeColor);

        JTextField txtDay = new JTextField(10);
        JTextField txtTime = new JTextField(10);
        JTextField txtSubject = new JTextField(15);

        // ---------- Set input field colors ----------

        txtDay.setForeground(Color.WHITE);
        txtDay.setForeground(themeColor);
        txtTime.setForeground(Color.WHITE);
        txtTime.setForeground(themeColor);
        txtSubject.setForeground(Color.WHITE);
        txtSubject.setForeground(themeColor);

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(lblDay, gbc);
        gbc.gridx = 1; inputPanel.add(txtDay, gbc);
        gbc.gridx = 2; inputPanel.add(lblTime, gbc);
        gbc.gridx = 3; inputPanel.add(txtTime, gbc);
        gbc.gridx = 4; inputPanel.add(lblSubject, gbc);
        gbc.gridx = 5; inputPanel.add(txtSubject, gbc);

        // ---------- Buttons ----------
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnSave = new JButton("Save Changes");

        JButton[] buttons = {btnAdd, btnDelete, btnSave};
        for (JButton b : buttons) {
            b.setBackground(themeColor);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.BOLD, 14));
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; inputPanel.add(btnAdd, gbc);
        gbc.gridx = 2; gbc.gridwidth = 2; inputPanel.add(btnDelete, gbc);
        gbc.gridx = 4; gbc.gridwidth = 2; inputPanel.add(btnSave, gbc);

        add(inputPanel, BorderLayout.SOUTH);

        // ---------- Button Actions ----------

        // Add value
        btnAdd.addActionListener((ActionEvent e) -> {
            String day = txtDay.getText().trim();
            String time = txtTime.getText().trim();
            String subject = txtSubject.getText().trim();

            if (day.isEmpty() || time.isEmpty() || subject.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            int row = -1;
            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, 0).toString().equalsIgnoreCase(time)) {
                    row = i;
                    break;
                }
            }

            if (row == -1 || row == 2) {
                JOptionPane.showMessageDialog(this, "Invalid time!");
                return;
            }

            int col = -1;
            for (int i = 1; i < table.getColumnCount(); i++) {
                if (table.getColumnName(i).equalsIgnoreCase(day)) {
                    col = i;
                    break;
                }
            }

            if (col == -1) {
                JOptionPane.showMessageDialog(this, "Invalid day!");
                return;
            }

            table.setValueAt(subject, row, col);

            txtDay.setText(""); txtTime.setText(""); txtSubject.setText("");
        });

        // Delete value
        btnDelete.addActionListener((ActionEvent e) -> {
            String day = txtDay.getText().trim();
            String time = txtTime.getText().trim();

            if (day.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill Day and Time to delete");
                return;
            }

            int row = -1;
            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, 0).toString().equalsIgnoreCase(time)) {
                    row = i;
                    break;
                }
            }

            if (row == -1 || row == 2) {
                JOptionPane.showMessageDialog(this, "Invalid time!");
                return;
            }

            int col = -1;
            for (int i = 1; i < table.getColumnCount(); i++) {
                if (table.getColumnName(i).equalsIgnoreCase(day)) {
                    col = i;
                    break;
                }
            }

            if (col == -1) {
                JOptionPane.showMessageDialog(this, "Invalid day!");
                return;
            }

            table.setValueAt("", row, col);
        });

        // Save Changes (Database simulation)
        btnSave.addActionListener((ActionEvent e) -> {
            // Here you can loop through table and save all values to database
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 1; j < table.getColumnCount(); j++) {
                    String value = table.getValueAt(i, j).toString();
                    String time = table.getValueAt(i, 0).toString();
                    String day = table.getColumnName(j);

                    if (!value.isEmpty() && !time.equalsIgnoreCase("INTERVAL")) {
                        // Example: Save to DB logic
                        System.out.println("Saving: " + day + " " + time + " -> " + value);
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "All changes saved successfully!");
        });
    }
}
