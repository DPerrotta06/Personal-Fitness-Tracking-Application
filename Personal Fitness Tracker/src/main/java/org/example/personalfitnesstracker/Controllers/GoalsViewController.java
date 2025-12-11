package org.example.personalfitnesstracker.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.BulkingGoal;
import org.example.personalfitnesstracker.Models.CardioGoal;
import org.example.personalfitnesstracker.Models.CuttingGoal;
import org.example.personalfitnesstracker.Models.Goal;
import org.example.personalfitnesstracker.Models.MuscularGoal;
import org.example.personalfitnesstracker.Models.User;

import static javafx.scene.control.Alert.AlertType;
import static javafx.scene.control.ButtonType.OK;

public class GoalsViewController extends BaseController {

    // table
    @FXML private TableView<Goal> goalsTable;
    @FXML private TableColumn<Goal, String> nameColumn;
    @FXML private TableColumn<Goal, String> descriptionColumn;
    @FXML private TableColumn<Goal, String> typeColumn;
    @FXML private TableColumn<Goal, Boolean> completedColumn;

    // form
    @FXML private ComboBox<String> goalTypeCombo;
    @FXML private TextField goalNameField;
    @FXML private TextArea goalDescriptionArea;

    @FXML private Label extraLabel1;
    @FXML private Label extraLabel2;
    @FXML private Label extraLabel3;
    @FXML private TextField extraField1;
    @FXML private TextField extraField2;
    @FXML private TextField extraField3;

    @FXML private Button addGoalButton;
    @FXML private Button markCompletedButton;

    private final ObservableList<Goal> goals = FXCollections.observableArrayList();

    private User currentUser;

    /** Called from MainPageController after loading the FXML. */
    public void setUser(User user) {
        this.currentUser = user;
        refreshGoals();
    }

    @FXML
    private void initialize() {

        // ---- combo values ----
        goalTypeCombo.getItems().setAll("Cardio", "Muscular", "Bulking", "Cutting");
        goalTypeCombo.getSelectionModel().selectFirst();
        updateExtraFieldLabels(goalTypeCombo.getValue());

        goalTypeCombo.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldType, newType) -> updateExtraFieldLabels(newType)
        );

        // ---- table columns ----
        nameColumn.setCellValueFactory(cd -> cd.getValue().goalNameProperty());
        descriptionColumn.setCellValueFactory(cd -> cd.getValue().goalDescriptionProperty());

        typeColumn.setCellValueFactory(cd ->
                new SimpleStringProperty(getGoalTypeString(cd.getValue()))
        );

        completedColumn.setCellValueFactory(cd -> cd.getValue().isCompletedProperty());
        completedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(completedColumn));

        goalsTable.setItems(goals);

        // ---- buttons ----
        addGoalButton.setOnAction(e -> handleAddGoal());
        markCompletedButton.setOnAction(e -> handleMarkCompleted());
    }

    private void updateExtraFieldLabels(String goalType) {
        if (goalType == null) return;

        // Reset fields each time
        extraField1.clear();
        extraField2.clear();
        extraField3.clear();
        extraField3.setDisable(false);
        extraLabel3.setDisable(false);

        switch (goalType) {
            case "Cardio" -> {
                extraLabel1.setText("Target Resting HR (bpm)");
                extraLabel2.setText("Max Distance (km)");
                extraLabel3.setText("Unused");
                extraField3.setDisable(true);
                extraLabel3.setDisable(true);
                extraField1.setPromptText("e.g. 60");
                extraField2.setPromptText("e.g. 5.0");
            }
            case "Muscular" -> {
                extraLabel1.setText("Heaviest Lift (kg)");
                extraLabel2.setText("Max Reps");
                extraLabel3.setText("Max Sets");
                extraField1.setPromptText("e.g. 100");
                extraField2.setPromptText("e.g. 8");
                extraField3.setPromptText("e.g. 4");
            }
            case "Bulking" -> {
                extraLabel1.setText("Target Weight Gain (kg)");
                extraLabel2.setText("Target Daily Calories");
                extraLabel3.setText("Unused");
                extraField3.setDisable(true);
                extraLabel3.setDisable(true);
                extraField1.setPromptText("e.g. 5");
                extraField2.setPromptText("e.g. 2800");
            }
            case "Cutting" -> {
                extraLabel1.setText("Target Weight Loss (kg)");
                extraLabel2.setText("Daily Calorie Deficit");
                extraLabel3.setText("Unused");
                extraField3.setDisable(true);
                extraLabel3.setDisable(true);
                extraField1.setPromptText("e.g. 5");
                extraField2.setPromptText("e.g. 500");
            }
        }
    }

    private String getGoalTypeString(Goal g) {
        if (g instanceof CardioGoal)   return "Cardio";
        if (g instanceof MuscularGoal) return "Muscular";
        if (g instanceof BulkingGoal)  return "Bulking";
        if (g instanceof CuttingGoal)  return "Cutting";
        return "Goal";
    }

    private void refreshGoals() {
        if (currentUser == null) return;
        goals.setAll(DatabaseManager.displayGoals(currentUser.userIdProperty().get()));
    }

    private void handleAddGoal() {

        if (currentUser == null) {
            showMessageWindow("Error", "No user loaded.", AlertType.ERROR, OK);
            return;
        }

        String type = goalTypeCombo.getValue();
        String name = goalNameField.getText().trim();
        String description = goalDescriptionArea.getText().trim();

        if (type == null || name.isEmpty()) {
            showMessageWindow("Missing data",
                    "Please pick a type and enter at least a name.",
                    AlertType.ERROR, OK);
            return;
        }

        Goal newGoal = null;

        try {
            int userId = currentUser.userIdProperty().get();

            switch (type) {
                case "Cardio" -> {
                    int targetHR = Integer.parseInt(extraField1.getText().trim());
                    double maxDistance = Double.parseDouble(extraField2.getText().trim());
                    newGoal = new CardioGoal(
                            0, name, description, false, userId,
                            targetHR, maxDistance
                    );
                }
                case "Muscular" -> {
                    int heaviestLift = Integer.parseInt(extraField1.getText().trim());
                    int maxReps = Integer.parseInt(extraField2.getText().trim());
                    int maxSets = Integer.parseInt(extraField3.getText().trim());
                    newGoal = new MuscularGoal(
                            0, name, description, false, userId,
                            heaviestLift, maxReps, maxSets
                    );
                }
                case "Bulking" -> {
                    double targetGain = Double.parseDouble(extraField1.getText().trim());
                    int targetCalories = Integer.parseInt(extraField2.getText().trim());
                    newGoal = new BulkingGoal(
                            0, name, description, false, userId,
                            targetGain, targetCalories
                    );
                }
                case "Cutting" -> {
                    double targetLoss = Double.parseDouble(extraField1.getText().trim());
                    int calorieDeficit = Integer.parseInt(extraField2.getText().trim());
                    newGoal = new CuttingGoal(
                            0, name, description, false, userId,
                            targetLoss, calorieDeficit
                    );
                }
                default -> {
                    showMessageWindow("Error", "Unknown goal type.", AlertType.ERROR, OK);
                    return;
                }
            }

        } catch (NumberFormatException ex) {
            showMessageWindow("Invalid number",
                    "One of the numeric fields is invalid. Please fix it and try again.",
                    AlertType.ERROR, OK);
            return;
        }

        // If for some reason newGoal is still null (shouldn’t happen)
        if (newGoal == null) {
            showMessageWindow("Error", "Failed to create goal.", AlertType.ERROR, OK);
            return;
        }

        // save to DB
        DatabaseManager.addNewGoalToDb(newGoal);

        // reload table
        refreshGoals();

        // clear inputs
        goalNameField.clear();
        goalDescriptionArea.clear();
        extraField1.clear();
        extraField2.clear();
        extraField3.clear();

        showMessageWindow("Success", "Goal added.", AlertType.INFORMATION, OK);
    }


    private void handleMarkCompleted() {

        Goal selected = goalsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessageWindow("No selection",
                    "Select a goal in the table first.",
                    AlertType.ERROR, OK);
            return;
        }

        selected.isCompletedProperty().set(true);
        DatabaseManager.updateGoal(selected);

        goalsTable.refresh();
    }
    @FXML
    private void onCloseClicked() {
        // just close the window
        goalsTable.getScene().getWindow().hide();
    }

}
