package com.example.id_220041102_lab7.controller;

import com.example.id_220041102_lab7.service.WeatherApiClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.example.id_220041102_lab7.service.WeatherApiClient.*;

public class MainController {

    @FXML private TextField cityField;
    @FXML private Button searchBtn;
    @FXML private ToggleButton themeToggle;

    @FXML private Label cityNameLabel;
    @FXML private Label localTimeLabel;
    @FXML private Label tempLabel;
    @FXML private Label feelsLabel;
    @FXML private Label humidityLabel;
    @FXML private Label cloudsLabel;
    @FXML private Label precipLabel;
    @FXML private Label aqiLabel;
    @FXML private ImageView currentIcon;

    @FXML private HBox forecastBox;

    @FXML private TableView<HistoryRow> historyTable;
    @FXML private TableColumn<HistoryRow, String> colDate;
    @FXML private TableColumn<HistoryRow, String> colCond;
    @FXML private TableColumn<HistoryRow, String> colMin;
    @FXML private TableColumn<HistoryRow, String> colMax;
    @FXML private TableColumn<HistoryRow, String> colHum;
    @FXML private TableColumn<HistoryRow, String> colPrecip;

    @FXML private StackPane rootStack;
    @FXML private ProgressIndicator loadingSpinner;
    @FXML private Label errorLabel;

    private final WeatherApiClient api = new WeatherApiClient();

    @FXML
    public void initialize() {
        colDate.setCellValueFactory(data -> data.getValue().dateProperty());
        colCond.setCellValueFactory(data -> data.getValue().condProperty());
        colMin.setCellValueFactory(data -> data.getValue().minProperty());
        colMax.setCellValueFactory(data -> data.getValue().maxProperty());
        colHum.setCellValueFactory(data -> data.getValue().humProperty());
        colPrecip.setCellValueFactory(data -> data.getValue().precipProperty());

        loading(false);
        showError(null);

        themeToggle.selectedProperty().addListener((obs, oldVal, dark) -> applyTheme(dark));

        cityField.setText("Bangladesh");
        search();
    }

    @FXML
    private void onSearchClicked() {
        search();
    }

    private void search() {
        String city = cityField.getText() == null ? "" : cityField.getText().trim();
        if (city.isEmpty()) {
            showError("Please type a city name.");
            return;
        }
        showError(null);
        loading(true);

        CompletableFuture
                .supplyAsync(() -> {
                    try {
                        JsonObject currentForecast = api.fetchCurrentAndForecast(city);
                        List<JsonObject> history7 = api.fetch7DayHistory(city);
                        return new Object[]{currentForecast, history7};
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .whenComplete((result, throwable) -> Platform.runLater(() -> {
                    loading(false);
                    if (throwable != null) {
                        showError(prettifyError(throwable.getMessage()));
                        return;
                    }
                    Object[] arr = (Object[]) result;
                    JsonObject currentForecast = (JsonObject) arr[0];
                    @SuppressWarnings("unchecked")
                    List<JsonObject> history7 = (List<JsonObject>) arr[1];

                    try {
                        renderCurrent(currentForecast);
                        renderForecast(currentForecast);
                        renderHistory(history7);
                    } catch (Exception ex) {
                        showError(prettifyError(ex.getMessage()));
                    }
                }));
    }

    private void renderCurrent(JsonObject json) {
        String cityName = optString(json, "location", "name");
        String region = optString(json, "location", "region");
        String country = optString(json, "location", "country");
        String localtime = optString(json, "location", "localtime");

        double tempC = optDouble(json, "current", "temp_c");
        double feelsC = optDouble(json, "current", "feelslike_c");
        int humidity = optInt(json, "current", "humidity");
        int cloud = optInt(json, "current", "cloud");
        double precipMm = optDouble(json, "current", "precip_mm");

        String condText = optString(json, "current", "condition", "text");
        String iconUrl = toHttpsIcon(optString(json, "current", "condition", "icon"));

        int epaIndex = optInt(json, "current", "air_quality", "us-epa-index");
        String aqiText = epaIndex == 0 ? "N/A" : (epaIndex + " — " + epaLabel(epaIndex));

        cityNameLabel.setText("%s, %s (%s)".formatted(cityName, region, country));
        localTimeLabel.setText("Local time: " + localtime);
        tempLabel.setText(String.format("%.1f °C — %s", tempC, condText));
        feelsLabel.setText(String.format("Feels like: %.1f °C", feelsC));
        humidityLabel.setText("Humidity: " + humidity + " %");
        cloudsLabel.setText("Clouds: " + cloud + " %");
        precipLabel.setText(String.format("Precipitation: %.1f mm", precipMm));
        aqiLabel.setText("AQI (US EPA): " + aqiText);

        if (!iconUrl.isBlank()) {
            currentIcon.setImage(new Image(iconUrl, true));
        } else {
            currentIcon.setImage(null);
        }
    }

    private void renderForecast(JsonObject json) {
        forecastBox.getChildren().clear();
        JsonArray days = json.getAsJsonObject("forecast").getAsJsonArray("forecastday");
        for (int i = 0; i < Math.min(3, days.size()); i++) {
            JsonObject d = days.get(i).getAsJsonObject();
            String date = optString(d, "date");
            JsonObject day = d.getAsJsonObject("day");

            double min = optDouble(day, "mintemp_c");
            double max = optDouble(day, "maxtemp_c");
            String cond = optString(day, "condition", "text");
            String icon = toHttpsIcon(optString(day, "condition", "icon"));

            VBox card = makeForecastCard(date, cond, min, max, icon);
            forecastBox.getChildren().add(card);
        }
    }

    private VBox makeForecastCard(String date, String cond, double min, double max, String icon) {
        VBox v = new VBox(6);
        v.getStyleClass().add("card");

        Label dateL = new Label(date);
        dateL.getStyleClass().add("h4");

        ImageView iv = new ImageView();
        iv.setFitWidth(56);
        iv.setFitHeight(56);
        if (!icon.isBlank()) {
            iv.setImage(new Image(icon, true));
        }

        Label condL = new Label(cond);
        Label tL = new Label(String.format("Min %.1f° / Max %.1f°", min, max));

        v.getChildren().addAll(dateL, iv, condL, tL);
        return v;
    }

    private void renderHistory(List<JsonObject> history) {
        historyTable.getItems().clear();
        for (JsonObject dayJson : history) {
            JsonArray fdays = dayJson.getAsJsonObject("forecast").getAsJsonArray("forecastday");
            if (fdays.size() == 0) continue;
            JsonObject fd = fdays.get(0).getAsJsonObject();
            String date = optString(fd, "date");
            JsonObject day = fd.getAsJsonObject("day");

            String cond = optString(day, "condition", "text");
            double min = optDouble(day, "mintemp_c");
            double max = optDouble(day, "maxtemp_c");
            double hum = optDouble(day, "avghumidity");
            double precip = optDouble(day, "totalprecip_mm");

            historyTable.getItems().add(new HistoryRow(
                    date,
                    cond,
                    String.format("%.1f °C", min),
                    String.format("%.1f °C", max),
                    String.format("%.0f %%", hum),
                    String.format("%.1f mm", precip)
            ));
        }
    }

    private static String epaLabel(int idx) {
        return switch (idx) {
            case 1 -> "Good";
            case 2 -> "Moderate";
            case 3 -> "Unhealthy for Sensitive Groups";
            case 4 -> "Unhealthy";
            case 5 -> "Very Unhealthy";
            case 6 -> "Hazardous";
            default -> "Unknown";
        };
    }

    private void loading(boolean on) {
        loadingSpinner.setVisible(on);
        loadingSpinner.setManaged(on);
        setDisabled(rootStack, on);
    }

    private void setDisabled(Pane container, boolean disabled) {
        for (Node n : container.getChildren()) {
            if (n != loadingSpinner) n.setDisable(disabled);
        }
    }

    private void showError(String msg) {
        boolean show = msg != null && !msg.isBlank();
        errorLabel.setText(show ? msg : "");
        errorLabel.setManaged(show);
        errorLabel.setVisible(show);
    }

    private String prettifyError(String raw) {
        if (raw == null) return "Something went wrong.";
        if (raw.contains("No matching location found")) return "City not found. Try a different name.";
        if (raw.contains("API key")) return "API key error. Double-check your key in code.";
        if (raw.contains("HTTP 4")) return "Request error. Please verify your city name.";
        if (raw.contains("HTTP 5")) return "Weather service is busy. Try again.";
        return raw;
    }

    private void applyTheme(boolean dark) {
        var sheets = rootStack.getScene().getStylesheets();
        sheets.clear();
        String base = getClass().getResource("/com/example/id_220041102_lab7/styles/light.css").toExternalForm();
        String darkCss = getClass().getResource("/com/example/id_220041102_lab7/styles/dark.css").toExternalForm();
        sheets.add(dark ? darkCss : base);
    }

    public static class HistoryRow {
        private final javafx.beans.property.SimpleStringProperty date = new javafx.beans.property.SimpleStringProperty();
        private final javafx.beans.property.SimpleStringProperty cond = new javafx.beans.property.SimpleStringProperty();
        private final javafx.beans.property.SimpleStringProperty min = new javafx.beans.property.SimpleStringProperty();
        private final javafx.beans.property.SimpleStringProperty max = new javafx.beans.property.SimpleStringProperty();
        private final javafx.beans.property.SimpleStringProperty hum = new javafx.beans.property.SimpleStringProperty();
        private final javafx.beans.property.SimpleStringProperty precip = new javafx.beans.property.SimpleStringProperty();

        public HistoryRow(String date, String cond, String min, String max, String hum, String precip) {
            this.date.set(date);
            this.cond.set(cond);
            this.min.set(min);
            this.max.set(max);
            this.hum.set(hum);
            this.precip.set(precip);
        }

        public javafx.beans.property.StringProperty dateProperty() { return date; }
        public javafx.beans.property.StringProperty condProperty() { return cond; }
        public javafx.beans.property.StringProperty minProperty() { return min; }
        public javafx.beans.property.StringProperty maxProperty() { return max; }
        public javafx.beans.property.StringProperty humProperty() { return hum; }
        public javafx.beans.property.StringProperty precipProperty() { return precip; }
    }
}
