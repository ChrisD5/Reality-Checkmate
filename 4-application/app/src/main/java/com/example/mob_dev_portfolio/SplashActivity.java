package com.example.mob_dev_portfolio;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.mob_dev_portfolio.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {


    private ActivitySplashBinding binding;
    private boolean keep = true;
    private final int DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        SplashScreen.Companion.installSplashScreen(this);

        setContentView(view);


        Handler handler = new Handler();
        handler.postDelayed(() -> {
            keep = false;
            goToFirstActivity();
        }, DELAY);
    }

    private void goToFirstActivity() {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
        finish();
    }
}