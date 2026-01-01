package com.faculty.main;

import com.faculty.view.LoginView;
import com.faculty.controller.LoginController;
import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView view = new LoginView();
            new LoginController(view);
            view.setVisible(true);
        });
    }
}