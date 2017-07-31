package com.example.android.popularmovies.models;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("id")
    public final int id;

    @SerializedName("author")
    public final String author;

    @SerializedName("content")
    public final String content;

    public Review(int id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }
}
