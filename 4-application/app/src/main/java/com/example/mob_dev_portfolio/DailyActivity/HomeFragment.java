package com.example.mob_dev_portfolio.DailyActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mob_dev_portfolio.Database.Activity;
import com.example.mob_dev_portfolio.Database.AppDatabase;
import com.example.mob_dev_portfolio.R;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment {


        ExecutorService executor;
        List<Activity> activities;
        int currentIndex = 0;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_home, container, false);

            AppDatabase db = Room.databaseBuilder(getContext(),
                    AppDatabase.class, "my database").fallbackToDestructiveMigration().build();

            this.executor = Executors.newFixedThreadPool(4);

            Button buttonInit = view.findViewById(R.id.button_init);
            buttonInit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            db.activityDao().deleteAllActivities();
                            db.activityDao().insertAll(
                                    new Activity(getString(R.string.dailygoal_one), "4/10"),
                                    new Activity(getString(R.string.dailygoal_two), "2/10"),
                                    new Activity(getString(R.string.dailygoal_three), "3/10"),
                                    new Activity(getString(R.string.dailygoal_four), "5/10"),
                                    new Activity(getString(R.string.dailygoal_five), "6/10"),
                                    new Activity(getString(R.string.dailygoal_six), "6/10"),
                                    new Activity(getString(R.string.dailygoal_seven), "8/10"),
                                    new Activity(getString(R.string.dailygoal_eight), "4/10"),
                                    new Activity(getString(R.string.dailygoal_nine), "2/10"),
                                    new Activity(getString(R.string.dailygoal_ten), "7/10")

                                    );

                            activities = db.activityDao().getAllActivities();
                        }
                    });
                }
            });


            TextView activityTextView = view.findViewById(R.id.activity_text_view);
            TextView activityDifficultyTextView = view.findViewById(R.id.activity_difficulty_text_view);
            Button DisplayButton = view.findViewById(R.id.display_button);
            DisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Activity> activities = db.activityDao().getAllActivities();

                        if (activities.size() > 0) {
                            Activity activity = activities.get(currentIndex);
                            currentIndex = (currentIndex + 1) % activities.size();
                            Log.i("ACTIVITIES", activity.toString());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activityTextView.setText(activity.getActivityName());
                                    activityDifficultyTextView.setText(String.valueOf(activity.getDifficulty()));

                                }
                            });
                        }
                    }
                });
            }
        });
            return view;
        }
    }
