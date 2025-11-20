package org.example.personalfitnesstracker.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Food extends Nutrition{

    private final StringProperty recipe;
    private final IntegerProperty calories;
    private final IntegerProperty protein;
    private final IntegerProperty carbs;
    private final IntegerProperty fats;
    private final DoubleProperty foodServingSize;

    public Food(int nutritionId, String nutritionDescription, LocalDate timeStamp, int userId, String recipe, int calories, int protein, int carbs, int fats, double foodServingSize) {
        super(nutritionId, nutritionDescription, timeStamp, userId);
        this.recipe = new SimpleStringProperty(recipe);
        this.calories = new SimpleIntegerProperty(calories);
        this.protein = new SimpleIntegerProperty(protein);
        this.carbs = new SimpleIntegerProperty(carbs);
        this.fats = new SimpleIntegerProperty(fats);
        this.foodServingSize = new SimpleDoubleProperty(foodServingSize);
    }

    public StringProperty recipeProperty() {
        return recipe;
    }

    public IntegerProperty caloriesProperty() {
        return calories;
    }

    public IntegerProperty proteinProperty() {
        return protein;
    }

    public IntegerProperty carbsProperty() {
        return carbs;
    }

    public IntegerProperty fatsProperty() {
        return fats;
    }

    public DoubleProperty foodServingSizeProperty() {
        return foodServingSize;
    }
}
