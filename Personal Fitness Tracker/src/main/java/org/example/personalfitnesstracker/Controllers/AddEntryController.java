package org.example.personalfitnesstracker.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.Entry;
import org.example.personalfitnesstracker.Models.User;

import java.time.LocalDateTime;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.example.personalfitnesstracker.Threads.AddEntryThread;

public class AddEntryController extends BaseController {

    private Runnable onEntrySaved;
    @FXML
    private ComboBox<String> entryTypeCombo;
    @FXML
    private TextField valueField;
    @FXML
    private TextArea notesField;
    @FXML
    private Button saveButton;

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    public void setOnEntrySaved(Runnable callback) {
        this.onEntrySaved = callback;
    }

    @FXML
    public void initialize() {

        entryTypeCombo.getItems().addAll(
                "Calories",
                "Water",
                "Sleep"
        );

        saveButton.setOnAction(e -> saveEntry());
    }

    private void saveEntry() {

        if (currentUser == null) {
            showMessageWindow("Error", "No user loaded.", AlertType.ERROR, ButtonType.OK);
            return;
        }

        String type = entryTypeCombo.getValue();
        if (type == null) {
            showMessageWindow("Missing Type", "Please select an entry type.", AlertType.ERROR, ButtonType.OK);
            return;
        }

        double value;
        try {
            value = Double.parseDouble(valueField.getText().trim());
        } catch (NumberFormatException ex) {
            showMessageWindow("Invalid value", "Value must be a number.", AlertType.ERROR, ButtonType.OK);
            return;
        }

        String notes = notesField.getText().trim();

        Entry entry = new Entry(
                currentUser.userIdProperty().get(),
                type,
                value,
                notes,
                LocalDateTime.now()
        );

        AddEntryThread addEntryThread =
                new AddEntryThread("Saving entry to database...", entry);

        addEntryThread.thread.start();

        try {
            addEntryThread.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        showMessageWindow("Success", "Entry saved!", AlertType.INFORMATION, ButtonType.OK);

        if (onEntrySaved != null) {
            onEntrySaved.run();
        }

        saveButton.getScene().getWindow().hide();
    }

}

