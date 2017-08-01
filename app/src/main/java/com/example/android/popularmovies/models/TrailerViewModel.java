package com.example.android.popularmovies.models;

public class TrailerViewModel implements TrailerViewModelType {
    private Trailer trailer;

    public TrailerViewModel(Trailer trailer) {
        this.trailer = trailer;
    }

    public String getName() {
        return trailer.name;
    }
}
