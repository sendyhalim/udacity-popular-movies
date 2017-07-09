package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
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

    private MovieApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        api = new MovieApi();

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            int id = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
            loadMovie(id);
        }

        if (intent.hasExtra(Intent.EXTRA_TITLE)) {
            setTitle(intent.getStringExtra(Intent.EXTRA_TITLE));
        }
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

    private void setup(MovieViewModelType viewModel) {
        setTitle(viewModel.getTitle());

        Picasso.with(getApplicationContext())
                .load(viewModel.previewImage())
                .into(movieImageView);

        titleTextView.setText(viewModel.getTitle());
        ratingTextView.setText(viewModel.getRating());
        releaseDateTextView.setText(viewModel.getReleaseDate());
        synopsisTextView.setText(viewModel.getSynopsis());
    }
}
