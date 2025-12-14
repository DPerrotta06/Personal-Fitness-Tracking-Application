package org.example.personalfitnesstracker.Threads;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;

public class LoginThread implements Runnable {

    public Thread thread;

    private final String message;
    private final String email;
    private final byte[] password;

    public User user;

    public LoginThread(String message, String email, byte[] password) {
        this.message = message;
        this.email = email;
        this.password = password;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        System.out.println(message);
        user = DatabaseManager.getUserByEmailAndPw(email, password);
    }
}
