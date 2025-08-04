package com.example.id_220041102_lab5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:movies_db.db";

    public static List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM movies";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("cast"),
                        rs.getString("duration"),
                        rs.getDouble("rating"),
                        rs.getString("summary"),
                        rs.getString("poster_url")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
