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
            String confirm = view.getSignUpConfirm();
            String role = view.getSelectedRole();

            // Check if passwords match
            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(view, "Passwords do not match");
                return;
            }

            // Register
            boolean ok = dao.register(username, password, role);

            JOptionPane.showMessageDialog(view,
                    ok ? "Registration successful" : "Username already exists");

            // Clear fields after success
            if (ok) view.clearSignUpFields();
        });

        // ---------------- SIGN IN ----------------
        view.getSignInButton().addActionListener(e -> {

            String username = view.getSignInUsername();
            String password = view.getSignInPassword();

            String role = dao.getRole(username, password); // use method that returns role

            if (role == null) {
                JOptionPane.showMessageDialog(view, "Invalid login");
                return;
            }

            view.dispose(); // close login

            User user = new User(username, role);

            switch (role) {
                case "Admin" -> new AdminDashboardView(user).setVisible(true);
                case "Lecturer" -> new StudentDashboardView(user).setVisible(true);
                default -> new StudentDashboardView(user).setVisible(true);
            }
        });
    }
}
