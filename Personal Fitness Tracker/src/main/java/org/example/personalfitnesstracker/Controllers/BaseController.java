package org.example.personalfitnesstracker.Controllers;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.personalfitnesstracker.Models.User;

/**
 * Base controller with helper methods.
 */
public abstract class BaseController {

    private static final Logger logger = Logger.getLogger(BaseController.class.getName());
    private static FileHandler fileHandler;

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

    static {
        try {
            fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);;

        } catch (IOException e) {
            System.err.println("Failed to initialize log file: " + e.getMessage());
        }
    }

    protected void log(String message) {
        logger.info(message);
    }

    /**
     * Info pop up window
     */
    protected void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        DialogPane pane = alert.getDialogPane();
        pane.setStyle("-fx-background-color: #47d647; -fx-font-size: 14px;");

        alert.showAndWait();
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
