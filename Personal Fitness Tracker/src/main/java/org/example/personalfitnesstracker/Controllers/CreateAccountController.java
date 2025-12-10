package org.example.personalfitnesstracker.Controllers;

import java.time.DateTimeException;
import java.time.LocalDate;
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

            String username = createAccountView.getUsernameField().getText();
            String email = createAccountView.getEmailField().getText();

            if (!email.contains("@")) {
                showError("ERROR", "Invalid email address");
                return;
            }

            String passwordText = createAccountView.getPasswordField().getText();
            byte[] password = passwordText.getBytes();

            double weight = Double.parseDouble(createAccountView.getWeightField().getText());
            double height = Double.parseDouble(createAccountView.getHeightField().getText());

            LocalDate dob;
            try {
                dob = LocalDate.parse(createAccountView.getDateOfBirthField().getText());
            } catch (DateTimeException e) {
                showError("ERROR", "Invalid date of birth format");
                return;
            }

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
                    showInfo("INFO", "User created successfully");

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Fitness Tracker - Main Page");
                    stage.show();

                    createAccountView.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public User createUser(int userId, byte[] password, String email, double weight, double height, LocalDate dateOfBirth, String username) {
        if (isNullOrEmpty(username) || password.length == 0 || isNullOrEmpty(email)) {
            showError("ERROR", "Cannot create user: one or more required fields are empty.");
            return null;
        }

        if (!isPositive(weight) || !isPositive(height)) {
            showError("ERROR", "Cannot create user: weight and height must be positive.");
            return null;
        }

        if (DatabaseManager.userExists(email, password)) {
            showError("Cannot create a duplicate User", "This user already exists! Please enter a different Username and Password.");
            return null;
        }

        User user = new User(userId, password, email, weight, height, dateOfBirth, username);
        DatabaseManager.addNewUserToDb(user);
        return user;
    }
}
