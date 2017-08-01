package com.example.android.popularmovies.models;

public class ReviewViewModel implements ReviewViewModelType {
    private Review review;

    public ReviewViewModel(Review review) {
        this.review = review;
    }

    public String getAuthor() {
        return this.review.author;
    }

    public String getContent() {
        return this.review.content;
    }
}

