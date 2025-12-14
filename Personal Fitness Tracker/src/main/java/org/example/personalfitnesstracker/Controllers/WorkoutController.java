package org.example.personalfitnesstracker.Controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.*;
import org.example.personalfitnesstracker.Threads.AddWorkoutThread;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javafx.scene.control.Alert.AlertType;
import static javafx.scene.control.ButtonType.OK;

public class WorkoutController extends BaseController {

    // ---------------- CARDIO ----------------
    @FXML private Button cwBackButton, cwAddButton;
    @FXML private ComboBox<String> cwFilter;
    @FXML private TableView<CardioWorkout> cardioTable;

    @FXML private TableColumn<CardioWorkout, Integer> cwID, cwCalBurned;
    @FXML private TableColumn<CardioWorkout, String> cwName, cwDesc, cwHR, cwDate;
    @FXML private TableColumn<CardioWorkout, Double> cwDuration, cwDistance;

    @FXML private TextField cwNameField, cwDescField, cwDurationField,
            cwCaloriesField, cwDistanceField, cwHRField;

    // ---------------- MUSCULAR ----------------
    @FXML private Button mwBackButton, mwAddButton;
    @FXML private ComboBox<String> mwFilter;
    @FXML private TableView<MuscularWorkout> muscularTable;

    @FXML private TableColumn<MuscularWorkout, Integer> mwID, mwCalBurned, mwSets, mwReps;
    @FXML private TableColumn<MuscularWorkout, String> mwName, mwDesc, mwDate;
    @FXML private TableColumn<MuscularWorkout, Double> mwDuration, mwWeight;

    @FXML private TextField mwNameField, mwDescField, mwDurationField,
            mwCaloriesField, mwSetsField, mwRepsField, mwWeightField;

    private User currentUser;

    private final ObservableList<CardioWorkout> allCardio = FXCollections.observableArrayList();
    private final ObservableList<MuscularWorkout> allMuscular = FXCollections.observableArrayList();

    private final DateTimeFormatter dateFmt =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    private void initialize() {
        setupTables();
        setupFilters();
        setupButtons();
        setupContextMenus();
    }

    public void setUser(User user) {
        this.currentUser = user;
        refreshAll();
    }

    private void setupTables() {

        cwID.setCellValueFactory(d -> d.getValue().workoutIdProperty().asObject());
        cwName.setCellValueFactory(d -> d.getValue().workoutNameProperty());
        cwDesc.setCellValueFactory(d -> d.getValue().workoutDescriptionProperty());
        cwDuration.setCellValueFactory(d -> d.getValue().workoutDurationProperty().asObject());
        cwCalBurned.setCellValueFactory(d -> d.getValue().caloriesBurnedProperty().asObject());
        cwDistance.setCellValueFactory(d -> d.getValue().totalDistanceProperty().asObject());
        cwHR.setCellValueFactory(d -> d.getValue().heartRateZoneProperty());
        cwDate.setCellValueFactory(d ->
                Bindings.createStringBinding(() ->
                        d.getValue().dateStampProperty().format(dateFmt)));

        cardioTable.setItems(allCardio);

        mwID.setCellValueFactory(d -> d.getValue().workoutIdProperty().asObject());
        mwName.setCellValueFactory(d -> d.getValue().workoutNameProperty());
        mwDesc.setCellValueFactory(d -> d.getValue().workoutDescriptionProperty());
        mwDuration.setCellValueFactory(d -> d.getValue().workoutDurationProperty().asObject());
        mwCalBurned.setCellValueFactory(d -> d.getValue().caloriesBurnedProperty().asObject());
        mwSets.setCellValueFactory(d -> d.getValue().totalSetsProperty().asObject());
        mwReps.setCellValueFactory(d -> d.getValue().totalRepsProperty().asObject());
        mwWeight.setCellValueFactory(d -> d.getValue().totalWeightProperty().asObject());
        mwDate.setCellValueFactory(d ->
                Bindings.createStringBinding(() ->
                        d.getValue().dateStampProperty().format(dateFmt)));

        muscularTable.setItems(allMuscular);
    }

    private void setupFilters() {
        cwFilter.getItems().setAll("All", "Today", "Last 7 days");
        mwFilter.getItems().setAll("All", "Today", "Last 7 days");
        cwFilter.setValue("All");
        mwFilter.setValue("All");

        cwFilter.setOnAction(e -> applyFilter(cwFilter, allCardio, cardioTable));
        mwFilter.setOnAction(e -> applyFilter(mwFilter, allMuscular, muscularTable));
    }

    private void setupButtons() {
        cwBackButton.setOnAction(e -> close());
        mwBackButton.setOnAction(e -> close());
        cwAddButton.setOnAction(e -> addCardio());
        mwAddButton.setOnAction(e -> addMuscular());
    }

    private void close() {
        ((Stage) cwBackButton.getScene().getWindow()).close();
    }

    private void refreshAll() {
        if (currentUser == null) return;

        allCardio.clear();
        allMuscular.clear();

        for (Workout w : DatabaseManager.displayWorkoutLogs(currentUser.userIdProperty().get())) {
            if (w instanceof CardioWorkout cw) allCardio.add(cw);
            if (w instanceof MuscularWorkout mw) allMuscular.add(mw);
        }

        applyFilter(cwFilter, allCardio, cardioTable);
        applyFilter(mwFilter, allMuscular, muscularTable);
    }

    private <T extends Workout> void applyFilter(
            ComboBox<String> filter,
            ObservableList<T> source,
            TableView<T> table) {

        FilteredList<T> filtered = new FilteredList<>(source, w -> true);
        LocalDate today = LocalDate.now();

        if ("Today".equals(filter.getValue())) {
            filtered.setPredicate(w -> w.dateStampProperty().toLocalDate().isEqual(today));
        } else if ("Last 7 days".equals(filter.getValue())) {
            LocalDate weekAgo = today.minusDays(7);
            filtered.setPredicate(w -> !w.dateStampProperty().toLocalDate().isBefore(weekAgo));
        }

        table.setItems(filtered);
    }

    private void addCardio() {
        try {
            CardioWorkout cw = new CardioWorkout(
                    0,
                    cwNameField.getText().trim(),
                    cwDescField.getText().trim(),
                    Double.parseDouble(cwDurationField.getText().trim()),
                    Integer.parseInt(cwCaloriesField.getText().trim()),
                    currentUser.userIdProperty().get(),
                    LocalDateTime.now(),
                    Double.parseDouble(cwDistanceField.getText().trim()),
                    cwHRField.getText().trim()
            );

            AddWorkoutThread addWorkoutThread =
                    new AddWorkoutThread("Adding cardio workout...", cw);

            addWorkoutThread.thread.start();

            try {
                addWorkoutThread.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            refreshAll();
            clearCardioForm();

        } catch (Exception e) {
            showMessageWindow("Invalid input", "Check cardio workout fields.", AlertType.ERROR, OK);
        }
    }


    private void addMuscular() {
        try {
            MuscularWorkout mw = new MuscularWorkout(
                    0,
                    mwNameField.getText().trim(),
                    mwDescField.getText().trim(),
                    Double.parseDouble(mwDurationField.getText().trim()),
                    Integer.parseInt(mwCaloriesField.getText().trim()),
                    currentUser.userIdProperty().get(),
                    LocalDateTime.now(),
                    Integer.parseInt(mwSetsField.getText().trim()),
                    Integer.parseInt(mwRepsField.getText().trim()),
                    Double.parseDouble(mwWeightField.getText().trim())
            );

            AddWorkoutThread addWorkoutThread =
                    new AddWorkoutThread("Adding muscular workout...", mw);

            addWorkoutThread.thread.start();

            try {
                addWorkoutThread.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            refreshAll();
            clearMuscularForm();

        } catch (Exception e) {
            showMessageWindow("Invalid input", "Check muscular workout fields.", AlertType.ERROR, OK);
        }
    }


    private void clearCardioForm() {
        cwNameField.clear();
        cwDescField.clear();
        cwDurationField.clear();
        cwCaloriesField.clear();
        cwDistanceField.clear();
        cwHRField.clear();
    }

    private void clearMuscularForm() {
        mwNameField.clear();
        mwDescField.clear();
        mwDurationField.clear();
        mwCaloriesField.clear();
        mwSetsField.clear();
        mwRepsField.clear();
        mwWeightField.clear();
    }

    private void setupContextMenus() {
        MenuItem delC = new MenuItem("Delete");
        delC.setOnAction(e -> delete(cardioTable.getSelectionModel().getSelectedItem()));
        cardioTable.setContextMenu(new ContextMenu(delC));

        MenuItem delM = new MenuItem("Delete");
        delM.setOnAction(e -> delete(muscularTable.getSelectionModel().getSelectedItem()));
        muscularTable.setContextMenu(new ContextMenu(delM));
    }

    private void delete(Workout w) {
        if (w == null) return;

        Alert a = new Alert(AlertType.CONFIRMATION,
                "Delete this workout?", OK, ButtonType.CANCEL);

        if (a.showAndWait().filter(b -> b == OK).isEmpty()) return;

        DatabaseManager.deleteWorkout(
                w.workoutIdProperty().get(),
                currentUser.userIdProperty().get());

        refreshAll();
    }
}
