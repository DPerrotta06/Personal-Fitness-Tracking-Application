package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.Goal;

/**
 * Factory interface for Goal types
 */
public interface IGoalFactory {
    public org.example.personalfitnesstracker.Models.Goal createNewGoal(GoalAttributeData attr);
}
