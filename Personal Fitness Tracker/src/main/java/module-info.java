module org.example.personalfitnesstracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.logging;
    requires javafx.graphics;
    requires javafx.base;

    // Allow FXML to access controllers
    opens org.example.personalfitnesstracker.Controllers to javafx.fxml;

    // Allow FXML to access view classes
    opens org.example.personalfitnesstracker.Views to javafx.fxml;

    // Optional but recommended
    exports org.example.personalfitnesstracker;
    exports org.example.personalfitnesstracker.Controllers;
    exports org.example.personalfitnesstracker.Views;
}
