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

public class AddEntryController extends BaseController {

    private Runnable onEntrySaved;
    @FXML private ComboBox<String> entryTypeCombo;
    @FXML private TextField valueField;
    @FXML private TextArea notesField;
    @FXML private Button saveButton;

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    public void setOnEntrySaved(Runnable callback) {
        this.onEntrySaved = callback;
    }


    @FXML
    public void initialize() {

        // Pre-load valid entry types
        entryTypeCombo.getItems().addAll(
                "Calories",
                "Water",
                "Sleep"
        );

        saveButton.setOnAction(e -> saveEntry());
    }

    private void saveEntry() {

        if (currentUser == null) {
            showError("Error", "No user loaded.");
            return;
        }

        String type = entryTypeCombo.getValue();
        if (type == null) {
            showError("Missing Type", "Please select an entry type.");
            return;
        }

        double value;
        try {
            value = Double.parseDouble(valueField.getText().trim());
        } catch (NumberFormatException ex) {
            showError("Invalid value", "Value must be a number.");
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

        DatabaseManager.addEntry(entry);

        showInfo("Success", "Entry saved!");

        if (onEntrySaved != null) {
            onEntrySaved.run();
        }

        // Close window
        saveButton.getScene().getWindow().hide();
    }
}
