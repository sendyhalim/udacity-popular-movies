package com.example.android.popularmovies;

import com.google.gson.annotations.SerializedName;

class MovieCollectionResponse {
    public final int page;

    @SerializedName("results")
    public final Movie[] movies;

    public MovieCollectionResponse(int page, Movie[] movies) {
        this.page = page;
        this.movies = movies;
    }
}

class Movie {
    public final int id;

    @SerializedName("original_title")
    public final String title;

    @SerializedName("vote_average")
    public final double rating;

    @SerializedName("overview")
    public final String synopsis;

    @SerializedName("release_date")
    public final String releaseDate;

    @SerializedName("poster_path")
    public final String previewRelativePath;

    @SerializedName("runtime")
    public final int durationInMinutes;

    public Movie(
        int id,
        String title,
        double rating,
        String synopsis,
        String releaseDate,
        String previewRelativePath,
        int durationInMinutes
    ) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.previewRelativePath = previewRelativePath;
        this.durationInMinutes = durationInMinutes;
    }
}
