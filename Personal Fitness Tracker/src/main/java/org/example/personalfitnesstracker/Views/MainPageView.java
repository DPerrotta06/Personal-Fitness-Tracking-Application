package org.example.personalfitnesstracker.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainPageView extends Stage {

    private Button nutritionButton, sleepButton, exerciseButton, goalsButton, inboxButton, settingsButton, logoutButton;
    private Label dateLabel;
    // can properly make this a functionality later
    // private ImageView profileImageView;

    public MainPageView() {
        setTitle("Fitness Tracker - Main Page");

        // --- Main layout ---
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #000000;");

        // --- Date and profile at the top ---
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10, 20, 10, 20));

        dateLabel = new Label("Saturday November 22 2025 08:44 PM");
        dateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        topBar.getChildren().addAll(dateLabel);
        root.setTop(topBar);

        // --- Center Grid layout for the main content ---
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));

        nutritionButton = createSectionButton("Daily Calories", "0 cal");
        sleepButton = createSectionButton("Daily Water Consumption", "0 L");
        exerciseButton = createSectionButton("Daily Sleep Amount", "0 h min");
        goalsButton = createSectionButton("Goals", "Customize");

        inboxButton = new Button("Check Inbox");
        inboxButton.setStyle("""
                -fx-background-color: #1435d1;
                -fx-text-fill: white;
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-padding: 10 20 10 20;
        """);
        inboxButton.setMaxWidth(150);

        settingsButton = new Button("Customize");
        settingsButton.setStyle("""
                -fx-background-color: #1435d1;
                -fx-text-fill: white;
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-padding: 10 20 10 20;
        """);
        settingsButton.setMaxWidth(150);

        logoutButton = new Button("Logout");
        logoutButton.setStyle("""
                -fx-background-color: #1435d1;
                -fx-text-fill: white;
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-padding: 10 20 10 20;
        """);
        logoutButton.setMaxWidth(150);

        grid.add(nutritionButton, 0, 0);
        grid.add(sleepButton, 1, 0);
        grid.add(exerciseButton, 0, 1);
        grid.add(goalsButton, 1, 1);
        grid.add(inboxButton, 0, 2);
        grid.add(settingsButton, 1, 2);
        grid.add(logoutButton, 0, 3, 2, 1);

        root.setCenter(grid);

        // --- Scene ---
        Scene scene = new Scene(root, 700, 600);
        setScene(scene);
    }

    // --- Button maker to follow our format
    private Button createSectionButton(String title, String subtitle) {
        Button button = new Button(title + "\n" + subtitle);
        button.setStyle("""
                -fx-background-color: #1435d1;
                -fx-text-fill: white;
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-padding: 10 20 10 20;
        """);
        return button;
    }

    // Getters to link with the controller
    public Button getNutritionButton() {
        return nutritionButton;
    }

    public Button getSleepButton() {
        return sleepButton;
    }

    public Button getExerciseButton() {
        return exerciseButton;
    }

    public Button getGoalsButton() {
        return goalsButton;
    }

    public Button getInboxButton() {
        return inboxButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}
