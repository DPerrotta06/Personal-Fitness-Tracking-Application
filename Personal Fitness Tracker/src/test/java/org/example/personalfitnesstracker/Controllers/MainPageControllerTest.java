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
public class MainPageControllerTest {
    
    public MainPageControllerTest() {
    }

    @Test
    public void testInitialize() { //fail
        System.out.println("initialize");
        MainPageController instance = new MainPageController();
        instance.initialize();
    }

    @Test
    public void testSetUser() { //fail
        System.out.println("setUser");
        User user = new User(70, "userPassword".getBytes(), "vaniercollege@edu.ca", 80, 200, LocalDate.now(), "setUserTest");
        MainPageController instance = new MainPageController();
        instance.setUser(user);
    }
    
}
