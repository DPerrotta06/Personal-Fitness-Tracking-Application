package org.example.personalfitnesstracker.Controllers;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Views.MainPageView;

import org.example.personalfitnesstracker.Models.User;

public class MainPageController extends BaseController {

    private final MainPageView mainPageView; //loads up the main dashborad page

    /**
     * Constructor
     *
     * @param mainPageView
     * @param loggedUser
     */
    public MainPageController(MainPageView mainPageView, User loggedUser) {
        super(loggedUser);
        this.mainPageView = new MainPageView();
        setupHandlers();
    }

    /**
     * Displays the main page GUI
     */
    public void show() {
        mainPageView.show();
        DatabaseManager.loadUserDataInParallel(loggedUser.userIdProperty().get());
    }

    /**
     *
     */
    private void setupHandlers() {
        mainPageView.getExerciseButton().setOnAction(event -> {
            System.out.println("Navigating to Exercise section...");
            // TODO: Handle the logout and return to the Login screen
        });

        mainPageView.getGoalsButton().setOnAction(event -> {
            System.out.println("Navigating to Goals section...");
            // TODO: Handle the logout and return to the Login screen
        });

        mainPageView.getInboxButton().setOnAction(event -> {
            System.out.println("Navigating to Inbox section...");
            // TODO: Handle the logout and return to the Login screen
        });

        mainPageView.getNutritionButton().setOnAction(event -> {
            System.out.println("Navigating to Nutrition section...");
            // TODO: Implement the actual functionality here
        });

        mainPageView.getSleepButton().setOnAction(event -> {
            System.out.println("Navigating to Sleep section...");
            // TODO: Implement the actual functionality here
        });

        mainPageView.getLogoutButton().setOnAction(event -> {
            System.out.println("Logging out...");
            // TODO: Handle the logout and return to the Login screen
        });
    }

    /**
     * Calculates the user's BMI based on their height and weight
     *
     * @param user
     * @return
     */
    public double calculateBmiNumeric(User user) {
        return user.weightProperty().get() / (Math.pow((user.heightProperty().get() / 1000), 2));
    }

    /**
     * Gets the numeric value of the User's BMI and assigns a verbal value
     *
     * @param user
     * @return
     */
    public String calculateBmiVerbal(User user) {
        double bmiNumber = calculateBmiNumeric(user);
        if (bmiNumber < 18.0) {
            return "Underweight";
        } else if (bmiNumber >= 18.0 && bmiNumber <= 24.9) {
            return "Normal";
        } else if (bmiNumber >= 25 && bmiNumber <= 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

}
