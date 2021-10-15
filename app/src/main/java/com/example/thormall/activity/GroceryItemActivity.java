package com.example.thormall.activity;

import static com.example.thormall.adapter.GroceryItemAdapter.GROCERY_ITEM_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thormall.R;
import com.example.thormall.adapter.ReviewsAdapter;
import com.example.thormall.dialog.AddQuantityCartItem;
import com.example.thormall.dialog.AddReviewsDialog;
import com.example.thormall.model.GroceryItem;
import com.example.thormall.model.Review;
import com.example.thormall.util.TrackUserTime;
import com.example.thormall.util.Utils;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Objects;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewsDialog.AddReview, AddQuantityCartItem.AddQuantity {

    public static final String QUANTITY_ITEM_GROCERY = "quantity_item";

    private boolean isBound;
    private TrackUserTime mService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackUserTime.LocalBinder binder = (TrackUserTime.LocalBinder) service;
            mService = binder.getService();
            isBound = true;
            mService.setItem(incomingGrocery);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    public void ResultAddQuantity(int quantity) {
        Log.d(TAG, "ResultAddQuantity: quantity: " + quantity);



        Utils.addItemToCart(GroceryItemActivity.this, incomingGrocery);
        Intent intent = new Intent(GroceryItemActivity.this, CartActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt(QUANTITY_ITEM_GROCERY, quantity);
        intent.putExtras(bundle);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onAddReviewResult(Review review) {
        Log.d(TAG, "onAddReviewResult: new review: " + review);
        Utils.addReview(this, review);

        float sumRating = 0;
        ArrayList<Review> reviews = Utils.getReviewById(this,review.getGroceryItemId());
        if (reviews != null)
            reviewsAdapter.setReviews(reviews);


        int size = reviews.size();
        for (int i = 0; i < size; i++) {
            sumRating += (reviews.get(i).getRating());
        }
        Log.d(TAG, "onAddReviewResult: sumRating: " + sumRating);

        String quantityRatingGrocery = "(" + size + ")";
        Log.d(TAG, "onAddReviewResult: size: " + size);
        quantityRating.setText(quantityRatingGrocery);
        float avgRating = sumRating / size;

        ratingBar.setRating(avgRating);

        Utils.updateRatingAvg(this, avgRating, review.getGroceryItemId());
    }

    private static final String TAG = "GroceryItem";

    private MaterialToolbar toolbar;
    private TextView txtName, txtPrice, txtDescription, txtAddReview;
    private ImageView itemImage;

    private Button btnAddToCart;

    private TextView quantityRating;
    private RatingBar ratingBar;

    private GroceryItem incomingGrocery;

    private RecyclerView reviewsRecView;

    private ReviewsAdapter reviewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);

        initViews();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        reviewsAdapter = new ReviewsAdapter(this);

        Intent intent = getIntent();
        if (intent != null) {
            incomingGrocery = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            if (incomingGrocery != null) {

                Utils.changeUserPoint(GroceryItemActivity.this, incomingGrocery, 1);

                txtName.setText(incomingGrocery.getName());
                txtPrice.setText(String.valueOf(incomingGrocery.getPrice() + "$"));
                txtDescription.setText(incomingGrocery.getDescription());

                Glide.with(this)
                        .asBitmap()
                        .load(incomingGrocery.getImageUrl())
                        .into(itemImage);

                ratingBar.setRating(Utils.getRatingAvg(this, incomingGrocery.getId()));

                ArrayList<Review> reviews = Utils.getReviewById(this, incomingGrocery.getId());

                String quantityRatingGrocery = "(" + reviews.size() + ")";
                quantityRating.setText(quantityRatingGrocery);

                reviewsRecView.setAdapter(reviewsAdapter);
                reviewsRecView.setLayoutManager(new LinearLayoutManager(this));

                if (reviews != null) {
                    if (reviews.size() > 0) {
                        reviewsAdapter.setReviews(reviews);
                    }
                }

                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        AddQuantityCartItem addQuantityDialog = new AddQuantityCartItem();
//                        addQuantityDialog.show(getSupportFragmentManager(), "add quantity");
                        Utils.addItemToCart(GroceryItemActivity.this, incomingGrocery);
                        Intent intent = new Intent(GroceryItemActivity.this, CartActivity.class);

//                        Bundle bundle = new Bundle();
//                        bundle.putInt(QUANTITY_ITEM_GROCERY, quantity);
//                        intent.putExtras(bundle);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });

                txtAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddReviewsDialog dialog = new AddReviewsDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(GROCERY_ITEM_KEY, incomingGrocery);

                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "add reviews");
                    }
                });
            }
        }
    }


    private void initViews() {

        reviewsRecView = findViewById(R.id.reviewsRecView);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtAddReview = findViewById(R.id.txtAddReview);

        itemImage = findViewById(R.id.itemImage);
        ratingBar = findViewById(R.id.ratingBar);
        quantityRating = findViewById(R.id.txtQuantityRating);
        btnAddToCart = findViewById(R.id.btnAddToCard);

        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, TrackUserTime.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isBound)
            unbindService(connection);
    }
}