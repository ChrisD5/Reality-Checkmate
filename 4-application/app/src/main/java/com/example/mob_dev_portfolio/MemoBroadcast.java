package com.example.mob_dev_portfolio;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class MemoBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent repeating_Intent = new Intent(context, FirstActivity.class);
        repeating_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, repeating_Intent, PendingIntent.FLAG_IMMUTABLE);

        Properties notifications = new Properties();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.notifications);
            notifications.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        int index = random.nextInt(10) + 1;
        String description = notifications.getProperty("notification" + index);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_pawn)
                .setLargeIcon(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_pawn), 128, 128, false))
                .setContentTitle("Reality Check-Mate")
                .setContentText(description)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }






}
