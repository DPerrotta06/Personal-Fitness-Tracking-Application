package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.Food;
import org.example.personalfitnesstracker.Models.Nutrition;


public class FoodFactory implements INutritionFactory{
    @Override
    public Nutrition addNutritionLog(NutritionAttributeData attr) {
        return new Food(attr.nutritionId(), attr.nutritionDescription(), attr.timeStamp(), attr.userId(), attr.recipe(), attr.calories(), attr.protein(), attr.carbs(), attr.fats(), attr.foodServingSize());
    }
}
