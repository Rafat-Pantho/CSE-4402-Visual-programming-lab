package com.example.id_220041102_lab5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class HomeController {

    @FXML private TextField searchField;
    @FXML private ComboBox<String> genreCombo;
    @FXML private ListView<Movie> movieListView;

    private List<Movie> allMovies;
    private final ObservableList<Movie> watchLaterList = FXCollections.observableArrayList();

    public void initialize() {
        allMovies = DatabaseManager.getAllMovies();
        genreCombo.getItems().add("All");
        genreCombo.getItems().addAll(allMovies.stream()
                .map(Movie::getGenre)
                .distinct()
                .sorted()
                .toList()
        );
        genreCombo.setValue("All");

        setupListViewCellFactory();
        updateMovieList();
    }

    private void setupListViewCellFactory() {
        movieListView.setCellFactory(list -> new ListCell<>() {
            private final ImageView posterView = new ImageView();
            private final Label titleLabel = new Label();
            private final VBox vbox = new VBox(titleLabel);
            private final HBox content = new HBox(posterView, vbox);

            {
                posterView.setFitHeight(120);
                posterView.setFitWidth(80);
                posterView.setPreserveRatio(true);
                content.setSpacing(10);
            }

            @Override
            protected void updateItem(Movie movie, boolean empty) {
                super.updateItem(movie, empty);
                if (empty || movie == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    titleLabel.setText(movie.getTitle());

                    try {
                        Image img = new Image(movie.getPoster_url(), true);
                        posterView.setImage(img);
                    } catch (Exception e) {
                        posterView.setImage(null);
                    }

                    setGraphic(content);
                }
            }
        });
    }

    public void updateMovieList() {
        String search = searchField.getText().toLowerCase().trim();
        String genre = genreCombo.getValue();

        List<Movie> filtered = allMovies.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(search))
                .filter(m -> genre.equals("All") || m.getGenre().equals(genre))
                .collect(Collectors.toList());

        movieListView.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    public void handleMovieClick(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {  // Only respond to double-clicks
            Movie selected = movieListView.getSelectionModel().getSelectedItem();
            if (selected == null) return;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("movie_details.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            MovieDetailsController controller = loader.getController();
            controller.setMovie(selected, watchLaterList.contains(selected), watchLaterList);
            stage.setTitle(selected.getTitle());
            stage.show();
        }
    }


    @FXML
    public void openWatchLater() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("watch_later.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        WatchLaterController controller = loader.getController();
        controller.setWatchLaterList(watchLaterList);
        stage.setTitle("Watch Later List");
        stage.show();
    }

    @FXML
    public void handleSearch() {
        updateMovieList();
    }
}
