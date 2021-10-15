package com.example.thormall.activity;

import static com.example.thormall.dialog.AllCategoriesDialog.CALLING_ACTIVITY;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.thormall.R;
import com.example.thormall.dialog.AllCategoriesDialog;
import com.example.thormall.dialog.LicencesDialog;
import com.example.thormall.fragment.HomeFragment;
import com.example.thormall.notificate.Receiver;
import com.example.thormall.util.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    public static final String ALL_CATEGORIES = "categories";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cart:
                        Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.categories:
                        AllCategoriesDialog dialog = new AllCategoriesDialog();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(ALL_CATEGORIES, Utils.getCategory(HomeActivity.this));
                        bundle.putString(CALLING_ACTIVITY, "home_activity");
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "all categories dialog");
                        break;
                    case R.id.terms:
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("Terms")
                                .setMessage("There are no terms.\nEnjoy using our application <3")
                                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.create().show();
                        break;
                    case R.id.licences:
                        LicencesDialog licencesDialog = new LicencesDialog();
                        licencesDialog.show(getSupportFragmentManager(), "licences dialog");
                        break;
                    case R.id.aboutUs:
                        new android.app.AlertDialog.Builder(HomeActivity.this)
                                .setTitle("About Us")
                                .setMessage("Designed and Developed by TanLoc at github.thor\nVisit for more")
                                .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent webIntent = new Intent(HomeActivity.this, WebsiteActivity.class);
                                        startActivity(webIntent);
                                    }
                                }).create().show();
                        break;
                }
                return false;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new HomeFragment());
        transaction.commit();

        checkNotificationsSettings();

    }

    private void checkNotificationsSettings() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        if (!manager.areNotificationsEnabled()) {
            android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(this)
                    .setTitle("Notifications are disable")
                    .setMessage("Please enable the notifications")
                    .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                            startActivity(intent);
                        }
                    });
            alertBuilder.show();
        }
    }


    private void initViews() {
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }
}