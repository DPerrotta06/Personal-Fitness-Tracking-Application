package org.example.personalfitnesstracker.Factories;

public record GoalAttributeData(
        int goalId,
        String goalName,
        String goalDescription,
        boolean isCompleted,
        int userId,
        Integer targetRestingHeartRate,
        Double maxDistance,
        Integer heaviestLift,
        Integer maxRepCount,
        Integer maxSetsCount,
        Double targetWeightLoss,
        Integer targetCaloricDeficit,
        Double targetWeightGain,
        Integer targetDailyCaloricIntake
) {
}
