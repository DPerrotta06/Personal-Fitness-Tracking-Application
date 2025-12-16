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
public class LoginThreadTest {
    
    public LoginThreadTest() {
    }

    @Test
    public void testRun() { //fail
        System.out.println("run");
        LoginThread instance = null;
        instance.run();
    }
    
}
