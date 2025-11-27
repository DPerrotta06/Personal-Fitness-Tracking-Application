package org.example.personalfitnesstracker.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateAccountView extends Stage {
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField emailField;
    private final Button createButton;
    public CreateAccountView() {
        setTitle("Create Account");
        // --- Main Layout ---
        VBox root = new VBox();
        root.setSpacing(12);
        root.setAlignment(Pos.CENTER);
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
        Label usernameLabel = new Label("USERNAME:");
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setMaxWidth(220);
        usernameField.setStyle("""
                -fx-background-radius: 10;
                -fx-padding: 8;
        """);

        // --- Email ---
        Label emailLabel = new Label("EMAIL:");
        emailLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        emailField = new TextField();
        emailField.setPromptText("Enter email");
        emailField.setMaxWidth(230);
        emailField.setStyle("""
                -fx-background-radius: 10;
                -fx-padding: 8;
                """);

        // --- Password ---
        Label passwordLabel = new Label("PASSWORD:");
        passwordLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setMaxWidth(230);
        passwordField.setStyle("""
                -fx-background-radius: 10;
                -fx-padding: 8;
                """);

        // --- Create Account button ---
        createButton = new Button("Create Account");
        createButton.setStyle("""
                -fx-background-color: #1435d1;
                -fx-text-fill: white;
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-padding: 10 20 10 20;
        """);

        root.getChildren().addAll(
                title,
                usernameLabel,
                usernameField,
                emailLabel,
                emailField,
                passwordLabel,
                passwordField,
                createButton
        );
        Scene scene = new Scene(root, 420, 350);
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
}
