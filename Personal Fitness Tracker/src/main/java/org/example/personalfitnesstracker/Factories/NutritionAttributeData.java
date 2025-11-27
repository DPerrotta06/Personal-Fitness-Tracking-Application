package org.example.personalfitnesstracker.Factories;

import java.time.LocalDateTime;

public record NutritionAttributeData(
        int nutritionId,
        String nutritionDescription,
        LocalDateTime timeStamp,
        int userId,
        Double amountInLiters,
        String recipe,
        Integer calories,
        Integer protein,
        Integer carbs,
        Integer fats,
        Double foodServingSize
) {
}
