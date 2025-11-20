package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.Workout;

public interface IWorkoutFactory {
    public Workout addNewWorkoutSession(WorkoutAttributeData attr);
}
