package com.example.thormall.notificate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
//            managerCompat.cancel(1);
            managerCompat.cancelAll();
        }
    }
}
