package com.example.shopping.utility;

import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.example.shopping.R;

public class NotificationBuilder {
    public static NotificationCompat.Builder testNotificationBuilder(Context context, String title, String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constant.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu_store)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder;
    }
}
