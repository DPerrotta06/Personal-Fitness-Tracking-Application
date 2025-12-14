package org.example.personalfitnesstracker.Threads;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;

import java.time.LocalDate;
import java.util.Map;

public class MainPageLoadThread implements Runnable {

    public Thread thread;

    private final String message;
    private final int userId;

    public double calories;
    public double water;
    public double[] sleep;
    public Map<LocalDate, Double> weeklyCalories;

    public MainPageLoadThread(String message, int userId) {
        this.message = message;
        this.userId = userId;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        System.out.println(message);
        calories = DatabaseManager.getDailyCalories(userId);
        water = DatabaseManager.getDailyWater(userId);
        sleep = DatabaseManager.getDailySleep(userId);
        weeklyCalories = DatabaseManager.getWeeklyCalories(userId);
    }
}
