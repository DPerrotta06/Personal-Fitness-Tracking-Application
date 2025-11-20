package org.example.personalfitnesstracker.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BulkingGoal extends Goal{

    private final DoubleProperty targetWeightGain;
    private final IntegerProperty targetDailyCaloricIntake;

    public BulkingGoal(int goalId, String goalName, String goalDescription, boolean isCompleted, int userId, double targetWeightGain, int targetDailyCaloricIntake) {
        super(goalId, goalName, goalDescription, isCompleted, userId);
        this.targetWeightGain = new SimpleDoubleProperty(targetWeightGain);
        this.targetDailyCaloricIntake = new SimpleIntegerProperty(targetDailyCaloricIntake);
    }

    public DoubleProperty targetWeightGainProperty(){
        return targetWeightGain;
    }

    public IntegerProperty targetCaloricIntakeProperty(){
        return targetDailyCaloricIntake;
    }
}
