package com.faculty.view;

import com.faculty.model.User;
import com.faculty.util.ColorPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StudentDashboardView extends JFrame {

    private final JPanel contentPanel;
    private final ProfileDetailsPanel profilePanel;
    private final TimeTablePanel timetablePanel;
    private final CourseEntrolledPanel courseEnrolledPanel;
    private User user;

    private JButton btnProfile, btnTimetable, btnCourses, btnLogout;
    private final Color PURPLE = new Color(132, 84, 255);

    // Track which button is currently highlighted
    private JButton selectedButton;

    // Controllers
    @SuppressWarnings("unused")
    private com.faculty.controller.StudentDashboardController dashboardController;

    public StudentDashboardView(User user) {
        this.user = user;

        profilePanel = new ProfileDetailsPanel(user);
        timetablePanel = new TimeTablePanel(user);
        courseEnrolledPanel = new CourseEntrolledPanel(user);

        // Initialize Timetable logic
        // timeTableController = new
        // com.faculty.controller.StudentTimeTableController(timetablePanel, user);

        setTitle("Student Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== LEFT SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(280, 600));
        sidebar.setBackground(PURPLE);
        sidebar.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Welcome label
        JLabel welcome = new JLabel("Welcome, " + user.getUsername());
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setBorder(new EmptyBorder(25, 20, 25, 20));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(welcome);

        // Buttons with emojis (initially only Profile highlighted)
        btnProfile = menuButton("Profile Details", "👤", true);
        btnTimetable = menuButton("Time Table", "🗓️", false);
        btnCourses = menuButton("Course Enrolled", "📚", false);

        // Align buttons
        btnProfile.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnTimetable.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCourses.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(btnProfile);
        sidebar.add(Box.createVerticalStrut(15)); // space between buttons
        sidebar.add(btnTimetable);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(btnCourses);

        // Set default selected button
        selectedButton = btnProfile;

        // Add vertical glue to push logout to bottom
        sidebar.add(Box.createVerticalGlue());

        // Logout button
        btnLogout = new JButton("Log Out");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogout.setPreferredSize(new Dimension(160, 45));
        btnLogout.setMaximumSize(new Dimension(200, 45));
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setForeground(PURPLE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(new LineBorder(Color.WHITE, 1, true));

        sidebar.add(btnLogout);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20))); // bottom padding

        // Logout action
        btnLogout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged out successfully");
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
            dispose();
        });

        // Hover effect for logout button
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(Color.WHITE);
                btnLogout.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(Color.WHITE);
                btnLogout.setForeground(PURPLE);
            }
        });

        add(sidebar, BorderLayout.WEST);

        // ===== RIGHT CONTENT PANEL =====
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        add(contentPanel, BorderLayout.CENTER);

        // Default view
        showPanel(profilePanel);

        // ===== BUTTON NAVIGATION WITH HIGHLIGHT =====
        btnProfile.addActionListener((ActionEvent e) -> {
            showPanel(profilePanel);
            highlightButton(btnProfile);
        });

        btnTimetable.addActionListener((ActionEvent e) -> {
            showPanel(timetablePanel);
            highlightButton(btnTimetable);
        });

        btnCourses.addActionListener((ActionEvent e) -> {
            showPanel(courseEnrolledPanel);
            highlightButton(btnCourses);
        });

        setVisible(true);

        // Initialize Controller
        this.dashboardController = new com.faculty.controller.StudentDashboardController(this, user);
    }

    // Sidebar button with emoji
    private JButton menuButton(String text, String emoji, boolean selected) {
        JButton btn = new JButton("  " + emoji + "     " + text);
        btn.setPreferredSize(new Dimension(250, 45));
        btn.setMaximumSize(new Dimension(250, 45));
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setBorder(new EmptyBorder(0, 15, 0, 0));
        btn.setForeground(selected ? PURPLE : new Color(160, 160, 160));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn != selectedButton) {
                    btn.setBackground(new Color(240, 235, 255));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn != selectedButton) {
                    btn.setBackground(Color.WHITE);
                }
            }
        });

        return btn;
    }

    // Highlight only the clicked button
    private void highlightButton(JButton button) {
        if (selectedButton != null) {
            selectedButton.setForeground(new Color(160, 160, 160)); // reset previous
            selectedButton.setBackground(Color.WHITE);
        }

        button.setForeground(PURPLE);
        button.setBackground(new Color(240, 235, 255));
        selectedButton = button;
    }

    // Switch content panels
    private void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ===== GETTERS =====
    public ProfileDetailsPanel getProfilePanel() {
        return profilePanel;
    }

    public TimeTablePanel getTimetablePanel() {
        return timetablePanel;
    }

    public CourseEntrolledPanel getCourseEnrolledPanel() {
        return courseEnrolledPanel;
    }

    public JButton getBtnProfile() {
        return btnProfile;
    }

    public JButton getBtnTimetable() {
        return btnTimetable;
    }

    public JButton getBtnCourses() {
        return btnCourses;
    }

    public User getUser() {
        return user;
    }
}
