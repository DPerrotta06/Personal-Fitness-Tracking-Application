package org.example.personalfitnesstracker.Models;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MuscularGoal extends Goal{
    
    private final IntegerProperty heaviestLift;
    private final IntegerProperty maxRepCount;
    private final IntegerProperty maxSetsCount;

    public MuscularGoal(int goalId, String goalName, String goalDescription, boolean isCompleted, int userId, int heaviestLift, int maxRepCount, int maxSetsCount) {
        super(goalId, goalName, goalDescription, isCompleted, userId);
        this.heaviestLift = new SimpleIntegerProperty(heaviestLift);
        this.maxRepCount = new SimpleIntegerProperty(maxRepCount);
        this.maxSetsCount = new SimpleIntegerProperty(maxSetsCount);
    }

    public IntegerProperty heaviestLiftProperty(){
        return heaviestLift;
    }

    public IntegerProperty maxRepCountProperty(){
        return maxRepCount;
    }

    public IntegerProperty maxSetsCount(){
        return maxSetsCount;
    }
}
