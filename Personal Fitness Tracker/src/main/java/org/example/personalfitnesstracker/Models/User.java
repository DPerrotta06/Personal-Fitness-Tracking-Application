package org.example.personalfitnesstracker.Models;

import java.sql.Date;
import javafx.beans.property.*;

public class User {

    private final IntegerProperty userId;
    private final StringProperty username;
    private final ObjectProperty<byte[]> password;
    private final StringProperty email;
    private final DoubleProperty weight;
    private final DoubleProperty height;
    private final Date dateOfBirth;

    public User(int userId, String username, byte[] password, String email, double weight, double height, Date dateOfBirth) {
        this.userId = new SimpleIntegerProperty(userId);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleObjectProperty(password);
        this.email = new SimpleStringProperty(email);
        this.weight = new SimpleDoubleProperty(weight);
        this.height = new SimpleDoubleProperty(height);
        this.dateOfBirth = dateOfBirth;
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public ObjectProperty<byte[]> passwordProperty() {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}
