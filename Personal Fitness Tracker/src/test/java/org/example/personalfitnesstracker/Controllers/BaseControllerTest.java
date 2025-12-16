/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.example.personalfitnesstracker.Controllers;

import java.util.logging.Level;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danie
 */
public class BaseControllerTest {
    
    public BaseControllerTest() {
    }

    @Test
    public void testIsNullOrEmpty() {
        System.out.println("isNullOrEmpty");
        String value = "";
        BaseController instance = new BaseControllerImpl();
        boolean result = instance.isNullOrEmpty(value);
        assertTrue(result);
    }

    @Test
    public void testIsPositive_double() {
        System.out.println("isPositive");
        double value = -5.5;
        BaseController instance = new BaseControllerImpl();
        boolean result = instance.isPositive(value);
        assertFalse(result);
    }

    @Test
    public void testIsPositive_int() {
        System.out.println("isPositive");
        int value = -10;
        BaseController instance = new BaseControllerImpl();
        boolean result = instance.isPositive(value);
        assertFalse(result);
    }

    @Test
    public void testLog() {
        System.out.println("log");
        String message = "Test error message";
        Level level = Level.SEVERE;
        BaseController instance = new BaseControllerImpl();
        instance.log(message, level);
    }

    @Test
    public void testIsValidEmail() {
        System.out.println("isValidEmail");
        String email = "123@example.com";
        boolean result = BaseController.isValidEmail(email);
        assertTrue(result);
    }

    @Test
    public void testIsValidPassword() {
        System.out.println("isValidPassword");
        byte[] password = new byte[0];
        boolean result = BaseController.isValidPassword(password);
        assertFalse(result);
    }

    @Test
    public void testShowMessageWindow() { //fail
        System.out.println("showMessageWindow");
        String title = "Info";
        String msg = "Info message";
        Alert.AlertType alertType = Alert.AlertType.INFORMATION;
        ButtonType button = ButtonType.OK;
        BaseController instance = new BaseControllerImpl();
        instance.showMessageWindow(title, msg, alertType, button);
    }

    public class BaseControllerImpl extends BaseController {
    }
    
}
