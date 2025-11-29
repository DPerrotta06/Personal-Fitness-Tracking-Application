package org.example.personalfitnesstracker.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//---------------------------PLACE HOLDER FOR NOW NEED TO MAKE THE REST OF THE WINDOWS USING SCENE BUILDER AND FXML---------------------------
public class MainPageView extends Stage {

    private Button nutritionButton, sleepButton, exerciseButton, goalsButton, inboxButton, logoutButton;
    private Label dateLabel;

    public MainPageView() {
        setTitle("Fitness Tracker - Main Page");

        // --- Main layout ---
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #000000;");

        // --- Date and profile at the top ---
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10, 20, 10, 20));

        // Getting current date in a specific format
        LocalDateTime currentTime = LocalDateTime.now();
        String pattern = "EEEE MMMM dd yyyy hh:mm a";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedDate = currentTime.format(formatter);
        dateLabel = new Label(formattedDate);
        dateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        topBar.getChildren().addAll(dateLabel);
        root.setTop(topBar);

        // --- Center Grid layout for the main content (empty for now) ---
        Region centerRegion = new Region();
        root.setCenter(centerRegion);  // Empty space for charts/graphs

        // --- Bottom layout for buttons ---
        HBox bottomBar = new HBox(15);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(20));

        nutritionButton = createSectionButton("Daily Calories", "0 cal");
        sleepButton = createSectionButton("Daily Water Consumption", "0 L");
        exerciseButton = createSectionButton("Daily Sleep Amount", "0 h min");
        goalsButton = createSectionButton("Goals", "Customize");

        inboxButton = createSmallButton("Check Inbox");
        logoutButton = createSmallButton("Logout");

        bottomBar.getChildren().addAll(
                nutritionButton, sleepButton, exerciseButton, goalsButton,
                inboxButton, logoutButton
        );

        root.setBottom(bottomBar);

        // --- Scene ---
        Scene scene = new Scene(root, 1000, 650);
        setScene(scene);
    }

    // --- Button maker for larger section buttons
    private Button createSectionButton(String title, String subtitle) {
        Button button = new Button(title + "\n" + subtitle);
        button.setStyle("""
                -fx-background-color: #1435d1;
                -fx-text-fill: white;
                -fx-font-size: 12px;  // Smaller font size
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-padding: 8 16 8 16;
        """);
        button.setMaxWidth(150);
        return button;
    }

    // --- Button maker for smaller buttons
    private Button createSmallButton(String text) {
        Button button = new Button(text);
        button.setStyle("""
                -fx-background-color: #1435d1;
                -fx-text-fill: white;
                -fx-font-size: 12px;  // Smaller font size
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-padding: 8 16 8 16;
        """);
        button.setMaxWidth(120);  // Smaller width
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

    public Button getLogoutButton() {
        return logoutButton;
    }
}
