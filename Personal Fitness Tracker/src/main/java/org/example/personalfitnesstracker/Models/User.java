package org.example.personalfitnesstracker.Models;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class User {
    private final IntegerProperty userId;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty email;
    private final DoubleProperty weight;
    private final DoubleProperty height;
    private final LocalDateTime dateOfBirth;

    public User(int userId, String username, String password, String email, double weight, double height, LocalDateTime dateOfBirth) {
        this.userId = new SimpleIntegerProperty(userId);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.weight = new SimpleDoubleProperty(weight);
        this.height = new SimpleDoubleProperty(height);
        this.dateOfBirth = dateOfBirth;
    }

    public IntegerProperty userIdProperty(){
        return userId;
    }

    public StringProperty usernameProperty(){
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }
}
