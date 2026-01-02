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

    public StudentDashboardView(User user) {
        this.user = user;

        profilePanel = new ProfileDetailsPanel(user);
        timetablePanel = new TimeTablePanel(user);
        courseEnrolledPanel = new CourseEntrolledPanel(user);

        setTitle("Student Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== LEFT SIDEBAR =====
        JPanel sidebar = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        sidebar.setPreferredSize(new Dimension(280, 600));
        sidebar.setBackground(PURPLE);

        JLabel welcome = new JLabel("Welcome, " + user.getUsername());
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setBorder(new EmptyBorder(25, 0, 15, 0));
        sidebar.add(welcome);

        // Buttons with emojis
        btnProfile = menuButton("Profile Details", "👤", true);
        btnTimetable = menuButton("Time Table", "🗓️", false);
        btnCourses = menuButton("Course Enrolled", "📚", false);

        sidebar.add(btnProfile);
        sidebar.add(btnTimetable);
        sidebar.add(btnCourses);

        // Logout button
        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogout.setPreferredSize(new Dimension(160, 45));
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setForeground(PURPLE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(new LineBorder(Color.WHITE, 1, true));

        JPanel logoutBox = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 40));
        logoutBox.setOpaque(false);
        logoutBox.add(btnLogout);
        sidebar.add(logoutBox);

        // Logout action
        btnLogout.addActionListener(e -> {
            // Show logout message
            JOptionPane.showMessageDialog(this, "Logged out successfully");

            // Open login/signup window
            LoginView loginView = new LoginView();
            loginView.setVisible(true);  // opens login window (can default to signup)

            // Close current dashboard
            dispose();
        });


        add(sidebar, BorderLayout.WEST);

        // ===== RIGHT CONTENT PANEL =====
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // left padding
        add(contentPanel, BorderLayout.CENTER);

        // Default view
        showPanel(profilePanel);

        // Button navigation
        btnProfile.addActionListener((ActionEvent e) -> showPanel(profilePanel));
        btnTimetable.addActionListener((ActionEvent e) -> showPanel(timetablePanel));
        btnCourses.addActionListener((ActionEvent e) -> showPanel(courseEnrolledPanel));

        setVisible(true);
    }

    // Lecturer-style sidebar button with emoji
    private JButton menuButton(String text, String emoji, boolean selected) {
        JButton btn = new JButton("  " + emoji + "     " + text);
        btn.setPreferredSize(new Dimension(250, 45));
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16)); // emoji-friendly font
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setBorder(new EmptyBorder(0, 15, 0, 0));
        btn.setForeground(selected ? PURPLE : new Color(160, 160, 160));

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

    // Switch content panels
    private void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ===== GETTERS =====
    public ProfileDetailsPanel getProfilePanel() { return profilePanel; }
    public TimeTablePanel getTimetablePanel() { return timetablePanel; }
    public CourseEntrolledPanel getCourseEnrolledPanel() { return courseEnrolledPanel; }
    public User getUser() { return user; }
}
