package com.faculty.controller;

import com.faculty.dao.UserDAO;
import com.faculty.model.User;
import com.faculty.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private LoginView view;
    private UserDAO userDAO;

    public LoginController(LoginView view) {
        this.view = view;
        this.userDAO = new UserDAO();


        if (view.getSignInButton() != null) {
            view.getSignInButton().addActionListener(new LoginHandler());
        }

        if (view.getSignUpButton() != null) {
            view.getSignUpButton().addActionListener(new RegisterHandler());
        }
    }


    class LoginHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = view.getLoginUsername();
            String password = view.getLoginPassword();
            String role = view.getSelectedRole();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                        "Please enter username and password");
                return;
            }

            User user = userDAO.login(username, password);

            if (user != null && user.getRole().equalsIgnoreCase(role)) {
                view.dispose();

                if (user.getRole().equalsIgnoreCase("Admin")) {
                    new AdminDashboardView(user).setVisible(true);
                } else if (user.getRole().equalsIgnoreCase("Student")) {
                    new StudentDashboardView(user).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Lecturer dashboard not implemented yet");
                }

            } else {
                JOptionPane.showMessageDialog(view,
                        "Invalid login credentials or role mismatch");
            }
        }
    }


    class RegisterHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = view.getLoginUsername();
            String password = view.getLoginPassword();
            String confirm  = view.getConfirmPassword();
            String role     = view.getSelectedRole();

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                        "All fields are required");
                return;
            }

            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(view,
                        "Passwords do not match");
                return;
            }

            User user = new User(username, password, role);

            if (userDAO.register(user)) {
                JOptionPane.showMessageDialog(view,
                        "Registration successful! Please Sign In.");
            } else {
                JOptionPane.showMessageDialog(view,
                        "Registration failed. Username may already exist.");
            }
        }
    }
}
