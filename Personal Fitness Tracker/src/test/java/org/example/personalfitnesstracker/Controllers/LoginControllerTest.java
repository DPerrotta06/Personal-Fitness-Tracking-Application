/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.Controllers;

import org.example.personalfitnesstracker.Views.LoginView;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class LoginControllerTest {
    
    public LoginControllerTest() {
    }

    @Test
    public void testShow() { //fail
        System.out.println("show");
        LoginController instance = new LoginController(new LoginView());
        instance.show();
    }

    @Test
    public void testSetupHandlers() { //fail
        System.out.println("setupHandlers");
        LoginController instance = new LoginController(new LoginView());
        instance.setupHandlers();
    }

}
