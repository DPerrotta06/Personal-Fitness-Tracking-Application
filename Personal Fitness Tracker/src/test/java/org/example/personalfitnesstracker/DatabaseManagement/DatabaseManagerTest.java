/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.DatabaseManagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.Models.CardioGoal;
import org.example.personalfitnesstracker.Models.Entry;
import org.example.personalfitnesstracker.Models.Food;
import org.example.personalfitnesstracker.Models.Goal;
import org.example.personalfitnesstracker.Models.MuscularWorkout;
import org.example.personalfitnesstracker.Models.Nutrition;
import org.example.personalfitnesstracker.Models.Sleep;
import org.example.personalfitnesstracker.Models.User;
import org.example.personalfitnesstracker.Models.Water;
import org.example.personalfitnesstracker.Models.Workout;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class DatabaseManagerTest {
    
    public DatabaseManagerTest() {
    }

    @Test
    public void testUserExists() {
        System.out.println("userExists");
        String email = "test123@example.com";
        byte[] hashedPw = "password".getBytes();
        boolean expResult = false;
        boolean result = DatabaseManager.userExists(email, hashedPw);
        assertEquals(expResult, result);
    }

    @Test
    public void testAddNewUserToDb() {
        System.out.println("addNewUserToDb");
        User user = new User(1, "password".getBytes(), "123@example.com", 78.8, 180, LocalDate.now(), "myUser");
        DatabaseManager.addNewUserToDb(user);
    }

    @Test
    public void testAddEntry() {
        System.out.println("addEntry");
        Entry entry = new Entry(1, 1, "Test", 45.0, "testing entry", LocalDateTime.now());
        DatabaseManager.addEntry(entry);
    }

    @Test
    public void testAddNewSleepSessionToDb() {
        System.out.println("addNewSleepSessionToDb");
        Sleep sleep = new Sleep(1, LocalDateTime.now(), LocalDateTime.now(), 1);
        DatabaseManager.addNewSleepSessionToDb(sleep);
    }

    @Test
    public void testAddNewNutritionToDb() {
        System.out.println("addNewNutritionToDb");
        Nutrition nutrition = new Nutrition(1, "nutrition description", LocalDateTime.now(), 1);
        DatabaseManager.addNewNutritionToDb(nutrition);
    }

    @Test
    public void testAddNewGoalToDb() {
        System.out.println("addNewGoalToDb");
        Goal goal = new CardioGoal(1, "name", "description", false, 1, 60, 2.3);
        DatabaseManager.addNewGoalToDb(goal);
    }

    @Test
    public void testAddNewWorkoutToDb() {
        System.out.println("addNewWorkoutToDb");
        Workout workout = new MuscularWorkout(1, "name", "description", 30, 190, 1, LocalDateTime.now(), 5, 20, 0);
        DatabaseManager.addNewWorkoutToDb(workout);
    }

    @Test
    public void testGetUserById() { //fail
        System.out.println("getUserById");
        int userId = 1;
        User result = DatabaseManager.getUserById(userId);
        assertNotNull(result);
    }

    @Test
    public void testGetUserByEmailAndPw() { //fail 
        System.out.println("getUserByEmailAndPw");
        String email = "123test@example.com";
        byte[] password = "mypassword".getBytes();
        User result = DatabaseManager.getUserByEmailAndPw(email, password);
        assertNotNull(result);
    }

    @Test
    public void testDisplaySleepingLogs() {
        System.out.println("displaySleepingLogs");
        int userID = 1;
        ObservableList<Sleep> result = DatabaseManager.displaySleepingLogs(userID);
        assertNotNull(result);
    }

    @Test
    public void testDisplayNutritionLogs() {
        System.out.println("displayNutritionLogs");
        int userID = 1;
        ObservableList<Nutrition> result = DatabaseManager.displayNutritionLogs(userID);
        assertNotNull(result);
    }

    @Test
    public void testDisplayWorkoutLogs() {
        System.out.println("displayWorkoutLogs");
        int userID = 1;
        ObservableList<Workout> result = DatabaseManager.displayWorkoutLogs(userID);
        assertNotNull(result);
    }

    @Test
    public void testDisplayGoals() {
        System.out.println("displayGoals");
        int userID = 1;
        ObservableList<Goal> result = DatabaseManager.displayGoals(userID);
        assertNotNull(result);
    }

    @Test
    public void testGetDailyCalories() {
        System.out.println("getDailyCalories");
        int userId = 1;
        double result = DatabaseManager.getDailyCalories(userId);
        assertTrue(result >= 0.0);
    }

    @Test
    public void testGetDailyWater() {
        System.out.println("getDailyWater");
        int userId = 1;
        double result = DatabaseManager.getDailyWater(userId);
        assertTrue(result >= 0);
    }

    @Test
    public void testGetDailySleep() {
        System.out.println("getDailySleep");
        int userId = 1;
        double[] result = DatabaseManager.getDailySleep(userId);
        assertNotNull(result);
    }

    @Test
    public void testGetWeeklyCalories() {
        System.out.println("getWeeklyCalories");
        int userId = 1;
        Map<LocalDate, Double> result = DatabaseManager.getWeeklyCalories(userId);
        assertNotNull(result);
    }

    @Test
    public void testGetAllFood() {
        System.out.println("getAllFood");
        int userId = 1;
        List<Food> result = DatabaseManager.getAllFood(userId);
        assertNotNull(result);
    }

    @Test
    public void testGetAllWater() {
        System.out.println("getAllWater");
        int userId = 1;
        List<Water> result = DatabaseManager.getAllWater(userId);
        assertNotNull(result);
    }

    @Test
    public void testUpdateUser() { 
        System.out.println("updateUser");
        User user = new User(1, "newpassword".getBytes(), "update@example.com", 80.0, 175, LocalDate.now(), "updatedUser");
        DatabaseManager.updateUser(user);
    }

    @Test
    public void testUpdateSleep() {
        System.out.println("updateSleep");
        Sleep sleep = new Sleep(1, LocalDateTime.now(), LocalDateTime.now().plusHours(7), 1);
        DatabaseManager.updateSleep(sleep);
    }

    @Test
    public void testUpdateGoal() {
        System.out.println("updateGoal");
        Goal goal = new CardioGoal(1, "Updated Goal", "Updated description", true, 1, 90, 3.0);
        DatabaseManager.updateGoal(goal);
    }

    @Test
    public void testUpdateWorkout() {
        System.out.println("updateWorkout");
        Workout workout = new MuscularWorkout(1, "Updated Workout", "Updated description", 45, 200, 1, LocalDateTime.now(), 8, 15, 0);
        DatabaseManager.updateWorkout(workout);
    }

    @Test
    public void testUpdateNutrition() {
        System.out.println("updateNutrition");
        Nutrition nutrition = new Nutrition(1, "Updated meal description", LocalDateTime.now(), 1);
        DatabaseManager.updateNutrition(nutrition);
    }

    @Test
    public void testDeleteUser() {
        System.out.println("deleteUser");
        int userId = 999;
        DatabaseManager.deleteUser(userId);
    }

    @Test
    public void testDeleteNutrition() {
        System.out.println("deleteNutrition");
        int nutritionId = 999;
        DatabaseManager.deleteNutrition(nutritionId);
    }

    @Test
    public void testDeleteWorkout() {
        System.out.println("deleteWorkout");
        int workoutId = 999;
        int userId = 1;
        DatabaseManager.deleteWorkout(workoutId, userId);
    }

    @Test
    public void testLoadUserDataInParallel() {
        System.out.println("loadUserDataInParallel");
        int userId = 1;
        DatabaseManager.loadUserDataInParallel(userId);
    }
    
}
