package com.faculty.controller;

import com.faculty.dao.TimeTableDAO;
import com.faculty.view.AdminDashboardView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class TimeTableController {

    private final AdminDashboardView view;
    private final TimeTableDAO dao;

    public TimeTableController(AdminDashboardView view) {
        this.view = view;
        this.dao = new TimeTableDAO();
        initActions();
        refreshPanel();
    }

    private void initActions() {
        view.getTimeTablePanel().getAddButton().addActionListener(e -> addTimeTable());
        view.getTimeTablePanel().getDeleteButton().addActionListener(e -> deleteTimeTable());
        view.getTimeTablePanel().getSaveButton().addActionListener(e -> saveChanges());

        // Edit button is less relevant for Degree/Year PKs, but perhaps for
        // re-uploading?
        // The View handles re-upload via the table button directly.
        // We can wire it to show a message or just disable it if not needed.
        view.getTimeTablePanel().getEditButton().addActionListener(e -> JOptionPane.showMessageDialog(view,
                "To edit the image, click 'Upload files' in the table. To change Degree/Year, please delete and add new."));
    }

    private void addTimeTable() {
        try {
            List<String> degrees = dao.getAllDegrees();
            JComboBox<String> degreeCombo = new JComboBox<>(degrees.toArray(new String[0]));
            JTextField yearField = new JTextField();

            Object[] message = {
                    "Select Degree:", degreeCombo,
                    "Year:", yearField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Add Time Table", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String degree = (String) degreeCombo.getSelectedItem();
                try {
                    int year = Integer.parseInt(yearField.getText());
                    if (dao.addTimeTable(degree, year)) {
                        JOptionPane.showMessageDialog(view, "Time Table added successfully!");
                        refreshPanel();
                    } else {
                        JOptionPane.showMessageDialog(view,
                                "Failed to add. This Degree+Year combination may already exist.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "Year must be a number.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void deleteTimeTable() {
        Object[] selected = view.getTimeTablePanel().getSelectedTimeTable();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Please select a row to delete.");
            return;
        }

        String degree = selected[0].toString();
        int year = Integer.parseInt(selected[1].toString());

        int confirm = JOptionPane.showConfirmDialog(view,
                "Delete Time Table for " + degree + " - Year " + year + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteTimeTable(degree, year)) {
                JOptionPane.showMessageDialog(view, "Deleted successfully!");
                refreshPanel();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete.");
            }
        }
    }

    private void saveChanges() {
        // Iterate table and update DB with current image paths
        JTable table = view.getTimeTablePanel().getTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        boolean success = true;

        // Ensure "Tables" directory exists
        File tablesDir = new File("Tables");
        if (!tablesDir.exists()) {
            tablesDir.mkdirs();
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            String degree = model.getValueAt(i, 0).toString();
            int year = Integer.parseInt(model.getValueAt(i, 1).toString());
            Object imgObj = model.getValueAt(i, 2);
            String currentPath = (imgObj != null) ? imgObj.toString() : "";

            String dbPath = currentPath;

            // Check if it is a new file upload (file exists and path is absolute, or
            // doesn't start with Tables/)
            // We assume if it's already "Tables/timetable_..." it's good.
            // If the user selected a new file, the Table will contain the absolute path.
            File file = new File(currentPath);
            if (file.exists() && file.isAbsolute()) {
                String newFileName = "timetable_" + degree + "_" + year + ".png";
                File destFile = new File(tablesDir, newFileName);

                try {
                    Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    dbPath = "Tables/" + newFileName; // Path to save in DB
                } catch (IOException e) {
                    e.printStackTrace();
                    success = false;
                    continue; // Skip DB update if copy failed
                }
            }

            if (!dao.updateTimeTableImage(degree, year, dbPath)) {
                success = false;
            }
        }

        if (success) {
            JOptionPane.showMessageDialog(view, "All changes saved successfully!");
        } else {
            JOptionPane.showMessageDialog(view, "Some changes could not be saved.");
        }
        refreshPanel();
    }

    public void refreshPanel() {
        try {
            Object[][] values = dao.loadTimeTables();
            view.getTimeTablePanel().setData(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
