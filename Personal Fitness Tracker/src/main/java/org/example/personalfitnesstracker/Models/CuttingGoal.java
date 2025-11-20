package org.example.personalfitnesstracker.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CuttingGoal extends Goal{

    private final DoubleProperty targetWeightLoss;
    private final IntegerProperty targetCaloricDeficit;

    public CuttingGoal(int goalId, String goalName, String goalDescription, boolean isCompleted, int userId, double targetWeightLoss, int targetCaloricDeficit) {
        super(goalId, goalName, goalDescription, isCompleted, userId);
        this.targetWeightLoss = new SimpleDoubleProperty(targetWeightLoss);
        this.targetCaloricDeficit = new SimpleIntegerProperty(targetCaloricDeficit);
    }

    public DoubleProperty targetWeightLossProperty(){
        return targetWeightLoss;
    }

    public IntegerProperty targetCaloricDeficitProperty(){
        return targetCaloricDeficit;
    }
}
