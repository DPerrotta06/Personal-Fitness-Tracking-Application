package org.example.personalfitnesstracker.Models;

import java.time.LocalDateTime;

public class Entry {

    private int entryId;
    private int userId;
    private String entryType;
    private Double value;
    private String notes;
    private LocalDateTime entryTimestamp;

    public Entry(int entryId, int userId, String entryType,
                 Double value, String notes, LocalDateTime timestamp) {

        this.entryId = entryId;
        this.userId = userId;
        this.entryType = entryType;
        this.value = value;
        this.notes = notes;
        this.entryTimestamp = timestamp;
    }

    public Entry(int userId, String entryType,
                 Double value, String notes, LocalDateTime timestamp) {

        this(-1, userId, entryType, value, notes, timestamp);
    }

    public int getEntryId() { return entryId; }
    public int getUserId() { return userId; }
    public String getEntryType() { return entryType; }
    public Double getValue() { return value; }
    public String getNotes() { return notes; }
    public LocalDateTime getEntryTimestamp() { return entryTimestamp; }
}
