package org.example.personalfitnesstracker.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.Factories.CardioFactory;
import org.example.personalfitnesstracker.Factories.IWorkoutFactory;
import org.example.personalfitnesstracker.Factories.MuscularFactory;
import org.example.personalfitnesstracker.Factories.WorkoutAttributeData;
import org.example.personalfitnesstracker.Models.Workout;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.CardioWorkout;
import org.example.personalfitnesstracker.Models.MuscularWorkout;
import org.example.personalfitnesstracker.Models.User;

/**
 * Controller responsible for creating and managing Workout sessions
 * (CardioWorkout and MuscularWorkout).
 */
public class WorkoutController extends BaseController {

    private final IWorkoutFactory cardioFactory;
    private final IWorkoutFactory muscularFactory;
    private final ArrayDeque<Workout> recentWorkouts = new ArrayDeque<>();
    private final ObservableList<Workout> workouts;
    private User loggedUser;

    public WorkoutController(User loggedUser) {
        this.cardioFactory = new CardioFactory();
        this.muscularFactory = new MuscularFactory();
        this.workouts = FXCollections.observableArrayList();
        this.loggedUser = loggedUser;
    }

    public ObservableList<Workout> getWorkouts() {
        return workouts;
    }

    // CARDIO WORKOUT
    public Workout addCardioWorkout(int workoutId,
            String workoutName,
            String workoutDescription,
            double duration,
            int caloriesBurned,
            LocalDateTime dateStamp,
            int userId,
            double totalDistance,
            String heartRateZone) {

        if (!isPositive(duration)) {
            return null;
        }

        WorkoutAttributeData attr = new WorkoutAttributeData(
                workoutId,
                workoutName,
                workoutDescription,
                duration,
                caloriesBurned,
                dateStamp,
                userId,
                null, // totalSets
                null, // totalReps
                null, // totalWeight
                totalDistance,
                heartRateZone
        );

        Workout workout = cardioFactory.addNewWorkoutSession(attr);
        workouts.add(workout);
        return workout;
    }

    // MUSCULAR WORKOUT
    public Workout addMuscularWorkout(int workoutId,
            String workoutName,
            String workoutDescription,
            double duration,
            int caloriesBurned,
            LocalDateTime dateStamp,
            int userId,
            int totalSets,
            int totalReps,
            double totalWeight) {

        if (!isPositive(duration)) {
            return null;
        }

        WorkoutAttributeData attr = new WorkoutAttributeData(
                workoutId,
                workoutName,
                workoutDescription,
                duration,
                caloriesBurned,
                dateStamp,
                userId,
                totalSets,
                totalReps,
                totalWeight,
                null, // totalDistance
                null // heartRateZone
        );

        Workout workout = muscularFactory.addNewWorkoutSession(attr);
        workouts.add(workout);
        return workout;
    }

    /**
     * Using an arraydequeue to get the recent workouts done by the user to act
     * as a sort of history
     *
     * @return
     */
    public ObservableList<Workout> getRecentWorkouts() {
        return FXCollections.observableArrayList(recentWorkouts);
    }

    //ADD A WAY TO FILTER WORKOUTS BY TYPE, INTENSITY AND ESLAPSED TIME PREFERABLY STREAMS
    /**
     * Filtering data using a parallel stream by the workout type. Types can be
     * either Cardio or Muscular. Can get the type from a combo box.
     *
     * @param type
     * @return
     */
    public ObservableList<Workout> filterByType(String type) {
        ObservableList<Workout> workouts = DatabaseManager.displayWorkoutLogs(loggedUser.userIdProperty().get());
        List<Workout> filtered = workouts.parallelStream().filter(workout -> {
            if (type.equalsIgnoreCase("Muscular")) {
                return workout instanceof MuscularWorkout;
            } else if (type.equalsIgnoreCase("Cardio")) {
                return workout instanceof CardioWorkout;
            } else {
                showMessageWindow("Type unknown", "Please choose a valid workout type provided by the dropdown list to filter by.", AlertType.WARNING, ButtonType.OK);
                return false;
            }
        }).collect(Collectors.toList());
        return FXCollections.observableArrayList(filtered);
    }

    /**
     * Filtering data using a parallel stream by how intense a workout was.
     * Types can range from Light -> Medium -> Intense.
     *
     * @param intensityRating
     * @return
     */
    public ObservableList<Workout> filterByIntensity(String intensityRating) {
        ObservableList<Workout> workouts = DatabaseManager.displayWorkoutLogs(loggedUser.userIdProperty().get());
        List<Workout> filtered = workouts.parallelStream().filter(workout -> {
            String intensity = getIntensityRange(workout);
            return intensity.equalsIgnoreCase(intensityRating);
        }).collect(Collectors.toList());
        return FXCollections.observableArrayList(filtered);
    }

    /**
     * Helper function to get the verbal intensity rating based on already
     * existing data
     *
     * @param workout
     * @return
     */
    private String getIntensityRange(Workout workout) {
        if (workout instanceof MuscularWorkout mw) {
            double volume = mw.totalWeightProperty().get() * mw.totalRepsProperty().get() * mw.totalSetsProperty().get();
            if (volume <= 1000) {
                return "Light";
            } else if (volume > 1000 && volume <= 3000) {
                return "Medium";
            } else {
                return "Intense";
            }
        } else if (workout instanceof CardioWorkout cw) {
            String hr = cw.heartRateZoneProperty().get();
            if (hr.equalsIgnoreCase("Healthy")) {
                return "Light";
            } else if (hr.equalsIgnoreCase("Fitness") || hr.equalsIgnoreCase("Aerobic")) {
                return "Medium";
            } else {
                return "Intense";
            }
        }
        return null;
    }

    public ObservableList<Workout> filterByElapsedTime(double minutes) {
        
    }

}
