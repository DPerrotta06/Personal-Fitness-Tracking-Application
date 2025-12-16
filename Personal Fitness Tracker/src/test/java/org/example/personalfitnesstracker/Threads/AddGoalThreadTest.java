/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.Threads;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class AddGoalThreadTest {
    
    public AddGoalThreadTest() {
    }

    @Test
    public void testRun() {//fail
        System.out.println("run");
        AddGoalThread instance = null;
        instance.run();
    }
    
}
