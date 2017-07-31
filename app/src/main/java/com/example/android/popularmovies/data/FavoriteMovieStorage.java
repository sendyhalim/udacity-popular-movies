package com.example.android.popularmovies.data;

import android.content.ContentResolver;
import android.database.Cursor;

import com.example.android.popularmovies.Movie;
import com.example.android.popularmovies.data.FavoriteMovieContract.FavoriteMovieEntry;

import java.util.ArrayList;


public class FavoriteMovieStorage {
    final static String[] projection = new String[]{
        FavoriteMovieEntry.COLUMN_API_MOVIE_ID,
        FavoriteMovieEntry.COLUMN_TITLE,
        FavoriteMovieEntry.COLUMN_RATING,
        FavoriteMovieEntry.COLUMN_SYNOPSIS,
        FavoriteMovieEntry.COLUMN_RELEASE_DATE,
        FavoriteMovieEntry.COLUMN_DURATION_IN_MINUTES,
        FavoriteMovieEntry.COLUMN_PREVIEW_RELATIVE_PATH
    };

    public Movie[] fetchFavoriteMovies(ContentResolver contentResolver) {
        ArrayList<Movie> movies = new ArrayList<>();

        Cursor cursor = contentResolver.query(
            FavoriteMovieEntry.CONTENT_URI,
            projection,
            null,
            null,
            null
        );

        if (cursor.moveToFirst()) {
            do {
                movies.add(Movie.from(cursor));
            } while (cursor.moveToNext());
        }

        return movies.toArray(new Movie[movies.size()]);
    }
}
