/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.Factories;

import org.example.personalfitnesstracker.Models.CardioGoal;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class CardioGoalFactoryTest {
    
    public CardioGoalFactoryTest() {
    }

    @Test
    public void testCreateNewGoal() { //fail
        System.out.println("createNewGoal");
        GoalAttributeData attr = null;
        CardioGoalFactory instance = new CardioGoalFactory();
        CardioGoal expResult = null;
        CardioGoal result = instance.createNewGoal(attr);
        assertEquals(expResult, result);
    }
    
}
