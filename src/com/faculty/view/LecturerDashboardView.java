package com.faculty.view;

import com.faculty.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LecturerDashboardView extends JFrame {

    private final Color PURPLE = new Color(132, 84, 255);

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JButton profileBtn, messagesBtn, logoutBtn;

    private LecturerProfilePanel profilePanel;
    private LecturerMessagePanel messagePanel;

    public LecturerDashboardView(User user) {

        setTitle("Faculty Management System - Lecturer");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(sidebar(user), BorderLayout.WEST);

        // ===== CENTER CONTENT =====
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // ✅ FIX: Constructor matches now
        profilePanel = new LecturerProfilePanel(user);
        messagePanel = new LecturerMessagePanel();

        cardPanel.add(profilePanel, "Profile");
        cardPanel.add(messagePanel, "Messages");

        add(cardPanel, BorderLayout.CENTER);

        initNavigation();
    }

    // ===== SIDEBAR =====
    private JPanel sidebar(User user) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panel.setPreferredSize(new Dimension(280, 600));
        panel.setBackground(PURPLE);

        JLabel welcome = new JLabel("Welcome, " + user.getUsername());
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setBorder(new EmptyBorder(25, 0, 15, 0));
        panel.add(welcome);

        profileBtn = menuButton("Profile", "👤", true);
        messagesBtn = menuButton("Messages", "💬", false);

        panel.add(profileBtn);
        panel.add(messagesBtn);

        // Logout
        logoutBtn = new JButton("Log out");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoutBtn.setPreferredSize(new Dimension(160, 45));
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(PURPLE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(new LineBorder(Color.WHITE, 1, true));

        JPanel logoutBox = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 40));
        logoutBox.setOpaque(false);
        logoutBox.add(logoutBtn);

        panel.add(logoutBox);
        return panel;
    }

    private JButton menuButton(String text, String icon, boolean selected) {
        JButton btn = new JButton("  " + icon + "     " + text);
        btn.setPreferredSize(new Dimension(250, 45));
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setBorder(new EmptyBorder(0, 15, 0, 0));
        btn.setForeground(selected ? PURPLE : new Color(160, 160, 160));
        return btn;
    }

    // ===== NAVIGATION =====
    private void initNavigation() {

        profileBtn.addActionListener(e -> switchPanel("Profile", profileBtn));
        messagesBtn.addActionListener(e -> switchPanel("Messages", messagesBtn));

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged out successfully");
            dispose();
        });
    }

    private void switchPanel(String name, JButton activeBtn) {
        cardLayout.show(cardPanel, name);
        updateSidebarStyles(activeBtn);
    }

    private void updateSidebarStyles(JButton activeBtn) {
        JButton[] buttons = {profileBtn, messagesBtn};
        for (JButton btn : buttons) {
            btn.setForeground(btn == activeBtn ? PURPLE : new Color(160, 160, 160));
        }
    }
}
