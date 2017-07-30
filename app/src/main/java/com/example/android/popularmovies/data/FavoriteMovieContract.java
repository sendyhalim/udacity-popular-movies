package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteMovieContract {
    public static final String AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITE_MOVIES = "favorite-movies";

    public static final class FavoriteMovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
            .buildUpon()
            .appendPath(PATH_FAVORITE_MOVIES)
            .build();

        public static final String TABLE_NAME = "favorite_movies";

        // Movie id for moviedb API
        public static final String COLUMN_API_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
    }
}
