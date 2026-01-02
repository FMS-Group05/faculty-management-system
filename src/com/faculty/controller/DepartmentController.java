package com.faculty.controller;

import com.faculty.dao.DepartmentDAO;
import com.faculty.view.AdminDashboardView;
import javax.swing.*;

public class DepartmentController {

    private final AdminDashboardView view;
    private final DepartmentDAO dao;

    public DepartmentController(AdminDashboardView view) {
        this.view = view;
        this.dao = new DepartmentDAO();
        initActions();
        refreshPanel();
    }

    private void initActions() {
        view.getDepartmentPanel().getAddButton().addActionListener(e -> addDepartment());
        view.getDepartmentPanel().getEditButton().addActionListener(e -> editDepartment());
        view.getDepartmentPanel().getDeleteButton().addActionListener(e -> deleteDepartment());
    }

    private void addDepartment() {
        JTextField dptNField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField hodField = new JTextField();
        JTextField staffField = new JTextField();

        Object[] message = {
                "Department Code (ID):", dptNField,
                "Department Name:", nameField,
                "Head of Department (HOD):", hodField,
                "No of Staff:", staffField
        };

        int option = JOptionPane.showConfirmDialog(view, message, "Add New Department", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String dptN = dptNField.getText();
                String name = nameField.getText();
                String hod = hodField.getText();
                int noOfStaf = Integer.parseInt(staffField.getText());

                if (dao.addDepartment(dptN, name, hod, noOfStaf)) {
                    JOptionPane.showMessageDialog(view, "Department added successfully!");
                    refreshPanel();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add department. Code might already exist.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "No of Staff must be a number.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
            }
        }
    }

    private void editDepartment() {
        String dptN = view.getDepartmentPanel().getSelectedDeptCode();
        if (dptN == null) {
            JOptionPane.showMessageDialog(view, "Please select a department to edit.");
            return;
        }

        try {
            JTable table = view.getDepartmentPanel().getDepartmentTable();
            int viewRow = table.getSelectedRow();

            // Columns: Name=0, HOD=1, Degree=2, Staff=3, Code=4(hidden)
            Object nameObj = table.getValueAt(viewRow, 0);
            Object hodObj = table.getValueAt(viewRow, 1);
            Object staffObj = table.getValueAt(viewRow, 3);

            String currentName = nameObj != null ? nameObj.toString() : "";
            String currentHod = hodObj != null ? hodObj.toString() : "";
            String currentStaff = staffObj != null ? staffObj.toString() : "";

            JTextField nameField = new JTextField(currentName);
            JTextField hodField = new JTextField(currentHod);
            JTextField staffField = new JTextField(currentStaff);

            Object[] message = {
                    "Department Code: " + dptN,
                    "Department Name:", nameField,
                    "Head of Department:", hodField,
                    "No of Staff:", staffField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Edit Department", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String updateName = nameField.getText();
                    String updateHod = hodField.getText();
                    int updateStaff = Integer.parseInt(staffField.getText());

                    if (dao.updateDepartment(dptN, updateName, updateHod, updateStaff)) {
                        JOptionPane.showMessageDialog(view, "Department updated successfully!");
                        refreshPanel();
                    } else {
                        JOptionPane.showMessageDialog(view, "Failed to update department.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "No of Staff must be a number.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void deleteDepartment() {
        String dptN = view.getDepartmentPanel().getSelectedDeptCode();
        if (dptN == null) {
            JOptionPane.showMessageDialog(view, "Please select a department to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete department '" + dptN + "'?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteDepartment(dptN)) {
                JOptionPane.showMessageDialog(view, "Department deleted successfully!");
                refreshPanel();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete department.");
            }
        }
    }

    public void refreshPanel() {
        try {
            Object[][] values = dao.loadDepartments();
            view.getDepartmentPanel().setData(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
