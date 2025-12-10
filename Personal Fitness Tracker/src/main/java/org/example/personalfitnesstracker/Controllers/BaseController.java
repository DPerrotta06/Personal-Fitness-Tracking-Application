package org.example.personalfitnesstracker.Controllers;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.personalfitnesstracker.Models.User;

/**
 * Base controller with small helper methods.
 */
public abstract class BaseController {

    protected User loggedUser;

    public BaseController(User logggedUser) {
        this.loggedUser = logggedUser;
    }

    public BaseController() {

    }

    /**
     *
     * @param value
     * @return
     */
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

    /**
     * Using regex to check if an email is valid
     *
     * @param email
     * @return
     */
    public static boolean validEmail(String email) {
        Pattern regex = Pattern.compile("/[A-Za-z0-9]+@[a-z]+.[a-z]+"); //regular expression for emails
        Matcher match = regex.matcher(email);
        return match.find();
    }

    /**
     * Using regex to check if a password is valid
     *
     * @param password
     * @return
     */
    public static boolean validPassword(byte[] password) {
        String pw = new String(password, StandardCharsets.UTF_8);
        Pattern regex = Pattern.compile("/^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$");
        Matcher match = regex.matcher(pw);
        return match.find();
    }

    /**
     * Generic pop window of any kind that is chosen by the programmer
     *
     * @param title
     * @param msg
     * @param alert
     * @param button
     */
    protected void showMessageWindow(String title, String msg, Alert.AlertType alertType, ButtonType button) {
        Alert a = new Alert(alertType.getDeclaringClass().cast(alertType));
        a.setTitle(title);
        a.setHeaderText(msg);
        a.getButtonTypes().add(button);
        a.showAndWait();
    }
}
