/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.Factories;

import java.time.LocalDateTime;
import org.example.personalfitnesstracker.Models.Workout;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class CardioFactoryTest {
    
    public CardioFactoryTest() {
    }

    @Test
    public void testAddNewWorkoutSession() { //fail
        System.out.println("addNewWorkoutSession");
    CardioFactory instance = new CardioFactory();
    
    // Test with valid cardio WorkoutAttributeData
    WorkoutAttributeData validAttr = new WorkoutAttributeData(
        1,                              // workoutId
        "Morning Run",                  // workoutName
        "5km run in the park",         // workoutDescription
        30.0,                           // workoutDuration (double)
        250,                            // caloriesBurned
        LocalDateTime.now(),            // dateStamp
        1,                              // userId
        null,                           // totalSets (null for cardio)
        null,                           // totalReps (null for cardio)
        null,                           // totalWeight (null for cardio)
        5.0,                            // totalDistance (km)
        "Moderate"                      // heartRateZone
    );
    
    Workout result = instance.addNewWorkoutSession(validAttr);
    assertNotNull(result);
    assertEquals("Morning Run", result.workoutNameProperty());
    assertEquals(30.0, result.workoutDurationProperty());
    assertEquals(250, result.caloriesBurnedProperty());
    }
    
}
