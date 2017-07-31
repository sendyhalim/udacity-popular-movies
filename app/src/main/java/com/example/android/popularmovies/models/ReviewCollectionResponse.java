package com.example.android.popularreviews.models;

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
