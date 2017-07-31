package com.example.android.popularmovies.models;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import static com.example.android.popularmovies.data.FavoriteMovieContract.*;

public class Movie {
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

    static public Movie from(Cursor cursor) {
        return new Movie(
                cursor.getInt(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_MOVIE_API_ID)),
                cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_TITLE)),
                cursor.getDouble(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_RATING)),
                cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_SYNOPSIS)),
                cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_RELEASE_DATE)),
                cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_PREVIEW_RELATIVE_PATH)),
                cursor.getInt(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_DURATION_IN_MINUTES))
        );
    }

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
