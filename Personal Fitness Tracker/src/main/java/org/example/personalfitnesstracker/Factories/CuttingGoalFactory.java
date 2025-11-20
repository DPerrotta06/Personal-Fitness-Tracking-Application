package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.CuttingGoal;

/**
 * Concrete factory class for CuttingGoal
 */
public class CuttingGoalFactory implements IGoalFactory{

    @Override
    public CuttingGoal createNewGoal(GoalAttributeData attr) {
        return new CuttingGoal(attr.goalId(), attr.goalName(), attr.goalDescription(), attr.isCompleted(), attr.userId(), attr.targetWeightLoss(), attr.targetCaloricDeficit());
    }
}
