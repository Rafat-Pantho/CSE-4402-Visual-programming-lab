package com.example.id_220041102_task_1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CongratulationsController {

    @FXML
    public void playAgain() throws Exception {
        // Load the main game scene again
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml")); // Adjust path if needed
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
        stage.setScene(scene);
    }
}
