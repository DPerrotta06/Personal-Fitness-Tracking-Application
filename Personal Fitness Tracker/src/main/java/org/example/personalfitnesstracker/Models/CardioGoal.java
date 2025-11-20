package org.example.personalfitnesstracker.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CardioGoal extends Goal{

    private final IntegerProperty targetRestingHeartRate;
    private final DoubleProperty maxDistance;

    public CardioGoal(int goalId, String goalName, String goalDescription, boolean isCompleted, int userId, int targetRestingHeartRate, double maxDistance) {
        super(goalId, goalName, goalDescription, isCompleted, userId);
        this.targetRestingHeartRate = new SimpleIntegerProperty(targetRestingHeartRate);
        this.maxDistance = new SimpleDoubleProperty(maxDistance);
    }

    public IntegerProperty targetRestingHeartRateProperty() {
        return targetRestingHeartRate;
    }

    public DoubleProperty maxDistanceProperty(){
        return maxDistance;
    }
}
