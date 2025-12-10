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
    private Button homeButton;
    @FXML
    private Button addEntryButton;
    @FXML
    private Button goalsButton;
    @FXML
    private Button logoutButton;

    //private User currentUser; //We can just use the loggedUser provided by the base controller
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
        if (loggedUser == null) {
            return;
        }
        bmiStatusLabel.setText(String.format("BMI: %.1f (%s)", calculateBmiNumeric(loggedUser), calculateBmiVerbal(loggedUser)));
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
            showMessageWindow("Logout", "You have been logged out.", AlertType.INFORMATION, ButtonType.OK);
            logoutButton.getScene().getWindow().hide();
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
                controller.setOnEntrySaved(() -> {
                    loadAllSections();   // Reload calories, water, sleep, BMI, chart
                });

                Stage stage = new Stage();
                stage.setTitle("Add Entry");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                
            }
        });
        goalsButton.setOnAction(e -> showMessageWindow("Goals", "Goals page not implemented yet.", AlertType.INFORMATION, ButtonType.OK));
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
