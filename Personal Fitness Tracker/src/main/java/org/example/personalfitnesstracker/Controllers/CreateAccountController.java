package org.example.personalfitnesstracker.Controllers;

import java.sql.Date;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;
import org.example.personalfitnesstracker.Views.CreateAccountView;

public class CreateAccountController extends BaseController {

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
