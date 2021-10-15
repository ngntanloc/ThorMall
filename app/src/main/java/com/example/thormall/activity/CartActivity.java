package com.example.thormall.activity;

import static com.example.thormall.activity.GroceryItemActivity.QUANTITY_ITEM_GROCERY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.thormall.R;
import com.example.thormall.fragment.ListCartFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";

    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        setSupportActionBar(toolbar);
        initBottomNavView();

//        Intent intent = getIntent();
//        if (intent != null) {
//            Bundle bundle = intent.getExtras();
//            if (bundle != null) {
//                int quantity = bundle.getInt(QUANTITY_ITEM_GROCERY);
//                Log.d(TAG, "onCreate: quantity: " + quantity);
//            }
//        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new ListCartFragment());
        transaction.commit();

    }

    private void initBottomNavView() {

        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        Intent searchIntent = new Intent(CartActivity.this, SearchActivity.class);
                        searchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(searchIntent);
                        break;
                    case R.id.home:
                        Intent homeIntent = new Intent(CartActivity.this, HomeActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homeIntent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavView);
    }
}