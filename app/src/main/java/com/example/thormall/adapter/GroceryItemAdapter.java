package com.example.thormall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thormall.R;
import com.example.thormall.activity.GroceryItemActivity;
import com.example.thormall.model.GroceryItem;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;


public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder> {

    public static final String GROCERY_ITEM_KEY = "incoming_item";

    private ArrayList<GroceryItem> items = new ArrayList<>();
    private Context context;

    public GroceryItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GroceryItem item = items.get(position);

        holder.txtPrice.setText(String.valueOf(item.getPrice() + "$"));
        holder.txtName.setText(item.getName());

        Glide.with(context)
                .asBitmap()
                .load(item.getImageUrl())
                .into(holder.image);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroceryItemActivity.class);
                intent.putExtra(GROCERY_ITEM_KEY, item);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtPrice;
        private ImageView image;
        private MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            image = itemView.findViewById(R.id.imageItems);
            parent = itemView.findViewById(R.id.cardViewItems);
        }
    }
}
