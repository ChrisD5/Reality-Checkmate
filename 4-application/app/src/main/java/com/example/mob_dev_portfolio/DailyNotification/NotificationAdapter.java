package com.example.mob_dev_portfolio.DailyNotification;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob_dev_portfolio.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private ArrayList<NotificationItem> notificationList;

    public NotificationAdapter(ArrayList<NotificationItem> notificationList) {
        this.notificationList = notificationList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView notification;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notification = itemView.findViewById(R.id.textView3);
        }
    }


    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notificatons, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {
        String notification = notificationList.get(position).getNotification();
        holder.notification.setText(notification);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}

