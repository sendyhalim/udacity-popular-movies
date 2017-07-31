package com.example.android.popularmovies.models;

public interface MovieViewModelType {
    String previewImage();
    int getId();
    String getTitle();
    String getRating();
    String getReleaseDate();
    String getSynopsis();
    String getDuration();
    Movie getMovie();
}

