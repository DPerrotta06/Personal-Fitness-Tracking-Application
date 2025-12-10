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
import java.util.logging.Level;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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
                showMessageWindow("Login Status", "Login Successful!", AlertType.INFORMATION, ButtonType.OK);
                User user = getUserDataByEmail(email, password.getBytes());

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
                        log("Unexpected File Exception." + e, Level.SEVERE); //logger
                    }
                }
            } else {
                showMessageWindow("ERROR", "User " + email + " does not exist or incorrect password.", AlertType.ERROR, ButtonType.OK);
                log("User " + email + " does not exist or incorrect password.", Level.WARNING);
            }
        });

        loginView.getCreateAccountLink().setOnAction(e -> {
            createAccountView = new CreateAccountView();
            CreateAccountController createAccountController = new CreateAccountController(createAccountView);
            createAccountController.show();
            loginView.close();
        });
    }

    /**
     *
     * @param email
     * @param pw
     * @return
     */
    private User getUserDataByEmail(String email, byte[] pw) {
        User user = DatabaseManager.getUserByEmailAndPw(email, pw);
        if (user != null) {
            return user;
        }
        showMessageWindow("ERROR", "User " + email + " does not exist", AlertType.ERROR, ButtonType.OK);
        log("User " + email + " does not exist", Level.SEVERE);
        return null;
    }
}
