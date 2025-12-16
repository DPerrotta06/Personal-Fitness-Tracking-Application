/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.Controllers;

import java.time.LocalDate;
import org.example.personalfitnesstracker.Models.User;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class NutritionControllerTest {

    public NutritionControllerTest() {
    }

    @Test
    public void testSetUser() { //fail
        System.out.println("setUser");
        User user = new User(720, "anotherPassword".getBytes(), "user@example.com", 65.0, 168, LocalDate.of(1990, 3, 20), "AnotherUser");
        NutritionController instance = new NutritionController();
        instance.setUser(user);
    }

}
