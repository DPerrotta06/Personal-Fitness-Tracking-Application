package org.example.personalfitnesstracker.Controllers;

import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;
import org.example.personalfitnesstracker.Views.CreateAccountView;
import org.example.personalfitnesstracker.Views.LoginView;
import org.example.personalfitnesstracker.Views.MainPageView;

public class LoginController extends BaseController {

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
            String email = loginView.getEmailField().getText().trim();
            String password = loginView.getPasswordField().getText().trim();

            if (DatabaseManager.userExists(email, password.getBytes())) {
                System.out.println("Login successful!");

                User user = getUserDataByEmail(email);
                if (user != null) {
                    MainPageView mainPageView = new MainPageView();
                    MainPageController mainPageController = new MainPageController(mainPageView);
                    mainPageController.show();
                    loginView.close();
                }
            } else {
                System.out.println("User " + email + " does not exist or incorrect password.");
            }
        });

        loginView.getCreateAccountLink().setOnAction(e -> {
            createAccountView = new CreateAccountView();
            CreateAccountController createAccountController = new CreateAccountController(createAccountView);
            createAccountController.show();
            loginView.close();
        });
    }

    private User getUserDataByEmail(String email) {
        ObservableList<User> users = DatabaseManager.getUserByEmail(email);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        System.out.println("User not found");
        return null;
    }
}
