package org.example.personalfitnesstracker.Controllers;

import org.example.personalfitnesstracker.Views.LoginView;

public class LoginController {
    private final LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        setupHandlers();
    }

    public void show() {
        loginView.show();
    }

    private void setupHandlers() {
        loginView.getLoginButton().setOnAction(event -> {
           String username = loginView.getUsernameField().getText().trim();
           String password = loginView.getPasswordField().getText().trim();
           // temporary beta logic, need to properly handle exceptions later
            System.out.println("Login successful");
        });
        // temporary beta logic, need to properly handle exceptions later
        loginView.getCreateAccountLink().setOnAction(event -> {
            System.out.println("Create account clicked");
        });
    }
}
