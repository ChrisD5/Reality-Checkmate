package com.example.mob_dev_portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import com.example.mob_dev_portfolio.databinding.ActivityFirstBinding;

public class FirstActivity extends AppCompatActivity {

    private ActivityFirstBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}