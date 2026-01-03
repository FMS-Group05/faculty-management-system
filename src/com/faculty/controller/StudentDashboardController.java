package com.faculty.controller;

import com.faculty.model.User;
import com.faculty.view.StudentDashboardView;

import java.awt.event.ActionEvent;

public class StudentDashboardController {

    private final StudentDashboardView view;
    private final User user;

    private final StudentDetailsController profileController;
    private final StudentTimeTableController timeTableController;
    private final StudentCourseController courseController;

    public StudentDashboardController(StudentDashboardView view, User user) {
        this.view = view;
        this.user = user;

        // Initialize sub-controllers
        this.profileController = new StudentDetailsController(view.getProfilePanel(), user);
        this.timeTableController = new StudentTimeTableController(view.getTimetablePanel(), user);
        this.courseController = new StudentCourseController(view.getCourseEnrolledPanel(), user);

        initController();
    }

    private void initController() {
        // Profile Tab
        view.getBtnProfile().addActionListener((ActionEvent e) -> {
            profileController.loadProfile(user);
        });

        // Timetable Tab
        view.getBtnTimetable().addActionListener((ActionEvent e) -> {
            timeTableController.refreshPanel();
        });

        // Course Tab
        view.getBtnCourses().addActionListener((ActionEvent e) -> {
            courseController.loadData();
        });
    }
}
