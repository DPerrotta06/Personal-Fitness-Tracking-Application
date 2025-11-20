package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.MuscularGoal;

/**
 * Concrete factory class for MuscularGoal
 */
public class MuscularGoalFactory implements IGoalFactory{
    @Override
    public MuscularGoal createNewGoal(GoalAttributeData attr) {
        return new MuscularGoal(attr.goalId(), attr.goalName(), attr.goalDescription(), attr.isCompleted(), attr.userId(), attr.heaviestLift(), attr.maxRepCount(), attr.maxSetsCount());
    }
}
