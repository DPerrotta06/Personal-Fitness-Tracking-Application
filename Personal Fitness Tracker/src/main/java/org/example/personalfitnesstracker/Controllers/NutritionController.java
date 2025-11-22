package org.example.personalfitnesstracker.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.Factories.FoodFactory;
import org.example.personalfitnesstracker.Factories.INutritionFactory;
import org.example.personalfitnesstracker.Factories.NutritionAttributeData;
import org.example.personalfitnesstracker.Factories.WaterFactory;
import org.example.personalfitnesstracker.Models.Nutrition;

import java.time.LocalDate;

/**
 * Controller responsible for creating and managing Nutrition logs
 * (Food and Water).
 */
public class NutritionController extends BaseController {

    private final INutritionFactory foodFactory;
    private final INutritionFactory waterFactory;

    private final ObservableList<Nutrition> nutritionLogs;

    public NutritionController() {
        this.foodFactory = new FoodFactory();
        this.waterFactory = new WaterFactory();
        this.nutritionLogs = FXCollections.observableArrayList();
    }

    public ObservableList<Nutrition> getNutritionLogs() {
        return nutritionLogs;
    }

    // WATER LOG
    public Nutrition addWaterLog(int nutritionId,
                                 String description,
                                 LocalDate timeStamp,
                                 int userId,
                                 double amountInLiters) {

        if (!isPositive(amountInLiters)) {
            log("Cannot add water log: amount must be positive.");
            return null;
        }

        NutritionAttributeData attr = new NutritionAttributeData(
                nutritionId,
                description,
                timeStamp,
                userId,
                amountInLiters,  // amountInLiters
                null,            // recipe
                null,            // calories
                null,            // protein
                null,            // carbs
                null,            // fats
                null             // foodServingSize
        );

        Nutrition waterLog = waterFactory.addNutritionLog(attr);
        nutritionLogs.add(waterLog);
        log("Water log added for user " + userId);
        return waterLog;
    }

    // FOOD LOG
    public Nutrition addFoodLog(int nutritionId,
                                String description,
                                LocalDate timeStamp,
                                int userId,
                                String recipe,
                                int calories,
                                int protein,
                                int carbs,
                                int fats,
                                double servingSize) {

        if (!isPositive(calories)) {
            log("Cannot add food log: calories must be positive.");
            return null;
        }

        NutritionAttributeData attr = new NutritionAttributeData(
                nutritionId,
                description,
                timeStamp,
                userId,
                null,           // amountInLiters
                recipe,
                calories,
                protein,
                carbs,
                fats,
                servingSize
        );

        Nutrition foodLog = foodFactory.addNutritionLog(attr);
        nutritionLogs.add(foodLog);
        log("Food log added for user " + userId);
        return foodLog;
    }
}
