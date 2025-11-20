package org.example.personalfitnesstracker.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;

public class Sleep {
    private final IntegerProperty sleepSessionId;
    private final LocalDate sleepStart;
    private final LocalDate sleepEnd;
    private final IntegerProperty userId;

    public Sleep(int sleepSessionId, LocalDate sleepStart, LocalDate sleepEnd, int userId) {
        this.sleepSessionId = new SimpleIntegerProperty(sleepSessionId);
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.userId = new SimpleIntegerProperty(userId);
    }

    public IntegerProperty sleepSessionIdProperty(){
        return sleepSessionId;
    }

    public LocalDate sleepStartProperty(){
        return sleepStart;
    }

    public LocalDate sleepEndProperty(){
        return sleepEnd;
    }
}
