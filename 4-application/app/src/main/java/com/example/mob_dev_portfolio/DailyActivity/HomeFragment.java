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
                                    new Activity("Practice active listening: Instead of always talking about yourself and your accomplishments, make an effort to listen to others and engage in meaningful conversations.", "4/10"),
                                    new Activity("Give compliments to others: Make it a habit to give genuine compliments to others on their strengths and achievements, rather than constantly seeking validation for your own.", "2/10"),
                                    new Activity("Help someone in need: Volunteering your time and skills to help someone who is less fortunate can help you gain perspective and lower your ego.", "3/10"),
                                    new Activity("Admit your mistakes: Instead of always trying to appear perfect, admit your mistakes and take responsibility for them. This can show humility and help you grow.", "5/10"),
                                    new Activity("Do something anonymously: Perform a good deed without seeking recognition or praise, such as donating to a charity anonymously or helping a stranger in need.", "6/10"),
                                    new Activity("Accept constructive criticism: Instead of becoming defensive when someone offers constructive criticism, listen to their feedback and use it to improve yourself.", "6/10"),
                                    new Activity("Take a break from social media: Constantly seeking validation and attention on social media can feed your ego. Take a break from social media to focus on real-life relationships and activities.", "8/10"),
                                    new Activity("Share credit: When working on a team or collaborating with others, make sure to share credit for successes and accomplishments.", "4/10"),
                                    new Activity("Practice gratitude: Focusing on what you are grateful for can help you appreciate what you have and minimize the need for constant validation and attention.", "2/10"),
                                    new Activity("Engage in activities that challenge you: Push yourself out of your comfort zone and engage in activities that challenge you, such as learning a new skill or taking on a difficult project. This can help you stay humble and focused on personal growth.", "7/10")

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
