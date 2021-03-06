package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.ReviewViewModelType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private ArrayList<ReviewViewModelType> reviews;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.review_item, parent, shouldAttachToParentImmediately);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setup(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews == null ? 0 : reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.reviewAuthorTextView)
        public TextView reviewAuthorTextView;

        @BindView(R.id.reviewContentTextView)
        public TextView reviewContentTextView;

        ReviewViewModelType viewModel;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void setup(ReviewViewModelType viewModel) {
            this.viewModel = viewModel;

            reviewAuthorTextView.setText(viewModel.getAuthor());
            reviewContentTextView.setText(viewModel.getContent());
        }

        @Override
        public void onClick(View view) {

        }
    }

    public void setReviews(ArrayList<ReviewViewModelType> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}

