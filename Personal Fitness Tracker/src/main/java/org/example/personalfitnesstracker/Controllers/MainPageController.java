package org.example.personalfitnesstracker.Controllers;

import org.example.personalfitnesstracker.Views.MainPageView;

public class MainPageController extends BaseController{

    private final MainPageView mainPageView;

    public MainPageController(MainPageView mainPageView) {
        this.mainPageView = new MainPageView();
        setupHandlers();
    }

    public void show() {
        mainPageView.show();
    }

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
}
