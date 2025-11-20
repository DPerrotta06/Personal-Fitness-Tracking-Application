package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.BulkingGoal;

/**
 * Concrete factory class for BulkingGoal
 */
public class BulkingGoalFactory implements IGoalFactory{
    @Override
    public BulkingGoal createNewGoal(GoalAttributeData attr) {
        return new BulkingGoal(attr.goalId(), attr.goalName(), attr.goalDescription(), attr.isCompleted(), attr.userId(), attr.targetWeightGain(), attr.targetDailyCaloricIntake());
    }
}
