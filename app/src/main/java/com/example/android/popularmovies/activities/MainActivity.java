package com.example.android.popularmovies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.models.TrailerViewModelType;
import com.example.android.popularmovies.utils.MovieApi;
import com.example.android.popularmovies.models.MovieCollectionResponse;
import com.example.android.popularmovies.models.MovieViewModel;
import com.example.android.popularmovies.models.MovieViewModelType;
import com.example.android.popularmovies.adapters.MoviesAdapter;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.FavoriteMovieStorage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnClickHandler {
    @BindView(R.id.moviesRecyclerView)
    RecyclerView moviesRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public MoviesAdapter moviesAdapter;
    private String currentActionBarTitle;
    private MovieApi api;
    private FavoriteMovieStorage favoriteMovieStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Setup layout manager
        int numberOfColumns = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        moviesRecyclerView.setLayoutManager(layoutManager);

        // Setup adapter
        moviesAdapter = new MoviesAdapter(this);
        moviesRecyclerView.setAdapter(moviesAdapter);

        api = new MovieApi();
        favoriteMovieStorage = new FavoriteMovieStorage(getContentResolver());
        currentActionBarTitle = getString(R.string.popularMovies);
        loadData(api.fetchPopularMovies(1));
    }

    private void loadData(Call<MovieCollectionResponse> apiCall) {
        progressBar.setVisibility(View.VISIBLE);
        moviesRecyclerView.setVisibility(View.INVISIBLE);

        apiCall.enqueue(new Callback<MovieCollectionResponse>() {
            @Override
            public void onResponse(Call<MovieCollectionResponse> call, Response<MovieCollectionResponse> response) {
                progressBar.setVisibility(View.INVISIBLE);
                moviesRecyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                    moviesAdapter.setMovies(createViewModels(response.body().movies));
                }
            }

            @Override
            public void onFailure(Call<MovieCollectionResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(), "Error " + t.toString(), Toast.LENGTH_SHORT)
                    .show();
            }
        });
    }

    public void onMovieClicked(MovieViewModelType movieViewModel) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, movieViewModel.getId());
        intent.putExtra(Intent.EXTRA_TITLE, currentActionBarTitle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.movies, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // For now we only show the first page
        int page = 1;

        if (id == R.id.showMostPopularMovies) {
            currentActionBarTitle = getString(R.string.popularMovies);
            setTitle(currentActionBarTitle);
            loadData(api.fetchPopularMovies(page));

            return true;
        }

        if (id == R.id.showTopRatedMovies) {
            currentActionBarTitle = getString(R.string.topRatedMovies);
            setTitle(currentActionBarTitle);
            loadData(api.fetchTopRatedMovies(page));

            return true;
        }

        if (id == R.id.showFavoriteMovies) {
            currentActionBarTitle = getString(R.string.favoriteMovies);
            setTitle(currentActionBarTitle);
            Movie[] movies = favoriteMovieStorage.fetchFavoriteMovies();
            moviesAdapter.setMovies(createViewModels(movies));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<MovieViewModelType> createViewModels(Movie[] movies) {
        ArrayList<MovieViewModelType> viewModels = new ArrayList<MovieViewModelType>();

        for (Movie movie: movies) {
            viewModels.add(new MovieViewModel(movie));
        }

        return viewModels;
    }
}
