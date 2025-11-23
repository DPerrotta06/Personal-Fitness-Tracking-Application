module org.example.personalfitnesstracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;

    opens org.example.personalfitnesstracker to javafx.fxml;
    exports org.example.personalfitnesstracker;
}