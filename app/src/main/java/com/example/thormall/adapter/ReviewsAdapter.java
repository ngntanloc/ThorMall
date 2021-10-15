package com.example.thormall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thormall.R;
import com.example.thormall.model.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {


    private ArrayList<Review> reviews = new ArrayList<>();

    private Context context;



    public ReviewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(reviews.get(position).getUserName());
        holder.txtDescription.setText(reviews.get(position).getText());
        holder.txtDate.setText(reviews.get(position).getDate());
        holder.ratingBar.setRating(reviews.get(position).getRating());
    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtDescription, txtDate;
        private RatingBar ratingBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtUserName);
            txtDescription = itemView.findViewById(R.id.txtReviews);
            txtDate = itemView.findViewById(R.id.txtDate);

            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }

}
