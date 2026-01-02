package com.faculty.view;

import com.faculty.model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AdminDashboardView extends JFrame {

    private final Color PURPLE = new Color(132, 84, 255);
    private JPanel cardPanel;
    private CardLayout cardLayout;


    private JButton studentsBtn, lecturersBtn, coursesBtn, departmentsBtn, degreesBtn, timeTableBtn, logoutBtn;


    private StudentPanel studentPanel;
    private LecturerPanel lecturerPanel;
    private CoursePanel coursePanel;
    private DepartmentPanel departmentPanel;
    private DegreePanel degreePanel;
    private TimeTable timeTablePanel;

    public AdminDashboardView(User user) {
        setTitle("Faculty Management System - Admin");
        setSize(1000, 600); // Window size 1000x600
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());


        add(sidebar(), BorderLayout.WEST);


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        studentPanel = new StudentPanel();
        lecturerPanel = new LecturerPanel();
        coursePanel = new CoursePanel();
        departmentPanel = new DepartmentPanel();
        degreePanel = new DegreePanel();
        timeTablePanel = new TimeTable(); // ✅ නව TimeTable පැනලය initialize කිරීම

        cardPanel.add(studentPanel, "Students");
        cardPanel.add(lecturerPanel, "Lecturers");
        cardPanel.add(coursePanel, "Courses");
        cardPanel.add(departmentPanel, "Departments");
        cardPanel.add(degreePanel, "Degrees");
        cardPanel.add(timeTablePanel, "TimeTable"); // ✅ CardLayout එකට එක් කිරීම

        add(cardPanel, BorderLayout.CENTER);


        initNavigation();
    }

    private JPanel sidebar() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(280, 600));
        panel.setBackground(PURPLE);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        JLabel welcome = new JLabel("Welcome, Admin");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setBorder(new EmptyBorder(25, 0, 15, 0));
        panel.add(welcome);


        studentsBtn = menuButton("Students", "\uD83D\uDC64", true);
        lecturersBtn = menuButton("Lecturers", "\uD83D\uDC65", false);
        coursesBtn = menuButton("Courses", "\uD83D\uDCD6", false);
        departmentsBtn = menuButton("Departments", "\uD83C\uDFDB", false);
        degreesBtn = menuButton("Degrees", "\uD83C\uDF93", false);
        timeTableBtn = menuButton("Time Table", "\uD83D\uDCC5", false); // ✅ 📅 Icon එක සමඟ නව බොත්තම

        panel.add(studentsBtn);
        panel.add(lecturersBtn);
        panel.add(coursesBtn);
        panel.add(departmentsBtn);
        panel.add(degreesBtn);
        panel.add(timeTableBtn);


        logoutBtn = new JButton("Log out");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoutBtn.setPreferredSize(new Dimension(160, 45));
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(PURPLE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(new LineBorder(Color.WHITE, 1, true));

        JPanel logoutContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 40));
        logoutContainer.setOpaque(false);
        logoutContainer.add(logoutBtn);
        panel.add(logoutContainer);

        return panel;
    }

    private JButton menuButton(String text, String icon, boolean selected) {
        JButton btn = new JButton("  " + icon + "     " + text);
        btn.setPreferredSize(new Dimension(250, 45));
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(0, 15, 0, 0));
        btn.setForeground(selected ? PURPLE : new Color(160, 160, 160));
        return btn;
    }

    private void initNavigation() {
        studentsBtn.addActionListener(e -> switchPanel("Students", studentsBtn));
        lecturersBtn.addActionListener(e -> switchPanel("Lecturers", lecturersBtn));
        coursesBtn.addActionListener(e -> switchPanel("Courses", coursesBtn));
        departmentsBtn.addActionListener(e -> switchPanel("Departments", departmentsBtn));
        degreesBtn.addActionListener(e -> switchPanel("Degrees", degreesBtn));
        timeTableBtn.addActionListener(e -> switchPanel("TimeTable", timeTableBtn)); // ✅ Navigation එක එක් කිරීම
        
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
    }

    private void switchPanel(String name, JButton activeBtn) {
        cardLayout.show(cardPanel, name);
        updateSidebarStyles(activeBtn);
    }

    private void updateSidebarStyles(JButton activeBtn) {
        JButton[] buttons = {studentsBtn, lecturersBtn, coursesBtn, departmentsBtn, degreesBtn, timeTableBtn};
        for (JButton btn : buttons) {
            btn.setForeground(btn == activeBtn ? PURPLE : new Color(160, 160, 160));
        }
    }

    // Getters for Controller
    public StudentPanel getStudentPanel() { return studentPanel; }
    public LecturerPanel getLecturerPanel() { return lecturerPanel; }
    public CoursePanel getCoursePanel() { return coursePanel; }
    public DepartmentPanel getDepartmentPanel() { return departmentPanel; }
    public DegreePanel getDegreePanel() { return degreePanel; }
    public TimeTable getTimeTablePanel() { return timeTablePanel; } // ✅ නව Getter
    public JButton getLogoutButton() { return logoutBtn; }
}