package com.example.android.popularmovies.models;

import com.google.gson.annotations.SerializedName;

public class TrailerCollectionResponse {
    @SerializedName("results")
    public final Trailer[] trailers;

    public TrailerCollectionResponse(Trailer[] trailers) {
        this.trailers = trailers;
    }
}
