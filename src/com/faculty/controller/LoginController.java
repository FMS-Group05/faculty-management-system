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

        // SIGN UP
        view.getSignUpButton().addActionListener(e -> {
            boolean ok = dao.register(
                    view.getLoginUsername(),
                    view.getLoginPassword(),
                    view.getSelectedRole()
            );

            JOptionPane.showMessageDialog(view,
                    ok ? "Registration successful" : "Username already exists");
        });

        // SIGN IN
        view.getSignInButton().addActionListener(e -> {
            String role = dao.login(
                    view.getLoginUsername(),
                    view.getLoginPassword()
            );

            if (role == null) {
                JOptionPane.showMessageDialog(view, "Invalid login");
                return;
            }

            view.dispose(); // close login

            // Create a User object using the username and role
            User user = new User(view.getLoginUsername(), role);

            // Open the correct dashboard
            switch (role) {
                case "Admin" -> new AdminDashboardView(user).setVisible(true);
                case "Lecturer" -> new StudentDashboardView(user).setVisible(true);
                default -> new StudentDashboardView(user).setVisible(true);
            }
        });
    }
}
