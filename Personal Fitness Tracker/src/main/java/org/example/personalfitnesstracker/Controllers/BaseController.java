package org.example.personalfitnesstracker.Controllers;

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

    protected void log(String message) {
        System.out.println("[Controller] " + message);
    }
}
