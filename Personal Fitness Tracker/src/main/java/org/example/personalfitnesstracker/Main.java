package org.example.personalfitnesstracker;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.personalfitnesstracker.Controllers.LoginController;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Views.LoginView;
import org.example.personalfitnesstracker.Views.MainPageView;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
        //DatabaseManager.testConnection(); //FOR CONNECTION TESTING PURPOSES DELETE LATER!!!
    }

    @Override
    public void start(Stage stage) throws Exception {
//        LoginView loginView = new LoginView();
//        LoginController loginController = new LoginController(loginView);
//        loginController.show();
        MainPageView mainPageView = new MainPageView();
        mainPageView.show();
    }
}
