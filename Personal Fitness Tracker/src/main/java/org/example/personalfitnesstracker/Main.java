package org.example.personalfitnesstracker;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.personalfitnesstracker.Views.LoginView;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginView loginView = new LoginView();
        loginView.show();
    }
}
