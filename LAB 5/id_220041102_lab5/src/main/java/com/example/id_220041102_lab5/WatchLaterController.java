package com.example.id_220041102_lab5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WatchLaterController {
    @FXML private ListView<Movie> watchLaterListView;

    private final ObservableList<Movie> watchLaterList = FXCollections.observableArrayList();

    public void setWatchLaterList(ObservableList<Movie> list) {
        watchLaterList.setAll(list);

        watchLaterListView.setItems(watchLaterList);
        watchLaterListView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Movie movie, boolean empty) {
                super.updateItem(movie, empty);
                if (empty || movie == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(movie.getPoster_url(), 60, 90, true, true));
                    setText(movie.getTitle());
                    setGraphic(imageView);
                }
            }
        });
    }
}
