package org.example.personalfitnesstracker.Models;

import javafx.beans.property.*;

public class Goal {

    protected final IntegerProperty goalId;
    protected final StringProperty goalName;
    protected final StringProperty goalDescription;
    protected final BooleanProperty isCompleted;
    protected final IntegerProperty userId;

    public Goal(int goalId, String goalName, String goalDescription, boolean isCompleted, int userId) {
        this.goalId = new SimpleIntegerProperty(goalId);
        this.goalName = new SimpleStringProperty(goalName);
        this.goalDescription = new SimpleStringProperty(goalDescription);
        this.isCompleted = new SimpleBooleanProperty(isCompleted);
        this.userId = new SimpleIntegerProperty(userId);
    }

     public IntegerProperty goalIdProperty(){
        return goalId;
     }

    public StringProperty goalNameProperty() {
        return goalName;
    }

    public StringProperty goalDescriptionProperty() {
        return goalDescription;
    }

    public BooleanProperty isCompletedProperty() {
        return isCompleted;
    }

    public IntegerProperty userIdProperty(){
        return userId;
    }


}
