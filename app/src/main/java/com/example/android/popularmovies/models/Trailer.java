package com.example.android.popularmovies.models;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("source")
    public final String youtubeKey;

    @SerializedName("name")
    public final String name;

    public Trailer(String youtubeKey, String name) {
        this.youtubeKey = youtubeKey;
        this.name = name;
    }
}
