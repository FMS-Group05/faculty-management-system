package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class LecturerPanel extends JPanel {

    private final Color PURPLE = new Color(132, 84, 255);
    private final Color LIGHT_GRAY = new Color(200, 200, 200);
    private JTable table;
    private JButton addBtn, editBtn, deleteBtn, saveBtn;

    public LecturerPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title - Lecturers
        JLabel titleLabel = new JLabel("Lecturers", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PURPLE);
        titleLabel.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(0, 30, 0, 30));

        // Top Action Buttons
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
        String[] columns = {"Full Name", "Department", "Courses teaching", "Email", "Mobile Number"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Scroll access පරීක්ෂා කිරීමට දත්ත පේළි කිහිපයක් එක් කරමු
        for(int i = 0; i < 15; i++) {
            model.addRow(new Object[]{"Kumar Sangakkara", "Software Engineering", "ETEC 21062", "kumars@kln.ac.lk", "0123456789"});
        }

        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(PURPLE); // දම් පැහැති Grid lines
        table.setShowGrid(true);    // තීරු සහ පේළි රාමු පෙන්වීමට

        // දත්ත මැදට පෙළගැස්වීම
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setForeground(PURPLE);
        header.setBackground(Color.WHITE);
        header.setBorder(new LineBorder(PURPLE, 1));

        // ScrollPane with visible right side scroll line
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(PURPLE, 2));
        scroll.getViewport().setBackground(Color.WHITE);

        // දකුණු පස Scroll bar එක පෙන්වීමට සහ සුමටව ක්‍රියා කරවීමට
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        content.add(scroll, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        // Save changes button
        saveBtn = new JButton("Save changes");
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

    // Getters for AdminController
    public JTable getLecturerTable() { return table; }
    public JButton getAddButton() { return addBtn; }
    public JButton getEditButton() { return editBtn; }
    public JButton getDeleteButton() { return deleteBtn; }
    public JButton getSaveButton() { return saveBtn; }
}