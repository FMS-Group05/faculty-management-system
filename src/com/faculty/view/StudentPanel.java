package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class StudentPanel extends JPanel {

    private final Color PURPLE = new Color(132, 84, 255);
    private final Color LIGHT_GRAY = new Color(200, 200, 200);
    private JTable table;
    private JButton addBtn, editBtn, deleteBtn, saveBtn;

    public StudentPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Students", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PURPLE);
        titleLabel.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(0, 30, 0, 30));

        // Action Buttons
        JPanel topActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        topActions.setBackground(Color.WHITE);
        addBtn = actionButton("Add new", PURPLE);
        editBtn = actionButton("Edit", LIGHT_GRAY);
        deleteBtn = actionButton("Delete", LIGHT_GRAY);
        topActions.add(addBtn);
        topActions.add(editBtn);
        topActions.add(deleteBtn);
        content.add(topActions, BorderLayout.NORTH);

        // Table Configuration with purple lines
        // Added "Username" as the last column (will be hidden)
        String[] columns = { "Full Name", "Student ID", "Degree", "Email", "Mobile Number", "Username" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (int i = 0; i < 10; i++) {
            model.addRow(new Object[] { "Kumar Sangakkara", "ET/2022/007", "Engineering Technology", "kumars@kln.ac.lk",
                    "0123456789" });
        }

        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(PURPLE);
        table.setShowGrid(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Hide the "Username" column (index 5)
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(5));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setForeground(PURPLE);
        header.setBackground(Color.WHITE);
        header.setBorder(new LineBorder(PURPLE, 1));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(PURPLE, 2));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        content.add(scroll, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        // Save changes button
        saveBtn = new JButton("Save changes");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        saveBtn.setBackground(PURPLE);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setPreferredSize(new Dimension(400, 50));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(saveBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton actionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(bg, 1, true));
        return btn;
    }

    // Getters for AdminController
    public JTable getStudentTable() {
        return table;
    }

    public JButton getAddButton() {
        return addBtn;
    }

    public JButton getEditButton() {
        return editBtn;
    }

    public JButton getDeleteButton() {
        return deleteBtn;
    }

    public JButton getSaveButton() {
        return saveBtn;
    }

    public void setData(Object[][] values) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Object[] row : values) {
            model.addRow(row);
        }
    }

    public String getSelectedUsername() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // "Username" is at index 5 in the model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            // We need to convert view index to model index in case of sorting (if enabled
            // later)
            int modelRow = table.convertRowIndexToModel(selectedRow);
            return model.getValueAt(modelRow, 5).toString();
        }
        return null;
    }
}