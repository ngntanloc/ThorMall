package com.example.thormall.util;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.thormall.model.GroceryItem;

public class TrackUserTime extends Service {

    private static final String TAG = "TrackUserTime";

    private int seconds = 0;
    private boolean shouldFinish = false;
    private GroceryItem item;

    private IBinder binder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        trackTime();
        return binder;
    }

    public class LocalBinder extends Binder {
        public TrackUserTime getService() {
            return TrackUserTime.this;
        }
    }

    private void trackTime() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!shouldFinish) {
                    try {
                        Thread.sleep(1000);
                        seconds++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void setItem(GroceryItem item) {
        this.item = item;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        shouldFinish = true;

        int minute = seconds / 60;
        if (minute > 0) {
            if (item != null) {
                Utils.changeUserPoint(this, item, minute);
                Log.d(TAG, "onDestroy: TrackUserTimePoint: " + minute);
            }
        }
    }
}
