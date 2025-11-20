package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.CardioGoal;

/**
 *Concrete factory class for CardioGoal
 */
public class CardioGoalFactory implements IGoalFactory{
    @Override
    public CardioGoal createNewGoal(GoalAttributeData attr) {
        return new CardioGoal(attr.goalId(), attr.goalName(), attr.goalDescription(), attr.isCompleted(), attr.userId(), attr.targetRestingHeartRate(), attr.maxDistance());
    }
}
