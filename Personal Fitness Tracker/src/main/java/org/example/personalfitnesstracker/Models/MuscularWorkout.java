package org.example.personalfitnesstracker.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDateTime;

public class MuscularWorkout extends Workout{

    private final IntegerProperty totalSets;
    private final IntegerProperty totalReps;
    private final DoubleProperty totalWeight;

    public MuscularWorkout(int workoutId, String workoutName, String workoutDescription, double workoutDuration, int caloriesBurned, int userId, LocalDateTime dateStamp, int totalSets, int totalReps, double totalWeight) {
        super(workoutId, workoutName, workoutDescription, workoutDuration, caloriesBurned, dateStamp, userId);
        this.totalSets = new SimpleIntegerProperty(totalSets);
        this.totalReps = new SimpleIntegerProperty(totalReps);
        this.totalWeight = new SimpleDoubleProperty(totalWeight);
    }

    public IntegerProperty totalSetsProperty() {
        return totalSets;
    }

    public IntegerProperty totalRepsProperty() {
        return totalReps;
    }

    public DoubleProperty totalWeightProperty() {
        return totalWeight;
    }
}
