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
        public static final String COLUMN_MOVIE_API_ID = "movie_api_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_DURATION_IN_MINUTES = "duration_in_minutes";
        public static final String COLUMN_PREVIEW_RELATIVE_PATH = "preview_relative_path";
    }
}
