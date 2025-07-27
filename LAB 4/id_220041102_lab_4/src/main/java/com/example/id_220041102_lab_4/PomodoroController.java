package com.example.id_220041102_lab_4;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

public class PomodoroController {
    @FXML private Label timerLabel, sessionLabel;
    @FXML private TextField workField, breakField;
    @FXML private ProgressBar progressBar;

    private Timeline timeline;
    private int totalSeconds, secondsLeft;
    private boolean isWorkSession = true;
    private int sessionCount =0;

    public void startTimer(){
        int workMinutes = Integer.parseInt(workField.getText());
        int breakMinutes = Integer.parseInt(breakField.getText());

        if(timeline!=null)timeline.stop();

        if(secondsLeft<=0){
            if(isWorkSession) totalSeconds = workMinutes*60;
            else {
                if(sessionCount==4){
                    totalSeconds = 20*60;
                    sessionCount=0;
                }
                else totalSeconds = breakMinutes * 60;
            }
            secondsLeft = totalSeconds;
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e ->{
            secondsLeft--;
            updateUI();

            if (secondsLeft <=0){
                timeline.stop();
                isWorkSession= !isWorkSession;
                if(isWorkSession) sessionCount++;
                startTimer();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void pauseTimer(){
        if(timeline != null) timeline.pause();
    }

    public void resetTimer(){
        if(timeline !=null) timeline.stop();
        secondsLeft =0;
        updateUI();
    }

    private void updateUI(){
        int minutes = secondsLeft/60;
        int seconds = secondsLeft%60;
        timerLabel.setText(String.format("%02d:%02d",minutes,seconds));
        progressBar.setProgress(1.0*(totalSeconds-secondsLeft)/totalSeconds);
        sessionLabel.setText("Session: "+ (isWorkSession? "Work":(sessionCount==0? "Long Break": "Break")));
    }
}