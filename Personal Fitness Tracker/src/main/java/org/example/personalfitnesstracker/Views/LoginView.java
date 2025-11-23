package org.example.personalfitnesstracker.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView extends Stage {

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Hyperlink createAccountLink;

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

        // --- Username ---
        Label usernameLabel = new Label("USERNAME:");
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setMaxWidth(220);
        usernameField.setStyle("""
                -fx-background-radius: 10;
                -fx-padding: 8;
        """);

        // --- Password ---
        Label passwordLabel = new Label("PASSWORD:");
        passwordLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setMaxWidth(220);
        passwordField.setStyle("""
                -fx-background-radius: 10;
                -fx-padding: 8;
        """);

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
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                createAccountLink,
                loginButton
        );

        // --- Scene ---
        Scene scene = new Scene(root, 420, 320);
        setScene(scene);
    }

    // Getters so your controller can use the fields
    public TextField getUsernameField() { return usernameField; }
    public PasswordField getPasswordField() { return passwordField; }
    public Button getLoginButton() { return loginButton; }
    public Hyperlink getCreateAccountLink() { return createAccountLink; }
}
