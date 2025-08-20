package com.example.id_220041102_lab7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/id_220041102_lab7/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1040, 720);
        stage.setTitle("Noobs Weather app");
        stage.setScene(scene);
        stage.show();
    }
}
