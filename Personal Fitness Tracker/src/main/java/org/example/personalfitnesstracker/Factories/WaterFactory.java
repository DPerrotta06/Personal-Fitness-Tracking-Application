package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.Nutrition;
import org.example.personalfitnesstracker.Models.Water;

public class WaterFactory implements INutritionFactory{
    @Override
    public Nutrition addNutritionLog(NutritionAttributeData attr) {
        return new Water(attr.nutritionId(), attr.nutritionDescription(), attr.timeStamp(), attr.userId(), attr.amountInLiters());
    }
}
