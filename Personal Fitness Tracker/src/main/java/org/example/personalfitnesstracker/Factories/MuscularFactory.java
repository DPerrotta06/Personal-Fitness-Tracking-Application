package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.MuscularWorkout;
import org.example.personalfitnesstracker.Models.Workout;

public class MuscularFactory implements IWorkoutFactory{
    @Override
    public Workout addNewWorkoutSession(WorkoutAttributeData attr) {
        return new MuscularWorkout(attr.workoutId(), attr.workoutName(), attr.workoutDescription(), attr.workoutDuration(), attr.caloriesBurned(), attr.dateStamp(), attr.userId(), attr.totalSets(), attr.totalReps(), attr.totalWeight());
    }
}
