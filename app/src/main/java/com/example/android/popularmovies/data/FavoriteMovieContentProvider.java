package com.example.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import static com.example.android.popularmovies.data.FavoriteMovieContract.FavoriteMovieEntry;

public class FavoriteMovieContentProvider extends ContentProvider {
    public static final int FAVORITE_MOVIES = 100;
    public static final int FAVORITE_MOVIE_WITH_ID = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(FavoriteMovieContract.AUTHORITY, FavoriteMovieContract.PATH_FAVORITE_MOVIES, FAVORITE_MOVIES);
        uriMatcher.addURI(FavoriteMovieContract.AUTHORITY, FavoriteMovieContract.PATH_FAVORITE_MOVIES + "/#", FAVORITE_MOVIE_WITH_ID);

        return uriMatcher;
    }

    private FavoriteMovieDbHelper favoriteMovieDbHelper;


    @Override
    public boolean onCreate() {
        Context context = getContext();
        favoriteMovieDbHelper = new FavoriteMovieDbHelper(context);
        return true;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = favoriteMovieDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        Uri returnUri;

        if (match == FAVORITE_MOVIES) {
            long id = db.insert(FavoriteMovieEntry.TABLE_NAME, null, values);

            if (id < 1) {
                throw new android.database.SQLException("Failed to insert row into " + uri);
            }

            returnUri = ContentUris.withAppendedId(FavoriteMovieEntry.CONTENT_URI, id);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }


    @Override
    public Cursor query(
        @NonNull Uri uri,
        String[] projection,
        String selection,
        String[] selectionArgs,
        String sortOrder
    ) {
        final SQLiteDatabase db = favoriteMovieDbHelper.getReadableDatabase();

        int match = uriMatcher.match(uri);
        Cursor retCursor;

        if (match == FAVORITE_MOVIES) {
            retCursor =  db.query(
                FavoriteMovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
            );
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = favoriteMovieDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        int deletedCount;

        if (match == FAVORITE_MOVIE_WITH_ID) {
            String id = uri.getPathSegments().get(1);

            deletedCount = db.delete(
                FavoriteMovieEntry.TABLE_NAME,
                FavoriteMovieEntry.COLUMN_MOVIE_API_ID + "=?",
                new String[]{id}
            );
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (deletedCount != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deletedCount;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}

