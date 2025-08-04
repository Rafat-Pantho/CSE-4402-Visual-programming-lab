package com.example.id_220041102_lab5;

public class Movie {
    private final int id;
    private final String title, genre,cast,duration,summary,poster_url;
    private final double rating;

    public Movie(int id, String title, String genre, String cast, String duration, double rating, String summary, String poster_url) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.cast = cast;
        this.duration = duration;
        this.summary = summary;
        this.rating = rating;
        this.poster_url = poster_url;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public String getGenre() {return genre;}
    public String getCast() {return cast;}
    public String getDuration() {return duration;}
    public String getSummary() {return summary;}
    public double getRating() {return rating;}
    public String getPoster_url() {return poster_url;}

    @Override
    public String toString() {return title;}
}
