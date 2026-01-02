package com.faculty.controller;

import com.faculty.dao.AdminPanelsDAO;
import com.faculty.view.AdminDashboardView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminController {
    private final AdminDashboardView view;

    private StudentController studentController;
    private LecturerController lecturerController;
    private CourseController courseController;
    private DepartmentController departmentController;
    private DegreeController degreeController;
    private TimeTableController timeTableController;

    public AdminController(AdminDashboardView view) {
        this.view = view;

        // Initialize sub-controllers
        this.studentController = new StudentController(view);
        this.lecturerController = new LecturerController(view);
        this.courseController = new CourseController(view);
        this.departmentController = new DepartmentController(view);
        this.degreeController = new DegreeController(view);
        this.timeTableController = new TimeTableController(view);

        initActions();
        // initTimeTableActions(); // Handled by TimeTableController now
    }

    private void initActions() {

        view.getStudentsBtn().addActionListener(e -> studentController.refreshStudentPanel());
        view.getLecturersBtn().addActionListener(e -> lecturerController.refreshPanel());
        view.getCoursesBtn().addActionListener(e -> courseController.refreshPanel());
        view.getDepartmentsBtn().addActionListener(e -> departmentController.refreshPanel());
        view.getDegreesBtn().addActionListener(e -> degreeController.refreshPanel());
        view.getTimeTableBtn().addActionListener(e -> timeTableController.refreshPanel());

        view.getLogoutButton().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(view, "Do you want to log out?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                view.dispose();
                JOptionPane.showMessageDialog(null, "Logged out successfully");
            }
        });
    }

    // initTimeTableActions removed - moved to TimeTableController
}