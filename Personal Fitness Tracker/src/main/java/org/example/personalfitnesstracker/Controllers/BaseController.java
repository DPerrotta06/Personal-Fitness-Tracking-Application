package org.example.personalfitnesstracker.Controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import org.example.personalfitnesstracker.Models.User;

/**
 * Base controller with helper methods.
 */
public abstract class BaseController {

    private static final Logger logger = Logger.getLogger(BaseController.class.getName());
    private static FileHandler fileHandler;
    protected User loggedUser;

    public BaseController(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public BaseController() {

    }

    protected boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     *
     * @param value
     * @return
     */
    protected boolean isPositive(double value) {
        return value > 0;
    }

    /**
     *
     * @param value
     * @return
     */
    protected boolean isPositive(int value) {
        return value > 0;
    }

    static { //do we need this?
        try {
            fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);

        } catch (IOException e) {
            System.err.println("Failed to initialize log file: " + e.getMessage());
        }
    }

    /**
     * Logger function to call the logger
     * @param message 
     * @param level 
     */
    protected void log(String message, Level level) {
        logger.info(message);
    }

    /**
     * Using regex to check if an email is valid
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        // Correct Java regex (no slashes)
        String regex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        return email != null && email.matches(regex);
    }

    /**
     * Using regex to check if a password is valid. It must contain at least one
     * uppercase letter, one lowercase letter, one digit, one special character
     * and be at least 8 characters long
     *
     * @param password
     * @return
     */
    public static boolean isValidPassword(byte[] password) {
        if (password == null) return false;
        String pw = new String(password, StandardCharsets.UTF_8);
        // Correct Java regex (no slashes)
        String regex =
                "^(?=.*[a-z])" +        // at least one lowercase
                        "(?=.*[A-Z])" +         // at least one uppercase
                        "(?=.*\\d)" +           // at least one number
                        "(?=.*[@$!%*?&_])" +    // at least one special char
                        "[A-Za-z\\d@$!%*?&_]{8,}$"; // length ≥ 8
        return pw.matches(regex);
    }

    /**
     * Generic pop window of any kind that is chosen by the programmer
     *
     * @param title
     * @param msg
     * @param alertType
     * @param button
     */
    protected void showMessageWindow(String title, String msg, Alert.AlertType alertType, ButtonType button) {

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.getButtonTypes().setAll(button);

        DialogPane pane = alert.getDialogPane();
        pane.setStyle("-fx-background-color: #47d647; -fx-font-size: 14px;");

        alert.showAndWait();
    }

}
