/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.CuttingGoal;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class CuttingGoalFactoryTest {
    
    public CuttingGoalFactoryTest() {
    }

    @Test
    public void testCreateNewGoal() { //fail
        System.out.println("createNewGoal");
        GoalAttributeData attr = null;
        CuttingGoalFactory instance = new CuttingGoalFactory();
        CuttingGoal expResult = null;
        CuttingGoal result = instance.createNewGoal(attr);
        assertEquals(expResult, result);
    }
    
}
