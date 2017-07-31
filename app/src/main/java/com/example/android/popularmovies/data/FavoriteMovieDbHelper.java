package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popularmovies.data.FavoriteMovieContract.FavoriteMovieEntry;

public class FavoriteMovieDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite_movies.db";
    private static final int VERSION = 1;

    FavoriteMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "    + FavoriteMovieEntry.TABLE_NAME + " (" +
                FavoriteMovieEntry._ID                 + " INTEGER PRIMARY KEY, " +
                FavoriteMovieEntry.COLUMN_API_MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                FavoriteMovieEntry.COLUMN_TITLE        + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_PREVIEW_URL  + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieEntry.TABLE_NAME);
        onCreate(db);
    }
}

