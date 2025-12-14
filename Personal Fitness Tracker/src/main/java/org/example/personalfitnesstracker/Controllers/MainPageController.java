package org.example.personalfitnesstracker.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;

import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Level;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.example.personalfitnesstracker.Views.LoginView;

import static javafx.scene.control.ButtonType.OK;

public class MainPageController extends BaseController {

    @FXML
    private Label dateLabel;
    @FXML
    private Label welcomeLabel;

    @FXML
    private Label dailyCaloriesLabel;
    @FXML
    private Label dailyWaterLabel;
    @FXML
    private Label dailySleepLabel;

    @FXML
    private Label bmiStatusLabel;

    @FXML
    private VBox weeklyChartContainer;
    @FXML
    private Button addEntryButton;
    @FXML
    private Button goalsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button nutritionButton;
    @FXML
    private Button addWorkoutButton;

    // -------------------------------------------------------------------------
    // INITIALIZE
    // -------------------------------------------------------------------------
    @FXML
    public void initialize() {

        // Buttons must be linked immediately
        setupHandlers();

    }

    // -------------------------------------------------------------------------
    // SET USER (called by LoginController)
    // -------------------------------------------------------------------------
    public void setUser(User user) {
        this.loggedUser = user;

        welcomeLabel.setText("Welcome, " + user.usernameProperty().get());
        dateLabel.setText(LocalDate.now().toString());

        loadAllSections();
    }

    // -------------------------------------------------------------------------
    // LOAD EVERYTHING
    // -------------------------------------------------------------------------
    private void loadAllSections() {
        loadDailySummary();
        loadBMI();
        loadWeeklyChart();
    }

    // -------------------------------------------------------------------------
    // DAILY SUMMARY (calories, water, sleep)
    // -------------------------------------------------------------------------
    private void loadDailySummary() {
        if (loggedUser == null) {
            return;
        }

        //int userId = currentUser.userIdProperty().get();
        int userId = loggedUser.userIdProperty().get();

        double calories = DatabaseManager.getDailyCalories(userId);
        dailyCaloriesLabel.setText(String.format("%.0f kcal", calories));

        double water = DatabaseManager.getDailyWater(userId);
        dailyWaterLabel.setText(String.format("%.2f L", water));

        double[] sleep = DatabaseManager.getDailySleep(userId);
        dailySleepLabel.setText(String.format("%.0f h %.0f min", sleep[0], sleep[1]));
    }

    // -------------------------------------------------------------------------
    // BMI STATUS
    // -------------------------------------------------------------------------
    /**
     * Loading the user's BMI by using the bmi calculation helper methods
     */
    private void loadBMI() {
        if (loggedUser == null) return;

        // DB stores:
        // - Weight in POUNDS
        // - Height in CENTIMETERS
        double weightLbs = loggedUser.weightProperty().get();
        double heightCm  = loggedUser.heightProperty().get();

        // Convert to metric for BMI calculation
        double heightMeters = heightCm / 100.0;            // cm → m
        double weightKg     = weightLbs * 0.45359237;      // lb → kg

        double bmi = weightKg / (heightMeters * heightMeters);

        String category;
        if (bmi < 18.5)       category = "Underweight";
        else if (bmi < 25.0)  category = "Healthy";
        else if (bmi < 30.0)  category = "Overweight";
        else                  category = "Obese";

        bmiStatusLabel.setText(String.format("BMI: %.1f (%s)", bmi, category));
    }


    // -------------------------------------------------------------------------
    // WEEKLY CHART
    // -------------------------------------------------------------------------
    private void loadWeeklyChart() {
        if (loggedUser == null) {
            return;
        }

        //Map<LocalDate, Double> data = DatabaseManager.getWeeklyCalories(currentUser.userIdProperty().get());
        Map<LocalDate, Double> data = DatabaseManager.getWeeklyCalories(loggedUser.userIdProperty().get());

        weeklyChartContainer.getChildren().clear();

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Day");
        yAxis.setLabel("Calories");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setPrefHeight(180);
        chart.setPrefWidth(320);
        chart.setStyle("-fx-background-color: transparent;");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        int day = 1;
        for (Map.Entry<LocalDate, Double> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(day, entry.getValue()));
            day++;
        }

        chart.getData().add(series);
        weeklyChartContainer.getChildren().add(chart);
    }

    // -------------------------------------------------------------------------
    // BUTTON HANDLERS
    // -------------------------------------------------------------------------
    private void setupHandlers() {

        logoutButton.setOnAction(e -> {
            try {
                // Close the current window
                Stage currentStage = (Stage) logoutButton.getScene().getWindow();
                showMessageWindow("Logout", "You have been logged out.", AlertType.INFORMATION, ButtonType.OK);
                currentStage.close();

                // Load Login View again
                LoginView loginView = new LoginView();
                LoginController loginController = new LoginController(loginView);
                loginController.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        addEntryButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/org/example/personalfitnesstracker/Views/AddEntry.fxml")
                );
                Parent root = loader.load();

                AddEntryController controller = loader.getController();
                controller.setUser(loggedUser);

                //Tell AddEntryController how to refresh the main page
                controller.setOnEntrySaved(this::loadAllSections);

                Stage stage = new Stage();
                stage.setTitle("Add Entry");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                
            }
        });
        goalsButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/org/example/personalfitnesstracker/Views/GoalsView.fxml")
                );
                Parent root = loader.load();

                GoalsViewController controller = loader.getController();
                controller.setUser(loggedUser);

                Stage stage = new Stage();
                stage.setTitle("Goals");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        nutritionButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/personalfitnesstracker/Views/Nutrition.fxml"));
                Parent root = loader.load();

                NutritionController controller = loader.getController();
                controller.setUser(loggedUser);

                Stage stage = new Stage();
                stage.setTitle("Nutrition Log");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        addWorkoutButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/personalfitnesstracker/Views/Workout.fxml"));
                Parent root = loader.load();

                WorkoutController controller = loader.getController();
                controller.setUser(loggedUser);

                Stage stage = new Stage();
                stage.setTitle("Workout Log");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
