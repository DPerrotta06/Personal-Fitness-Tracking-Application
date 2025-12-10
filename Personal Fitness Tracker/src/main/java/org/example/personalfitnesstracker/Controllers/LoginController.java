package org.example.personalfitnesstracker.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;
import org.example.personalfitnesstracker.Views.CreateAccountView;
import org.example.personalfitnesstracker.Views.LoginView;

import java.io.IOException;

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

            if (DatabaseManager.userExists(email, password.getBytes())) {
                showInfo("INFO", "Login Successful!");
                log("User " + email + " logged in!");

                User user = getUserDataByEmail(email);

                 if (user != null) {

                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/org/example/personalfitnesstracker/Views/MainPageView.fxml")
                        );
                        Parent root = loader.load();

                        MainPageController controller = loader.getController();
                        controller.setUser(user);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Fitness Tracker - Main Page");
                        stage.show();

                        loginView.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                showError("ERROR", "User " + email + " does not exist or incorrect password.");
                log("User " + email + " does not exist or incorrect password.");
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
        showError("ERROR", "User " + email + " does not exist");
        log("User " + email + " does not exist");
        return null;
    }
}
