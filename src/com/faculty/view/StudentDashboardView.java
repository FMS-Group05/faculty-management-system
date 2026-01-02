package com.faculty.view;

import com.faculty.model.User;
import com.faculty.util.ColorPalette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StudentDashboardView extends JFrame {

    private final JPanel contentPanel;
    private final ProfileDetailsPanel profilePanel;
    private final TimeTablePanel timetablePanel;
    private final CourseEntrolledPanel courseEnrolledPanel;
    private User user;

    public StudentDashboardView(User user) {
        this.user = user;
        profilePanel = new ProfileDetailsPanel(user);
        timetablePanel = new TimeTablePanel(user);
        courseEnrolledPanel = new CourseEntrolledPanel(user);
        setTitle("Student Dashboard");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- LEFT SIDEBAR ----------
        JPanel sidebar = new JPanel();
        sidebar.setBackground(ColorPalette.PRIMARY);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername());
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        sidebar.add(welcomeLabel);

        // Buttons
        JButton btnProfile = createSidebarButton("Profile Details ");
        JButton btnTimetable = createSidebarButton("Time Table ");
        JButton btnCourses = createSidebarButton("Course Enrolled ");


        // Add buttons + spacing
        sidebar.add(btnProfile);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        sidebar.add(btnTimetable);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        sidebar.add(btnCourses);
        sidebar.add(Box.createVerticalGlue());

        // Logout button
        JButton btnLogout = new JButton("Logout");
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setFocusPainted(false);
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setForeground(ColorPalette.PRIMARY);
        btnLogout.setMaximumSize(new Dimension(150, 35));
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));

// Add ActionListener for logout
        btnLogout.addActionListener(e -> {
            // Assuming LoginView is a JFrame
            LoginView loginView = new LoginView(); // create login view
            loginView.setVisible(true);           // show login view

            // Close current window
            SwingUtilities.getWindowAncestor(btnLogout).dispose();
        });

        sidebar.add(btnLogout);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));


        // ---------- RIGHT CONTENT PANEL ----------
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

// Shift panel to left by adding a border with right padding
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
// Insets: top, left, bottom, right


        // Default view
        showPanel(profilePanel);

        // Button actions
        btnProfile.addActionListener((ActionEvent e) -> showPanel(profilePanel));
        btnTimetable.addActionListener((ActionEvent e) -> showPanel(timetablePanel));
        btnCourses.addActionListener((ActionEvent e) -> showPanel(courseEnrolledPanel));

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ---------- BUTTON CREATOR ----------
    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setForeground(ColorPalette.PRIMARY);
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Rounded border with padding
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorPalette.PRIMARY, 1, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(240, 235, 255)); // light purple hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
            }
        });

        return btn;
    }

    // ---------- PANEL SWITCHER ----------
    private void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public ProfileDetailsPanel getProfilePanel() { return profilePanel; }
    public TimeTablePanel getTimetablePanel() { return timetablePanel; }
    public CourseEntrolledPanel getCourseEnrolledPanel() { return courseEnrolledPanel; }
    public User getUser() { return user; }
}
