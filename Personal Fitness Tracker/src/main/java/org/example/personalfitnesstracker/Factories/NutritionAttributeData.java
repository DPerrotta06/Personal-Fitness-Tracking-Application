package org.example.personalfitnesstracker.Factories;

import java.time.LocalDate;

public record NutritionAttributeData(
        int nutritionId,
        String nutritionDescription,
        LocalDate timeStamp,
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
