package org.example.personalfitnesstracker.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDateTime;

public class Sleep {

    private final IntegerProperty sleepSessionId;
    private final LocalDateTime sleepStart;
    private final LocalDateTime sleepEnd;
    private final IntegerProperty userId;

    public Sleep(int sleepSessionId, LocalDateTime sleepStart, LocalDateTime sleepEnd, int userId) {
        this.sleepSessionId = new SimpleIntegerProperty(sleepSessionId);
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.userId = new SimpleIntegerProperty(userId);
    }

    public IntegerProperty sleepSessionIdProperty() {
        return sleepSessionId;
    }

    public LocalDateTime sleepStartProperty() {
        return sleepStart;
    }

    public LocalDateTime sleepEndProperty() {
        return sleepEnd;
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }
}
