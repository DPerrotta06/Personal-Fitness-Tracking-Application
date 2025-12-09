package org.example.personalfitnesstracker.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView extends Stage {

    private final TextField emailField;
    private final PasswordField passwordField;
    private final Button loginButton;
    private final Hyperlink createAccountLink;

    public LoginView() {
        setTitle("Fitness Tracker - Login");

        // --- Main layout ---
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #000000;");

        // --- App Title ---
        Label title = new Label("FITNESS TRACKER");
        title.setStyle("""
                -fx-font-size: 26px;
                -fx-text-fill: white;
                -fx-font-family: Arial, Helvetica, sans-serif;
                -fx-font-weight: bold;
        """);

        // --- Email Row ---
        HBox emailRow = new HBox(10);
        emailRow.setAlignment(Pos.CENTER);

        Label emailLabel = new Label("EMAIL:");
        emailLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial; -fx-font-weight: bold;");

        emailField = new TextField();
        emailField.setPromptText("Enter email");
        emailField.setMinWidth(190);
        emailField.setMaxWidth(220);
        emailField.setStyle("""
        -fx-background-radius: 10;
        -fx-padding: 8;
        """);

        emailRow.getChildren().addAll(emailLabel, emailField);



        // --- Password Row ---
        HBox passwordRow = new HBox(10);
        passwordRow.setAlignment(Pos.CENTER);

        Label passwordLabel = new Label("PASSWORD:");
        passwordLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial; -fx-font-weight: bold;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setMaxWidth(220);
        passwordField.setStyle("""
        -fx-background-radius: 10;
        -fx-padding: 8;
        """);

        passwordRow.getChildren().addAll(passwordLabel, passwordField);

        // --- Create Account Link ---
        createAccountLink = new Hyperlink("Don't have an account? Create one.");
        createAccountLink.setStyle("-fx-text-fill: #C4EFFF;");
        createAccountLink.setBorder(Border.EMPTY);

        // --- Login Button ---
        loginButton = new Button("Login");
        loginButton.setStyle("""
                -fx-background-color: #1435d1;
                -fx-text-fill: white;
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-padding: 10 20 10 20;
        """);

        // --- Adding everything ---
        root.getChildren().addAll(
                title,
                emailRow,
                passwordRow,
                createAccountLink,
                loginButton
        );

        // --- Scene ---
        Scene scene = new Scene(root, 420, 320);
        setScene(scene);
    }

    // Getters so your controller can use the fields
    public TextField getEmailField() { return emailField; }
    public PasswordField getPasswordField() { return passwordField; }
    public Button getLoginButton() { return loginButton; }
    public Hyperlink getCreateAccountLink() { return createAccountLink; }
}
