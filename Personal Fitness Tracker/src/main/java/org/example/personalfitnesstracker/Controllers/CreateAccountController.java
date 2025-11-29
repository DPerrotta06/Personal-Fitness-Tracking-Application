package org.example.personalfitnesstracker.Controllers;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;
import org.example.personalfitnesstracker.Views.CreateAccountView;
import org.example.personalfitnesstracker.Views.MainPageView;

public class CreateAccountController extends BaseController {

    private final CreateAccountView createAccountView;
    private final DatabaseManager databaseManager;

    public CreateAccountController(CreateAccountView createAccountView) {
        this.createAccountView = createAccountView;
        try {
            this.databaseManager = new DatabaseManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setupHandlers();
    }

    public void show() {
        createAccountView.show();
    }

    private void setupHandlers() {
        // need to display proper error window later instead of printing to console
        createAccountView.getCreateButton().setOnAction(ev -> {
            String username = createAccountView.getUsernameField().getText();
            String email = createAccountView.getEmailField().getText();
            if (!email.contains("@")) {
                log("Invalid email format.");
                return;
            }
            String passwordText = createAccountView.getPasswordField().getText();
            byte[] password = passwordText.getBytes();
            String weightText = createAccountView.getWeightField().getText();
            String heightText = createAccountView.getHeightField().getText();
            double weight = Double.parseDouble(weightText);
            double height = Double.parseDouble(heightText);
            String dateOfBirthText = createAccountView.getDateOfBirthField().getText();
            // Format Date of Birth (yyyy-MM-dd)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = null;
            try {
                dob = (Date) dateFormat.parse(dateOfBirthText);
            } catch (ParseException e) {
                e.printStackTrace();
                log("Invalid Date format. Please use yyyy-MM-dd.");
                return;
            }
            createUser(0, password, email, weight, height, dob, username);
            // When Create Account Button is clicked
            createAccountView.getCreateAccountButton().setOnAction(e -> {
                MainPageView mainPageView = new MainPageView();
                MainPageController mainPageController = new MainPageController(mainPageView);
                mainPageController.show();
                createAccountView.close();
            });
        });
    }

    /**
     * Create a new User for the database to store
     *
     * @param userId
     * @param username
     * @param password
     * @param email
     * @param weight
     * @param height
     * @param dateOfBirth
     * @return
     */
    public User createUser(int userId,
            byte[] password,
            String email,
            double weight,
            double height,
            Date dateOfBirth,
            String username) {

        if (isNullOrEmpty(username) || password.length == 0 || isNullOrEmpty(email)) {
            log("Cannot create user: one or more required fields are empty.");
            return null;
        }

        if (!isPositive(weight) || !isPositive(height)) {
            log("Cannot create user: weight and height must be positive.");
            return null;
        }

        if (DatabaseManager.userExists(email, password) == true) {
            showError("Cannot create a duplicate User", " This user already exists!\nPlease enter a different Username and Password!");
        }
        User user = new User(userId, password, email, weight, height, dateOfBirth, username);
        DatabaseManager.addNewUserToDb(user);
        log("User created: " + username);
        return user;
    }
}
