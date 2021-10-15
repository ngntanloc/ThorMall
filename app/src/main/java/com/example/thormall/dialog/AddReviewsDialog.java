package com.example.thormall.dialog;

import static com.example.thormall.adapter.GroceryItemAdapter.GROCERY_ITEM_KEY;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.thormall.R;
import com.example.thormall.model.GroceryItem;
import com.example.thormall.model.Review;
import com.example.thormall.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddReviewsDialog extends DialogFragment {

    private static final String TAG = "Add Review Dialog";

    public interface AddReview {
        void onAddReviewResult(Review review);
    }

    private AddReview addReview;

    private TextView txtItemName, txtWarning;
    private EditText edtUserName, edtReview;

    private RatingBar ratingBar;

    private GroceryItem incomingItem;

    private Button btnAdd;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review, null);
        initView(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            
            incomingItem = bundle.getParcelable(GROCERY_ITEM_KEY);
            if (incomingItem != null) {
                txtItemName.setText(incomingItem.getName());


                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = edtUserName.getText().toString();
                        String review = edtReview.getText().toString();
                        String date = getCurrentDate();

                        if (userName.isEmpty() || review.isEmpty() ) {
                            txtWarning.setText("Please Fill all the blanks");
                            txtWarning.setVisibility(View.VISIBLE);
                        } else if (ratingBar.getRating() == 0) {
                            txtWarning.setText("Rating can't be equals to zero");
                            txtWarning.setVisibility(View.VISIBLE);
                        } else {
                            txtWarning.setVisibility(View.GONE);
                            try {
                                float rating = ratingBar.getRating();
                                changUserPoint(rating);
                                addReview = (AddReview) getActivity();
                                addReview.onAddReviewResult(new Review(incomingItem.getId(), userName, review, rating, date));
                                dismiss();
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
        return builder.create();
    }

    private void changUserPoint(float ratingPoint) {
        if (ratingPoint >= 4) {
            Utils.changeUserPoint(getActivity(), incomingItem, 3);
        } else if (ratingPoint == 5) {
            Utils.changeUserPoint(getActivity(), incomingItem, 4);
        } else if (ratingPoint > 4) {
            Utils.changeUserPoint(getActivity(), incomingItem, -2);
        } else if (ratingPoint > 3) {
            Utils.changeUserPoint(getActivity(), incomingItem, -3);
        } else if (ratingPoint > 2) {
            Utils.changeUserPoint(getActivity(), incomingItem,  -4);
        } else if (ratingPoint >= 1) {
            Utils.changeUserPoint(getActivity(), incomingItem, -5);
        }
    }


    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        return sdf.format(calendar.getTime());
    }


    private void initView(View view) {
        txtItemName = view.findViewById(R.id.txtItemName);
        txtWarning = view.findViewById(R.id.txtWarning);

        edtUserName = view.findViewById(R.id.edtUserName);
        edtReview = view.findViewById(R.id.edtTxtReview);

        ratingBar = view.findViewById(R.id.RatingBar);

        btnAdd = view.findViewById(R.id.btnAddReview);

    }

}
