package org.example.personalfitnesstracker.Controllers;

import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;
import org.example.personalfitnesstracker.Views.CreateAccountView;
import org.example.personalfitnesstracker.Views.LoginView;
import org.example.personalfitnesstracker.Views.MainPageView;

import java.io.IOException;

public class LoginController extends BaseController{

    private final LoginView loginView;
    private CreateAccountView createAccountView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        try {
            DatabaseManager databaseManager = new DatabaseManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setupHandlers();
    }

    public void show() {
        loginView.show();
    }

    private void setupHandlers() {

        loginView.getLoginButton().setOnAction(event -> {
            String email = loginView.getEmailField().getText().trim();
            byte[] password = loginView.getPasswordField().getText().trim().getBytes();

            if (DatabaseManager.userExists(email, password)) {
                User user = getUserDataByEmail(email);
                if (user != null) {
                    MainPageView mainPageView = new MainPageView();
                    MainPageController mainPageController = new MainPageController(mainPageView);
                    mainPageController.show();
                }
            }
            else {
                System.out.println("User " + email + " does not exist");
            }
        });


        // When create account link is clicked
        loginView.getCreateAccountLink().setOnAction(e -> {
            createAccountView = new CreateAccountView();
            CreateAccountController createAccountController = new CreateAccountController(createAccountView);
            createAccountController.show();
            loginView.close();
        });
    }

    // looping through db until email of user is found
    private User getUserDataByEmail(String email) {
        ObservableList<User> users = DatabaseManager.getUserByEmail();
        for (User user : users) {
            if (user.emailProperty().equals(email)) {
                return user;
            }
        }
        System.out.println("User not found");
        return null;
    }
}
