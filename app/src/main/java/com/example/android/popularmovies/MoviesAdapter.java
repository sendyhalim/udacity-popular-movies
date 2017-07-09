package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private Movie[] movies;

    private OnClickHandler onClickHandler;

    public interface OnClickHandler {
        void onMovieClicked(MovieViewModelType movieViewModel);
    }

    public MoviesAdapter(OnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.movie_item, parent, shouldAttachToParentImmediately);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieViewModel viewModel = new MovieViewModel(movies[position]);
        holder.setup(viewModel);
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.movieImageView)
        public ImageView movieImageView;

        MovieViewModelType viewModel;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickHandler.onMovieClicked(viewModel);
        }

        public void setup(MovieViewModelType viewModel) {
            this.viewModel = viewModel;

            Picasso.with(movieImageView.getContext())
                    .load(viewModel.previewImage())
                    .into(movieImageView);
        }
    }

    public void setMovies(Movie[] movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}
