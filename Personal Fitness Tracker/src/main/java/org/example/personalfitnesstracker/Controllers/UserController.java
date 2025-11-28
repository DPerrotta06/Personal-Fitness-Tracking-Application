package org.example.personalfitnesstracker.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;


/**
 * Controller responsible managing User objects. To be used by JavaFX UI
 * controllers (FXML) to keep logic out of the view.
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
        return DatabaseManager.getUserByEmail();
    }
}
