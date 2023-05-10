package com.example.mob_dev_portfolio.ViewCreateNotification;
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

import com.example.mob_dev_portfolio.DailyNotification.NotificationAdapter;
import com.example.mob_dev_portfolio.DailyNotification.NotificationItem;
import com.example.mob_dev_portfolio.R;

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
            FileInputStream fis = getActivity().openFileInput("notifications.xml");
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
            notificationList.add(new NotificationItem(getString(R.string.samnot1)));
            notificationList.add(new NotificationItem(getString(R.string.samnot2)));
            notificationList.add(new NotificationItem(getString(R.string.samnot3)));
            notificationList.add(new NotificationItem(getString(R.string.samnot4)));
            notificationList.add(new NotificationItem(getString(R.string.samnot5)));
            notificationList.add(new NotificationItem(getString(R.string.samnot6)));
            notificationList.add(new NotificationItem(getString(R.string.samnot7)));
            notificationList.add(new NotificationItem(getString(R.string.samnot8)));
            notificationList.add(new NotificationItem(getString(R.string.samnot9)));
            notificationList.add(new NotificationItem(getString(R.string.samnot10)));
            notificationList.add(new NotificationItem(getString(R.string.samnot11)));
            notificationList.add(new NotificationItem(getString(R.string.samnot12)));
            notificationList.add(new NotificationItem(getString(R.string.samnot13)));
            notificationList.add(new NotificationItem(getString(R.string.samnot14)));
            notificationList.add(new NotificationItem(getString(R.string.samnot15)));
            notificationList.add(new NotificationItem(getString(R.string.samnot16)));
            notificationList.add(new NotificationItem(getString(R.string.samnot17)));
            notificationList.add(new NotificationItem(getString(R.string.samnot18)));
            notificationList.add(new NotificationItem(getString(R.string.samnot19)));
            notificationList.add(new NotificationItem(getString(R.string.samnot20)));
            notificationList.add(new NotificationItem(getString(R.string.samnot21)));
            notificationList.add(new NotificationItem(getString(R.string.samnot22)));
            notificationList.add(new NotificationItem(getString(R.string.samnot23)));
            notificationList.add(new NotificationItem(getString(R.string.samnot24)));
            notificationList.add(new NotificationItem(getString(R.string.samnot25)));


        }
    }
