package com.faculty.main;

import com.faculty.view.LoginView;
import com.faculty.controller.LoginController;

public class MainApp {
    public static void main(String[] args) {

        LoginView view = new LoginView();
        new LoginController(view);
        view.setVisible(true);
    }
}
