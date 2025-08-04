package com.example.id_220041102_lab5;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class MovieDetailsController {
    @FXML private Label titleLabel;
    @FXML private Label genreLabel;
    @FXML private Label castLabel;
    @FXML private Label durationLabel;
    @FXML private Label ratingLabel;
    @FXML private Label summaryLabel;
    @FXML private ImageView posterImageView;
    @FXML private Button watchLaterButton;

    private Movie movie;
    private boolean isInWatchLater;
    private List<Movie> watchLaterList;

    public void setMovie(Movie movie, boolean isInWatchLater, List<Movie> watchLaterList) {
        this.movie = movie;
        this.isInWatchLater = isInWatchLater;
        this.watchLaterList = watchLaterList;

        titleLabel.setText(movie.getTitle());
        genreLabel.setText("Genre: " + movie.getGenre());
        castLabel.setText("Cast: " + movie.getCast());
        durationLabel.setText("Duration: " + movie.getDuration());
        ratingLabel.setText("Rating: " + movie.getRating());
        summaryLabel.setText("Summary: " + movie.getSummary());

        try {
            posterImageView.setImage(new Image(movie.getPoster_url(), true));
        } catch (Exception e) {
            // handle image error
        }

        updateButton();
    }

    public void toggleWatchLater() {
        if (isInWatchLater) {
            watchLaterList.remove(movie);
        } else {
            watchLaterList.add(movie);
        }
        isInWatchLater = !isInWatchLater;
        updateButton();
    }

    private void updateButton() {
        watchLaterButton.setText(isInWatchLater ? "Remove from Watch Later" : "Add to Watch Later");
    }
}
