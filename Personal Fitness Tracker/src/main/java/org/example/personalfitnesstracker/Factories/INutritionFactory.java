package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.Nutrition;

/**
 * Interface factory for Nutrition types to implement
 */
public interface INutritionFactory {
    public Nutrition addNutritionLog(NutritionAttributeData attr);
}
