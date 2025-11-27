package org.example.personalfitnesstracker.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Base controller with small helper methods.
 */
public abstract class BaseController {

    protected boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    protected boolean isPositive(double value) {
        return value > 0;
    }

    protected boolean isPositive(int value) {
        return value > 0;
    }

    protected void log(String message) { //is this suppose to be a logger?
        System.out.println("[Controller] " + message);
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
