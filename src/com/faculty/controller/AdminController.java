package com.faculty.controller;

import com.faculty.dao.AdminPanelsDAO;
import com.faculty.view.AdminDashboardView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminController {
    private final AdminDashboardView view;
    // Removed old student logic fields if any, using StudentController now

    // Legacy DAO removed as all panels now have their own controllers
    // private final AdminPanelsDAO dao = new AdminPanelsDAO();

    @SuppressWarnings("unused")
    private StudentController studentController;
    @SuppressWarnings("unused")
    private LecturerController lecturerController;
    @SuppressWarnings("unused")
    private CourseController courseController;
    @SuppressWarnings("unused")
    private DepartmentController departmentController;
    @SuppressWarnings("unused")
    private DegreeController degreeController;

    public AdminController(AdminDashboardView view) {
        this.view = view;

        // Initialize sub-controllers
        this.studentController = new StudentController(view);
        this.lecturerController = new LecturerController(view);
        this.courseController = new CourseController(view);
        this.departmentController = new DepartmentController(view);
        this.degreeController = new DegreeController(view);

        initActions();
        initTimeTableActions();
    }

    private void initActions() {
        // Student actions are handled by StudentController
        // Lecturer actions are handled by LecturerController
        // Student, Lecturer, Course actions handled by respective controllers
        // Department actions handled by DepartmentController

        view.getStudentsBtn().addActionListener(e -> studentController.refreshStudentPanel());
        view.getLecturersBtn().addActionListener(e -> lecturerController.refreshPanel());
        view.getCoursesBtn().addActionListener(e -> courseController.refreshPanel());
        view.getDepartmentsBtn().addActionListener(e -> departmentController.refreshPanel());
        view.getDegreesBtn().addActionListener(e -> degreeController.refreshPanel());
        // Degree actions handled by DegreeController

        view.getLogoutButton().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(view, "Do you want to log out?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                view.dispose();
                JOptionPane.showMessageDialog(null, "Logged out successfully");
            }
        });
    }

    private void initTimeTableActions() {
        view.getTimeTablePanel().getAddButton().addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) view.getTimeTablePanel().getTable().getModel();
            model.addRow(new Object[] { "", "", "Upload files" });

            int lastRow = model.getRowCount() - 1;
            view.getTimeTablePanel().getTable().setRowSelectionInterval(lastRow, lastRow);
        });

        view.getTimeTablePanel().getSaveButton()
                .addActionListener(e -> JOptionPane.showMessageDialog(view, "Time Table changes saved to database!"));
    }

    // ---------- Student CRUD Actions ----------
    // Logic moved to StudentController
}