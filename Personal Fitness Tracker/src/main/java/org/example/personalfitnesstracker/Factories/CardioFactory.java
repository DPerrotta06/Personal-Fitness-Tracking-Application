package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.CardioWorkout;
import org.example.personalfitnesstracker.Models.Workout;

public class CardioFactory implements IWorkoutFactory{
    @Override
    public Workout addNewWorkoutSession(WorkoutAttributeData attr) {
        return new CardioWorkout(attr.workoutId(), attr.workoutName(), attr.workoutDescription(), attr.workoutDuration(), attr.caloriesBurned(), attr.dateStamp(), attr.userId(), attr.totalDistance(), attr.heartRateZone());
    }
}
