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
            notifications.add("You're not as perfect as you think. Take a step back and reassess.");
            notifications.add("You're not the only one with good ideas. Listen to others and collaborate.");
            notifications.add("You're not the only one who can do this. Trust and delegate tasks.");
            notifications.add("Think about other people for once. Show empathy and kindness.");
            notifications.add("It's okay to be wrong sometimes. Learn from your mistakes.");
            notifications.add("You don't have to win every argument. Sometimes it's better to just let it go.");
            notifications.add("Acknowledge and learn from your mistakes. Growth comes from failure.");
            notifications.add("Don't let success get to your head. Stay humble and grounded.");
            notifications.add("Be open to feedback from others. It can help you improve.");
            notifications.add("Don't put others down to make yourself look better. Lift each other up.");
            notifications.add("Practice empathy and understanding towards others. Everyone has their own story.");
            notifications.add("Be willing to compromise and collaborate with others. It can lead to better outcomes.");
            notifications.add("Take responsibility for your actions and their consequences. Own up to your mistakes.");
            notifications.add("Don't judge others based on their appearance or background. Get to know them for who they are.");
            notifications.add("Challenge yourself to learn and grow every day. It can lead to new opportunities.");
            notifications.add("Avoid seeking attention and validation for every little thing. Focus on your own growth.");
            notifications.add("Give credit where credit is due. Recognize the efforts of those around you.");
            notifications.add("Appreciate the value and contributions of others. We are all part of a team.");
            notifications.add("Remember that everyone has their own struggles and challenges. Be kind and supportive.");
            notifications.add("Don't be afraid to ask for help or support when you need it. We all need a helping hand sometimes.");
            notifications.add("Be humble and grateful for what you have. Appreciate the present moment.");
            notifications.add("Treat others with kindness and respect, regardless of their status. We are all equal.");
            notifications.add("Focus on the journey, not just the destination. Enjoy the process.");
            notifications.add("Be mindful of your impact on others and the world around you. Small actions can make a big difference.");
            notifications.add("Don't take yourself too seriously. Laugh at yourself and enjoy life.");



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
