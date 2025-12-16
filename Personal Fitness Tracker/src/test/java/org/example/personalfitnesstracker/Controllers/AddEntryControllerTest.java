/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.Controllers;

import java.time.LocalDate;
import java.time.Month;
import org.example.personalfitnesstracker.Models.User;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class AddEntryControllerTest {

    @Test
    public void testSetUser() {
        System.out.println("setUser");
        User user = new User(1, "password123".getBytes(), "test@example.com", 70.5, 175, LocalDate.of(2006, Month.JANUARY, 22), "TestUser");
        AddEntryController instance = new AddEntryController();
        instance.setUser(user);
    }

    @Test
    public void testSetOnEntrySaved() {
        System.out.println("setOnEntrySaved");
        Runnable callback = null;
        AddEntryController instance = new AddEntryController();
        instance.setOnEntrySaved(callback);
    }

    @Test
    public void testInitialize() { //fail
        System.out.println("initialize");
        AddEntryController instance = new AddEntryController();
        instance.initialize();
    }
    
}
