package org.example.personalfitnesstracker.Threads;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.Entry;

public class AddEntryThread implements Runnable {

    public Thread thread;

    private final String message;
    private final Entry entry;

    public boolean success = false;

    public AddEntryThread(String message, Entry entry) {
        this.message = message;
        this.entry = entry;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        System.out.println(message);
        DatabaseManager.addEntry(entry);
        success = true;
    }
}
