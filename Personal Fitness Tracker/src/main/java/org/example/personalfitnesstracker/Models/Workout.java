package org.example.personalfitnesstracker.Models;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public abstract class Workout {

    protected final IntegerProperty workoutId;
    protected final StringProperty workoutName;
    protected final StringProperty workoutDescription;
    protected final DoubleProperty workoutDuration; //in minutes
    protected final IntegerProperty caloriesBurned;
    protected final LocalDateTime dateStamp;
    protected final IntegerProperty userId;

    public Workout(int workoutId, String workoutName, String workoutDescription, double workoutDuration, int caloriesBurned, LocalDateTime dateStamp, int userId) {
        this.workoutId = new SimpleIntegerProperty(workoutId);
        this.workoutName = new SimpleStringProperty(workoutName);
        this.workoutDescription = new SimpleStringProperty(workoutDescription);
        this.workoutDuration = new SimpleDoubleProperty(workoutDuration);
        this.caloriesBurned = new SimpleIntegerProperty(caloriesBurned);
        this.dateStamp = dateStamp;
        this.userId = new SimpleIntegerProperty(userId);
    }

    public IntegerProperty workoutIdProperty(){
        return workoutId;
    }

    public StringProperty workoutNameProperty() {
        return workoutName;
    }

    public StringProperty workoutDescriptionProperty() {
        return workoutDescription;
    }

    public DoubleProperty workoutDurationProperty() {
        return workoutDuration;
    }

    public IntegerProperty caloriesBurnedProperty() {
        return caloriesBurned;
    }

    public LocalDateTime dateStampProperty() {
        return dateStamp;
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }
}
