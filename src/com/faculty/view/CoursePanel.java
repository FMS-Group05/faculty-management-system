package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CoursePanel extends JPanel {
    private JTable table;
    private final Color PURPLE = new Color(132, 84, 255);
    private JButton addBtn, editBtn, deleteBtn;

    public CoursePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Courses", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(PURPLE);
        title.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(0, 30, 0, 30));

        JPanel topActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        topActions.setBackground(Color.WHITE);
        addBtn = actionButton("Add new", PURPLE);
        editBtn = actionButton("Edit", new Color(180, 180, 180));
        deleteBtn = actionButton("Delete", new Color(180, 180, 180));
        topActions.add(addBtn);
        topActions.add(editBtn);
        topActions.add(deleteBtn);
        content.add(topActions, BorderLayout.NORTH);

        String[] columns = { "Course code", "Course name", "Credits", "Lecturer" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        table.setRowHeight(40);
        table.setGridColor(PURPLE);
        table.setShowGrid(true);

        // Header Formatting - Bold and Purple
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setForeground(PURPLE);
        header.setBackground(Color.WHITE);
        header.setBorder(new LineBorder(PURPLE, 1));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(PURPLE, 2));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setBackground(Color.WHITE);
        content.add(scroll, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Save changes");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        saveBtn.setBackground(PURPLE);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setPreferredSize(new Dimension(400, 50));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottom.setBackground(Color.WHITE);
        bottom.add(saveBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    private JButton actionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBorder(new LineBorder(bg, 1, true));
        return btn;
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

    public JTable getCourseTable() {
        return table;
    }

    public void setData(Object[][] values) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Object[] row : values) {
            model.addRow(row);
        }
    }

    public String getSelectedCourseCode() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return table.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }
}