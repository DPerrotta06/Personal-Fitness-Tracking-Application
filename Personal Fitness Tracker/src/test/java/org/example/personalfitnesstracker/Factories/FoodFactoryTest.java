/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.Nutrition;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class FoodFactoryTest {
    
    public FoodFactoryTest() {
    }

    @Test
    public void testAddNutritionLog() { //fail
        System.out.println("addNutritionLog");
        NutritionAttributeData attr = null;
        FoodFactory instance = new FoodFactory();
        Nutrition expResult = null;
        Nutrition result = instance.addNutritionLog(attr);
        assertEquals(expResult, result);
    }
    
}
