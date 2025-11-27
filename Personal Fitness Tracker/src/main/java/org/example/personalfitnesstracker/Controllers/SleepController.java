package org.example.personalfitnesstracker.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.Models.Sleep;

import java.time.LocalDateTime;

/**
 * Controller responsible for creating and managing Sleep sessions.
 */
public class SleepController extends BaseController {

    private final ObservableList<Sleep> sleepSessions;

    public SleepController() {
        this.sleepSessions = FXCollections.observableArrayList();
    }

    public ObservableList<Sleep> getSleepSessions() {
        return sleepSessions;
    }

    public Sleep addSleepSession(int sleepSessionId,
            LocalDateTime sleepStart,
            LocalDateTime sleepEnd,
            int userId) {

        if (sleepStart == null || sleepEnd == null || sleepEnd.isBefore(sleepStart)) {
            log("Cannot add sleep session: invalid dates.");
            return null;
        }

        Sleep sleep = new Sleep(sleepSessionId, sleepStart, sleepEnd, userId);
        sleepSessions.add(sleep);
        log("Sleep session added for user " + userId);
        return sleep;
    }
}
