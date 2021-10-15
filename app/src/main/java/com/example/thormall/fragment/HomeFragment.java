package com.example.thormall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thormall.R;
import com.example.thormall.activity.CartActivity;
import com.example.thormall.activity.SearchActivity;
import com.example.thormall.adapter.GroceryItemAdapter;
import com.example.thormall.model.GroceryItem;
import com.example.thormall.util.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment {
    private BottomNavigationView bottomNavView;
    private RecyclerView newItemsRecView, popularRecView, suggestedRecView;

    private GroceryItemAdapter newsItemsAdapter, popularItemsAdapter, suggestedItemsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        initBottomNavView(view);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initRecView();
    }

    private void initRecView() {

        newsItemsAdapter = new GroceryItemAdapter(getActivity());
        newItemsRecView.setAdapter(newsItemsAdapter);
        newItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularItemsAdapter = new GroceryItemAdapter(getActivity());
        popularRecView.setAdapter(popularItemsAdapter);
        popularRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        suggestedItemsAdapter = new GroceryItemAdapter(getActivity());
        suggestedRecView.setAdapter(suggestedItemsAdapter);
        suggestedRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        ArrayList<GroceryItem> newsItems = Utils.getAllItems(getActivity());
        if (newsItems != null) {
            Comparator<GroceryItem> newItemComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getId() - o2.getId();
                }
            };
            Collections.sort(newsItems, Collections.reverseOrder(newItemComparator));
            newsItemsAdapter.setItems(newsItems);
        }

        ArrayList<GroceryItem> popularItems = Utils.getAllItems(getActivity());
        if (popularItems != null) {
            Comparator<GroceryItem> popularItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getPopularityPoint() - o2.getPopularityPoint();
                }
            };
            Collections.sort(popularItems, Collections.reverseOrder(popularItemsComparator));
            popularItemsAdapter.setItems(popularItems);
        }

        ArrayList<GroceryItem> suggestedItem = Utils.getAllItems(getActivity());
        if (suggestedItem != null) {
            Comparator<GroceryItem> suggestedItemComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getUserPoint() - o2.getUserPoint();
                }
            };
            Collections.sort(suggestedItem, Collections.reverseOrder(suggestedItemComparator));
            suggestedItemsAdapter.setItems(suggestedItem);
        }
    }

    private void initBottomNavView(View view) {
        bottomNavView.setSelectedItemId(R.id.home);
        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        Intent intentCart = new Intent(getActivity(), CartActivity.class);
                        intentCart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentCart);
                        break;
                }
                return false;
            }
        });
    }

    private void initView(View view) {
        bottomNavView = view.findViewById(R.id.bottomNavView);
        newItemsRecView = view.findViewById(R.id.newItemsRecView);
        popularRecView = view.findViewById(R.id.popularItemsRecView);
        suggestedRecView = view.findViewById(R.id.suggestedItemsRecView);
    }
}
