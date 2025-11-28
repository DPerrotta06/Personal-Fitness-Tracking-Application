package org.example.personalfitnesstracker.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MuscularGoal extends Goal {

    private final DoubleProperty heaviestLift;
    private final IntegerProperty maxRepCount;
    private final IntegerProperty maxSetsCount;

    public MuscularGoal(int goalId, String goalName, String goalDescription, boolean isCompleted, int userId, double heaviestLift, int maxRepCount, int maxSetsCount) {
        super(goalId, goalName, goalDescription, isCompleted, userId);
        this.heaviestLift = new SimpleDoubleProperty(heaviestLift);
        this.maxRepCount = new SimpleIntegerProperty(maxRepCount);
        this.maxSetsCount = new SimpleIntegerProperty(maxSetsCount);
    }

    public DoubleProperty heaviestLiftProperty() {
        return heaviestLift;
    }

    public IntegerProperty maxRepCountProperty() {
        return maxRepCount;
    }

    public IntegerProperty maxSetsCount() {
        return maxSetsCount;
    }
}
