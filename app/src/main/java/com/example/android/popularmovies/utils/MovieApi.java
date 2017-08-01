package com.example.android.popularmovies.utils;

import android.util.Log;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.models.MovieCollectionResponse;
import com.example.android.popularmovies.models.ReviewCollectionResponse;
import com.example.android.popularmovies.models.TrailerCollectionResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MovieApi {
    private static final String baseUrl = "https://api.themoviedb.org/3/";

    private MovieApiInterface request() {
        return new Retrofit.Builder()
                .baseUrl(MovieApi.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApiInterface.class);
    }

    public Call<MovieCollectionResponse> fetchPopularMovies(int page) {
        return request().popularMovies(page, BuildConfig.MOVIE_DB_API_KEY);
    }

    public Call<MovieCollectionResponse> fetchTopRatedMovies(int page) {
        return request().topRatedMovies(page, BuildConfig.MOVIE_DB_API_KEY);
    }

    public Call<Movie> fetchMovie(int id) {
        return request().movie(id, BuildConfig.MOVIE_DB_API_KEY);
    }

    public Call<TrailerCollectionResponse> fetchTrailers(int id) {
        return request().trailers(id, BuildConfig.MOVIE_DB_API_KEY);
    }

    public Call<ReviewCollectionResponse> fetchReviews(int id) {
        return request().reviews(id, BuildConfig.MOVIE_DB_API_KEY);
    }
}

