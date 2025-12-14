package org.example.personalfitnesstracker.Threads;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.Workout;

public class AddWorkoutThread implements Runnable {

    public Thread thread;

    private final String message;
    private final Workout workout;

    public AddWorkoutThread(String message, Workout workout) {
        this.message = message;
        this.workout = workout;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        System.out.println(message);
        DatabaseManager.addNewWorkoutToDb(workout);
    }
}
