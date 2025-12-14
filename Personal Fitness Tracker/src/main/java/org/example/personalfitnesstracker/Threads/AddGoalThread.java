package org.example.personalfitnesstracker.Threads;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.Goal;

public class AddGoalThread implements Runnable {

    public Thread thread;
    private final String message;
    private final Goal goal;

    public AddGoalThread(String message, Goal goal) {
        this.message = message;
        this.goal = goal;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        System.out.println(message);
        DatabaseManager.addNewGoalToDb(goal);
    }
}
