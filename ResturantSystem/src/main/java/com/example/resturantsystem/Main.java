package com.example.resturantsystem;

import com.example.resturantsystem.Misc.DBHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DBHandler.getDBConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/signIN.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Restaurant System!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}