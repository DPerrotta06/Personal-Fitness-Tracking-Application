package org.example.personalfitnesstracker.Factories;

import java.time.LocalDateTime;

public record WorkoutAttributeData(
    int workoutId,
    String workoutName,
    String workoutDescription,
    double workoutDuration,
    int caloriesBurned,
    LocalDateTime dateStamp,
    int userId,
    Integer totalSets,
    Integer totalReps,
    Double totalWeight,
    Double totalDistance,
    String heartRateZone
) {
}
