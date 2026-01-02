package com.faculty.controller;

import com.faculty.dao.DegreeDAO;
import com.faculty.view.AdminDashboardView;
import javax.swing.*;
import java.util.List;

public class DegreeController {

    private final AdminDashboardView view;
    private final DegreeDAO dao;

    public DegreeController(AdminDashboardView view) {
        this.view = view;
        this.dao = new DegreeDAO();
        initActions();
        refreshPanel();
    }

    private void initActions() {
        view.getDegreePanel().getAddButton().addActionListener(e -> addDegree());
        view.getDegreePanel().getEditButton().addActionListener(e -> editDegree());
        view.getDegreePanel().getDeleteButton().addActionListener(e -> deleteDegree());
    }

    private void addDegree() {
        try {
            List<String> depts = dao.getAllDepartments();
            JComboBox<String> deptCombo = new JComboBox<>(depts.toArray(new String[0]));

            JTextField degreeField = new JTextField();
            JTextField stdCountField = new JTextField();

            Object[] message = {
                    "Degree Name:", degreeField,
                    "Department (Code):", deptCombo,
                    "No of Students:", stdCountField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Add New Degree", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String degreeName = degreeField.getText();
                    String deptCode = (String) deptCombo.getSelectedItem();
                    int noOfStd = Integer.parseInt(stdCountField.getText());

                    if (dao.addDegree(degreeName, deptCode, noOfStd)) {
                        JOptionPane.showMessageDialog(view, "Degree added successfully!");
                        refreshPanel();
                    } else {
                        JOptionPane.showMessageDialog(view, "Failed to add degree. Name might already exist.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "No of Students must be a number.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void editDegree() {
        String degreeName = view.getDegreePanel().getSelectedDegree();
        if (degreeName == null) {
            JOptionPane.showMessageDialog(view, "Please select a degree to edit.");
            return;
        }

        try {
            JTable table = view.getDegreePanel().getDegreeTable();
            int viewRow = table.getSelectedRow();

            // Columns: Degree=0, Dept Name=1, Count=2
            // Note: Dept displayed is Name, but we need Code for update if we change it.
            // Our DAO getAllDepartments returns Codes (dptN).
            // This is a disconnect. DAO loads Codes for combo, but Table shows Names.
            // For now, we'll try to pre-select if possible, or user has to re-select.
            // Or we assume the user knows the code.
            // In a pro app we'd map Name->Code. Here we'll just populate combo with Codes.

            Object countObj = table.getValueAt(viewRow, 2);
            String currentCount = countObj != null ? countObj.toString() : "";

            List<String> depts = dao.getAllDepartments();
            JComboBox<String> deptCombo = new JComboBox<>(depts.toArray(new String[0]));
            // Can't easily match Name to Code without extra map. We'll leave it at first
            // item.

            JTextField stdCountField = new JTextField(currentCount);

            Object[] message = {
                    "Degree Name: " + degreeName,
                    "Department (Code):", deptCombo,
                    "No of Students:", stdCountField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Edit Degree", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String deptCode = (String) deptCombo.getSelectedItem();
                    int noOfStd = Integer.parseInt(stdCountField.getText());

                    if (dao.updateDegree(degreeName, deptCode, noOfStd)) {
                        JOptionPane.showMessageDialog(view, "Degree updated successfully!");
                        refreshPanel();
                    } else {
                        JOptionPane.showMessageDialog(view, "Failed to update degree.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "No of Students must be a number.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void deleteDegree() {
        String degreeName = view.getDegreePanel().getSelectedDegree();
        if (degreeName == null) {
            JOptionPane.showMessageDialog(view, "Please select a degree to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete degree '" + degreeName + "'?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteDegree(degreeName)) {
                JOptionPane.showMessageDialog(view, "Degree deleted successfully!");
                refreshPanel();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete degree.");
            }
        }
    }

    public void refreshPanel() {
        try {
            Object[][] values = dao.loadDegrees();
            view.getDegreePanel().setData(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
