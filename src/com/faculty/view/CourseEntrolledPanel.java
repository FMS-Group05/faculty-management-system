package com.faculty.view;

import com.faculty.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CourseEntrolledPanel extends JPanel {
    private DefaultTableModel model;
    private Object[][] data = {
            { "CSCI 21062", "OOP", "3", "A+" },
            { "CSCI 21052", "SE", "2", "B" },
            { "CSCI 21042", "ADBS", "2", "A" },
            { "CSCI 21032", "STAT", "3", "D" },
            { "CSCI 21022", "ENGLISH", "2", "C" },
            { "CSCI 21012", "MANAGEMNET", "2", "B" },
            { "CSCI 21062", "NETWORKING", "2", "A" },
            { "CSCI 21052", "PROJECT", "2", "-B" },
            { "CSCI 21042", "ADBS", "2", "A" },
            { "CSCI 21032", "STAT", "3", "D+" },
            { "CSCI 21022", "ENGLISH", "2", "C" },
            { "CSCI 21012", "MANAGEMNET II", "2", "B" }
    };

    private JButton enrollBtn, unenrollBtn, gradeBtn;
    private JTable table;

    public CourseEntrolledPanel(User user) {
        // 1. Basic Panel Setup
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(0, 20)); // Vgap of 20px
        setBorder(new EmptyBorder(20, 40, 40, 40)); // Padding around the whole panel

        // Define the Theme Color (Purple)
        Color themeColor = new Color(138, 79, 255);

        // 2. The Header Title ("Courses Enrolled")
        JLabel titleLabel = new JLabel("Courses Enrolled", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(themeColor);
        add(titleLabel, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);

        // Top Actions Panel
        JPanel topActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        topActions.setBackground(Color.WHITE);
        enrollBtn = actionButton("Enroll", themeColor);
        unenrollBtn = actionButton("Unenroll", new Color(180, 180, 180));
        gradeBtn = actionButton("Change Grade", new Color(180, 180, 180));

        topActions.add(enrollBtn);
        topActions.add(unenrollBtn);
        topActions.add(gradeBtn);
        content.add(topActions, BorderLayout.NORTH);

        // 3. Data for the Table (Mock Data based on your image)
        String[] columnNames = { "Course code", "Course name", "Credits", "Grade" };

        // 4. Create the Table Model
        this.model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        // 5. Styling the Table to match the design
        table.setFillsViewportHeight(true);
        table.setRowHeight(50); // Taller rows like the design
        table.setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setForeground(themeColor);
        table.setGridColor(themeColor); // Purple grid lines
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(230, 230, 250)); // Light purple selection
        table.setSelectionForeground(Color.BLACK);

        // Center align the text in all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // 6. Styling the Header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setForeground(themeColor);
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(themeColor));

        // Re-apply center renderer to header as well
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // 7. Add Table to ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE); // White background for empty space
        scrollPane.setBorder(BorderFactory.createLineBorder(themeColor, 1)); // Border around the whole table list

        content.add(scrollPane, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        content.add(scrollPane, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    private JButton actionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBorder(BorderFactory.createLineBorder(bg, 1, true));
        return btn;
    }

    public JButton getEnrollButton() {
        return enrollBtn;
    }

    public JButton getUnenrollButton() {
        return unenrollBtn;
    }

    public JButton getGradeButton() {
        return gradeBtn;
    }

    public String getSelectedCourseCode() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return table.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }

    public void setData(Object[][] newData) {
        if (newData == null || model == null)
            return;
        model.setRowCount(0); // Clear existing rows
        for (Object[] row : newData) {
            model.addRow(row);
        }
    }
}