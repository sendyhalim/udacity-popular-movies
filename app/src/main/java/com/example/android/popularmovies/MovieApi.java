package com.example.android.popularmovies;

import android.net.Uri;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

class MovieApi {
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
}

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
}

