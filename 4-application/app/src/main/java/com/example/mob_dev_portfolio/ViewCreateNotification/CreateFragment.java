package com.example.mob_dev_portfolio.ViewCreateNotification;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mob_dev_portfolio.R;
import com.example.mob_dev_portfolio.ViewCreateNotification.ViewNotifications;

import java.io.FileOutputStream;
import java.io.IOException;


public class CreateFragment extends Fragment {

    EditText notificationTitle;
    Button button;
    String notificationStr;
    ViewNotifications viewNotifications = new ViewNotifications();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        notificationTitle = view.findViewById(R.id.editTextTextPersonName);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationStr = notificationTitle.getText().toString();
                try {
                    // Append the new notification to the file
                    FileOutputStream fileOutputStream = getActivity().openFileOutput("notifications.xml", Context.MODE_APPEND);
                    fileOutputStream.write(notificationStr.getBytes());
                    fileOutputStream.write(System.getProperty("line.separator").getBytes());
                    fileOutputStream.close();
                    Toast.makeText(getActivity(), "Notification saved", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button clearNotificationsButton = view.findViewById(R.id.clear_notifications_button);
        clearNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the notifications file
                try {
                    FileOutputStream fileOutputStream = getActivity().openFileOutput("notifications.xml", Context.MODE_PRIVATE);
                    fileOutputStream.write("".getBytes());
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "Cleared Custom Notifications", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}