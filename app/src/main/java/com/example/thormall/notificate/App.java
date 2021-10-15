package com.example.thormall.notificate;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationsChannel();
    }

    private void createNotificationsChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager manager = getSystemService(NotificationManager.class);
            NotificationChannel channel1 = new NotificationChannel("channel1", "Channel1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This channel is important for social activities on your profile");

            manager.createNotificationChannel(channel1);
        }
    }

}
