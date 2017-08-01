package com.example.android.popularmovies.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.adapters.ReviewsAdapter;
import com.example.android.popularmovies.adapters.TrailersAdapter;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.models.MovieViewModel;
import com.example.android.popularmovies.models.MovieViewModelType;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.FavoriteMovieStorage;
import com.example.android.popularmovies.models.Review;
import com.example.android.popularmovies.models.ReviewCollectionResponse;
import com.example.android.popularmovies.models.ReviewViewModel;
import com.example.android.popularmovies.models.ReviewViewModelType;
import com.example.android.popularmovies.models.Trailer;
import com.example.android.popularmovies.models.TrailerCollectionResponse;
import com.example.android.popularmovies.models.TrailerViewModel;
import com.example.android.popularmovies.models.TrailerViewModelType;
import com.example.android.popularmovies.utils.MovieApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity
        implements TrailersAdapter.OnClickHandler {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.movieDetailContainerView)
    ScrollView movieDetailContainerView;

    @BindView(R.id.movieImageView)
    ImageView movieImageView;

    @BindView(R.id.titleTextView)
    TextView titleTextView;

    @BindView(R.id.ratingTextView)
    TextView ratingTextView;

    @BindView(R.id.releaseDateTextView)
    TextView releaseDateTextView;

    @BindView(R.id.synopsisTextView)
    TextView synopsisTextView;

    @BindView(R.id.durationTextView)
    TextView durationTextView;

    @BindView(R.id.markAsFavoriteButton)
    Button markAsFavoriteButton;

    @BindView(R.id.removeFromFavoriteButton)
    Button removeFromFavoriteButton;

    @BindView(R.id.trailersRecyclerView)
    RecyclerView trailersRecyclerView;

    @BindView(R.id.reviewsRecyclerView)
    RecyclerView reviewsRecyclerView;

    private MovieApi api;
    private MovieViewModelType viewModel;
    private FavoriteMovieStorage favoriteMovieStorage;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        api = new MovieApi();
        favoriteMovieStorage = new FavoriteMovieStorage(getContentResolver());

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            int id = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
            loadMovie(id);
        }

        if (intent.hasExtra(Intent.EXTRA_TITLE)) {
            setTitle(intent.getStringExtra(Intent.EXTRA_TITLE));
        }

        // Setup trailer recycler view
        trailersAdapter = new TrailersAdapter(this);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trailersRecyclerView.setAdapter(trailersAdapter);

        // Setup reviews recycler view
        reviewsAdapter = new ReviewsAdapter();
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewsRecyclerView.setAdapter(reviewsAdapter);
    }


    private void loadMovie(int id) {
        progressBar.setVisibility(View.VISIBLE);
        movieDetailContainerView.setVisibility(View.INVISIBLE);

        api.fetchMovie(id)
            .enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    movieDetailContainerView.setVisibility(View.VISIBLE);
                    setup(new MovieViewModel(response.body()));
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast
                        .makeText(getApplicationContext(), "Error " + t.toString(), Toast.LENGTH_SHORT)
                        .show();                    }
            });
    }


    private void showMarkAsFavoriteButton() {
        removeFromFavoriteButton.setVisibility(View.INVISIBLE);
        markAsFavoriteButton.setVisibility(View.VISIBLE);
    }


    private void showRemoveFromFavoriteButton() {
        removeFromFavoriteButton.setVisibility(View.VISIBLE);
        markAsFavoriteButton.setVisibility(View.INVISIBLE);
    }


    private void setup(MovieViewModelType viewModel) {
        this.viewModel = viewModel;
        setTitle(viewModel.getTitle());

        Picasso.with(getApplicationContext())
            .load(viewModel.previewImage())
            .into(movieImageView);

        titleTextView.setText(viewModel.getTitle());
        ratingTextView.setText(viewModel.getRating());
        releaseDateTextView.setText(viewModel.getReleaseDate());
        synopsisTextView.setText(viewModel.getSynopsis());
        durationTextView.setText(viewModel.getDuration());

        if (favoriteMovieStorage.movieExists(viewModel.getId())) {
            showRemoveFromFavoriteButton();
        } else {
            showMarkAsFavoriteButton();
        }

        loadTrailers(api.fetchTrailers(viewModel.getId()));
        loadReviews(api.fetchReviews(viewModel.getId()));
    }


    public void markAsFavorite(View button) {
        Uri uri = favoriteMovieStorage.markAsFavorite(viewModel.getMovie());

        if (uri != null) {
            showRemoveFromFavoriteButton();
        }
    }


    public void removeFromFavorite(View button) {
        if (favoriteMovieStorage.removeFromFavorite(viewModel.getId())) {
            showMarkAsFavoriteButton();
        }
    }


    private void loadTrailers(Call<TrailerCollectionResponse> apiCall) {
        trailersRecyclerView.setVisibility(View.INVISIBLE);

        apiCall.enqueue(new Callback<TrailerCollectionResponse>() {
            @Override
            public void onResponse(Call<TrailerCollectionResponse> call, Response<TrailerCollectionResponse> response) {
                trailersRecyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                    trailersAdapter.setTrailers(createTrailerViewModels(response.body().trailers));
                }
            }

            @Override
            public void onFailure(Call<TrailerCollectionResponse> call, Throwable t) {
                Toast
                    .makeText(getApplicationContext(), "Error " + t.toString(), Toast.LENGTH_SHORT)
                    .show();
            }
        });
    }


    private void loadReviews(Call<ReviewCollectionResponse> apiCall) {
        reviewsRecyclerView.setVisibility(View.INVISIBLE);

        apiCall.enqueue(new Callback<ReviewCollectionResponse>() {
            @Override
            public void onResponse(Call<ReviewCollectionResponse> call, Response<ReviewCollectionResponse> response) {
                reviewsRecyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                    reviewsAdapter.setReviews(createReviewViewModels(response.body().reviews));
                }
            }

            @Override
            public void onFailure(Call<ReviewCollectionResponse> call, Throwable t) {
                Toast
                    .makeText(getApplicationContext(), "Error " + t.toString(), Toast.LENGTH_SHORT)
                    .show();
            }
        });
    }


    private ArrayList<ReviewViewModelType> createReviewViewModels(Review[] reviews) {
        ArrayList<ReviewViewModelType> viewModels = new ArrayList<ReviewViewModelType>();

        for (Review review: reviews) {
            viewModels.add(new ReviewViewModel(review));
        }

        return viewModels;
    }


    private ArrayList<TrailerViewModelType> createTrailerViewModels(Trailer[] trailers) {
        ArrayList<TrailerViewModelType> viewModels = new ArrayList<TrailerViewModelType>();

        for (Trailer trailer: trailers) {
            viewModels.add(new TrailerViewModel(trailer));
        }

        return viewModels;
    }


    @Override
    public void onTrailerClicked(TrailerViewModelType trailerViewModel) {
        Uri trailerUri = trailerViewModel.getTrailerUri();

        Intent intent = new Intent(Intent.ACTION_VIEW, trailerUri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

