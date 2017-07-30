package com.example.android.popularmovies;

interface MovieViewModelType {
    String previewImage();
    int getId();
    String getTitle();
    String getRating();
    String getReleaseDate();
    String getSynopsis();
    String getDuration();
}

public class MovieViewModel implements  MovieViewModelType {
    private Movie movie;

    MovieViewModel(Movie movie) {
        this.movie = movie;
    }

    public String previewImage() {
        return "http://image.tmdb.org/t/p/w185" + movie.previewRelativePath;
    }

    public int getId() {
        return movie.id;
    }

    public String getTitle() {
        return movie.title;
    }

    public String getRating() {
        return Double.toString(movie.rating) + "/10";
    }

    public String getReleaseDate() {
        return movie.releaseDate;
    }

    public String getSynopsis() {
        return movie.synopsis;
    }

    public String getDuration() {
        return Integer.toString(movie.durationInMinutes) + " minutes";
    }
}
