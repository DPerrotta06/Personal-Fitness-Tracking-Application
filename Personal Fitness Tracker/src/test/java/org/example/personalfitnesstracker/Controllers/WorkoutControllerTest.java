/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.Controllers;

import org.example.personalfitnesstracker.Models.User;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class WorkoutControllerTest {
    
    public WorkoutControllerTest() {
    }

    @Test
    public void testSetUser() {
        System.out.println("setUser");
        User user = null;
        WorkoutController instance = new WorkoutController();
        instance.setUser(user);
    }
    
}
