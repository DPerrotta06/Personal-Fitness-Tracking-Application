package org.example.personalfitnesstracker.Controllers;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.logging.Level;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;
import org.example.personalfitnesstracker.Views.CreateAccountView;

public final class CreateAccountController extends BaseController {

    private final CreateAccountView createAccountView;

    /**
     * Constructor
     *
     * @param createAccountView
     */
    public CreateAccountController(CreateAccountView createAccountView) {
        this.createAccountView = createAccountView;
        setUpHandlers();
    }

    /**
     * Load up UI
     */
    public void show() {
        createAccountView.show();
    }

    /**
     * Create a new User and add it to the DB
     *
     * @param userId
     * @param password
     * @param email
     * @param weight
     * @param height
     * @param dateOfBirth
     * @param username
     * @return
     */
    public User createUser(int userId, byte[] password, String email, double weight, double height, LocalDate dateOfBirth, String username) {
        if (isNullOrEmpty(username) || password.length == 0 || isNullOrEmpty(email)) {
            return null;
        }
        if (!isPositive(weight) || !isPositive(height)) {
            return null;
        }
        if (DatabaseManager.userExists(email, password)) {
            showMessageWindow("Cannot create a duplicate User", "This user already exists! Please enter a different Username and Password.", AlertType.WARNING, ButtonType.OK);
            return null;
        }
        User user = new User(userId, password, email, weight, height, dateOfBirth, username);
        DatabaseManager.addNewUserToDb(user);
        return user;
    }

    /**
     *
     */
    public void setUpHandlers() {
        createAccountView.getCreateButton().setOnAction(ev -> {
            try {
                String username = createAccountView.getUsernameField().getText();
                String email = createAccountView.getEmailField().getText();
                String passwordText = createAccountView.getPasswordField().getText();
                String weightText = createAccountView.getWeightField().getText();
                String heightText = createAccountView.getHeightField().getText();
                String dobText = createAccountView.getDateOfBirthField().getText();
                if (username.isEmpty() || !isValidEmail(email) && !isValidPassword(passwordText.getBytes()) || dobText.isEmpty()) {
                    showMessageWindow("Account Creation Error", "Please fill in all fields to create your account.", AlertType.ERROR, ButtonType.OK);
                }
                byte[] password = passwordText.getBytes();
                double weight = Double.parseDouble(weightText);
                double height = Double.parseDouble(heightText);
                LocalDate dob = LocalDate.parse(dobText);
                User newUser = createUser(0, password, email, weight, height, dob, username);

                if (newUser != null) {
                    // Load the MainPage FXML
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/org/example/personalfitnesstracker/Views/MainPageView.fxml")
                        );
                        Parent root = loader.load();

                        MainPageController controller = loader.getController();
                        controller.setUser(newUser);
                        showMessageWindow("INFO", "User created successfully", AlertType.INFORMATION, ButtonType.OK);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Fitness Tracker - Main Page");
                        stage.show();

                        createAccountView.close();

                    } catch (IOException e) {
                        log("File error." + e, Level.SEVERE);
                    }
                }
            } catch (DateTimeException e) {
                log("Date error." + e, Level.SEVERE);
                showMessageWindow("Invalid date format!", "Date must in the format of YYYY-MM-DD", AlertType.ERROR, ButtonType.OK);
            }
        });
    }
}
