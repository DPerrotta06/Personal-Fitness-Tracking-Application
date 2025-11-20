module org.example.personalfitnesstracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.personalfitnesstracker to javafx.fxml;
    exports org.example.personalfitnesstracker;
}