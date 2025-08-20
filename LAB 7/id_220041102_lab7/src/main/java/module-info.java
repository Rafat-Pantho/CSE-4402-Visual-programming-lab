module com.example.id_220041102_lab7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    opens com.example.id_220041102_lab7 to javafx.fxml;
    opens com.example.id_220041102_lab7.controller to javafx.fxml, com.google.gson;

    exports com.example.id_220041102_lab7;
    exports com.example.id_220041102_lab7.controller;
}
