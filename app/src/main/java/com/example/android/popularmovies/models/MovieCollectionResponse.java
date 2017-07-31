package com.example.android.popularmovies.models;

import com.google.gson.annotations.SerializedName;

public class MovieCollectionResponse {
    public final int page;

    @SerializedName("results")
    public final Movie[] movies;

    public MovieCollectionResponse(int page, Movie[] movies) {
        this.page = page;
        this.movies = movies;
    }
}
