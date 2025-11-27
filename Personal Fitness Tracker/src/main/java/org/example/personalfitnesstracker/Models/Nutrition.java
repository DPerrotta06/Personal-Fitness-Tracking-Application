package org.example.personalfitnesstracker.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class Nutrition {

    protected final IntegerProperty nutritionId;
    protected final StringProperty nutritionDescription;
    protected final LocalDateTime timeStamp;
    protected final IntegerProperty userId;

    public Nutrition(int nutritionId, String nutritionDescription, LocalDateTime timeStamp, int userId) {
        this.nutritionId = new SimpleIntegerProperty(nutritionId);
        this.nutritionDescription = new SimpleStringProperty(nutritionDescription);
        this.timeStamp = timeStamp;
        this.userId = new SimpleIntegerProperty(userId);
    }

    public IntegerProperty nutritionIdProperty() {
        return nutritionId;
    }

    public StringProperty nutritionDescriptionProperty() {
        return nutritionDescription;
    }

    public LocalDateTime timeStampProperty() {
        return timeStamp;
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }
}
