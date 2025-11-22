package org.example.personalfitnesstracker.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.Models.User;

import java.time.LocalDateTime;

/**
 * Controller responsible for creating and managing User objects.
 * To be used by JavaFX UI controllers (FXML) to keep logic out of the view.
 */
public class UserController extends BaseController {

    private final ObservableList<User> users;

    public UserController() {
        this.users = FXCollections.observableArrayList();
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public User createUser(int userId,
                           String username,
                           String password,
                           String email,
                           double weight,
                           double height,
                           LocalDateTime dateOfBirth) {

        if (isNullOrEmpty(username) || isNullOrEmpty(password) || isNullOrEmpty(email)) {
            log("Cannot create user: one or more required fields are empty.");
            return null;
        }

        if (!isPositive(weight) || !isPositive(height)) {
            log("Cannot create user: weight and height must be positive.");
            return null;
        }

        User user = new User(userId, username, password, email, weight, height, dateOfBirth);
        users.add(user);
        log("User created: " + username);
        return user;
    }
}
