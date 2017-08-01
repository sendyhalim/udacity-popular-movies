package com.example.android.popularmovies.models;

import android.net.Uri;

public class TrailerViewModel implements TrailerViewModelType {
    private Trailer trailer;

    public TrailerViewModel(Trailer trailer) {
        this.trailer = trailer;
    }

    public String getName() {
        return trailer.name;
    }

    public Uri getTrailerUri() {
        return Uri.parse("https://www.youtube.com/watch")
            .buildUpon()
            .appendQueryParameter("v", trailer.youtubeKey)
            .build();
    }
}
