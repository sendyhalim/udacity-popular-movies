package com.example.android.popularmovies.models;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("id")
    public final int id;

    @SerializedName("name")
    public final String name;

    @SerializedName("key")
    public final String youtubeKey;

    public Trailer(int id, String name, String youtubeKey) {
        this.id = id;
        this.name = name;
        this.youtubeKey = youtubeKey;
    }
}
