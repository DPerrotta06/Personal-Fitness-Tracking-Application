package org.example.personalfitnesstracker.Controllers;

import org.example.personalfitnesstracker.Views.CreateAccountView;

public class CreateAccountController {
    private final CreateAccountView createAccountView;

    public CreateAccountController(CreateAccountView createAccountView) {
        this.createAccountView = createAccountView;
        setupHandlers();
    }

    public void show() {
        createAccountView.show();
    }

    // Basic beta logic, will need to properly integrate with UserController
    private void setupHandlers() {
        createAccountView.getCreateButton().setOnAction(ev -> {
            String username = createAccountView.getUsernameField().getText();
            String email = createAccountView.getEmailField().getText();
            String password = createAccountView.getPasswordField().getText();
            System.out.println("Account created (beta): " + username + " | " + email + " | " + password);
        });
    }
}
