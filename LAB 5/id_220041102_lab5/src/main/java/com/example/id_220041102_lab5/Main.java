package com.example.id_220041102_lab5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Scene scene = new Scene(loader.load(), 600, 800);
        stage.setTitle("Movie Browser");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(800);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
