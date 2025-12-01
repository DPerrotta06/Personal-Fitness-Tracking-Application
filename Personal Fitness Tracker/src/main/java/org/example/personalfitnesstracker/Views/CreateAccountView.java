package org.example.personalfitnesstracker.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateAccountView extends Stage {
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField emailField;
    private final TextField weightField;
    private final TextField heightField;
    private final TextField dateOfBirthField;
    private final Button createButton;
//-----------------------NEED TO ALIGN HBOXES-----------------------
    public CreateAccountView() {
        setTitle("Create Account");
        // --- Main Layout ---
        VBox root = new VBox();
        root.setSpacing(12);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #000000;");

        // --- App Title ---
        Label title = new Label("CREATE ACCOUNT");
        title.setStyle("""
                -fx-font-size: 26px;
                -fx-text-fill: white;
                -fx-font-family: Arial, Helvetica, sans-serif;
                -fx-font-weight: bold;
                """);

        // --- Username ---
        HBox usernameRow = new HBox(10);
        usernameRow.setAlignment(Pos.CENTER);
        Label usernameLabel = new Label("USERNAME:");
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setMaxWidth(220);
        usernameField.setStyle("""
                -fx-background-radius: 8;
                -fx-padding: 6;
        """);

        usernameRow.getChildren().addAll(usernameLabel, usernameField);
        // --- Email ---
        HBox emailRow = new HBox(10);
        emailRow.setAlignment(Pos.CENTER);
        Label emailLabel = new Label("EMAIL:");
        emailLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        emailField = new TextField();
        emailField.setPromptText("Enter email");
        emailField.setMaxWidth(230);
        emailField.setStyle("""
                -fx-background-radius: 8;
                -fx-padding: 6;
                """);

        emailRow.getChildren().addAll(emailLabel, emailField);

        // --- Password ---
        HBox passwordRow = new HBox(10);
        passwordRow.setAlignment(Pos.CENTER);
        Label passwordLabel = new Label("PASSWORD:");
        passwordLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setMaxWidth(230);
        passwordField.setStyle("""
                -fx-background-radius: 8;
                -fx-padding: 6;
                """);

        passwordRow.getChildren().addAll(passwordLabel, passwordField);
        // --- Weight ---
        HBox weightRow = new HBox(10);
        weightRow.setAlignment(Pos.CENTER);
        Label weightLabel = new Label("WEIGHT:");
        weightLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        weightField = new TextField();
        weightField.setPromptText("Enter weight");
        weightField.setMaxWidth(220);
        weightField.setStyle("""
                -fx-background-radius: 8;
                -fx-padding: 6;
        """);

        weightRow.getChildren().addAll(weightLabel, weightField);
        // --- Height ---
        HBox heightRow = new HBox(10);
        heightRow.setAlignment(Pos.CENTER);
        Label heightLabel = new Label("HEIGHT:");
        heightLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        heightField = new TextField();
        heightField.setPromptText("Enter height");
        heightField.setMaxWidth(220);
        heightField.setStyle("""
                -fx-background-radius: 8;
                -fx-padding: 6;
        """);

        heightRow.getChildren().addAll(heightLabel, heightField);
        // --- Date of birth ---
        HBox dateOfBirthRow = new HBox(10);
        dateOfBirthRow.setAlignment(Pos.CENTER);
        Label dateOfBirthLabel = new Label("DATE OF BIRTH:");
        dateOfBirthLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        dateOfBirthField = new TextField();
        dateOfBirthField.setPromptText("Enter date of birth");
        dateOfBirthField.setMaxWidth(220);
        dateOfBirthField.setStyle("""
                -fx-background-radius: 8;
                -fx-padding: 6;
        """);

        dateOfBirthRow.getChildren().addAll(dateOfBirthLabel, dateOfBirthField);
        // --- Create Account button ---
        createButton = new Button("Create Account");
        createButton.setStyle("""
                -fx-background-color: #1435d1;
                -fx-text-fill: white;
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-padding: 15 20 10 20;
        """);

        root.getChildren().addAll(
                title,
                weightRow,
                heightRow,
                dateOfBirthRow,
                usernameRow,
                emailRow,
                passwordRow,
                createButton
        );
        Scene scene = new Scene(root, 350, 400);
        setScene(scene);
    }

    public TextField getUsernameField() {
        return usernameField;
    }
    public TextField getPasswordField() {
        return passwordField;
    }
    public TextField getEmailField() {
        return emailField;
    }
    public Button getCreateButton() {
        return createButton;
    }
    public TextField getWeightField() {
        return weightField;
    }
    public TextField getHeightField() {
        return heightField;
    }
    public TextField getDateOfBirthField() {
        return dateOfBirthField;
    }
}
