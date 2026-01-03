package com.faculty.controller;

import com.faculty.dao.StudentDetailsDAO;
import com.faculty.dao.TimeTableDAO;
import com.faculty.model.User;
import com.faculty.view.TimeTablePanel;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.MediaTracker;

public class StudentTimeTableController {

    private final TimeTablePanel view;
    private final User user;
    private final StudentDetailsDAO studentDao;
    private final TimeTableDAO timeTableDao;
    private String studentDegree;

    public StudentTimeTableController(TimeTablePanel view, User user) {
        this.view = view;
        this.user = user;
        this.studentDao = new StudentDetailsDAO();
        this.timeTableDao = new TimeTableDAO();

        loadStudentDegree();
        initActions();
        updateImage();
    }

    private void loadStudentDegree() {
        try {
            studentDegree = studentDao.getStudentDegree(user.getUsername());
            if (studentDegree == null) {
                view.setStatus("Degree not found for user.");
            } else {
                view.setStatus("Timetable for: " + studentDegree);
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.setStatus("Error loading degree info.");
        }
    }

    private void initActions() {
        view.getYearComboBox().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateImage();
            }
        });
    }

    public void updateImage() {
        if (studentDegree == null)
            return;

        Integer year = (Integer) view.getYearComboBox().getSelectedItem();
        if (year != null) {
            String path = timeTableDao.getImagePath(studentDegree, year);
            if (path != null && !path.isEmpty()) {

                try {
                    ImageIcon icon = new ImageIcon(path);
                    if (icon.getImageLoadStatus() == MediaTracker.ERRORED) {
                        view.setImage(null);
                        view.setStatus("Image file not found: " + path);
                    } else {
                        view.setImage(icon);
                        view.setStatus("Showing timetable for " + studentDegree + " - Year " + year);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setImage(null);
                }
            } else {
                view.setImage(null);
                view.setStatus("No timetable uploaded for " + studentDegree + " - Year " + year);
            }
        }
    }

    public void refreshPanel() {
        loadStudentDegree();
        updateImage();
    }
}
