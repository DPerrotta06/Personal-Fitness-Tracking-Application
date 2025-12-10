package org.example.personalfitnesstracker.Controllers;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.Models.Sleep;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.User;

/**
 * Controller responsible for creating and managing Sleep sessions.
 */
public class SleepController extends BaseController {

    private final ObservableList<Sleep> sleepSessions;

    public SleepController(User logggedUser) {
        super(logggedUser);
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
            return null;
        }

        Sleep sleep = new Sleep(sleepSessionId, sleepStart, sleepEnd, userId);
        sleepSessions.add(sleep);
        return sleep;
    }

    /**
     * Get a sleep session by the date
     *
     * @param date
     * @return
     */
    public ObservableList<Sleep> filterDateByRange(LocalDate start, LocalDate end) {
        ObservableList<Sleep> sleep = DatabaseManager.displaySleepingLogs(loggedUser.userIdProperty().get());
        List<Sleep> filtered = sleep.parallelStream().filter(sleeps -> {
            LocalDate sleepDate = sleeps.sleepStartProperty().toLocalDate();
            return !sleepDate.isBefore(start) && !sleepDate.isAfter(end);
        }
        ).collect(Collectors.toList());
        return FXCollections.observableArrayList(filtered);
    }
}
