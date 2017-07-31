package com.example.android.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.popularmovies.Movie;
import com.example.android.popularmovies.data.FavoriteMovieContract.FavoriteMovieEntry;

import java.util.ArrayList;


public class FavoriteMovieStorage {
    final static String[] projection = new String[]{
        FavoriteMovieEntry.COLUMN_MOVIE_API_ID,
        FavoriteMovieEntry.COLUMN_TITLE,
        FavoriteMovieEntry.COLUMN_RATING,
        FavoriteMovieEntry.COLUMN_SYNOPSIS,
        FavoriteMovieEntry.COLUMN_RELEASE_DATE,
        FavoriteMovieEntry.COLUMN_DURATION_IN_MINUTES,
        FavoriteMovieEntry.COLUMN_PREVIEW_RELATIVE_PATH
    };

    private ContentResolver contentResolver;

    public FavoriteMovieStorage(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public Movie[] fetchFavoriteMovies() {
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

        cursor.close();

        return movies.toArray(new Movie[movies.size()]);
    }

    public Boolean movieExists(int movieApiId) {
        String whereQuery = FavoriteMovieEntry.COLUMN_MOVIE_API_ID +
            "='" + Integer.toString(movieApiId) + "'";

        Cursor cursor = contentResolver.query(
            FavoriteMovieEntry.CONTENT_URI,
            null,
            whereQuery,
            null,
            null
        );

        Boolean exists = cursor.getCount() == 1;
        cursor.close();

        return exists;
    }

    public Uri markAsFavorite(Movie movie) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(FavoriteMovieEntry.COLUMN_MOVIE_API_ID, movie.id);
        contentValues.put(FavoriteMovieEntry.COLUMN_TITLE, movie.title);
        contentValues.put(FavoriteMovieEntry.COLUMN_RATING, movie.rating);
        contentValues.put(FavoriteMovieEntry.COLUMN_SYNOPSIS, movie.synopsis);
        contentValues.put(FavoriteMovieEntry.COLUMN_RELEASE_DATE, movie.releaseDate);
        contentValues.put(FavoriteMovieEntry.COLUMN_PREVIEW_RELATIVE_PATH, movie.previewRelativePath);
        contentValues.put(FavoriteMovieEntry.COLUMN_DURATION_IN_MINUTES, movie.durationInMinutes);

        return contentResolver.insert(FavoriteMovieEntry.CONTENT_URI, contentValues);
    }

    public Boolean removeFromFavorite(int movieApiId) {
        Uri deleteUri = FavoriteMovieEntry.CONTENT_URI
            .buildUpon()
            .appendPath(Integer.toString(movieApiId))
            .build();

        return contentResolver.delete(deleteUri, null, null) == 1;
    }
}

