package com.example.shopping.broadcastReceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.shopping.utility.Constant;
import com.example.shopping.utility.NotificationBuilder;
import com.example.shopping.utility.Util;

public class MusicBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MusicBroadcastReceiver";

    public MusicBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String cmd = intent.getStringExtra("command");
        String artist = intent.getStringExtra("artist");
        String album = intent.getStringExtra("album");
        String track = intent.getStringExtra("track");
        String s = "tag" + artist + ":" + album + ":" + track;
        Log.d(TAG, "onReceive: command: " + cmd);

        Util.showSnackBar((Activity) context, intent.getAction());
//        Util.showSnackBar((Activity) context, s);
        NotificationCompat.Builder builder = NotificationBuilder.testNotificationBuilder(context, track, album + ": " + artist);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//            notificationId is a unique int for each notification that you must define
        notificationManager.notify(Constant.MEDIA_NOTIFICATION_ID, builder.build());
    }
}
