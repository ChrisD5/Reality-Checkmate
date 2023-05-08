package com.example.mob_dev_portfolio;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ViewNotifications extends Fragment {

    private ArrayList<NotificationItem> notificationList;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_notifications, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        notificationList = new ArrayList<>();
        setNotificationInfo();
        setAdapter();
    }

    private void setAdapter() {
        NotificationAdapter adapter = new NotificationAdapter(notificationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private void setNotificationInfo() {


        try {
            FileInputStream fis = getActivity().openFileInput("notifications.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            addSampleNotifications();

            String line;
            while ((line = br.readLine()) != null) {
              notificationList.add(new NotificationItem(line));
            }

            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void addSampleNotifications () {
            notificationList.add(new NotificationItem("You're not as perfect as you think. Take a step back and reassess."));
            notificationList.add(new NotificationItem("You're not the only one with good ideas. Listen to others and collaborate."));
            notificationList.add(new NotificationItem("You're not the only one who can do this. Trust and delegate tasks."));
            notificationList.add(new NotificationItem("Think about other people for once. Show empathy and kindness."));
            notificationList.add(new NotificationItem("It's okay to be wrong sometimes. Learn from your mistakes."));
            notificationList.add(new NotificationItem("You don't have to win every argument. Sometimes it's better to just let it go."));
            notificationList.add(new NotificationItem("Acknowledge and learn from your mistakes. Growth comes from failure."));
            notificationList.add(new NotificationItem("Don't let success get to your head. Stay humble and grounded."));
            notificationList.add(new NotificationItem("Be open to feedback from others. It can help you improve."));
            notificationList.add(new NotificationItem("Don't put others down to make yourself look better. Lift each other up."));
            notificationList.add(new NotificationItem("Practice empathy and understanding towards others. Everyone has their own story."));
            notificationList.add(new NotificationItem("Be willing to compromise and collaborate with others. It can lead to better outcomes."));
            notificationList.add(new NotificationItem("Take responsibility for your actions and their consequences. Own up to your mistakes."));
            notificationList.add(new NotificationItem("Don't judge others based on their appearance or background. Get to know them for who they are."));
            notificationList.add(new NotificationItem("Challenge yourself to learn and grow every day. It can lead to new opportunities."));
            notificationList.add(new NotificationItem("Avoid seeking attention and validation for every little thing. Focus on your own growth."));
            notificationList.add(new NotificationItem("Give credit where credit is due. Recognize the efforts of those around you."));
            notificationList.add(new NotificationItem("Appreciate the value and contributions of others. We are all part of a team."));
            notificationList.add(new NotificationItem("Remember that everyone has their own struggles and challenges. Be kind and supportive."));
            notificationList.add(new NotificationItem("Don't be afraid to ask for help or support when you need it. We all need a helping hand sometimes."));
            notificationList.add(new NotificationItem("Be humble and grateful for what you have. Appreciate the present moment."));
            notificationList.add(new NotificationItem("Treat others with kindness and respect, regardless of their status. We are all equal."));
            notificationList.add(new NotificationItem("Focus on the journey, not just the destination. Enjoy the process."));
            notificationList.add(new NotificationItem("Be mindful of your impact on others and the world around you. Small actions can make a big difference."));
            notificationList.add(new NotificationItem("Don't take yourself too seriously. Laugh at yourself and enjoy life."));


        }
    }
