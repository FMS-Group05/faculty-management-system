package com.faculty.controller;

import com.faculty.view.AdminDashboardView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminController {
    private final AdminDashboardView view;

    public AdminController(AdminDashboardView view) {
        this.view = view;
        initActions();
        initTimeTableActions();
    }

    private void initActions() {
        view.getStudentPanel().getAddButton().addActionListener(e ->
                JOptionPane.showMessageDialog(view, "Add New Student clicked")
        );

        view.getLecturerPanel().getAddButton().addActionListener(e ->
                JOptionPane.showMessageDialog(view, "Add New Lecturer clicked")
        );

        view.getCoursePanel().getAddButton().addActionListener(e ->
                JOptionPane.showMessageDialog(view, "Add New Course")
        );

        view.getDepartmentPanel().getAddButton().addActionListener(e ->
                JOptionPane.showMessageDialog(view, "Add New Department")
        );

        view.getDegreePanel().getAddButton().addActionListener(e ->
                JOptionPane.showMessageDialog(view, "Add New Degree")
        );

        view.getLogoutButton().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(view, "Do you want to log out?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                view.dispose();
                JOptionPane.showMessageDialog(null, "Logged out successfully");
            }
        });
    }

    private void initTimeTableActions() {
        view.getTimeTablePanel().getAddButton().addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) view.getTimeTablePanel().getTable().getModel();
            model.addRow(new Object[]{"", "", "Upload files"});

            int lastRow = model.getRowCount() - 1;
            view.getTimeTablePanel().getTable().setRowSelectionInterval(lastRow, lastRow);

            view.getTimeTablePanel().openFileChooser();
        });

        view.getTimeTablePanel().getSaveButton().addActionListener(e ->
                JOptionPane.showMessageDialog(view, "Time Table changes saved to database!")
        );
    }
}