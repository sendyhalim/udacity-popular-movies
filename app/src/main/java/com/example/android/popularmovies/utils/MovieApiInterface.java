package com.example.android.popularmovies.utils;

import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.models.MovieCollectionResponse;
import com.example.android.popularmovies.models.TrailerCollectionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface MovieApiInterface {
    @GET("movie/popular")
    Call<MovieCollectionResponse> popularMovies(
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

    @GET("movie/top_rated")
    Call<MovieCollectionResponse> topRatedMovies(
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

    @GET("movie/{id}")
    Call<Movie> movie(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

    @GET("movie/{id}/trailers")
    Call<TrailerCollectionResponse> trailers(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );
}
