package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.TrailerViewModelType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {
    private ArrayList<TrailerViewModelType> trailers;

    private OnClickHandler onClickHandler;

    public interface OnClickHandler {
        void onTrailerClicked(TrailerViewModelType trailerViewModel);
    }

    public TrailersAdapter(OnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.trailer_item, parent, shouldAttachToParentImmediately);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setup(trailers.get(position));
    }

    @Override
    public int getItemCount() {
        return trailers == null ? 0 : trailers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.trailerNameTextView)
        public TextView trailerNameTextView;

        TrailerViewModelType viewModel;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickHandler.onTrailerClicked(viewModel);
        }

        public void setup(TrailerViewModelType viewModel) {
            this.viewModel = viewModel;

            trailerNameTextView.setText(viewModel.getName());
        }
    }

    public void setTrailers(ArrayList<TrailerViewModelType> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }
}
