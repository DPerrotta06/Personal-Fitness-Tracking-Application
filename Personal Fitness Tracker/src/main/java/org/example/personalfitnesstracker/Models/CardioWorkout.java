package org.example.personalfitnesstracker.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class CardioWorkout extends Workout{

    private final DoubleProperty totalDistance;
    private final StringProperty heartRateZone;

    public CardioWorkout(int workoutId, String workoutName, String workoutDescription, double workoutDuration, int caloriesBurned, LocalDateTime dateStamp, int userId, double totalDistance, String heartRateZone) {
        super(workoutId, workoutName, workoutDescription, workoutDuration, caloriesBurned, dateStamp, userId);
        this.totalDistance = new SimpleDoubleProperty(totalDistance);
        this.heartRateZone = new SimpleStringProperty(heartRateZone);
    }

    public DoubleProperty totalDistanceProperty() {
        return totalDistance;
    }

    public StringProperty heartRateZoneProperty() {
        return heartRateZone;
    }
}
