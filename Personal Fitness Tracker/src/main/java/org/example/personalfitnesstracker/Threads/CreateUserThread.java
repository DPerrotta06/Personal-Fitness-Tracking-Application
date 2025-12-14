package org.example.personalfitnesstracker.Threads;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;

public class CreateUserThread implements Runnable {

    public Thread thread;

    private final String message;
    private final User user;

    public boolean userAlreadyExists = false;
    public User createdUser = null;

    public CreateUserThread(String message, User user) {
        this.message = message;
        this.user = user;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        System.out.println(message);

        if (DatabaseManager.userExists(
                user.emailProperty().get(),
                user.passwordProperty().get()
        )) {
            userAlreadyExists = true;
            return;
        }

        DatabaseManager.addNewUserToDb(user);
        createdUser = user;
    }
}
