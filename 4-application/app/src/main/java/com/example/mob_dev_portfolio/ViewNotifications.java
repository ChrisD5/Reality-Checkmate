package com.example.mob_dev_portfolio;
import android.content.SharedPreferences;
import android.os.Bundle;
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

        recyclerView = view.findViewById(R.id.recyclerView);
        notificationList = new ArrayList<>();
        setNotificationInfo();
        setAdapter();
        return view;
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



            String line;
            while ((line = br.readLine()) != null) {
              notificationList.add(new NotificationItem(line));
            }
            addSampleNotifications();

            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void addSampleNotifications () {
            notificationList.add(new NotificationItem("You're not as perfect as you think."));
            notificationList.add(new NotificationItem("You're not the only one with good ideas."));
            notificationList.add(new NotificationItem("You're not the only one who can do this."));
            notificationList.add(new NotificationItem("Think about other people for once."));
            notificationList.add(new NotificationItem("It's okay to be wrong sometimes."));
            notificationList.add(new NotificationItem("You don't have to win every argument."));
        }
    }
