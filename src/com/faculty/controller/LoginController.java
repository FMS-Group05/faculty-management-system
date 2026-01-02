package com.faculty.controller;

import com.faculty.dao.UserDAO;
import com.faculty.model.User;
import com.faculty.view.*;

import javax.swing.*;

public class LoginController {

    private final LoginView view;
    private final UserDAO dao = new UserDAO();

    public LoginController(LoginView view) {
        this.view = view;
        initActions();
    }

    private void initActions() {

        // ---------------- SIGN UP ----------------
        view.getSignUpButton().addActionListener(e -> {

            String username = view.getSignUpUsername();
            String password = view.getSignUpPassword();
            String confirm  = view.getSignUpConfirm();
            String role     = view.getSelectedRole();

            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(view, "Passwords do not match");
                return;
            }

            boolean ok = dao.register(username, password, role);

            JOptionPane.showMessageDialog(
                    view,
                    ok ? "Registration successful" : "Username already exists"
            );

            if (ok) view.clearSignUpFields();
        });

        // ---------------- SIGN IN ----------------
        view.getSignInButton().addActionListener(e -> {

            String username = view.getSignInUsername();
            String password = view.getSignInPassword();

            String role = dao.login(username, password);

            if (role == null) {
                JOptionPane.showMessageDialog(view, "Invalid login");
                return;
            }

            view.dispose();

            // Create a User object to pass to dashboards
            User user = new User(username, role);

            switch (role) {
                case "Admin" -> new AdminDashboardView(user).setVisible(true);
                case "Lecturer" -> new LecturerDashboardView(user).setVisible(true);

                // ✅ Added Student Dashboard Launch (using your new GUI)
                case "Student" -> {
                    StudentDashboardView s = new StudentDashboardView(user);
                    s.setVisible(true);
                    new StudentController(s);
                }

                default -> JOptionPane.showMessageDialog(null, "Unknown role: " + role);
            }
        });
    }
}
