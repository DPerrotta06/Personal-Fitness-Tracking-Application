package org.example.personalfitnesstracker.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.Factories.CardioFactory;
import org.example.personalfitnesstracker.Factories.IWorkoutFactory;
import org.example.personalfitnesstracker.Factories.MuscularFactory;
import org.example.personalfitnesstracker.Factories.WorkoutAttributeData;
import org.example.personalfitnesstracker.Models.Workout;

import java.time.LocalDateTime;

/**
 * Controller responsible for creating and managing Workout sessions
 * (CardioWorkout and MuscularWorkout).
 */
public class WorkoutController extends BaseController {

    private final IWorkoutFactory cardioFactory;
    private final IWorkoutFactory muscularFactory;

    private final ObservableList<Workout> workouts;

    public WorkoutController() {
        this.cardioFactory = new CardioFactory();
        this.muscularFactory = new MuscularFactory();
        this.workouts = FXCollections.observableArrayList();
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
            log("Cannot add cardio workout: duration must be positive.");
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
                null,           // totalSets
                null,           // totalReps
                null,           // totalWeight
                totalDistance,
                heartRateZone
        );

        Workout workout = cardioFactory.addNewWorkoutSession(attr);
        workouts.add(workout);
        log("Cardio workout added for user " + userId);
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
            log("Cannot add muscular workout: duration must be positive.");
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
                null,          // totalDistance
                null                      // heartRateZone
        );

        Workout workout = muscularFactory.addNewWorkoutSession(attr);
        workouts.add(workout);
        log("Muscular workout added for user " + userId);
        return workout;
    }
}
