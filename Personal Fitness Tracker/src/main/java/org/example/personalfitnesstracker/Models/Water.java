package org.example.personalfitnesstracker.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDateTime;

public class Water extends Nutrition{

    private final DoubleProperty amountInLiters;

    public Water(int nutritionId, String nutritionDescription, LocalDateTime timeStamp, int userId, double amountInLiters) {
        super(nutritionId, nutritionDescription, timeStamp, userId);
        this.amountInLiters = new SimpleDoubleProperty(amountInLiters);
    }

    public DoubleProperty amountInLitersProperty() {
        return amountInLiters;
    }
}
