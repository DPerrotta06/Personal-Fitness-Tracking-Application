package org.example.personalfitnesstracker.Controllers;

import org.example.personalfitnesstracker.Views.CreateAccountView;
import org.example.personalfitnesstracker.Controllers.LoginController;
import org.example.personalfitnesstracker.Views.LoginView;

public class LoginController {

    private final LoginView loginView;
    private CreateAccountView createAccountView;

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
            System.out.println("Login successful (beta)");
        });

        loginView.getCreateAccountLink().setOnAction(e -> {
            createAccountView = new CreateAccountView();
            CreateAccountController createAccountController = new CreateAccountController(createAccountView);
            createAccountController.show();
            loginView.close();
        });
    }
}
