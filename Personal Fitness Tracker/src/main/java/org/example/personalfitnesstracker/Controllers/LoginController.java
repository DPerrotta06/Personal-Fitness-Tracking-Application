package org.example.personalfitnesstracker.Controllers;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;
import org.example.personalfitnesstracker.Views.CreateAccountView;
import org.example.personalfitnesstracker.Views.LoginView;
import org.example.personalfitnesstracker.Views.MainPageView;

public final class LoginController extends BaseController {

    private final LoginView loginView;
    private CreateAccountView createAccountView;

    /**
     * Constructor
     *
     * @param loginView
     */
    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        setupHandlers();
    }

    /**
     * Shows the login page
     */
    public void show() {
        loginView.show();
    }

    /**
     * Takes care of the general setup and event handling from button clicks to
     * retrieving database data.
     */
    public void setupHandlers() {
        loginView.getLoginButton().setOnAction(event -> {
            String email = loginView.getEmailField().getText().trim();
            String password = loginView.getPasswordField().getText().trim();
            User user = DatabaseManager.getUserByEmailAndPw(email, password.getBytes());
            if (DatabaseManager.userExists(email, password.getBytes()) && user != null) {
                MainPageView mainPageView = new MainPageView();
                MainPageController mainPageController = new MainPageController(mainPageView, user);
                mainPageController.show();
                loginView.close();
            } else {
                showMessageWindow("Unknown user", "User " + email + " does not exist or incorrect password.", AlertType.WARNING, ButtonType.OK);
            }
        });
        loginView.getCreateAccountLink().setOnAction(e -> {
            createAccountView = new CreateAccountView();
            CreateAccountController createAccountController = new CreateAccountController(createAccountView);
            createAccountController.show();
            loginView.close();
        });
    }
}
