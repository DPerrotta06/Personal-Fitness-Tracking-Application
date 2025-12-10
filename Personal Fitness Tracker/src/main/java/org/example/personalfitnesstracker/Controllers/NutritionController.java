package org.example.personalfitnesstracker.Controllers;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.Factories.FoodFactory;
import org.example.personalfitnesstracker.Factories.INutritionFactory;
import org.example.personalfitnesstracker.Factories.NutritionAttributeData;
import org.example.personalfitnesstracker.Factories.WaterFactory;
import org.example.personalfitnesstracker.Models.Nutrition;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;

/**
 * Controller responsible for creating and managing Nutrition logs (Food and
 * Water).
 */
public class NutritionController extends BaseController {

    private final INutritionFactory foodFactory;
    private final INutritionFactory waterFactory;
    private final ObservableList<Nutrition> nutritionLogs;

    /**
     * Constructor
     */
    public NutritionController(User loggedUser) {
        super(loggedUser);
        this.foodFactory = new FoodFactory();
        this.waterFactory = new WaterFactory();
        this.nutritionLogs = FXCollections.observableArrayList();
    }

    /**
     *
     * @return
     */
    public ObservableList<Nutrition> getNutritionLogs() {
        return nutritionLogs;
    }

    /**
     * Adding a water log to the UI
     *
     * @param nutritionId
     * @param description
     * @param timeStamp
     * @param userId
     * @param amountInLiters
     * @return
     */
    public Nutrition addWaterLog(int nutritionId,
            String description,
            LocalDateTime timeStamp,
            int userId,
            double amountInLiters) {

        if (!isPositive(amountInLiters)) {
            return null;
        }

        NutritionAttributeData attr = new NutritionAttributeData(
                nutritionId,
                description,
                timeStamp,
                userId,
                amountInLiters, // amountInLiters
                null, // recipe
                null, // calories
                null, // protein
                null, // carbs
                null, // fats
                null // foodServingSize
        );

        Nutrition waterLog = waterFactory.addNutritionLog(attr);
        nutritionLogs.add(waterLog);
        return waterLog;
    }

    /**
     * Adding a food log to the UI
     *
     * @param nutritionId
     * @param description
     * @param timeStamp
     * @param userId
     * @param recipe
     * @param calories
     * @param protein
     * @param carbs
     * @param fats
     * @param servingSize
     * @return
     */
    public Nutrition addFoodLog(int nutritionId,
            String description,
            LocalDateTime timeStamp,
            int userId,
            String recipe,
            int calories,
            int protein,
            int carbs,
            int fats,
            double servingSize) {

        if (!isPositive(calories)) {
            return null;
        }

        NutritionAttributeData attr = new NutritionAttributeData(
                nutritionId,
                description,
                timeStamp,
                userId,
                null, // amountInLiters
                recipe,
                calories,
                protein,
                carbs,
                fats,
                servingSize
        );

        Nutrition foodLog = foodFactory.addNutritionLog(attr);
        nutritionLogs.add(foodLog);
        return foodLog;
    }

    /**
     *
     * @param date
     * @return
     */
    public ObservableList<Nutrition> filterByDate(LocalDate date) {
        ObservableList<Nutrition> nutrient = DatabaseManager.displayNutritionLogs(loggedUser.userIdProperty().get());
        List<Nutrition> filtered = nutrient.parallelStream().filter(n -> {
            LocalDate nutritionDate = n.timeStampProperty().toLocalDate();
            return nutritionDate.isEqual(date);
        }).collect(Collectors.toList());
        return FXCollections.observableArrayList(filtered);
    }

    /**
     *
     * @param meal
     * @return
     */
    public ObservableList<Nutrition> filterByMeal(String meal) {
        ObservableList<Nutrition> nutrient = DatabaseManager.displayNutritionLogs(loggedUser.userIdProperty().get());
        List<Nutrition> filtered = nutrient.parallelStream().filter(n -> {
            String foodEaten = n.nutritionDescriptionProperty().get();
            return foodEaten.equalsIgnoreCase(meal);
        }).collect(Collectors.toList());
        return FXCollections.observableArrayList(filtered);
    }
}
