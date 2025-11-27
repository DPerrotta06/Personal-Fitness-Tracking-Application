package org.example.personalfitnesstracker.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.Models.User;

import java.sql.Date;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;

/**
 * Controller responsible for creating and managing User objects. To be used by
 * JavaFX UI controllers (FXML) to keep logic out of the view.
 */
public class UserController extends BaseController {

    private final ObservableList<User> users;

    /**
     * Constructor
     */
    public UserController() {
        this.users = FXCollections.observableArrayList();
    }

    /**
     *
     * @return
     */
    public ObservableList<User> getUsers() {
        return users;
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
        User user = new User(userId, username, password, email, weight, height, dateOfBirth);
        users.add(user);
        DatabaseManager.addNewUserToDb(user);
        log("User created: " + username);
        return user;
    }
}
