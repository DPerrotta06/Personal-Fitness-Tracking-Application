package org.example.personalfitnesstracker.Controllers;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;
import org.example.personalfitnesstracker.Views.CreateAccountView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateAccountController {

    private final CreateAccountView createAccountView;
    private final DatabaseManager databaseManager;

    public CreateAccountController(CreateAccountView createAccountView) {
        this.createAccountView = createAccountView;
        try {
            this.databaseManager = new DatabaseManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setupHandlers();
    }

    public void show() {
        createAccountView.show();
    }

    private void setupHandlers() {
        // need to display proper error window later instead of printing to console
        createAccountView.getCreateButton().setOnAction(ev -> {
            String username = createAccountView.getUsernameField().getText();
            String email = createAccountView.getEmailField().getText();
            if (!email.contains("@")) {
                System.out.println("Invalid email format.");
                return;
            }
            String passwordText = createAccountView.getPasswordField().getText();
            byte[] password = passwordText.getBytes();
            String weightText = createAccountView.getWeightField().getText();
            String heightText = createAccountView.getHeightField().getText();
            double weight = Double.parseDouble(weightText);
            double height = Double.parseDouble(heightText);
            String dateOfBirthText = createAccountView.getDateOfBirthField().getText();

            // Format Date of Birth (yyyy-MM-dd)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = null;
            try {
                dob = dateFormat.parse(dateOfBirthText);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("Invalid Date format. Please use yyyy-MM-dd.");
                return;
            }

            User newUser = new User(0, username, password, email, weight, height, (java.sql.Date) dob);
            DatabaseManager.addNewUserToDb(newUser);
            createAccountView.close();
        });
    }
}
