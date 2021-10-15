package com.example.thormall.activity;

import static com.example.thormall.dialog.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.thormall.dialog.AllCategoriesDialog.CALLING_ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thormall.R;
import com.example.thormall.adapter.GroceryItemAdapter;
import com.example.thormall.dialog.AllCategoriesDialog;
import com.example.thormall.model.GroceryItem;
import com.example.thormall.util.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity implements AllCategoriesDialog.GetCategory {

    @Override
    public void onGetCategoryResult(String category) {
        ArrayList<GroceryItem> items = Utils.getItemsByCategory(this, category);
        adapter.setItems(items);
    }

    private static final String TAG = "SearchActivity";

    private MaterialToolbar toolbar;
    private EditText searchBox;
    private ImageView btnSearch;
    private TextView firstCat, secondCat, thirdCat, txtAllCat;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;

    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        setSupportActionBar(toolbar);

        adapter = new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Intent intent = getIntent();
        if (intent != null) {
            String category = intent.getStringExtra("category");
            if (category != null) {
                ArrayList<GroceryItem> items = Utils.getItemsByCategory(this, category);
                adapter.setItems(items);
            }
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSearch();
            }
        });



        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayList<String> categories = Utils.getCategory(this);
        Log.d(TAG, "onCreate: Category: " + categories);
        if (categories != null) {
            if (categories.size() > 0) {
                if (categories.size() == 1) {
                    showCategories(categories, 1);
                } else if (categories.size() == 2) {
                    showCategories(categories, 2);
                } else {
                    showCategories(categories, 3);
                }
            }
        }

        txtAllCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCategoriesDialog dialog = new AllCategoriesDialog();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(ALL_CATEGORIES, Utils.getCategory(SearchActivity.this));
                bundle.putString(CALLING_ACTIVITY, "search_activity");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "all categories dialog");
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        Intent intentCart = new Intent(SearchActivity.this, CartActivity.class);
                        intentCart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentCart);
                        break;
                }
                return false;
            }
        });
    }

    private void showCategories(ArrayList<String> categories, int i) {
        switch (i) {
            case 1:
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));

                secondCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);

                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                        if (items != null) {
                            adapter.setItems(items);
                        }
                    }
                });

                break;
            case 2:
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));

                secondCat.setVisibility(View.VISIBLE);
                secondCat.setText(categories.get(1));

                thirdCat.setVisibility(View.GONE);

                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                        if (items != null) {
                            adapter.setItems(items);
                        }
                    }
                });

                secondCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(1));
                        if (items != null) {
                            adapter.setItems(items);
                        }
                    }
                });

                break;
            case 3:
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));

                secondCat.setVisibility(View.VISIBLE);
                secondCat.setText(categories.get(1));

                thirdCat.setVisibility(View.VISIBLE);
                thirdCat.setText(categories.get(2));

                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                        if (items != null) {
                            adapter.setItems(items);
                        }
                    }
                });

                secondCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(1));
                        if (items != null) {
                            adapter.setItems(items);
                        }
                    }
                });

                thirdCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(2));
                        if (items != null) {
                            adapter.setItems(items);
                        }
                    }
                });

                break;
            default:
                
                firstCat.setVisibility(View.GONE);
                secondCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);
                
                break;
        }
    }

    private void initSearch() {
        if (!searchBox.getText().toString().equals("")) {
            String name = searchBox.getText().toString();

            ArrayList<GroceryItem> items = Utils.searchForItem(this, name);
            if (items != null) {
                adapter.setItems(items);
            }

        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        searchBox = findViewById(R.id.searchBox);
        btnSearch = findViewById(R.id.btnSearch);
        firstCat = findViewById(R.id.txtFirstCat);
        secondCat = findViewById(R.id.txtSecondCat);
        thirdCat = findViewById(R.id.txtThirdCat);
        txtAllCat = findViewById(R.id.txtAllCategories);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        recyclerView = findViewById(R.id.recyclerCategories);
    }


}