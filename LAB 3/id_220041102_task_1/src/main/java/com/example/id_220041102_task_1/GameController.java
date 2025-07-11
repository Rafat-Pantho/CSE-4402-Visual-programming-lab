package com.example.id_220041102_task_1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.*;
public class GameController {
    @FXML private HBox letterBox;
    @FXML private TextField guessField;

    private final List<String> words = Arrays.asList("apple","orange","rafat");
    private String selectWord;
    private int attempts =0;

    @FXML public void initialize(){
        startNewGame();
    }
    private void startNewGame(){
        guessField.setText("");
        letterBox.getChildren().clear();
        selectWord = words.get(new Random().nextInt(words.size()));

        List<Character> scrambled = new ArrayList<>();
        for(char c: selectWord.toCharArray())scrambled.add(c);

        Collections.shuffle(scrambled);

        for(char c: scrambled){
            Button btn = new Button(String.valueOf(c));
            btn.setOnAction(e ->{
                guessField.setText(guessField.getText()+btn.getText());
            });
            letterBox.getChildren().add(btn);
        }
    }

    @FXML public void handleSubmit() throws Exception{
        String guess = guessField.getText();
        if (guess.equalsIgnoreCase(selectWord))showScene("congratulations.fxml");
        else{
            attempts++;
            if(attempts==3)showScene("tryagain.fxml");
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Wrong! Try again",ButtonType.OK);
                alert.showAndWait();
                resetCurrentGuess();
            }
        }
    }

    private void resetCurrentGuess(){
        guessField.setText("");
        for(javafx.scene.Node node: letterBox.getChildren()) if(node instanceof Button)node.setDisable(false);
    }

    private void showScene(String filename) throws Exception{
        Stage stage = (Stage) guessField.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/"+filename)));
        stage.setScene(scene);
    }
}
