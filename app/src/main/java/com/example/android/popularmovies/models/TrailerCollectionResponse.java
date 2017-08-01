package com.example.android.popularmovies.models;

import com.google.gson.annotations.SerializedName;

public class TrailerCollectionResponse {
    @SerializedName("youtube")
    public final Trailer[] trailers;

    public TrailerCollectionResponse(Trailer[] trailers) {
        this.trailers = trailers;
    }
}
