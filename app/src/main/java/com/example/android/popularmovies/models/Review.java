package com.example.android.popularmovies.models;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("id")
    public final String id;

    @SerializedName("author")
    public final String author;

    @SerializedName("content")
    public final String content;

    public Review(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }
}
