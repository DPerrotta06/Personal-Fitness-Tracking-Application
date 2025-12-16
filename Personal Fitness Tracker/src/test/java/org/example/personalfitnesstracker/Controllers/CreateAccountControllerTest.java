/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.Controllers;

import java.time.LocalDate;
import java.time.Month;
import org.example.personalfitnesstracker.Models.User;
import org.example.personalfitnesstracker.Views.CreateAccountView;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class CreateAccountControllerTest {
    
    public CreateAccountControllerTest() {
    }

    @Test
    public void testShow() { //fail
        System.out.println("show");
        CreateAccountController instance = new CreateAccountController(new CreateAccountView());
        instance.show();
    }

   @Test
    public void testCreateUser() { //fail
        System.out.println("createUser");
        int userId = 10;
        byte[] password = "testPassword".getBytes();
        String email = "testuser123@example.com";
        double weight = 74.0;
        double height = 190.0;
        LocalDate dateOfBirth = LocalDate.of(2000, Month.MARCH, 19);
        String username = "myTestUser";
        CreateAccountController instance = new CreateAccountController(new CreateAccountView());
        User result = instance.createUser(userId, password, email, weight, height, dateOfBirth, username);
        assertNotNull(result);
    }

    @Test
    public void testSetUpHandlers() { //fail
        System.out.println("setUpHandlers");
        CreateAccountController instance = new CreateAccountController(new CreateAccountView());
        instance.setUpHandlers();
    }
    
}
