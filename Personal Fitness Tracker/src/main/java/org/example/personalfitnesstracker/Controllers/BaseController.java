package org.example.personalfitnesstracker.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Base controller with helper methods.
 */
public abstract class BaseController {

    private static final Logger logger = Logger.getLogger(BaseController.class.getName());
    private static FileHandler fileHandler;

    protected boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    protected boolean isPositive(double value) {
        return value > 0;
    }

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
     * Error pop up window
     */
    protected void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(msg);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }
}
