package com.example.thormall.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thormall.R;
import com.example.thormall.adapter.CartAdapter;
import com.example.thormall.model.GroceryItem;
import com.example.thormall.util.Utils;

import java.util.ArrayList;

public class ListCartFragment extends Fragment implements CartAdapter.TotalPrice, CartAdapter.RemoveItemCart {

    @Override
    public void onRemoveResult(GroceryItem item) {
        Utils.removeItemFromCart(getActivity(), item);
        ArrayList<GroceryItem> list = Utils.getCartItems(getActivity());
        if (list != null) {
            if (!list.isEmpty()) {
                txtNoItems.setVisibility(View.GONE);
                itemsRelLayout.setVisibility(View.VISIBLE);
                adapter.setItems(list);
            } else {
                txtNoItems.setVisibility(View.VISIBLE);
                itemsRelLayout.setVisibility(View.GONE);
            }
        } else {
            txtNoItems.setVisibility(View.VISIBLE);
            itemsRelLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void getTotalPrice(double price) {
        txtSum.setText(String.valueOf(price) + "$");
    }

    private RecyclerView recyclerView;
    private TextView txtSum, txtNoItems;
    private Button btnNext;
    private RelativeLayout itemsRelLayout;

    private CartAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_cart, null);

        initView(view);

        adapter = new CartAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<GroceryItem> cartItems = Utils.getCartItems(getActivity());
        if (cartItems != null) {
            if (cartItems.isEmpty()) {
                txtNoItems.setVisibility(View.VISIBLE);
                itemsRelLayout.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
            } else {
                txtNoItems.setVisibility(View.GONE);
                itemsRelLayout.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
                adapter.setItems(cartItems);
            }
        } else {
            txtNoItems.setVisibility(View.VISIBLE);
            itemsRelLayout.setVisibility(View.GONE);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new InformationOrderFragment());
                transaction.commit();
            }
        });

        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        txtNoItems = view.findViewById(R.id.txtNoItem);
        txtSum = view.findViewById(R.id.txtTotalPrice);
        btnNext = view.findViewById(R.id.btnNext);
        itemsRelLayout = view.findViewById(R.id.itemsRelLayout);
    }
}