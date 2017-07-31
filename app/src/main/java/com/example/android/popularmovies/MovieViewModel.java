package com.example.android.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.widget.Toast;

import com.example.android.popularmovies.data.FavoriteMovieContract.FavoriteMovieEntry;

interface MovieViewModelType {
    String previewImage();
    int getId();
    String getTitle();
    String getRating();
    String getReleaseDate();
    String getSynopsis();
    String getDuration();
    Uri markAsFavorite(ContentResolver contentResolver);
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

    public Uri markAsFavorite(ContentResolver contentResolver) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(FavoriteMovieEntry.COLUMN_API_MOVIE_ID, movie.id);
        contentValues.put(FavoriteMovieEntry.COLUMN_TITLE, movie.title);
        contentValues.put(FavoriteMovieEntry.COLUMN_RATING, movie.rating);
        contentValues.put(FavoriteMovieEntry.COLUMN_SYNOPSIS, movie.synopsis);
        contentValues.put(FavoriteMovieEntry.COLUMN_RELEASE_DATE, movie.releaseDate);
        contentValues.put(FavoriteMovieEntry.COLUMN_PREVIEW_RELATIVE_PATH, movie.previewRelativePath);
        contentValues.put(FavoriteMovieEntry.COLUMN_DURATION_IN_MINUTES, movie.durationInMinutes);

        return contentResolver.insert(FavoriteMovieEntry.CONTENT_URI, contentValues);
    }
}
