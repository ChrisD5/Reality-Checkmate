package com.example.mob_dev_portfolio;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class MemoBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent repeating_Intent = new Intent(context, FirstActivity.class);
        repeating_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, repeating_Intent, PendingIntent.FLAG_IMMUTABLE);

        FileInputStream fis = null;
        try {
            fis = context.openFileInput("notifications.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            List<String> notifications = new ArrayList<>();
            String line = reader.readLine();
            notifications.add("You're not as perfect as you think.");
            notifications.add("You're not the only one with good ideas.");
            notifications.add("You're not the only one who can do this.");
            notifications.add("Think about other people for once.");
            notifications.add("It's okay to be wrong sometimes.");
            notifications.add("You don't have to win every argument.");
            while (line != null) {
                String description = line.trim();
                if (!description.isEmpty()) {
                    notifications.add(description);
                }
                line = reader.readLine();
            }


            if (notifications.isEmpty()) {
                Log.d("MemoBroadcast", "No notifications to display");
                return; // no notifications to display
            }

            Random random = new Random();
            String description = notifications.get(random.nextInt(notifications.size()));


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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
