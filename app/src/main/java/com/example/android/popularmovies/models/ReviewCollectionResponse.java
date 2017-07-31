package com.example.android.popularmovies.models;

import com.example.android.popularmovies.models.Review;
import com.google.gson.annotations.SerializedName;

public class ReviewCollectionResponse {
    @SerializedName("page")
    public final int page;

    @SerializedName("results")
    public final Review[] reviews;

    public ReviewCollectionResponse(int page, Review[] reviews) {
        this.page = page;
        this.reviews = reviews;
    }
}
